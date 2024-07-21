package br.com.guilherme_momolli.controle_patrimonial.controller;

import br.com.guilherme_momolli.controle_patrimonial.dto.CadastroRequestDTO;
import br.com.guilherme_momolli.controle_patrimonial.dto.LoginRequestDTO;
import br.com.guilherme_momolli.controle_patrimonial.model.Usuario;
import br.com.guilherme_momolli.controle_patrimonial.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/usuario")
public class UsuarioController{

    @Autowired
    private UsuarioService usuarioService;

    @GetMapping("/hello")
    public String helloWorld() {return "Olá, mundo!";}

    @GetMapping("/list")
    public ResponseEntity<List<Usuario>> getAllUsuarios() {
        return usuarioService.listarUsuario();
    }

    @GetMapping("/list/{id}")
    public ResponseEntity<Usuario> getUsuarioById(@PathVariable Long id){
        return usuarioService.getById(id);
    }

    @PostMapping("/create")
    public ResponseEntity<Usuario> criarUsuario(@RequestBody CadastroRequestDTO cadastroRequestDTO) {
        return usuarioService.criarUsuario(cadastroRequestDTO);
    }

    @PostMapping("/login")
    public ResponseEntity<String> loginUsuario(@RequestBody LoginRequestDTO loginRequestDTO){
        //return usuarioService.loginUsuario(loginRequestDTO);
        String email = loginRequestDTO.getEmail();
        String senha = loginRequestDTO.getSenha();

        boolean autenticado = usuarioService.autenticar(email, senha);

        if (autenticado) {
            return ResponseEntity.ok("Login bem-sucedido!");
        }
        else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Credenciais inválidas");
        }

    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Usuario> updateUsuario(@PathVariable Long id, @RequestBody Usuario usuario) {
        return  usuarioService.atualizarUsuario(id, usuario);
    }

    @DeleteMapping("delete/{id}")
    public ResponseEntity<Usuario> deleterUsuario(@PathVariable Long id){
        return usuarioService.deletarUsuario(id);
    }




}
