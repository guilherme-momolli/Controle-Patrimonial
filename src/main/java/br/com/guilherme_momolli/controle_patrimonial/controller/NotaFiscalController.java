package br.com.guilherme_momolli.controle_patrimonial.controller;

import br.com.guilherme_momolli.controle_patrimonial.model.Hardware;
import br.com.guilherme_momolli.controle_patrimonial.model.NotaFiscal;
import br.com.guilherme_momolli.controle_patrimonial.service.NotaFiscalService;
import org.aspectj.weaver.ast.Not;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/nota_fiscal")
public class NotaFiscalController {

    private NotaFiscalService notaFiscalService;

//    @Autowired
//    public NotaFiscalController(NotaFiscalService notaFiscalService) {
//        this.notaFiscalService = notaFiscalService;
//    }
//
//    @GetMapping("/hello")
//    public String helloWorld() {
//        return "Olá, mundo!";
//    }
//
//    @GetMapping("/list")
//    public ResponseEntity<List<NotaFiscal>> getAllNotasFiscais() {
//        return notaFiscalService.listarNotasFiscais();
//    }
//
//    @GetMapping("/list/{id}")
//    public ResponseEntity<NotaFiscal> getNotaFiscalById(@PathVariable Long id) {
//        return notaFiscalService.getById(id);
//    }
//
//
//    @PostMapping("/create")
//    public ResponseEntity<NotaFiscal> criarNotaFiscal(@RequestBody NotaFiscal notaFiscal) {
//        return notaFiscalService.criarNotaFiscal(notaFiscal);
//    }
//
//    @PutMapping("/update/{id}")
//    public ResponseEntity<NotaFiscal> updateNotaFiscal(@PathVariable Long id, @RequestBody NotaFiscal notaFiscal) {
//        return notaFiscalService.atualizarNotaFiscal(id, notaFiscal);
//    }
//
//    @DeleteMapping("delete/{id}")
//    public ResponseEntity<NotaFiscal> deleterNotaFiscal(@PathVariable Long id) {
//        return notaFiscalService.deletarNotaFiscal(id);
//    }
}
