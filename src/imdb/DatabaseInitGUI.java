package imdb;

import java.awt.BorderLayout;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

//import project3.BankGUI.TableListener;

public class DatabaseInitGUI extends JFrame {

	private SearchModel searchModel;

	private JTextField searchField;

	private JButton top20, nowPlaying, upcoming, btnClear;

	private JTable imdbTable;

	private JFrame frame;

	private JPanel panel, panelButtons, panelList;

	private JMenuBar menubar;

	private JMenuItem quitItem;

	private JMenu menu;

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

		ButtonListener listener = new ButtonListener();

		frame = new JFrame();

		frame.setLayout(new BorderLayout());
		// frame.setPreferredSize(new Dimension(500, 300));

		panel = new JPanel();

		panelButtons = new JPanel();
		panelButtons.setBorder(new EmptyBorder(10, 31, 10, 30));

		panelList = new JPanel();

		imdbTable = new JTable(searchModel);

		searchField = new JTextField();

		searchField.setPreferredSize(new Dimension(250, 250));
		
		panel.setBorder(new EmptyBorder(3, 31, 0, 23));
		// searchField.set

		top20 = new JButton("Top 20");

		nowPlaying = new JButton("Now Playing");

		upcoming = new JButton("Upcoming");

		btnClear = new JButton("Clear");

		searchField.setEnabled(true);

		top20.addActionListener(listener);
		nowPlaying.addActionListener(listener);
		upcoming.addActionListener(listener);
		btnClear.addActionListener(listener);

		menubar = new JMenuBar();

		quitItem = new JMenuItem("Quit");

		quitItem.addActionListener(listener);

		menu = new JMenu("File");

		menubar.add(menu);

		menu.add(quitItem);

		panel.add(searchField);

		frame.add(panel);

		panelButtons.add(top20);

		panelButtons.add(nowPlaying);

		panelButtons.add(upcoming);

		panelButtons.add(btnClear);

		JScrollPane scrollPane = new JScrollPane(imdbTable);
		scrollPane.setPreferredSize(new Dimension(710, 500));
		imdbTable.setFillsViewportHeight(true);

		panelList.setBorder(new EmptyBorder(0, 10, 0, 0));

		panelList.add(scrollPane);

		panel.setLayout(new GridLayout(1, 1));

		panelButtons.setLayout(new GridLayout(1, 0));

		frame.setVisible(true);

		// frame.add(panel, BorderLayout.WEST);

		frame.add(panelButtons, BorderLayout.SOUTH);

		frame.add(panelList, BorderLayout.NORTH);

		frame.setJMenuBar(menubar);

		frame.pack();

		frame.setResizable(true);

		frame.setDefaultCloseOperation(EXIT_ON_CLOSE);

		frame.setSize(770, 650);
		
		action = new AbstractAction() {
			
			
			@Override
			public void actionPerformed(ActionEvent e) {
				searchModel.clearMovies();
				
				String[] searchMovies = d.searchMovies(searchField.getText(), false, null);
				
				for (int x = 0; x < searchMovies.length; x++) {
					searchModel.addMovie(searchMovies[x]);
				}
				
				searchModel.update();
			}
		};

		searchField.addActionListener(action);

	}

	private class ButtonListener implements ActionListener {
		//
		public void actionPerformed(ActionEvent e) {

			// Top 20 movies.
			if (e.getSource() == top20) {
				searchModel.clearMovies();

				for (int x = 0; x < topMovies.length; x++) {
					searchModel.addMovie(topMovies[x]);
				}

				// Now playing movies.
			} else if (e.getSource() == nowPlaying) {
				searchModel.clearMovies();

				for (int x = 0; x < nowMovies.length; x++) {
					searchModel.addMovie(nowMovies[x]);
				}

				// Upcoming movies.
			} else if (e.getSource() == upcoming) {
				searchModel.clearMovies();

				for (int x = 0; x < upcomingMovies.length; x++) {
					searchModel.addMovie(upcomingMovies[x]);
				}

				// Clear
			} else if (e.getSource() == btnClear) {

				searchModel.clearMovies();

			//Quit
			} else if (e.getSource() == quitItem) {
				System.exit(0);
			}

			searchModel.update();
		}
		
	}


	public static void main(String[] args) {

		new DatabaseInitGUI();

	}

}
