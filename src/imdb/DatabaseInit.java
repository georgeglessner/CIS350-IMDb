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

	/** Main Loop. 
	 * @param args arguments*/
	public static void main(final String[] args) {
		listMainCommands();
		
		String input = "";

		// Checks command and calls associated method to handle request.
		// Search loop entered if a search is made. 
		while (true) {

			log("Enter a command (H for help):  ");
			
			try { 
				input = input().toLowerCase();
				runCommand(input);
			} catch (Exception e) {
				log("Error assigning input.");
			}
		}
	}

	/** Main commands
	 * @param input input*/
	public static void runCommand(final String input){
		if (input.equals("quit")) {
			log("Good bye!");
			System.exit(0);
		
		} else if (input.equals("top")) {
			topMovies(movies);
		
		} else if (input.equals("now")) {
			nowMovies(movies);
		
		} else if (input.equals("upcoming")) {
			upcomingMovies(movies);
		
		} else if (input.equals("h")) {
			listMainCommands();
		
		} else if (input.equals("")) {
			log("Empty input not valid.");
		}
			else {
			searchMovies(input);
		}
	}
	
	/** Lists top movies.
	 * @param movies movies */
	private static void topMovies(final TmdbMovies movies) {
		for (int i = 0; i < movies.getPopularMovies("en", 0).getResults().size(); i++) {
			log((i + 1) + ") " + movies.getPopularMovies("en", 0).getResults().get(i).getTitle());
		}
	}
	
	
	/** Lists now movies. 
	 * @param movies movies*/
	private static void nowMovies(final TmdbMovies movies) {
		for (int i = 0; i < movies.getNowPlayingMovies("en", 0).getResults().size(); i++) {
			log((i + 1) + ") " + movies.getNowPlayingMovies("en", 0).getResults().get(i).getTitle());
		}
		
	}
	
	
	/** Lists up and coming movies. 
	 * @param movies movies*/
	private static void upcomingMovies(final TmdbMovies movies) {
		for (int i = 0; i < movies.getUpcoming("en", 0).getResults().size(); i++) {
			log((i + 1) + ") " + movies.getUpcoming("en", 0).getResults().get(i).getTitle());
		}
	}

	
	/** Search loop. 
	 * @param input input*/
	private static void searchMovies(final String input) {
		int[] list = new int[6];
		
		List<MovieDb> movies = api.getSearch().searchMovie(input, 0, "en", false, 0).getResults();

		if (movies.size() == 0) {
			log("Title not found, try again.");
		} else {
			list = listCurrentSearch(movies, list);
			
			while (true) {
				log("\nType the movie number and one of the following:"); 
				log("Cast, Rating, Similar, Revenue, or Genre (eg: '1 cast').");
				log("Type 'h' to relist movies in current search.");
				int inputID = 0;
				String input2;
				String[] inputSplit = new String[2];
				
				
				while (inputID < 1 || inputID > 5) {
					input2 = input().toLowerCase();
					inputSplit = input2.split(" ");
					inputID = getSearchInputID(inputSplit[0]);
					
					// Error Handling. 
					// Checks to make sure inputSplit[0] is valid.
					// Gives user feedback if necessary.
					// Lists current search if 'h' is typed. 
					if (inputID == -1) {
						log("Exiting search... Goodbye!");
					} else if (inputID == -2) {
						listCurrentSearch(movies, list);
						log("\nType the movie number and one of the following: Cast, Rating, Similar, Revenue, or Genre (eg: '1 cast').");
						log("Type 'h' to relist movies in current search.");
					} else if (inputID == -3) {
						log("Movie command not recognized, try a number (1-5) and then a command (cast).");
					} else if (inputID == -4) {
						log("Movie number out of bounds (try a number 1-5).");
					}
					
					if (inputSplit[0].contains("quit")) 
						break;
				}
				
				// Condition can only be met if 'quit' is typed above. 
				// Exits search. Goes back to main loop.
				if (inputSplit[0].contains("quit")) {
					break;
				}
				 
				// Stores input, movie, and movie name.
				String inputCommand = inputSplit[1];
				MovieDb movie = api.getMovies().getMovie(list[inputID], "en");
				String movieName = api.getMovies().getMovie(list[inputID], "en").getTitle();

				// Checks command and calls associated method to handle request. 
				//searchCommand(inputID, inputCommand, list);
				if (inputID < 1 || inputID > 5) {
					log("Invalid movie ID!");
				
				} else if (inputCommand.contains("cast")) {
					getCast(list, inputID);
				
				} else if (inputCommand.contains("rating")) {
					getRating(movie, movieName);
				
				} else if (inputCommand.contains("similar")) {
					getSimilar(movie, movieName, list, inputID);
				
				} else if (inputCommand.contains("revenue")) {
					getRevenue(movie, movieName);
					
				} else if (inputCommand.contains("genre")) {
					getGenres(movie, movieName);
					
				} else {
					log("Command not recognized.");
				}
			}
		}
	}

	
	/** Gets search input ID. 
	 * @param input input
	 * @return inputID inputID*/
	private static int getSearchInputID(final String input) {
		int inputID;
		try {
			inputID = Integer.parseInt(input);
			
			if (inputID > 0 && inputID < 6) 
				return inputID;
			
			if (inputID < 0 || inputID > 5) 
				return -4;
				
		} catch (Exception e) {
			if (input.contains("quit")) {
				return -1;
			} else if (input.equals("h")) {
				return -2;
			} else {
				return -3;
			}
		}
		
		return 0;
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
		+ (cast.get(i).getCharacter() != "" ? cast.get(i).getCharacter() 
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
	public static String input() {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		try {
			return br.readLine();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	
	/** Main command list. */
	public static void listMainCommands() {
		log("List of commands");
		log("'H' = Help");
		log("'Top' = Displays current top 20 Movies");
		log("'Now' = List of movies currently playing");
		log("'Upcoming' = List of movies upcoming");
		log("'Quit' = To quit");
		log("Or enter title of movie to search\n");
	}
	
	
	/** Deprecated. */ 
	public static void listSearchCommands() {
		log("Type the number and one of the following:");
		log("'C' = Cast");
		log("'Rat' = Rating");
		log("'S' = Similar");
		log("'Rev' = Revenue");
		log("'G' = Genres");
		log("'H' = Help (relists movies)");
		log("Example: '1 c'"); 
	}

	
	/** Log.
	 * @param s string*/
	public static void log(final String s) {
		System.out.println(s);
	}
}
