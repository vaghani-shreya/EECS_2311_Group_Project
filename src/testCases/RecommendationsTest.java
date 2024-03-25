package testCases;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import recommendation.Recommendation;

class RecommendationsTest {
	
	Recommendation recommendation;
	
	@Test
	void testGetShowName1() {
		recommendation = new Recommendation("Saving Private Ryan", 4);
		assertEquals("Saving Private Ryan", recommendation.getShowName());
	}
	
	@Test
	void testGetRating1() {
		recommendation = new Recommendation("Home Alone", 2);
		assertEquals(2, recommendation.getRating());
	}
	
	@Test
	void testGetReview1() {
		recommendation = new Recommendation("I Am Legend", 5);
		recommendation.setReview("This is a good movie!");
		assertNotEquals("AND", recommendation.getReview());
	}
	
	@Test
	void testGetShowName2() {
		recommendation = new Recommendation("Team America: World Police", 5);
//		recommendation.setShowName("Hook");
		assertEquals("Hook", recommendation.getShowName());
	}
	
	@Test
	void testGetRating2() {
		recommendation = new Recommendation("Iron Man", 1);
		assertEquals(1, recommendation.getRating());
	}
	
	@Test
	void testGetReview2() {
		recommendation = new Recommendation("Shrek", 0);
		recommendation.setReview("PROJECT!");
		assertEquals("PROJECT!", recommendation.getReview());
	}

}
