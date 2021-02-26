package api.services.impl;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import api.entities.PermissaoVoto;
import api.services.ApiPermissaoVotoService;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class ApiPermissaoVotoServiceImpl implements ApiPermissaoVotoService {

	public String associadoComPermissaoParaVotar(String cpf) {

		log.info("Pedindo à API externa, permissão de voto para o CPF({})", cpf);
		RestTemplate restTemplate = new RestTemplate();
		PermissaoVoto permissaoVoto = restTemplate.getForObject("https://user-info.herokuapp.com/users/" + cpf,
				PermissaoVoto.class);

		return permissaoVoto.getStatus();
	}
}
