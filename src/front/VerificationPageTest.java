package front;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class VerificationPageTest {
	VerificationPage verificationPage;
	ForgotPasswordPage forgotpasswordPage;

	
	@BeforeEach
	void init() {
		verificationPage = new VerificationPage(forgotpasswordPage);		
	}
	
	//Correct Username
	@Test
	void testCorrectUsername() {
		// Simulate a valid username
		boolean result = verificationPage.check("user",12345);

		// Verify that the login is successful
		assertTrue(result);
	}
	
	//Incorrect Username
	@Test
	void testIncorrectUsername() {
		// Simulate a invalid username
		boolean result = verificationPage.check("users",12345);

		// Verify that the login is unsuccessful
		assertFalse(result);
	}
	
	//Incorrect code
		@Test
		void testIncorrectCode() {
			// Simulate a invalid username
			boolean result = verificationPage.check("users",1234);

			// Verify that the login is unsuccessful
			assertFalse(result);
		}
	
	//Check if set and get user name works
	@Test
	void testSetGetUsername() {
		String username = "testUser";
		verificationPage.setUsername(username);

		assertEquals(username, verificationPage.getUsername());
	}
	
	//Check if set and get code works
		@Test
		void testSetGetCode() {
			String username = "12345";
			verificationPage.setUsername(username);

			assertEquals(username, verificationPage.getUsername());
		}
}
