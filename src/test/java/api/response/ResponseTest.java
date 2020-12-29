package api.response;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;

import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import api.exception.ApiExceptionHandler.Erro;

@SpringBootTest
//@AutoConfigureMockMvc
@ActiveProfiles("test")
@TestMethodOrder(OrderAnnotation.class)
public class ResponseTest {
	
	private Response<String> response;
	
	@Test
	public void testarResponse() {
		
		response = new Response<String>();
		
		response.setData("exemplo");
		response.setErrors(new ArrayList<Erro>());	
		
		assertEquals(new ArrayList<Erro>(), response.getErrors());
		assertEquals("exemplo", response.getData());
	}
	
}
