package br.com.guilherme_momolli.controle_patrimonial.service;

import br.com.guilherme_momolli.controle_patrimonial.dto.AuthRequestDTO;
import br.com.guilherme_momolli.controle_patrimonial.dto.AuthResponseDTO;
import br.com.guilherme_momolli.controle_patrimonial.dto.InstituicaoDTO;
import br.com.guilherme_momolli.controle_patrimonial.model.Instituicao;
import br.com.guilherme_momolli.controle_patrimonial.model.Usuario;
import br.com.guilherme_momolli.controle_patrimonial.model.UsuarioInstituicao;
import br.com.guilherme_momolli.controle_patrimonial.repository.UsuarioInstituicaoRepository;
import br.com.guilherme_momolli.controle_patrimonial.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class AuthService {

    private static final Logger logger = LoggerFactory.getLogger(AuthService.class);
    private final UsuarioRepository usuarioRepository;
    private final UsuarioInstituicaoRepository usuarioInstituicaoRepository;
    private final JwtUtil jwtUtil;
    private final PasswordEncoder passwordEncoder;


    public AuthResponseDTO iniciarLogin(AuthRequestDTO request) {
        Usuario usuario = usuarioRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new BadCredentialsException("Credenciais inválidas"));

        if (!passwordEncoder.matches(request.getSenha(), usuario.getSenha())) {
            throw new BadCredentialsException("Credenciais inválidas");
        }

        List<UsuarioInstituicao> vinculos = usuarioInstituicaoRepository.findByUsuarioEmail(request.getEmail());

        if (vinculos.isEmpty()) {
            throw new RuntimeException("Usuário não está vinculado a nenhuma instituição.");
        }

        if (vinculos.size() == 1) {
            Long idInstituicao = vinculos.get(0).getInstituicao().getId();
            String instituicaoNome = vinculos.get(0).getInstituicao().getNomeFantasia();
            String token = jwtUtil.generateToken(usuario.getEmail(), idInstituicao, instituicaoNome, usuario.getNome());
            return new AuthResponseDTO(token, usuario.getNome(), null);
        }

        List<InstituicaoDTO> instituicoes = vinculos.stream()
                .map(v -> new InstituicaoDTO(v.getInstituicao()))
                .toList();

        return new AuthResponseDTO(null, usuario.getNome(), instituicoes);
    }

    public AuthResponseDTO finalizarLogin(String email, Long instituicaoId) {
        Usuario usuario = usuarioRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        Instituicao instituicao = usuarioInstituicaoRepository.findByUsuarioEmail(email).stream()
                .map(UsuarioInstituicao::getInstituicao)
                .filter(inst -> inst.getId().equals(instituicaoId))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Instituição não encontrada ou não vinculada ao usuário"));

        String token = jwtUtil.generateToken(
                email,
                instituicaoId,
                instituicao.getNomeFantasia(),
                usuario.getNome()
        );

        return new AuthResponseDTO(token, usuario.getNome(), null);
    }




}
