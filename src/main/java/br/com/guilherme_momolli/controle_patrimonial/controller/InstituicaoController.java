package br.com.guilherme_momolli.controle_patrimonial.controller;

import br.com.guilherme_momolli.controle_patrimonial.dto.InstituicaoRequestDTO;
import br.com.guilherme_momolli.controle_patrimonial.model.Instituicao;
import br.com.guilherme_momolli.controle_patrimonial.service.InstituicaoService;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/instituicao")
public class InstituicaoController {

    private final InstituicaoService instituicaoService;
    private final ObjectMapper objectMapper;

    @GetMapping("/list")
    public ResponseEntity<List<Instituicao>> getAllInstituicoes() {
        List<Instituicao> instituicoes = instituicaoService.listInstituicoes();
        return ResponseEntity.ok(instituicoes);
    }

    @GetMapping("list/{id}")
    public ResponseEntity<Instituicao> getInstituicaoById(@PathVariable Long id) {
        try {
            Instituicao instituicao = instituicaoService.getById(id);
            return ResponseEntity.ok(instituicao);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @PostMapping(
            value = "/create",
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE + ";charset=UTF-8"
    )
    public ResponseEntity<Instituicao> createInstituicao(
            @RequestPart("instituicao") String instituicaoJson,
            @RequestParam(value = "senha") String senha) {
        try {
            Instituicao instituicao = objectMapper.readValue(instituicaoJson, Instituicao.class);
            Instituicao savedInstituicao = instituicaoService.createInstituicao(instituicao, senha);
            return ResponseEntity.status(HttpStatus.CREATED).body(savedInstituicao);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateInstituicao(@PathVariable Long id, @RequestBody InstituicaoRequestDTO request) {
        try {
            Instituicao instituicaoAtualizada = instituicaoService.updateInstituicao(id, request.getInstituicao(), request.getSenha());
            return ResponseEntity.ok(instituicaoAtualizada);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Erro ao atualizar instituição: " + e.getMessage());
        }
    }


    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteInstituicao(@PathVariable Long id) {
        try {
            instituicaoService.deleteInstituicao(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }
}
