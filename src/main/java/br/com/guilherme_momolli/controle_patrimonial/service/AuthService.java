package br.com.guilherme_momolli.controle_patrimonial.service;

import br.com.guilherme_momolli.controle_patrimonial.dto.AuthRequestDTO;
import br.com.guilherme_momolli.controle_patrimonial.dto.AuthResponseDTO;
import br.com.guilherme_momolli.controle_patrimonial.dto.LoginRequestDTO;
import br.com.guilherme_momolli.controle_patrimonial.model.Usuario;
import br.com.guilherme_momolli.controle_patrimonial.repository.UsuarioRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Optional;

@Service
public class AuthService {

    private final UsuarioRepository usuarioRepository;
    private  JwtUtil jwtUtil;
    private PasswordEncoder passwordEncoder;

    public AuthService(UsuarioRepository usuarioRepository, PasswordEncoder passwordEncoder) {
        this.usuarioRepository = usuarioRepository;
        this.passwordEncoder = passwordEncoder;
    }


    public AuthResponseDTO authenticate(AuthRequestDTO request) {
        Optional<Usuario> usuario = usuarioRepository.findByEmail(request.getEmail());

        if (usuario.isPresent() && passwordEncoder.matches(request.getSenha(), usuario.get().getSenha())) {
            String token = "TOKEN_GERADO";
            return new AuthResponseDTO(token);
        }

        throw new RuntimeException("Credenciais inválidas");
    }

}
