package br.com.guilherme_momolli.controle_patrimonial.controller;

import br.com.guilherme_momolli.controle_patrimonial.dto.CadastroRequestDTO;
import br.com.guilherme_momolli.controle_patrimonial.dto.VinculoRequestDTO;
import br.com.guilherme_momolli.controle_patrimonial.model.Hardware;
import br.com.guilherme_momolli.controle_patrimonial.model.Usuario;
import br.com.guilherme_momolli.controle_patrimonial.model.UsuarioInstituicao;
import br.com.guilherme_momolli.controle_patrimonial.model.enums.Privilegio;
import br.com.guilherme_momolli.controle_patrimonial.service.UsuarioService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@RequiredArgsConstructor
@RequestMapping("/usuario")
public class UsuarioController {

    private final UsuarioService usuarioService;

    @GetMapping("/list")
    public ResponseEntity<List<Usuario>> listarUsuarios() {
        try{
            usuarioService.listUsuarios();
            return ResponseEntity.status(HttpStatus.OK).build();
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/list/{id}")
    public ResponseEntity<Usuario> getUsuarioById(@PathVariable Long id) {
        try{
            usuarioService.getById(id);
            return ResponseEntity.status(HttpStatus.OK).build();
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @GetMapping("/instituicao/{instituicaoId}")
    public ResponseEntity<List<Usuario>> listByInstituicao(@PathVariable Long instituicaoId) {
        List<Usuario> usuario = usuarioService.listByInstituicao(instituicaoId);
        return ResponseEntity.ok(usuario);
    }

    @PostMapping("/create")
    public ResponseEntity<Usuario> createUsuario(@RequestBody CadastroRequestDTO cadastroRequestDTO) {
        try {
           usuarioService.createUsuario(cadastroRequestDTO);
           return ResponseEntity.status(HttpStatus.CREATED).build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Usuario> updateUsuario(@PathVariable Long id, @RequestBody Usuario usuario) {
        try{
            usuarioService.updateUsuario(id, usuario);
            return ResponseEntity.status(HttpStatus.OK).build();
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteUsuario(@PathVariable Long id) {
        try {
            usuarioService.deleteUsuario(id);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }


    @PostMapping("/vincular")
    public ResponseEntity<UsuarioInstituicao> vincularUsuarioAInstituicao(@RequestBody VinculoRequestDTO request) {
        UsuarioInstituicao usuarioInstituicao = usuarioService.vincularUsuarioAInstituicao(
                request.getUsuarioId(),
                request.getInstituicaoId(),
                request.getPermissao()
        );
        return ResponseEntity.ok(usuarioInstituicao);
    }

    @DeleteMapping("/desvincular")
    public ResponseEntity<Void> desvincularUsuarioDeInstituicao(@RequestParam Long usuarioId, @RequestParam Long instituicaoId) {
        usuarioService.desvincularUsuarioDeInstituicao(usuarioId, instituicaoId);
        return ResponseEntity.noContent().build();
    }



}
