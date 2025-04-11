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

    public List<Usuario> listByInstituicao(Long id){
        return usuarioRepository.findByInstituicaoId(id);
    }

    @Transactional
    public Usuario createUsuario(CadastroRequestDTO cadastroRequestDTO) {
        if (usuarioRepository.findByEmail(cadastroRequestDTO.getEmail()).isPresent()) {
            throw new IllegalArgumentException("Email já cadastrado");
        }

        Instituicao instituicao = instituicaoRepository.findById(cadastroRequestDTO.getInstituicaoId())
                .orElseThrow(() -> new ResourceNotFoundException("Instituição não encontrada"));

        if (cadastroRequestDTO.getPermissao() == null) {
            throw new IllegalArgumentException("É obrigatório informar um privilégio para o usuário.");
        }

        Usuario usuario = new Usuario();
        usuario.setNome(cadastroRequestDTO.getNome());
        usuario.setEmail(cadastroRequestDTO.getEmail());
        usuario.setSenha(passwordEncoder.encode(cadastroRequestDTO.getSenha()));
        usuario = usuarioRepository.save(usuario);

        if (usuarioInstituicaoRepository.existsByUsuarioAndInstituicao(usuario, instituicao)) {
            throw new IllegalArgumentException("Usuário já está vinculado a esta instituição.");
        }

        UsuarioInstituicao usuarioInstituicao = new UsuarioInstituicao();
        usuarioInstituicao.setUsuario(usuario);
        usuarioInstituicao.setInstituicao(instituicao);
        usuarioInstituicao.setPermissao(cadastroRequestDTO.getPermissao());

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

    @Transactional
    public UsuarioInstituicao vincularUsuarioAInstituicao(Long usuarioId, Long instituicaoId, Privilegio permissao) {
        Usuario usuario = usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new ResourceNotFoundException("Usuário não encontrado"));

        Instituicao instituicao = instituicaoRepository.findById(instituicaoId)
                .orElseThrow(() -> new ResourceNotFoundException("Instituição não encontrada"));

        if (usuarioInstituicaoRepository.existsByUsuarioAndInstituicao(usuario, instituicao)) {
            throw new IllegalArgumentException("O usuário já está vinculado a esta instituição.");
        }

        UsuarioInstituicao usuarioInstituicao = new UsuarioInstituicao();
        usuarioInstituicao.setUsuario(usuario);
        usuarioInstituicao.setInstituicao(instituicao);
        usuarioInstituicao.setPermissao(permissao);

        return usuarioInstituicaoRepository.save(usuarioInstituicao);
    }

    @Transactional
    public void desvincularUsuarioDeInstituicao(Long usuarioId, Long instituicaoId) {
        Usuario usuario = usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new ResourceNotFoundException("Usuário não encontrado"));

        Instituicao instituicao = instituicaoRepository.findById(instituicaoId)
                .orElseThrow(() -> new ResourceNotFoundException("Instituição não encontrada"));

        UsuarioInstituicao usuarioInstituicao = usuarioInstituicaoRepository
                .findByUsuarioAndInstituicao(usuario, instituicao)
                .orElseThrow(() -> new ResourceNotFoundException("Vínculo não encontrado"));

        if (usuario.getEmail().equals(instituicao.getEmail())) {
            throw new IllegalArgumentException("O e-mail da instituição não pode se desvincular.");
        }

        usuarioInstituicaoRepository.delete(usuarioInstituicao);
    }

    private boolean isEmailInstitucional(Usuario usuario, Instituicao instituicao) {
        return usuario.getEmail().equalsIgnoreCase(instituicao.getEmail());
    }
}
