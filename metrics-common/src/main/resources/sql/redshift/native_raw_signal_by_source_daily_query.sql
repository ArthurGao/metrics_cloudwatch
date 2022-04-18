SELECT
	source_event_date as date,
	source,
	country,
	short_id,
	SUM(source_event_total) as source_event_total
FROM
	datastores.sit_prod_tv
WHERE
	:source_in_clause:
	AND country IN #markets
	AND short_id IN #short_ids
	AND source_event_date IN #date_ranges
GROUP BY
	source_event_date,
	source,
	country,
	short_id
ORDER BY
	:order_by_field: :direction:
LIMIT :size: