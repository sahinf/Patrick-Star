-- WITH tab1(customerid,num) as
-- (
-- select customerid, count(*) from ratings
-- group by customerid
-- )
-- select count(*) from tab1;

--  SELECT COUNT(DISTINCT customerid) AS uniques FROM ratings;

--   SELECT DISTINCT customerid AS uniques FROM ratings;

-- select count(*) from ratings where customerid = '826696';

-- WITH 
-- stars(titleid,customerid) AS
-- (
-- SELECT titleid,customerid,rating
--     FROM ratings
--     WHERE ratings.rating = '4.0' or ratings.rating = '5.0'
-- ),
-- tab1(titleid,num) as
-- (
-- select titleid, count(*) from stars
-- group by titleid
-- )
-- select count(*) from tab1;

-- create table if not exists ratings4plus(
--     customerid int,
--     rating text,
--     date text,
--     titleid text
-- )

-- INSERT INTO ratings4plus (
--     SELECT
--         cast(customerid as int),
--         rating,
--         date,
--         titleid
--     FROM ratings
--     WHERE rating = '4.0' or rating = '5.0'
-- );

-- WITH 
-- tab1(customerid,num) as
-- (
-- select customerid, count(*) as num from ratings4plus
-- group by customerid
-- order by num desc
-- )
-- select count(*) from tab1;

-- select count(*) from ratings4plus where titleid = 'tt14627576';

-- WITH tab1(titleid,num) as (select titleid, count(*) as num from ratings4plus group by titleid order by num desc) select * from tab1;

with 
moviesWorkedIn as(
select titleid,nconst from principals as p where nconst = 'nm0000003'
),
-- select * from moviesWorkedIn;
costars as(
    select p.nconst, c.titleid from moviesWorkedIn as c
    left join principals as p on  c.titleid = p.titleid
    where c.nconst != p.nconst
),
-- select count(*) from costars 
costarsDirectors as(
    select p.titleid, c.nconst, p.nconst as director from costars as c
    left join principals as p on c.nconst = p.nconst
    where p.category = 'director'
),
starDirectors as (
    select p.nconst as director, c.titleid from moviesWorkedIn as c
    left join principals as p on  c.titleid = p.titleid
    where c.nconst != p.nconst and  p.category = 'director'

),
costarsDirectorsOrdered as (
    select director, count(*) as num from costarsDirectors
    group by director
    order by num desc
    
),
starDirectorsOrdered as (
    select director, count(*) as num from starDirectors
    group by director
    order by num desc
),
costarsDirectorsOnly as (
select director from costarsDirectorsOrdered
except
select director from starDirectorsOrdered
)
 select costarsDirectorsOnly.director,costarsDirectorsOrdered.num
 from costarsDirectorsOnly left join costarsDirectorsOrdered on costarsDirectorsOnly.director = costarsDirectorsOrdered.director
 order by num desc;
 --limit 1;
--  select count(*) from cte4;



-- cte4 as (
--     select p.nconst, c.titleid from cte1 as c
--     inner join principals as p on c.titleid = p.titleid
--     where p.category = 'director'
-- )
-- SELECT count( distinct nconst) FROM cte4

with 
cte1 as(
select titleid,nconst from principals as p where nconst = 'nm0000001'
),
-- select * from cte1;
cte2 as(
    select p.nconst as director, c.titleid from cte1 as c
    left join principals as p on  c.titleid = p.titleid
    where c.nconst != p.nconst and  p.category = 'director'
)
 select count( distinct director ) from cte2 
-- cte3 as(
--     select p.titleid, c.nconst, p.nconst as director from cte2 as c
--     left join principals as p on c.nconst = p.nconst
--     where p.category = 'director'
-- )
-- select count(distinct director) from cte3;


