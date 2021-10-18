import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class indirectDirector {
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
	public indirectDirector() {
		connection = dbConnector();
		//String otherDirector = getIndirectDirector("nm0000003");
		//System.out.println(otherDirector);
		
		
	}
	
	public static void main(String[] args) {
		indirectDirector nothaOne = new indirectDirector();
	}
	
	public String getIndirectDirector(String nameId) {
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
		    		   + "  moviesWorkedIn as("
		    		   + " select titleid,nconst from principals as p where nconst = '"+nameId+"'"
		    		   + " ),"
		    		   + " costars as("
		    		   + "     select p.nconst, c.titleid from moviesWorkedIn as c"
		    		   + "     left join principals as p on  c.titleid = p.titleid"
		    		   + "     where c.nconst != p.nconst"
		    		   + " ),"
		    		   + " costarsDirectors as("
		    		   + "     select p.titleid, c.nconst, p.nconst as director from costars as c"
		    		   + "     left join principals as p on c.nconst = p.nconst"
		    		   + "     where p.category = 'director'"
		    		   + " ),"
		    		   + " starDirectors as ("
		    		   + "     select p.nconst as director, c.titleid from moviesWorkedIn as c"
		    		   + "     left join principals as p on  c.titleid = p.titleid"
		    		   + "     where c.nconst != p.nconst and  p.category = 'director'"
		    		   + " "
		    		   + " ),"
		    		   + " costarsDirectorsOrdered as ("
		    		   + "     select director, count(*) as num from costarsDirectors"
		    		   + "     group by director"
		    		   + "     order by num desc"
		    		   + "     "
		    		   + " ),"
		    		   + " starDirectorsOrdered as ("
		    		   + "     select director, count(*) as num from starDirectors"
		    		   + "     group by director"
		    		   + "     order by num desc"
		    		   + " ),"
		    		   + " costarsDirectorsOnly as ("
		    		   + " select director from costarsDirectorsOrdered"
		    		   + " except"
		    		   + " select director from starDirectorsOrdered"
		    		   + " )"
		    		   + "  select costarsDirectorsOnly.director,costarsDirectorsOrdered.num"
		    		   + "  from costarsDirectorsOnly left join costarsDirectorsOrdered on costarsDirectorsOnly.director = costarsDirectorsOrdered.director"
		    		   + "  order by num desc"
		    		   + "  limit 1;"; // change
		       //send statement to DBMS
		       ResultSet result = stmt.executeQuery(sqlStatement);
		       String directorId = "";
		       //OUTPUT
		       while(result.next()) {
		    	    directorId = result.getString("director");
		    	   
		       }
		       return directorId;
		       
		   } catch (Exception e){
		     System.out.println(e);
		     return null;
		   }
	}

}
