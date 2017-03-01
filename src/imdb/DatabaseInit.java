package imdb;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

import info.movito.themoviedbapi.TmdbApi;
import info.movito.themoviedbapi.TmdbMovies;
import info.movito.themoviedbapi.model.MovieDb;
import info.movito.themoviedbapi.model.people.PersonCast;

/**********************************************************************
 * Program that allows user to find information about top 20 movies,
 * upcoming movies, movies currently out, and the ability 
 * to search for any movie.
 * 
 *@author George Glessner, Gabe Vansolkema, Trace Remick
 *@version 2/29/17
 **********************************************************************/
public class DatabaseInit {
	
	/** API. */
	static TmdbApi api = new TmdbApi("dbae952f0b2a4b3711bf5808e97c4769");
	
	/** Movies. */
	static TmdbMovies movies = api.getMovies();

	/** Main. 
	 * @param args arguments*/
	public static void main(final String[] args) {
		// List main commands.
		listMainCommands();
		
		// Input.
		String input;
		
		// Main loop.
		// Checks command and calls associated method to handle request.
		// Search loop entered if a search is made. 
		while (true) {
			
			// Allows argument input for testing.
			try {
				input = args[0];
			} catch (Exception e) {
				input = "";
			}
			
			log("Enter a command (H for help):  ");
			
			// Breaks if argument is inputed (only JUnit tests). 
			if (!input.equals("")) {
				run(input);
				break;
			}
			
			try {
				input = input("").toLowerCase();
			} catch (Exception e) {
				log("Error assigning input.");
			}
		
			run(input);
		}
		
	}

	public static int run(String input) {
		int status = -1;
		
		try {
			status = runCommand(input);
		} catch (Exception e) {
			status = -1;
		}	
		
		// Pulled these out so I could test runCommand.
		if (status == 4)
			searchMovies(input, false, "");
		if (status == 0)
			System.exit(0);
		
		return status;
	}

	/** Main commands
	 * @param input input
	 * @return */
	public static int runCommand(final String input){
		int status;
		
		if (input.equals("quit")) {
			log("Good bye!");
			status = 0;
		
		} else if (input.equals("top")) {
			status = topMovies(movies);
		
		} else if (input.equals("now")) {
			status = nowMovies(movies);
		
		} else if (input.equals("upcoming")) {
			status = upcomingMovies(movies);
		
		} else if (input.equals("h")) {
			status = listMainCommands();
		
		} else if (input.equals("")) {
			status = 3;
			log("Empty input not valid.");
		
		} else {
			status = 4;
		}
		
		return status;
	}
	
	/** Lists top movies.
	 * @param movies movies */
	private static int topMovies(final TmdbMovies movies) {
		int status = -1;
		
		try { 
			for (int i = 0; i < movies.getPopularMovies("en", 0).getResults().size(); i++) {
				log((i + 1) + ") " + movies.getPopularMovies("en", 0).getResults().get(i).getTitle());
			}
			status = 1;
		} catch (Exception e) {
			status = -1;
		}
		
		log("");
		return status;
	}
	
	
	/** Lists now movies. 
	 * @param movies movies*/
	private static int nowMovies(final TmdbMovies movies) {
		int status = -1;
		
		try {
			for (int i = 0; i < movies.getNowPlayingMovies("en", 0).getResults().size(); i++) {
				log((i + 1) + ") " + movies.getNowPlayingMovies("en", 0).getResults().get(i).getTitle());
			}
			status = 1;
		} catch (Exception e) {
			status = -1;
		}
		
		log("");
		return status;
	}
	
	
	/** Lists up and coming movies. 
	 * @param movies movies*/
	private static int upcomingMovies(final TmdbMovies movies) {
		int status = -1;
		
		try {
			for (int i = 0; i < movies.getUpcoming("en", 0).getResults().size(); i++) {
				log((i + 1) + ") " + movies.getUpcoming("en", 0).getResults().get(i).getTitle());
			}
			status = 1;
		} catch (Exception e) {
			status = -1;
		}
		
		log("");
		return status;
	}

	
	/** Search loop. 
	 * @param input input*/
	public static int searchMovies(final String input, boolean test, String testCommand) {
		log("Searching for movies with: " + input);
		int status = 0;
		int[] list = new int[6];
		List<MovieDb> movies = api.getSearch().searchMovie(input, 0, "en", false, 0).getResults();

		if (movies.size() == 0) {
			log("Title not found, try again.");
		} else {
			list = listCurrentSearch(movies, list);
			
			// Main search loop.
			while (true) {
				if (!testCommand.equals(""))
					status = getFinalSearchCommand(testCommand, list);
				else 
					status = getFinalSearchCommand("", list);
					
				// If input is 'h'.
				// Lists current search.
				if (status == -2)
					listCurrentSearch(movies, list);
				
				// If input is 'quit'.
				// Exits search. Goes back to main loop.
				if (status == -1 || test)
					break;
				
			}
		}
		
		return status;
	}
	

	public static int getFinalSearchCommand(String input, int[] list) {
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
				
			input = "";
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

	/** Gets search command ID. 
	 * @param inputID inputID
	 * @param inputCommand input command
	 * @return commandID command ID*/
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

	/** Gets search input ID. 
	 * @param input input
	 * @return inputID input ID*/
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

	
	/** Lists current search. 
	 * @param movies movies
	 * @param list list
	 * @return list list*/
	private static int[] listCurrentSearch(final List<MovieDb> movies, final int[] list) {
		for (int i = 0; i < (movies.size() > 5 ? 5 : movies.size()); i++) {
			log((i + 1) + ") " + movies.get(i).getTitle() + " ("
					+ movies.get(i).getReleaseDate().split("-")[0] + ")");
			list[i + 1] = movies.get(i).getId();
		}
		return list;
	}
	

	/** Gets cast for specified movie (uses list and inputID to do this). 
	 * @param list list
	 * @param inputID ID*/
	private static void getCast(final int[] list, final int inputID) {
		List<PersonCast> cast = api.getMovies().getCredits(list[inputID]).getCast();
		
		for (int i = 0; i < (cast.size() > 5 ? 5 : cast.size()); i++) {
			log((i + 1) + ") " + cast.get(i).getName() + " who plays "  
		+ (!cast.get(i).getCharacter().equals("") ? cast.get(i).getCharacter() 
			: "unknown"));
		}
	}
	

	/** Gets rating for specified movie. 
	 * @param movie movie
	 * @param movieName movie name*/
	private static void getRating(final MovieDb movie, final String movieName) {
		log("Users give '" + movieName + "' an average of " 
				+ movie.getVoteAverage() + " out of 10!");
		
	}

	
	/** Gets similar movies for specified movie. 
	 * @param movie movie
	 * @param movieName movie name
	 * @param list list of movies
	 * @param inputID movie id*/
	private static void getSimilar(final MovieDb movie, final String movieName, final int[] list, final int inputID) {
		log("Some movies similar to '" + movieName + "' are: ");
		List<MovieDb> similarMovies = api.getMovies().getSimilarMovies(list[inputID], "en", 0).getResults();
		
		for (int i = 0; i < (similarMovies.size()  > 5 ? 5 : similarMovies.size()); i++) {
			log((i + 1) + ") " + similarMovies.get(i).getTitle());
		}
	}

	
	/** Gets revenue for specified movie. 
	 * @param movie movie
	 * @param movieName movie name*/
	
	private static void getRevenue(final MovieDb movie, final String movieName) {
		log(movieName + " earned " + movie.getRevenue() + " in the box office!");
		
	}

	
	/** Gets genres for specified movie. 
	 * @param movie movie 
	 * @param movieName movie name*/
	private static void getGenres(final MovieDb movie, final String movieName) {
		log("Genres for " + movieName + ":");
		
		for (int i = 0; i < (movie.getGenres().size() > 3 ? 3 : movie.getGenres().size()); i++) {
			log((i + 1) + ") " + movie.getGenres().get(i).getName());
		}
	}

	
	/** Input. 
	 * @return br input*/
	public static String input(String input) {
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

	
	/** Main command list. */
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
	
	
	/** Log.
	 * @param s string*/
	public static void log(final String s) {
		System.out.println(s);
	}
}
