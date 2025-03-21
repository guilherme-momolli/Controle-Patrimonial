package br.com.guilherme_momolli.controle_patrimonial.controller;

import br.com.guilherme_momolli.controle_patrimonial.model.Hardware;
import br.com.guilherme_momolli.controle_patrimonial.service.FileStorageService;
import br.com.guilherme_momolli.controle_patrimonial.service.HardwareService;
import lombok.RequiredArgsConstructor;
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

    @PostMapping(value = "/create", consumes = {"multipart/form-data"})
    public ResponseEntity<Hardware> criarHardware(
            @ModelAttribute Hardware hardware,
            @RequestPart(value = "imagem", required = false) MultipartFile imagem) {
        return hardwareService.criarHardware(hardware, imagem);
    }

    @PutMapping(value = "/update/{id}", consumes = {"multipart/form-data"})
    public ResponseEntity<Hardware> updateHardware(
            @PathVariable Long id,
            @ModelAttribute Hardware hardware,
            @RequestPart(value = "imagem", required = false) MultipartFile imagem) {
        return hardwareService.atualizarHardware(id, hardware, imagem);
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
