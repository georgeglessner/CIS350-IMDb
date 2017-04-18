package imdb;

import java.util.ArrayList;

import javax.swing.table.AbstractTableModel;

public class SearchModel extends AbstractTableModel {
	private ArrayList<String> movies;
	private String columns[];

	public SearchModel() {
		movies = new ArrayList<String>();
		columns = new String[] { "Results" };
	}

	@Override
	public int getRowCount() {
		// TODO Auto-generated method stub
		try {
			return movies.size();
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
		return movies.get(rowIndex);
	}

	public void addMovie(String movieName) {
		// TODO Auto-generated method stub
		movies.add(movieName);
	}

	public void clearMovies() {
		movies.clear();
	}

	public void update() {
		this.fireTableDataChanged();
	}

}
