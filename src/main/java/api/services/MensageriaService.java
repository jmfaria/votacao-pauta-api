package api.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;

import api.services.MensageriaService;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class MensageriaService {
	
	@Autowired
    private KafkaTemplate<String, String> kafkaTemplate;
	
	public void publicarMensagemNaFila(String mensagem) {
    	
    	log.info("Enviando mensagem para a fila. Mensagem: {}", mensagem);
		this.sendMessage(mensagem);
	}
             
    public void sendMessage(String mensagem) {
        
        ListenableFuture<SendResult<String, String>> future = 
          kafkaTemplate.send("votacao.pauta.api", mensagem);
    	
        future.addCallback(new ListenableFutureCallback<SendResult<String, String>>() {

            @Override
            public void onSuccess(SendResult<String, String> result) {
            	log.info("Mensagem enviada=[{}] com offset=[{}]", 
            			mensagem, result.getRecordMetadata().offset());
            }
            @Override
            public void onFailure(Throwable ex) {
                log.error("Incapaz de enviar a mensagem=[{}] devido a: {}", 
                		mensagem, ex.getMessage());
            }
        });
    }
}
