package api.services.exceptions;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import api.services.impl.exceptions.AssociadoJaVotouPautaException;

@SpringBootTest
@ActiveProfiles("test")
public class AssociadoJaVotouPautaExceptionTest {
	
	@Test
	public void textException() {

		Assertions.assertThrows(AssociadoJaVotouPautaException.class, () -> {
			throw new AssociadoJaVotouPautaException();
		});
	}

}
