SELECT
	date,
	short_id,
	country,
	e.raw_de as dexpercapita,
	peak_raw_de as peak_dexpercapita,
	real_de as dex,
	peak_real_de as peak_dex,
	rank_by_avg as overall_rank,
	rank_by_peak
FROM
	(
	SELECT
		date, country, short_id, raw_de, peak_raw_de, real_de, peak_real_de, RANK() OVER (ORDER BY raw_de DESC ) AS rank_by_avg, RANK() OVER (ORDER BY peak_raw_de DESC) AS rank_by_peak
	FROM
		(
		SELECT
			MAX(date) as date, country, short_id, AVG(raw_de) AS raw_de, MAX(raw_de) AS peak_raw_de, AVG(real_de) AS real_de, MAX(real_de) AS peak_real_de
		FROM
			datastores.expressions_daily ed
		WHERE
			short_id IN #sub_short_ids
			AND date IN #date_ranges
			AND country IN #markets
		GROUP BY
			short_id, country
		ORDER BY
			AVG(raw_de) DESC) overall
	ORDER BY
		RANK() OVER (ORDER BY raw_de DESC ) ) e
WHERE
	e.short_id IN #short_ids
ORDER BY
	:sort_by: :direction:
LIMIT :size:;