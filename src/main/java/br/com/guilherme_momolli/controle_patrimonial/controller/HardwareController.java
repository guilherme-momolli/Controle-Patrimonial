package br.com.guilherme_momolli.controle_patrimonial.controller;

import br.com.guilherme_momolli.controle_patrimonial.model.Hardware;
import br.com.guilherme_momolli.controle_patrimonial.service.HardwareService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@CrossOrigin
@RestController
@RequiredArgsConstructor
@RequestMapping("/hardware")
public class HardwareController {

    private final HardwareService hardwareService;
    private final ObjectMapper objectMapper;

    @GetMapping("/list")
    public ResponseEntity<List<Hardware>> getAllHardwares() {
        List<Hardware> hardwares = hardwareService.listHardware();
        return ResponseEntity.ok(hardwares);
    }

    @GetMapping("/list/{id}")
    public ResponseEntity<Optional<Hardware>> getHardwareById(@PathVariable Long id) {
        Optional <Hardware> hardware = hardwareService.getById(id);
        return ResponseEntity.ok(hardware);
    }

    @GetMapping("/list/agrupado")
    public ResponseEntity<Map<String, List<Hardware>>> listarAgrupado() {
        Map<String, List<Hardware>> agrupados = hardwareService.listarHardwareAgrupado();
        return ResponseEntity.ok(agrupados);
    }

    @PostMapping(path = "/create", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Hardware> createHardware(
            @RequestPart("hardware") String hardwareJson,
            @RequestPart(value = "file", required = false) MultipartFile file) {
        try {
            Hardware hardware = objectMapper.readValue(hardwareJson, Hardware.class);
            Hardware savedHardware = hardwareService.createHardware(hardware, file);
            return ResponseEntity.status(HttpStatus.CREATED).body(savedHardware);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @PutMapping(value = "/update/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Hardware> updateHardware(
            @PathVariable Long id,
            @RequestPart("hardware") String hardwareJson,
            @RequestPart(value = "file", required = false) MultipartFile file) {
        try {
            Hardware hardware = objectMapper.readValue(hardwareJson, Hardware.class);
            Hardware updatedHardware = hardwareService.updateHardware(id, hardware, file);
            return ResponseEntity.ok(updatedHardware);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteHardware(@PathVariable Long id) {
        hardwareService.deleteHardware(id);
        return ResponseEntity.noContent().build();
    }
}
