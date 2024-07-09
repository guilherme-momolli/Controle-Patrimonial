//package br.com.guilherme_momolli.controle_patrimonial.service;
//
//import br.com.guilherme_momolli.controle_patrimonial.model.Endereco;
//import br.com.guilherme_momolli.controle_patrimonial.model.Instituicao;
//
//import br.com.guilherme_momolli.controle_patrimonial.repository.InstituicaoRepository;
//
//import org.springframework.beans.factory.annotation.Autowired;
//
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//
//import org.springframework.stereotype.Service;
//
//import org.springframework.web.bind.annotation.PathVariable;
//import org.springframework.web.bind.annotation.RequestBody;
//
//import java.util.List;
//import java.util.Optional;
//
//@Service
//public class InstituicaoService {
//
//    @Autowired
//    private InstituicaoRepository instituicaoRepository;
//
//    public InstituicaoService(InstituicaoRepository instiuicaoRepository){
//        this.instituicaoRepository = instituicaoRepository;
//    }
//
//    public ResponseEntity<List<Instituicao>> listarInstituicao(){
//        try{
//            return new ResponseEntity(instituicaoRepository.findAll(), HttpStatus.OK);
//        }
//        catch(Exception exception){
//            System.out.println(exception.getMessage());
//            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
//        }
//    }
//
//    public ResponseEntity<Instituicao> getById(Long id) {
//        try {
//            Optional<Instituicao> instituicao = instituicaoRepository.findById(id);
//            return instituicao.map(value -> new ResponseEntity<>(value, HttpStatus.OK)).orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
//        } catch (Exception exception) {
//            System.out.println(exception.getMessage());
//            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
//        }
//    }
//
//    public ResponseEntity<Instituicao> criarInstituicao(@RequestBody Instituicao instituicao){
//        try {
//            Instituicao saveInstituicao = instituicaoRepository.save(instituicao);
//            return new ResponseEntity<>(saveInstituicao, HttpStatus.CREATED);
//        }catch (Exception exception){
//            System.out.println(exception.getMessage());
//            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
//        }
//    }
//
//    public ResponseEntity<Instituicao> atualizarInstituicao(@PathVariable Long id, @RequestBody Instituicao instituicao){
//        try{
//            Optional<Instituicao> instituicaoAtualizar = instituicaoRepository.findById(id);
//            Instituicao instituicaoParaAtualizar = instituicaoAtualizar.get();
//            instituicaoParaAtualizar.setNome(instituicao.getNome());
//            instituicaoParaAtualizar.setEmail(instituicao.getEmail());
//            instituicaoParaAtualizar.setSenha(instituicao.getSenha());
//            instituicaoParaAtualizar.setCnpj(instituicao.getCnpj());
//            instituicaoParaAtualizar.setEndereco_id(instituicao.getEndereco_id());
//
//            Instituicao update = instituicaoRepository.save(instituicaoParaAtualizar);
//            return new ResponseEntity<>(update, HttpStatus.OK);
//        }catch (Exception exception){
//            System.out.println(exception.getMessage());
//            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
//        }
//    }
//
//    public ResponseEntity<Instituicao> deletarInstituicao(@PathVariable Long id){
//        try {
//            instituicaoRepository.deleteById(id);
//            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
//        }catch (Exception exception){
//            System.out.println(exception.getMessage());
//            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
//        }
//    }
//}
