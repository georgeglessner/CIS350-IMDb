package imdb;

import static org.junit.Assert.*;

import org.junit.Test;

public class DatabaseInitTest {

	@Test
	public void testTopMovies() {
		int x = DatabaseInit.runCommand("top");
		
		if (x != 1)
			fail("Top movies is failing.");
	}
	
	@Test
	public void testNowMovies() {
		int x = DatabaseInit.runCommand("now");
		
		if (x != 1)
			fail("Now is failing.");
	}
	
	@Test
	public void testUpcomingMovies() {
		int x = DatabaseInit.runCommand("upcoming");
		
		if (x != 1)
			fail("Upcoming is failing.");
	}
	
	@Test
	public void testHelpMain() {
		int x = DatabaseInit.runCommand("h");
		
		if (x != 2)
			fail("Help is failing.");
	}
	
	@Test
	public void testEmptyMain() {
		int x = DatabaseInit.runCommand("");
		
		if (x != 3)
			fail("Empty command is failing.");
	}
	
	@Test
	public void testQuitMain() {
		int x = DatabaseInit.runCommand("quit");
		
		if (x != 0)
			fail("Main quit is failing.");
	}
	
	@Test
	public void testSearch1() {
		int x = DatabaseInit.runCommand("Lord of the");
		if (x != 4)
			fail("Search is failing.");
	}
	
	@Test
	public void testSearch2() {
		int x = DatabaseInit.runCommand("Into the");
		if (x != 4)
			fail("Search is failing.");
	}
	
	@Test
	public void testSearch3() {
		int x = DatabaseInit.runCommand("Harry Potter");
		if (x != 4)
			fail("Search is failing.");
	}
	
	@Test
	public void testSearchInputGood1() {
		int x = DatabaseInit.getSearchInputID("1");
		if (x < 1 || x > 5)
			fail("Search is failing.");
	}
	
	@Test
	public void testSearchInputGood2() {
		int x = DatabaseInit.getSearchInputID("2");
		if (x < 1 || x > 5)
			fail("Search is failing.");
	}
	
	@Test
	public void testSearchInputGood3() {
		int x = DatabaseInit.getSearchInputID("3");
		if (x < 1 || x > 5)
			fail("Search is failing.");
	}
	
	@Test
	public void testSearchInputGood4() {
		int x = DatabaseInit.getSearchInputID("4");
		if (x < 1 || x > 5)
			fail("Search is failing.");
	}
	
	@Test
	public void testSearchInputGood5() {
		int x = DatabaseInit.getSearchInputID("5");
		if (x < 1 || x > 5)
			fail("Search is failing.");
	}
	
	@Test
	public void testSearchInputOutOfBoundsSmall() {
		int x = DatabaseInit.getSearchInputID("0");
		if (x != -4)
			fail("Lower search bounds are failing.");
	}
	
	@Test
	public void testSearchInputOutOfBoundsBig() {
		int x = DatabaseInit.getSearchInputID("6");
		if (x != -4)
			fail("Upper search bounds are failing.");
	}
	
	@Test
	public void testSearchInputQuit() {
		int x = DatabaseInit.getSearchInputID("quit");
		if (x != -1)
			fail("Quit search is failing.");
	}
	
	@Test
	public void testSearchInputHelp() {
		int x = DatabaseInit.getSearchInputID("h");
		if (x != -2)
			fail("Search help is failing.");
	}
	
	@Test
	public void testSearchInputIDRandom1() {
		int x = DatabaseInit.getSearchInputID("asdfasd asdfa");
		if (x != -3)
			fail("Random search command is failing.");
	}
	
	@Test
	public void testSearchInputIDRandom2() {
		int x = DatabaseInit.getSearchInputID("12asdf asdfa");
		if (x != -3)
			fail("Random search command is failing.");
	}
	
	@Test
	public void testSearchInputIDRandom3() {
		int x = DatabaseInit.getSearchInputID("!74a ;alksdf");
		if (x != -3)
			fail("Random search command is failing.");
	}

	@Test
	public void testSearchCommandIDFail1() {
		int x = DatabaseInit.getSearchInputCommand(0, "cast");
		if (x != 0)
			fail("Command inputID bounds check is failing.");
	}
	
	@Test
	public void testSearchCommandIDFail2() {
		int x = DatabaseInit.getSearchInputCommand(6, "cast");
		if (x != 0)
			fail("Command inputID bounds check is failing.");
	}
	
	@Test
	public void testSearchCast() {
		int x = DatabaseInit.getSearchInputCommand(1, "cast");
		if (x != 1)
			fail("Search cast is failing.");
	}
	
	@Test
	public void testSearchRating() {
		int x = DatabaseInit.getSearchInputCommand(1, "rating");
		if (x != 2)
			fail("Search rating is failing.");
	}
	
	@Test
	public void testSearchSimilar() {
		int x = DatabaseInit.getSearchInputCommand(1, "similar");
		if (x != 3)
			fail("Search similar is failing.");
	}
	
	@Test
	public void testSearchRevenue() {
		int x = DatabaseInit.getSearchInputCommand(1, "revenue");
		if (x != 4)
			fail("Search revenue is failing.");
	}
	
	@Test
	public void testSearchGenre() {
		int x = DatabaseInit.getSearchInputCommand(1, "genre");
		if (x != 5)
			fail("Search genre is failing.");
	}
	
	@Test
	public void testSearchCommandRandom1() {
		int x = DatabaseInit.getSearchInputCommand(1, "asdf");
		if (x != 6)
			fail("Search no commmand found is failing.");
	}
	
	@Test
	public void testSearchCommandRandom2() {
		int x = DatabaseInit.getSearchInputCommand(1, "141dfa asdf ");
		if (x != 6)
			fail("Search no command found is failing.");
	}
	
	@Test
	public void testSearchCommandRandom3() {
		int x = DatabaseInit.getSearchInputCommand(1, "'`141dfa aas32 ");
		if (x != 6)
			fail("Search no command found is failing.");
	}
	
	@Test
	public void randomTRexTest() {
		// Feedback integer.
		int z;
		
		// Now.
		z = DatabaseInit.runCommand("now");
		if (z != 1)
			fail("Now is failing.");
		
		// Top.
		z = DatabaseInit.runCommand("top");
		if (z != 1)
			fail("Top is failing.");
		
		// Search 'the dark'.
		z = DatabaseInit.runCommand("the dark");
		if (z != 4)
			fail("Search is failing.");
		
		// Search help.
		z = DatabaseInit.getSearchInputID("h");
		if (z != -2)
			fail("Search help is failing.");
		
		// Search command bounds check.
		z = DatabaseInit.getSearchInputCommand(-13, "cast");
		if (z != 0)
			fail("Command inputID bounds check is failing.");
		
		// Search command similar.
		z = DatabaseInit.getSearchInputCommand(3, "similar");
		if (z != 3)
			fail("Search similar is failing.");
		
		z = DatabaseInit.getSearchInputID("quit");
		if (z != -1)
			fail("Quit search is failing.");
		
		z = DatabaseInit.runCommand("quit");
		if (z != 0)
			fail("Main quit is failing.");
	}
	
	@Test
	public void testMainCommands() {
		int x = DatabaseInit.listMainCommands();
		if (x != 2)
			fail("Main commands are failing.");
	}
	
	@Test
	public void testRunNow() {
		int x = DatabaseInit.run("now");
		if (x != 1)
			fail("Run now is failing.");
	}
	
	@Test
	public void testRunTop() {
		int x = DatabaseInit.run("top");
		if (x != 1)
			fail("Run top is failing.");
	}
	
	@Test
	public void TestRunUpcoming() {
		int x = DatabaseInit.run("upcoming");
		if (x != 1)
			fail("Run upcoming is failing.");
	}
	
	@Test
	public void TestMainArg() {
		String args[] = { "now" };
		try {
			DatabaseInit.main(args);
		} catch (Exception e) {
			fail("Main now arg is failing.");
		}
	}
	
	@Test
	public void TestSearchArgCast() {
		int x = DatabaseInit.searchMovies("lord of the", true, "1 cast");
		if (x != 1)
			fail("Seach is failing.");
	}
	
	@Test
	public void TestSearchArgRating() {
		int x = DatabaseInit.searchMovies("lord of the", true, "1 rating");
		if (x != 1)
			fail("Seach is failing.");
	}
	
	@Test
	public void TestSearchArgSimilar() {
		int x = DatabaseInit.searchMovies("lord of the", true, "1 similar");
		if (x != 1)
			fail("Seach is failing.");
	}
	
	@Test
	public void TestSearchArgRevenue() {
		int x = DatabaseInit.searchMovies("lord of the", true, "1 revenue");
		if (x != 1)
			fail("Seach is failing.");
	}
	
	@Test
	public void TestSearchArgGenres() {
		int x = DatabaseInit.searchMovies("lord of the", true, "1 genre");
		if (x != 1)
			fail("Seach is failing.");
	}
	
	@Test
	public void TestInput() {
		try {
			DatabaseInit.input("yo");
		} catch (Exception e) {
			fail("Input is failing.");
		}
	}
	
}