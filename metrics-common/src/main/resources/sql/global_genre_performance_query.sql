SELECT
	Str_to_date(Concat(d.week, ' Sunday'), '%X%V %W') AS date,
	d.country,
	d.short_id,
	g.genre,
	d.avg_raw_de AS global_avg,
	g.avg_genre AS genre_avg
FROM
	(
	SELECT
		Yearweek(date, 1) AS week, country, short_id, AVG(raw_de) AS avg_raw_de
	FROM
		:metrics_schema:.:expressions_daily:
	WHERE
		date in #dateRangeList
		AND short_id IN #shortIDList
		AND country IN ('WW')
	GROUP BY
		short_id, Yearweek(date, 1), country
	ORDER BY
		Yearweek(date, 1) ASC) AS d
JOIN (
	SELECT
		yearweek(e.date, 1) AS week, country, e.short_id, AVG(e.raw_de) AS avg_genre, genevalue AS genre
	FROM
		:metrics_schema:.:expressions_daily: e
	JOIN portal.catalog_genes c ON
		e.short_id = c.short_id
		AND gene = 'genre_tags'
	WHERE
		e.short_id IN (
		SELECT
			short_id
		FROM
			portal.series_metadata
		WHERE
			catalog_state IN ('demand_generation', 'client_ready'))
		AND e.country = 'WW'
		AND e.date IN #dateRangeList
		AND genevalue IN #genres
	GROUP BY
		yearweek(e.date, 1), genevalue
	ORDER BY
		e.date ASC) AS g ON
	d.week = g.week
	AND d.country = d.country; 