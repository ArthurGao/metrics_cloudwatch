(SELECT b.date, 'social' AS bucket, b.country, b.short_id, b.`de_social` AS 'bucket_demand'
FROM metrics.`breakdown_daily_four_bucket` b
WHERE  b.date = #date AND b.country = #iso_code AND b.`short_id` IN (SELECT `subscriptionRefId` FROM `subscription`.`subscriptions` WHERE `idAccount` = #account_id AND `subscriptionType` = 'tvtitle')
ORDER BY bucket_demand DESC
LIMIT 0,#topn)

UNION ALL 

(SELECT b.date, 'video' AS bucket, b.country, b.short_id, b.`de_video` AS 'bucket_demand'
FROM metrics.breakdown_daily_four_bucket b
WHERE  b.date = #date AND b.country = #iso_code AND b.`short_id` IN (SELECT `subscriptionRefId` FROM `subscription`.`subscriptions` WHERE `idAccount` = #account_id AND `subscriptionType` = 'tvtitle')
ORDER BY bucket_demand DESC
LIMIT 0,#topn)

UNION ALL

(SELECT b.date, 'research' AS bucket, b.country, b.short_id, b.`de_research` AS 'bucket_demand'
FROM metrics.breakdown_daily_four_bucket b
WHERE  b.date = #date AND b.country = #iso_code AND b.`short_id` IN (SELECT `subscriptionRefId` FROM `subscription`.`subscriptions` WHERE `idAccount` = #account_id AND `subscriptionType` = 'tvtitle')
ORDER BY bucket_demand DESC
LIMIT 0,#topn)