from os import curdir
from connect import *
import pandas as pd

######################################## 
#          SANITIZE CSV 
########################################

# Uncomment to connect to database
# conn = hackIntoMainframe()

# Removes first instance of column with empty name to make it look cleaner
def cleanup(csv_file, dir='data/'):
    # remove duplicate entries and 'Year' entry from 'title'
    if (csv_file == 'titles'):
        data = pd.read_csv(dir + csv_file + '.csv', sep='\t')
        del data['Unnamed: 0']
        del data['Year']
        print(data)
        data.drop_duplicates(keep="first", inplace=True)
        data.to_csv('clean_data/'+ csv_file+'.csv', index=False, sep='\t')
    else: 
        data = pd.read_csv(dir + csv_file + '.csv', sep='\t')
        del data['Unnamed: 0']
        data.drop_duplicates(keep="first", inplace=True)
        data.to_csv('clean_data/'+csv_file+'.csv', index=False, sep='\t')

# To sanitize the 5 provided csv files. they suck.
def cleanupAll():
    cleanup('crew')
    cleanup('customer_ratings')
    cleanup('names')
    cleanup('principals')
    cleanup('titles')


######################################## 
#          CREATE TABLES 
########################################

# Add "crew" table to database
def createCrew():
    cur = conn.cursor()
    cur.execute('''
        CREATE TABLE IF NOT EXISTS crew(
            titleId TEXT PRIMARY KEY,
            directors TEXT,
            writers TEXT ); ''')
    conn.commit()

# Add "ratings" table to database
def createCustomerRatings():
    cur = conn.cursor()
    cur.execute('''
        CREATE TABLE IF NOT EXISTS ratings(
            customerId TEXT,
            rating TEXT,
            date TEXT,
            titleId TEXT);''')
    conn.commit()

# Add "names" table to database
def createNames():
    cur = conn.cursor()
    cur.execute('''
        CREATE TABLE IF NOT EXISTS names(
            nconst TEXT PRIMARY KEY,
            primaryName TEXT,
            birthyear TEXT,
            deathYear TEXT,
            primaryProfession TEXT );''')
    conn.commit()

# Add "principals" table to database
def createPrincipals():
    cur = conn.cursor()
    cur.execute('''
        CREATE TABLE IF NOT EXISTS principals(
            titleId TEXT,
            nconst TEXT,
            category TEXT,
            job TEXT,
            characters TEXT );''')
    conn.commit()

# Add "titles" table to database
def createTitles():
    cur = conn.cursor()
    cur.execute('''
        CREATE TABLE IF NOT EXISTS titles(
            titleId TEXT PRIMARY KEY,
            titleType TEXT,
            originalTitle TEXT,
            startYear TEXT,
            endYear TEXT,
            runtimeMinutes TEXT,
            genres TEXT,
            averageRating TEXT,
            numVotes TEXT );''')
    conn.commit()

def createAll():
    createCrew()
    createCustomerRatings()
    createNames()
    createPrincipals()
    createTitles()

######################################## 
#          COPY TO TABLES 
########################################
def copyTable(tableName):
    cur = conn.cursor()

    # table "ratings" csv is "cusotmer_ratings.csv"
    if (tableName == "ratings"):
        fileName = tableName
        # file = open('clean_data/customer_ratings.csv')
    else:
        fileName = tableName + ".csv"
        # file = open('clean_data/' + fileName + '.csv')
    
    # HARD command in order to do "CSV HEADER"
    command = sql.SQL("COPY {} FROM '{}' CSV HEADER;").format(sql.Identifier(fileName), fileName)
    # cur.copy_from(file, fileName, sep="\t")
    cur.copy_expert(command)
    conn.commit()

def copyAll():
    copyTable('crew')
    copyTable('ratings')
    copyTable('names')
    copyTable('principals')
    copyTable('titles')

######################################## 
#          DELETE TABLES 
########################################
# Delete provided table from database
def deleteTable(tablename=0):
    cur = conn.cursor()
    cur.execute(
        sql.SQL("DROP TABLE IF EXISTS {};")
        .format(sql.Identifier(tablename))
    )

# Clear database of all tables
def deleteAllTables():
    deleteTable("crew")
    deleteTable("ratings")
    deleteTable("names")
    deleteTable("principals")
    deleteTable("titles")

# conn.commit()
# conn.close()

def query(col, table, limit=10):
    cur = conn.cursor()
    psql_query = f'SELECT {col} FROM {table} LIMIT {limit}'
    print('\n')
    print(psql_query)
    cur.execute(psql_query)
    # cur.execute("SELECT %s FROM %s LIMIT 10", (col, table,))
    query_results = cur.fetchall()
    print(query_results)
    cur.close()

def query21():
    # 1
    query('titleId', 'crew')

    # 2
    query('directors', 'crew')

    # 3
    query('writers', 'crew')

    # 4
    query('customerId', 'ratings')

    # 5
    query('rating', 'ratings')

    # 7
    query('date', 'ratings')

    # 8
    query('titleId', 'ratings')

    # 9
    query('nconst', 'names')

    # 10
    query('primaryName', 'names')

    # 11
    query('birthYear', 'names')

    # 12
    query('deathYear', 'names')

    # 13
    query('primaryProfession', 'names')

    # 14
    query('titleId', 'principals')

    # 15
    query('nconst', 'principals')

    # 16
    query('category', 'principals')

    # 17
    query('characters', 'principals')

    # 18
    query('titleId', 'titles')

    # 19
    query('titleType', 'titles')

    # 20
    query('originalTitle', 'titles')

    # 21
    query('genres', 'titles')