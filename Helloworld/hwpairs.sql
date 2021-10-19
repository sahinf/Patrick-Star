with
truncated as (
    select * from principals
    where category = 'actor' or category = 'actress'
    limit 100000
),
allpairs as (
    select c.nconst as name1, d.nconst as name2, cast(t.averagerating as decimal) as averagerating,t.titleid
    from truncated as c
    join truncated as d on c.titleid = d.titleid
    join titles as t on t.titleid = c.titleid
    where c.nconst != d.nconst
),
rankedpairs as (
    select least(name1,name2) as person1, greatest(name1,name2) as person2, avg(cast(averagerating as float)) as average, count(cast(averagerating as float)) as total, 
    sum(cast(averagerating as float)) as weighted
    from allpairs
    group by least(name1,name2),greatest(name1,name2)
    order by weighted desc
    
)
-- rankedpairs as (
--     select name1, name2, avg(cast(averagerating as float)) as average
--     from allpairs
--     group by name1, name2
--     order by average desc
-- )
-- rankedpairs as (
--     select name1, name2,averagerating, titleid
--     from allpairs
-- )
--  select sum(cast(averagerating as float)) from rankedpairs where greatest(name1,name2) = 'nm0005658' and least(name1,name2) = 'nm0000428';
select * from rankedpairs limit 10;