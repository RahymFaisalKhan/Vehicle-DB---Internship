package vehicle_db;
import java.sql.*;
import java.util.Scanner;
import java.util.Random;

public class DB_interactive_console {
    public static void main(String[] args) {
        String jdbcURL = "jdbc:mysql://localhost:3306/Vehicles";
        String username = "rahyms_acc";
        String password = "rahym@2006";

        try {
            Connection connection = DriverManager.getConnection(jdbcURL, username, password);
            System.out.println("Welcome.");
            
            while (true)
            {
	            System.out.println("Database Operations Available:");
	            System.out.println("1. Insert Entry");
	            System.out.println("2. Update Entry");
	            System.out.println("3. Delete Entries");
	            System.out.println("4. Replace Entry's with Random Attributes");
	            System.out.println("5. Display Table");
	            System.out.println("6. Exit Application");
	            System.out.println();
	            System.out.println("Select an Option (Number): ");
	            Scanner sc = new Scanner(System.in);
	            int selected = sc.nextInt();  // Read user input
	
	            switch(selected) 
	            {
		            case 1:
		            	System.out.println("For your new entry:");
		            	System.out.println("- Enter the Vehicle Number:");
		                Scanner new_Number = new Scanner(System.in);
		                int new_Number_value = new_Number.nextInt(); 
		                
		            	System.out.println("- Enter the Vehicle Cost:");
		                Scanner new_Cost = new Scanner(System.in);
		                int new_Cost_value = new_Cost.nextInt(); 
		                
		            	System.out.println("- Enter the Vehicle Type:");
		                Scanner new_Type = new Scanner(System.in);
		                String new_Type_value = new_Type.nextLine(); 
		                
		                insert_entry(connection, new_Number_value, new_Type_value, new_Cost_value);
		              break;
		            case 2:
		            	System.out.println("Enter the Vehicle Number you want to update:");
		                Scanner update_Number = new Scanner(System.in);
		                int update_Number_value = update_Number.nextInt(); 
		                
		            	System.out.println("Enter the new Cost of the vehicle:");
		                Scanner update_Cost = new Scanner(System.in);
		                int update_Cost_value = update_Cost.nextInt(); 
		                
		            	System.out.println("Enter the new Type of the Vehicle:");
		                Scanner update_Type_val = new Scanner(System.in);
		                String update_Type_value = update_Type_val.nextLine(); 
		                
		                update_entry(connection, update_Number_value, update_Type_value, update_Cost_value);
		              break;
		            case 3:
		                Scanner delete_index_input = new Scanner(System.in);
		                int delete_index = delete_index_input.nextInt(); 
		                System.out.println("Enter vehicle numbers to delete (separated by spaces):");
	
			             // Read the full line of input
			             String line = delete_index_input.nextLine();
		
			             // Split it into parts using space as the delimiter
			             String[] parts = line.trim().split("\\s+");
		
			             // Convert to int array
			             int[] deleteIndexes = new int[parts.length];
			             for (int i = 0; i < parts.length; i++) 
			             {
			                 deleteIndexes[i] = Integer.parseInt(parts[i]);
			             }
			             delete_entries(connection, deleteIndexes);
		                break;
		            case 4:
		            	System.out.println("Enter index you want to start randomly replacing from:");
		                Scanner Replace_Starting = new Scanner(System.in);
		                int Replace_Starting_index = Replace_Starting.nextInt(); 
		                
		            	System.out.println("Enter index you want to stop randomly replacing from:");
		                Scanner Replace_Ending = new Scanner(System.in);
		                int Replace_Ending_index = Replace_Ending.nextInt(); 
		                
		                random_replacement (connection, Replace_Starting_index, Replace_Ending_index);
		                break;
		            case 5:
		            	display_table(connection);
		                break;
		            case 6:
		                connection.close();
		            	System.out.println("Exiting Program....");
		            	return;
		            default:
		            	System.out.println("Invalid Option Entered. Try again.");
	          }
            }            
        } 
        catch (Exception e) 
        {
            System.out.println("Database connection failed.");
            e.printStackTrace();
        }
    }
    
    public static void insert_entry(Connection connect, int Vehicle_Number, String Vehicle_Type, int Vehicle_Cost) {
        try 
        {
            // Step 1: Check if the vehicle number exists
            String checkSQL = "SELECT 1 FROM Vehicle_Tables WHERE `Vehicle Number` = ?";
            PreparedStatement checkStmt = connect.prepareStatement(checkSQL);
            checkStmt.setInt(1, Vehicle_Number);
            ResultSet rs = checkStmt.executeQuery();

            if (rs.next()) // If a result is returned, the vehicle number already exists
            {
                System.out.println("Vehicle Number " + Vehicle_Number + " already exists. Insertion skipped.");
                return;
            } 
            else 
            {
                String insertSQL = "INSERT INTO Vehicle_Tables (`Vehicle Number`, `Vehicle Type`, `Vehicle Cost`) VALUES (?, ?, ?)";
                PreparedStatement insertStmt = connect.prepareStatement(insertSQL);
                insertStmt.setInt(1, Vehicle_Number);
                insertStmt.setString(2, Vehicle_Type);
                insertStmt.setInt(3, Vehicle_Cost);
                int inserted = insertStmt.executeUpdate();
                System.out.println("Inserted row: " + inserted);
            }
        } 
        catch (Exception e) 
        {
            System.out.println("Database connection failed.");
            e.printStackTrace();
        }
    }
    
    public static void update_entry (Connection connect, int Vehicle_Number, String Vehicle_Type, int Vehicle_Cost) {
        try {
            // Step 1: Check if the vehicle number exists
            String checkSQL = "SELECT 1 FROM Vehicle_Tables WHERE `Vehicle Number` = ?";
            PreparedStatement checkStmt = connect.prepareStatement(checkSQL);
            checkStmt.setInt(1, Vehicle_Number);
            ResultSet rs = checkStmt.executeQuery();

            if (rs.next()) // If a result is returned, the vehicle exists and we can continue updating value
            {
                String updateSQL = "UPDATE Vehicle_Tables SET `Vehicle Type` = ?, `Vehicle Cost` = ? WHERE `Vehicle Number` = ?";
                PreparedStatement updatestmt = connect.prepareStatement(updateSQL);
                updatestmt.setString(1, Vehicle_Type);
                updatestmt.setInt(2, Vehicle_Cost);
                updatestmt.setInt(3, Vehicle_Number);
                int updated = updatestmt.executeUpdate();
                System.out.println("Inserted row: " + updated);
            } 
            else 
            {
                System.out.println("Vehicle Number " + Vehicle_Number + " Doesnt exist. Updating entry skipped.");
                return;
            }
        } catch (Exception e) 
        {
            System.out.println("Database connection failed.");
            e.printStackTrace();
        }
    }
    
    public static void delete_entries (Connection connect, int [] index)
    {
    	try 
    	{
			String delete_SQL = "DELETE FROM Vehicle_Tables WHERE `Vehicle Number` = ?";
			PreparedStatement deleteStmt = connect.prepareStatement(delete_SQL);
	    	for (int i = 0; i < index.length ; i++) 
	    	{
	    		int value = index[i];
	    		deleteStmt.setInt(1, value);
	    		deleteStmt.addBatch();
	    	}
	    	int[] results = deleteStmt.executeBatch();
	        System.out.println("Deleted rows: " + results.length);
    	}
        catch (Exception e) 
        {
            System.out.println("Database connection failed.");
            e.printStackTrace();
        }
    }
   
    public static void display_table (Connection connect)
    {
    	try
    	{
	        Statement statement = connect.createStatement();
	        ResultSet resultSet = statement.executeQuery("SELECT * FROM Vehicle_Tables");
	
	        // Display updated data
	        while (resultSet.next()) 
	        {
	            System.out.println("Vehicle Number: " + resultSet.getInt("Vehicle Number"));
	            System.out.println("Vehicle Type: " + resultSet.getString("Vehicle Type"));
	            System.out.println("Vehicle Cost: " + resultSet.getInt("Vehicle Cost"));
	            System.out.println("---");
	        }
    	}
        catch (Exception e) 
        {
            System.out.println("Database connection failed.");
            e.printStackTrace();
        }
    }
    
    public static void random_replacement (Connection connect, int from, int to)
    {
    	try
    	{
	        String[] types = {"Sedan", "SUV", "Truck", "Bike", "Airbus", "Van"};
	        Random rand = new Random();
			String sql4 = "REPLACE INTO Vehicle_Tables (`Vehicle Number`, `Vehicle Type`, `Vehicle Cost`) VALUES (?, ?, ?)";
			PreparedStatement pstmt4 = connect.prepareStatement(sql4);
	        for (int i = from; i <= to; i++) 
	        {
	            String type = types[rand.nextInt(5)];
	            int cost = 1000 + rand.nextInt(100000); // Cost between 1000 and 101000
	            
				pstmt4.setInt(1, i);
				pstmt4.setString(2, type);
				pstmt4.setInt(3, cost);
	
	            pstmt4.addBatch();
	        }
	        int[] results = pstmt4.executeBatch();
	        System.out.println("Replaced rows: " + results.length);
    	}
        catch (Exception e) 
        {
            System.out.println("Database connection failed.");
            e.printStackTrace();
        }
    }
}
