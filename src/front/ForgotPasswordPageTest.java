package front;
import static org.junit.Assert.*;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class ForgotPasswordPageTest {
	ForgotPasswordPage forgotpasswordPage;
	LoginPage loginPage;
	
	@BeforeEach
	void init() {
		forgotpasswordPage = new ForgotPasswordPage(loginPage);
	}
	
	//Correct Username
	@Test
	void testCorrectUsername() {
		// Simulate a valid username
		boolean result = forgotpasswordPage.username("anusham@my.yorku.ca");

		// Verify that the login is successful
		assertTrue(result);
	}
	
	//Incorrect Username
	@Test
	void testIncorrectUsername() {
		// Simulate a invalid username
		boolean result = forgotpasswordPage.username("incorrect");

		// Verify that the login is unsuccessful
		assertFalse(result);
	}
	
	//Check if set and get user name works
	@Test
	void testSetGetUsername() {
		String username = "testUser";
		forgotpasswordPage.setUsername(username);

		assertEquals(username, forgotpasswordPage.getUsername());
	}

}
