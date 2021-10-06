import java.sql.*;
//import javax.swing.JOptionPane;

/*
CSCE 315
9-25-2019 Original
2/7/2020 Update for AWS
 */

 //Commands to run this script
  //This will compile all java files in this directory
  //javac *.java 
  //This command tells the file where to find the postgres jar which it needs to execute postgres commands, then executes the code
  //Windows: java -cp ".;postgresql-42.2.8.jar" jdbcpostgreSQL
  //Mac/Linux: java -cp ".:postgresql-42.2.8.jar" jdbcpostgreSQL

public class jdbcpostgreSQL {
  public static void main(String args[]) {
    //dbSetup hides my username and password
    dbSetup my = new dbSetup();
    //Building the connection
     Connection conn = null;
     String sectionNumber = "911"; // replace with your section number
     String groupNumber = "group1"; // replace with your group nymber
     // csce315911_group1db     csce911_group2_db   csce912_group1db ......
     String dbName = "csce315"+sectionNumber + "_" + groupNumber + "db";
     String dbConnectionString = "jdbc:postgresql://csce-315-db.engr.tamu.edu/" + dbName;
     // csce315911_group1user  csce911_group2user  csce912_group1user .....
     String userName = "csce315"+sectionNumber + "_" + groupNumber + "user";
     String userPassword = "password";
     

     try {
        
        conn = DriverManager.getConnection(dbConnectionString,userName, userPassword);
     } catch (Exception e) {
        e.printStackTrace();
        System.err.println(e.getClass().getName()+": "+e.getMessage());
        System.exit(0);
     }//end try catch
     System.out.println("Opened database successfully");
     String cus_lname = "";
     try{
     //create a statement object
       Statement stmt = conn.createStatement();
       //create an SQL statement
       //TO DO: update the sql command here
       String sqlStatement = "ENTER SQL COMMAND HERE";
       //send statement to DBMS
       //ResultSet result = stmt.executeQuery(sqlStatement);

       //OUTPUT
       System.out.println("Query Results");
       System.out.println("______________________________________");
      //  while (result.next()) {
      //    System.out.println(result.getString("column_lname"));
      //  }
   } catch (Exception e){
     System.out.println("Error accessing Database.");
   }
    //closing the connection
    try {
      conn.close();
      System.out.println("Connection Closed.");
    } catch(Exception e) {
      System.out.println("Connection NOT Closed.");
    }//end try catch
  }//end main
}//end Class
