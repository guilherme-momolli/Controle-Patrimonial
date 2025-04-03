//package br.com.guilherme_momolli.controle_patrimonial.repository;
//
//import br.com.guilherme_momolli.controle_patrimonial.model.NotaFiscal;
//import org.springframework.data.jpa.repository.JpaRepository;
//
//import java.util.ArrayList;
//import java.util.Optional;
//
//public interface NotaFiscalRepository extends JpaRepository<NotaFiscal, Long> {
//    ArrayList<NotaFiscal> findAll();
//    NotaFiscal save(NotaFiscal notaFiscal);
//    Optional<NotaFiscal> findById(Long id);
//    NotaFiscal findByNotaFiscalEletronica(String notaFiscalEletronica);
//}
