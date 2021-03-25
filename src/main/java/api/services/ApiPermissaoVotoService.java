package api.services;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import api.entities.PermissaoVoto;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class ApiPermissaoVotoService {

	@Value("${api-permissao-voto.url}")
	private String urlPermissaoVoto;

	public boolean associadoComPermissaoParaVotar(String cpf) {

		log.info("Pedindo à API externa, permissão de voto para o CPF({})", cpf);
		PermissaoVoto permissaoVoto = new RestTemplate().getForObject(urlPermissaoVoto + cpf, PermissaoVoto.class);

		return "ABLE_TO_VOTE".equals(permissaoVoto.getStatus());
	}
}
