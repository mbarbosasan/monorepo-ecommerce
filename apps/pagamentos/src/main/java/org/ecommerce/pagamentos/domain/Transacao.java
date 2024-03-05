package org.ecommerce.pagamentos.domain;

import jakarta.persistence.*;
import org.ecommerce.pagamentos.enums.StatusPagamento;

import java.util.Objects;

@Entity
public class Transacao {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private Long id_cliente;
    @Column(nullable = false, unique = true)
    private Long id_pedido;
    @Column(nullable = false, unique = true)
    private Long id_carrinho;
    @Column(nullable = false)
    private StatusPagamento status;
    @Column(nullable = false)
    private Double valor;

    public Transacao() {
    }

    public Transacao(Long id_cliente, Long id_pedido, Long id_carrinho, Double valor) {
        this.id_cliente = id_cliente;
        this.id_pedido = id_pedido;
        this.id_carrinho = id_carrinho;
        this.valor = valor;
    }

    public Transacao(Long id_cliente, Long id_pedido, Long id_carrinho, StatusPagamento status, Double valor) {
        this.id_cliente = id_cliente;
        this.id_pedido = id_pedido;
        this.id_carrinho = id_carrinho;
        this.status = status;
        this.valor = valor;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId_cliente() {
        return id_cliente;
    }

    public void setId_cliente(Long id_cliente) {
        this.id_cliente = id_cliente;
    }

    public Long getId_pedido() {
        return id_pedido;
    }

    public void setId_pedido(Long id_pedido) {
        this.id_pedido = id_pedido;
    }

    public Long getId_carrinho() {
        return id_carrinho;
    }

    public void setId_carrinho(Long id_carrinho) {
        this.id_carrinho = id_carrinho;
    }

    public StatusPagamento getStatus() {
        return status;
    }

    public void setStatus(StatusPagamento status) {
        this.status = status;
    }

    public Double getValor() {
        return valor;
    }

    public void setValor(Double valor) {
        this.valor = valor;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Transacao that = (Transacao) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
