package br.com.guilherme_momolli.controle_patrimonial.repository;

import br.com.guilherme_momolli.controle_patrimonial.model.Hardware;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.ArrayList;
import java.util.Optional;

public interface HardwareRepository extends JpaRepository<Hardware, Long> {
    ArrayList<Hardware> findAll();
    Hardware save(Hardware hardware);
    Optional<Hardware> findById(Long id);
    ArrayList<Hardware> findByCodigoPatrimonial(String codigoPatrimonial);


}

