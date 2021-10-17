-- select titleid,count(*) as Total 
-- from ratings 
-- group by titleid 
-- order by Total desc
-- limit 10;

-- get top 10 movies from all users, can filter by date
-- select ratings.titleid,titles.originaltitle,count(ratings.titleid) as total
-- from ratings 
-- left join titles on ratings.titleid = titles.titleid
-- where ratings.date > '2005-09-06' and ratings.date <= '2005-09-08'
-- group by ratings.titleid,titles.originaltitle
-- order by total desc
-- limit 10;

-- select ratings.titleid,titles.originaltitle,count(ratings.titleid) as total
-- from ratings 
-- left join titles on ratings.titleid = titles.titleid
-- group by ratings.titleid,titles.originaltitle
-- order by total desc
-- limit 10;

-- get top 10 movies from one users, can filter by date
-- select ratings.titleid,titles.originaltitle,count(ratings.titleid) as total
-- from ratings 
-- left join titles on ratings.titleid = titles.titleid
-- where ratings.date > '1999-01-01' and ratings.date <= '2021-01-01' and ratings.customerid = '1488844'
-- group by ratings.titleid,titles.originaltitle
-- order by total desc
-- limit 10;

-- select titleid,count(*) as Total 
-- from ratings 
-- group by titleid 
-- order by Total desc
-- limit 10;

--select count(*) from ratings

with stars(titleid,customerid,rating) AS
     (
         SELECT titleid,customerid,rating
         FROM ratings
         WHERE ratings.rating = '4.0' or ratings.rating = '5.0'
         limit 100
     ),
     comovies(titleid1, titleid2,customerid) AS
     ( 
       SELECT stars1.titleid, stars2.titleid,stars1.customerid
       FROM stars AS stars1
        left JOIN stars AS stars2
       ON stars1.customerid = stars2.customerid and stars1.titleid != stars2.titleid
     )
    select count(*) from comovies;

-- with comovies(titleid1, titleid2) AS
--      ( 
--        SELECT ratings1.titleid, ratings2.titleid
--        FROM ratings AS ratings1
--        inner JOIN ratings AS ratings2
--        ON ratings1.customerid = ratings2.customerid
--      )
--     select count(*) from comovies;

-- join in recursive method
-- WITH RECURSIVE
--      stars(titleid,customerid) AS
--      (
--         SELECT titleid,customerid,rating
--          FROM ratings
--          WHERE ratings.rating = '4.0' or ratings.rating = '5.0'
--          limit 1000
--      ),
--      comovies(titleid1, titleid2,customerid) AS
--      ( 
--        SELECT stars1.titleid, stars2.titleid, stars1.customerid
--        FROM stars AS stars1
--        left JOIN stars AS stars2
--        ON stars1.customerid = stars2.customerid and stars1.titleid != stars2.titleid
--      ),
--      bacon(titleid, num) AS
--      (
--        VALUES('tt0389605', 0)
--     --    select 'tt0389605' as titleid, 0 as num
--        UNION ALL
--        SELECT titleid2, num+1
--        FROM bacon JOIN comovies
--        ON bacon.titleid = comovies.titleid1
--        WHERE num < 3
--      )
--      SELECT bacon.titleid, MIN(num)
--      FROM bacon 
--      GROUP BY bacon.titleid,num
--      ORDER BY num

--non join in recursive method:
-- WITH RECURSIVE
--      stars(titleid,customerid) AS
--      (
--         SELECT titleid,customerid,rating
--          FROM ratings
--          WHERE ratings.rating = '4.0' or ratings.rating = '5.0'
--          limit 100
--      ),
--      comovies(titleid1, titleid2,customerid) AS
--      ( 
--        SELECT stars1.titleid, stars2.titleid, stars1.customerid
--        FROM stars AS stars1
--        left JOIN stars AS stars2
--        ON stars1.customerid = stars2.customerid and stars1.titleid != stars2.titleid
--      ),
--      bacon(titleid, num,route) AS
--      (
--     --    VALUES('tt0389605', 0)
--         select 'tt0389605' as titleid, 0 as num, titleid2 || ' --(' || customerid ||  ')--> ' || 'tt0389605' AS route
--         from comovies
--        UNION ALL
--        SELECT titleid2, num+1, titleid2 || ' --(' || customerid ||  ')--> ' || titleid || E'\n'|| route
--        from bacon, comovies
--        WHERE bacon.titleid = comovies.titleid1
--        AND num < 3
--      )
--      SELECT bacon.titleid, MIN(num),route
--      FROM bacon 
--      GROUP BY bacon.titleid,num,route
--      ORDER BY num


WITH RECURSIVE 
stars(titleid,customerid) AS
     (
        SELECT titleid,customerid,rating
         FROM ratings
         WHERE ratings.rating = '4.0' or ratings.rating = '5.0'
         limit 100
     ),
     comovies(titleid1, titleid2,customerid) AS
     ( 
       SELECT stars1.titleid, stars2.titleid, stars1.customerid
       FROM stars AS stars1
       left JOIN stars AS stars2
       ON stars1.customerid = stars2.customerid where stars1.titleid != stars2.titleid
     ),
    kevinbacon AS(
    SELECT
    f.titleid1,
    f.titleid2,
    0 AS bacon_no,
    titleid2 || ' --(' || customerid || ')--> ' || titleid1 AS route
    FROM comovies AS f
    WHERE titleid1 = 'tt0389605'
    UNION ALL
    SELECT
    p.titleid1,
    f.titleid2,
    p.bacon_no + 1,
    f.titleid2 || ' --(' || customerid  || ')--> ' || f.titleid1 ||E'\n'|| p.route
    FROM comovies AS f, kevinbacon AS p
    WHERE p.titleid2 = f.titleid1 
    AND p.bacon_no < 3
    )
    SELECT 
    titleid2 AS destinationTitle, 
    titleid1 AS connected_to, 
    bacon_no, 
    route AS Connection
    FROM kevinbacon

