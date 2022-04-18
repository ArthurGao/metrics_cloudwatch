package com.parrotanalytics.api.security;

import java.util.Base64;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

import org.apache.commons.collections4.map.PassiveExpiringMap;
import org.springframework.security.authentication.AccountStatusUserDetailsChecker;
import org.springframework.security.core.userdetails.User;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.parrotanalytics.api.config.APIConfig;
import com.parrotanalytics.commons.config.ConfigFactory;
import com.parrotanalytics.commons.constants.App;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

/**
 * The Class TokenHandler generates and decodes tokens handed out by us, given
 * their expire time and session secret.
 * 
 * @author Chris
 */
public final class TokenHandler
{
    private static final int SECONDS_THRESHOLD_TO_REFRESH_TOKEN = 3600;

    private final ObjectMapper mapper;

    /*
     * Token Expiry Time in Seconds
     * 
     * 2 hours
     */
    public static final long TOKEN_EXPIRY_TIMEOUT_SECONDS = TimeUnit.MINUTES
            .toSeconds(ConfigFactory.getConfig(App.TV360_API).readInteger(APIConfig.TOKEN_EXPIRY_MINS));

    /*
     * JWT Token Signing Secret
     */
    private static final String ENCODED64_SESSION_SECRET = Base64.getEncoder()
            .encodeToString(ConfigFactory.getConfig(App.TV360_API).readProperty(APIConfig.D360_JWT_SECRET).getBytes());

    /*
     * JWT Token Issuer
     */
    private static final String ISSUER = "parrotanalytics.com";

    private PassiveExpiringMap<String, String> tokenCache = new PassiveExpiringMap<>(120000, TimeUnit.MILLISECONDS,
            new ConcurrentHashMap<>());

    public static final AccountStatusUserDetailsChecker springSecurityChecker = new AccountStatusUserDetailsChecker();

    public TokenHandler()
    {
        this.mapper = new ObjectMapper();
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }

    @SuppressWarnings("unchecked")
    public String createTokenForUser(User user)
    {
        springSecurityChecker.check(user);

        Map<String, Object> claimsMap = mapper.convertValue(new UserClaimsJsonMap(user, UUID.randomUUID().toString(),
                TimeUnit.MILLISECONDS.toSeconds(System.currentTimeMillis()),
                TimeUnit.MILLISECONDS.toSeconds(System.currentTimeMillis()) + TOKEN_EXPIRY_TIMEOUT_SECONDS, ISSUER),
                Map.class);

        return Jwts.builder().setClaims(claimsMap).signWith(SignatureAlgorithm.HS512, ENCODED64_SESSION_SECRET)
                .compact();
    }

    public User parseUserFromToken(String token)
    {
        Claims body = Jwts.parser().setSigningKey(ENCODED64_SESSION_SECRET).parseClaimsJws(token).getBody();
        UserClaimsJsonMap parsedUser = mapper.convertValue(body, UserClaimsJsonMap.class);
        User springUser = parsedUser.toBasicUser();
        springSecurityChecker.check(springUser);
        return springUser;
    }

    public boolean isRefreshTokenRequired(String accessToken)
    {
        if (tokenCache.get(accessToken) == null)
        {
            Claims body = Jwts.parser().setSigningKey(ENCODED64_SESSION_SECRET).parseClaimsJws(accessToken).getBody();
            UserClaimsJsonMap parsedUser = mapper.convertValue(body, UserClaimsJsonMap.class);

            if (parsedUser.secondsToExpire() <= SECONDS_THRESHOLD_TO_REFRESH_TOKEN)
            {
                return true;
            }
        }

        return false;
    }

    public void registerToken(String accessToken, String refreshToken)
    {
        tokenCache.put(accessToken, refreshToken);
    }

}