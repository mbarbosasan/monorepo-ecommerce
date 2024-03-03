package org.ecommerce.produtos.domain;

import jakarta.persistence.*;
import org.ecommerce.produtos.domain.Produto;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
public class CarrinhoPedido {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "id_cliente")
    private Long idCliente;
    @ManyToMany
    private List<Produto> produtos = new ArrayList<>();
    @Column
    private Double valorTotal;

public CarrinhoPedido() {
    }

    public CarrinhoPedido(Long idCliente, List<Produto> produtos, Double valorTotal) {
        this.idCliente = idCliente;
        this.produtos = produtos;
        this.valorTotal = valorTotal;
    }

    public CarrinhoPedido(Long id, Long idCliente, List<Produto> produtos, Double valorTotal) {
        this.id = id;
        this.idCliente = idCliente;
        this.produtos = produtos;
        this.valorTotal = valorTotal;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(Long idCliente) {
        this.idCliente = idCliente;
    }

    public List<Produto> getProdutos() {
        return produtos;
    }

    public void setProdutos(List<Produto> produtos) {
        this.produtos = produtos;
    }

    public Double getValorTotal() {
        return valorTotal;
    }

    public void setValorTotal(Double valorTotal) {
        this.valorTotal = valorTotal;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CarrinhoPedido that = (CarrinhoPedido) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "CarrinhoPedido{" +
                "id=" + id +
                ", idCliente=" + idCliente +
                ", produtos=" + produtos +
                ", valorTotal=" + valorTotal +
                '}';
    }
}
