package org.ecommerce.pagamentos.repository;

import org.ecommerce.pagamentos.domain.Transacao;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransacoesRepository extends JpaRepository<Transacao, Long> {

}
