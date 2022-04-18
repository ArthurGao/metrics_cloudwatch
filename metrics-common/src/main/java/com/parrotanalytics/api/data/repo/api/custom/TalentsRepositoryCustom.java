package com.parrotanalytics.api.data.repo.api.custom;

import java.util.List;

import com.parrotanalytics.api.apidb_model.Account;
import com.parrotanalytics.api.data.repo.api.TalentsRepository;
import com.parrotanalytics.apicore.exceptions.APIException;
import com.parrotanalytics.apicore.model.catalogapi.metadata.CatalogItem;

/**
 * Interface for custom methods in {@link TalentsRepository}
 *
 * @author Raja
 * @since v1
 */
public interface TalentsRepositoryCustom
{
	/**
	 * convert content GUID to short ID
	 *
	 * @param parrotUUID
	 * @return
	 */
	Long shortID(String parrotUUID);

	/**
	 * convert content GUIDs to short IDs
	 *
	 * @param parrotUUIDs
	 * @return
	 */
	List<Long> shortIDs(List<String> parrotUUIDs);

	/**
	 * convert content short ID to GUID
	 *
	 * @param shortID
	 * @return
	 */
	String longUUIDs(Long shortID);

	/**
	 * convert content short IDs to long GUIDs
	 *
	 * @param shortIDs
	 * @return
	 */
	List<String> longUUIDs(List<Long> shortIDs);

	/**
	 * @return
	 */
	List<String> findAllParrotIDs();

	/**
	 * @param dynamicFilterQuery
	 * @return
	 */
	List<Long> filteredTalents(String dynamicFilterQuery);

	/**
	 * Returns title metadata bean for given content id
	 *
	 * @param contentID
	 * @return
	 * @throws APIException
	 */
	CatalogItem resolveItem(Object contentID) throws APIException;

	/**
	 * @param parrotID
	 * @return
	 * @throws APIException
	 */
	CatalogItem talentMetadata(String parrotID) throws APIException;

	/**
	 * returns the master catalog Talents
	 *
	 * @return
	 * @throws APIException
	 */
	List<CatalogItem> getAllTalents() throws APIException;

	/**
	 * returns the master catalog Talents
	 *
	 * @param parrotIDsList
	 * @return
	 */
	List<CatalogItem> subscriptionTalents(List<String> parrotIDsList);

	/**
	 * @param account
	 * @return
	 */
	List<CatalogItem> accountTalents(Account account) throws APIException;

	/**
	 * @param idAccount
	 * @return
	 * @throws APIException
	 */
	List<CatalogItem> accountTalents(Integer idAccount) throws APIException;

}
