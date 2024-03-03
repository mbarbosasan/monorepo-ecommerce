package org.ecommerce.produtos.repository;

import org.ecommerce.produtos.domain.Produto;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProdutosRepository extends JpaRepository<Produto, Long>{
}
