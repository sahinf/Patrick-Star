from os import fsdecode, read
import psycopg2
# import csv
import pandas as pd
# from sqlalchemy import create_engine

# 315_psql='psql -h csce-315-db.engr.tamu.edu -U csce315_913_3_user -d csce315_913_3_db'
_host = "csce-315-db.engr.tamu.edu"
_db = "csce315_913_3_db"
_user = "csce315_913_3_user"
_pass = "sikewrongnumber"
conn = psycopg2.connect(
    host=_host,
    database=_db,
    user=_user,
    password=_pass
)


# engine = create_engine(r'postgresql://some:user@host/db')


def cleanup(csv_file, dir='data/'):
    data = pd.read_csv(dir + csv_file + '.csv', sep='\t')
    del data['Unnamed: 0']
    # print(f"Cleaned up {csv_file}.csv:")
    # print(data)
    data.to_csv('clean_data/'+csv_file+'.csv', encoding='utf-8', index=False)

def cleanupAll():
    cleanup('crew')
    cleanup('customer_ratings')
    cleanup('names')
    cleanup('principals')
    cleanup('titles')

def createCrew(csv):
    cur = conn.cursor()
    # print(csv.to_sql(name=))
    # cur.execute('''
    #     CREATE TABLE IF NOT EXISTS titles(
    #         titleId TEXT,
    #         directors TEXT,
    #         writers TEXT
    #     );
    #     \copy titles from \'crew.csv
    # ''')

# Remove the first garbage columns from csv and then use sep=','
# cleanupAll()


cur = conn.cursor()
# cur.execute("SELECT * FROM crew LIMIT 10")
# query_results = cur.fetchall()
# print(query_results)
