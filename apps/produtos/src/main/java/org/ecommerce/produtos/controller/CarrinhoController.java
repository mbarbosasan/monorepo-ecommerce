package org.ecommerce.produtos.controller;

import org.ecommerce.produtos.domain.CarrinhoPedido;
import org.ecommerce.produtos.domain.Produto;
import org.ecommerce.produtos.dtos.CriacaoCarrinho;
import org.ecommerce.produtos.repository.CarrinhoRepository;
import org.ecommerce.produtos.repository.ProdutosRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
        Double valorTotal = produtos.stream().mapToDouble(Produto::getPreco).sum();
        CarrinhoPedido carrinhoPedido = new CarrinhoPedido(criacaoCarrinho.getIdCliente(), produtos, valorTotal);
        carrinhoRepository.save(carrinhoPedido);
        return ResponseEntity.ok(carrinhoPedido);
    }

    @GetMapping("{id}")
    public ResponseEntity buscarCarrinho(@PathVariable("id") Long id) {
        CarrinhoPedido carrinhoPedido = carrinhoRepository.findById(id).orElse(null);
        if (carrinhoPedido == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(carrinhoPedido);
    }
}
