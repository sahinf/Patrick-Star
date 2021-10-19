import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class HollywoodPairs {
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
	public HollywoodPairs() {
		connection = dbConnector();
		//String otherDirector = getIndirectDirector("nm0000003");
		//System.out.println(otherDirector);
		
		
	}
	
	public static void main(String[] args) {
		HollywoodPairs pairs = new HollywoodPairs();
	}
	
	public List<String> getPairs() {
		try{
		     //create a statement object
		       Statement stmt = connection.createStatement();
		       //create an SQL statement
		       
		       /*the logic is as follows:
		        * moviesWorkedin gets filmography of name in question
		        * costars gets the actors in those movies not including original person
		        * costarsDirectors joins again with principals, this time getting all movies costars appear in
		        * these rows also have a category column, so we can filter further to get the directors.
		        * then,we also get the directors directly related to the movies the original star was in.
		        * after that, the directors in common needed to be removed so only the directors who were not related
		        * to the actor stayed.
		        */
		       String sqlStatement = 
		    		   "with"
		    		   + " truncated as ("
		    		   + "     select * from principals"
		    		   + "     where category = 'actor' or category = 'actress'"
		    		   + "     limit 10000" // capped it at 10000 for speed
		    		   + " ),"
		    		   + " allpairs as ("
		    		   + "     select c.nconst as name1, d.nconst as name2, cast(t.averagerating as decimal) as averagerating,t.titleid"
		    		   + "     from truncated as c"
		    		   + "     join truncated as d on c.titleid = d.titleid"
		    		   + "     join titles as t on t.titleid = c.titleid"
		    		   + "     where c.nconst != d.nconst"
		    		   + " ),"
		    		   + " rankedpairs as ("
		    		   + "     select least(name1,name2) as person1, greatest(name1,name2) as person2, avg(cast(averagerating as float)) as average, count(cast(averagerating as float)) as total, sum(cast(averagerating as float)) as weighted"
		    		   + "     from allpairs"
		    		   + "     group by least(name1,name2),greatest(name1,name2)"
		    		   + "     order by weighted desc"
		    		   + "     "
		    		   + " )"
		    		   + " select * from rankedpairs limit 10;";
		    		   

		       //send statement to DBMS
		       ResultSet result = stmt.executeQuery(sqlStatement);
		       List<String> thePairs =new ArrayList<>();;
		       //OUTPUT
		       while(result.next()) {
		    	    String pairString = result.getString("person1") + " with "+  result.getString("person2"); 
		    	    thePairs.add(pairString);
		    	   
		       }
		       return thePairs;
		       
		   } catch (Exception e){
		     System.out.println(e);
		     return null;
		   }
	}

}
