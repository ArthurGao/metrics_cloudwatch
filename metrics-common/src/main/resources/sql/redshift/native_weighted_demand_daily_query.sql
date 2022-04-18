SELECT
	date,
	short_id,
	':countries:' as country,
	SUM(e.raw_de * e.population) / sum(e.population) AS dexpercapita,
	SUM(e.peak_raw_de * e.population) / sum(e.population) AS peak_dexpercapita
	SUM(e.real_de * e.population) / sum(e.population) AS dex,
	SUM(e.peak_real_de * e.population) / sum(e.population) AS peak_dex
	0 as overall_rank,
	0 as rank_by_peak
FROM
	(
	SELECT
		c.date, c.short_id, c.country, p.population, c.raw_de AS raw_de, c.raw_de AS peak_raw_de, c.real_de AS real_de, c.real_de AS peak_real_de
	FROM
		datastores.expressions_daily as c
	JOIN (
		SELECT
			iso, population
		FROM
			managed.countries ) p ON
		p.iso = c.country
	WHERE
		c.short_id IN #short_ids
		AND DATE IN #date_ranges
		and c.country IN #markets
	ORDER BY
		c.short_id, c.date ) as e
GROUP BY
	e.date,
	e.short_id
ORDER BY
	:sort_by: :direction:
LIMIT :size: