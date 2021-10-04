from funcs import *

# Remove the first garbage columns from csv and then use sep=','
# cleanupAll()


# UNCOMMENT THIS TO CONNECT TO DATABASE:
# conn = hackIntoMainframe()

# Delete tables from database (checks if already exists)
# deleteAllTables()

# Add tables to database (checks if already exists)
# createAll()

# Copy data to tables
# copyAll() # This function takes a while to execute. Bottleneck


# TEST QUERIES (20 of them)
cur = conn.cursor()
print("Testing whether data was added to tables")


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







cur.close()
