package api.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;

import api.services.MensageriaService;

@Service
public class MensageriaServiceImpl implements MensageriaService {
	
	@Autowired
    private JmsTemplate jmsTemplate;
	
	public MensageriaServiceImpl() {}

    @Override
	public void publicarMensagemNaFila(String mensagem) {
    	
    	jmsTemplate.convertAndSend("votacao.pauta.api", mensagem);    	
		
	}

}
