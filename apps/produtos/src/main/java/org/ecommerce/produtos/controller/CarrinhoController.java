package org.ecommerce.produtos.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.ecommerce.pedidos.exceptions.UsuarioNaoEncontradoException;
import org.ecommerce.produtos.domain.CarrinhoPedido;
import org.ecommerce.produtos.domain.Produto;
import org.ecommerce.produtos.dtos.CriacaoCarrinho;
import org.ecommerce.produtos.repository.CarrinhoRepository;
import org.ecommerce.produtos.repository.ProdutosRepository;
import org.ecommerce.usuarios.domain.Usuario;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;

@RestController
@RequestMapping("/carrinho")
public class CarrinhoController {

    private final CarrinhoRepository carrinhoRepository;
    private final ProdutosRepository produtosRepository;

    public CarrinhoController(CarrinhoRepository carrinhoRepository, ProdutosRepository produtosRepository) {
        this.carrinhoRepository = carrinhoRepository;
        this.produtosRepository = produtosRepository;
    }

    @PostMapping
    public ResponseEntity criarCarrinho(@RequestBody CriacaoCarrinho criacaoCarrinho) {
        List<Produto> produtos = produtosRepository.findByIdIn(criacaoCarrinho.getIdsProdutos());

        try {
            buscarUsuarioPorID(criacaoCarrinho.getIdCliente());

            Double valorTotal = produtos.stream().mapToDouble(Produto::getPreco).sum();
            CarrinhoPedido carrinhoPedido = new CarrinhoPedido(criacaoCarrinho.getIdCliente(), produtos, valorTotal);
            carrinhoRepository.save(carrinhoPedido);
            return ResponseEntity.ok(carrinhoPedido);
        } catch (UsuarioNaoEncontradoException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Não foi possível encontrar o usuário associado ao carrinho, verifique se foi digitado corretamente ou tente novamente.");
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Houve um erro interno ao tentar criar o carrinho, tente novamente mais tarde.");
        }


    }

    @GetMapping("{id}")
    public ResponseEntity buscarCarrinho(@PathVariable("id") Long id) {
        CarrinhoPedido carrinhoPedido = carrinhoRepository.findById(id).orElse(null);
        if (carrinhoPedido == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(carrinhoPedido);
    }

    public void buscarUsuarioPorID(Long id) throws UsuarioNaoEncontradoException {
        HttpRequest request = HttpRequest.newBuilder().uri(URI.create("http://localhost:8084/usuarios/" + id))
                .header("Content-Type", "application/json")
                .GET()
                .build();

        try {
            HttpResponse<String> response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
            if (response.statusCode() != 200) {
                throw new UsuarioNaoEncontradoException("Usuário não encontrado");
            }
            new ObjectMapper().readValue(response.body(), Usuario.class);
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException("Erro ao buscar usuário");
        }
    }
}
