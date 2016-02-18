import java.util.Vector;

import javax.swing.table.DefaultTableModel;

public class Standart extends DefaultTableModel{
	
	public Standart(Vector<Vector<String>> data, Vector<String> columns) {
		super(data, columns);
	}
	
	public boolean isCellEditable(int row, int column){  
        return false;  
    }

}