package org.ecommerce.usuarios.repository;

import org.ecommerce.usuarios.domain.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Repository
public interface UsuarioRepository  extends JpaRepository<Usuario, Long>{
}
