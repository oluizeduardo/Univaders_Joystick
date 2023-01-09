package shoot_the_alien.model.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * It works to generate an instance connection with database.
 * 
 * @author Luiz Eduardo da Costa
 * @version 1.0, 14/05/2016
 *
 */
public class DatabaseConnection {
	
	
	/**
	 * An instance of the class.
	 */
	public static DatabaseConnection instance = null;
		
		
	// PostgreSQL data connection
	private String address = "127.0.0.1";
	private String port = "5432";
	private String nameDataBase = "Univaders";
    private String url = "jdbc:postgresql://"+address+":"+port+"/"+nameDataBase;  
    private String username = "postgres";  
    private String password = "postgres";  
    private String driver = "org.postgresql.Driver";
		
	    
	    
	    
	    
	    
    /**
     * It returns an instance of this class.
     */
    public static DatabaseConnection getInstance(){
        if(instance == null){
            instance = new DatabaseConnection();
        }
        return instance;
    }
	    
	    
	    
	    
	    
	    
	    
    /**
     * It returns an object <code>Connection</code> containing
     * the instance of the connection with database. If it does not
     * find the instance of the driver specified or there is any error
     * in the connection with the database, the exception is handled.
     * 
     * @return conn <code>Connection</code>
     */
    public Connection getConnection() {
        
    	Connection conn = null;
        
        try {
        	// Load the driver.
			Class.forName(driver);	
			// Give the address, username and password, it returns the instance of connection.
	        conn = DriverManager.getConnection(url, username ,password);
	        
		} catch (ClassNotFoundException e) {
			System.err.println("Error to load the class!! \n"+e.getMessage());
			e.printStackTrace();
		}catch (SQLException e) {
			System.err.println("SQL Error!!\n"+e.getMessage());
		}
        
        return conn;
    }
	    

}
