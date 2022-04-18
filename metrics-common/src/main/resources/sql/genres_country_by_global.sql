SELECT 	date, 
		country_avg.country,
		country_avg.gene,
       	country_avg.genevalue,
       	round(country_avg.avg_raw_de, 2) AS country_avg,
       	round(global_avg.avg_raw_de, 2) AS global_avg,
       	round((country_avg.avg_raw_de - global_avg.avg_raw_de) / global_avg.avg_raw_de * 100, 2) AS 'vs_percent'
FROM (SELECT MAX(e1.date) AS DATE, c1.genevalue, AVG(e1.raw_de) AS avg_raw_de
      FROM :metrics_schema:.:table_name: AS e1 INNER JOIN portal.catalog_genes c1 ON e1.`short_id` = c1.`short_id`
      WHERE e1.DATE = #date
      AND   e1.country IN ('AL','DZ','AR','AU','AT','BH','BD','BE','BA','BR','BN','BG','CA','CL','CN','CO','HR','CY','CZ','DK','EC','EG','EE','FI','FR','DE','GH','GR','HK','HU','IS','IN','ID','IR','IQ','IE','IL','IT','JM','JP','JO','KE','KR','KW','LV','LB','LT','LU','MK','MY','MT','MX','MA','NL','NZ','NG','NO','OM','PK','PA','PY','PE','PH','PL','PT','PR','QA','RO','RU','SA','CS','SG','SK','SI','ZA','ES','LK','SE','CH','TW','TH','TT','TN','TR','UA','AE','GB','US','UY','VE','VN','RS')
      AND   c1.gene = #gene
      GROUP BY c1.genevalue
      ) AS global_avg
  INNER JOIN 
  	(SELECT e.`country`, c.`gene`, c.`genevalue`, AVG(e.`raw_de`) AS avg_raw_de
              FROM :metrics_schema:.:table_name: e INNER JOIN `portal`.`catalog_genes` c ON e.`short_id` = c.`short_id`
              WHERE e.DATE = #date
              AND   e.country = #iso_code
              AND   c.gene = #gene
              GROUP BY e.country, c.genevalue LIMIT 0, 5
      ) AS country_avg 
ON country_avg.`genevalue` = global_avg.`genevalue`