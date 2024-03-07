package org.ecommerce.pedidos.controller;

import org.ecommerce.pedidos.exceptions.CarrinhoJaTemPedidoException;
import org.ecommerce.pedidos.exceptions.CarrinhoNaoEncontradoException;
import org.ecommerce.pedidos.exceptions.UsuarioNaoEncontradoException;
import org.ecommerce.pedidos.service.PedidoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/pedidos")
public class PedidoController {

    private final PedidoService pedidoService;

    public PedidoController(PedidoService pedidoService) {
        this.pedidoService = pedidoService;
    }

    @PostMapping("{id}")
    public ResponseEntity criarPedido(@PathVariable("id") Long id) {
        try {
            this.pedidoService.criarPedido(id);
            return ResponseEntity.status(HttpStatus.CREATED).build();
        } catch (CarrinhoJaTemPedidoException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Já existe um pedido em andamento para esse carrinho.");
        } catch (CarrinhoNaoEncontradoException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Não foi possível encontrar o carrinho com o id informado, verifique se foi digitado corretamente ou tente novamente.");
        } catch (UsuarioNaoEncontradoException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Não foi possível encontrar o usuário associado ao carrinho, verifique se foi digitado corretamente ou tente novamente.");
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Houve um erro interno ao tentar criar o pedido, tente novamente mais tarde.");
        }
    }
}
