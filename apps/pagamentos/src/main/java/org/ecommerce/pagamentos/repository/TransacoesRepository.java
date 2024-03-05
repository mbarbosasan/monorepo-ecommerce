package org.ecommerce.pagamentos.repository;

import org.ecommerce.pagamentos.domain.Transacoes;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransacoesRepository extends JpaRepository<Transacoes, Long> {

}
