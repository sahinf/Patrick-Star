-- WITH tab1(customerid,num) as
-- (
-- select customerid, count(*) from ratings
-- group by customerid
-- )
-- select count(*) from tab1;

--  SELECT COUNT(DISTINCT customerid) AS uniques FROM ratings;

--   SELECT DISTINCT customerid AS uniques FROM ratings;

-- select count(*) from ratings where customerid = '826696';

-- WITH 
-- stars(titleid,customerid) AS
-- (
-- SELECT titleid,customerid,rating
--     FROM ratings
--     WHERE ratings.rating = '4.0' or ratings.rating = '5.0'
-- ),
-- tab1(titleid,num) as
-- (
-- select titleid, count(*) from stars
-- group by titleid
-- )
-- select count(*) from tab1;

-- create table if not exists ratings4plus(
--     customerid int,
--     rating text,
--     date text,
--     titleid text
-- )

-- INSERT INTO ratings4plus (
--     SELECT
--         cast(customerid as int),
--         rating,
--         date,
--         titleid
--     FROM ratings
--     WHERE rating = '4.0' or rating = '5.0'
-- );

-- WITH 
-- tab1(customerid,num) as
-- (
-- select customerid, count(*) as num from ratings4plus
-- group by customerid
-- order by num desc
-- )
-- select count(*) from tab1;

-- select count(*) from ratings4plus where titleid = 'tt14627576';

-- WITH tab1(titleid,num) as (select titleid, count(*) as num from ratings4plus group by titleid order by num desc) select * from tab1;

with 
cte1 as(
select titleid,nconst from principals as p where nconst = 'nm0000001'
),
-- select * from cte1;
cte2 as(
    select p.nconst, c.titleid from cte1 as c
    left join principals as p on  c.titleid = p.titleid
    where c.nconst != p.nconst
),
-- select count(*) from cte2 
cte3 as(
    select p.titleid, c.nconst, p.nconst as director from cte2 as c
    left join principals as p on c.nconst = p.nconst
    where p.category = 'director'
)
select count(distinct director) from cte3;

-- cte4 as (
--     select p.nconst, c.titleid from cte1 as c
--     inner join principals as p on c.titleid = p.titleid
--     where p.category = 'director'
-- )
-- SELECT count( distinct nconst) FROM cte4


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;

import javax.swing.JOptionPane;

//Use it in another class:
/*graph Graph = new graph();
List<String> chain = Graph.findShortestPath("tt0023563","tt0304678");
for (String id: chain) {
	DLM2.addElement(id);
}*/

public class graph {
	public static final String user = "csce315_913_3_user";
	public static final String pswd = "sikewrongnumber";
	Connection conn = null;
	public Connection dbConnector() {
		try {
	        Class.forName("org.postgresql.Driver");
	        
	        conn = DriverManager.getConnection("jdbc:postgresql://csce-315-db.engr.tamu.edu/csce315_913_3_db",
	           user, pswd);
	        System.out.println("Opened database successfully");
	        return conn;
	     } catch (Exception e) {
	        e.printStackTrace();
	        System.err.println(e.getClass().getName()+": "+e.getMessage());
	        return null;
	     }//end try catch
		
	}
	Connection connection = null;
	public graph() {
		connection = dbConnector();
		bulkquery();
		//tt0023563 , tt0304678
		/*List<String> shortpath = findShortestPath(graph,"tt0023563","tt0304678");
		for (String id: shortpath) {
			if (id.contains("tt")) {
				String titleName = getTitleName(id);
				System.out.println(titleName);
			}
			else {
				System.out.println(id);
			}
			
		}*/
		
		
	}
	
	public static void main(String[] args) {
		graph Graph = new graph();
	}
	
		public void bulkquery() {
		String cus_lname = "";
		try{
		     //create a statement object
		       Statement stmt = connection.createStatement();
		       //create an SQL statement
		       String sqlStatement = "select titleid, customerid from ratings4plus"; // change
		       //send statement to DBMS
		       ResultSet result = stmt.executeQuery(sqlStatement);

		       //OUTPUT
		       //JOptionPane.showMessageDialog(null,"something bout crew.");
		       //System.out.println("______________________________________");
		         //System.out.println(result.getString("cus_lname"));
		       while(result.next()) {
		    	   String titleid = result.getString("titleid");
		    	   String customerid = result.getString("customerid");
		    	    addEdge(titleid,customerid,true);
		       }
		       //JOptionPane.showMessageDialog(null,cus_lname);
		       //return cus_lname;
		       //System.out.println(cus_lname);
		   } catch (Exception e){
		     //JOptionPane.showMessageDialog(null,e);
		     System.out.println(e);
		   }
		System.out.println(graph.size());
	}
	public Map<String, List<String>> graph = new HashMap<>();

    public void addEdge(String source, String destination, boolean biDirectional) {
        if (!graph.containsKey(source)) {
            addVertex(source);
        }

        if (!graph.containsKey(destination)) {
            addVertex(destination);
        }

        graph.get(source).add(destination);
        if(biDirectional == true) {
            graph.get(destination).add(source);
        }
    }

    public void hasVertex(String vertex) {
        if(graph.containsKey(vertex)) {
            System.out.println("The Graph contains " + vertex + " as a vertex");
        }else {
            System.out.println("The Graph does not contain " + vertex + " as a vertex");
        }
    }

    public void hasEdge(String source, String destination) {
        if(graph.get(source).contains(destination)) {
            System.out.println("The Graph has an edge between " + source + " and " + destination);
        }else {
            System.out.println("The Graph has no edge between " + source + " and " + destination);
        }
    }

    public String printGraph() {
        StringBuilder builder = new StringBuilder();

        for(String vertex : graph.keySet()) {
            builder.append(vertex.toString() + ": ");
            for(String node: graph.get(vertex)) {
                builder.append(node.toString() + " ");
            }
            builder.append("\n");
        }
        return builder.toString();
    }

    private void addVertex(String vertex) {
        graph.put(vertex, new LinkedList<String>());
    }
    
    public  List<String> findShortestPath(String start,
            String end) {
    	
    	Map<String, Boolean> visitedMap = new HashMap<>();

    	for (String key : graph.keySet()) {
    	    visitedMap.put(key,false);
    	}
            List<String> bfsList = new LinkedList<>();
            Queue<String> queue = new LinkedList<>();
            Map<String,  String> prev = new HashMap<>();
           String current = start;

            queue.add(start);
            if (visitedMap.containsKey(start)){
            	visitedMap.put(start,true);
            }
            else {
            	System.out.println("\nThe title does not exist");
            	return Collections.emptyList();
            }
            //current.setVisited(true);

            while (!queue.isEmpty()) {

                current = queue.remove();

                if (current.equals(end)) {
                    break;
                } else {
                    List<String> currentFriends = graph.get(current);
                    for (String currentFriend : currentFriends) {
                        if (visitedMap.get(currentFriend)==false) {
                            queue.add(currentFriend);
                            visitedMap.put(currentFriend,true);
                            prev.put(currentFriend, current);
                        }
                    }
                }
            }

            if (!current.equals(end)) {
                System.out.println("\nThere is no path between " + start + " and " + end);
                return Collections.emptyList();
            }
            for (String node = end; node != null; node = prev.get(node)) {
                bfsList.add(node);
            }
            Collections.reverse(bfsList);
            for (String node: bfsList) {
            	System.out.println(node);
            }
            return bfsList;

        }
    
    
    public String getTitleName(String id){
    			try {
    			Statement stmt = connection.createStatement();
 		       //create an SQL statement
 		       String sqlStatement = "select originaltitle from titles where titleid = '"+id+"' limit 1; "; // change
 		       //send statement to DBMS
 		       ResultSet result = stmt.executeQuery(sqlStatement);
 		       String titleName = "";
 		       while (result.next()) {
 		    	   titleName = result.getString("originaltitle");
 		       }
 		       return titleName;
 		       
    			}
    			catch (Exception e){
    			     //JOptionPane.showMessageDialog(null,e);
    			     System.out.println(e);
    			     return null;
    			   }
    	}
	

}
