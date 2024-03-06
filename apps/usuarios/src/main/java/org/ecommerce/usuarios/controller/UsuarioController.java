package org.ecommerce.usuarios.controller;

import org.ecommerce.usuarios.domain.Usuario;
import org.ecommerce.usuarios.repository.UsuarioRepository;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

    private final UsuarioRepository usuarioRepository;

    public UsuarioController(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    @GetMapping("{id}")
    public ResponseEntity<Usuario> getUsuario(@PathVariable Long id) {
        Usuario usuario = usuarioRepository.findById(id).orElse(null);

        if (usuario == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(usuarioRepository.findById(id).get());
    }

    @PostMapping
    public ResponseEntity createUsuario(@RequestBody Usuario usuario) {
        try {
            usuarioRepository.save(usuario);
            return ResponseEntity.ok().build();
        } catch (DataIntegrityViolationException e) {
            e.printStackTrace();
            return new ResponseEntity<>("Email já cadastrado", HttpStatus.BAD_REQUEST);
        }
    }

}
