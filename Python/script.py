from funcs import *

# Remove the first garbage columns from csv and then use sep=','
# cleanupAll()
cleanup('titles')

# UNCOMMENT THIS TO CONNECT TO DATABASE:
# conn = hackIntoMainframe()

# Delete tables from database (checks if already exists)
# deleteAllTables()

# Add tables to database (checks if already exists)
# createAll()

# Copy data to tables
# copyAll() # This function takes a while to execute. Bottleneck


# TEST QUERIES (20 of them)
# cur = conn.cursor()
# print("Testing whether data was added to tables")
# query21()

# cur.close()
