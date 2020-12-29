package api.services.exceptions;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import api.services.impl.exceptions.ApiExternalNaoPermitiuVotoException;

@SpringBootTest
@ActiveProfiles("test")

public class ApiExternalNaoPermitiuVotoExceptionTest {

	@Test
	public void textException() {

		Assertions.assertThrows(ApiExternalNaoPermitiuVotoException.class, () -> {
			throw new ApiExternalNaoPermitiuVotoException();
		});
	}

}
