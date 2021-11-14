package shoot_the_alien.model.table;

import java.awt.Color;
import java.awt.Font;

import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;

/**
 * Build the table to list the best winners.
 * 
 * @author Luiz Eduardo da Costa
 * @version 1.0, 09/05/16
 *
 */
public class TableWinners{

	/**
	 * The model to be apply on the table.
	 */
	private WinnersTableModel model;
	/**
	 * The table where will appear the informations about the best winners.
	 */
	private JTable table;
	/**
	 * The panel will add the table.
	 */
	private JPanel pnBaseTable; 
	
	
	
	
	/**
	 * The constructor.
	 */
	public TableWinners(JPanel pnBase) {
		
		this.pnBaseTable = pnBase;
		this.model = new WinnersTableModel();		
	}
	
	
	
	
	/**
	 * Build and sets the table.
	 */
	public JTable getTable(){
	
		table = new JTable(model);
		table.setRowHeight(41);
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		table.getTableHeader().setFont(new Font("Lucida Sans", Font.BOLD, 27));//Nome das colunas em negrito.
		table.setFont(new Font("Lucida Sans", Font.PLAIN, 25));
		table.getTableHeader().setBackground(new Color(200, 200, 200));		
		table.setSelectionForeground(Color.BLACK);
		table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		table.setBackground(new Color(0,0,0, 10));
		
		table.getColumnModel().getColumn(0).setCellRenderer(model.centerCell());
		table.getColumnModel().getColumn(1).setCellRenderer(model.centerCell());
		table.getColumnModel().getColumn(2).setCellRenderer(model.centerCell());
		table.getColumnModel().getColumn(3).setCellRenderer(model.centerCell());
				
		setPreferredWidthPercentage(0, 9);// Position
		setPreferredWidthPercentage(1, 18.51f);// Final Score
		setPreferredWidthPercentage(2, 36);// Name
		setPreferredWidthPercentage(3, 36);// Identification
		
		return table;
	}
	
	
	
	
	/**
	 * It returns the model applied on the table.
	 */
	public WinnersTableModel getTableModel(){
		return model;
	}
	
	
	/**
	 * 
	 * @param column The number of the column.
	 * @param percentage 
	 */
	private void setPreferredWidthPercentage(int column, float percentage){
		
		float minWidht = (pnBaseTable.getWidth() * percentage / 100);
		table.getColumnModel().getColumn(column).setMinWidth((int)minWidht);
	}
	
	
}
