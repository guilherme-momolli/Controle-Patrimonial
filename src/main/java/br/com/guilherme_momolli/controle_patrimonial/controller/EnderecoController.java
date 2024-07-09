package br.com.guilherme_momolli.controle_patrimonial.controller;

import br.com.guilherme_momolli.controle_patrimonial.model.Endereco;
import br.com.guilherme_momolli.controle_patrimonial.service.EnderecoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/endereco")
public class EnderecoController{

    private EnderecoService enderecoService;
    @Autowired

    public EnderecoController(EnderecoService enderecoService) {
        this.enderecoService = enderecoService;
    }

    @GetMapping("/hello")
    public String helloWorld() {
        return "Olá, mundo!";
    }
    @GetMapping("/list")
    public ResponseEntity<List<Endereco>> getAllEnderecos(){
        return enderecoService.listarEndereco();
    }
    @GetMapping("/list/{id}")
    public ResponseEntity<Endereco> getEnderecoById(@PathVariable Long id){
        return enderecoService.getById(id);
    }
    @PostMapping("/create")
    public ResponseEntity<Endereco> criarEndereco(@RequestBody Endereco endereco) {
        return enderecoService.criarEndereco(endereco);
    }
    @PutMapping("/update/{id}")
    public ResponseEntity<Endereco> updateEndereco(@PathVariable Long id, @RequestBody Endereco endereco) {
        return  enderecoService.atualizarEndereco(id, endereco);
    }
    @DeleteMapping("delete/{id}")
    public ResponseEntity<Endereco> deleterEndereco(@PathVariable Long id){
        return enderecoService.deletarEndereco(id);
    }
}
