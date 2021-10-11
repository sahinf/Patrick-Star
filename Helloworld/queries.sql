-- select titleid,count(*) as Total 
-- from ratings 
-- group by titleid 
-- order by Total desc
-- limit 10;

-- get top 10 movies from all users, can filter by date
select ratings.titleid,titles.originaltitle,count(ratings.titleid) as total
from ratings 
left join titles on ratings.titleid = titles.titleid
where ratings.date > '2005-09-06' and ratings.date <= '2005-09-08'
group by ratings.titleid,titles.originaltitle
order by total desc
limit 10;

-- select ratings.titleid,titles.originaltitle,count(ratings.titleid) as total
-- from ratings 
-- left join titles on ratings.titleid = titles.titleid
-- group by ratings.titleid,titles.originaltitle
-- order by total desc
-- limit 10;

-- get top 10 movies from one users, can filter by date
select ratings.titleid,titles.originaltitle,count(ratings.titleid) as total
from ratings 
left join titles on ratings.titleid = titles.titleid
where ratings.date > '1999-01-01' and ratings.date <= '2021-01-01' and ratings.customerid = '1488844'
group by ratings.titleid,titles.originaltitle
order by total desc
limit 10;
