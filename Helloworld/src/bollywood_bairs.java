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
    }

    public static void main(String[] args) {
        bollywood_bairs boly = new bollywood_bairs();
        try {
			String s = boly.doWork();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }

    public HashMap<String, Double> queryTop25k() throws Exception {
        //create a statement object
        ResultSet result;
        try {
            Statement stmt = connection.createStatement();
//            String sqlStatement = "SELECT titles.titleid, titles.averagerating FROM titles\n" +
//                    "ORDER BY titles.averagerating DESC\n" +
//                    "LIMIT 10;";
            String sqlStatement = "select titles.titleid, titles.averagerating"
    	       		+ " from titles"
            		+ " where titles.titleid != 'titleId' "
    	       		+ " order by titles.averagerating desc" 
    	       		+ " LIMIT 250000;";
            // send statement to DB
            result = stmt.executeQuery(sqlStatement);
            HashMap<String, Double> res = new HashMap<>();
            while(result.next()){
                String titleId = result.getString("titleId");
                Double rating = result.getDouble("averageRating");
                res.put(titleId, rating);
            }
            return res;
        }
        catch (Exception e){
            //JOptionPane.showMessageDialog(null,e);
            throw new Exception("Error in accessing data in queryTop25k");
        }
    }
    public String queryGood() throws Exception {
    	ResultSet re;
    	try {
    		String s = new String();
    		Statement stmt = connection.createStatement();
    		String sqlStatement = "with\n"
    				+ "truncated as (\n"
    				+ "    select * from principals\n"
    				+ "    where category = 'actor' or category = 'actress'\n"
    				+ "    limit 100000\n"
    				+ "),\n"
    				+ "allpairs as (\n"
    				+ "    select c.nconst as name1, d.nconst as name2, cast(t.averagerating as decimal) as averagerating,t.titleid\n"
    				+ "    from truncated as c\n"
    				+ "    join truncated as d on c.titleid = d.titleid\n"
    				+ "    join titles as t on t.titleid = c.titleid\n"
    				+ "    where c.nconst != d.nconst\n"
    				+ "),\n"
    				+ "rankedpairs as (\n"
    				+ "    select least(name1,name2) as person1, greatest(name1,name2) as person2, avg(cast(averagerating as float)) as average, count(cast(averagerating as float)) as total\n"
    				+ "    from allpairs\n"
    				+ "    group by least(name1,name2),greatest(name1,name2)\n"
    				+ "    order by average desc\n"
    				+ "    \n"
    				+ ")\n"
    				+ "-- rankedpairs as (\n"
    				+ "--     select name1, name2, avg(cast(averagerating as float)) as average\n"
    				+ "--     from allpairs\n"
    				+ "--     group by name1, name2\n"
    				+ "--     order by average desc\n"
    				+ "-- )\n"
    				+ "-- rankedpairs as (\n"
    				+ "--     select name1, name2,averagerating, titleid\n"
    				+ "--     from allpairs\n"
    				+ "-- )\n"
    				+ "--  select sum(cast(averagerating as float)) from rankedpairs where greatest(name1,name2) = 'nm0005658' and least(name1,name2) = 'nm0000428';\n"
    				+ "select * from rankedpairs;";
            re = stmt.executeQuery(sqlStatement);
    		while(re.next()) {
    			String act1 = re.getString("name1");
    			String act2 = re.getString("name2");
                Double rating = re.getDouble("average");
                s += act1 + ", " + act2 + ": " + rating;

    		}
    		return s;
    	}
    	catch(Exception e) {
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
    

    public String doWork() throws Exception {
        // Type is <titleID, rating>
//        HashMap<String, Double> title_ratings = queryTop25k();
//
//        // Type is <titleID, vector<Actors>>
//        HashMap<String, ArrayList<String>> title_actors = queryActors();
//        
        // Test whether title_actors is populated
//        for(Map.Entry<String, ArrayList<String>> m : title_actors.entrySet()) {
//        	// .getValue and .getKey
//            System.out.println(m.getKey() + m.getValue().get(0));
//        }

        // Type is <<Actor1, Actor2>, vector<rating>>
//        HashMap<Pair<String, String>, ArrayList<Double>> pair_ratings = new HashMap<>();

//        Iterator ratingIter = title_ratings.entrySet().iterator();
//        while(ratingIter.hasNext()){
//            // This map element is <titleID, rating>
//            Map.Entry mapElement = (Map.Entry) ratingIter.next();
//
//            // .getValue and .getKey
//            ArrayList<String> actors = new ArrayList();
//            actors = title_actors.get(mapElement.getKey());
//            int size = actors.size();
//            for (int i = 0; i < size - 1; i++) {
//                for (int j = i+1; j < size; j++) {
//                    Pair<String, String> p = new Pair<>(actors.get(i), actors.get(j));
//                    if (pair_ratings.get(p) != null) {
//                        pair_ratings.get(p).add((Double) mapElement.getValue());
//                    }
//                }
//            }
//        }
//        for(Map.Entry<String, Double> m : title_ratings.entrySet()) {
//        	// .getValue and .getKey
//            ArrayList<String> actors = new ArrayList();
//            if (title_actors.get(m.getKey()) != null){
//	            actors = title_actors.get(m.getKey());
//	            int size = actors.size();
//	            for (int i = 0; i < size - 1; i++) {
//	                for (int j = i+1; j < size; j++) {
//	                    Pair<String, String> p = new Pair<>(actors.get(i), actors.get(j));
//	                    if (pair_ratings.get(p) != null) {
//	                        pair_ratings.get(p).add((Double) m.getValue());
//	                    }
//	                }
//	            }
//            }
//        }

        // Ordered map of rating, <actor, actor> in descending order thanks to reverseOrder();
//        TreeMap<Double, Pair<String, String>> pair_avg_rat = new TreeMap<Double, Pair<String, String>>(Collections.reverseOrder());
//        for (Map.Entry<Pair<String, String>, ArrayList<Double>> m : pair_ratings.entrySet()){
//            Double sum = 0.0;
//            // m.getValue() = ArrayList<Double>
//            ArrayList<Double> vec_rat = m.getValue();
//            for (Double f : vec_rat) {
//                sum += f;
//            }
//            // Divide total ratings by size to get AVERAGE
//            sum = sum / vec_rat.size();
//            pair_avg_rat.put(sum, m.getKey()); // actor, actor, rating_average
//        }
//
//
//        // TESTING Print out the top 20 values
//        System.out.println("Printing out the top 10 actor pairs and their ratings");
//        String result = new String();
//        int i = 0;
//        int print_num = 10;
//        Iterator iter = pair_avg_rat.entrySet().iterator();
//        while (iter.hasNext() && i < print_num) {
//            Map.Entry m = (Map.Entry) iter.next();
//            System.out.println("Actors: " + m.getValue() + " rating: " + m.getKey());
//            result += m.getValue() + " : " + m.getKey();
//        }
    	String result = new String();
        result = queryGood();
        System.out.println(result);
        return result;

    }

}


