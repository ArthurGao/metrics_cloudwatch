DELIMITER ;;
CREATE DEFINER=`minh`@`%` PROCEDURE `SP_BACKFILL_COMPUTED_DATASETS`(
IN date_from DATE,
IN date_to DATE,
IN selected_interval_type VARCHAR (20)
)
BEGIN
	DECLARE enabled BOOLEAN DEFAULT TRUE;
	DECLARE finished INT DEFAULT 0;

	DECLARE interval_type VARCHAR (100) DEFAULT "";
	DECLARE range_key VARCHAR (100) DEFAULT "";
	DECLARE start_date DATE;
	DECLARE end_date DATE;
	DECLARE precomputed INT DEFAULT 0;		
	
	DECLARE customer_ready_account INT DEFAULT 99;
	
	DECLARE cursor_ranges CURSOR FOR SELECT * FROM `range_key_lookup` WHERE START >= date_from AND END <= date_to;	
		
	DECLARE CONTINUE HANDLER FOR NOT FOUND SET finished = 1;
	
	INSERT INTO `metrics`.`sp_logging`(TIMESTAMP, `log`) VALUES(now(), concat('executing `PROC_COMPUTE_EXPRESSIONS` for date [', date_from,' to ', date_to, ']'));
		
	OPEN cursor_ranges;

        range_loop: LOOP
        
            /* otherwise compute data for given country */
            FETCH cursor_ranges INTO interval_type, range_key, start_date, end_date, precomputed;
       
			
            /* check if reached end of list */
            IF finished = 1 THEN 
                LEAVE range_loop; 
            END IF;
            /* continue to next iteration if selected_interval_type is set and NOT match the current interval */
            IF (selected_interval_type IS NULL) OR ((selected_interval_type IS NOT NULL) AND (selected_interval_type = interval_type)) THEN
            	
  				UPDATE `range_key_lookup` SET precomputed=0 WHERE start = start_date AND end = end_date AND range_key=range_key;
            	
            	INSERT INTO `metrics`.`sp_logging`(TIMESTAMP, `log`) VALUES(now(), concat('Got Row : {', interval_type , '::', range_key, ' } --> [', start_date,' TO ', end_date, ']')); 
            
            	IF interval_type = 'WEEK' THEN
            		CALL `metrics`.`SP_COMPUTE_EXPRESSION_BY_WEEK`(start_date, end_date, range_key);
				ELSEIF interval_type = 'MONTH' THEN
            		CALL `metrics`.`SP_COMPUTE_EXPRESSION_BY_MONTH`(start_date, end_date, range_key);
				ELSEIF interval_type = 'QUARTER' THEN
            		CALL `metrics`.`SP_COMPUTE_EXPRESSION_BY_QUARTER`(start_date, end_date, range_key);
            	ELSEIF interval_type = 'YEAR' THEN
            		CALL `metrics`.`SP_COMPUTE_EXPRESSION_BY_YEAR`(start_date, end_date, range_key);	
				ELSE
					INSERT INTO `metrics`.`sp_logging`(TIMESTAMP, `log`) VALUES(now(), concat('wrong INTERVAL TYPE FOR computing expressions : ', interval_type ,'[', start_date,' TO ', end_date, ']'));  
				END IF;
            	
   				UPDATE `range_key_lookup` SET precomputed=1 WHERE start = start_date AND end = end_date AND range_key=range_key;

            END IF;

		
		END LOOP;
		
	CLOSE cursor_ranges;
	
END;;
DELIMITER ;