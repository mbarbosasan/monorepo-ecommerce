package org.ecommerce.pedidos.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.ecommerce.pedidos.domain.Pedido;
import org.ecommerce.pedidos.domain.StatusPedido;
import org.ecommerce.pedidos.dtos.CarrinhoDTO;
import org.ecommerce.pedidos.repository.PedidoRepository;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

@RestController
@RequestMapping("/pedidos")
public class PedidoController {

    private final PedidoRepository pedidoRepository;

    public PedidoController(PedidoRepository pedidoRepository) {
        this.pedidoRepository = pedidoRepository;
    }

    @PostMapping("{id}")
    public ResponseEntity criarPedido(@PathVariable("id") Long id) {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:8080/carrinho/" + id))
                .header("Content-Type", "application/json")
                .GET()
                .build();

        try {
            HttpResponse<String> response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() != 200) {
                return new ResponseEntity<>("Não foi possivel encontrar o carrinho de pedidos, verifique se foi digitado corretamente e tente novamente", HttpStatus.NOT_FOUND);
            }

            ObjectMapper mapper = new ObjectMapper();
            CarrinhoDTO carrinhoDTO = mapper.readValue(response.body(), CarrinhoDTO.class);

            Pedido pedido = new Pedido(carrinhoDTO.getId(), carrinhoDTO.getIdCliente(), StatusPedido.AGUARDANDO_PAGAMENTO);

            try {
                pedidoRepository.save(pedido);
            } catch (DataIntegrityViolationException e) {
                return new ResponseEntity<>("Já existe um pedido realizado para esse carrinho.", HttpStatus.BAD_REQUEST);
            } catch (Exception e) {
                return new ResponseEntity<>("Não foi possivel criar o pedido, tente novamente", HttpStatus.INTERNAL_SERVER_ERROR);
            }


            return ResponseEntity.ok(pedido);
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
