 SELECT   date, 
           '-1' AS short_id, 
           country, 
           sum(raw_de)  AS dexpercapita, 
           sum(raw_de)  AS peak_dexpercapita, 
           sum(real_de) AS dex, 
           sum(real_de) AS peak_dex, 
           0            AS overall_rank, 
           0            AS rank_by_peak,
           0            AS best_rank 
  FROM     :table_name: 
  WHERE    date IN #date_ranges 
  AND      overall_rank != 0 
  AND      short_id  IN #others_short_ids 
  AND      country      IN #markets 
  GROUP BY date, country
