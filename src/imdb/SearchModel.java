package imdb;

import java.util.ArrayList;
import javax.swing.table.AbstractTableModel;

public class SearchModel extends AbstractTableModel {
	public ArrayList<String> entities;
	public ArrayList<String> releaseYears;
	private String columns[];

	public SearchModel() {
		entities = new ArrayList<String>();
		releaseYears = new ArrayList<String>();
		columns = new String[] {  " ", "Title", "Year Released", };
	}

	@Override
	public int getRowCount() {
		// TODO Auto-generated method stub
		try {
			return entities.size();
		} catch (Exception e) {
			return 0;
		}
	}

	@Override
	public int getColumnCount() {
		return columns.length;
	}

	@Override
	public String getColumnName(int column) {
		return columns[column];
	}

	public String[] getColumnNames() {
		return columns;
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		if (columnIndex == 0) return "   ";
		if (columnIndex == 2) 
			return releaseYears.get(rowIndex);
		
		return entities.get(rowIndex);
	}

	public void addEntity(String name, String year) {
		// TODO Auto-generated method stub
		entities.add(name);
		
		if (year == "")
			releaseYears.add("---");
		else 
			releaseYears.add(year);
	}

	public void clear() {
		entities.clear();
		releaseYears.clear();
	}

	public void update() {
		this.fireTableDataChanged();
	}

}
