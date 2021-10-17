import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.*;

public class bollywood_bairs {

    /*
    Attempt to connect to the database
     */
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

    public bollywood_bairs(){
        connection = dbConnector();
//        bulkquery();
    }

    public static void main(String[] args) {
        bollywood_bairs boly = new bollywood_bairs();
    }

//    public void bulkquery() {
//        String cus_lname = "";
//        try{
//            //create a statement object
//            Statement stmt = connection.createStatement();
//            //create an SQL statement
//            String sqlStatement = "select titleid, customerid from ratings4plus"; // change
//            //send statement to DBMS
//            ResultSet result = stmt.executeQuery(sqlStatement);
//
//            //OUTPUT
//            //JOptionPane.showMessageDialog(null,"something bout crew.");
//            //System.out.println("______________________________________");
//            //System.out.println(result.getString("cus_lname"));
//            while(result.next()) {
//                String titleid = result.getString("titleid");
//                String customerid = result.getString("customerid");
//                addEdge(titleid,customerid,true);
//            }
//            //JOptionPane.showMessageDialog(null,cus_lname);
//            //return cus_lname;
//            //System.out.println(cus_lname);
//        } catch (Exception e){
//            //JOptionPane.showMessageDialog(null,e);
//            System.out.println(e);
//        }
//        System.out.println(graph.size());
//    }

    public HashMap<String, Float> queryTop25k(){ return null; } // THIS COULD POTENTIALLY BE A VECTOR IDK YET
    public HashMap<String, ArrayList<String>> queryAllPrincipals() {
        // the hardest logic here is to query only if the column is a job :(
        return null;
    }

    public void queryTitleActors() {}



}


