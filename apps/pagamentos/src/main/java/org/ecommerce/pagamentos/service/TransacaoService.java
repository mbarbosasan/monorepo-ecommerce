package org.ecommerce.pagamentos.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jdk.swing.interop.SwingInterOpUtils;
import org.ecommerce.pagamentos.domain.Transacao;
import org.ecommerce.pagamentos.enums.StatusPagamento;
import org.ecommerce.pagamentos.rabbitmq.QueueSender;
import org.ecommerce.pagamentos.repository.TransacoesRepository;
import org.ecommerce.pedidos.dtos.PedidoDTO;
import org.ecommerce.pedidos.enums.StatusPedido;
import org.springframework.stereotype.Service;

@Service
public class TransacaoService {

    private final TransacoesRepository transacoesRepository;
    private final QueueSender queueSender;

    private final Integer UM_MINUTO = 60000;

    public TransacaoService(TransacoesRepository transacoesRepository, QueueSender queueSender) {
        this.transacoesRepository = transacoesRepository;
        this.queueSender = queueSender;
    }

    public void salvarTransacao(PedidoDTO pedidoDTO) {
        Transacao transacao = new Transacao(
                pedidoDTO.getIdCliente(),
                pedidoDTO.getId(),
                pedidoDTO.getIdCarrinho(),
                StatusPagamento.PENDENTE,
                pedidoDTO.getValorTotal()
        );

        try {
            transacoesRepository.save(transacao);

            System.out.println("Processando transação...");
            Thread.sleep(UM_MINUTO);
            System.out.println("Transação processada");
            transacao.setStatus(StatusPagamento.APROVADO);
            transacoesRepository.save(transacao);
            pedidoDTO.setStatus(StatusPedido.PAGAMENTO_APROVADO);
            enviarNotificacao(pedidoDTO);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void enviarNotificacao(PedidoDTO pedidoDTO) {

        try {
            System.out.println("Enviando email de pagamento");
            ObjectMapper mapper = new ObjectMapper();
            queueSender.send(mapper.writeValueAsString(pedidoDTO), "notificacoes");
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        // Enviar notificação para o cliente
    }

}
