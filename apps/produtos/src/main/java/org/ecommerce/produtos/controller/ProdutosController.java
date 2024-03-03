package org.ecommerce.produtos.controller;

import org.ecommerce.produtos.domain.Produto;
import org.ecommerce.produtos.repository.ProdutosRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/produtos")
public class ProdutosController {

    private final ProdutosRepository produtosRepository;

    public ProdutosController(ProdutosRepository produtosRepository) {
        this.produtosRepository = produtosRepository;
    }

    @GetMapping
    public ResponseEntity<List<Produto>> buscarProdutos() {
        List<Produto> lista = produtosRepository.findAll();

        if (lista.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(lista);
    }

    @GetMapping
    @RequestMapping("/{id}")
    public ResponseEntity<Produto> buscarProdutoPorId(@PathVariable Long id) {
        return ResponseEntity.of(produtosRepository.findById(id));
    }

    @PostMapping
    public ResponseEntity<Produto> criarProduto(@RequestBody Produto produto) {

        return ResponseEntity.ok(produtosRepository.save(produto));
    }
}
