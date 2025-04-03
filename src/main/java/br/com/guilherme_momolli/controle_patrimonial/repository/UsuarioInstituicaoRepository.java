package br.com.guilherme_momolli.controle_patrimonial.repository;

import br.com.guilherme_momolli.controle_patrimonial.model.Instituicao;
import br.com.guilherme_momolli.controle_patrimonial.model.Usuario;
import br.com.guilherme_momolli.controle_patrimonial.model.UsuarioInstituicao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

public interface UsuarioInstituicaoRepository extends JpaRepository<UsuarioInstituicao, Long> {

    @Transactional(readOnly = true)
    @Query("SELECT ui FROM UsuarioInstituicao ui WHERE ui.usuario.email = :email")
    List<UsuarioInstituicao> findByUsuarioEmail(@Param("email") String email);

    @Transactional(readOnly = true)
    @Query("SELECT ui FROM UsuarioInstituicao ui WHERE ui.instituicao.id = :id")
    List<UsuarioInstituicao> findByInstituicaoId(@Param("id") Long id);

    boolean existsByUsuarioAndInstituicao(Usuario usuario, Instituicao instituicao);
    Optional<UsuarioInstituicao> findByUsuarioAndInstituicao(Usuario usuario, Instituicao instituicao);

}
