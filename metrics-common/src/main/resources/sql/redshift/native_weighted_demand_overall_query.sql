
SELECT
	date,
	short_id,
	':countries:' AS country,
	e.raw_de AS dexpercapita,
	e.peak_raw_de AS peak_dexpercapita ,
	e.real_de AS dex,
	e.peak_real_de AS peak_dex,
	RANK() OVER (ORDER BY e.raw_de DESC ) AS rank_by_avg,
	RANK() OVER (ORDER BY e.peak_raw_de DESC ) AS rank_by_peak
FROM
	(
	SELECT
		a.short_id, SUM(a.raw_de * p.population) / sum(p.population) AS dexpercapita, SUM(a.peak_raw_de * p.population) / sum(p.population) AS peak_dexpercapita, SUM(a.real_de * p.population) / sum(p.population) AS dex, SUM(a.peak_real_de * p.population) / sum(p.population) AS peak_dex
	FROM
		(
		SELECT
			short_id, country, AVG(raw_de) AS raw_de, MAX(raw_de) as peak_raw_de, AVG(real_de) as real_de, MAX(real_de) as peak_real_de
		FROM
			datastores.expressions_daily
		WHERE
			short_id IN #sub_short_ids
			AND country IN #markets
			AND date IN #date_ranges
		GROUP BY
			short_id, country) a
	JOIN (
		SELECT
			iso, population
		FROM
			managed.countries) p ON
		p.iso = a.country
	GROUP BY
		a.short_id) e
WHERE
	e.short_id IN #short_ids
ORDER BY
	:sort_by: :direction:
LIMIT :size:;

