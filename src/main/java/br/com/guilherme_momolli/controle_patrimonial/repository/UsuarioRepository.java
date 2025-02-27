package br.com.guilherme_momolli.controle_patrimonial.repository;

import br.com.guilherme_momolli.controle_patrimonial.model.Usuario;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.ArrayList;
import java.util.Optional;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    ArrayList<Usuario> findAll();
    Usuario save(Usuario usuario);
    Optional<Usuario> findById(Long id);
    Optional<Usuario> findByEmail(String email);
}

