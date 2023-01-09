package shoot_the_alien.model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import shoot_the_alien.model.Winner;

/**
 * 
 * @author Luiz Eduardo da Costa
 * @version 1.0, 14/05/16
 */
public class WinnerDAO {
	
	/**
	 * The table's name.
	 */
	private static final String TABLE_NAME = "winners"; 
	/**
	 * The instance of this class.
	 */
	private static WinnerDAO instance;
    /**
     * It is used to build a new connection with the database.
     */
    private Connection conn = null;
    /**
     * It loads an instruction that will be executed on the database. 
     */
    private PreparedStatement preStm = null;
    /**
     * It is used to do searchs on the database.
     */
    private ResultSet resSet = null;
	
    /**
     * It uses the Singleton method to returs the instance of this class.
     * 
     * @return An instance of this class.
     */
    public static WinnerDAO getInstance(){
    	if(instance == null){
    		instance = new WinnerDAO();
    	}
    	return instance;
    }
	
	/**
	 * It inserts a new Winner into database.
	 * 
	 * @param newWinner
	 * @return The status of the execution.
	 */
	public boolean insertNew(Winner newWinner){
		
		String sql = "INSERT INTO "+TABLE_NAME+" (score, name, identification) "
				+ "VALUES ("+newWinner.getFinalScore()+", "
						+ "'"+newWinner.getName()+"', "
						+ "'"+newWinner.getIdentification()+"')";
		
		return executeSQL(sql);
	}
	
	/**
	 * It executes a research on the database.
	 *  
	 * @return A list with the winners ordered by the higher score.
	 */
	public ArrayList<Winner> getAllWinners(){
		
		// Reserach's statement.
		String sql = "SELECT * FROM "+TABLE_NAME+" ORDER BY score DESC LIMIT 10";
		ArrayList<Winner> registeredWinners = new ArrayList<Winner>();
		Winner winner = null;
		
        try {

        	// Open the connections with the database.
        	openConnection();
        	
            preStm = conn.prepareStatement(sql);
            
            resSet = preStm.executeQuery();

            while(resSet.next()){
            	
            	int score = resSet.getInt("score");
            	String name = resSet.getString("name");
            	String id = resSet.getString("identification");
            	
            	winner = new Winner(name, id, score);
            	registeredWinners.add(winner);
            	
            }
        } catch (SQLException e) {
           
        	JOptionPane.showMessageDialog(null, "Erro ao consultar lista de vencedores!", "Erro", JOptionPane.ERROR_MESSAGE);
        	
        }finally{
            try {
            	// Close the connections with the database.
                closeConnections();
            } catch (SQLException e) {
                System.out.println("\nErro ao fechar conexões com o banco de dados!\n"+e.getMessage());
            }
         }
		
		return registeredWinners;
	}
	
	/**
	 * It executes some SQL statement on the database. 
	 * 
	 * @param sql
	 * @return The status of the execution.
	 */
	private boolean executeSQL(String sql){ 
        
        try {
        	
        	// Open the connection with the database.
            openConnection();
            
            this.preStm = conn.prepareStatement(sql);
            
            return !preStm.execute(); 
            
        } catch (SQLException e) {
            return false;
            
        }finally{
            try {
            	
                this.closeConnections();
                
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(null, "Houve um erro de conexão com o Banco de Dados!","Univaders - Erro",JOptionPane.ERROR_MESSAGE);
            
                System.err.println(e.getMessage());
            }
        }
    }
	
	/**
	 * Open a connnection with the database.
	 */
	private void openConnection(){
        this.conn = DatabaseConnection.getInstance().getConnection(); 
    }
	
	/**
	 * Close the connections with the database.
	 * @throws SQLException
	 */
	private void closeConnections() throws SQLException{
        if(conn   != null) conn.close();
        if(preStm != null) preStm.close();
        if(resSet != null) resSet.close();
    }	
}
