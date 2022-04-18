DELIMITER ;;
CREATE DEFINER=`minh`@`%` PROCEDURE `DAILY_DATA_PRE_COMPUTATION`()
BEGIN

    SET @date_to = (SELECT max(DATE) FROM `metrics`.`expressions_daily`);
    SET @cur_date = (SELECT max(DATE) FROM `metrics`.`expressions_computed`);

    INSERT INTO `metrics`.event_history_log (job, EVENT) VALUES ('data-pre-computation', 'START');

    -- Reset Tables;
    TRUNCATE TABLE `metrics`.`sp_logging`;

    -- ################################################### COMPUTE EXPRESSIONS ###################################################
    -- latest date
    CALL `metrics`.`SP_COMPUTE_EXPRESSION_RANGES`(@date_to, @date_to, 'latest','day', '_staging');

    -- last 7 days
    CALL `metrics`.`SP_COMPUTE_EXPRESSION_RANGES`(DATE_SUB(@date_to, INTERVAL 6 DAY), @date_to, 'last7days','day', '_staging');

    -- last 30 days
    CALL `metrics`.`SP_COMPUTE_EXPRESSION_RANGES`(DATE_SUB(@date_to, INTERVAL 29 DAY), @date_to, 'last30days','day', '_staging');

    -- this month
    CALL `metrics`.`SP_COMPUTE_EXPRESSION_RANGES`(DATE_FORMAT( @date_to, '%Y/%m/01'), @date_to, 'thismonth','day', '_staging');

    -- last month
    CALL `metrics`.`SP_COMPUTE_EXPRESSION_RANGES`(DATE_FORMAT( @date_to - INTERVAL 1 MONTH, '%Y/%m/01'), DATE_FORMAT( @date_to, '%Y/%m/01' ) - INTERVAL 1 DAY, 'lastmonth','day', '_staging');

    -- last 60 days
    CALL `metrics`.`SP_COMPUTE_EXPRESSION_RANGES`(DATE_SUB(@date_to, INTERVAL 59 DAY), @date_to, 'last60days','day', '_staging');

    -- last 90 days
    CALL `metrics`.`SP_COMPUTE_EXPRESSION_RANGES`(DATE_SUB(@date_to, INTERVAL 89 DAY), @date_to, 'last90days','day', '_staging');

    TRUNCATE TABLE `metrics`.`expressions_computed`;

    INSERT INTO `metrics`.`expressions_computed`
    	SELECT * FROM `metrics`.`expressions_computed_staging`;

    TRUNCATE TABLE `metrics`.`weighted_expressions_computed`;

    --  latest 24 hour weighted average
    CALL `metrics`.`SP_COMPUTE_WEIGHTED_EXPRESSION_RANGES`(@date_to, @date_to,@countries, 'global', 'latest','day', true);

     -- last 7 days weighted average
    CALL `metrics`.`SP_COMPUTE_WEIGHTED_EXPRESSION_RANGES`(DATE_SUB(@date_to, INTERVAL 6 DAY), @date_to, @countries, 'global', 'last7days','day', true);

    -- last 30 days weighted average
    CALL `metrics`.`SP_COMPUTE_WEIGHTED_EXPRESSION_RANGES`(DATE_SUB(@date_to, INTERVAL 29 DAY), @date_to, @countries, 'global', 'last30days','day', true);

    -- this month weighted average
    CALL `metrics`.`SP_COMPUTE_WEIGHTED_EXPRESSION_RANGES`(DATE_FORMAT( @date_to, '%Y/%m/01'), @date_to, @countries, 'global', 'thismonth','day', true);

    -- last month weighted average
    CALL `metrics`.`SP_COMPUTE_WEIGHTED_EXPRESSION_RANGES`(DATE_FORMAT( @date_to - INTERVAL 1 MONTH, '%Y/%m/01'), DATE_FORMAT( @date_to, '%Y/%m/01' ) - INTERVAL 1 DAY,@countries, 'global','lastmonth','day', true);

     -- last 60 days weighted average
    CALL `metrics`.`SP_COMPUTE_WEIGHTED_EXPRESSION_RANGES`(DATE_SUB(@date_to, INTERVAL 59 DAY), @date_to,@countries, 'global', 'last60days','day', true);

    -- last 90 days weighted average
    CALL `metrics`.`SP_COMPUTE_WEIGHTED_EXPRESSION_RANGES`(DATE_SUB(@date_to, INTERVAL 89 DAY), @date_to, @countries, 'global', 'last90days','day', true);

    -- ################################################### BENCHMARK DEMAND ###################################################

    INSERT INTO `metrics`.`benchmark_demand`
	SELECT DATE, AVG(raw_de)
	FROM `metrics`.`expressions_daily`
	WHERE DATE >= @date_to AND `country` = 'US' and short_id IN (SELECT short_id from portal.series_metadata WHERE content_type = 'Show')
	GROUP BY DATE;
END;;
DELIMITER ;