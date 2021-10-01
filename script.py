from os import fsdecode, read
import psycopg2
import csv
import pandas as pd


def cleanup(csv_file, dir='data/'):
    data = pd.read_csv(dir + csv_file + '.csv', sep='\t')
    del data['Unnamed: 0']
    # print(f"Cleaned up {csv_file}.csv:")
    # print(data)
    return data

def addTable(csv):
    print(csv)

# 315_psql='psql -h csce-315-db.engr.tamu.edu -U csce315_913_3_user -d csce315_913_3_db'
conn = psycopg2.connect(
    host="csce-315-db.engr.tamu.edu",
    database="csce315_913_3_db",
    user="csce315_913_3_user",
    password="sikewrongnumber"
)

addTable(cleanup('crew'))
cleanup('customer_ratings')
cleanup('names')
cleanup('principals')
cleanup('titles')

cur = conn.cursor()
# cur.execute("SELECT * FROM crew LIMIT 10")
# query_results = cur.fetchall()
# print(query_results)
