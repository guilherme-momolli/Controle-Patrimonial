//package br.com.guilherme_momolli.controle_patrimonial.controller;
//
//import br.com.guilherme_momolli.controle_patrimonial.model.Instituicao;
//import br.com.guilherme_momolli.controle_patrimonial.service.InstituicaoService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.List;
//
//@RestController
//@RequestMapping("/instituicao")
//public class InstituicaoController{
//
//    private InstituicaoService instituicaoService;
//
//    @Autowired
//
//    public InstituicaoController(InstituicaoService instituicaoService) {this.instituicaoService = instituicaoService;}
//
//    @GetMapping("/hello")
//    public String helloWorld() {return "Olá, mundo!";}
//
//    @GetMapping("/list")
//    public ResponseEntity<List<Instituicao>> getAllInstituicoes(){return instituicaoService.listarInstituicao();}
//
//    @GetMapping("/list/{id}")
//    public ResponseEntity<Instituicao> getInstituicaoById(@PathVariable Long id){return instituicaoService.getById(id);}
//
//    @PostMapping("/create")
//    public ResponseEntity<Instituicao> criarInstituicao(@RequestBody Instituicao instituicao) {return instituicaoService.criarInstituicao(instituicao);}
//
//    @PutMapping("/update/{id}")
//    public ResponseEntity<Instituicao> updateInstituicao(@PathVariable Long id, @RequestBody Instituicao instituicao) {return  instituicaoService.atualizarInstituicao(id, instituicao);}
//
//    @DeleteMapping("delete/{id}")
//    public ResponseEntity<Instituicao> deleterinstituicao(@PathVariable Long id){
//        return instituicaoService.deletarInstituicao(id);
//    }
//}
//
