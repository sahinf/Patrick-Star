# from connect import *
import pandas as pd

######################################## 
#          SANITIZE CSV 
########################################

# Removes first instance of column with empty name to make it look cleaner
def cleanup(csv_file, dir='data/'):
    # remove duplicate entries and 'Year' entry from 'title'
    if (csv_file == 'titles'):
        data = pd.read_csv(dir + csv_file + '.csv', sep='\t')
        print(data)
        del data['Unnamed: 0']
        del data['Year']
        print(data)
        data.to_csv('clean_data/'+csv_file+'.csv', encoding='utf-8', index=False)
    else: 
        data = pd.read_csv(dir + csv_file + '.csv', sep='\t')
        del data['Unnamed: 0']
        data.to_csv('clean_data/'+csv_file+'.csv', encoding='utf-8', index=False)

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
    cur.execute('''\copy crew from 'clean_data\\crew.csv' CSV HEADER''')

# Add "ratings" table to database
def createCustomerRatings():
    cur = conn.cursor()
    cur.execute('''
        CREATE TABLE IF NOT EXISTS ratings(
            customerId TEXT PRIMARY KEY,
            rating FLOAT,
            date TEXT,
            titleId TEXT );''')
    cur.execute('''\copy ratings from 'clean_data\\customer_ratings.csv' CSV HEADER''')

# Add "names" table to database
def createNames():
    cur = conn.cursor()
    cur.execute('''
        CREATE TABLE IF NOT EXISTS names(
            nconst TEXT PRIMARY KEY,
            primaryName TEXT,
            birthyear FLOAT,
            deathYear FLOAT,
            primaryProfession TEXT );''')
    cur.execute('''\copy names from 'clean_data\\names.csv' CSV HEADER''')

# Add "principals" table to database
def createPrincipals():
    cur = conn.cursor()
    cur.execute('''
        CREATE TABLE IF NOT EXISTS principals(
            titleId TEXT PRIMARY KEY,
            nconst TEXT,
            category TEXT,
            job TEXT,
            characters TEXT );''')
    cur.execute('''\copy names from 'clean_data\\principals.csv' CSV HEADER''')

# Add "titles" table to database
def createTitles():
    cur = conn.cursor()
    cur.execute('''
        CREATE TABLE IF NOT EXISTS titles(
            titleId TEXT PRIMARY KEY,
            titleType TEXT,
            originalTitle TEXT,
            startYear FLOAT,
            endYear FLOAT,
            runtimeMinutes FLOAT,
            genres TEXT,
            year FLOAT,
            averageRating FLOAT,
            numVotes FLOAT );''')
    cur.execute('''\copy names from 'clean_data\\titles.csv' CSV HEADER''')

def createAll():
    createCrew()
    createCustomerRatings()
    createNames()
    createPrincipals()
    createTitles()

######################################## 
#          DELETE TABLES 
########################################
# Delete provided table from database
def deleteTable(tablename=0):
    cur = conn.cursor()
    cur.execute(
        sql.SQL("DROP TABLE {};")
        .format(sql.Identifier(tablename))
    )

# Clear database of all tables
def deleteAllTables():
    deleteTable("crew")
    deleteTable("ratings")
    deleteTable("names")
    deleteTable("principals")
    deleteTable("titles")