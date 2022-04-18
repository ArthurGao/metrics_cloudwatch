SELECT ':date:' AS date,
	   a.short_id, 
       a.country, 
       ( a.de_social - b.de_social ) / a.de_social * 100       AS social, 
       ( a.de_video - b.de_video ) / a.de_video * 100          AS video, 
       ( a.de_research - b.de_research ) / a.de_research * 100 AS research, 
       ( a.de_piracy - b.de_piracy ) / a.de_piracy * 100       AS piracy 
FROM   (SELECT short_id, 
               country, 
               Avg(de_social)   AS de_social, 
               Avg(de_video)    AS de_video, 
               Avg(de_research) AS de_research, 
               Avg(de_piracy)   AS de_piracy 
        FROM   metrics.breakdown_daily_four_bucket 
        WHERE  date IN #this_date_range 
               AND short_id = #short_id 
               AND country IN #countries 
        GROUP  BY short_id, 
                  country) AS a 
       JOIN (SELECT short_id, 
                    country, 
                    Avg(de_social)   AS de_social, 
                    Avg(de_video)    AS de_video, 
                    Avg(de_research) AS de_research, 
                    Avg(de_piracy)   AS de_piracy 
             FROM   metrics.`breakdown_daily_four_bucket` 
             WHERE  date IN #last_date_range 
                    AND short_id = #short_id 
                    AND country IN #countries 
             GROUP  BY short_id, 
                       country) AS b 
         ON a.short_id = b.short_id 
            AND a.country = b.country 
ORDER  BY :order_by_field: :direction:
LIMIT :page:, :size: ; 