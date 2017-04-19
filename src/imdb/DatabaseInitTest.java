package imdb;

import static org.junit.Assert.*;

import org.junit.Test;

public class DatabaseInitTest {
	
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
	public void TestInput() {
		try {
			DatabaseInit.input("yo");
		} catch (Exception e) {
			fail("Input is failing.");
		}
	}
	
}