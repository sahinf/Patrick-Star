import psycopg2

# 315_psql='psql -h csce-315-db.engr.tamu.edu -U csce315_913_3_user -d csce315_913_3_db'

conn = psycopg2.connect(
    host="localhost",
    database="suppliers",
    user="postgres",
    password="Abcd1234"
)

# import numpy as np
# a = np.arange(15).reshape(3, 5)s
# print(a)

print("\n\ntesting\n")