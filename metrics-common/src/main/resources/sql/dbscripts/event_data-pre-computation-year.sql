USE `metrics`;
DROP EVENT IF EXISTS `data-pre-computation-year`;
DELIMITER $$

CREATE EVENT `data-pre-computation-year` ON SCHEDULE EVERY 1 QUARTER STARTS '2019-01-01 18:30:00' ON COMPLETION NOT PRESERVE ENABLE DO BEGIN

	SET @start_date = (SELECT start from `metrics`.`range_key_lookup` WHERE interval_type = 'year' and start <= DATE_SUB(NOW(),INTERVAL 1 YEAR) order by start DESC LIMIT 1);
	SET @end_date = (SELECT end from `metrics`.`range_key_lookup` WHERE interval_type = 'year' and start <= DATE_SUB(NOW(),INTERVAL 1 YEAR) order by start DESC LIMIT 1);
    SET @countries = (SELECT GROUP_CONCAT(DISTINCT iso SEPARATOR ',') FROM `subscription`.`countries` c  where c.`idCountries` in (select distinct subscriptionRefId from `subscription`.`subscriptions` WHERE `subscriptionType` = 'market' and `idAccount`= 99) order by iso ASC);

   	CALL `metrics`.`SP_BACKFILL_COMPUTED_DATASETS`(@start_date, @end_date, 'year');

   	-- all markets weighted precomputation --
    CALL `metrics`.`SP_BACKFILL_WEIGHTED_COMPUTED_DATASETS`(@start_date, @end_date,@countries, 'global','year');

END $$

DELIMITER ;