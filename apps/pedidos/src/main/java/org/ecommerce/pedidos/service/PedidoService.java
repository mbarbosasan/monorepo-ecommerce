package org.ecommerce.pedidos.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.ecommerce.pedidos.domain.Pedido;
import org.ecommerce.pedidos.dtos.CarrinhoDTO;
import org.ecommerce.pedidos.dtos.PedidoDTO;
import org.ecommerce.pedidos.enums.StatusPedido;
import org.ecommerce.pedidos.exceptions.CarrinhoJaTemPedidoException;
import org.ecommerce.pedidos.exceptions.CarrinhoNaoEncontradoException;
import org.ecommerce.pedidos.exceptions.UsuarioNaoEncontradoException;
import org.ecommerce.pedidos.rabbitmq.QueueSender;
import org.ecommerce.pedidos.repository.PedidoRepository;
import org.ecommerce.usuarios.domain.Usuario;
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
    private final QueueSender queueSender;
    public PedidoService(PedidoRepository pedidoRepository, QueueSender queueSender) {
        this.pedidoRepository = pedidoRepository;
        this.queueSender = queueSender;
    }

    public void criarPedido(Long id) throws CarrinhoNaoEncontradoException, CarrinhoJaTemPedidoException, UsuarioNaoEncontradoException {
        try {
            CarrinhoDTO carrinhoDTO = buscaCarrinhoPorId(id);
            Usuario usuario = buscaUsuarioPorId(carrinhoDTO.getIdCliente());

            Pedido pedido = new Pedido(carrinhoDTO.getId(), carrinhoDTO.getIdCliente(), StatusPedido.AGUARDANDO_PAGAMENTO);
            try {
                Pedido pedidoSaved = pedidoRepository.save(pedido);
                PedidoDTO pedidoDTO = new PedidoDTO(pedidoSaved.getId(), pedidoSaved.getId_carrinho(), pedidoSaved.getId_cliente(), pedidoSaved.getStatus(), carrinhoDTO.getValorTotal());
                pedidoDTO.setId(pedidoSaved.getId());
                enviarParaPagamento(pedidoDTO);
                enviarEmailPedidoEfetuado(pedidoDTO);
            } catch (DataIntegrityViolationException e) {
                throw new CarrinhoJaTemPedidoException("Erro ao salvar pedido");
            }

        } catch (CarrinhoNaoEncontradoException e) {
            throw new CarrinhoNaoEncontradoException(e.getMessage());
        } catch (UsuarioNaoEncontradoException e) {
            throw new UsuarioNaoEncontradoException(e.getMessage());
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

    private Usuario buscaUsuarioPorId(Long id) throws UsuarioNaoEncontradoException, RuntimeException {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:8084/usuarios" + id))
                .header("Content-Type", "application/json")
                .GET()
                .build();

        try {
            HttpResponse<String> response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() != 200) {
                throw new UsuarioNaoEncontradoException("Usuário não encontrado");
            }

            ObjectMapper mapper = new ObjectMapper();
            return mapper.readValue(response.body(), Usuario.class);
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }

    }

    private void enviarParaPagamento(PedidoDTO pedidoDTO) {
        try {
            System.out.println("Pedido recebido, enviando o pedido para pagamento");
            ObjectMapper mapper = new ObjectMapper();
            String carrinhoJSON = mapper.writeValueAsString(pedidoDTO);
            this.queueSender.send(carrinhoJSON, "pagamentos");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    private void enviarEmailPedidoEfetuado(PedidoDTO pedidoDTO) {
        try {
            System.out.println("Pedido recebido, enviando email de confirmação para o usuário");
            ObjectMapper mapper = new ObjectMapper();
            String carrinhoJSON = mapper.writeValueAsString(pedidoDTO);
            this.queueSender.send(carrinhoJSON, "notificacoes");
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
