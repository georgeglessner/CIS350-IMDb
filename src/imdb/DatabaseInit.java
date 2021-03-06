package imdb;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import info.movito.themoviedbapi.TmdbApi;
import info.movito.themoviedbapi.TmdbMovies;
import info.movito.themoviedbapi.TmdbTV;
import info.movito.themoviedbapi.model.MovieDb;
import info.movito.themoviedbapi.model.people.PersonCast;
import info.movito.themoviedbapi.model.tv.TvSeries;

/**********************************************************************
 * Program that allows user to find information about top 20 movies, upcoming
 * movies, movies currently out, and the ability to search for any movie.
 * 
 * @author George Glessner, Gabe Vansolkema, Trace Remick
 * @version 2/29/17
 **********************************************************************/
public class DatabaseInit {
	
	public ArrayList<MovieDb> currMovies;
	
	public ArrayList<TvSeries> currShows;

	public DatabaseInit() {
		currMovies = new ArrayList<MovieDb>();
		currShows = new ArrayList<TvSeries>();
	}

	/** API. */
	static TmdbApi api = new TmdbApi("dbae952f0b2a4b3711bf5808e97c4769");

	/** Movies. */
	TmdbMovies movies = api.getMovies();
	
	/** TV shows. */
	TmdbTV shows = api.getTvSeries();
	
	

	/**
	 * Main.
	 * 
	 * @param args
	 *            
	 */
	public static void main(final String[] args) {
		new DatabaseInitGUI();
	}


	/**
	 * Lists top movies.
	 * 
	 * @param tmovies movies
	 * @return String[] moviesList
	 */
	public String[] topMovies(final TmdbMovies tmovies) {
		currMovies.clear();
		String[] moviesList = new String[20];
		List<MovieDb> results = tmovies.getPopularMovies("en", 0).getResults();

		try {
			for (int i = 0; i < results.size(); i++) {
				MovieDb m = results.get(i);
				currMovies.add(m);
				moviesList[i] = m.getTitle();
			}
		} catch (Exception e) {
			String[] errorList = new String[] {
				"Top movies search did not work."
			};
			return errorList;
		}

		return moviesList;
	}

	/**
	 * Lists now movies.
	 * 
	 * @param tmovies movies
	 * @return moviesList
	 */
	public String[] nowMovies(final TmdbMovies tmovies) {
		currMovies.clear();
		String[] moviesList = new String[20];
		List<MovieDb> results = tmovies.getNowPlayingMovies("en", 0).getResults();
		

		try {
			for (int i = 0; i < results.size(); i++) {
				MovieDb m = results.get(i);
				currMovies.add(m);
				moviesList[i] = m.getTitle();
			}
		} catch (Exception e) {
			String[] errorList = new String[] {
				"Now playing movies search did not work."
			};
			return errorList;
		}

		return moviesList;
	}

	/**
	 * Lists up and coming movies.
	 * 
	 * @param tmovies movies
	 * @return String[] moviesList
	 */
	public String[] upcomingMovies(final TmdbMovies tmovies) {
		currMovies.clear();
		String[] moviesList = new String[20];
		List<MovieDb> results = tmovies.getUpcoming("en", 0).getResults();

		try {
			for (int i = 0; i < results.size(); i++) {
				MovieDb m = results.get(i);
				currMovies.add(m);
				moviesList[i] = m.getTitle();
			}
			
		} catch (Exception e) {
			String[] errorList = new String[] {
				"Now playing movies search did not work."
			};
			return errorList;
		}

		return moviesList;
	}

	/**
	 * Searches movies.
	 * 
	 * @param input input
	 * @return status
	 */
	public String[] searchMovies(final String input) {
		currMovies.clear();
		String[] moviesList;
		List<MovieDb> searchMovies = api.getSearch().searchMovie(input, 0, "en", false, 0).getResults(); 
		int maxMovie = 20;
		
		try {
			if (searchMovies.size() < 20)
				maxMovie = searchMovies.size();
			
			moviesList = new String[maxMovie];
			
			for (int x = 0; x < maxMovie; x++) {
				MovieDb m = searchMovies.get(x);
				currMovies.add(m);
				moviesList[x] = m.getTitle();
			}
			
		} catch (Exception e) {
			String[] errorList = new String[] {
					"Movie search did not work."
				};
				return errorList;
		} 

		if (searchMovies.size() == 0) {
			String[] errorList = new String[] {
					"Movie search yielded no results."
				};
				return errorList;
		} 
//
//			// Main search loop.
//			while (true) {
//				if (!testCommand.equals(""))
//					status = getFinalSearchCommand(testCommand, list);
//				else
//					status = getFinalSearchCommand("", list);
//
//				// If input is 'h'.
//				// Lists current search.
//				if (status == -2)
//					listCurrentSearch(smovies, list);
//
//				// If input is 'quit'.
//				// Exits search. Goes back to main loop.
//				if (status == -1 || test)
//					break;
//
//			}

		return moviesList;
	}
	
	/**
	 * Lists top shows.
	 * 
	 * @param shows shows
	 * @return String[] showsList
	 */
	public String[] topShows(final TmdbTV shows) {
		currShows.clear();
		String[] showsList = new String[20];
		List<TvSeries> results = shows.getPopular("en", 0).getResults();

		try {
			for (int i = 0; i < results.size(); i++) {
				TvSeries s = results.get(i);
				currShows.add(s);
				showsList[i] = s.getName();
			}
		} catch (Exception e) {
			String[] errorList = new String[] {
				"Top movies search did not work."
			};
			return errorList;
		}

		return showsList;
	}
	
	/**
	 * Lists now shows.
	 * 
	 * @param shows shows
	 * @return String[] showsList
	 */
	public String[] nowShows(final TmdbTV shows) {
		currShows.clear();
		String[] moviesList = new String[20];
		List<TvSeries> results = shows.getOnTheAir("en", 0).getResults();

		try {
			for (int i = 0; i < results.size(); i++) {
				TvSeries m = results.get(i);
				currShows.add(m);
				moviesList[i] = m.getName();
			}
		} catch (Exception e) {
			String[] errorList = new String[] {
				"Now playing movies search did not work."
			};
			return errorList;
		}

		return moviesList;
	}

	/**
	 * Lists up and coming shows.
	 * 
	 * @param shows shows
	 * @return String[] showsList
	 */
	public String[] upcomingShows(final TmdbTV shows) {
		currShows.clear();
		String[] showsList = new String[20];
		List<TvSeries> results = shows.getAiringToday("en", 0, null).getResults();

		try {
			for (int i = 0; i < results.size(); i++) {
				TvSeries m = results.get(i);
				currShows.add(m);
				showsList[i] = m.getName();
			}
			
		} catch (Exception e) {
			String[] errorList = new String[] {
				"Now playing movies search did not work."
			};
			return errorList;
		}

		return showsList;
	}
	
	/**
	 * Searches shows.
	 * 
	 * @param input input
	 * @return status
	 */
	public String[] searchShows(final String input) {
		currShows.clear();
		String[] showsList;
		List<TvSeries> searchShows = api.getSearch().searchTv(input, "en", 0).getResults();
		int maxShows = 20;
		
		try {
			if (searchShows.size() < 20)
				maxShows = searchShows.size();
			
			showsList = new String[maxShows];
			
			for (int x = 0; x < maxShows; x++) {
				TvSeries m = searchShows.get(x);
				currShows.add(m);
				showsList[x] = m.getName();
			}
			
		} catch (Exception e) {
			String[] errorList = new String[] {
					"TV Show search did not work."
				};
				return errorList;
		} 

		if (searchShows.size() == 0) {
			String[] errorList = new String[] {
					"TV Show search yielded no results."
				};
				return errorList;
		} 
	
		return showsList;
	}
	
	/**
	 * Final Search Command.
	 * 
	 * @param input input
	 * @param list list
	 * @return status
	 */
	public static int getFinalSearchCommand(final String input, final int[] list) {

		log("\nType a movie number and one of the following:");
		log("Cast, Rating, Similar, Revenue, or Genre (eg: '1 cast').");
		log("Type 'h' to relist movies in current search. Type 'quit' to quit search.");
		int inputID = 0;
		String input2;
		String[] inputSplit = new String[2];

		while (inputID < 1 || inputID > 5) {
			// Allows string input for testing.
			if (input.equals(""))
				input2 = input("").toLowerCase();
			else
				input2 = input;

			inputSplit = input2.split(" ");
			inputID = getSearchInputID(inputSplit[0]);

			// If 'quit' is typed.
			if (inputID == -1)
				return -1;

			if (inputID == -2)
				return -2;
		}

		// Stores input, movie, and movie name.
		String inputCommand = inputSplit[1];
		MovieDb movie = api.getMovies().getMovie(list[inputID], "en");
		String movieName = api.getMovies().getMovie(list[inputID], "en").getTitle();

		// Checks command and calls associated method to handle request.
		int commandID = getSearchInputCommand(inputID, inputCommand);

		// Gets cast, rating, similar, revenue, and genre based on command.
		if (commandID == 0)
			log("Invalid movie ID!");
		else if (commandID == 1)
			getCast(list, inputID);
		else if (commandID == 2)
			getRating(movie, movieName);
		else if (commandID == 3)
			getSimilar(movie, movieName, list, inputID);
		else if (commandID == 4)
			getRevenue(movie, movieName);
		else if (commandID == 5)
			getGenres(movie, movieName);
		else if (commandID == 6)
			log("Movie command " + inputCommand + " not recognized. Try a valid command (cast).");

		return 1;
	}

	/**
	 * Gets search command ID.
	 * 
	 * @param inputID
	 *            inputID
	 * @param inputCommand
	 *            input command
	 * @return commandID command ID
	 */
	public static int getSearchInputCommand(final int inputID, final String inputCommand) {
		if (inputID < 1 || inputID > 5)
			return 0;
		else if (inputCommand.contains("cast"))
			return 1;
		else if (inputCommand.contains("rating"))
			return 2;
		else if (inputCommand.contains("similar"))
			return 3;
		else if (inputCommand.contains("revenue"))
			return 4;
		else if (inputCommand.contains("genre"))
			return 5;

		return 6;
	}

	/**
	 * Gets search input ID.
	 * 
	 * @param input
	 *            input
	 * @return inputID input ID
	 */
	public static int getSearchInputID(final String input) {
		int inputID = 0;

		try {
			inputID = Integer.parseInt(input);

			if (inputID > 0 && inputID < 6)
				return inputID;

			if (inputID < 1 || inputID > 5)
				log("Movie number out of bounds (try a number 1-5).");
			return -4;

		} catch (Exception e) {
			if (input.contains("quit")) {
				log("Exiting search... Goodbye!");
				return -1;
			} else if (input.equals("h")) {
				log("\nType a movie number and one of the following: Cast, Rating, Similar, Revenue, or Genre (eg: '1 cast').");
				log("Type 'h' to relist movies in current search. Type 'quit' to exit search.");
				return -2;
			} else {
				log("Movie command not recognized, try a number (1-5) and then a command (cast).");
				return -3;
			}
		}
	}

	/**
	 * Lists current search.
	 * 
	 * @param cmovies
	 *            movies
	 * @param list
	 *            list
	 * @return list list
	 */
	private static int[] listCurrentSearch(final List<MovieDb> cmovies, final int[] list) {
		for (int i = 0; i < (cmovies.size() > 5 ? 5 : cmovies.size()); i++) {
			log((i + 1) + ") " + cmovies.get(i).getTitle() + " (" + cmovies.get(i).getReleaseDate().split("-")[0] + ")");
			list[i + 1] = cmovies.get(i).getId();
		}
		return list;
	}

	/**
	 * Gets cast for specified movie (uses list and inputID to do this).
	 * 
	 * @param list
	 *            list
	 * @param inputID
	 *            ID
	 */
	private static void getCast(final int[] list, final int inputID) {
		List<PersonCast> cast = api.getMovies().getCredits(list[inputID]).getCast();

		for (int i = 0; i < (cast.size() > 5 ? 5 : cast.size()); i++) {
			log((i + 1) + ") " + cast.get(i).getName() + " who plays "
					+ (!cast.get(i).getCharacter().equals("") ? cast.get(i).getCharacter() : "unknown"));
		}
	}

	/**
	 * Gets rating for specified movie.
	 * 
	 * @param movie
	 *            movie
	 * @param movieName
	 *            movie name
	 */
	private static void getRating(final MovieDb movie, final String movieName) {
		log("Users give '" + movieName + "' an average of " + movie.getVoteAverage() + " out of 10!");

	}

	/**
	 * Gets similar movies for specified movie.
	 * 
	 * @param movie
	 *            movie
	 * @param movieName
	 *            movie name
	 * @param list
	 *            list of movies
	 * @param inputID
	 *            movie id
	 */
	private static void getSimilar(final MovieDb movie, final String movieName, final int[] list, final int inputID) {
		log("Some movies similar to '" + movieName + "' are: ");
		List<MovieDb> similarMovies = api.getMovies().getSimilarMovies(list[inputID], "en", 0).getResults();

		for (int i = 0; i < (similarMovies.size() > 5 ? 5 : similarMovies.size()); i++) {
			log((i + 1) + ") " + similarMovies.get(i).getTitle());
		}
	}

	/**
	 * Gets revenue for specified movie.
	 * 
	 * @param movie
	 *            movie
	 * @param movieName
	 *            movie name
	 */

	private static void getRevenue(final MovieDb movie, final String movieName) {
		log(movieName + " earned " + movie.getRevenue() + " in the box office!");

	}

	/**
	 * Gets genres for specified movie.
	 * 
	 * @param movie
	 *            movie
	 * @param movieName
	 *            movie name
	 */
	private static void getGenres(final MovieDb movie, final String movieName) {
		log("Genres for " + movieName + ":");

		for (int i = 0; i < (movie.getGenres().size() > 3 ? 3 : movie.getGenres().size()); i++) {
			log((i + 1) + ") " + movie.getGenres().get(i).getName());
		}
	}

	/**
	 * Input.
	 * 
	 * @return br input
	 */
	public static String input(final String input) {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

		if (!input.equals(""))
			return input;

		try {
			return br.readLine();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return null;
	}

	/** Main command list. 
	 * 
	 * @return status status
	 * /
	
	public static int listMainCommands() {
		log("List of commands");
		log("'H' = Help");
		log("'Top' = Displays current top 20 Movies");
		log("'Now' = List of movies currently playing");
		log("'Upcoming' = List of movies upcoming");
		log("'Quit' = To quit");
		log("Or enter title of movie to search\n");

		return 2;
	}

	/**
	 * Log.
	 * 
	 * @param s
	 *            string
	 */
	public static void log(final String s) {
		System.out.println(s);
	}
}