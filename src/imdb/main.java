package imdb;

import info.movito.themoviedbapi.TmdbApi;
import info.movito.themoviedbapi.TmdbMovies;
import info.movito.themoviedbapi.model.MovieDb;



public class main {
	
	
	public static void main(String[] args){

		//sTmdbApi movies = new TmdbApi("dbae952f0b2a4b3711bf5808e97c4769");
		TmdbMovies movies = new TmdbApi("dbae952f0b2a4b3711bf5808e97c4769").getMovies();
		MovieDb movie = movies.getMovie(5353, "en");
		
		for(int i = 0; i<20; i++){
			log(movies.getPopularMovies("en", 0).getResults().get(i).getOriginalTitle());
		}

//		static MovieDb movie = movies.getMovie(5353, "en");
		//log("" + movies.getPopularMovies("en", 0).getResults().size());
	}

	public static void log(String s) {
		System.out.println(s);
	}
}