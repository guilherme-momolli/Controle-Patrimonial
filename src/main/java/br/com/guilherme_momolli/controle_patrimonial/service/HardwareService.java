package br.com.guilherme_momolli.controle_patrimonial.service;

import br.com.guilherme_momolli.controle_patrimonial.excepitions.file_storage.FileStorageException;
import br.com.guilherme_momolli.controle_patrimonial.model.Hardware;
import br.com.guilherme_momolli.controle_patrimonial.model.enums.Componente;
import br.com.guilherme_momolli.controle_patrimonial.repository.HardwareRepository;

import jakarta.transaction.Transactional;

import lombok.RequiredArgsConstructor;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class HardwareService {

    private final HardwareRepository hardwareRepository;
    private final FileStorageService fileStorageService;

    public Hardware createHardware(Hardware hardware, MultipartFile file) {
        if (hardware.getComponente() == Componente.GABINETE && (hardware.getCodigoPatrimonial() == null || hardware.getCodigoPatrimonial().isEmpty())) {
            throw new IllegalArgumentException("Código patrimonial é obrigatório para GABINETE.");
        }
        if (file != null && !file.isEmpty()) {
            String imagemUrl = fileStorageService.storeFile(file);
            hardware.setImagemUrl(imagemUrl);
        }
        return hardwareRepository.save(hardware);
    }

    @Transactional
    public Hardware updateHardware(Long id, Hardware hardware, MultipartFile file) {
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
        return hardwareRepository.save(existingHardware);
    }

    public List<Hardware> listHardware() {
        return hardwareRepository.findAll();
    }

    public Optional<Hardware> getById(Long id) {
        return hardwareRepository.findById(id);
    }

    public Map<String, List<Hardware>> listarHardwareAgrupado() {
        return hardwareRepository.findAll().stream()
                .collect(Collectors.groupingBy(Hardware::getCodigoPatrimonial));
    }

    public void deleteHardware(Long id) {
        if (!hardwareRepository.existsById(id)) {
            throw new RuntimeException("Hardware não encontrado");
        }
        hardwareRepository.deleteById(id);
    }
}
