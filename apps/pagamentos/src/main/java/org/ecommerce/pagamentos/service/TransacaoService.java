package org.ecommerce.pagamentos.service;

import org.ecommerce.pagamentos.domain.Transacao;
import org.ecommerce.pagamentos.enums.StatusPagamento;
import org.ecommerce.pagamentos.repository.TransacoesRepository;
import org.ecommerce.pedidos.dtos.PedidoDTO;
import org.springframework.stereotype.Service;

@Service
public class TransacaoService {

    private final TransacoesRepository transacoesRepository;

    public TransacaoService(TransacoesRepository transacoesRepository) {
        this.transacoesRepository = transacoesRepository;
    }

    public void salvarTransacao(PedidoDTO pedidoDTO) {
        Transacao transacao = new Transacao(
                pedidoDTO.getIdCliente(),
                pedidoDTO.getId(),
                pedidoDTO.getIdCarrinho(),
                StatusPagamento.APROVADO,
                pedidoDTO.getValorTotal()
        );

        transacoesRepository.save(transacao);
    }

}
