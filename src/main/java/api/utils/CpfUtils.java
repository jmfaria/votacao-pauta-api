package api.utils;

public class CpfUtils {

	public static Boolean ValidarCPF(String cpf) {
		int soma = 0;
		int dig1 = 0;
		int dig2 = 0;
		int conta1 = 0;
		int conta2 = 0;

		cpf = cpf.trim();
		cpf = cpf.replace(".", "").replace("-", "");

		if (cpf.length() != 11)
			return false;

		if (cpf.equals("00000000000") || cpf.equals("11111111111") || cpf.equals("22222222222")
				|| cpf.equals("33333333333") || cpf.equals("44444444444") || cpf.equals("55555555555")
				|| cpf.equals("66666666666") || cpf.equals("77777777777") || cpf.equals("88888888888")
				|| cpf.equals("99999999999") || (cpf.length() != 11))
			return (false);

		for (soma = 0, conta1 = 0, conta2 = 10; conta1 < (int) (cpf.length() - 2); conta1++, conta2--) {
			soma += ((new Integer(cpf.charAt(conta1)) - '0') * conta2);
		}
		dig1 = 11 - (soma % 11);
		if (dig1 > 9)
			dig1 = 0;

		for (soma = 0, conta1 = 0, conta2 = 11; conta1 < (int) (cpf.length() - 2); conta1++, conta2--) {
			soma += ((new Integer(cpf.charAt(conta1)) - '0') * conta2);
		}
		soma += dig1 * 2;
		dig2 = 11 - (soma % 11);
		if (dig2 > 9)
			dig2 = 0;

		if (((new Integer(cpf.charAt(9)) - '0') == dig1) && ((new Integer(cpf.charAt(10) - '0') == dig2))) {
			return true;
		} else {
			return false;
		}
	}
}
