package br.com.guilherme_momolli.controle_patrimonial.service;

import br.com.guilherme_momolli.controle_patrimonial.dto.CadastroRequestDTO;
import br.com.guilherme_momolli.controle_patrimonial.excepitions.recource.ResourceNotFoundException;
import br.com.guilherme_momolli.controle_patrimonial.model.Instituicao;
import br.com.guilherme_momolli.controle_patrimonial.model.Usuario;
import br.com.guilherme_momolli.controle_patrimonial.model.UsuarioInstituicao;
import br.com.guilherme_momolli.controle_patrimonial.model.enums.Privilegio;
import br.com.guilherme_momolli.controle_patrimonial.repository.InstituicaoRepository;
import br.com.guilherme_momolli.controle_patrimonial.repository.UsuarioInstituicaoRepository;
import br.com.guilherme_momolli.controle_patrimonial.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class UsuarioService {

    private final PasswordEncoder passwordEncoder;
    private final UsuarioRepository usuarioRepository;
    private final InstituicaoRepository instituicaoRepository;
    private final UsuarioInstituicaoRepository usuarioInstituicaoRepository;

    public List<Usuario> listUsuarios() {
        return usuarioRepository.findAll();
    }

    public Usuario getById(Long id) {
        return usuarioRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Usuário não encontrado"));
    }

    @Transactional
    public Usuario createUsuario(CadastroRequestDTO cadastroRequestDTO) {
        if (usuarioRepository.findByEmail(cadastroRequestDTO.getEmail()).isPresent()) {
            throw new IllegalArgumentException("Email já cadastrado");
        }

        Instituicao instituicao = instituicaoRepository.findById(cadastroRequestDTO.getInstituicaoId())
                .orElseThrow(() -> new ResourceNotFoundException("Instituição não encontrada"));

        if (cadastroRequestDTO.getPrivilegio() == null) {
            throw new IllegalArgumentException("É obrigatório informar um privilégio para o usuário.");
        }

        Usuario usuario = new Usuario();
        usuario.setNome(cadastroRequestDTO.getNome());
        usuario.setEmail(cadastroRequestDTO.getEmail());
        usuario.setSenha(passwordEncoder.encode(cadastroRequestDTO.getSenha()));
        usuario = usuarioRepository.save(usuario);

        // Criando relacionamento com a instituição e atribuindo a permissão informada
        UsuarioInstituicao usuarioInstituicao = new UsuarioInstituicao();
        usuarioInstituicao.setUsuario(usuario);
        usuarioInstituicao.setInstituicao(instituicao);
        usuarioInstituicao.setPermissao(cadastroRequestDTO.getPrivilegio());

        usuarioInstituicaoRepository.save(usuarioInstituicao);

        return usuario;
    }


    @Transactional
    public Usuario updateUsuario(Long id, Usuario usuario) {
        Usuario usuarioParaAtualizar = usuarioRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Usuário não encontrado"));

        usuarioParaAtualizar.setNome(usuario.getNome());
        usuarioParaAtualizar.setEmail(usuario.getEmail());

        if (usuario.getSenha() != null && !usuario.getSenha().isEmpty()) {
            usuarioParaAtualizar.setSenha(passwordEncoder.encode(usuario.getSenha()));
        }

        return usuarioRepository.save(usuarioParaAtualizar);
    }

    @Transactional
    public void deleteUsuario(Long id) {
        if (!usuarioRepository.existsById(id)) {
            throw new ResourceNotFoundException("Usuário não encontrado");
        }
        usuarioRepository.deleteById(id);
    }
}