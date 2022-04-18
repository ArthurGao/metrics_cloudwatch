DELIMITER $$;
CREATE PROCEDURE `metrics`.`SP_COMPUTE_EXPRESSION_RANGES`(
IN date_from DATE,
IN date_to DATE,
IN range_key VARCHAR (20),
IN interval_type VARCHAR(20))
BEGIN
	DECLARE enabled BOOLEAN DEFAULT TRUE;
	DECLARE finished INT DEFAULT 0;
	DECLARE iso_code VARCHAR (20) DEFAULT "";
	DECLARE customer_ready_account INT DEFAULT 99;	
	
	DECLARE cursor_countries CURSOR FOR 
	SELECT iso FROM `subscription`.`countries` WHERE `active` = 1;
	
	DECLARE CONTINUE HANDLER FOR NOT FOUND SET finished = 1;
	
	SET @target_table = '`metrics`.`expressions_computed`';
	
	IF interval_type = 'WEEK' THEN 
		SET @target_table = '`metrics`.`expressions_computed_weekly`';	
	ELSEIF interval_type = 'MONTH' THEN 
		SET @target_table = '`metrics`.`expressions_computed_monthly`';	
	ELSEIF interval_type = 'QUARTER' THEN
		SET @target_table = '`metrics`.`expressions_computed_quarterly`';	
	ELSEIF interval_type = 'YEAR' THEN
		SET @target_table = '`metrics`.`expressions_computed_yearly`';	
	END IF;
	
	INSERT INTO `metrics`.`sp_logging`(TIMESTAMP, `log`) VALUES(now(), concat('executing `PROC_COMPUTE_EXPRESSIONS` for date [', date_from,' to ', date_to, ']'));
		
	-- delete existing rows first before insert new ones
	SET @query = CONCAT('DELETE FROM ', @target_table, ' WHERE range_key = ', quote(range_key));

	PREPARE delete_statement FROM @query;
							
	EXECUTE delete_statement;

	DEALLOCATE PREPARE delete_statement;				           

	
	OPEN cursor_countries;

        country_loop: LOOP
        
            /* otherwise compute data for given country */
            FETCH cursor_countries INTO iso_code;
       
			
            /* check if reached end of list */
            IF finished = 1 THEN 
                LEAVE country_loop; 
            END IF;
            
	   		INSERT INTO `metrics`.`sp_logging`(TIMESTAMP, `log`) VALUES(now(), concat('computing EXPRESSIONS for [ ', iso_code, ']'));			
	   		
	   		SET @iso_code = iso_code;
	   		SET @query = CONCAT('INSERT INTO ', @target_table, 
					            ' SELECT ', quote(date_to) , ' AS DATE,' , quote(range_key) , ' AS range_key, a.country, a.`short_id`, a.raw_de, a.peak_raw_de, a.real_de, a.peak_real_de, a.rank_by_avg, p.rank_by_peak
					            FROM ( SELECT   country, `short_id`, raw_de, peak_raw_de, real_de, peak_real_de, rank AS rank_by_avg
					                        FROM (SELECT country, `short_id`, raw_de, peak_raw_de, real_de, peak_real_de, 
					                        @prev := @curr, @curr := real_de, @rank := IF(@prev = @curr, @rank, @rank+1) AS rank
					                        FROM  (SELECT country, `short_id`, AVG(raw_de) AS raw_de, MAX(raw_de) AS peak_raw_de, AVG(real_de) AS real_de, MAX(`real_de`) AS peak_real_de
					                        FROM `metrics`.`expressions_daily` e
					                        WHERE DATE IN (SELECT `datestr` FROM `date_lookup_prod` WHERE datestr >= ',quote(date_from),' AND datestr <= ',quote(date_to) ,') 
					                        AND `short_id` IN (SELECT `subscriptionRefId` FROM `subscription`.`subscriptions` WHERE `idAccount` = ', customer_ready_account , ' AND `subscriptionType` = \'tvtitle\') 
					                        AND country  = ' ,quote(iso_code), ' GROUP BY `short_id`, country 
					                        ORDER BY AVG(raw_de) DESC) overall, (SELECT @curr := NULL, @prev := NULL, @rank := 0) r ) od
					                        ORDER BY rank_by_avg * 1 ) a
					            JOIN (SELECT country, `short_id`, raw_de, peak_raw_de, real_de, peak_real_de, rank AS rank_by_peak
					                        FROM (SELECT country, `short_id`, raw_de, peak_raw_de, real_de, peak_real_de, 
					                        @prev := @curr, @curr := real_de, @prank := IF(@prev = @curr, @prank, @prank+1) AS rank
					                        FROM  (SELECT country, `short_id`, AVG(raw_de) AS raw_de, MAX(raw_de) AS peak_raw_de, AVG(real_de) AS real_de, MAX(`real_de`) AS peak_real_de
					                        FROM `metrics`.`expressions_daily` e
					                        WHERE DATE IN (SELECT `datestr` FROM `date_lookup_prod` WHERE datestr >= ',quote(date_from),' AND datestr <= ',quote(date_to) ,') 
					                        AND `short_id` IN (SELECT `subscriptionRefId` FROM `subscription`.`subscriptions` WHERE `idAccount` = ', customer_ready_account , ' AND `subscriptionType` = \'tvtitle\') 
					                        AND country  = ' , quote(iso_code), ' GROUP BY `short_id`, country 
					                        ORDER BY MAX(raw_de) DESC) overall, (SELECT @curr := NULL, @prev := NULL, @prank := 0) r ) od
					                        ORDER BY rank * 1) p
					            ON a.`country` = p.`country` AND  a.`short_id` = p.`short_id`
					            ORDER BY rank_by_avg * 1');
				           
					           
			PREPARE insert_statement FROM @query;
							
			EXECUTE insert_statement;
					
			DEALLOCATE PREPARE insert_statement;				           
	   		
        END LOOP; 
        
	CLOSE cursor_countries;
	
    /*INSERT INTO `metrics`.`expressions_computed`
    SELECT date_to AS DATE, range_key AS range_key, country, `short_id`, AVG(raw_de) AS raw_de, MAX(raw_de) AS peak_raw_de, AVG(real_de) AS real_de, MAX(`real_de`) AS peak_real_de, 0 AS rank_by_avg, 0 AS rank_by_peak
    FROM `metrics`.`expressions_daily` e
    WHERE DATE IN (SELECT `datestr` FROM `date_lookup_prod` WHERE datestr >= date_from AND datestr <= date_to) 
    AND `short_id` NOT IN (SELECT `subscriptionRefId` FROM `subscription`.`subscriptions` WHERE `idAccount` = customer_ready_account AND `subscriptionType` = 'tvtitle') 
    GROUP BY `short_id`, country 
    ORDER BY country, AVG(raw_de) DESC;*/
	
END $$
DELIMITER ;