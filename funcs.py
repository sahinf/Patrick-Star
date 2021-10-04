from os import curdir
from connect import *
import pandas as pd

######################################## 
#          SANITIZE CSV 
########################################

# Uncomment to connect to database
conn = hackIntoMainframe()

# Removes first instance of column with empty name to make it look cleaner
def cleanup(csv_file, dir='data/'):
    # remove duplicate entries and 'Year' entry from 'title'
    if (csv_file == 'titles'):
        data = pd.read_csv(dir + csv_file + '.csv', sep='\t')
        del data['Unnamed: 0']
        del data['Year']
        data.drop_duplicates(keep=False, inplace=True)
        # data.to_csv('clean_data/'+csv_file+'.csv', encoding='utf-8', index=False)
        data.to_csv('clean_data/'+csv_file+'.csv', index=False, sep='\t')
    else: 
        data = pd.read_csv(dir + csv_file + '.csv', sep='\t')
        del data['Unnamed: 0']
        data.drop_duplicates(keep=False, inplace=True)
        # data.to_csv('clean_data/'+csv_file+'.csv', encoding='utf-8', index=False)
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
            year TEXT,
            averageRating TEXT,
            numVotes TEXT 
        );''')
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

def copyCrew():
    cur = conn.cursor()
    file = open('clean_data/crew.csv')
    cur.copy_from(file, 'crew', sep="\t")
    conn.commit()

def copyRatings():
    cur = conn.cursor()
    file = open('clean_data/customer_ratings.csv')
    cur.copy_from(file, 'ratings', sep="\t")
    conn.commit()

def copyNames():
    cur = conn.cursor()
    file = open('clean_data/names.csv')
    cur.copy_from(file, 'names', sep="\t")
    conn.commit()

def copyPrincipals():
    cur = conn.cursor()
    file = open('clean_data/principals.csv')
    cur.copy_from(file, 'principals', sep="\t")
    conn.commit()

def copyTitles():
    cur = conn.cursor()
    file = open('clean_data/titles.csv')
    cur.copy_from(file, 'titles', sep="\t")
    conn.commit()

def copyAll():
    copyCrew()
    copyRatings()
    copyNames()
    copyPrincipals()
    copyTitles()

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