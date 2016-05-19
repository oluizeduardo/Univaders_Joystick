package shoot_the_alien.model.table;

import java.util.ArrayList;
import javax.swing.SwingConstants;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableCellRenderer;

import shoot_the_alien.model.Winner;


/**
 * Table model to apply on the table of the best winners.
 * 
 * @author Luiz Eduardo da Costa
 * @version 1.0, 09/05/16
 */
public class WinnersTableModel extends AbstractTableModel {


	private static final long serialVersionUID = 1L;

	// Cell Renderer.
	private DefaultTableCellRenderer rendererCell = null;
	
	// A list with datas to be write on the table.
	private ArrayList<Winner> listWinners = new ArrayList<Winner>();
	
	// Names of the table columns.
	private String[] columns = {"Pontuação", "Nome", "Curso/Cidade"};
	
	
	
	
	
	/**
	 * Constructor.
	 */
	public WinnersTableModel() {
		
		
	}
	
	
	
	
	/**
     * It sets the object <code>DefaultTableCellRenderer</code> to, 
     * when apply in a table, center its content.
     * 
     * @return Object <code>DefaultTableCellRenderer</code> 
     * configured to centralization of the table content.
     */
    public TableCellRenderer centerCell(){     
    	rendererCell = new DefaultTableCellRenderer();
    	rendererCell.setHorizontalAlignment(SwingConstants.CENTER); 
        
        return rendererCell;       
    }
	
	
	
    /**
     * It returns the númeber of table columns.
     */
	@Override
	public int getColumnCount() {
		return columns.length;
	}

	
	/**
     * It returns the númeber of table rows.
     */
	@Override
	public int getRowCount() {
		return listWinners.size();
	}

	
	
	@Override
	public Object getValueAt(int row, int column) {
		
		Winner winner = listWinners.get(row);
		
		switch (column) {
			case 0:
				return winner.getFinalScore();
			case 1:
				return winner.getName();
			case 2:
				return winner.getIdentification();
			default:
				throw new IndexOutOfBoundsException("There is no index '"+column+"'in the list.");
		}	
	}

	
	
	
	
	
	/**
	 * It returns the column name in the specific position.
	 */
	@Override
	public String getColumnName(int column) {
		return columns[column];
	}

	
	
	/**
	 * It returns the data type of the content in the cell.
	 */
	@Override
	public Class<?> getColumnClass(int columnIndex) {
		return String.class;
	}
	
	
	
	/**
	 * Locks editing of all columns
	 */
	@Override
	public boolean isCellEditable(int rowIndex, int columnIndex) {
		return false;
	}
	
	
	

	/**
	 * Sets a new list to be applied in the table model.
	 * @param novaLista
	 */
	public void setList(ArrayList<Winner> newList){
		this.listWinners = newList;
		fireTableDataChanged();
	}
	
	
	
	/**
	 * Add a new object in the list.
	 * @param newWinner
	 */
	public void add(Winner newWinner){		
		listWinners.add(newWinner);
		fireTableDataChanged();
	}
	
	
	
}
