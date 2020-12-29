package api.services.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;

import api.services.MensageriaService;

@Service
public class MensageriaServiceImpl implements MensageriaService {
	
	private static final Logger log = LoggerFactory.getLogger(MensageriaServiceImpl.class);
	@Autowired
    private JmsTemplate jmsTemplate;
	
    @Override
	public void publicarMensagemNaFila(String mensagem) {
    	
    	log.info("Enviando mensagem para a fila. Mensagem: {}", mensagem);
    	jmsTemplate.convertAndSend("votacao.pauta.api", mensagem);    	
		
	}
}
