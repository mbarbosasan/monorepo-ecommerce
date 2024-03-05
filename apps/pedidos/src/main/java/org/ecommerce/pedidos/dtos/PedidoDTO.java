package org.ecommerce.pedidos.dtos;

import org.ecommerce.pedidos.enums.StatusPedido;

public class PedidoDTO {
    private Long id;
    private Long idCarrinho;
    private Long idCliente;
    private StatusPedido status;
    private Double valorTotal;

    public PedidoDTO() {
    }

    public PedidoDTO(Long id, Long idCarrinho, Long idCliente, StatusPedido status, Double valorTotal) {
        this.id = id;
        this.idCarrinho = idCarrinho;
        this.idCliente = idCliente;
        this.status = status;
        this.valorTotal = valorTotal;
    }

    public PedidoDTO(Long idCarrinho, Long idCliente, StatusPedido status, Double valorTotal) {
        this.idCarrinho = idCarrinho;
        this.idCliente = idCliente;
        this.status = status;
        this.valorTotal = valorTotal;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getIdCarrinho() {
        return idCarrinho;
    }

    public void setIdCarrinho(Long idCarrinho) {
        this.idCarrinho = idCarrinho;
    }

    public Long getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(Long idCliente) {
        this.idCliente = idCliente;
    }

    public StatusPedido getStatus() {
        return status;
    }

    public void setStatus(StatusPedido status) {
        this.status = status;
    }

    public Double getValorTotal() {
        return valorTotal;
    }

    public void setValorTotal(Double valorTotal) {
        this.valorTotal = valorTotal;
    }

    @Override
    public String toString() {
        return "PedidoDTO{" +
                "id=" + id +
                ", idCarrinho=" + idCarrinho +
                ", idCliente=" + idCliente +
                ", status='" + status + '\'' +
                ", valorTotal=" + valorTotal +
                '}';
    }
}
