package br.com.guilherme_momolli.controle_patrimonial.controller;

import br.com.guilherme_momolli.controle_patrimonial.dto.AuthRequestDTO;
import br.com.guilherme_momolli.controle_patrimonial.dto.AuthResponseDTO;
import br.com.guilherme_momolli.controle_patrimonial.dto.FinalizarLoginDTO;
import br.com.guilherme_momolli.controle_patrimonial.service.AuthService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<AuthResponseDTO> iniciarLogin(@RequestBody AuthRequestDTO request) {
        AuthResponseDTO response = authService.iniciarLogin(request);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/finalizar-login")
    public ResponseEntity<AuthResponseDTO> finalizarLogin(@RequestBody FinalizarLoginDTO request) {
        AuthResponseDTO response = authService.finalizarLogin(request.getEmail(), request.getInstituicaoId());
        return ResponseEntity.ok(response);
    }
}