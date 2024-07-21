package br.com.guilherme_momolli.controle_patrimonial.service;

import br.com.guilherme_momolli.controle_patrimonial.model.Endereco;
import br.com.guilherme_momolli.controle_patrimonial.repository.EnderecoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.Optional;

@Service
public class EnderecoService {

    @Autowired
    private EnderecoRepository enderecoRepository;

    public EnderecoService(EnderecoRepository enerecoRepository){
        this.enderecoRepository = enderecoRepository;
    }

    public ResponseEntity<List<Endereco>> listarEndereco(){
        try{
            return new ResponseEntity(enderecoRepository.findAll(), HttpStatus.OK);
        }
        catch(Exception exception){
            System.out.println(exception.getMessage());
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    public ResponseEntity<Endereco> getById(Long id) {
        try {
            Optional<Endereco> endereco = enderecoRepository.findById(id);
            return endereco.map(value -> new ResponseEntity<>(value, HttpStatus.OK)).orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
        } catch (Exception exception) {
            System.out.println(exception.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    public ResponseEntity<Endereco> criarEndereco(@RequestBody Endereco endereco){
        try {
            Endereco saveEndereco = enderecoRepository.save(endereco);
            return new ResponseEntity<>(saveEndereco, HttpStatus.CREATED);
        }catch (Exception exception){
            System.out.println(exception.getMessage());
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }
    public ResponseEntity<Endereco> atualizarEndereco(@PathVariable Long id, @RequestBody Endereco endereco){
        try{
            Optional<Endereco> enderecoAtualizar = enderecoRepository.findById(id);
            Endereco enderecoParaAtualizar = enderecoAtualizar.get();
            enderecoParaAtualizar.setLogradouro(endereco.getLogradouro());
            enderecoParaAtualizar.setBairro(endereco.getBairro());
            enderecoParaAtualizar.setNumero(endereco.getNumero());
            enderecoParaAtualizar.setCep(endereco.getCep());
            enderecoParaAtualizar.setMunicipio(endereco.getMunicipio());
            enderecoParaAtualizar.setUf(endereco.getUf());
            enderecoParaAtualizar.setPais(endereco.getPais());

            Endereco update = enderecoRepository.save(enderecoParaAtualizar);
            return new ResponseEntity<>(update, HttpStatus.OK);
        }catch (Exception exception){
            System.out.println(exception.getMessage());
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<Endereco> deletarEndereco(@PathVariable Long id){
        try {
            enderecoRepository.deleteById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }catch (Exception exception){
            System.out.println(exception.getMessage());
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
