package br.com.guilherme_momolli.controle_patrimonial.service;

import br.com.guilherme_momolli.controle_patrimonial.model.Endereco;
import br.com.guilherme_momolli.controle_patrimonial.model.Instituicao;
import br.com.guilherme_momolli.controle_patrimonial.model.Usuario;
import br.com.guilherme_momolli.controle_patrimonial.model.UsuarioInstituicao;
import br.com.guilherme_momolli.controle_patrimonial.model.enums.Privilegio;
import br.com.guilherme_momolli.controle_patrimonial.repository.EnderecoRepository;
import br.com.guilherme_momolli.controle_patrimonial.repository.InstituicaoRepository;
import br.com.guilherme_momolli.controle_patrimonial.repository.UsuarioInstituicaoRepository;
import br.com.guilherme_momolli.controle_patrimonial.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class InstituicaoService {

    private final InstituicaoRepository instituicaoRepository;
    private final UsuarioRepository usuarioRepository;
    private final UsuarioInstituicaoRepository usuarioInstituicaoRepository;
    private final PasswordEncoder passwordEncoder;
    @Autowired
    private EnderecoRepository enderecoRepository;

    public List<Instituicao> listInstituicoes() {
        return instituicaoRepository.findAll();
    }

    public Instituicao getById(Long id) {
        return instituicaoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Instituição não encontrada."));
    }

    @Transactional
    public Instituicao createInstituicao(Instituicao instituicao, String senha) {
        try {
            System.out.println("Criando Instituição: " + instituicao.getNomeFantasia());

            if (instituicaoRepository.findByEmail(instituicao.getEmail()).isPresent()) {
                throw new RuntimeException("E-mail já está em uso.");
            }

            if (instituicao.getEndereco() != null) {
                if (instituicao.getEndereco().getId() == null) {
                    instituicao.setEndereco(enderecoRepository.save(instituicao.getEndereco()));
                } else {
                    instituicao.setEndereco(enderecoRepository.findById(instituicao.getEndereco().getId())
                            .orElseThrow(() -> new RuntimeException("Endereço não encontrado.")));
                }
            }

            Instituicao novaInstituicao = instituicaoRepository.save(instituicao);
            System.out.println("Instituição salva com sucesso!");

            Usuario usuario = new Usuario();
            usuario.setNome(instituicao.getNomeFantasia());
            usuario.setEmail(instituicao.getEmail());
            usuario.setSenha(passwordEncoder.encode(senha));
            usuario = usuarioRepository.save(usuario);
            System.out.println("Usuário salvo com sucesso!");

            UsuarioInstituicao usuarioInstituicao = new UsuarioInstituicao();
            usuarioInstituicao.setUsuario(usuario);
            usuarioInstituicao.setInstituicao(novaInstituicao);
            usuarioInstituicao.setPermissao(Privilegio.ADMIN_MASTER);
            usuarioInstituicaoRepository.save(usuarioInstituicao);
            System.out.println("Usuário vinculado à instituição!");

            return novaInstituicao;
        } catch (Exception e) {
            System.err.println("Erro ao criar instituição: " + e.getMessage());
            throw e;
        }
    }


    @Transactional
    public Instituicao updateInstituicao(Long id, Instituicao novaInstituicao, String senha) {
        Instituicao instituicaoExistente = getById(id);

        instituicaoExistente.setRazaoSocial(novaInstituicao.getRazaoSocial());
        instituicaoExistente.setNomeFantasia(novaInstituicao.getNomeFantasia());
        instituicaoExistente.setTipoInstituicao(novaInstituicao.getTipoInstituicao());
        instituicaoExistente.setTelefoneFixo(novaInstituicao.getTelefoneFixo());
        instituicaoExistente.setTelefoneCelular(novaInstituicao.getTelefoneCelular());

        if (novaInstituicao.getEndereco() != null && novaInstituicao.getEndereco().getId() != null) {
            Endereco endereco = enderecoRepository.findById(novaInstituicao.getEndereco().getId())
                    .orElseThrow(() -> new RuntimeException("Endereço não encontrado."));
            instituicaoExistente.setEndereco(endereco);
        }

        if (senha != null && !senha.isBlank()) {
            Usuario usuario = usuarioRepository.findByEmail(instituicaoExistente.getEmail())
                    .orElseThrow(() -> new RuntimeException("Usuário não encontrado."));
            usuario.setSenha(passwordEncoder.encode(senha));
            usuarioRepository.save(usuario);
        }

        return instituicaoRepository.save(instituicaoExistente);
    }

    @Transactional
    public void deleteInstituicao(Long id) {
        Instituicao instituicao = getById(id);

        List<UsuarioInstituicao> vinculos = usuarioInstituicaoRepository.findByInstituicaoId(id);
        usuarioInstituicaoRepository.deleteAll(vinculos);

        instituicaoRepository.delete(instituicao);
    }
}
