package org.ecommerce.produtos.repository;

import org.ecommerce.produtos.domain.CarrinhoPedido;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CarrinhoRepository extends JpaRepository<CarrinhoPedido, Long> {
}
