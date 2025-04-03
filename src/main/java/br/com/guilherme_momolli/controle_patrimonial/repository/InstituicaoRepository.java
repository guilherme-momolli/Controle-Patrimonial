package br.com.guilherme_momolli.controle_patrimonial.repository;

import br.com.guilherme_momolli.controle_patrimonial.model.Instituicao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public interface InstituicaoRepository extends JpaRepository<Instituicao, Long> {
    Optional<Instituicao> findByEmail(String email);

    Optional<Instituicao> findByCnpj(String cnpj);

    @Query("SELECT ui.instituicao FROM UsuarioInstituicao ui WHERE ui.usuario.email = :email")
    List<Instituicao> findInstituicoesByUsuarioEmail(@Param("email") String email);
}

