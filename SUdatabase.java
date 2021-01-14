// STEP: Import required packages
import java.sql.*;
import java.time.LocalDateTime;

import java.util.Scanner;

public class SUdatabase {
    private Connection c = null;
    private String dbName;
    private boolean isConnected = false;
    private String termGlobal = "hi";
    private String userLoggedIn = "hi";
	

    private void openConnection(String _dbName) {
        dbName = _dbName;

        if (false == isConnected) {
            System.out.println("++++++++++++++++++++++++++++++++++");
            System.out.println("Open database: " + _dbName);

            try {
                String connStr = new String("jdbc:sqlite:");
                connStr = connStr + _dbName;

                // STEP: Register JDBC driver
                Class.forName("org.sqlite.JDBC");

                // STEP: Open a connection
                c = DriverManager.getConnection(connStr);

                // STEP: Diable auto transactions
                c.setAutoCommit(false);

                isConnected = true;
                System.out.println("success");
            } catch (Exception e) {
                System.err.println(e.getClass().getName() + ": " + e.getMessage());
                System.exit(0);
            }

            System.out.println("++++++++++++++++++++++++++++++++++");
        }
    }

    private void closeConnection() {
        if (true == isConnected) {
            System.out.println("++++++++++++++++++++++++++++++++++");
            System.out.println("Close database: " + dbName);

            try {
                // STEP: Close connection
                c.close();

                isConnected = false;
                dbName = "";
                System.out.println("success");
            } catch (Exception e) {
                System.err.println(e.getClass().getName() + ": " + e.getMessage());
                System.exit(0);
            }

            System.out.println("++++++++++++++++++++++++++++++++++");
        }
    }

   
//handleStages


private boolean filterStageReviews(Scanner input, String term, int id) throws SQLException {
    boolean condition = true;
    boolean found = true;
        while(condition){

    System.out.println("++++++++++++++++++++++++++++++++++");
    System.out.println("Filter Options");
    System.out.println("Filter applies to r_date, r_score, or a search username");
    System.out.println("0 - cancel ()");
    System.out.println("1 - Date filter (equal to)");
    System.out.println("2 - Score Filter (greater than)");
    System.out.println("3 - Score Filter (less than)");
    System.out.println("4 - Date (equal to) and Score Filter(less than)");
    System.out.println("5 - Date (equal to) and Score Filter(greater than)");
    System.out.println("6 - Search Username");

    //if statement to catch blank case

    while (!input.hasNextInt()){
        System.out.println("Enter 0, 1, 2, 3, 4, 5, or 6");
        input.nextLine();
    }

    int num = input.nextInt();
    String sql = "empty"; //arbitrary initialization
    int first = -1;
    int second = -1;
    String filler = "empty";

    if (num > 6 || num < 0){
        System.out.println("ERROR : Not an acceptable input. Try again");
        continue;}
    else if (num ==0){
        return found;
    }
    else if (num == 1){
        System.out.println("Enter year constraint");

        while (!input.hasNextInt()){
            System.out.println("Enter a number.");
            input.nextLine();}
            
            first = input.nextInt();
    }
    else if (num == 2){
        System.out.println("Enter score greater than constraint");

        while (!input.hasNextInt()){
            System.out.println("Enter a number.");
            input.nextLine();}

            first = input.nextInt();
    }
    else if (num == 3){
        System.out.println("Enter score less than constraint");

        while (!input.hasNextInt()){
            System.out.println("Enter a number.");
            input.nextLine();}

            first = input.nextInt();
    }
    else if (num == 4){
        System.out.println("Enter year constraint");

        while (!input.hasNextInt()){
            System.out.println("Enter a number.");
            input.nextLine();}

            first = input.nextInt();

            System.out.println("Enter score less than constraint");

            while (!input.hasNextInt()){
                System.out.println("Enter a number.");
                input.nextLine();}

            second = input.nextInt();
    }
    else if (num == 5){
        System.out.println("Enter year constraint");

        while (!input.hasNextInt()){
            System.out.println("Enter a number.");
            input.nextLine();}

            first = input.nextInt();

            System.out.println("Enter score greater than constraint");

            while (!input.hasNextInt()){
                System.out.println("Enter a number.");
                input.nextLine();}

            second = input.nextInt();
    }
    else if (num == 6){
        System.out.println("Enter username to search");


            filler = input.nextLine();
            if (filler.isBlank()){
                filler = input.nextLine();
            }
            else{
                first = input.nextInt();
            }

            
    }

    try {


        if (num == 1 ){
            
            sql = "SELECT r_rateID, u_username, s_name, r_date, r_score, r_comment " + 
            "FROM Rates, Users, Stages " +
            "WHERE s_stageID  = ? AND " +
            "s_stageID = r_stageID AND " + 
            "u_userID = r_userID AND " +
            "r_date = ? ";
            
        }
        else if (num == 2){

            sql = "SELECT r_rateID, u_username, s_name, r_date, r_score, r_comment " + 
            "FROM Rates, Users, Stages " +
            "WHERE s_stageID  = ? AND " +
            "s_stageID = r_stageID AND " + 
            "u_userID = r_userID AND " +
            "r_score > ? ";
        }
        else if (num == 3){

            sql = "SELECT r_rateID, u_username, s_name, r_date, r_score, r_comment " + 
            "FROM Rates, Users, Stages " +
            "WHERE s_stageID  = ? AND " +
            "s_stageID = r_stageID AND " + 
            "u_userID = r_userID AND " +
            "r_score < ? ";
        }

        else if (num == 4){
            sql = "SELECT r_rateID, u_username, s_name, r_date, r_score, r_comment " + 
            "FROM Rates, Users, Stages " +
            "WHERE s_stageID  = ? AND " +
            "s_stageID = r_stageID AND " + 
            "u_userID = r_userID AND " +
            "r_date = ? AND " +
            "r_score < ? ";
        }
        else if (num == 5){
            sql = "SELECT r_rateID, u_username, s_name, r_date, r_score, r_comment " + 
            "FROM Rates, Users, Stages " +
            "WHERE s_stageID  = ? AND " +
            "s_stageID = r_stageID AND " + 
            "u_userID = r_userID AND " +
            "r_date = ? AND " +
            "r_score > ? ";
        }
        else if (num == 6){
            sql = "SELECT r_rateID, u_username, s_name, r_date, r_score, r_comment " + 
            "FROM Rates, Users, Stages " +
            "WHERE s_stageID  = ? AND " +
            "s_stageID = r_stageID AND " + 
            "u_userID = r_userID AND " +
            "u_username LIKE ? ";
        }
        //System.out.println("This is sql : " + sql);
        PreparedStatement stmt = c.prepareStatement(sql);
        //System.out.println("Prepared statement made");
        //need to gate second set with if statement
            stmt.setInt(1, id);
        if (num == 1 || num == 2 || num==3 || num == 4 || num == 5 ){
            stmt.setInt(2, first );}
        if (num == 4 || num == 5){
             stmt.setInt(3, second);}
        if (num == 6){
             stmt.setString(2, '%' + filler + '%');
                }
           
           // System.out.println("String set");
        
        ResultSet rs = stmt.executeQuery();
      
        

        System.out.printf("%10s | %-20s | %-20s | %-20s | %-20s | %-90s\n", "RateID", "Username", "Stage", "Date", "Score", "Comment");

            if(!rs.isBeforeFirst()){
                System.out.println("Filter resulted in no entries");
                found = false;
            }
        
            while (rs.next()) {
                int rateID = rs.getInt(1);
                String username = rs.getString(2);
                String stage = rs.getString(3);
                int date = rs.getInt(4);
                int score = rs.getInt(5);
                String comment = rs.getString(6);
                System.out.printf("%10d | %-20s | %-20s | %-20d | %-20d | %-90s\n", rateID, username, stage, date, score, comment);
             }  
       

        
        rs.close();
        stmt.close();
    } catch (Exception e) {
        System.err.println(e.getClass().getName() + ": " + e.getMessage());
    }
        }//while end

    System.out.println("++++++++++++++++++++++++++++++++++");
    return found;
        
}


private boolean reviewsOfStages(Scanner input, String term, int switch1) throws SQLException {
    boolean found = true;
    boolean condition = true;
    while(condition){
    
    System.out.println("++++++++++++++++++++++++++++++++++");
    System.out.println("Stage Review Information");
    System.out.println("Enter the ID # of the stage you would like information on. Or enter 0 to cancel");
    System.out.println("***Note: This works with stage ID's not currently on your list so be careful! Max stage ID is 74***");
    String sql = "empty"; //arbitrary initialization
    int num = 0;
  

    //if statement to catch blank case
   
    while (!input.hasNextInt()){
        System.out.println("Enter 0 or the ID# of the stage you would like to see reviews on!");
        input.nextLine();
    }

    num = input.nextInt();
    
    

    if (num > 74 || num < 0){
        System.out.println("ERROR : Not an acceptable input. Try again");
        continue;}
    else if (num ==0){
        return found;
    }

       

        

        
    
    try {
        
            sql = "SELECT r_rateID, u_username, s_name, r_date, r_score, r_comment " + 
            "FROM Rates, Users, Stages " +
            "WHERE s_stageID  = ? AND " +
            "s_stageID = r_stageID AND " + 
            "u_userID = r_userID";
                
        //System.out.println("This is sql : " + sql);
        PreparedStatement stmt = c.prepareStatement(sql);
        //System.out.println("Prepared statement made");
        //need to gate second set with if statement
            stmt.setInt(1,num);
        
           // System.out.println("String set");
       
        ResultSet rs = stmt.executeQuery();
      
      
            System.out.printf("%10s | %-20s | %-20s | %-10s | %-10s | %-90s\n", "RateID", "Username", "Stage", "Date", "Score", "Comment");
        
            if(!rs.isBeforeFirst()){
                System.out.println("No reviews on this stage!");
                found = false;
            }
            else{

            while (rs.next()) {
                int rateID = rs.getInt(1);
                String username = rs.getString(2);
                String stage = rs.getString(3);
                int date = rs.getInt(4);
                int score = rs.getInt(5);
                String comment = rs.getString(6);
                System.out.printf("%10d | %-20s | %-20s | %-10d | %-10d | %-90s\n", rateID, username, stage, date, score, comment);
             }  

             System.out.println("If you would like to filter results, enter 1. Otherwise enter 0.");
             
             while (!input.hasNextInt()){
                System.out.println("Enter 0 or 1");
                input.nextLine();
            }

            int filter = input.nextInt();
            while(filter != 0 && filter != 1){
                System.out.println("Enter 0 or 1");
                filter = input.nextInt();
            }

           if (filter == 1){
                //call filterchar rates here
               // System.out.println("calling rate filter");
                filterStageReviews(input, term, num);
            }
           

        }
            

        
        rs.close();
        stmt.close();
    } catch (Exception e) {
        System.err.println(e.getClass().getName() + ": " + e.getMessage());
    }

    
        System.out.println("Resetting.....");
        }
    System.out.println("++++++++++++++++++++++++++++++++++");
    return found;

}



private void reviewStage(Scanner input, String term) throws SQLException {
    
    boolean condition = true;
    while(condition){
    
    System.out.println("++++++++++++++++++++++++++++++++++");
    System.out.println("Review Stage Menu");
    System.out.println("Enter the ID # of the stage you would like to review. Or enter 0 to cancel");
    System.out.println("***Note: This works with stage ID's not currently on your list so be careful! Max character ID is 74***");
    
  

    //if statement to catch blank case

    while (!input.hasNextInt()){
        System.out.println("Enter 0 to cancel or the ID# of the stage you would like to view!");
        input.nextLine();
    }

    int num = input.nextInt();
    if (num > 74 || num < 0 ){
        System.out.println("ERROR : Not an acceptable input. Try again");
        continue;}
   else if (num == 0 ){
        return;
    }

    System.out.println("Enter the score for stage with ID #" + num + ". Score range is between 1 and 100.");
    System.out.println("If this is the wrong is ID, enter 0 to reset.");

    while (!input.hasNextInt()){
        System.out.println("Enter 0 or the score value!");
        input.nextLine();
    }

    int score = input.nextInt();


    

    if ( score < 0 || score > 100){
        System.out.println("ERROR : Not an acceptable input. Try again");
        continue;}
    else if (num == 0 || score == 0){
        continue;
    }
    else{

        System.out.println("Enter your review of the stage.");
        String comment = input.nextLine();
        if (comment.isBlank()){
            comment = input.nextLine();
        }
    

        int userID = retrieveUserID();
        int max = newRateID();
        int r_rateID = max + 1;
        int date = retrieveDate();
        
    try {

        String sql = "INSERT INTO Rates VALUES(?, ?, NULL, NULL, ?, 'stage', ?, ?, ?)";

        //System.out.println("This is sql : " + sql);
        PreparedStatement stmt = c.prepareStatement(sql);
        //System.out.println("Prepared statement made");
        //need to gate second set with if statement
            stmt.setInt(1,r_rateID);
            stmt.setInt(2,userID);
            stmt.setInt(3,num);
            stmt.setInt(4,date);
            stmt.setInt(5,score);
            stmt.setString(6,comment);
           // System.out.println("String set");
       
         // STEP: Execute batch
         stmt.executeUpdate();

         // STEP: Commit transaction
         c.commit();
 
         stmt.close();
         System.out.println("success");
      

        stmt.close();
    } catch (Exception e) {
        System.err.println(e.getClass().getName() + ": " + e.getMessage());
    }

    }
        System.out.println("Resetting.....");
        }
    System.out.println("++++++++++++++++++++++++++++++++++");
    return ;

}

private boolean songsOfStages(Scanner input, String term) throws SQLException {
    boolean found = true;
    boolean condition = true;
    while(condition){
    
    System.out.println("++++++++++++++++++++++++++++++++++");
    System.out.println("Stage Music Information");
    System.out.println("Enter the ID # of the stage you would like music information on. Or enter 0 to cancel");
    System.out.println("***Note: This works with stage ID's not currently on your list so be careful! Max character ID is 74***");
    
  

    //if statement to catch blank case

    while (!input.hasNextInt()){
        System.out.println("Enter 0 or the ID# of the character you would like to view!");
        input.nextLine();
    }

    int num = input.nextInt();
    String sql = "empty"; //arbitrary initialization
    

    if (num > 74 || num < 0){
        System.out.println("ERROR : Not an acceptable input. Try again");
        continue;}
    else if (num ==0){
        return found;
    }
    else{


    
    try {

            sql = "SELECT s_name, so_name, so_type, so_notes " + 
            "FROM Stages, StageSongs, Songs " +
            "WHERE s_stageID  = ? AND " +
            "s_stageID = ss_stageID AND " + 
            "ss_songID = so_songID";

        //System.out.println("This is sql : " + sql);
        PreparedStatement stmt = c.prepareStatement(sql);
        //System.out.println("Prepared statement made");
        //need to gate second set with if statement
            stmt.setInt(1,num);
           // System.out.println("String set");
       
        ResultSet rs = stmt.executeQuery();
      
        if(!rs.isBeforeFirst()){
            //System.out.println("My guy, our databse doesn't have what youre looking for");
            found = false;
        }

            System.out.printf("%10s | %-50s | %-50s | %-50s\n", "Stage", "Song", "Type", "Notes");
        
            while (rs.next()) {
                String stage = rs.getString(1);
                String song = rs.getString(2);
                String type = rs.getString(3);
                String notes = rs.getString(4);
                System.out.printf("%10s | %-50s | %-50s | %-50s\n", stage, song, type, notes);
             }  
        
       

        
        rs.close();
        stmt.close();
    } catch (Exception e) {
        System.err.println(e.getClass().getName() + ": " + e.getMessage());
    }

    }
        System.out.println("Resetting.....");
        }
    System.out.println("++++++++++++++++++++++++++++++++++");
    return found;

}

private boolean filterStages(Scanner input, String term) throws SQLException {
    boolean condition = true;
    boolean found = true;
        while(condition){

    System.out.println("++++++++++++++++++++++++++++++++++");
    System.out.println("Filter Options");
    System.out.println("Filter applies to s_size and s_type");
    System.out.println("0 - cancel ()");
    System.out.println("1 - Size filter");
    System.out.println("2 - Type filter");
    System.out.println("3 - Size and Type filter");
  

    //if statement to catch blank case

    while (!input.hasNextInt()){
        System.out.println("Enter 0, 1, 2, or 3");
        input.nextLine();
    }

    int num = input.nextInt();
    String sql = "empty"; //arbitrary initialization
    int first = -1;
    int second = -1;
    String third = "empty";
    String fourth = "empty";

    if (num > 3 || num < 0){
        System.out.println("ERROR : Not an acceptable input. Try again");
        continue;}
    else if (num ==0){
        return found;
    }
    else if (num == 1){
        System.out.println("These are the size options");
        System.out.println("Type in the number corresponding to your desired option");
        System.out.println("1 - small");
        System.out.println("2 - medium");
        System.out.println("3 - large");
        System.out.println("4 - extra-large");
      
        while (!input.hasNextInt()){
            System.out.println("Enter a number.");
            input.nextLine();}
            
            first = input.nextInt();

            if (first > 4 || first < 1){
                System.out.println("ERROR : Not an acceptable input. Resetting...");
                continue;}
    }
    else if (num == 2){
        System.out.println("These are the type options");
        System.out.println("Type in the number corresponding to your desired option");
        System.out.println("1 - Rarely Legal");
        System.out.println("2 - banned");
        System.out.println("3 - starter");
        System.out.println("4 - counter pick");
        System.out.println("5 - Almost Always Banned");
       
        while (!input.hasNextInt()){
            System.out.println("Enter a number.");
            input.nextLine();}
            
            first = input.nextInt();

            if (first > 5 || first < 1){
                System.out.println("ERROR : Not an acceptable input. Resetting...");
                continue;}
    }
    else if (num == 3){
        System.out.println("These are the size options");
        System.out.println("Type in the number corresponding to your desired option");
        System.out.println("1 - small");
        System.out.println("2 - medium");
        System.out.println("3 - large");
        System.out.println("4 - extra-large");
      
        while (!input.hasNextInt()){
            System.out.println("Enter a number.");
            input.nextLine();}
            
            first = input.nextInt();

            if (first > 4 || first < 1){
                System.out.println("ERROR : Not an acceptable input. Resetting...");
                continue;}

    
        System.out.println("These are the type options");
        System.out.println("Type in the number corresponding to your desired option");
        System.out.println("1 - Rarely Legal");
        System.out.println("2 - banned");
        System.out.println("3 - starter");
        System.out.println("4 - counter pick");
        System.out.println("5 - Almost Always Banned");
       
        while (!input.hasNextInt()){
            System.out.println("Enter a number.");
            input.nextLine();}
            
            second = input.nextInt();

            if (second > 5 || second < 1){
                System.out.println("ERROR : Not an acceptable input. Resetting...");
                continue;}
    }


    try {


        if (num == 1 ){
            
            if (first == 1 ){ third = "small"; }
            else if (first == 2 ){ third = "medium"; }
            else if (first == 3 ){ third = "large"; }
            else if (first == 4 ){ third = "extra-large"; }
           

            sql = "SELECT s_stageID, s_name, g_name, s_size, s_type " + 
            "FROM Stages, Games " +
            "WHERE s_name  LIKE ? AND " +
            "s_gameID = g_gameID AND " +
            "s_size = ? ";

            
        }
        else if (num == 2){

            if (first == 1 ){ third = "Rarely Legal"; }
            else if (first == 2 ){ third = "banned"; }
            else if (first == 3 ){ third = "starter"; }
            else if (first == 4 ){ third = "counter pick"; }
            else if (first == 5 ){ third = "Almost Always Banned"; }
         
            sql = "SELECT s_stageID, s_name, g_name, s_size, s_type " + 
            "FROM Stages, Games " +
            "WHERE s_name  LIKE ? AND " +
            "s_gameID = g_gameID AND " +
            "s_type = ? ";
        }
        else if (num == 3){

            if (first == 1 ){ third = "small"; }
            else if (first == 2 ){ third = "medium"; }
            else if (first == 3 ){ third = "large"; }
            else if (first == 4 ){ third = "extra-large"; }
            
            if (second == 1 ){ fourth = "Rarely Legal"; }
            else if (second == 2 ){ fourth = "banned"; }
            else if (second == 3 ){ fourth = "starter"; }
            else if (second == 4 ){ fourth = "counter pick"; }
            else if (second == 5 ){ fourth = "Almost Always Banned"; }

            sql = "SELECT s_stageID, s_name, s_size, s_type,  g_name " + 
            "FROM Stages, Games " +
            "WHERE s_name  LIKE ? AND " +
            "s_gameID = g_gameID AND " +
            "s_size = ? AND " +
            "s_type = ? ";
        }
       
        //System.out.println("This is sql : " + sql);
        PreparedStatement stmt = c.prepareStatement(sql);
        //System.out.println("Prepared statement made");
        //need to gate second set with if statement
            stmt.setString(1, '%' + term + '%');
            stmt.setString(2, third);
           // System.out.println("String set");
        if (num == 3){
            stmt.setString(3, fourth);
        }
        ResultSet rs = stmt.executeQuery();
      
        if(!rs.isBeforeFirst()){
            System.out.println("Filter resulted in no entries.");
            found = false;
        }

            System.out.printf("%10s | %-40s | %-30s | %-40s | %-50s\n", "Id", "Name", "Size", "Type", "Game");
        
            while (rs.next()) {
                int id = rs.getInt(1);
                String name = rs.getString(2);
                String size = rs.getString(3);
                String type = rs.getString(4);
                String game = rs.getString(5);
                System.out.printf("%10s | %-40s | %-30s | %-40s | %-50s\n", id, name, size, type, game);
             }  
        
       

        
        rs.close();
        stmt.close();
    } catch (Exception e) {
        System.err.println(e.getClass().getName() + ": " + e.getMessage());
    }
        }//while end

    System.out.println("++++++++++++++++++++++++++++++++++");
    return found;
        
}


    private void handleStages(Scanner input, String term) throws SQLException {
        boolean condition = true;
        while(condition){
        
        System.out.println("++++++++++++++++++++++++++++++++++");
        System.out.println("Would You like to start a new list search, make a review, or filter the current list?");
        System.out.println("0 - Return to Table Display");
        System.out.println("1 - Make a review");
        System.out.println("2 - Filter Current List");
        System.out.println("3 - Retrieve song information for a particular stage");
        System.out.println("4 - Retrieve other user reviews for a particular stage");
        
        while (!input.hasNextInt()){
            System.out.println("Enter 0, 1, 2, 3, or 4");
            input.nextLine();
        }

        int choice = input.nextInt();

        if (choice > 4 || choice < 0){
            System.out.println("ERROR : Not an acceptable input. Try again");
            continue;
        }
            else if (choice == 0){
                //System.out.println("Returning to Table Display");
                return;
        }
            else if (choice == 1){
                //System.out.println("Calling review stage");
                reviewStage(input, term);
        }
            else if (choice == 2){
                //System.out.println("Calling filter stages here");
                filterStages(input, term);

        }
            else if (choice == 3){
                //System.out.println("Calling songsOfStages");
                songsOfStages(input, term);
        }
        else if (choice == 4){
            //System.out.println("Calling reviewsOfStages");
            reviewsOfStages(input, term, 1);
    }

        }//while end
    }



//handleItems
private boolean filterItemReviews(Scanner input, String term, int id) throws SQLException {
    boolean condition = true;
    boolean found = true;
        while(condition){

    System.out.println("++++++++++++++++++++++++++++++++++");
    System.out.println("Filter Options");
    System.out.println("Filter applies to r_date, r_score, or a search username");
    System.out.println("0 - cancel ()");
    System.out.println("1 - Date filter (equal to)");
    System.out.println("2 - Score Filter (greater than)");
    System.out.println("3 - Score Filter (less than)");
    System.out.println("4 - Date (equal to) and Score Filter(less than)");
    System.out.println("5 - Date (equal to) and Score Filter(greater than)");
    System.out.println("6 - Search Username");

    //if statement to catch blank case

    while (!input.hasNextInt()){
        System.out.println("Enter 0, 1, 2, 3, 4, 5, or 6");
        input.nextLine();
    }

    int num = input.nextInt();
    String sql = "empty"; //arbitrary initialization
    int first = -1;
    int second = -1;
    String filler = "empty";

    if (num > 6 || num < 0){
        System.out.println("ERROR : Not an acceptable input. Try again");
        continue;}
    else if (num ==0){
        return found;
    }
    else if (num == 1){
        System.out.println("Enter year constraint");

        while (!input.hasNextInt()){
            System.out.println("Enter a number.");
            input.nextLine();}
            
            first = input.nextInt();
    }
    else if (num == 2){
        System.out.println("Enter score greater than constraint");

        while (!input.hasNextInt()){
            System.out.println("Enter a number.");
            input.nextLine();}

            first = input.nextInt();
    }
    else if (num == 3){
        System.out.println("Enter score less than constraint");

        while (!input.hasNextInt()){
            System.out.println("Enter a number.");
            input.nextLine();}

            first = input.nextInt();
    }
    else if (num == 4){
        System.out.println("Enter year constraint");

        while (!input.hasNextInt()){
            System.out.println("Enter a number.");
            input.nextLine();}

            first = input.nextInt();

            System.out.println("Enter score less than constraint");

            while (!input.hasNextInt()){
                System.out.println("Enter a number.");
                input.nextLine();}

            second = input.nextInt();
    }
    else if (num == 5){
        System.out.println("Enter year constraint");

        while (!input.hasNextInt()){
            System.out.println("Enter a number.");
            input.nextLine();}

            first = input.nextInt();

            System.out.println("Enter score greater than constraint");

            while (!input.hasNextInt()){
                System.out.println("Enter a number.");
                input.nextLine();}

            second = input.nextInt();
    }
    else if (num == 6){
        System.out.println("Enter username to search");


            filler = input.nextLine();
            if (filler.isBlank()){
                filler = input.nextLine();
            }
            else{
                first = input.nextInt();
            }

            
    }

    try {


        if (num == 1 ){
            
            sql = "SELECT r_rateID, u_username, i_name, r_date, r_score, r_comment " + 
            "FROM Rates, Users, Items " +
            "WHERE i_itemID  = ? AND " +
            "i_itemID = r_itemID AND " + 
            "u_userID = r_userID AND " +
            "r_date = ? ";
            
        }
        else if (num == 2){

            sql = "SELECT r_rateID, u_username, i_name, r_date, r_score, r_comment " + 
            "FROM Rates, Users, Items " +
            "WHERE i_itemID  = ? AND " +
            "i_itemID = r_itemID AND " + 
            "u_userID = r_userID AND " +
            "r_score > ? ";
        }
        else if (num == 3){

            sql = "SELECT r_rateID, u_username, i_name, r_date, r_score, r_comment " + 
            "FROM Rates, Users, Items " +
            "WHERE i_itemID  = ? AND " +
            "i_itemID = r_itemID AND " + 
            "u_userID = r_userID AND " +
            "r_score < ? ";
        }

        else if (num == 4){
            sql = "SELECT r_rateID, u_username, i_name, r_date, r_score, r_comment " + 
            "FROM Rates, Users, Items " +
            "WHERE i_itemID  = ? AND " +
            "i_itemID = r_itemID AND " + 
            "u_userID = r_userID AND " +
            "r_date = ? AND " +
            "r_score < ? ";
        }
        else if (num == 5){
            sql = "SELECT r_rateID, u_username, i_name, r_date, r_score, r_comment " + 
            "FROM Rates, Users, Items " +
            "WHERE i_itemID  = ? AND " +
            "i_itemID = r_itemID AND " + 
            "u_userID = r_userID AND " +
            "r_date = ? AND " +
            "r_score > ? ";
        }
        else if (num == 6){
            sql = "SELECT r_rateID, u_username, i_name, r_date, r_score, r_comment " + 
            "FROM Rates, Users, Items " +
            "WHERE i_itemID  = ? AND " +
            "i_itemID = r_itemID AND " + 
            "u_userID = r_userID AND " +
            "u_username LIKE ? ";
        }
        //System.out.println("This is sql : " + sql);
        PreparedStatement stmt = c.prepareStatement(sql);
        //System.out.println("Prepared statement made");
        //need to gate second set with if statement
            stmt.setInt(1, id);
        if (num == 1 || num == 2 || num==3 || num == 4 || num == 5 ){
            stmt.setInt(2, first );}
        if (num == 4 || num == 5){
             stmt.setInt(3, second);}
        if (num == 6){
             stmt.setString(2, '%' + filler + '%');
                }
           
           // System.out.println("String set");
        
        ResultSet rs = stmt.executeQuery();
      
        

        System.out.printf("%10s | %-20s | %-20s | %-20s | %-20s | %-90s\n", "RateID", "Username", "Character", "Date", "Score", "Comment");

            if(!rs.isBeforeFirst()){
                System.out.println("Filter resulted in no entries");
                found = false;
            }
        
            while (rs.next()) {
                int rateID = rs.getInt(1);
                String username = rs.getString(2);
                String item = rs.getString(3);
                int date = rs.getInt(4);
                int score = rs.getInt(5);
                String comment = rs.getString(6);
                System.out.printf("%10d | %-20s | %-20s | %-20d | %-20d | %-90s\n", rateID, username, item, date, score, comment);
             }  
       

        
        rs.close();
        stmt.close();
    } catch (Exception e) {
        System.err.println(e.getClass().getName() + ": " + e.getMessage());
    }
        }//while end

    System.out.println("++++++++++++++++++++++++++++++++++");
    return found;
        
}


private boolean reviewsOfItem(Scanner input, String term, int switch1) throws SQLException {
    boolean found = true;
    boolean condition = true;
    while(condition){
    
    System.out.println("++++++++++++++++++++++++++++++++++");
    System.out.println("Item Review Information");
    System.out.println("Enter the ID # of the item you would like information on. Or enter 0 to cancel");
    System.out.println("***Note: This works with item ID's not currently on your list so be careful! Max character ID is 87***");
    String sql = "empty"; //arbitrary initialization
    int num = 0;
  

    //if statement to catch blank case
   
    while (!input.hasNextInt()){
        System.out.println("Enter 0 or the ID# of the item you would like to see reviews on!");
        input.nextLine();
    }

    num = input.nextInt();
    
    

    if (num > 87 || num < 0){
        System.out.println("ERROR : Not an acceptable input. Try again");
        continue;}
    else if (num ==0){
        return found;
    }

       

        

        
    
    try {
        
        sql = "SELECT r_rateID, u_username, i_name, r_date, r_score, r_comment " + 
        "FROM Rates, Items, Users " +
        "WHERE i_itemID  = ? AND " +
        "i_itemID = r_itemID AND " + 
        "u_userID = r_userID";
                
        //System.out.println("This is sql : " + sql);
        PreparedStatement stmt = c.prepareStatement(sql);
        //System.out.println("Prepared statement made");
        //need to gate second set with if statement
            stmt.setInt(1,num);
        
           // System.out.println("String set");
       
        ResultSet rs = stmt.executeQuery();
      
      
            System.out.printf("%10s | %-20s | %-20s | %-20s | %-20s | %-90s\n", "RateID", "Username", "Item", "Date", "Score", "Comment");
        
            if(!rs.isBeforeFirst()){
                System.out.println("No reviews on this item!");
                found = false;
            }
            else{

            while (rs.next()) {
                int rateID = rs.getInt(1);
                String username = rs.getString(2);
                String item = rs.getString(3);
                int date = rs.getInt(4);
                int score = rs.getInt(5);
                String comment = rs.getString(6);
                System.out.printf("%10d | %-20s | %-20s | %-20d | %-20d | %-90s\n", rateID, username, item, date, score, comment);
             }  

             System.out.println("If you would like to filter results, enter 1. Otherwise enter 0.");
             
             while (!input.hasNextInt()){
                System.out.println("Enter 0 or 1");
                input.nextLine();
            }

            int filter = input.nextInt();
            while(filter != 0 && filter != 1){
                System.out.println("Enter 0 or 1");
                filter = input.nextInt();
            }

           if (filter == 1){
                //call filterchar rates here
                //System.out.println("calling rate filter");
                filterItemReviews(input, term, num);
            }
           

        }
            

        
        rs.close();
        stmt.close();
    } catch (Exception e) {
        System.err.println(e.getClass().getName() + ": " + e.getMessage());
    }

    
        System.out.println("Resetting.....");
        }
    System.out.println("++++++++++++++++++++++++++++++++++");
    return found;

}





private void reviewItem(Scanner input, String term) throws SQLException {
    
    boolean condition = true;
    while(condition){
    
    System.out.println("++++++++++++++++++++++++++++++++++");
    System.out.println("Review Item Menu");
    System.out.println("Enter the ID # of the item you would like to review. Or enter 0 to cancel");
    System.out.println("***Note: This works with item ID's not currently on your list so be careful! Max item ID is 87***");
    
  

    //if statement to catch blank case

    while (!input.hasNextInt()){
        System.out.println("Enter 0 to cancel or the ID# of the item you would like to view!");
        input.nextLine();
    }

    int num = input.nextInt();
    if (num > 87 || num < 0 ){
        System.out.println("ERROR : Not an acceptable input. Try again");
        continue;}
   else if (num == 0 ){
        return;
    }

    System.out.println("Enter the score for item with ID #" + num + ". Score range is between 1 and 100.");
    System.out.println("If this is the wrong is ID, enter 0 to reset.");

    while (!input.hasNextInt()){
        System.out.println("Enter 0 or the score value!");
        input.nextLine();
    }

    int score = input.nextInt();


    

    if ( score < 0 || score > 100){
        System.out.println("ERROR : Not an acceptable input. Try again");
        continue;}
    else if (num == 0 || score == 0){
        continue;
    }
    else{

        System.out.println("Enter your review of the item.");
        String comment = input.nextLine();
        if (comment.isBlank()){
            comment = input.nextLine();
        }
    

        int userID = retrieveUserID();
        int max = newRateID();
        int r_rateID = max + 1;
        int date = retrieveDate();
        
    try {

        String sql = "INSERT INTO Rates VALUES(?, ?, ?, NULL, NULL, 'Item', ?, ?, ?)";

        //System.out.println("This is sql : " + sql);
        PreparedStatement stmt = c.prepareStatement(sql);
        //System.out.println("Prepared statement made");
        //need to gate second set with if statement
            stmt.setInt(1,r_rateID);
            stmt.setInt(2,userID);
            stmt.setInt(3,num);
            stmt.setInt(4,date);
            stmt.setInt(5,score);
            stmt.setString(6,comment);
           // System.out.println("String set");
       
         // STEP: Execute batch
         stmt.executeUpdate();

         // STEP: Commit transaction
         c.commit();
 
         stmt.close();
         System.out.println("success");
      

        stmt.close();
    } catch (Exception e) {
        System.err.println(e.getClass().getName() + ": " + e.getMessage());
    }

    }
        System.out.println("Resetting.....");
        }
    System.out.println("++++++++++++++++++++++++++++++++++");
    return ;

}



private boolean filterItems(Scanner input, String term) throws SQLException {
    boolean condition = true;
    boolean found = true;
        while(condition){

    System.out.println("++++++++++++++++++++++++++++++++++");
    System.out.println("Filter Options");
    System.out.println("Filter applies to item type");
    System.out.println("0 - cancel ()");
    System.out.println("1 - Type filter");
 
    

    //if statement to catch blank case

    while (!input.hasNextInt()){
        System.out.println("Enter 0 or 1");
        input.nextLine();
    }

    int num = input.nextInt();
    String sql = "empty"; //arbitrary initialization
    int first = -1;
    String second = "empty";

    if (num > 1 || num < 0){
        System.out.println("ERROR : Not an acceptable input. Try again");
        continue;}
    else if (num ==0){
        return found;
    }
    else if (num == 1){
        System.out.println("These are the type options");
        System.out.println("Type in the number corresponding to your desired option");
        System.out.println("1 - Battering");
        System.out.println("2 - Container");
        System.out.println("3 - Explosive");
        System.out.println("4 - Recovery");
        System.out.println("5 - Shooting");
        System.out.println("6 - Special");
        System.out.println("7 - Status");
        System.out.println("8 - Summoning");
        System.out.println("9 - Throwing");
        System.out.println("10 - Transformation");

        while (!input.hasNextInt()){
            System.out.println("Enter a number.");
            input.nextLine();}
            
            first = input.nextInt();

            if (first > 10 || first < 1){
                System.out.println("ERROR : Not an acceptable input. Resetting...");
                continue;}
    }

    try {


        if (first == 1 ){ second = "Battering"; }
        else if (first == 2 ){ second = "Container"; }
        else if (first == 3 ){ second = "Explosive"; }
        else if (first == 4 ){ second = "Recovery"; }
        else if (first == 5 ){ second = "Shooting"; }
        else if (first == 6 ){ second = "Special"; }
        else if (first == 7 ){ second = "Status"; }
        else if (first == 8 ){ second = "Summoning"; }
        else if (first == 9 ){ second = "Throwing"; }
        else if (first == 10 ){ second = "Transformation"; }

        sql = "SELECT i_itemID, i_name, i_desc, i_type, g_name " + 
            "FROM Items, Games " +
            "WHERE i_gameID = g_gameID AND " +
            "i_itemID  LIKE ? AND " +
            "i_type LIKE ? ";

   
        //System.out.println("This is sql : " + sql);
        PreparedStatement stmt = c.prepareStatement(sql);
        //System.out.println("Prepared statement made");
        //need to gate second set with if statement
            stmt.setString(1, '%' + term + '%');
            stmt.setString(2, '%' + second + '%');
           // System.out.println("String set");
       
        ResultSet rs = stmt.executeQuery();
      
        if(!rs.isBeforeFirst()){
            //System.out.println("My guy, our databse doesn't have what youre looking for");
            found = false;
        }

            System.out.printf("%10s | %-30s | %-30s | %-40s | %-60s\n", "Id", "Name", "Type", "Game", "Description");
        
            while (rs.next()) {
                int id = rs.getInt(1);
                String name = rs.getString(2);
                String type = rs.getString(4);
                String game = rs.getString(5);
                String description = rs.getString(3);
                System.out.printf("%10s | %-30s | %-30s | %-40s | %-60s\n", id, name, type, game, description);
             }  
        
        
        rs.close();
        stmt.close();
    } catch (Exception e) {
        System.err.println(e.getClass().getName() + ": " + e.getMessage());
    }
        }//while end

    System.out.println("++++++++++++++++++++++++++++++++++");
    return found;
        
}


    private void handleItems(Scanner input, String term) throws SQLException {
        boolean condition = true;
        while(condition){
        
        System.out.println("++++++++++++++++++++++++++++++++++");
        System.out.println("Would You like to start a new list search, make a review, or filter the current list?");
        System.out.println("0 - Return to Table Display");
        System.out.println("1 - Make a review");
        System.out.println("2 - Filter Current List");
        System.out.println("3 - Retrieve other user reviews for a particular item");
        
        while (!input.hasNextInt()){
            System.out.println("Enter 0, 1, 2, or 3");
            input.nextLine();
        }

        int choice = input.nextInt();

        if (choice > 3 || choice < 0){
            System.out.println("ERROR : Not an acceptable input. Try again");
            continue;
        }
            else if (choice == 0){
                System.out.println("Returning to Table Display");
                return;
        }
            else if (choice == 1){
                //System.out.println("Calling review item");
                reviewItem(input, term);
        }
            else if (choice == 2){
                //System.out.println("Calling filter items here");
                filterItems(input, term);

        }   
        else if (choice == 3){
            //System.out.println("Calling reviewsOfItem");
            reviewsOfItem(input, term, 1);
    }

        }//while end
    }



//handle Character
private boolean filterCharacterReviews(Scanner input, String term, int id) throws SQLException {
    boolean condition = true;
    boolean found = true;
        while(condition){

    System.out.println("++++++++++++++++++++++++++++++++++");
    System.out.println("Filter Options");
    System.out.println("Filter applies to r_date, r_score, or a search username");
    System.out.println("0 - cancel ()");
    System.out.println("1 - Date filter (equal to)");
    System.out.println("2 - Score Filter (greater than)");
    System.out.println("3 - Score Filter (less than)");
    System.out.println("4 - Date (equal to) and Score Filter(less than)");
    System.out.println("5 - Date (equal to) and Score Filter(greater than)");
    System.out.println("6 - Search Username");

    //if statement to catch blank case

    while (!input.hasNextInt()){
        System.out.println("Enter 0, 1, 2, 3, 4, 5, or 6");
        input.nextLine();
    }

    int num = input.nextInt();
    String sql = "empty"; //arbitrary initialization
    int first = -1;
    int second = -1;
    String filler = "empty";

    if (num > 6 || num < 0){
        System.out.println("ERROR : Not an acceptable input. Try again");
        continue;}
    else if (num ==0){
        return found;
    }
    else if (num == 1){
        System.out.println("Enter year constraint");

        while (!input.hasNextInt()){
            System.out.println("Enter a number.");
            input.nextLine();}
            
            first = input.nextInt();
    }
    else if (num == 2){
        System.out.println("Enter score greater than constraint");

        while (!input.hasNextInt()){
            System.out.println("Enter a number.");
            input.nextLine();}

            first = input.nextInt();
    }
    else if (num == 3){
        System.out.println("Enter score less than constraint");

        while (!input.hasNextInt()){
            System.out.println("Enter a number.");
            input.nextLine();}

            first = input.nextInt();
    }
    else if (num == 4){
        System.out.println("Enter year constraint");

        while (!input.hasNextInt()){
            System.out.println("Enter a number.");
            input.nextLine();}

            first = input.nextInt();

            System.out.println("Enter score less than constraint");

            while (!input.hasNextInt()){
                System.out.println("Enter a number.");
                input.nextLine();}

            second = input.nextInt();
    }
    else if (num == 5){
        System.out.println("Enter year constraint");

        while (!input.hasNextInt()){
            System.out.println("Enter a number.");
            input.nextLine();}

            first = input.nextInt();

            System.out.println("Enter score greater than constraint");

            while (!input.hasNextInt()){
                System.out.println("Enter a number.");
                input.nextLine();}

            second = input.nextInt();
    }
    else if (num == 6){
        System.out.println("Enter username to search");


            filler = input.nextLine();
            if (filler.isBlank()){
                filler = input.nextLine();
            }
            else{
                first = input.nextInt();
            }

            
    }

    try {


        if (num == 1 ){
            
            sql = "SELECT r_rateID, u_username, c_name, r_date, r_score, r_comment " + 
            "FROM Rates, Users, Characters " +
            "WHERE c_charID  = ? AND " +
            "c_charID = r_charID AND " + 
            "u_userID = r_userID AND " +
            "r_date = ? ";
            
        }
        else if (num == 2){

            sql = "SELECT r_rateID, u_username, c_name, r_date, r_score, r_comment " + 
            "FROM Rates, Users, Characters " +
            "WHERE c_charID  = ? AND " +
            "c_charID = r_charID AND " + 
            "u_userID = r_userID AND " +
            "r_score > ? ";
        }
        else if (num == 3){

            sql = "SELECT r_rateID, u_username, c_name, r_date, r_score, r_comment " + 
            "FROM Rates, Users, Characters " +
            "WHERE c_charID  = ? AND " +
            "c_charID = r_charID AND " + 
            "u_userID = r_userID AND " +
            "r_score < ? ";
        }

        else if (num == 4){
            sql = "SELECT r_rateID, u_username, c_name, r_date, r_score, r_comment " + 
            "FROM Rates, Users, Characters " +
            "WHERE c_charID  = ? AND " +
            "c_charID = r_charID AND " + 
            "u_userID = r_userID AND " +
            "r_date = ? AND " +
            "r_score < ? ";
        }
        else if (num == 5){
            sql = "SELECT r_rateID, u_username, c_name, r_date, r_score, r_comment " + 
            "FROM Rates, Users, Characters " +
            "WHERE c_charID  = ? AND " +
            "c_charID = r_charID AND " + 
            "u_userID = r_userID AND " +
            "r_date = ? AND " +
            "r_score > ? ";
        }
        else if (num == 6){
            sql = "SELECT r_rateID, u_username, c_name, r_date, r_score, r_comment " + 
            "FROM Rates, Users, Characters " +
            "WHERE c_charID  = ? AND " +
            "c_charID = r_charID AND " + 
            "u_userID = r_userID AND " +
            "u_username LIKE ? ";
        }
        //System.out.println("This is sql : " + sql);
        PreparedStatement stmt = c.prepareStatement(sql);
        //System.out.println("Prepared statement made");
        //need to gate second set with if statement
            stmt.setInt(1, id);
        if (num == 1 || num == 2 || num==3 || num == 4 || num == 5 ){
            stmt.setInt(2, first );}
        if (num == 4 || num == 5){
             stmt.setInt(3, second);}
        if (num == 6){
             stmt.setString(2, '%' + filler + '%');
                }
           
           // System.out.println("String set");
        
        ResultSet rs = stmt.executeQuery();
      
        

        System.out.printf("%10s | %-20s | %-20s | %-20s | %-20s | %-90s\n", "RateID", "Username", "Character", "Date", "Score", "Comment");

            if(!rs.isBeforeFirst()){
                System.out.println("Filter resulted in no entries");
                found = false;
            }
        
            while (rs.next()) {
                int rateID = rs.getInt(1);
                String username = rs.getString(2);
                String character = rs.getString(3);
                int date = rs.getInt(4);
                int score = rs.getInt(5);
                String comment = rs.getString(6);
                System.out.printf("%10d | %-20s | %-20s | %-20d | %-20d | %-90s\n", rateID, username, character, date, score, comment);
             }  
       

        
        rs.close();
        stmt.close();
    } catch (Exception e) {
        System.err.println(e.getClass().getName() + ": " + e.getMessage());
    }
        }//while end

    System.out.println("++++++++++++++++++++++++++++++++++");
    return found;
        
}


private boolean reviewsOfCharacter(Scanner input, String term, int switch1) throws SQLException {
    boolean found = true;
    boolean condition = true;
    while(condition){
    
    System.out.println("++++++++++++++++++++++++++++++++++");
    System.out.println("Character Review Information");
    System.out.println("Enter the ID # of the character you would like information on. Or enter 0 to cancel");
    System.out.println("***Note: This works with character ID's not currently on your list so be careful! Max character ID is 55***");
    String sql = "empty"; //arbitrary initialization
    int num = 0;
  

    //if statement to catch blank case
   
    while (!input.hasNextInt()){
        System.out.println("Enter 0 or the ID# of the character you would like to see reviews on!");
        input.nextLine();
    }

    num = input.nextInt();
    
    

    if (num > 55 || num < 0){
        System.out.println("ERROR : Not an acceptable input. Try again");
        continue;}
    else if (num ==0){
        return found;
    }

       

        

        
    
    try {
        
            sql = "SELECT r_rateID, u_username, c_name, r_date, r_score, r_comment " + 
            "FROM Rates, Users, Characters " +
            "WHERE c_charID  = ? AND " +
            "c_charID = r_charID AND " + 
            "u_userID = r_userID";
                
        //System.out.println("This is sql : " + sql);
        PreparedStatement stmt = c.prepareStatement(sql);
        //System.out.println("Prepared statement made");
        //need to gate second set with if statement
            stmt.setInt(1,num);
        
           // System.out.println("String set");
       
        ResultSet rs = stmt.executeQuery();
      
      
            System.out.printf("%10s | %-20s | %-20s | %-20s | %-20s | %-90s\n", "RateID", "Username", "Character", "Date", "Score", "Comment");
        
            if(!rs.isBeforeFirst()){
                System.out.println("No reviews on this character!");
                found = false;
            }
            else{

            while (rs.next()) {
                int rateID = rs.getInt(1);
                String username = rs.getString(2);
                String character = rs.getString(3);
                int date = rs.getInt(4);
                int score = rs.getInt(5);
                String comment = rs.getString(6);
                System.out.printf("%10d | %-20s | %-20s | %-20d | %-20d | %-90s\n", rateID, username, character, date, score, comment);
             }  

             System.out.println("If you would like to filter results, enter 1. Otherwise enter 0.");
             
             while (!input.hasNextInt()){
                System.out.println("Enter 0 or 1");
                input.nextLine();
            }

            int filter = input.nextInt();
            while(filter != 0 && filter != 1){
                System.out.println("Enter 0 or 1");
                filter = input.nextInt();
            }

           if (filter == 1){
                //call filterchar rates here
               // System.out.println("calling rate filter");
                filterCharacterReviews(input, term, num);
            }
           

        }
            

        
        rs.close();
        stmt.close();
    } catch (Exception e) {
        System.err.println(e.getClass().getName() + ": " + e.getMessage());
    }

    
        System.out.println("Resetting.....");
        }
    System.out.println("++++++++++++++++++++++++++++++++++");
    return found;

}


private int retrieveDate() {
    LocalDateTime now = java.time.LocalDateTime.now();
    int year = now.getYear();
    System.out.println("This is the year : " + year);
   return year;
}

private int retrieveUserID() {
    System.out.println("++++++++++++++++++++++++++++++++++");
    System.out.println("Retrieving User ID");
    int max = -1;

    try {
                 
        String sql = "SELECT u_userID " + 
        "FROM Users " +
        "WHERE u_username = ? ";

    
    PreparedStatement stmt = c.prepareStatement(sql);
    
        stmt.setString(1,userLoggedIn);
    
    ResultSet rs = stmt.executeQuery();
       
        while (rs.next()) {
            max = rs.getInt(1);
            System.out.println("This is the user ID # : " + max);
        }  
        rs.close();
        stmt.close();
    } catch (Exception e) {
        System.err.println(e.getClass().getName() + ": " + e.getMessage());
    }

    System.out.println("++++++++++++++++++++++++++++++++++");
    return max;
    
}

private int newRateID() {
    System.out.println("++++++++++++++++++++++++++++++++++");
    System.out.println("Creating New Rate ID");
    int max = -1;

    try {
                 
        Statement stmt = c.createStatement();
        
        String sql = "SELECT max(r_rateID) " + 
        "FROM Rates ";
        
        stmt.execute(sql);
        ResultSet rs = stmt.getResultSet();
       
        while (rs.next()) {
            max = rs.getInt(1);
            System.out.println("This is the max rate ID # : " + max);
        }  
        rs.close();
        stmt.close();
    } catch (Exception e) {
        System.err.println(e.getClass().getName() + ": " + e.getMessage());
    }

    System.out.println("++++++++++++++++++++++++++++++++++");
    return max;
    
}

private void reviewCharacter(Scanner input, String term) throws SQLException {
    
    boolean condition = true;
    while(condition){
    
    System.out.println("++++++++++++++++++++++++++++++++++");
    System.out.println("Review Character Menu");
    System.out.println("Enter the ID # of the character you would like to review. Or enter 0 to cancel");
    System.out.println("***Note: This works with character ID's not currently on your list so be careful! Max character ID is 55***");
    
  

    //if statement to catch blank case

    while (!input.hasNextInt()){
        System.out.println("Enter 0 to cancel or the ID# of the character you would like to view!");
        input.nextLine();
    }

    int num = input.nextInt();
    if (num > 55 || num < 0 ){
        System.out.println("ERROR : Not an acceptable input. Try again");
        continue;}
   else if (num == 0 ){
        return;
    }

    System.out.println("Enter the score for character with ID #" + num + ". Score range is between 1 and 100.");
    System.out.println("If this is the wrong is ID, enter 0 to reset.");

    while (!input.hasNextInt()){
        System.out.println("Enter 0 or the score value!");
        input.nextLine();
    }

    int score = input.nextInt();


    

    if ( score < 0 || score > 100){
        System.out.println("ERROR : Not an acceptable input. Try again");
        continue;}
    else if (num == 0 || score == 0){
        continue;
    }
    else{

        System.out.println("Enter your review of the character.");
        String comment = input.nextLine();
        if (comment.isBlank()){
            comment = input.nextLine();
        }
    

        int userID = retrieveUserID();
        int max = newRateID();
        int r_rateID = max + 1;
        int date = retrieveDate();
        
    try {

        String sql = "INSERT INTO Rates VALUES(?, ?, NULL, ?, NULL, 'character', ?, ?, ?)";

        //System.out.println("This is sql : " + sql);
        PreparedStatement stmt = c.prepareStatement(sql);
        //System.out.println("Prepared statement made");
        //need to gate second set with if statement
            stmt.setInt(1,r_rateID);
            stmt.setInt(2,userID);
            stmt.setInt(3,num);
            stmt.setInt(4,date);
            stmt.setInt(5,score);
            stmt.setString(6,comment);
           // System.out.println("String set");
       
         // STEP: Execute batch
         stmt.executeUpdate();

         // STEP: Commit transaction
         c.commit();
 
         stmt.close();
         System.out.println("success");
      

        stmt.close();
    } catch (Exception e) {
        System.err.println(e.getClass().getName() + ": " + e.getMessage());
    }

    }
        System.out.println("Resetting.....");
        }
    System.out.println("++++++++++++++++++++++++++++++++++");
    return ;

}

private boolean gamesOfCharacter(Scanner input, String term) throws SQLException {
    boolean found = true;
    boolean condition = true;
    while(condition){
    
    System.out.println("++++++++++++++++++++++++++++++++++");
    System.out.println("Character Game Information");
    System.out.println("Enter the ID # of the character you would like information on. Or enter 0 to cancel");
    System.out.println("***Note: This works with character ID's not currently on your list so be careful! Max character ID is 55***");
    
  

    //if statement to catch blank case

    while (!input.hasNextInt()){
        System.out.println("Enter 0 or the ID# of the character you would like to view!");
        input.nextLine();
    }

    int num = input.nextInt();
    String sql = "empty"; //arbitrary initialization
    

    if (num > 55 || num < 0){
        System.out.println("ERROR : Not an acceptable input. Try again");
        continue;}
    else if (num ==0){
        return found;
    }
    else{


    
    try {

            sql = "SELECT c_name, g_name, g_year, g_genre, g_developer " + 
            "FROM Characters, CharacterGames, Games " +
            "WHERE c_charID  = ? AND " +
            "c_charID = cg_charID AND " + 
            "cg_gameID = g_gameID";

        //System.out.println("This is sql : " + sql);
        PreparedStatement stmt = c.prepareStatement(sql);
        //System.out.println("Prepared statement made");
        //need to gate second set with if statement
            stmt.setInt(1,num);
           // System.out.println("String set");
       
        ResultSet rs = stmt.executeQuery();
      
        if(!rs.isBeforeFirst()){
            //System.out.println("My guy, our databse doesn't have what youre looking for");
            found = false;
        }

            System.out.printf("%10s | %-80s | %-20s | %-20s | %-40s\n", "Character", "Game", "Year", "Genre", "Developer");
        
            while (rs.next()) {
                String c_name = rs.getString(1);
                String game = rs.getString(2);
                int year = rs.getInt(3);
                String genre = rs.getString(4);
                String developer = rs.getString(5);
                System.out.printf("%10s | %-80s | %-20d | %-20s | %-40s\n", c_name, game, year, genre, developer);
             }  
        
       

        
        rs.close();
        stmt.close();
    } catch (Exception e) {
        System.err.println(e.getClass().getName() + ": " + e.getMessage());
    }

    }
        System.out.println("Resetting.....");
        }
    System.out.println("++++++++++++++++++++++++++++++++++");
    return found;

}

private boolean filterCharacters(Scanner input, String term) throws SQLException {
    boolean condition = true;
    boolean found = true;
        while(condition){

    System.out.println("++++++++++++++++++++++++++++++++++");
    System.out.println("Filter Options");
    System.out.println("Filter applies to c_size");
    System.out.println("0 - cancel ()");
    System.out.println("1 - Less than filter");
    System.out.println("2 - Greater than filter");
    System.out.println("3 - Range (less than and greater than filter)");
    System.out.println("4 - Equal");

    //if statement to catch blank case

    while (!input.hasNextInt()){
        System.out.println("Enter 0, 1, 2, 3, or 4");
        input.nextLine();
    }

    int num = input.nextInt();
    String sql = "empty"; //arbitrary initialization
    int first = -1;
    int second = -1;

    if (num > 4 || num < 0){
        System.out.println("ERROR : Not an acceptable input. Try again");
        continue;}
    else if (num ==0){
        return found;
    }
    else if (num == 1){
        System.out.println("Enter c_size less than constraint");

        while (!input.hasNextInt()){
            System.out.println("Enter a number.");
            input.nextLine();}
            
            first = input.nextInt();
    }
    else if (num == 2){
        System.out.println("Enter c_size greater than constraint");

        while (!input.hasNextInt()){
            System.out.println("Enter a number.");
            input.nextLine();}

            first = input.nextInt();
    }
    else if (num == 3){
        System.out.println("Enter c_size less than constraint");

        while (!input.hasNextInt()){
            System.out.println("Enter a number.");
            input.nextLine();}

            first = input.nextInt();

            System.out.println("Enter c_size greater than constraint");

            second = input.nextInt();
    }
    else if (num == 4){
        System.out.println("Enter c_size equal to constraint");

        while (!input.hasNextInt()){
            System.out.println("Enter a number.");
            input.nextLine();}

            first = input.nextInt();
    }

    try {


        if (num == 1 ){
            
            sql = "SELECT c_charID, c_name, c_size " + 
            "FROM Characters " +
            "WHERE c_name  LIKE ? AND " +
            "c_size < ?";

            
        }
        else if (num == 2){

            sql = "SELECT c_charID, c_name, c_size " + 
            "FROM Characters " +
            "WHERE c_name  LIKE ? AND " +
            "c_size > ?";
        }
        else if (num == 3){

            sql = "SELECT c_charID, c_name, c_size " + 
            "FROM Characters " +
            "WHERE c_name  LIKE ? AND " +
            "c_size < ? AND c_size > ?";
        }
        else if (num == 4){
            sql = "SELECT c_charID, c_name, c_size " + 
            "FROM Characters " +
            "WHERE c_name  LIKE ? AND " +
            "c_size = ?";
        }
        //System.out.println("This is sql : " + sql);
        PreparedStatement stmt = c.prepareStatement(sql);
        //System.out.println("Prepared statement made");
        //need to gate second set with if statement
            stmt.setString(1, '%' + term + '%');
            stmt.setInt(2, first);
           // System.out.println("String set");
        if (num == 3){
            stmt.setInt(3, second);
        }
        ResultSet rs = stmt.executeQuery();
      
        if(!rs.isBeforeFirst()){
            //System.out.println("My guy, our databse doesn't have what youre looking for");
            found = false;
        }

            System.out.printf("%10s | %-20s | %10s\n", "Id", "Name", "Size");
        
            while (rs.next()) {
                int id = rs.getInt(1);
                String name = rs.getString(2);
                int size = rs.getInt(3);
                System.out.printf("%10d | %-20s | %10d\n", id, name, size);
             }  
        
       

        
        rs.close();
        stmt.close();
    } catch (Exception e) {
        System.err.println(e.getClass().getName() + ": " + e.getMessage());
    }
        }//while end

    System.out.println("++++++++++++++++++++++++++++++++++");
    return found;
        
}


    private void handleCharacters(Scanner input, String term) throws SQLException {
        boolean condition = true;
        while(condition){
        
        System.out.println("++++++++++++++++++++++++++++++++++");
        System.out.println("Would You like to start a new list search, make a review, or filter the current list?");
        System.out.println("0 - Return to Table Display");
        System.out.println("1 - Make a review");
        System.out.println("2 - Filter Current List");
        System.out.println("3 - Retrieve reference game information for a particular character");
        System.out.println("4 - Retrieve other user reviews for a particular character");
        
        while (!input.hasNextInt()){
            System.out.println("Enter 0, 1, 2, 3, or 4");
            input.nextLine();
        }

        int choice = input.nextInt();

        if (choice > 4 || choice < 0){
            System.out.println("ERROR : Not an acceptable input. Try again");
            continue;
        }
            else if (choice == 0){
                //System.out.println("Returning to Table Display");
                return;
        }
            else if (choice == 1){
               // System.out.println("Calling review character");
                reviewCharacter(input, term);
        }
            else if (choice == 2){
               // System.out.println("Calling filter characters here");
                filterCharacters(input, term);

        }
            else if (choice == 3){
              //  System.out.println("Calling gamesOfCharacter");
                gamesOfCharacter(input, term);
        }
        else if (choice == 4){
           // System.out.println("Calling reviewsOfCharacter");
            reviewsOfCharacter(input, term, 1);
    }

        }//while end
    }

    private boolean searchTerm(Scanner input, String table) throws SQLException {
        //NEED TO ADJUST PRINT STATEMENT FORMATTING
        System.out.println("++++++++++++++++++++++++++++++++++");
        System.out.println("Please enter a search term");
        //if statement to catch blank case
        String term = input.nextLine();
        if (term.isBlank()){
            term = input.nextLine();
        }
        termGlobal = term;
        String sql = "empty"; //arbitrary initialization
        boolean found = true;
        try {


            if (table == "Characters"){
                
                sql = "SELECT c_charID, c_name, c_size " + 
                "FROM Characters " +
                "WHERE c_name  LIKE ? ";

                //System.out.println("sql Character switch activated");
            }
            else if (table == "Stages"){
                sql = "SELECT s_stageID, s_name, g_name, s_size, s_type " + 
                "FROM Stages, Games " +
                "WHERE s_name  LIKE ? AND " +
                "s_gameID = g_gameID ";
            }
            else if (table == "Items"){
                sql = "SELECT i_itemID, i_name, i_desc, i_type, g_name " + 
                "FROM Items, Games " +
                "WHERE (i_name  LIKE ? OR " +
                "i_desc  LIKE ? ) AND " +
                "i_gameID = g_gameID ";
            }
    
            //System.out.println("This is sql : " + sql);
            PreparedStatement stmt = c.prepareStatement(sql);
            //System.out.println("Prepared statement made");
            //need to gate second set with if statement
                stmt.setString(1, '%' + term + '%');
               // System.out.println("String set");
            if (table == "Items" || table == "Music"){
                stmt.setString(2, '%' + term + '%');
            }
            ResultSet rs = stmt.executeQuery();
          
            if(!rs.isBeforeFirst()){
                //System.out.println("My guy, our databse doesn't have what youre looking for");
                found = false;
            }

            if (table == "Characters"){
                System.out.printf("%10s | %-20s | %10s\n", "Id", "Name", "Size");
            
                while (rs.next()) {
                    int id = rs.getInt(1);
                    String name = rs.getString(2);
                    int size = rs.getInt(3);
                    System.out.printf("%10d | %-20s | %10d\n", id, name, size);
                 }  
            }
            else if (table == "Stages"){
                System.out.printf("%10s | %-40s | %-40s | %-20s | %-20s\n", "Id", "Name", "Game","Size","Type");
            
                while (rs.next()) {
                    int id = rs.getInt(1);
                    String name = rs.getString(2);
                    String game = rs.getString(3);
                    String size = rs.getString(4);
                    String type = rs.getString(5);
                    System.out.printf("%10d | %-40s | %-40s | %-20s | %-20s\n", id, name, game,size,type);
                 }  
            }
            else if (table == "Items"){
                System.out.printf("%10s | %-20s | %-20s | %-30s | %-140s\n", "Id", "Name","Type","Game", "Desc");
            
                while (rs.next()) {
                    int id = rs.getInt(1);
                    String name = rs.getString(2);
                    String desc = rs.getString(3);
                    String type = rs.getString(4);
                    String game = rs.getString(5);
                    System.out.printf("%10d | %-20s | %-20s | %-30s | %-140s\n", id, name,type,game,desc.substring(0,45));
                 }  
            }            
            

            
            rs.close();
            stmt.close();
        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
        }

        System.out.println("++++++++++++++++++++++++++++++++++");
        return found;
    }


    private void tableDisplay(Scanner input, String table) throws SQLException {
        boolean condition = true;
        boolean con2 = true;
        while(condition){
        
        System.out.println("Moving on to display interface. Confirmation needed");
        System.out.println("0 - Cancel (return to database operations)");
        System.out.println("1 - continue");
       
        

        while (!input.hasNextInt()){
            System.out.println("Enter 0 or 1 ");
            input.nextLine();
        }

        int choice = input.nextInt();
       
        //System.out.println("This is the user input : " + choice);
       
        if (choice > 1 || choice < 0){
        System.out.println("ERROR : Not an acceptable input. Try again");
        continue;
    }
        else if (choice == 0){
            //System.out.println("Returning to database operations");
            return;
    }


        while (con2 == true){
            System.out.println("This is where I call searchTerm : ");
            System.out.println("To view the full list, hit enter without typing anything. Or seach for entries by typing a key word before hitting enter.");
            boolean searchAgain = true;
            boolean rList = searchTerm(input, table);
            System.out.println("This is rList : " + rList);
            if (rList == false) {    
                while (searchAgain){
                    System.out.println("No results for this search."); 
                    System.out.println("Would you like to search this category again? Enter 1 for yes and 0 to return to Database Operations"); 
                    while (!input.hasNextInt()){
                        System.out.println("Enter 0 or 1");
                        input.nextLine(); }
                    int choiceSearch = input.nextInt();
                    if (choiceSearch > 1 || choiceSearch < 0){
                            System.out.println("ERROR : Not an acceptable input. Try again");
                            continue;
                        }
                     else if (choiceSearch == 0){
                            //System.out.println("Returning to Database Operations");
                            searchAgain = false;
                            return;
                        }
                    
                    else if (choiceSearch == 1){
                                //System.out.println("Calling searchTerm again : ");
                                    break; 
                        }
        
               
                }// end of no results while loop
                break;
            }//end of if no results condition
            //databaseOp(input);
            else{
            if (table == "Characters"){
                //System.out.println("Calling handleCharacters");
               // System.out.println("This is termGlobal : " + termGlobal);
                handleCharacters(input, termGlobal);
            }
            else if (table == "Stages"){
               // System.out.println("Calling handleStages");
                handleStages(input, termGlobal);
            }
            else if (table == "Items"){
               // System.out.println("Calling handleItems");
                handleItems(input, termGlobal);
            }
           
            break;
                }//else end 

    }
        }
        
    }


    private void databaseOp(Scanner input) throws SQLException {

        boolean condition = true;
        while(condition){

        System.out.println("Database Operations");
        System.out.println("Choose a category to view by entering corresponding number");
        System.out.println("0 - Cancel (return to utility options)");
        System.out.println("1 - Characters");
        System.out.println("2 - Stages");
        System.out.println("3 - Items");
        String category;

        while (!input.hasNextInt()){
            System.out.println("Enter 0 , 1 , 2 , or 3");
            input.nextLine();
        }

        int choice = input.nextInt();
       
        //System.out.println("This is the user input : " + choice);
       
        if (choice > 3 || choice < 0){
        System.out.println("ERROR : Not an acceptable input. Try again");
        continue;
    }
        else if (choice == 0){
            System.out.println("Returning to utility options");
            return;
    }

        else if (choice == 1){
           // System.out.println("This is where I call tableDisplay(Characters) : ");
            category = "Characters";
            tableDisplay(input, category);
    }

        else if (choice == 2){
           // System.out.println("This is where I call tableDisplay(Stages) : ");
            category = "Stages";
            tableDisplay(input, category);
    }
   
        else if (choice == 3){
          //  System.out.println("This is where I call tableDisplay(Items) : ");
            category = "Items";
            tableDisplay(input, category);
    }


    }//while loop end


    }

    private void updateAccount(String keyword, int type) throws SQLException {
        System.out.println("++++++++++++++++++++++++++++++++++");
        System.out.println("Updating Account....");
        String sql = "empty";
    
        try {
            if (type ==1){
                sql = "UPDATE Users SET u_username = ? WHERE u_username = ?";
                
            }
            else if(type == 2){
                sql = "UPDATE Users SET u_password = ? WHERE u_username = ?";
            }
            else if(type == 3){
                sql = "UPDATE Users SET u_email = ? WHERE u_username = ?";
            }

            

            PreparedStatement stmt = c.prepareStatement(sql);
    
            stmt.setString(1, keyword);
            stmt.setString(2, userLoggedIn);
        
            // STEP: Execute batch
            stmt.executeUpdate();
    
            // STEP: Commit transaction
            c.commit();
            if (type == 1){
            userLoggedIn = keyword;}
            stmt.close();
            System.out.println("success");
        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            try {
                c.rollback();
            } catch (Exception e1) {
                System.err.println(e1.getClass().getName() + ": " + e1.getMessage());
            }
        }
    }

    private void deleteAllreviews() throws SQLException {
        System.out.println("++++++++++++++++++++++++++++++++++");
        System.out.println("Deleting Account....");
        
    
        try {
           
            String sql = "DELETE FROM Rates  WHERE r_userID = ? ";
                
            
          

            

            PreparedStatement stmt = c.prepareStatement(sql);
    
            int userID = retrieveUserID();
            stmt.setInt(1, userID);
           

             // STEP: Execute batch
             stmt.executeUpdate();
    
             // STEP: Commit transaction
             c.commit();

            stmt.close();
            System.out.println("success");
        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            try {
                c.rollback();
            } catch (Exception e1) {
                System.err.println(e1.getClass().getName() + ": " + e1.getMessage());
            }
        }
    }

    private void accountDeletion() throws SQLException {
        System.out.println("++++++++++++++++++++++++++++++++++");
        System.out.println("Deleting Account....");
        deleteAllreviews();
    
        try {
           
               String sql = "DELETE FROM Users  WHERE u_username = ?";
                
            
          

            

            PreparedStatement stmt = c.prepareStatement(sql);
    
            stmt.setString(1, userLoggedIn);
        
            // STEP: Execute batch
            stmt.executeUpdate();
    
            // STEP: Commit transaction
            c.commit();
          

            sql = "DELETE FROM Rates  WHERE r_userID = ? ";
            stmt = c.prepareStatement(sql);

            int userID = retrieveUserID();
            stmt.setInt(1, userID);

             // STEP: Execute batch
             stmt.executeUpdate();
    
             // STEP: Commit transaction
             c.commit();

            stmt.close();
            System.out.println("success");
        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            try {
                c.rollback();
            } catch (Exception e1) {
                System.err.println(e1.getClass().getName() + ": " + e1.getMessage());
            }
        }
    }

    private void deleteAccount(Scanner input) throws SQLException {
        
        System.out.println("Delete Account Window");
        System.out.println("This will delete your account");
        
        
        boolean condition = true;
        while(condition){

        
        System.out.println("0 - Return to My Account Operations");
        System.out.println("1 - continue to deletion");
        
        

        while (!input.hasNextInt()){
            System.out.println("Enter 0 or 1");
            input.nextLine();
        }

        int choice = input.nextInt();
       
        //System.out.println("This is the user input : ");
       
        if (choice > 1 || choice < 0){
        System.out.println("ERROR : Not an acceptable input. Try again");
        continue;
    }
        else if (choice == 0){
            //System.out.println("Returning to My Account operations ");
            return;
    }

    else if (choice == 1){
        boolean con2 = true;
        while(con2){
        System.out.println("Enter password : ");
        String pass = input.nextLine();
        if (pass.isBlank()){
            pass = input.nextLine();
        }

        boolean allowed = authenication(userLoggedIn, pass);
        if(allowed == false || pass.isBlank()){
        System.out.println("Password is false : ");}
        else{

            System.out.println("0 - cancel");
            System.out.println("1 - confirm deletion");
            
            
    
            while (!input.hasNextInt()){
                System.out.println("Enter 0 or 1");
                input.nextLine(); }
    
            int choice2 = input.nextInt();
           
            //System.out.println("This is the user input : " + choice2);
           
            if (choice2 > 1 || choice2 < 0){
            System.out.println("ERROR : Not an acceptable input. Reseting...");}
            else if (choice == 0){
                System.out.println("Cancelling");}
            else if (choice == 1){
                accountDeletion();
                System.out.println("Your account has been deleted. Re-launch for reuse.");
                System.exit(0);}

           
         }
       
    }//while con2 end
            }
        }   
    }
    

    private void editAccount(Scanner input) throws SQLException {
        
        System.out.println("My Account Operations");
        System.out.println("This will cycle through editing options");
        System.out.println("Cycle order = username - password - email ");
        System.out.println("***Note:Edits are done one at a time***");
        System.out.println("Enter nummber corresponding to desired operation. ");
        
        boolean condition = true;
        while(condition){

        
        System.out.println("0 - Return to My Account Operations");
        System.out.println("1 - Edit username");
        System.out.println("2 - Move onto editing password");
        
       
        

        while (!input.hasNextInt()){
            System.out.println("Enter 0 , 1 , or 2");
            input.nextLine();
        }

        int choice = input.nextInt();
       
        //System.out.println("This is the user input : " + choice);
       
        if (choice > 2 || choice < 0){
        System.out.println("ERROR : Not an acceptable input. Try again");
        continue;
    }
        else if (choice == 0){
           // System.out.println("Returning to My Account operations ");
            return;
    }

    else if (choice == 1){
        boolean con2 = true;
        while(con2){
        System.out.println("Enter new desired username : ");
        String username = input.nextLine();
        if (username.isBlank()){
            username = input.nextLine();
        }

        boolean available = checkUsername(username);
        if(available == true || username.isBlank()){
        System.out.println("Invalid username or already taken : ");}
        else{
            updateAccount(username,1);
         }
        System.out.println("Repeat Operatiion? Enter 1 to repeat or 0 to move on. " );

        while (!input.hasNextInt()){
        System.out.println("Enter 0  or 1");
        input.nextLine();
        }

        int val = input.nextInt();
   
        //System.out.println("This is the user input : " + choice);
   
        if (val > 1 || val < 0){
        System.out.println("ERROR : Not an acceptable input. Operation is resetting...");
        continue;}
        else if(val == 1){continue;}
        else if(val == 0){break;}
    }//while con2 end
            
    } // if choice = 1 end
    

          
        System.out.println("0 - Return to My Account Operations");
        System.out.println("1 - Edit password");
        System.out.println("2 - Move onto editing email");

        while (!input.hasNextInt()){
            System.out.println("Enter 0 , 1 , or 2");
            input.nextLine();
        }

        int choice2 = input.nextInt();
       
        //System.out.println("This is the user input : " + choice);
       
        if (choice2 > 2 || choice2 < 0){
        System.out.println("ERROR : Not an acceptable input. Resetting...");
        continue;
    }

    else if (choice2 == 0){
        //System.out.println("Returning to My Account operations ");
        return;
}

else if (choice2 == 1){

        boolean con2 = true;
        
       
        while(con2){
        System.out.println("Enter new desired password : ");
        String pass = input.nextLine();
        if (pass.isBlank()){
            pass = input.nextLine();
        }

        if(pass.isBlank()){
        System.out.println("Password is empty! Resetting... : ");
        break;}

        System.out.println("Confirm new pass by re-entering : ");
        String pass2 = input.nextLine();
        if (pass2.isBlank()){
            pass2 = input.nextLine();
        }

        if( !pass2.equals(pass) ){
        System.out.println("Passwords do not match... : ");}
        else{
            updateAccount(pass,2);
         }
        System.out.println("Repeat Operatiion? Enter 1 to repeat or 0 to move on. " );

        while (!input.hasNextInt()){
        System.out.println("Enter 0  or 1");
        input.nextLine();
        }

        int val = input.nextInt();
   
        //System.out.println("This is the user input : " + choice);
   
        if (val > 1 || val < 0){
            System.out.println("ERROR : Not an acceptable input. Operation is resetting...");
            continue;}
            else if(val == 1){continue;}
            else if(val == 0){break;}
    }//while con2 end
                    }
    

                    System.out.println("0 - Return to My Account Operations");
                    System.out.println("1 - Edit email");
                    System.out.println("2 - Move onto editing username");
            
                    while (!input.hasNextInt()){
                        System.out.println("Enter 0 , 1 , or 2");
                        input.nextLine();
                    }
            
                    int choice3 = input.nextInt();
                   
                    //System.out.println("This is the user input : " + choice);
                   
                    if (choice3 > 2 || choice3 < 0){
                    System.out.println("ERROR : Not an acceptable input. Resetting...");
                    continue;
                }
            
                else if (choice3 == 0){
                    System.out.println("Returning to My Account operations ");
                    return;
            }
            
            else if (choice3 == 1){
            
                    boolean con2 = true;
                    while(con2){
                    System.out.println("Enter new desired email : ");
                    String pass = input.nextLine();
                    if (pass.isBlank()){
                        pass = input.nextLine();
                    }
            
                    if(pass.isBlank()){
                    System.out.println("email is empty! Resetting... : ");
                    continue;}
            
                    System.out.println("Confirm new email by re-entering : " );
                    String pass2 = input.nextLine();
                    if (pass2.isBlank()){
                        pass2 = input.nextLine();
                    }
            
                    if( !pass2.equals(pass) ){
                    System.out.println("emails do not match... : ");}
                    else{
                        updateAccount(pass,3);
                     }
                    System.out.println("Repeat Operatiion? Enter 1 to repeat or 0 to move on. " );
            
                    while (!input.hasNextInt()){
                    System.out.println("Enter 0  or 1");
                    input.nextLine();
                    }
            
                    int val = input.nextInt();
               
                    //System.out.println("This is the user input : " + choice);
               
                    if (val > 1 || val < 0){
                        System.out.println("ERROR : Not an acceptable input. Operation is resetting...");
                        continue;}
                        else if(val == 1){continue;}
                        else if(val == 0){break;}
                }//while con2 end
                                }      


    }//while loop end
    
} 


    private void myAccount(Scanner input) throws SQLException {
        boolean condition = true;
        while(condition){

        System.out.println("My Account Operations");
        System.out.println("0 - Return to Utility Interface");
        System.out.println("1 - Edit account Information");
        System.out.println("2 - Delete account information");
       
        

        while (!input.hasNextInt()){
            System.out.println("Enter 0 , 1 , or 2");
            input.nextLine();
        }

        int choice = input.nextInt();
       
        //System.out.println("This is the user input : " + choice);
       
        if (choice > 2 || choice < 0){
        System.out.println("ERROR : Not an acceptable input. Try again");
        continue;
    }
        else if (choice == 0){
            //System.out.println("Returning to Utility Inteface ");
            
            return;
    }

        else if (choice == 1){
            //System.out.println("Calling Account editor : ");
            editAccount(input);
    }

        else if (choice == 2){
            //System.out.println("Calling Account Deletion : ");
            deleteAccount(input);
    }
   
        }//while loop end
        
    } 


    private boolean filterMyReviews(Scanner input) throws SQLException {
        boolean condition = true;
        boolean found = true;
            while(condition){
    
        System.out.println("++++++++++++++++++++++++++++++++++");
        System.out.println("Filter Options");
        System.out.println("Filter applies to r_date, r_score, or a search username");
        System.out.println("0 - cancel ()");
        System.out.println("1 - Date filter (equal to)");
        System.out.println("2 - Score Filter (greater than)");
        System.out.println("3 - Score Filter (less than)");
        System.out.println("4 - Date (equal to) and Score Filter(less than)");
        System.out.println("5 - Date (equal to) and Score Filter(greater than)");
        
    
        //if statement to catch blank case
    
        while (!input.hasNextInt()){
            System.out.println("Enter 0, 1, 2, 3, 4, or 5");
            input.nextLine();
        }
    
        int num = input.nextInt();
        String sql = "empty"; //arbitrary initialization
        int first = -1;
        int second = -1;
       
    
        if (num > 5 || num < 0){
            System.out.println("ERROR : Not an acceptable input. Try again");
            continue;}
        else if (num ==0){
            return found;
        }
        else if (num == 1){
            System.out.println("Enter year constraint");
    
            while (!input.hasNextInt()){
                System.out.println("Enter a number.");
                input.nextLine();}
                
                first = input.nextInt();
        }
        else if (num == 2){
            System.out.println("Enter score greater than constraint");
    
            while (!input.hasNextInt()){
                System.out.println("Enter a number.");
                input.nextLine();}
    
                first = input.nextInt();
        }
        else if (num == 3){
            System.out.println("Enter score less than constraint");
    
            while (!input.hasNextInt()){
                System.out.println("Enter a number.");
                input.nextLine();}
    
                first = input.nextInt();
        }
        else if (num == 4){
            System.out.println("Enter year constraint");
    
            while (!input.hasNextInt()){
                System.out.println("Enter a number.");
                input.nextLine();}
    
                first = input.nextInt();
    
                System.out.println("Enter score less than constraint");
    
                while (!input.hasNextInt()){
                    System.out.println("Enter a number.");
                    input.nextLine();}
    
                second = input.nextInt();
        }
        else if (num == 5){
            System.out.println("Enter year constraint");
    
            while (!input.hasNextInt()){
                System.out.println("Enter a number.");
                input.nextLine();}
    
                first = input.nextInt();
    
                System.out.println("Enter score greater than constraint");
    
                while (!input.hasNextInt()){
                    System.out.println("Enter a number.");
                    input.nextLine();}
    
                second = input.nextInt();
        }
    
        try {
    
    
            if (num == 1 ){
                
            sql = "SELECT r_rateID, u_username, c_name, r_date, r_score, r_comment " + 
            "FROM Rates, Users, Characters " +
            "WHERE u_username  = ? AND " +
            "c_charID = r_charID AND " + 
            "u_userID = r_userID AND " +
            "r_date = ? " +
            "UNION " +
            "SELECT r_rateID, u_username, i_name, r_date, r_score, r_comment " + 
            "FROM Rates, Items, Users " +
            "WHERE u_username  = ? AND " +
            "i_itemID = r_itemID AND " + 
            "u_userID = r_userID AND " + 
            "r_date = ? " +
            "UNION " +
            "SELECT r_rateID, u_username, s_name, r_date, r_score, r_comment " + 
            "FROM Rates, Users, Stages " +
            "WHERE u_username  = ? AND " +
            "s_stageID = r_stageID AND " + 
            "u_userID = r_userID AND " +
            "r_date = ? ";
                
            }
            else if (num == 2){
    
                sql = "SELECT r_rateID, u_username, c_name, r_date, r_score, r_comment " + 
                "FROM Rates, Users, Characters " +
                "WHERE u_username  = ? AND " +
                "c_charID = r_charID AND " + 
                "u_userID = r_userID AND " +
                "r_score > ? " +
                "UNION " +
                "SELECT r_rateID, u_username, i_name, r_date, r_score, r_comment " + 
                "FROM Rates, Items, Users " +
                "WHERE u_username  = ? AND " +
                "i_itemID = r_itemID AND " + 
                "u_userID = r_userID AND " + 
                "r_score > ? " +
                "UNION " +
                "SELECT r_rateID, u_username, s_name, r_date, r_score, r_comment " + 
                "FROM Rates, Users, Stages " +
                "WHERE u_username  = ? AND " +
                "s_stageID = r_stageID AND " + 
                "u_userID = r_userID AND " +
                "r_score > ? ";
            }
            else if (num == 3){
    
                sql = "SELECT r_rateID, u_username, c_name, r_date, r_score, r_comment " + 
                "FROM Rates, Users, Characters " +
                "WHERE u_username  = ? AND " +
                "c_charID = r_charID AND " + 
                "u_userID = r_userID AND " +
                "r_score < ? " +
                "UNION " +
                "SELECT r_rateID, u_username, i_name, r_date, r_score, r_comment " + 
                "FROM Rates, Items, Users " +
                "WHERE u_username  = ? AND " +
                "i_itemID = r_itemID AND " + 
                "u_userID = r_userID AND " + 
                "r_score < ? " +
                "UNION " +
                "SELECT r_rateID, u_username, s_name, r_date, r_score, r_comment " + 
                "FROM Rates, Users, Stages " +
                "WHERE u_username  = ? AND " +
                "s_stageID = r_stageID AND " + 
                "u_userID = r_userID AND " +
                "r_score < ? ";
            }
    
            else if (num == 4){
                sql = "SELECT r_rateID, u_username, c_name, r_date, r_score, r_comment " + 
                "FROM Rates, Users, Characters " +
                "WHERE u_username  = ? AND " +
                "c_charID = r_charID AND " + 
                "u_userID = r_userID AND " +
                "r_date = ? AND " +
                "r_score < ? " +
                "UNION " +
                "SELECT r_rateID, u_username, i_name, r_date, r_score, r_comment " + 
                "FROM Rates, Items, Users " +
                "WHERE u_username  = ? AND " +
                "i_itemID = r_itemID AND " + 
                "u_userID = r_userID AND " + 
                "r_date = ? AND " +
                "r_score < ? " +
                "UNION " +
                "SELECT r_rateID, u_username, s_name, r_date, r_score, r_comment " + 
                "FROM Rates, Users, Stages " +
                "WHERE u_username  = ? AND " +
                "s_stageID = r_stageID AND " + 
                "u_userID = r_userID AND " +
                "r_date = ? AND " +
                "r_score < ? ";
            }
            else if (num == 5){
                sql = "SELECT r_rateID, u_username, c_name, r_date, r_score, r_comment " + 
                "FROM Rates, Users, Characters " +
                "WHERE u_username  = ? AND " +
                "c_charID = r_charID AND " + 
                "u_userID = r_userID AND " +
                "r_date = ? AND " +
                "r_score > ? " +
                "UNION " +
                "SELECT r_rateID, u_username, i_name, r_date, r_score, r_comment " + 
                "FROM Rates, Items, Users " +
                "WHERE u_username  = ? AND " +
                "i_itemID = r_itemID AND " + 
                "u_userID = r_userID AND " + 
                "r_date = ? AND " +
                "r_score > ? " +
                "UNION " +
                "SELECT r_rateID, u_username, s_name, r_date, r_score, r_comment " + 
                "FROM Rates, Users, Stages " +
                "WHERE u_username  = ? AND " +
                "s_stageID = r_stageID AND " + 
                "u_userID = r_userID AND " +
                "r_date = ? AND " +
                "r_score > ? ";
            }
            //System.out.println("This is sql : " + sql);
            PreparedStatement stmt = c.prepareStatement(sql);
            //System.out.println("Prepared statement made");
            //need to gate second set with if statement
               
            if (num == 1 || num == 2 || num==3 ){
                stmt.setString(1, userLoggedIn);
                stmt.setInt(2, first );
                stmt.setString(3, userLoggedIn);
                stmt.setInt(4, first );
                stmt.setString(5, userLoggedIn);
                stmt.setInt(6, first );}
            if (num == 4 || num == 5){
                stmt.setString(1, userLoggedIn);
                stmt.setInt(2, first );
                stmt.setInt(3, second );
                stmt.setString(4, userLoggedIn);
                stmt.setInt(5, first );
                stmt.setInt(6, second );
                stmt.setString(7, userLoggedIn);
                stmt.setInt(8, first );
                stmt.setInt(9, second );}
               
               // System.out.println("String set");
            
            ResultSet rs = stmt.executeQuery();
          
            
    
            System.out.printf("%10s | %-20s | %-20s | %-20s | %-20s | %-90s\n", "RateID", "Username", "Character", "Date", "Score", "Comment");
    
                if(!rs.isBeforeFirst()){
                    System.out.println("Filter resulted in no entries");
                    found = false;
                }
            
                while (rs.next()) {
                    int rateID = rs.getInt(1);
                    String username = rs.getString(2);
                    String character = rs.getString(3);
                    int date = rs.getInt(4);
                    int score = rs.getInt(5);
                    String comment = rs.getString(6);
                    System.out.printf("%10d | %-20s | %-20s | %-20d | %-20d | %-90s\n", rateID, username, character, date, score, comment);
                 }  
           
    
            
            rs.close();
            stmt.close();
        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
        }
            }//while end
    
        System.out.println("++++++++++++++++++++++++++++++++++");
        return found;
            
    }
    


    
private boolean displayMyReviews(Scanner input) throws SQLException {
    boolean found = true;
   
    
    System.out.println("++++++++++++++++++++++++++++++++++");
    System.out.println("My Reviews Window");
   
    String sql = "empty"; //arbitrary initialization

    
    try {
        
            sql = "SELECT r_rateID, u_username, c_name, r_date, r_score, r_comment " + 
            "FROM Rates, Users, Characters " +
            "WHERE u_username  = ? AND " +
            "c_charID = r_charID AND " + 
            "u_userID = r_userID " +
            "UNION " +
            "SELECT r_rateID, u_username, i_name, r_date, r_score, r_comment " + 
            "FROM Rates, Items, Users " +
            "WHERE u_username  = ? AND " +
            "i_itemID = r_itemID AND " + 
            "u_userID = r_userID " + 
            "UNION " +
            "SELECT r_rateID, u_username, s_name, r_date, r_score, r_comment " + 
            "FROM Rates, Users, Stages " +
            "WHERE u_username  = ? AND " +
            "s_stageID = r_stageID AND " + 
            "u_userID = r_userID";

                
        //System.out.println("This is sql : " + sql);
        PreparedStatement stmt = c.prepareStatement(sql);
        //System.out.println("Prepared statement made");
        //need to gate second set with if statement
            stmt.setString(1,userLoggedIn);
            stmt.setString(2,userLoggedIn);
            stmt.setString(3,userLoggedIn);
        
           // System.out.println("String set");
       
        ResultSet rs = stmt.executeQuery();
      
      
            System.out.printf("%10s | %-20s | %-20s | %-20s | %-20s | %-90s\n", "RateID", "Username", "Character", "Date", "Score", "Comment");
        
            if(!rs.isBeforeFirst()){
                System.out.println("No user reviews");
                found = false;
            }
            else{

            while (rs.next()) {
                int rateID = rs.getInt(1);
                String username = rs.getString(2);
                String character = rs.getString(3);
                int date = rs.getInt(4);
                int score = rs.getInt(5);
                String comment = rs.getString(6);
                System.out.printf("%10d | %-20s | %-20s | %-20d | %-20d | %-90s\n", rateID, username, character, date, score, comment);
             }  

             System.out.println("If you would like to filter results, enter 1. Otherwise enter 0.");
             
             while (!input.hasNextInt()){
                System.out.println("Enter 0 or 1");
                input.nextLine();
            }

            int filter = input.nextInt();
            while(filter != 0 && filter != 1){
                System.out.println("Enter 0 or 1");
                filter = input.nextInt();
            }

           if (filter == 1){
                //call filterchar rates here
                //System.out.println("calling rate filter");
                filterMyReviews(input);
            }
           

        }
            

        
        rs.close();
        stmt.close();
    } catch (Exception e) {
        System.err.println(e.getClass().getName() + ": " + e.getMessage());
    }

    
       
    System.out.println("++++++++++++++++++++++++++++++++++");
    return found;

}

private void reviewDeletion(int ID) throws SQLException {
    System.out.println("++++++++++++++++++++++++++++++++++");
    System.out.println("Deleting Review....");
    

    try {
       
           String sql = "DELETE FROM Rates  WHERE r_rateID = ?";
            
        
      

        

        PreparedStatement stmt = c.prepareStatement(sql);

        stmt.setInt(1, ID);
    
        // STEP: Execute batch
        stmt.executeUpdate();

        // STEP: Commit transaction
        c.commit();
      
        stmt.close();
        System.out.println("success");
    } catch (Exception e) {
        System.err.println(e.getClass().getName() + ": " + e.getMessage());
        try {
            c.rollback();
        } catch (Exception e1) {
            System.err.println(e1.getClass().getName() + ": " + e1.getMessage());
        }
    }
}


private void deleteMyReviews(Scanner input) throws SQLException {
        
    System.out.println("Delete Review Window");
    System.out.println("This will delete your review");
    
    
    boolean condition = true;
    while(condition){

    
    System.out.println("0 - Return to My Account Operations");
    System.out.println("1 - continue to deletion");
    
    

    while (!input.hasNextInt()){
        System.out.println("Enter 0 or 1");
        input.nextLine();
    }

    int choice = input.nextInt();
   
    //System.out.println("This is the user input : " + choice);
   
    if (choice > 1 || choice < 0){
    System.out.println("ERROR : Not an acceptable input. Try again");
    continue;
}
    else if (choice == 0){
        System.out.println("Returning to My Account operations " );
        return;
}

else if (choice == 1){
    boolean con2 = true;
    while(con2){
        System.out.println("Enter the ID # of the review you would like to edit. Or enter 0 to cancel");
    
        
        int num = 0;
      
       
        while (!input.hasNextInt()){
            System.out.println("Enter 0 or the ID# of the review you would like to delete");
            input.nextLine();
        }
    
        num = input.nextInt();
        int ID = num;
        
        //call edit authorize
        boolean allowed = authorizeEdit(num);
        
        if (num ==0){
            return;
        }
        else if (!allowed){
            System.out.println("You do not own deletion rights to this review. Try Again");
            System.out.println("Cancelling");}
    
    else{

        System.out.println("0 - cancel");
        System.out.println("1 - confirm deletion");
        
        

        while (!input.hasNextInt()){
            System.out.println("Enter 0 or 1");
            input.nextLine(); }

        int choice2 = input.nextInt();
       
        //System.out.println("This is the user input : " + choice2);
       
        if (choice2 > 1 || choice2 < 0){
        System.out.println("ERROR : Not an acceptable input. Reseting...");}
        else if (choice == 0){
            System.out.println("Cancelling");}
        else if (choice == 1){
           reviewDeletion(ID);
            System.out.println("Your review has been deleted");}

       
     }
   
}//while con2 end
        }
    }   
}


private boolean authorizeEdit(int ID){
    System.out.println("++++++++++++++++++++++++++++++++++");
        System.out.println("authentication attempt");
        boolean attempt = false;

        try {
          
            String sql = "SELECT count(*) " + 
                         "FROM Users,Rates " + 
                         "WHERE u_username = ? AND " + 
                         "u_userID = r_userID AND " +
                         "r_rateID = ? ";

                         
            PreparedStatement stmt = c.prepareStatement(sql);
            stmt.setString(1, userLoggedIn);
            stmt.setInt(2, ID);
            ResultSet rs = stmt.executeQuery();
            
            while (rs.next()) {
                int acct = rs.getInt(1);
               // System.out.println("This is the count value : " + acct);
                if (acct == 0){
                    attempt = false;
                }
                else if (acct == 1){
                    attempt = true;
                }
    
            }  
            rs.close();
            stmt.close();
        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
        }

        System.out.println("++++++++++++++++++++++++++++++++++");
        return attempt;

}

private void editMyReviews(Scanner input ) throws SQLException {
    boolean condition = true;
    while(condition){
    
    System.out.println("++++++++++++++++++++++++++++++++++");
    System.out.println("Review editor");
    System.out.println("Enter the ID # of the review you would like to edit. Or enter 0 to cancel");
    
    String sql = "empty"; //arbitrary initialization
    int num = 0;
  

    //if statement to catch blank case
   
    while (!input.hasNextInt()){
        System.out.println("Enter 0 or the ID# of the review you would like to edit");
        input.nextLine();
    }

    num = input.nextInt();
    int ID = num;
    
    //call edit authorize
    boolean allowed = authorizeEdit(num);
    
    if (num ==0){
        return;
    }
    else if (!allowed){
        System.out.println("You do not own editing rights to this review. Try Again");
        continue;}

       
    System.out.println("++++++++++++++++++++++++++++++++++");
    System.out.println("Edit Options");
    System.out.println("Edit applies to score and description");
    System.out.println("0 - cancel ()");
    System.out.println("1 - Edit score");
    System.out.println("2 - Edit description");
    System.out.println("3 - Edit both");
    

    //if statement to catch blank case

    while (!input.hasNextInt()){
        System.out.println("Enter 0, 1, 2, or 3");
        input.nextLine();
    }


    num = input.nextInt();
   
    int first = -1;
    String second = "empty";

    if (num > 3 || num < 0){
        System.out.println("ERROR : Not an acceptable input. Try again");
        continue;}
    else if (num ==0){
        return;
    }
    else if (num == 1){
        System.out.println("Enter new score");

        while (!input.hasNextInt()){
            System.out.println("Enter a number.");
            input.nextLine();}
            
            first = input.nextInt();

            if (first > 100 || first < 1){
                System.out.println("ERROR : Not an acceptable input. Resetting...");
                continue;}
        
    }
    else if (num == 2){
        System.out.println("Enter new description");
        second = input.nextLine();
        if (second.isBlank()){
            second = input.nextLine();
        }

    }
    else if (num == 3){
        System.out.println("Enter new score");

        while (!input.hasNextInt()){
            System.out.println("Enter a number.");
            input.nextLine();}
            
            first = input.nextInt();

            if (first > 100 || first < 1){
                System.out.println("ERROR : Not an acceptable input. Resetting...");
                continue;}
        
                System.out.println("Enter new description");

                second = input.nextLine();
                if (second.isBlank()){
                    second = input.nextLine();
                }
        
            }
                System.out.println("++++++++++++++++++++++++++++++++++");
                System.out.println("Updating Review....");
               
            
                try {
                    if (num ==1){
                        sql = "UPDATE Rates SET r_score = ? WHERE r_rateID = ?";
                        
                    }
                    else if(num == 2){
                        sql = "UPDATE Rates SET r_comment = ? WHERE r_rateID = ?";
                    }
                    else if(num == 3){
                        sql = "UPDATE Rates SET r_score = ? , r_comment = ? WHERE r_rateID = ?";
                    }
        
                    
        
                    PreparedStatement stmt = c.prepareStatement(sql);
        
                    if (num ==1){
                        stmt.setInt(1, first);
                        stmt.setInt(2, ID);
                        
                    }
                    else if(num == 2){
                        stmt.setString(1, second);
                        stmt.setInt(2, ID);
                    }
                    else if(num == 3){
                        stmt.setInt(1, first);
                        stmt.setString(2, second);
                        stmt.setInt(3, ID);
                    }
                
                    // STEP: Execute batch
                    stmt.executeUpdate();
            
                    // STEP: Commit transaction
                    c.commit();
                    stmt.close();
                    System.out.println("success");
    } catch (Exception e) {
        System.err.println(e.getClass().getName() + ": " + e.getMessage());
    }

    
        System.out.println("Resetting.....");
        
    System.out.println("++++++++++++++++++++++++++++++++++");
}

}



    private void myReviews(Scanner input) throws SQLException {
        boolean condition = true;
        while(condition){

        displayMyReviews(input);
;
        System.out.println("My Review Operations");
        System.out.println("0 - Return to Utility Interface");
        System.out.println("1 - Edit reviews");
        System.out.println("2 - Delete reviews");
       
        

        while (!input.hasNextInt()){
            System.out.println("Enter 0 , 1 , or 2");
            input.nextLine();
        }

        int choice = input.nextInt();
       
        //System.out.println("This is the user input : " + choice);
       
        if (choice > 2 || choice < 0){
        System.out.println("ERROR : Not an acceptable input. Try again");
        continue;
    }
        else if (choice == 0){
            //System.out.println("Returning to Utility Inteface ");
            
            return;
    }

        else if (choice == 1){
            //System.out.println("Calling Review editor : ");
            editMyReviews(input);
    }

        else if (choice == 2){
            //System.out.println("Calling Review Deletion : ");
            deleteMyReviews(input);
    }
   
        }//while loop end
        
    } 


    private void utility(Scanner input) throws SQLException {
        boolean condition = true;
        while(condition){

        System.out.println("Utility Interface");
        System.out.println("0 - Logout");
        System.out.println("1 - Browse Database / Make a review / View other reviews");
        System.out.println("2 - View / Edit / Delete your reviews");
        System.out.println("3 - View / Edit / Delete your account");
        

        while (!input.hasNextInt()){
            System.out.println("Enter 0 , 1 , 2, or 3");
            input.nextLine();
        }

        int choice = input.nextInt();
       
        //System.out.println("This is the user input : " + choice);
       
        if (choice > 3 || choice < 0){
        System.out.println("ERROR : Not an acceptable input. Try again");
        continue;
    }
        else if (choice == 0){
            System.out.println("Logging Out! Going back to Main Menu ");
            
            return;
    }

        else if (choice == 1){
            //System.out.println("This is where I call Browse Database / Make a review : " + choice);
            databaseOp(input);
    }

        else if (choice == 2){
            //System.out.println("This is where I call View / Edit / Delete your review : " + choice);
            myReviews(input);
    }
   
        else if (choice == 3){
            System.out.println("View / Edit / Delete your account : ");
            myAccount(input);
    }
        }//while loop end
        
    }

    private void insertNewUser(String username, String pass, String email,
    int userId) {
    System.out.println("++++++++++++++++++++++++++++++++++");
    System.out.println("Insert Users");

    try {
        String sql = "INSERT INTO Users VALUES(?, ?, ?, ?)";
        PreparedStatement stmt = c.prepareStatement(sql);

        stmt.setInt(1, userId);
        stmt.setString(2, username);
        stmt.setString(3, pass);
        stmt.setString(4, email);
    
        // STEP: Execute batch
        stmt.executeUpdate();

        // STEP: Commit transaction
        c.commit();

        stmt.close();
        System.out.println("success");
    } catch (Exception e) {
        System.err.println(e.getClass().getName() + ": " + e.getMessage());
        try {
            c.rollback();
        } catch (Exception e1) {
            System.err.println(e1.getClass().getName() + ": " + e1.getMessage());
        }
    }

    System.out.println("++++++++++++++++++++++++++++++++++");
}

    private int newId() {
        System.out.println("++++++++++++++++++++++++++++++++++");
        System.out.println("Creating New ID");
        int max = -1;

        try {
                     
            Statement stmt = c.createStatement();
            
            String sql = "SELECT max(u_userID) " + 
            "FROM Users ";
            
            stmt.execute(sql);
            ResultSet rs = stmt.getResultSet();
           
            while (rs.next()) {
                max = rs.getInt(1);
                //System.out.println("This is the max ID # : " + max);
            }  
            rs.close();
            stmt.close();
        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
        }

        System.out.println("++++++++++++++++++++++++++++++++++");
        return max;
        
    }

    private boolean checkUsername(String username) {
        System.out.println("++++++++++++++++++++++++++++++++++");
        System.out.println("Checking Username");
        boolean attempt = false;

        try {
          
            String sql = "SELECT count(*) " + 
                         "FROM Users " + 
                         "WHERE u_username = ? ";

                         
            PreparedStatement stmt = c.prepareStatement(sql);
            stmt.setString(1, username);
            ResultSet rs = stmt.executeQuery();
            
            while (rs.next()) {
                int acct = rs.getInt(1);
                //System.out.println("This is the count value : " + acct);
                if (acct == 0){
                    attempt = false;
                }
                else if (acct == 1){
                    attempt = true;
                }
    
            }  
            rs.close();
            stmt.close();
        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
        }

        System.out.println("++++++++++++++++++++++++++++++++++");
        return attempt;
    }
    
    private boolean authenication(String username, String pass) {
        System.out.println("++++++++++++++++++++++++++++++++++");
        System.out.println("authentication attempt");
        boolean attempt = false;

        try {
          
            String sql = "SELECT count(*) " + 
                         "FROM Users " + 
                         "WHERE u_username = ? AND " + 
                         "u_password = ? ";

                         
            PreparedStatement stmt = c.prepareStatement(sql);
            stmt.setString(1, username);
            stmt.setString(2, pass);
            ResultSet rs = stmt.executeQuery();
            
            while (rs.next()) {
                int acct = rs.getInt(1);
                //System.out.println("This is the count value : " + acct);
                if (acct == 0){
                    attempt = false;
                }
                else if (acct == 1){
                    attempt = true;
                }
    
            }  
            rs.close();
            stmt.close();
        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
        }

        System.out.println("++++++++++++++++++++++++++++++++++");
        return attempt;
    }


    private void createAccount( Scanner input) throws SQLException {
        boolean condition = true;
        while(condition){

        System.out.println("Create Account Window");
     

        System.out.println("To cancel (return to main menu), enter 0.");
        System.out.println("To continue to create account, enter 1.");
        
        while (!input.hasNextInt()){
            System.out.println("Enter 1 or enter 0");
            input.nextLine();
        }

        int cancel = input.nextInt();

        if (cancel > 1 || cancel < 0){
            System.out.println("ERROR : Not an acceptable input. Try again");
            continue;
        }

        // try using an if statement for catching filler
        if (cancel == 0){
            return;}
        
        System.out.println("Moving onto creation");
        String filler = input.nextLine();
        String username = filler; // arbitrary initialiization
        if (filler.isBlank() == true){
            System.out.println("Enter your username : ");
            username = input.nextLine();
        }
        else{
            System.out.println("Enter your username : ");
            username = input.nextLine();
        }

       // System.out.println("This is username : " +  username);

        boolean taken  = checkUsername(username);
        //need a username check
        if (taken == true){
            System.out.println("Username is already taken. Hard resetting Create Account.");
            continue;
        }

        else{
            System.out.println("Username is unique! Moving on to password");
        }

    
       
        System.out.println("Enter your password");
        String pass = input.nextLine();
        //System.out.println("This is password : " + pass);

        System.out.println("Enter your email");
        String email = input.nextLine();
        //System.out.println("This is email : " + pass);

        System.out.println("*******Creating Account*******");
        int max = newId();
        int newId = max +1;
        //System.out.println("This is newId : " + newId);
        insertNewUser(username, pass, email, newId);
        System.out.println("Your Account has been made!");
        System.out.println("Please login through the main menu");
        return;

    }//while loop end

    }



    private void logIn( Scanner input) throws SQLException {
        boolean condition = true;
        while(condition){

        System.out.println("Log In Window");
     

        System.out.println("To cancel login (return to main menu), enter 0.");
        System.out.println("To continue to login, enter 1.");
        
        while (!input.hasNextInt()){
            System.out.println("Enter 1 or enter 0");
            input.nextLine();
        }

        int cancel = input.nextInt();

        if (cancel > 1 || cancel < 0){
            System.out.println("ERROR : Not an acceptable input. Try again");
                continue;
        }

        // try using an if statement for catching filler
        if (cancel == 0){
            condition = false;
            return;}
        
        System.out.println("Moving onto login");
        String filler = input.nextLine();
        String username = filler; // arbitrary initialiization
        if (filler.isBlank() == true){
            System.out.println("Enter your username : ");
            username = input.nextLine();
        }
        else{
            System.out.println("Enter your username : ");
            username = input.nextLine();
        }
        //System.out.println("This is username : " +  username);

       
        System.out.println("Enter your password");
        String pass = input.nextLine();
        //System.out.println("This is password : " + pass);

        boolean authorize  = authenication(username, pass);

        if (authorize == true){
            System.out.println("Loggin Successful!");
            //System.out.println("This is where we display the utility options");
            userLoggedIn = username;
            utility(input);
            
        }

        else{
            System.out.println("ERROR: Username / password combination is incorrect, try again!");
            continue;
        }

        //System.out.println("We should never reach this point with utility options ");
        condition = false;
        return;
        } //while loop
    }


   
    private void closeDown() {

        System.out.println("Thank you for using the Smash Ultimate Database!");
        System.exit(0);

    }

    private void mainMenu(Scanner input) throws SQLException {
        boolean condition = true;
        while(condition){

        System.out.println("Main Menu");
        System.out.println("0 - exit");
        System.out.println("1 - Log In");
        System.out.println("2 - Create Account");
        

        while (!input.hasNextInt()){
            System.out.println("Enter 0 , 1 , or 2");
            input.nextLine();
        }

        int choice = input.nextInt();
       
        //System.out.println("This is the user input : " + choice);
       
        if (choice > 2 || choice < 0){
        System.out.println("ERROR : Not an acceptable input. Try again");
        continue;

    }
        else if (choice == 0){
            //System.out.println("YURRRRRR we out! : " + choice);
            condition = false;    
    }

        else if (choice == 1){
            //System.out.println("This is where I call logIn : " + choice);
            logIn(input);
    }

        else if (choice == 2){
            //System.out.println("This is where I call createAccount : " + choice);
            createAccount(input);
    }
        
    }//while end
   
        
    }

    public static void main(String args[]) throws SQLException {
        SUdatabase sj = new SUdatabase();
        
        sj.openConnection("data/SU.db");
        Scanner input = new Scanner(System.in);

        System.out.println("Welcome to the Smash Ultimate Database!");
        System.out.println("Enter the number corresponding to your desired operation");

        sj.mainMenu(input);
        sj.closeDown();
        //sj.dropTable();
        //sj.createTable();
        //sj.populateTable();

        input.close();
        sj.closeConnection();
    }
}
