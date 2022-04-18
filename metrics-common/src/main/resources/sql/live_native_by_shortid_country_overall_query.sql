SELECT
	':date_to:' AS date,
	short_id,
	country,
	:raw_de: AS dexpercapita,
	:peak_raw_de: AS peak_dexpercapita,
	:real_de: AS dex,
	:peak_real_de: AS peak_dex,
	:overall_rank: AS overall_rank,
	:rank_by_peak: AS rank_by_peak,
	:best_rank: AS best_rank,
	:num_days: as num_days
FROM
	`metrics`.`:table_name:` e
WHERE
	DATE IN #date_ranges
	AND short_id IN #content_ids
	AND country IN #iso_code
	GROUP BY short_id,
	country
ORDER BY AVG(raw_de) DESC