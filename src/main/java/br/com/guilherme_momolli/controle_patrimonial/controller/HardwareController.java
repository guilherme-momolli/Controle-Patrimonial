package br.com.guilherme_momolli.controle_patrimonial.controller;

import br.com.guilherme_momolli.controle_patrimonial.model.Hardware;
import br.com.guilherme_momolli.controle_patrimonial.service.HardwareService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@CrossOrigin
@RestController
@RequestMapping("/hardware")
public class HardwareController {


    private HardwareService hardwareService;

    @Autowired
    public HardwareController(HardwareService hardwareService) {
        this.hardwareService = hardwareService;
    }

    @GetMapping("/hello")
    public String helloWorld() {
        return "Olá, mundo!";
    }

    @GetMapping("/list")
    public ResponseEntity<List<Hardware>> getAllHardwares() {
        return hardwareService.listarHardware();
    }

    @GetMapping("/list/{id}")
    public ResponseEntity<Hardware> getHardwareById(@PathVariable Long id) {
        return hardwareService.getById(id);
    }

    @GetMapping("/list/patrimonio/{codigoPatrimonial}")
    public ResponseEntity<List<Hardware>> getHardwareByCodigoPatrimonial(@PathVariable String codigoPatrimonial){
        return hardwareService.getByCodigoPatrimonial(codigoPatrimonial);
    }
    @PostMapping("/create")
    public ResponseEntity<Hardware> criarHardware(@RequestBody Hardware hardware) {
        return hardwareService.criarHardware(hardware);
    }

    @GetMapping("/list/agrupado")
    public ResponseEntity<Map<String, List<Hardware>>> listarAgrupado() {
        return hardwareService.listarHardwareAgrupado();
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Hardware> updateHardware(@PathVariable Long id, @RequestBody Hardware hardware) {
        return  hardwareService.atualizarHardware(id, hardware);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Hardware> deleterHardware(@PathVariable Long id) {
        return hardwareService.deletarHardware(id);
    }
}