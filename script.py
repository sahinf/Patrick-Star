import pandas as pd
from funcs import *

# Remove the first garbage columns from csv and then use sep=','
# cleanupAll()

# Add tables to database
createCrew()
createCustomerRatings()
createNames()
createPrincipals()
createTitles()


cur = conn.cursor()
# cur.execute("SELECT * FROM crew LIMIT 10")
# query_results = cur.fetchall()
# print(query_results)

cur.close()
