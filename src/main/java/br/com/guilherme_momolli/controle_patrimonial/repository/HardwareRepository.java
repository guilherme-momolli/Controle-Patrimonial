package br.com.guilherme_momolli.controle_patrimonial.repository;

import br.com.guilherme_momolli.controle_patrimonial.model.Hardware;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public interface HardwareRepository extends JpaRepository<Hardware, Long> {


    Optional<Hardware> findByCodigoPatrimonial(String codigoPatrimonial);

    @Query("SELECT h FROM Hardware h WHERE h.instituicao.id = :instituicaoId")
    List<Hardware> findByInstituicaoId(@Param("instituicaoId") Long instituicaoId);

}

