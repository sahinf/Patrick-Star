import psycopg2

# 315_psql='psql -h csce-315-db.engr.tamu.edu -U csce315_913_3_user -d csce315_913_3_db'
conn = psycopg2.connect(
    host="csce-315-db.engr.tamu.edu",
    database="csce315_913_3_db",
    user="csce315_913_3_user",
    password="sikewrongnumber"
)

cur = conn.cursor()
cur.execute("SELECT * FROM crew LIMIT 10")
query_results = cur.fetchall()
print(query_results)