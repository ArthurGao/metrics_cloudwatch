SELECT
  e.date AS date,
  e.short_id,
  ':countries:' AS country,
  SUM(e.raw_de * p.population) / sum(p.population) AS dexpercapita,
  SUM(e.peak_raw_de * p.population)/ sum(p.population) AS peak_dexpercapita,
  SUM(e.real_de * p.population) / sum(p.population) AS dex,
  SUM(e.peak_real_de * p.population) /sum(p.population) AS peak_dex,
  e.overall_rank as overall_rank,
  e.rank_by_peak as rank_by_peak,
  e.best_rank as best_rank,
  MIN(e.num_days) as num_days
FROM
  (
    SELECT
      date,
      short_id,
      country,
      :raw_de: AS raw_de,
      :peak_raw_de: as peak_raw_de,
      :real_de: as real_de,
      :peak_real_de: as peak_real_de,
      :overall_rank: as overall_rank,
      :rank_by_peak: as rank_by_peak,
      :best_rank: as best_rank,
      :num_days: as num_days
    FROM
      :table_name:
    WHERE
      short_id IN #content_ids
      AND country IN #iso_code
      AND date IN #date_ranges
    GROUP BY
      short_id,
      country,
      date
  ) e
  JOIN (
    SELECT
      iso,
      population
    FROM
      subscription.countries
  ) p ON p.iso COLLATE utf8_unicode_ci = e.country
GROUP BY
  e.short_id,
  e.date
ORDER BY
  :order_by_field: :direction:
