package br.com.guilherme_momolli.controle_patrimonial.controller;

import br.com.guilherme_momolli.controle_patrimonial.model.Hardware;
import br.com.guilherme_momolli.controle_patrimonial.service.FileStorageService;
import br.com.guilherme_momolli.controle_patrimonial.service.HardwareService;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

@CrossOrigin
@RestController
@RequiredArgsConstructor
@RequestMapping("/hardware")
public class HardwareController {

    private final HardwareService hardwareService;

    private final FileStorageService fileStorageService;

    @GetMapping("/list")
    public ResponseEntity<List<Hardware>> getAllHardwares() {
        return hardwareService.listarHardware();
    }

    @GetMapping("/list/{id}")
    public ResponseEntity<Hardware> getHardwareById(@PathVariable Long id) {
        return hardwareService.getById(id);
    }

    @GetMapping("/imagem/{fileName}")
    public ResponseEntity<byte[]> getImagem(@PathVariable String fileName) {
        return hardwareService.getImagem(fileName);
    }


    @GetMapping("/list/patrimonio/{codigoPatrimonial}")
    public ResponseEntity<List<Hardware>> getHardwareByCodigoPatrimonial(@PathVariable String codigoPatrimonial) {
        return hardwareService.getByCodigoPatrimonial(codigoPatrimonial);
    }

    @PostMapping(path = "/create", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Hardware> criarHardware(
            @RequestPart("hardware") String hardwareJson,
            @RequestPart(value = "file", required = false) MultipartFile file) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            Hardware hardware = objectMapper.readValue(hardwareJson, Hardware.class);
            return hardwareService.criarHardware(hardware, file);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }

    @PutMapping(value = "/update/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Hardware> updateHardware(@PathVariable Long id, @ModelAttribute String hardwareJson, @RequestPart(value = "file", required = false) MultipartFile file) {
        try {
        ObjectMapper objectMapper = new ObjectMapper();
        Hardware hardware = objectMapper.readValue(hardwareJson, Hardware.class);
        return hardwareService.criarHardware(hardware, file);
    } catch (Exception e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deletarHardware(@PathVariable Long id) {
        return hardwareService.deletarHardware(id);
    }

    @GetMapping("/list/agrupado")
    public ResponseEntity<Map<String, List<Hardware>>> listarAgrupado() {
        return hardwareService.listarHardwareAgrupado();
    }
}
