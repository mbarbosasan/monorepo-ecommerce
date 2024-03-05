package org.ecommerce.pedidos.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.ecommerce.pedidos.domain.Pedido;
import org.ecommerce.pedidos.dtos.CarrinhoDTO;
import org.ecommerce.pedidos.enums.StatusPedido;
import org.ecommerce.pedidos.exceptions.CarrinhoJaTemPedidoException;
import org.ecommerce.pedidos.exceptions.CarrinhoNaoEncontradoException;
import org.ecommerce.pedidos.repository.PedidoRepository;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

@Service
public class PedidoService {

    private final PedidoRepository pedidoRepository;

    public PedidoService(PedidoRepository pedidoRepository) {
        this.pedidoRepository = pedidoRepository;
    }

    public void criarPedido(Long id) throws CarrinhoNaoEncontradoException, CarrinhoJaTemPedidoException {
        try {
            CarrinhoDTO carrinho = buscaCarrinhoPorId(id);
            Pedido pedido = new Pedido(carrinho.getId(), carrinho.getIdCliente(), StatusPedido.AGUARDANDO_PAGAMENTO);

            try {
                pedidoRepository.save(pedido);
            } catch (DataIntegrityViolationException e) {
                throw new CarrinhoJaTemPedidoException("Erro ao salvar pedido");
            }

        } catch (CarrinhoNaoEncontradoException e) {
            throw new CarrinhoNaoEncontradoException(e.getMessage());
        }

    }

    private CarrinhoDTO buscaCarrinhoPorId(Long id) throws RuntimeException {
        HttpRequest request = HttpRequest.newBuilder().uri(URI.create("http://localhost:8080/carrinho/" + id)).header("Content-Type", "application/json").GET().build();

        try {
            HttpResponse<String> response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() != 200) {
                throw new CarrinhoNaoEncontradoException("Carrinho não encontrado");
            }

            ObjectMapper mapper = new ObjectMapper();
            return mapper.readValue(response.body(), CarrinhoDTO.class);

        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}