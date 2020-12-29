package api.utils;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles("test")
public class CpfUtilsTest {
	
	@Test
	void validarCPF(){
				
		assertFalse(CpfUtils.validarCPF(""));
		assertFalse(CpfUtils.validarCPF("9"));
		assertFalse(CpfUtils.validarCPF("99999999999"));
		assertFalse(CpfUtils.validarCPF("9999999999999999999999"));
		assertTrue(CpfUtils.validarCPF("56592423080"));	
		
	}

}
