package imdb;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.MatteBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;

//import project3.BankGUI.TableListener;

public class DatabaseInitGUI extends JFrame {

	private SearchModel searchModel;

	private JTextField searchField;
	
	private JLabel logo;
	
	private JRadioButton moviesBtn, tvShowsBtn;

	private JButton top20, nowPlaying, upcoming, btnClear;

	private JTable imdbTable;

	private JFrame frame;

	private JPanel mainPanel, logoPanel, findPanel, panelSearch, panelOptions, panelButtons, panelList;

	private JMenuItem quitItem;

	private DatabaseInit d;

	private String[] topMovies;

	private String[] nowMovies;

	private String[] upcomingMovies;
	
	Action action;

	public DatabaseInitGUI() {
		d = new DatabaseInit();
		topMovies = d.topMovies(d.movies);
		nowMovies = d.nowMovies(d.movies);
		upcomingMovies = d.upcomingMovies(d.movies);
		searchModel = new SearchModel();
	
		// Frame.
		frame = new JFrame();
		frame.setLayout(new BorderLayout());
		
		// Main Panel.
		mainPanel = new JPanel();
		mainPanel.setBorder(new EmptyBorder(20, 30, 20, 30));
		mainPanel.setLayout(new BorderLayout(2, 1));
		
		// Logo Panel.
		logoPanel = new JPanel();
		logoPanel.setBorder(new EmptyBorder(10, 220, 20, 0));
		logoPanel.setLayout(new GridLayout(1, 0));
		
		// Find Panel.
		findPanel = new JPanel();
		findPanel.setBorder(new EmptyBorder(0, 20, 0, 20));
		findPanel.setLayout(new BorderLayout(3, 1));
		
		// Panel list.
		panelList = new JPanel();
		panelList.setBorder(new EmptyBorder(0, 10, 0, 0));
		
		// Panel search.
		panelSearch = new JPanel();
		panelSearch.setBorder(new EmptyBorder(0, 0, 0, 0));
		panelSearch.setLayout(new BorderLayout(2, 1));
		
		// Panel options. 
		panelOptions = new JPanel();
		panelOptions.setBorder(new EmptyBorder(20, 155, 10, 0));
		panelOptions.setLayout(new GridLayout(1, 2));
		
		// Panel buttons.
		panelButtons = new JPanel();
		panelButtons.setBorder(new EmptyBorder(20, 0, 30, 0));
		panelButtons.setLayout(new GridLayout(1, 0));
		
		// Logo label. 
		logo = new JLabel();
		logo.setText("GGC Entertainment Database");
		logo.setFont(new Font("Arial", Font.PLAIN, 20));
		logo.setForeground(Color.BLUE);

		// Table.
		imdbTable = new JTable(searchModel);
		imdbTable.setAutoResizeMode(JTable.AUTO_RESIZE_LAST_COLUMN);
		imdbTable.getTableHeader().setPreferredSize(new Dimension(550, 35));
		imdbTable.getTableHeader().setFont(new Font("Arial", Font.ITALIC, 20));
		imdbTable.setRowHeight(25);
		imdbTable.getColumnModel().getColumn(0).setMaxWidth(5);
		imdbTable.getColumnModel().getColumn(1).setMinWidth(400);
		imdbTable.getColumnModel().setColumnMargin(0);
		for (int x = 0; x < 3; x++) {
			TableColumn col = imdbTable.getColumnModel().getColumn(x);
	    	col.setHeaderRenderer(new MyTableHeaderRenderer());
		}
		
		// Radio Buttons (movies and tvShows).
		moviesBtn = new JRadioButton();
		moviesBtn.setText("Movies");
		moviesBtn.setSelected(true);
		tvShowsBtn = new JRadioButton();
		tvShowsBtn.setText("TV Shows");
		tvShowsBtn.setSelected(false);
	
		// Search field.
		searchField = new HintTextField("Search here...");
		searchField.setEnabled(true);
		searchField.setBorder(BorderFactory.createCompoundBorder(
		        searchField.getBorder(), 
		        BorderFactory.createEmptyBorder(0, 8, 0, 0)));

		// Buttons.
		top20 = new JButton("Top 20");
		nowPlaying = new JButton("Now Playing");
		upcoming = new JButton("Upcoming");
		btnClear = new JButton("Clear");
		
		// Scroll pane. 
		JScrollPane scrollPane = new JScrollPane(imdbTable);
		scrollPane.setPreferredSize(new Dimension(650, 400));
		imdbTable.setBorder(new EmptyBorder(0, 20, 0, 20));
		//imdbTable.setFillsViewportHeight(true);

		// Action Listeners.
		ButtonListener listener = new ButtonListener();
		top20.addActionListener(listener);
		nowPlaying.addActionListener(listener);
		upcoming.addActionListener(listener);
		btnClear.addActionListener(listener);
		moviesBtn.addActionListener(listener);
		tvShowsBtn.addActionListener(listener);
		action = getAbstractAction();
		searchField.addActionListener(action);

		// MenuBar.
		//menubar = new JMenuBar();
		//quitItem = new JMenuItem("Quit");
		//quitItem.addActionListener(listener);
		//menu = new JMenu("File");
		//menu.add(quitItem);
		//menubar.add(menu);
		
		// Add to panelList, panelSearch, and panelButtons.
		panelList.add(scrollPane);
		panelOptions.add(moviesBtn);
		panelOptions.add(tvShowsBtn);
		panelSearch.add(panelOptions, BorderLayout.NORTH);
		panelSearch.add(searchField, BorderLayout.CENTER);
		panelButtons.add(top20);
		panelButtons.add(nowPlaying);
		panelButtons.add(upcoming);
		panelButtons.add(btnClear);
		

		// Frame. 
		frame.setVisible(true);
		//frame.setJMenuBar(menubar);
		frame.pack();
		frame.setResizable(true);
		frame.setDefaultCloseOperation(EXIT_ON_CLOSE);
		frame.setSize(770, 700);
		
		// Add panel, panelButtons, and panelList to frame.
		logoPanel.add(logo);
		findPanel.add(panelList, BorderLayout.NORTH);
		findPanel.add(panelSearch, BorderLayout.CENTER);
		findPanel.add(panelButtons, BorderLayout.SOUTH);
		
		mainPanel.add(logoPanel, BorderLayout.NORTH);
		mainPanel.add(findPanel, BorderLayout.CENTER);
		
		frame.add(mainPanel);
	}

	private class ButtonListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {

			// Top 20 movies.
			if (e.getSource() == top20) {
				searchModel.clear();
				
				if (moviesBtn.isSelected()) {
					String[] results = d.topMovies(d.movies);
					for (int x = 0; x < 20; x++) 
						searchModel.addEntity(results[x], d.currMovies.get(x).getReleaseDate().split("-")[0]);
		
				} else {
					String[] results = d.topShows(d.shows);
					for (int x = 0; x < 20; x++) 
						searchModel.addEntity(results[x], d.currShows.get(x).getFirstAirDate().split("-")[0]);
				}

			// Now playing movies.
			} else if (e.getSource() == nowPlaying) {
				searchModel.clear();
				
				if (moviesBtn.isSelected()) {
					String[] results = d.nowMovies(d.movies);
					for (int x = 0; x < 20; x++)	
						searchModel.addEntity(results[x], d.currMovies.get(x).getReleaseDate().split("-")[0]);
				
				} else {
					String[] results = d.nowShows(d.shows);
					for (int x = 0; x < 20; x++) 
						searchModel.addEntity(results[x], d.currShows.get(x).getFirstAirDate().split("-")[0]);
				}

			// Upcoming movies.
			} else if (e.getSource() == upcoming) {
				searchModel.clear();
				if (moviesBtn.isSelected()) {
					String[] results = d.upcomingMovies(d.movies);
					for (int x = 0; x < 20; x++)
					searchModel.addEntity(results[x], d.currMovies.get(x).getReleaseDate().split("-")[0]);
				
				} else {
					String[] results = d.upcomingShows(d.shows);
					for (int x = 0; x < 20; x++) 
						searchModel.addEntity(results[x], d.currShows.get(x).getFirstAirDate().split("-")[0]);
				}

			// Clear Button
			} else if (e.getSource() == btnClear) {
				searchModel.clear();

			// Movies Button
			} else if (e.getSource() == moviesBtn) {
				moviesBtn.setSelected(true);
				tvShowsBtn.setSelected(false);
				
			// TV Shows	Button
			} else if (e.getSource() == tvShowsBtn) {
				tvShowsBtn.setSelected(true);
				moviesBtn.setSelected(false);
				
			//Quit
			} else if (e.getSource() == quitItem) {
				System.exit(0);
			}

			searchModel.update();
		}
		
		
	}

	private Action getAbstractAction() {
		Action a = new AbstractAction() {		
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			@Override
			public void actionPerformed(ActionEvent e) {
				searchModel.clear();

				if (moviesBtn.isSelected()) {
					String[] searchResults = d.searchMovies(searchField.getText());
					for (int x = 0; x < searchResults.length; x++)
						searchModel.addEntity(searchResults[x], d.currMovies.get(x).getReleaseDate().split("-")[0]);
				
				} else {
					String[] searchResults = d.searchShows(searchField.getText());
					for (int x = 0; x < searchResults.length; x++) 
						searchModel.addEntity(searchResults[x], d.currShows.get(x).getFirstAirDate().split("-")[0]);
				}
				
				searchModel.update();
			}
		};
		
		return a;
	}

}

class HintTextField extends JTextField implements FocusListener {
	private static final long serialVersionUID = 1L;
	
	private final String hint;
	private boolean showingHint;

	public HintTextField(final String hint) {
		super(hint);
	    this.hint = hint;
	    this.showingHint = true;
	    super.setFont(new Font("Arial", Font.ITALIC, 12));
		super.setForeground(Color.LIGHT_GRAY);
	    super.addFocusListener(this);
	}

	@Override
	public void focusGained(FocusEvent e) {
		if (this.getText().isEmpty()) {
			super.setText("");
			super.setFont(new Font("Arial", Font.PLAIN, 12));
			super.setForeground(Color.BLACK);
			showingHint = false;
	    }
	}
	  
	@Override
	public void focusLost(FocusEvent e) {
		super.setText(hint);
		super.setFont(new Font("Arial", Font.ITALIC, 12));
		super.setForeground(Color.LIGHT_GRAY);
		showingHint = true;
	}

	@Override
	public String getText() {
    	return showingHint ? "" : super.getText();
    }
}

class MyTableHeaderRenderer extends JLabel implements TableCellRenderer {
	private static final long serialVersionUID = 1L;

	public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,
	      boolean hasFocus, int rowIndex, int vColIndex) {
	    setText(value.toString());
	    setToolTipText((String) value);
	    
	    return this;
	  }

	}
