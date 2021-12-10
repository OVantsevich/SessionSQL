import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;

public class SQLTest {
	String url = "jdbc:mysql://localhost:3306/session";
	String username = "java";
	String password = "password";
	
	public SQLTest() {
	}
	
    public String[][] get(String sql, int columns) throws Exception {
    	try {
    		Class.forName("com.mysql.cj.jdbc.Driver").getDeclaredConstructor().newInstance();
    		try (Connection connection = DriverManager.getConnection(url, username, password)) {
    			
	    		ArrayList<String[]> arr = new ArrayList<String[]>();
	    		Statement statement = connection.createStatement();
	            ResultSet resultSet = statement.executeQuery(sql);
	            
	            while(resultSet.next()) {
	            	arr.add(new String[columns]);
	            	for(int j = 1; j <= columns; j++) {
	            		arr.get(arr.size() - 1)[j - 1] = resultSet.getString(j);
	            	}
	            }
	            
	            String[][] arrString = new String[0][columns];
	            if(!arr.isEmpty()) {
		            arrString = new String[arr.size()][];
		            for(int i = 0; i < arr.size(); i++) {
		            	arrString[i] = arr.get(i);
		            }
	            }
	            return arrString;
    		} catch (SQLException e) {
    		    throw new IllegalStateException("Cannot connect the database!", e);
        	}
        } catch (Exception e) {
            throw e;
        }
    }
    
    public void set(String str) throws Exception {
    	try{
            Class.forName("com.mysql.cj.jdbc.Driver").getDeclaredConstructor().newInstance();
             
            try (Connection conn = DriverManager.getConnection(url, username, password)){
                 
               Statement statement = conn.createStatement();
               statement.executeUpdate(str);
                
            }
        }
        catch(Exception ex){
            System.out.println("Connection failed...");
            System.out.println(ex);
        }
    }

}