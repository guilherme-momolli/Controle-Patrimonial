package br.com.guilherme_momolli.controle_patrimonial.service;

import br.com.guilherme_momolli.controle_patrimonial.model.NotaFiscal;
import br.com.guilherme_momolli.controle_patrimonial.repository.NotaFiscalRepository;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.Optional;

@Service
public class NotaFiscalService {

    @Autowired
    private NotaFiscalRepository notaFiscalRepository;

    public NotaFiscalService(NotaFiscalRepository notaFiscalRepository) {
        this.notaFiscalRepository = notaFiscalRepository;
    }

    public ResponseEntity<List<NotaFiscal>> listarNotasFiscais(){
        try{
            return new ResponseEntity(notaFiscalRepository.findAll(), HttpStatus.OK);
        }
        catch(Exception exception){
            System.out.println(exception.getMessage());
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    public ResponseEntity<NotaFiscal> getById(Long id) {
        try {
            Optional<NotaFiscal> notaFiscal = notaFiscalRepository.findById(id);
            return notaFiscal.map(value -> new ResponseEntity<>(value, HttpStatus.OK)).orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
        } catch (Exception exception) {
            System.out.println(exception.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<NotaFiscal> criarNotaFiscal(@RequestBody NotaFiscal notaFiscal){
        try {
            NotaFiscal saveNotaFiscal = notaFiscalRepository.save(notaFiscal);
            return new ResponseEntity<>(saveNotaFiscal, HttpStatus.CREATED);
        }catch (Exception exception){
            System.out.println(exception.getMessage());
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    public ResponseEntity<NotaFiscal> atualizarNotaFiscal(@PathVariable Long id, @RequestBody NotaFiscal notaFiscal){
        try{
            Optional<NotaFiscal> notaFiscalAtualizar = notaFiscalRepository.findById(id);
            NotaFiscal notaFiscalParaAtualizar = notaFiscalAtualizar.get();
            notaFiscalParaAtualizar.setNotaFiscalEletronica(notaFiscal.getNotaFiscalEletronica());
            notaFiscalParaAtualizar.setCnpj(notaFiscal.getCnpj());
            notaFiscalParaAtualizar.setPrecoCompra(notaFiscal.getPrecoCompra());
            notaFiscalParaAtualizar.setDataCompra(notaFiscal.getDataCompra());
 //           notaFiscalParaAtualizar.setEnderecoId(notaFiscal.getEnderecoId());

            NotaFiscal update = notaFiscalRepository.save(notaFiscalParaAtualizar);
            return new ResponseEntity<>(update, HttpStatus.OK);
        }catch (Exception exception){
            System.out.println(exception.getMessage());
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<NotaFiscal> deletarNotaFiscal(@PathVariable Long id){
        try {
            notaFiscalRepository.deleteById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }catch (Exception exception){
            System.out.println(exception.getMessage());
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
