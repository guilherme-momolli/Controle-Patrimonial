package br.com.guilherme_momolli.controle_patrimonial.repository;

import br.com.guilherme_momolli.controle_patrimonial.model.Endereco;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.ArrayList;
import java.util.Optional;

public interface EnderecoRepository extends JpaRepository<Endereco, Long> {
        ArrayList<Endereco> findAll();
        Endereco save(Endereco endereco);
        Optional<Endereco> findById(Long id);
}

