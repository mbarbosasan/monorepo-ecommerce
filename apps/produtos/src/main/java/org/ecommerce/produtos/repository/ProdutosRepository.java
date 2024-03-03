package org.ecommerce.produtos.repository;

import org.ecommerce.produtos.domain.Produto;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProdutosRepository extends JpaRepository<Produto, Long>{
    List<Produto> findByIdIn(List<Long> ids);
}
