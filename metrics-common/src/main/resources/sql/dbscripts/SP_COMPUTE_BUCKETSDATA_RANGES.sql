-- Create syntax for 'SP_COMPUTE_BUCKETSDATA_RANGES'

DELIMITER ;;
CREATE DEFINER=`minh`@`%` PROCEDURE `SP_COMPUTE_BUCKETSDATA_RANGES`(
IN date_from DATE,
IN date_to DATE,
IN range_key VARCHAR (20)
)
BEGIN
	DECLARE enabled BOOLEAN DEFAULT TRUE;
	DECLARE finished INT DEFAULT 0;
	DECLARE iso_code VARCHAR (20) DEFAULT "";
	DECLARE customer_ready_account INT DEFAULT 99;

	INSERT INTO `metrics`.`sp_logging`(TIMESTAMP, `log`) VALUES(now(), concat('executing `SP_COMPUTE_GENESDATA_RANGES` for date [', date_from,' to ', date_to, ']'));
	
	INSERT INTO `metrics`.`genesdata_computed`
SELECT DATE,'latest', country_avg.country,
	   'genre' AS gene,
       country_avg.genevalue, 
       country_avg.avg_raw_de AS country_average, 
       global_avg.global_avg AS global_average, 
       country_avg.avg_raw_de / global_avg.global_avg AS diff,
       rank
FROM   (SELECT max(e1.date) AS DATE, c1.genevalue, 
               AVG(e1.raw_de) AS global_avg 
        FROM   metrics.expressions_daily AS e1 
               INNER JOIN portal.catalog_genes c1 
                       ON e1.`short_id` = c1.`short_id` 
        WHERE e1.DATE IN (SELECT `datestr` FROM `date_lookup_prod` WHERE datestr >= date_from AND datestr <= date_to)
        AND e1.`short_id` IN (SELECT `subscriptionRefId` FROM `subscription`.`subscriptions` WHERE `idAccount` = customer_ready_account AND `subscriptionType` = 'tvtitle')
        AND e1.country IN (SELECT c.`iso` FROM `subscription`.`subscriptions` s JOIN `subscription`.`countries` c ON s.`subscriptionRefId` = c.`idCountries` WHERE `idAccount` = customer_ready_account AND `subscriptionType` = 'market')
        AND c1.gene = 'genre' 
        GROUP  BY c1.genevalue) AS global_avg 
       INNER JOIN (SELECT alldata.country, 
                          alldata.genevalue, 
                          alldata.avg_raw_de,
                          alldata.rank 
                   FROM   (SELECT t.country, 
                                  t.genevalue, 
                                  t.avg_raw_de, 
                                  @country_rank := IF( 
                                  @current_country = t.country, 
                                                   @country_rank + 1, 1) AS rank, 
                                  @current_country := t.country 
                           FROM   (SELECT e.country, 
                                          c.genevalue, 
                                          AVG(e.raw_de) AS avg_raw_de 
                                   FROM   metrics.expressions_daily e 
                                          INNER JOIN `portal`.`catalog_genes` c 
                                                  ON e.`short_id` = c.`short_id` 
        							WHERE e.DATE IN (SELECT `datestr` FROM `date_lookup_prod` WHERE datestr >= date_from AND datestr <= date_to)
        							AND e.`short_id` IN (SELECT `subscriptionRefId` FROM `subscription`.`subscriptions` WHERE `idAccount` = customer_ready_account AND `subscriptionType` = 'tvtitle')
        							AND e.country IN (SELECT c.`iso` FROM `subscription`.`subscriptions` s JOIN `subscription`.`countries` c ON s.`subscriptionRefId` = c.`idCountries` WHERE `idAccount` = customer_ready_account AND `subscriptionType` = 'market')
                                          AND c.gene = 'genre' 
                                   GROUP  BY e.country, c.genevalue) AS t 
                           ORDER  BY t.country, 
                                     t.avg_raw_de DESC) AS alldata 
                   WHERE  alldata.rank <= 10) AS country_avg 
               ON country_avg.genevalue = global_avg.genevalue;		
	
END;;
DELIMITER ;
