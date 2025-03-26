package br.com.guilherme_momolli.controle_patrimonial.service;

import br.com.guilherme_momolli.controle_patrimonial.model.Hardware;
import br.com.guilherme_momolli.controle_patrimonial.model.enums.Componente;
import br.com.guilherme_momolli.controle_patrimonial.repository.HardwareRepository;

import jakarta.transaction.Transactional;

import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class HardwareService {

    private final HardwareRepository hardwareRepository;
    private final FileStorageService fileStorageService;

    public HardwareService(HardwareRepository hardwareRepository, FileStorageService fileStorageService) {
        this.hardwareRepository = hardwareRepository;
        this.fileStorageService = fileStorageService;
    }

    public ResponseEntity<Hardware> createHardware(Hardware hardware, MultipartFile file) {
        try {
            if (hardware.getComponente() == Componente.GABINETE
                    && (hardware.getCodigoPatrimonial() == null || hardware.getCodigoPatrimonial().isEmpty())) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
            }
            if (file != null && !file.isEmpty()) {
                String imagemUrl = fileStorageService.storeFile(file);
                hardware.setImagemUrl(imagemUrl);
            }
            Hardware savedHardware = hardwareRepository.save(hardware);
            return new ResponseEntity<>(savedHardware, HttpStatus.CREATED);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @Transactional
    public ResponseEntity<Hardware> updateHardware(Long id, Hardware hardware, MultipartFile file) {
        try {
            Hardware existingHardware = hardwareRepository.findById(id).orElseThrow(() -> new RuntimeException("Hardware não encontrado"));

            existingHardware.setCodigoPatrimonial(hardware.getCodigoPatrimonial());
            existingHardware.setFabricante(hardware.getFabricante());
            existingHardware.setModelo(hardware.getModelo());
            existingHardware.setNumeroSerial(hardware.getNumeroSerial());
            existingHardware.setPrecoTotal(hardware.getPrecoTotal());
            existingHardware.setVelocidade(hardware.getVelocidade());
            existingHardware.setDataFabricacao(hardware.getDataFabricacao());
            existingHardware.setCapacidadeArmazenamento(hardware.getCapacidadeArmazenamento());
            existingHardware.setComponente(hardware.getComponente());
            existingHardware.setEstatus(hardware.getEstatus());

            if (file != null && !file.isEmpty()) {
                String fileName = fileStorageService.storeFile(file);
                existingHardware.setImagemUrl(fileName);
            }
            Hardware updatedHardware = hardwareRepository.save(existingHardware);
            return new ResponseEntity<>(updatedHardware, HttpStatus.OK);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    public ResponseEntity<List<Hardware>> listHardware() {
        try {
            return new ResponseEntity<>(hardwareRepository.findAll(), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<Hardware> getById(Long id) {
        return hardwareRepository.findById(id)
                .map(hardware -> new ResponseEntity<>(hardware, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    public ResponseEntity<List<Hardware>> getByCodigoPatrimonial(String codigoPatrimonial) {
        List<Hardware> hardwareList = hardwareRepository.findByCodigoPatrimonial(codigoPatrimonial);
        return hardwareList.isEmpty()
                ? new ResponseEntity<>(HttpStatus.NOT_FOUND)
                : new ResponseEntity<>(hardwareList, HttpStatus.OK);
    }

    public ResponseEntity<Map<String, List<Hardware>>> listarHardwareAgrupado() {
        try {
            List<Hardware> hardwares = hardwareRepository.findAll();
            Map<String, List<Hardware>> agrupados = hardwares.stream()
                    .collect(Collectors.groupingBy(Hardware::getCodigoPatrimonial));
            return new ResponseEntity<>(agrupados, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<Void> deleteHardware(Long id) {
        if (!hardwareRepository.existsById(id)) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        hardwareRepository.deleteById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
