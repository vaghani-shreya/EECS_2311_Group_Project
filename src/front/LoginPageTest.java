package front;

import static org.junit.Assert.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class LoginPageTest {
	LoginPage loginPage;

	@BeforeEach
	void init() {
		loginPage = new LoginPage();
	}

	@Test
	void testValidLogin() {
		// Simulate a valid login attempt
		boolean result = loginPage.login("user", "password");

		// Verify that the login is successful
		assertTrue(result);
	}

	@Test
	void testInvalidLogin() {
		// Simulate an invalid login attempt
		boolean result = loginPage.login("invalidUser", "invalidPassword");

		// Verify that the login fails
		assertFalse(result);
	}

	@Test
	void testSetGetUsername() {
		String username = "testUser";
		loginPage.setUsername(username);

		assertEquals(username, loginPage.getUsername());
	}

	@Test
	void testSetGetPassword() {
		String password = "testPassword";
		loginPage.setPassword(password);

		assertEquals(password, loginPage.getPassword());
	}

}

