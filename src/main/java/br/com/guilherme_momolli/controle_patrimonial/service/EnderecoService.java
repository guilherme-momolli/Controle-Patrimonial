package br.com.guilherme_momolli.controle_patrimonial.service;

import br.com.guilherme_momolli.controle_patrimonial.excepitions.recource.ResourceNotFoundException;
import br.com.guilherme_momolli.controle_patrimonial.model.Endereco;
import br.com.guilherme_momolli.controle_patrimonial.repository.EnderecoRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EnderecoService {

    private final EnderecoRepository enderecoRepository;

    public List<Endereco> listEndereco() {
        return enderecoRepository.findAll();
    }

    public Endereco getById(Long id) {
        return enderecoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Endereço não encontrado"));
    }

    public Endereco createEndereco(Endereco endereco) {
        return enderecoRepository.save(endereco);
    }

    @Transactional
    public Endereco updateEndereco(Long id, Endereco endereco) {
        Endereco enderecoParaAtualizar = enderecoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Endereço não encontrado"));

        enderecoParaAtualizar.setLogradouro(endereco.getLogradouro());
        enderecoParaAtualizar.setNumero(endereco.getNumero());
        enderecoParaAtualizar.setBairro(endereco.getBairro());
        enderecoParaAtualizar.setMunicipio(endereco.getMunicipio());
        enderecoParaAtualizar.setCep(endereco.getCep());
        enderecoParaAtualizar.setUf(endereco.getUf());
        enderecoParaAtualizar.setPais(endereco.getPais());

        return enderecoRepository.save(enderecoParaAtualizar);
    }

    public void deleteEndereco(Long id) {
        if (!enderecoRepository.existsById(id)) {
            throw new ResourceNotFoundException("Endereço não encontrado");
        }
        enderecoRepository.deleteById(id);
    }
}
