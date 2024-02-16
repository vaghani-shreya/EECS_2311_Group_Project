package front;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ResetPasswordPageTest {
	ResetPasswordPage resetPasswordPage;
	VerificationPage verificationPage;
	
	@BeforeEach
	void init() {
		resetPasswordPage = new ResetPasswordPage(verificationPage);
	}
	
	@Test
	void testSetGetUsername() {
		String username = "testUser";
		resetPasswordPage.setUsername(username);

		assertEquals(username, resetPasswordPage.getUsername());
	}

	@Test
	void testSetGetPassword() {
		String password = "testPassword";
		resetPasswordPage.setPassword(password);

		assertEquals(password, resetPasswordPage.getPassword());
	}

}
