(SELECT date, 
       short_id,
       country,
       raw_de as dexpercapita,
       peak_raw_de as peak_dexpercapita,
       real_de     AS dex, 
       peak_real_de     AS peak_dex,
       overall_rank, 
       rank_by_peak,
       best_rank 
FROM   (SELECT MAX(date) as date,
               country,
               short_id,
               :raw_de: as raw_de,
               :peak_raw_de: as peak_raw_de,
               :real_de: as real_de,
               :peak_real_de: as peak_real_de,
               :overall_rank: as overall_rank,
               :rank_by_peak: as rank_by_peak,
               :best_rank: as best_rank
        FROM   :table_name:
        WHERE  :date_condition:
               AND short_id IN #short_ids
               AND country IN #markets
        GROUP  BY short_id,
                  country) overall)
UNION 
(SELECT MAX(date) as date, 
        '-1'    AS short_id, 
        country, 
        SUM(raw_de)/:days_count: AS dexpercapita, 
        SUM(:peak_raw_de:)/:days_count: AS peak_dexpercapita, 
        SUM(real_de)/:days_count:    AS dex, 
        SUM(:peak_real_de:)/:days_count: AS peak_dex, 
        0           AS overall_rank, 
        0           AS rank_by_peak,
        0           AS best_rank 
 FROM   :table_name: 
 WHERE  :date_condition: 
        AND overall_rank != 0 
        AND short_id IN #others_short_ids 
        AND country IN #markets 
 GROUP  BY country) 
ORDER BY :sort_by: :direction: 
LIMIT    :page:, :size: