package imdb;

import info.movito.themoviedbapi.TmdbApi;
import info.movito.themoviedbapi.TmdbMovies;
import info.movito.themoviedbapi.model.MovieDb;

public class main {
	static TmdbMovies movies = new TmdbApi("dbae952f0b2a4b3711bf5808e97c4769").getMovies();
	static MovieDb movie = movies.getMovie(5353, "en");
	
	public static void main(String[] args){

	}

	public void log(String s) {
		System.out.println(s);
	}
}