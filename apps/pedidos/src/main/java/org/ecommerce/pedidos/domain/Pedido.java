package org.ecommerce.pedidos.domain;

import jakarta.persistence.*;
import org.ecommerce.pedidos.enums.StatusPedido;

import java.util.Objects;

@Entity
public class Pedido {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true)
    private Long id_carrinho;
    @Column
    private Long id_cliente;
    @Column
    private StatusPedido status;

    public Pedido() {
    }

    public Pedido(Long id_carrinho, Long id_cliente, StatusPedido status) {
        this.id_carrinho = id_carrinho;
        this.id_cliente = id_cliente;
        this.status = status;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId_carrinho() {
        return id_carrinho;
    }

    public void setId_carrinho(Long id_carrinho) {
        this.id_carrinho = id_carrinho;
    }

    public StatusPedido getStatus() {
        return status;
    }

    public void setStatus(StatusPedido status) {
        this.status = status;
    }

    public Long getId_cliente() {
        return id_cliente;
    }

    public void setId_cliente(Long id_cliente) {
        this.id_cliente = id_cliente;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Pedido pedido = (Pedido) o;
        return Objects.equals(id, pedido.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "Pedido{" +
                "id=" + id +
                ", id_carrinho=" + id_carrinho +
                ", id_cliente=" + id_cliente +
                ", status=" + status +
                '}';
    }
}
