SELECT 
	key,
	'(' || key || ')-' || name as name,
	component,
	parentkey
FROM MENU
where isalive = 'Y'
order by sort