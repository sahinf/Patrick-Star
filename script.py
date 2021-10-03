from funcs import *

# Remove the first garbage columns from csv and then use sep=','
cleanupAll()


# UNCOMMENT THIS TO CONNECT TO DATABASE:
# conn = hackIntoMainframe()

# Add tables to database (checks if already exists)
# createAll()

# cur = conn.cursor()
# print("Testing whether data was added to tables")
# cur.execute("SELECT * FROM crew LIMIT 10")
# query_results = cur.fetchall()
# print(query_results)

# cur.close()
