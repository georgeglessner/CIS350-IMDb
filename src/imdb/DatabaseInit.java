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

		// Checks command and calls associated method to handle request.
		// Search loop entered if a search is made. 
		while (true) {
			log("\nEnter a command (H for help):  ");
			String input = input().toLowerCase();

			if (input.equals("quit")) {
				log("Good bye!");
				break;
			
			} else if (input.equals("top")) {
				topMovies(movies);
			
			} else if (input.equals("now")) {
				nowMovies(movies);
			
			} else if (input.equals("upcoming")) {
				upcomingMovies(movies);
			
			} else if (input.equals("h")) {
				listMainCommands();
			
			} else {
				searchMovies(input);
			}
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
				
				// Error Handling. 
				// Checks to make sure input[0] is valid.
				// Gives user feedback if necessary.
				// Quits search if command 'quit' is typed. 
				//Back to main loop.
				// Lists current search if 'h' is typed. 
				while (inputID < 1 || inputID > 5) {
					input2 = input().toLowerCase();
					inputSplit = input2.split(" ");
					try {
						inputID = Integer.parseInt(inputSplit[0]);
						if (inputID > 5) {
							log("Movie number not recognized (try a number 1-5).");
						}
						if (inputID < 0) {
							log("Movie number not recognized (try a number 1-5).");
						}
					
					} catch (Exception e) {
						if (inputSplit[0].contains("quit")) {
							log("Exiting search... Goodbye!");
							break;
						} else if (inputSplit[0].equals("h")) {
							listCurrentSearch(movies, list);
							log("\nType the movie number and one of the following: Cast, Rating, Similar, Revenue, or Genre (eg: '1 cast').");
							log("Type 'h' to relist movies in current search.");
						} else {
							log("Movie command not recognized, try a number (1-5) and then a command (cast).");
						}
					}
				}
				
				// Condition can only be met if 'quit' is typed above. 
				// Exits search. Goes back to main loop.
				if (inputID == 0 || inputSplit[0].contains("quit")) {
					break;
				}
				
				// Error handling.
				// Checks to make sure input[1] is valid.
				// Gives user feedback if necessary. 
				// Stores input, movie, and movie name.

				String inputCommand = inputSplit[1];
				MovieDb movie = api.getMovies().getMovie(list[inputID], "en");
				String movieName = api.getMovies().getMovie(list[inputID], "en").getTitle();

				// Checks command and calls associated method to handle request. 
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
		log("Or enter title of movie to search");
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
