package br.com.guilherme_momolli.controle_patrimonial.repository;

import br.com.guilherme_momolli.controle_patrimonial.model.Hardware;
import br.com.guilherme_momolli.controle_patrimonial.model.Usuario;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    Optional<Usuario> findByEmail(String email);

//    @Query("SELECT u FROM Usuario u JOIN u.usuarioInstituicoes ui WHERE ui.instituicao.id = :instituicaoId")
//    List<Usuario> findByInstituicaoId(@Param("instituicaoId") Long instituicaoId);

    @Query("SELECT ui.usuario FROM UsuarioInstituicao ui WHERE ui.instituicao.id = :instituicaoId")
    List<Usuario> findByInstituicaoId(@Param("instituicaoId") Long instituicaoId);
}

