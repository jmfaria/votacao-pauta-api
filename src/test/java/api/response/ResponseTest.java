package api.response;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import api.exception.ApiExceptionHandler.Erro;

@SpringBootTest(classes = {Response.class})
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
