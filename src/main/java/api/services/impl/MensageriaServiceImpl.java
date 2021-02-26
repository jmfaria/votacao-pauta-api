package api.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;

import api.services.MensageriaService;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class MensageriaServiceImpl implements MensageriaService {
	
	@Autowired
    private JmsTemplate jmsTemplate;
	
    @Override
	public void publicarMensagemNaFila(String mensagem) {
    	
    	log.info("Enviando mensagem para a fila. Mensagem: {}", mensagem);
    	jmsTemplate.convertAndSend("votacao.pauta.api", mensagem);    	
		
	}
}
