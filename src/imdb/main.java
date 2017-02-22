package imdb;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import info.movito.themoviedbapi.TmdbApi;
import info.movito.themoviedbapi.TmdbMovies;
import info.movito.themoviedbapi.model.core.MovieResultsPage;

public class main {
	static TmdbApi api = new TmdbApi("dbae952f0b2a4b3711bf5808e97c4769"); // API

	public static void main(String[] args) {
		
		log("Temp");
		listOfCommands();

		while (true) {
			log("\nEnter a command (H for help):  ");
			String input = input().toLowerCase();

			if (input.equals("h")) {
				listOfCommands();
			} else if (input.equals("top")) {
				TmdbMovies movies = api.getMovies();
				for (int i = 0; i < movies.getPopularMovies("en", 0).getResults().size(); i++) {
					log((i + 1) + ") " + movies.getPopularMovies("en", 0).getResults().get(i).getTitle());
				}
			} else if (input.equals("now")) {
				TmdbMovies movies = api.getMovies();

				for (int i = 0; i < movies.getNowPlayingMovies("en", 0).getResults().size(); i++) {
					log((i + 1) + ") " + movies.getNowPlayingMovies("en", 0).getResults().get(i).getTitle());
				}
			} else if (input.equals("upcoming")) {
				TmdbMovies movies = api.getMovies();

				for (int i = 0; i < movies.getUpcoming("en", 0).getResults().size(); i++) {
					log((i + 1) + ") " + movies.getUpcoming("en", 0).getResults().get(i).getTitle());
				}
			} else if (input.equals("quit")) {
				log("Good bye!");
				break;
			} else {
				int[] list = new int[6];

				MovieResultsPage movies = api.getSearch().searchMovie(input, 0, "en", false, 0);

				if (movies.getResults().size() == 0) {
					log("Title not found, try again.");
				} else {
					for (int i = 0; i < (movies.getResults().size() > 5 ? 5 : movies.getResults().size()); i++) {
						log((i + 1) + ") " + movies.getResults().get(i).getTitle() + " ("
								+ movies.getResults().get(i).getReleaseDate().split("-")[0] + ")");
						list[i + 1] = movies.getResults().get(i).getId();
					}
					while (true) {
						System.out.print("Type the number and one of the following: Cast, Rating, Similar, Revenue (eg: '1 cast') ");
						String input2 = input().toLowerCase();

						String[] inputSplit = input2.split(" ");
						int inputID = Integer.parseInt(inputSplit[0]);
						String inputCommand = inputSplit[1];
						
						String movieName = api.getMovies().getMovie(list[inputID], "en").getTitle();

						if (inputID < 1 || inputID > 5) {
							log("Invalid movie ID!");
						} else if (inputCommand.contains("cast")) {
							for(int i = 0; i < (api.getMovies().getCredits(list[inputID]).getCast().size() > 5 ? 5 : api.getMovies().getCredits(list[inputID]).getCast().size()); i++){
								log((i + 1) + ") " +api.getMovies().getCredits(list[inputID]).getCast().get(i).getName() + " who plays " + (api.getMovies().getCredits(list[inputID]).getCast().get(i).getCharacter() != "" ? api.getMovies().getCredits(list[inputID]).getCast().get(i).getCharacter() : "unknown"));
							}
						} else if (inputCommand.contains("rating")) {
							log("Users give '" + movieName +"' an average of " + api.getMovies().getMovie(list[inputID], "en").getVoteAverage() + " out of 10!");
						} else if (inputCommand.contains("similar")) {
							log("Some movies similar to '" + movieName + "' are: ");

							for(int i = 0; i < (api.getMovies().getSimilarMovies(list[inputID], "en", 0).getResults().size()  > 5 ? 5 : api.getMovies().getSimilarMovies(list[inputID], "en", 0).getResults().size()); i++){
								log((i + 1) + ") " +api.getMovies().getSimilarMovies(list[inputID], "en", 0).getResults().get(i).getTitle());
							}
						} else if (inputCommand.contains("revenue")) {
							log(movieName + " earned " + api.getMovies().getMovie(list[inputID], "en").getRevenue() + " in the box office!");
						} else if (inputCommand.equals("quit")) {
							log("Good bye!");
							break;
						}
					}
				}
			}
		}
	}

	public static String input() {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		try {
			return br.readLine();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static void listOfCommands() {
		log("List of commands");
		log("'H' = Help");
		log("'Top' = Displays current top 20 Movies");
		log("'Now' = List of movies currently playing");
		log("'Upcoming' = List of movies upcoming");
		log("'Quit' = To quit");
		log("Or enter title of movie to search");
	}

	public static void log(String s) {
		System.out.println(s);
	}
}