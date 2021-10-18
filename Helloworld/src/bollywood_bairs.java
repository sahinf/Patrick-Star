import java.sql.*;
import java.util.*;
import java.lang.Object;

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
//            // OUTPUT
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

    public HashMap<String, Float> queryTop25k(){
        //create a statement object
        ResultSet result;
        try {
            Statement stmt = connection.createStatement();
            String sqlStatement = "SELECT titles.titleid, titles.averagerating FROM titles\n" +
                    "ORDER BY titles.averagerating DESC\n" +
                    "LIMIT 10;";
            // send statement to DB
            result = stmt.executeQuery(sqlStatement);
        }
        catch (Exception e){
            //JOptionPane.showMessageDialog(null,e);
//            throw new Exception("Error in accessing data in queryTop25k");
        }

        return null;
    }

    public HashMap<String, ArrayList<String>> queryActors() {
        // the hardest logic here is to query only if the column is a job :(
        return null;
    }

    public void queryTitleActors() {}

    public void doWork(){
        // Type is <titleID, rating>
        HashMap<String, Float> title_ratings = queryTop25k();

        // Type is <titleID, vector<Actors>>
        HashMap<String, ArrayList<String>> title_actors = queryActors();

        // Type is <<Actor1, Actor2>, vector<rating>>
        HashMap<Pair<String, String>, ArrayList<Float>> pair_ratings = new HashMap<>();

        // Testing a different method of <<Actor1, Actor2>, rating>
        ArrayList<Pair<Pair<String, String> , Float>> = new ArrayList<>();

        Iterator ratingIter = title_ratings.entrySet().iterator();

        while(ratingIter.hasNext()){
            // This map element is <titleID, rating>
            Map.Entry mapElement = (Map.Entry) ratingIter.next();

            // .getValue and .getKey
            ArrayList<String> actors = title_actors.get(mapElement.getKey());
            int size = actors.size();
            for (int i = 0; i < size - 1; i++) {
                for (int j = i+1; j < size; j++) {
                    Pair<String, String> p = new Pair<>(actors.get(i), actors.get(j));
                    pair_ratings.get(p).add((Float) mapElement.getValue());
                }
            }
        }

        // Now the pair_ratings map is filled with ALL pairs of actors and a vector of their ratings.
        // We need a List < <Actor1, Actor2>, Float >

    }

}


