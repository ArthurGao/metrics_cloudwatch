package com.parrotanalytics.api.data.repo.api.custom.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.function.Consumer;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.apache.commons.collections.CollectionUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.parrotanalytics.api.apidb_model.Account;
import com.parrotanalytics.api.apidb_model.comparators.DisplayNameSort;
import com.parrotanalytics.api.data.repo.api.AccountRepository;
import com.parrotanalytics.api.data.repo.api.cache.MetadataCache;
import com.parrotanalytics.api.data.repo.api.custom.TalentsRepositoryCustom;
import com.parrotanalytics.apicore.exceptions.APIException;
import com.parrotanalytics.apicore.model.catalogapi.metadata.CatalogItem;
import com.parrotanalytics.apicore.utils.APIHelper;
import com.parrotanalytics.commons.constants.ParrotConstants;
import com.parrotanalytics.commons.service.Alerting;

/**
 * @author Minh Vu
 * @author Raja
 */
public class TalentsRepositoryImpl implements TalentsRepositoryCustom
{

    private static final Logger logger = LogManager.getLogger(TalentsRepositoryImpl.class);

    @PersistenceContext(unitName = "PARROT_API")
    private EntityManager entityManager;

    @Autowired
    private MetadataCache metadataCache;

    @Autowired
    private AccountRepository accountRepo;

    @PostConstruct
    public void init()
    {
        logger.debug("--> INITIALIZING TRImpl ....");
    }

    @Override
    public Long shortID(String parrotUUID)
    {
        return metadataCache.shortID(parrotUUID);
    }

    @Override
    public String longUUIDs(Long shortID)
    {
        return metadataCache.longUUID(shortID);
    }

    /**
     *
     */
    public List<Long> shortIDs(List<String> parrotUUIDs)
    {
        List<Long> shortIDs = parrotUUIDs.stream().map(parrotUUID -> {
            try
            {
                return metadataCache.shortID(parrotUUID);
            }
            catch (Exception e)
            {
                logger.error("failed to resolve UUID : {}", parrotUUID);
                return (long) 0;
            }
        }).filter(shortId -> shortId != 0).collect(Collectors.toList());
        return shortIDs;
    }

    public List<String> longUUIDs(List<Long> shortIDs)
    {
        final List<String> longUUIDs = new ArrayList<String>();

        shortIDs.stream().forEach(new Consumer<Long>()
        {
            @Override
            public void accept(Long shortID)
            {
                try
                {

                    String parrotID = metadataCache.longUUID(shortID);

                    if (parrotID == null)
                    {
                        CatalogItem resolveItem = resolveItem(shortID);

                        if (resolveItem != null)
                        {
                            parrotID = resolveItem.getParrotID();
                        }

                    }

                    if (parrotID != null)
                    {
                        longUUIDs.add(metadataCache.longUUID(shortID));
                    }

                }
                catch (APIException e)
                {
                    logger.error("failed to resolve ShortID : {}", shortIDs);
                }
            }
        });

        return longUUIDs;
    }

    /**
     * resolves the catalog item for given parrot id
     */
    @Override
    public CatalogItem resolveItem(Object parrotID) throws APIException
    {
        CatalogItem catalogItem = metadataCache.resolveItem(parrotID);

        /* if item is missing */
        if (catalogItem == null)
        {
            logger.warn("resolving {} from ElasticSeach talent_metadata..", parrotID);
            catalogItem = metadataCache.fetchMetadata(parrotID.toString(), ParrotConstants.TALENT);
        }
        // if still unable to resolve it
        if (catalogItem == null)
        {
            Alerting.sendSlackAlarm("Portal Metadata Error", "Metadata Missing for show : " + parrotID + "[ "
                    + APIHelper.runningEnvironment() + "] in Global Account");
        }

        return catalogItem;
    }

    /**
     * @throws APIException
     */
    @Override
    public List<CatalogItem> accountTalents(Account account) throws APIException
    {
        return metadataCache.accountTalents(account);
    }

    @Override
    public List<CatalogItem> accountTalents(Integer idAccount) throws APIException
    {
        Account account = accountRepo.findByIdAccount(idAccount);

        return metadataCache.accountTalents(account);
    }

    @Override
    public CatalogItem talentMetadata(String parrotID) throws APIException
    {
        try
        {
            return metadataCache.getCacheCatalogTitles().get(parrotID);
        }
        catch (ExecutionException e)
        {
            throw new APIException(e.getLocalizedMessage());
        }
    }

    public List<CatalogItem> subscriptionTalents(List<String> parrotIDsList)
    {
        parrotIDsList = parrotIDsList.stream().distinct().collect(Collectors.toList());

        List<CatalogItem> subsTalents = new ArrayList<CatalogItem>();

        parrotIDsList.stream().forEach(new Consumer<String>()
        {
            @Override
            public void accept(String parrotID)
            {
                try
                {
                    CatalogItem item = metadataCache.getCacheCatalogTitles().get(parrotID);

                    if (item != null)
                    {
                        subsTalents.add(item);
                    }
                    else
                    {
                        logger.error("failed to fetch metadata for : [{}]", parrotID);
                    }
                }
                catch (Exception e)
                {
                    logger.error("error in talent fetching : {}", e);
                }
            }
        });

        /* sort catalog items by display name */
        Collections.sort(subsTalents, new DisplayNameSort());

        return subsTalents;
    }

    @Override
    public List<CatalogItem> getAllTalents() throws APIException
    {

        Collection<CatalogItem> allTalents = metadataCache.getCacheCatalogTitles().asMap().values();

        if (CollectionUtils.isNotEmpty(allTalents))
        {
            return (new ArrayList<CatalogItem>(allTalents));
        }
        else
        {
            return new ArrayList<>();
        }
    }

    @Override
    public List<String> findAllParrotIDs()
    {
        List<String> parrotIDs = new ArrayList<>();

        Iterator<String> showItr = metadataCache.getCacheCatalogTitles().asMap().keySet().iterator();

        while (showItr.hasNext())
        {
            parrotIDs.add(showItr.next());
        }

        return parrotIDs;
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<Long> filteredTalents(String dynamicFilterQuery)
    {
        return entityManager.createNativeQuery(dynamicFilterQuery).getResultList();
    }
}
