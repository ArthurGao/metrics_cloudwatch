package com.parrotanalytics.api.services;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.TimeZone;
import java.util.stream.Collectors;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.LocalDate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;
import com.parrotanalytics.api.apidb_model.Account;
import com.parrotanalytics.api.apidb_model.InternalUser;
import com.parrotanalytics.api.apidb_model.Product;
import com.parrotanalytics.api.apidb_model.ProductSpec;
import com.parrotanalytics.api.apidb_model.Role;
import com.parrotanalytics.api.apidb_model.UserRole;
import com.parrotanalytics.api.apidb_model.UserSetting;
import com.parrotanalytics.api.commons.constants.ProductModule;
import com.parrotanalytics.api.commons.constants.ProductSpecificationName;
import com.parrotanalytics.api.commons.constants.SettingType;
import com.parrotanalytics.api.commons.constants.SubscriptionType;
import com.parrotanalytics.api.data.repo.api.ProductRepository;
import com.parrotanalytics.api.data.repo.api.ProductSpecsRepository;
import com.parrotanalytics.api.data.repo.api.ProjectRepository;
import com.parrotanalytics.api.data.repo.api.SubscriptionsRepository;
import com.parrotanalytics.api.data.repo.api.UserRepository;
import com.parrotanalytics.api.data.repo.api.UserSettingRepository;
import com.parrotanalytics.api.request.user.ExportSetting;
import com.parrotanalytics.api.request.user.TopNExportSetting;
import com.parrotanalytics.api.response.user.Setting;
import com.parrotanalytics.api.response.user.SettingsResponse;
import com.parrotanalytics.api.response.user.UserResponse;
import com.parrotanalytics.api.security.TokenHandler;
import com.parrotanalytics.api.security.UserAuthentication;
import com.parrotanalytics.apicore.commons.constants.APIStatus;
import com.parrotanalytics.apicore.config.APIConstants;
import com.parrotanalytics.apicore.config.MessageBundleService;
import com.parrotanalytics.apicore.exceptions.APIException;
import com.parrotanalytics.apicore.exceptions.AuthenticationException;
import com.parrotanalytics.apicore.utils.ServiceCaller;
import com.parrotanalytics.commons.utils.date.CommonsDateUtil;

/**
 * The Class UserService loads users from the backing store.
 *
 * @author Chris
 * @author Jackson
 */
@Service
public class UserService implements UserDetailsService {
    protected static final Logger logger = LogManager.getLogger(UserService.class);

    private static final String RATINGS_ORIGIN_DATE = "2015-03-31";

    private static final int DEFAULT_HISTORICAL_DATA_WINDOW = 30;

    @Autowired(required = true)
    private static UserRepository userRepo;

    @Autowired(required = true)
    private UserRepository userRepository;

    @Autowired
    protected SubscriptionsRepository subscriptionsRepo;

    /* UserSetting repository for UserSetting related operations */
    @Autowired
    protected UserSettingRepository userSettingRepo;

    @Autowired
    protected ProductSpecsRepository proOfferSpecRepo;

    @Autowired
    protected ProductSpecsRepository productSpecsRepo;

    @Autowired
    protected ProjectRepository projectRepo;

    /* I18n message service */
    @Autowired(required = true)
    protected static MessageBundleService messageBundle;

    @Autowired
    protected ProductRepository productRepo;

    public final boolean isUserExist(String email) {
        InternalUser user = null;
        user = userRepo.loadUserByEmail(email);
        if (user == null) {
            return false;
        } else {
            return true;
        }

    }

    /**
     * loads the spring user for given email address
     */
    @Override
    public final User loadUserByUsername(String userName) throws UsernameNotFoundException {
        User springUser = loadAppUserByEmail(userName).toSpringUser();
        TokenHandler.springSecurityChecker.check(springUser);
        return springUser;
    }

    /**
     * loads the application user for given email address from the database
     *
     * @param userEmail
     * @return
     * @throws UsernameNotFoundException
     */
    public final InternalUser loadAppUserByEmail(String userEmail) throws UsernameNotFoundException {
        InternalUser user = null;
        try {
            user = userRepository.loadUserByEmail(userEmail);
        } catch (Exception e) {
            logger.error("Could not load user", e);
        }

        if (user == null) {
            throw new UsernameNotFoundException("User not found or invalid");
        }
        return user;
    }

    public Integer findUserIdByEmailAddress(String emailAddress) {
        return userRepo.findUserIdByEmailAddress(emailAddress);
    }

    public final InternalUser loadExternalAPIUser(String apiKey) throws UsernameNotFoundException {
        com.parrotanalytics.api.apidb_model.InternalUser user = null;
        try {
            user = userRepo.loadAPIUser(apiKey);
        } catch (Exception e) {
            logger.error("Could not load user", e);
        }

        if (user == null) {
            throw new UsernameNotFoundException("User not found or invalid");
        }
        return user;
    }

    public static InternalUser callUser() throws APIException {
        InternalUser apiCallUser = null;

        UserAuthentication authentication = (UserAuthentication) SecurityContextHolder.getContext().getAuthentication();
        apiCallUser = authentication.getInternalUser();

        if (apiCallUser == null) {
            int retryCount = 0;

            do {
                retryCount++;
                apiCallUser = userRepo.loadUserByEmail(authentication.getName());
            }
            while (apiCallUser == null && retryCount <= 1);

            if (apiCallUser == null) {
                throw new AuthenticationException(messageBundle.getMessage("security.failed.userauth"))
                        .withApiError(APIStatus.AUTHENTICATION_FAILED).withHttpStatus(HttpStatus.UNAUTHORIZED);
            }

        }

        return apiCallUser;
    }

    public InternalUser save(InternalUser user) {
        return userRepo.save(user);
    }

    /**
     * Returns all users
     *
     * @throws UsernameNotFoundException
     */
    public final List<InternalUser> findAllUsers(Account account) throws UsernameNotFoundException {
        List<InternalUser> allUsers = new LinkedList<InternalUser>();
        try {
            if (account != null) {
                allUsers = userRepo.findAccountUsers(account);
            } else {
                allUsers = userRepo.findAllUsers();
            }
        } catch (Exception e) {
            logger.error("Could not load users", e);
        }

        return allUsers;
    }

    /**
     * Returns all roleNames
     *
     * @return List<String>
     * @throws UsernameNotFoundException
     */
    public final List<String> findAllRoleNames() throws UsernameNotFoundException {
        List<String> roleNames = new ArrayList<String>();
        try {
            roleNames = userRepo.loadAllRoleNames();
        } catch (Exception e) {
            logger.error("Could not load role names", e);
        }

        if (roleNames.isEmpty()) {
            throw new UsernameNotFoundException("No Role Identified");
        }
        return roleNames;
    }

    /**
     * Returns Role
     *
     * @param roleName
     * @return
     * @throws UsernameNotFoundException
     */
    public final Role loadRoleByRoleName(String roleName) throws UsernameNotFoundException {
        Role role = null;
        try {
            role = userRepo.loadRoleByRoleName(roleName);// TODO
        } catch (Exception e) {
            logger.error("Could not load role", e);
        }

        if (role == null) {
            throw new UsernameNotFoundException("Role not found or invalid");
        }
        return role;
    }

    /**
     * @param userAccount
     * @return
     */
    public String ratingsAccessFromAsString(Account userAccount) {
        return CommonsDateUtil.formattedTime(ratingsAccessFrom(userAccount), CommonsDateUtil.DAFAULT_DATE_PATTERN);
    }

    public DateTime ratingsAccessFrom(Account userAccount) {
        int historicalDataWindow = userAccount.getHistoricalWindow() != 0 ? userAccount.getHistoricalWindow()
                : DEFAULT_HISTORICAL_DATA_WINDOW;

        DateTime subscriptionDate = subscriptionDate(userAccount.getSubscriptionStart());

        String historicalStartDate = fetchSpecValue(productSpecsRepo.getAccountModules(userAccount.getIdAccount()),
                ProductModule.ADDON_HISTORICALDATAACCESS, "start_date");

        Date historicalDataAccessStartDate = null;

        if (!StringUtils.isEmpty(historicalStartDate)) {
            DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
            formatter.setTimeZone(TimeZone.getTimeZone("UTC"));

            try {
                historicalDataAccessStartDate = formatter.parse(historicalStartDate);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }

        subscriptionDate = historicalDataAccessStartDate != null ? subscriptionDate(historicalDataAccessStartDate)
                : subscriptionDate(userAccount.getSubscriptionStart()).minusDays(historicalDataWindow);

        return subscriptionDate;

    }

    private String fetchSpecValue(List<ProductSpec> productSpecs, ProductModule productModule, String string) {

        if (!CollectionUtils.isEmpty(productSpecs)) {
            List<ProductSpec> moduleSpecs = productSpecs.stream()
                    .filter(spec -> spec.getIdProduct().equals(productModule.id())).collect(Collectors.toList());

            for (ProductSpec productSpec : moduleSpecs) {
                if ("start_date".equalsIgnoreCase(productSpec.getSpecificationName())) {
                    return productSpec.getSpecificationValue();
                }
            }
        }

        return null;
    }

    private DateTime subscriptionDate(Date subscriptionDate) {
        DateTime today = new DateTime(DateTimeZone.UTC);

        try {
            if (subscriptionDate != null) {
                DateTime sD = new DateTime(subscriptionDate);

                if (sD.isAfter(new DateTime(RATINGS_ORIGIN_DATE)) && sD.isBefore(today)) {
                    return sD;
                }
            }
        } catch (Exception e) {
            return today;
        }

        return today;
    }

    /**
     * Prepares the product modules subscribed to user account
     *
     * @return
     */
    public Map<String, Map<String, Object>> subscribedModules(InternalUser iUser, Account account) {

        Map<String, Map<String, Object>> modules = new HashMap<>();

        List<ProductSpec> productSpecs = productSpecsRepo.getAccountModules(account.getIdAccount());

        if (CollectionUtils.isNotEmpty(productSpecs)) {
            for (ProductSpec product : productSpecs) {
                Product prod = product.getProduct();

                if (prod == null) {
                    prod = productRepo.findProductById(product.getIdProduct());
                    product.setProduct(prod);
                }
            }

            Map<Product, List<ProductSpec>> specsMap = productSpecs.stream()
                    .collect(Collectors.groupingBy(ProductSpec::getProduct));

            Iterator<Product> productItr = specsMap.keySet().iterator();

            while (productItr.hasNext()) {
                Product product = productItr.next();
                List<ProductSpec> productSpecsForProduct = specsMap.get(product);

                if ("account_level".equalsIgnoreCase(product.getAccess_type())
                        || ("user_level".equalsIgnoreCase(product.getAccess_type())
                        && userHasProductAccess(iUser, product))) {
                    for (ProductSpec productSpec : productSpecsForProduct) {
                        if (modules.get(productSpec.getProduct().getName()) == null) {
                            modules.put(productSpec.getProduct().getName(), new HashMap<>());
                        }

                        if (productSpec.getSpecificationName() != null) {
                            modules.get(productSpec.getProduct().getName()).put(productSpec.getSpecificationName(),
                                    productSpec.getSpecificationValue());
                        }
                    }
                }
            }
        }

        return modules;
    }

    private boolean userHasProductAccess(InternalUser iUser, Product product) {
        if (null != product && null != iUser.hasAccessBreakdownModule()) {
            if (product.getIdProduct() == ProductModule.ADDON_DEMANDBREAKDOWN.id() && iUser.hasAccessBreakdownModule()) {
                return true;
            }
        }
        return false;
    }

    /* Configuration for Account Config */
    @Deprecated
    public Map<String, Object> loadAccountConfig(int accountId) {
        List<ProductSpec> accountConfigs = subscriptionsRepo.getAccountConfigs(accountId, 6);

        Map<String, Object> configs = new HashMap<String, Object>();

        if (accountConfigs != null) {
            accountConfigs.forEach(config -> configs.put(config.getSpecificationName(),
                    Integer.parseInt(config.getSpecificationValue())));
        }

        return configs;
    }

    @Deprecated
    public String calculateEndDate(Account account) {

        ProductSpec consultantType = proOfferSpecRepo.getDPContractValue(account.getIdAccount(),
                ProductSpecificationName.CONSULTANTTYPE.value());

        ProductSpec spec = proOfferSpecRepo.getDPContractValue(account.getIdAccount(),
                ProductSpecificationName.SUBSCRIPTIONLENGTH.value());

        String resultDate = null;

        if (null != spec && null != consultantType) {
            Integer subscriptionLength = Integer.valueOf(spec.getSpecificationValue());

            if (consultantType.getSpecificationValue().equals(ProductSpecificationName.NONCARRYOVER.value())) {
                LocalDate startDate = new LocalDate(account.getSubscriptionStart());
                LocalDate currentDate = new LocalDate();
                LocalDate endOfMonth = new LocalDate();
                if (currentDate.getDayOfMonth() < startDate.getDayOfMonth()) {
                    endOfMonth = currentDate.withDayOfMonth(startDate.getDayOfMonth());
                } else {
                    endOfMonth = currentDate.plusMonths(1);
                    endOfMonth = endOfMonth.withDayOfMonth(startDate.getDayOfMonth());

                }
                resultDate = endOfMonth.toString();
            } else if (consultantType.getSpecificationValue().equals(ProductSpecificationName.CARRYOVER.value())) {

                LocalDate startDate = new LocalDate(account.getSubscriptionStart());
                startDate = startDate.plusMonths(subscriptionLength);
                resultDate = startDate.toString();

            } else if (consultantType.getSpecificationValue().equals(ProductSpecificationName.YEARLY.value())) {

                LocalDate startDate = new LocalDate(account.getSubscriptionStart());
                LocalDate currentDate = new LocalDate();
                LocalDate currentYearExpiry = startDate.withYear(currentDate.getYear());
                if (currentDate.isBefore(currentYearExpiry)) {
                    resultDate = currentYearExpiry.toString();

                } else {
                    resultDate = currentYearExpiry.plusYears(1).toString();
                }

            }
        }

        return resultDate;
    }

    public UserResponse injectUserDetail(InternalUser iUser, UserResponse userResponse) throws APIException {
        userResponse.setUserId(iUser.getIdUser());
        userResponse.setFirstName(iUser.getFirstName());
        userResponse.setLastName(iUser.getLastName());
        userResponse.setMobileNumber(iUser.getMobileNumber());
        userResponse.setUserEmail(iUser.getEmailAddress());
        userResponse.setCreatedOn(CommonsDateUtil.formatDate(iUser.getCreatedOn()));
        userResponse.setStatus(iUser.getStatus());
        userResponse.setTitle(iUser.getTitle());
        // inject role details to role response
        List<String> roleNameList = new ArrayList<String>();

        List<UserRole> userRoles = iUser.getUserRoles();
        for (UserRole roles : userRoles) {
            Role iRole = roles.getRole();
            roleNameList.add(iRole.getRoleName());
        }
        userResponse.setUserRoles(roleNameList);
        // inject user setting to response
        userResponse.setSettings(prepareSettingsResponse(
                userSettingRepo.loadUserSettingsByType(iUser.getIdUser(), SettingType.PROFILE.value()), true)
                .getSettings());

        return userResponse;
    }

    private SettingsResponse prepareSettingsResponse(List<UserSetting> userSettings, boolean valueRequired)
            throws APIException {
        SettingsResponse settingsResponse = new SettingsResponse();

        for (UserSetting uS : userSettings) {
            if (uS != null) {
                Setting setting = new Setting();
                setting.setSettingType(uS.getSettingType());
                setting.setSettingName(uS.getSettingName());

                if (valueRequired) {
                    Object settingValue = null;

                    if (uS.getSettingName().equals("avatarAlias")) {
                        if (!uS.getSettingValue().equals(SubscriptionType.DEFAULT.value())) {
                            settingValue = "https://cdn.parrotanalytics.com/images/poster-hyphen/"
                                    + uS.getSettingValue() + ".jpg";
                        } else {
                            settingValue = uS.getSettingValue();
                        }
                    } else {
                        settingValue = parseValue(uS.getSettingType(), uS);
                    }

                    setting.setSettingValue(settingValue);
                }

                setting.setLastUsedOn(uS.getLastUsedOn());

                settingsResponse.addSetting(setting);
            }
        }

        return settingsResponse;
    }

    private Object parseValue(String settingType, UserSetting uS) throws APIException {
        Object settingValue = null;

        try {
            if (SettingType.EXPORT.value().equalsIgnoreCase(settingType))
                settingValue = new Gson().fromJson(uS.getSettingValue(), ExportSetting.class);
            else if (SettingType.TOPN_EXPORT.value().equalsIgnoreCase(settingType))
                settingValue = new Gson().fromJson(uS.getSettingValue(), TopNExportSetting.class);
            else if (SettingType.PROFILE.value().equalsIgnoreCase(settingType))
                settingValue = uS.getSettingValue();
        } catch (Exception e) {
            throw new APIException("incorrect setting value");
        }

        return settingValue;
    }

    public List<InternalUser> getDemandPortalUsers(Account account) {
        List<InternalUser> userList = userRepo.findActiveAccountUsers(account);
        List<InternalUser> dpUsers = new ArrayList<InternalUser>();
        for (InternalUser user : userList) {
            List<UserRole> userRoles = user.getUserRoles();
            if (!userRoles.isEmpty() && userRoles != null) {
                for (UserRole role : userRoles) {
                    if (role.getRole().getIdRole().equals(4)) {
                        break;
                    } else if (role.getRole().getIdRole().equals(3)) {
                        dpUsers.add(user);
                    }
                }
            }
        }
        return dpUsers;
    }

    public boolean isSupportUser(InternalUser user) {
        List<UserRole> roleList = user.getUserRoles().stream()
                .filter(x -> x.getRole().getIdRole() == Role.SUPPORT_ROLE_ID).collect(Collectors.toList());

        if (!CollectionUtils.isEmpty(roleList)) {
            return true;
        } else {
            return false;
        }
    }

    public UserRepository getUserRepo() {
        return userRepo;
    }

    public void setUserRepo(UserRepository userRepo) {
        this.userRepo = userRepo;
    }

    /**
     * This method validates if user is of Monitor tier account. It prevents the
     * user from different account(not Monitor one) to logon TV360. Task
     * https://parrotanalytics.kanbanize.com/ctrl_board/20/cards/5037/details/
     *
     * @param loadedUser
     * @return
     */
    public boolean isMonitorTierUser(InternalUser loadedUser) {
        Account userAccount = loadedUser.getAccount();
        return userAccount.getIdAccount() == APIConstants.MONITOR_ACCOUNT;
    }

    public boolean isMonitorViewerRole(InternalUser loadedUser) {

        List<UserRole> roles = loadedUser.getUserRoles();
        Optional<UserRole> monitorViewerRole = roles.stream().filter(userRole -> userRole.getRole() != null
                && userRole.getRole().getRoleName().equalsIgnoreCase(Role.MONITOR_VIEWER)).findFirst();

        return monitorViewerRole.isPresent();
    }

    public boolean isMonitorStripeUser(InternalUser loadedUser) {
        Map<String, String[]> requestParams = new HashMap<String, String[]>();
        requestParams.put("email", new String[]
                {
                        loadedUser.getEmailAddress()
                });
        Map<String, String> requestHeaders = new HashMap<String, String>();
        try {
            ResponseEntity<String> response = ServiceCaller.executeCall(
                    com.parrotanalytics.apicore.commons.constants.Service.PAYMENT_API.value(), HttpMethod.GET,
                    "/userplan", requestParams, "", requestHeaders);
            return response.getStatusCode() == HttpStatus.OK;
        } catch (Exception e) {
            logger.error("Could not load Stripe userplan", e);
            return false;
        }
    }

    public boolean isEnterpriseRole(InternalUser loadedUser) {
        List<UserRole> roles = loadedUser.getUserRoles();
        Optional<UserRole> enterpriseRole = roles.stream()
                .filter(userRole -> userRole.getRole() != null
                        && (userRole.getRole().getRoleName().equalsIgnoreCase(Role.ENTERPRISE_VIEWER)
                        || userRole.getRole().getRoleName().equalsIgnoreCase(Role.ENTERPRISE_ADMIN)))
                .findFirst();

        return enterpriseRole.isPresent();
    }

    public boolean isTalentLiteUser(InternalUser loadedUser) {
        if (hasRole(Role.TALENT_ENTERPRISE_MODULE, loadedUser.getUserRoles())
            || isAccountHaveProduct(loadedUser,
            ProductModule.ADDON_TALENT_ENTERPRISE_MODULE.id())) {
            return false;
        }
        return hasRole(Role.TALENT_LITE_VIEWER, loadedUser.getUserRoles()) || isAccountHaveProduct(
            loadedUser, ProductModule.ADDON_TALENT_LITE_VIEWER.id());
    }

    public boolean isMovieLiteUser(InternalUser loadedUser) {
        if (hasRole(Role.MOVIE_ENTERPRISE_MODULE, loadedUser.getUserRoles())
            || isAccountHaveProduct(loadedUser,
            ProductModule.ADDON_MOVIE_ENTERPRISE_MODULE.id())) {
            return false;
        }
        return hasRole(Role.MOVIE_LITE_VIEWER, loadedUser.getUserRoles()) || isAccountHaveProduct(
            loadedUser, ProductModule.ADDON_MOVIE_LITE_VIEWER.id());
    }

    protected boolean hasRole(Integer selectedIdRole, List<UserRole> roles) {
        return org.apache.commons.collections4.CollectionUtils.isNotEmpty(
            roles.stream().filter(role -> role.getId().getIdRole() == selectedIdRole).collect(
                Collectors.toList()));
    }

    protected boolean isAccountHaveProduct(InternalUser internalUser, Integer productId) {
        Account account = internalUser.getAccount();
        if (account != null) {
            ProductSpec productSpec = productSpecsRepo.getModuleByAccountId(account.getIdAccount(),
                productId);
            return productSpec != null;
        }
        return false;
    }

}