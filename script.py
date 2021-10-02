from os import fsdecode, read
import psycopg2
from psycopg2 import sql
import pandas as pd

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

def createCrew(tablename=0, csvfile=0):
    cur = conn.cursor()
    cur.execute('''
        CREATE TABLE IF NOT EXISTS crew(
            titleId TEXT PRIMARY KEY,
            directors TEXT,
            writers TEXT ); ''')
    cur.execute('''\copy titles from 'clean_data\crew.csv' CSV HEADER''')

def createCustomerRatings():
    cur = conn.cursor()
    cur.execute('''
        CREATE TABLE IF NOT EXISTS ratings(
            customerId TEXT PRIMARY KEY,
            rating FLOAT,
            date TEXT,
            titleId TEXT );''')
    cur.execute('''\copy titles from 'clean_data\customer_ratings.csv' CSV HEADER''')

def createNames():
    cur = conn.cursor()
    cur.execute('''
        CREATE TABLE IF NOT EXISTS names(
            nconst TEXT PRIMARY KEY,
            primaryName TEXT,
            birthyear FLOAT,
            deathYear FLOAT,
            primaryProfession TEXT );''')
    cur.execute('''\copy titles from 'clean_data\customer_ratings.csv' CSV HEADER''')



# Remove the first garbage columns from csv and then use sep=','
# cleanupAll()

# Add crew table to database
createCrew('crew', 'crew.csv')

# cur = conn.cursor()
# cur.execute("SELECT * FROM crew LIMIT 10")
# query_results = cur.fetchall()
# print(query_results)
