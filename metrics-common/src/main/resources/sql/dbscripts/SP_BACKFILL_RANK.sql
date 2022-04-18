-- Create syntax for 'SP_BACKFILL_RANK'

DELIMITER ;;
CREATE DEFINER=`minh`@`%` PROCEDURE `SP_BACKFILL_RANK`(
IN date_from DATE,
IN date_to DATE
)
BEGIN
	DECLARE enabled BOOLEAN DEFAULT TRUE;
	DECLARE finished INT DEFAULT 0;

	DECLARE rank_date DATE;
	
	DECLARE customer_ready_account INT DEFAULT 99;

	
	DECLARE cursor_ranges CURSOR FOR 
	SELECT `datestr` FROM `date_lookup_prod` WHERE datestr >= date_from AND datestr <= date_to;
	
	DECLARE CONTINUE HANDLER FOR NOT FOUND SET finished = 1;
			
	OPEN cursor_ranges;

        range_loop: LOOP
        
            /* otherwise compute data for given country */
            FETCH cursor_ranges INTO rank_date;
       
			
            /* check if reached end of list */
            IF finished = 1 THEN 
                LEAVE range_loop; 
            END IF;
            
            
            CALL `metrics`.`SP_BACKFILL_RANK_DATA`(rank_date);
			
		END LOOP;
		
	CLOSE cursor_ranges;
	
END;;
DELIMITER ;
