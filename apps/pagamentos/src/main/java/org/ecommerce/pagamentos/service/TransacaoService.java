package org.ecommerce.pagamentos.service;

import org.ecommerce.pagamentos.domain.Transacao;
import org.ecommerce.pagamentos.enums.StatusPagamento;
import org.ecommerce.pagamentos.rabbitmq.QueueSender;
import org.ecommerce.pagamentos.repository.TransacoesRepository;
import org.ecommerce.pedidos.dtos.PedidoDTO;
import org.springframework.stereotype.Service;

@Service
public class TransacaoService {

    private final TransacoesRepository transacoesRepository;
    private final QueueSender queueSender;

    public TransacaoService(TransacoesRepository transacoesRepository, QueueSender queueSender) {
        this.transacoesRepository = transacoesRepository;
        this.queueSender = queueSender;
    }

    public void salvarTransacao(PedidoDTO pedidoDTO) {
        Transacao transacao = new Transacao(
                pedidoDTO.getIdCliente(),
                pedidoDTO.getId(),
                pedidoDTO.getIdCarrinho(),
                StatusPagamento.APROVADO,
                pedidoDTO.getValorTotal()
        );

        try {
            transacoesRepository.save(transacao);

            enviarNotificacao(pedidoDTO.getIdCliente());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void enviarNotificacao(Long idCliente) {
        queueSender.send("Notificação de pagamento", "notificacoes");
        // Enviar notificação para o cliente
    }

}
