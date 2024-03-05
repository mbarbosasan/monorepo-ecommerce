package org.ecommerce.pagamentos.domain;

import jakarta.persistence.*;
import org.ecommerce.pagamentos.enums.StatusPagamento;

import java.util.Objects;

@Entity
public class Transacoes {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private Long id_cliente;
    @Column(nullable = false, unique = true)
    private Long id_pedido;
    @Column(nullable = false, unique = true)
    private Long id_carinho;
    @Column(nullable = false)
    private StatusPagamento status;
    @Column(nullable = false)
    private Double valor;

    public Transacoes() {
    }

    public Transacoes(Long id, Long id_cliente, Long id_pedido, Long id_carinho, StatusPagamento status, Double valor) {
        this.id = id;
        this.id_cliente = id_cliente;
        this.id_pedido = id_pedido;
        this.id_carinho = id_carinho;
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

    public Long getId_carinho() {
        return id_carinho;
    }

    public void setId_carinho(Long id_carinho) {
        this.id_carinho = id_carinho;
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
        Transacoes that = (Transacoes) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
