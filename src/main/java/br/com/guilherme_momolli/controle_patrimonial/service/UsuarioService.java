package br.com.guilherme_momolli.controle_patrimonial.service;

import br.com.guilherme_momolli.controle_patrimonial.dto.CadastroRequestDTO;
import br.com.guilherme_momolli.controle_patrimonial.dto.LoginRequestDTO;

import br.com.guilherme_momolli.controle_patrimonial.model.Usuario;

import br.com.guilherme_momolli.controle_patrimonial.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.Optional;

@Service
public class UsuarioService {

    private BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Autowired
    private UsuarioRepository usuarioRepository;



    public ResponseEntity<List<Usuario>> listarUsuario(){
        try{
            return new ResponseEntity(usuarioRepository.findAll(), HttpStatus.OK);
        }
        catch(Exception exception){
            System.out.println(exception.getMessage());
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    public ResponseEntity<Usuario> getById(Long id) {
        try {
            Optional<Usuario> usuario = usuarioRepository.findById(id);
            return usuario.map(value -> new ResponseEntity<>(value, HttpStatus.OK)).orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
        } catch (Exception exception) {
            System.out.println(exception.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<Usuario> criarUsuario(@RequestBody CadastroRequestDTO cadastroRequestDTO){
        try {
            Usuario usuario = new Usuario();
            usuario.setNome(cadastroRequestDTO.getNome());
            usuario.setEmail(cadastroRequestDTO.getEmail());
            usuario.setSenha(passwordEncoder.encode(cadastroRequestDTO.getSenha()));

            usuarioRepository.save(usuario);

            return new ResponseEntity<>(usuario, HttpStatus.CREATED);
        }catch (Exception exception){
            System.out.println(exception.getMessage());
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    public ResponseEntity<String> loginUsuario(@RequestBody LoginRequestDTO loginRequestDTO){
        try {

            String email1 = loginRequestDTO.getEmail();
            String senha = loginRequestDTO.getSenha();

            Usuario usuario = usuarioRepository.findByEmail(email1);


            if (passwordEncoder.matches(senha, usuario.getSenha())) {
                return ResponseEntity.ok("Login bem-sucedido!");
            } else {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Credenciais inválidas");
            }
        }catch (Exception exception){
            System.out.println(exception.getMessage());
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }

    }


    public ResponseEntity<Usuario> atualizarUsuario(@PathVariable Long id, @RequestBody Usuario usuario){
        try{
            Optional<Usuario> usuarioAtualizar = usuarioRepository.findById(id);
            Usuario usuarioParaAtualizar = usuarioAtualizar.get();
            usuarioParaAtualizar.setNome(usuario.getNome());
            usuarioParaAtualizar.setEmail(usuario.getEmail());
            usuarioParaAtualizar.setSenha(passwordEncoder.encode(usuario.getSenha()));

            Usuario update = usuarioRepository.save(usuarioParaAtualizar);
            return new ResponseEntity<>(update, HttpStatus.OK);
        }catch (Exception exception){
            System.out.println(exception.getMessage());
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<Usuario> deletarUsuario(@PathVariable Long id){
        try {
            usuarioRepository.deleteById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }catch (Exception exception){
            System.out.println(exception.getMessage());
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public boolean autenticar(String email, String senha) {
        Usuario usuario = usuarioRepository.findByEmail(email);
        if (usuario != null && passwordEncoder.matches(senha, usuario.getSenha())) {
            return true;
        }
        return false;
    }
}
