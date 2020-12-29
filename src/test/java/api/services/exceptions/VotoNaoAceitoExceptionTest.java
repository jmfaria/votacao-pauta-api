package api.services.exceptions;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import api.services.impl.exceptions.VotoNaoAceitoException;

@SpringBootTest
@ActiveProfiles("test")
public class VotoNaoAceitoExceptionTest {

	@Test
	public void textException() {

		Assertions.assertThrows(VotoNaoAceitoException.class, () -> {
			throw new VotoNaoAceitoException();
		});
		
	}
}
