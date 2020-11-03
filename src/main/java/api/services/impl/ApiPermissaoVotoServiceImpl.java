package api.services.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import api.entities.PermissaoVoto;
import api.services.ApiPermissaoVotoService;

@Service
public class ApiPermissaoVotoServiceImpl implements ApiPermissaoVotoService {

	private static Logger log = LoggerFactory.getLogger(ApiPermissaoVotoServiceImpl.class);
	
	public String associadoComPermissaoParaVotar(String cpf) {

		log.info("Pedindo à API externa, permissão de voto para o CPF({})", cpf);
		RestTemplate restTemplate = new RestTemplate();
		PermissaoVoto permissaoVoto = restTemplate.getForObject("https://user-info.herokuapp.com/users/" + cpf,
				PermissaoVoto.class);

		return permissaoVoto.getStatus();
	}
}
