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

    public HashMap<String, Float> queryTop25k() throws Exception {
        //create a statement object
        ResultSet result;
        try {
            Statement stmt = connection.createStatement();
            String sqlStatement = "SELECT titles.titleid, titles.averagerating FROM titles\n" +
                    "ORDER BY titles.averagerating DESC\n" +
                    "LIMIT 10;";
            // send statement to DB
            result = stmt.executeQuery(sqlStatement);
            HashMap<String, Float> res = new HashMap<>();
            while(result.next()){
                String titleId = result.getString("titleId");
                Float rating = result.getFloat("averageRating");
                res.put(titleId, rating);
            }
            return res;
        }
        catch (Exception e){
            //JOptionPane.showMessageDialog(null,e);
            throw new Exception("Error in accessing data in queryTop25k");
        }
    }

    public HashMap<String, ArrayList<String>> queryActors() throws Exception {
        //create a statement object
        ResultSet result;
        try {
            Statement stmt = connection.createStatement();
            String sqlStatement = "select * from principals where category = 'actor' or category = 'actress'";
            // send statement to DB
            result = stmt.executeQuery(sqlStatement);
            HashMap<String, ArrayList<String>> res = new HashMap<>();
            while(result.next()){
                String titleId = result.getString("titleId");
                String category = result.getString("category");
                res.putIfAbsent(titleId, new ArrayList<String>());
                res.get(titleId).add(category);
            }
            return res;
        }
        catch (Exception e){
            //JOptionPane.showMessageDialog(null,e);
            throw new Exception("Error in accessing data in queryTop25k");
        }
    }

    public void queryTitleActors() {}

    public void doWork() throws Exception {
        // Type is <titleID, rating>
        HashMap<String, Float> title_ratings = queryTop25k();

        // Type is <titleID, vector<Actors>>
        HashMap<String, ArrayList<String>> title_actors = queryActors();

        // Type is <<Actor1, Actor2>, vector<rating>>
        HashMap<Pair<String, String>, ArrayList<Float>> pair_ratings = new HashMap<>();

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

        // Ordered map of rating, <actor, actor> in descending order thanks to reverseOrder();
        TreeMap<Float, Pair<String, String>> pair_avg_rat = new TreeMap<Float, Pair<String, String>>(Collections.reverseOrder());
        for (Map.Entry<Pair<String, String>, ArrayList<Float>> m : pair_ratings.entrySet()){
            Float sum = 0.0f;
            // m.getValue() = ArrayList<Float>
            ArrayList<Float> vec_rat = m.getValue();
            for (Float f : vec_rat) {
                sum += f;
            }
            // Divide total ratings by size to get AVERAGE
            sum = sum / vec_rat.size();
            pair_avg_rat.put(sum, m.getKey()); // actor, actor, rating_average
        }


        // TESTING Print out the top 20 values
        System.out.println("Printing out the top 20 actor pairs and their ratings");
        int i = 0;
        int print_num = 20;
        Iterator iter = pair_avg_rat.entrySet().iterator();
        while (iter.hasNext() && i < print_num) {
            Map.Entry m = (Map.Entry) iter.next();
            System.out.println("Act 1: " + m.getValue() + " rating: " + m.getKey());
        }

    }

}


