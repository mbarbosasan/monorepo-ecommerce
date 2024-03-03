package org.ecommerce.produtos.dtos;

import java.util.List;

public class CriacaoCarrinho {
    private long idCliente;
    private List<Long> idsProdutos;

    public CriacaoCarrinho() {
    }

    public CriacaoCarrinho(long idCliente, List<Long> idsProdutos) {
        this.idCliente = idCliente;
        this.idsProdutos = idsProdutos;
    }

    public long getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(long idCliente) {
        this.idCliente = idCliente;
    }

    public List<Long> getIdsProdutos() {
        return idsProdutos;
    }

    public void setIdsProdutos(List<Long> idsProdutos) {
        this.idsProdutos = idsProdutos;
    }
}
