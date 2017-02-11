package imdb;

import info.movito.themoviedbapi.TmdbApi;
import info.movito.themoviedbapi.TmdbMovies;
import info.movito.themoviedbapi.model.MovieDb;

import java.util.ArrayList;
import java.util.List;



public class main {
	
	// API
	static TmdbApi api = new TmdbApi("dbae952f0b2a4b3711bf5808e97c4769");
	
	public static void main(String[] args){

		log("List of commands: ");
		//sTmdbApi movies = new TmdbApi("dbae952f0b2a4b3711bf5808e97c4769");
		TmdbMovies movies = api.getMovies();
		MovieDb movie = movies.getMovie(5353, "en");
		
		for(int i = 0; i<20; i++){
			log(movies.getPopularMovies("en", 0).getResults().get(i).getOriginalTitle());
			
		}

//		static MovieDb movie = movies.getMovie(5353, "en");
		//log("" + movies.getPopularMovies("en", 0).getResults().size());
	}
	
	public void ListOfCommands(){
		log("List of commands");
		log("'H' = Help");
		log("'Top 20 Movies' = Displays current top 20 Movies");
		log("'Now Playing' = List of movies currently playing");
	}

	public static void log(String s) {
		System.out.println(s);
	}
}