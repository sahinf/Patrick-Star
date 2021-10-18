import java.awt.EventQueue;

import javax.swing.JFrame;
import java.lang.Throwable;
import java.awt.Color;
import javax.swing.JTextField;
import java.awt.BorderLayout;
import java.awt.GridBagLayout;
import javax.swing.JTextPane;
import javax.sound.midi.SysexMessage;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.sql.Array;
import java.util.HashMap;
import java.util.Arrays;
import java.util.Collections;
import java.util.Map;
import java.util.Vector;
import java.util.List;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.awt.event.ActionEvent;
import javax.swing.JPanel;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;

import java.awt.Button;
import javax.swing.JTabbedPane;
import javax.swing.JRadioButton;

public class viewersChoice{
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
	public viewersChoice() {
		connection = dbConnector();
		
	}
	
	public static void main(String[] args) {
		viewersChoice ViewersChoice = new viewersChoice();
	}
	
	
	public String getPersonalRec(String userFavGenres) throws Exception {
		String cus_lname = "<html>";
		ResultSet result;
		 
	     try{
	     //create a statement object
	       Statement stmt = connection.createStatement();
	       //Collecting 4+ ratings from the user
	       String sqlStatement = "select titles.originaltitle, titles.genres, titles.averagerating, titles.numvotes"
	       		+ " from titles"
	       		+ " where titles.genres != '' and titles.originaltitle != 'originalTitle' "
	       		+ " order by titles.averagerating desc;"; // change
	       //send statement to DBMS
	       result = stmt.executeQuery(sqlStatement);
	       
	       // titles.averagerating > '7.0' and titles.numvotes > '500' and 
	       //Generating recommended title list, only looking for titles that have a credible rating.
	       //Definition of credible rating according to our group: Rating that is 7.0+ AND has more than 50 votes.
	       String placeholder = "";
	       Integer aCount = 0;
	       while (result.next()) {
	    	   if ((result.getDouble("averagerating") < 7.0) || (result.getInt("numvotes") < 50)) {
	    		   continue;
	    	   }
	    	   placeholder = result.getString("genres");
	    	   if (placeholder.contentEquals(userFavGenres)) {
	    		   cus_lname += result.getString("originaltitle") + "<br>";
	    		   ++aCount;
	    		   if (aCount == 20) {
	    			   cus_lname += "</html>";
	    			   return cus_lname;
	    			  }
	    		   continue;
	    		   }
	       }
	       cus_lname += "</html>";
	       return cus_lname;
	   } catch (Exception e){
	     //JOptionPane.showMessageDialog(null,e);
	     throw new Exception("Error in accessing data");
	   }
		
		
	}
	
	
	
	public String curateViewersChoice(String user) throws Exception {
		Map<String,List<String>> userGenres = new HashMap<>();
		ResultSet result;
		 
	     try{
	     //create a statement object
	       Statement stmt = connection.createStatement();
	       //Collecting 4+ ratings from the user
	       String sqlStatement = "select ratings4plus.titleid,titles.originaltitle, titles.genres, ratings4plus.rating"
	       		+ " from ratings4plus"
	       		+ " left join titles on ratings4plus.titleid = titles.titleid"
	       		+ " where ratings4plus.customerid = '"+user+"' "
	       		+ " group by ratings4plus.titleid,titles.originaltitle,titles.genres, ratings4plus.rating"
	       		+ " order by ratings4plus.rating desc;"; // change
	       //send statement to DBMS
	       result = stmt.executeQuery(sqlStatement);
	       
	       //Populating hashmap of each title and their genres
	       while (result.next()) {
	         userGenres.put(result.getString("titleid"), Arrays.asList(result.getString("genres")));
	       }
	       
	       //Creating a hashmap which collects the frequency of genres in each category.
		   Map<String, Integer> genreCount = new HashMap<>();
		   for (Map.Entry<String, List<String> > set : userGenres.entrySet()) {
			   List<String> placeholder = set.getValue();
			   for (int i = 0; i < placeholder.size(); ++i) {
				   if (!genreCount.containsKey(placeholder.get(i))) {
					   genreCount.put(placeholder.get(i), 1);
				   } else {
					   genreCount.merge(placeholder.get(i),1,Integer::sum);
				   }
			   }
		   }
		   
//		   System.out.println(genreCount);
		   //Find most occuring genre
		   Integer mostOccuring = 0;
		   String mostGenre = null;
		   for (Map.Entry<String,Integer> set : genreCount.entrySet()) {
			   if ((set.getValue() > mostOccuring) && (set.getKey().contains(","))) {
				   mostOccuring = set.getValue();
				   mostGenre = set.getKey();
			   }
		   }
		   
//		   System.out.println(mostGenre);
//		   System.out.println(mostOccuring);
		   return mostGenre;
	       
	   } catch (Exception e){
	     //JOptionPane.showMessageDialog(null,e);
	     throw new Exception("Error in accessing data");
	   }
		
		
	}
	
	
	
	
	
}