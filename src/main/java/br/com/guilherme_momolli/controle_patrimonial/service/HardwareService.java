package br.com.guilherme_momolli.controle_patrimonial.service;

import br.com.guilherme_momolli.controle_patrimonial.model.Hardware;


import br.com.guilherme_momolli.controle_patrimonial.model.enums.Componente;
import br.com.guilherme_momolli.controle_patrimonial.repository.HardwareRepository;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.StringUtils;
import java.io.File;
import java.io.IOException;
import java.nio.file.*;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.stereotype.Service;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.Set;

import java.util.List;
import java.util.Optional;

@Service
public class HardwareService {

    private static final Logger logger = LoggerFactory.getLogger(HardwareService.class);
    private static final Set<String> EXTENSOES_PERMITIDAS = Set.of("jpg", "jpeg", "png");
    @Autowired
    private HardwareRepository hardwareRepository;

    @Value("${file.upload-dir}")
    private String uploadDir;

    public HardwareService(HardwareRepository hardwareRepository){
        this.hardwareRepository = hardwareRepository;
    }

    public ResponseEntity<String> uploadImagem(Long id, MultipartFile file) {
        try {
            Optional<Hardware> hardwareOptional = hardwareRepository.findById(id);
            if (hardwareOptional.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Hardware não encontrado");
            }

            Path uploadPath = Paths.get(uploadDir);
            if (!Files.exists(uploadPath)) {
                Files.createDirectories(uploadPath);
            }

            String fileExtension = StringUtils.getFilenameExtension(file.getOriginalFilename());
            String fileName = "hardware_" + System.currentTimeMillis() + "." + fileExtension;

            Path filePath = uploadPath.resolve(fileName);
            Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

            Hardware hardware = hardwareOptional.get();
            hardware.setImagemUrl(fileName);
            hardwareRepository.save(hardware);

            return ResponseEntity.ok("Imagem salva com sucesso!");
        } catch (IOException e) {
            return ResponseEntity.internalServerError().body("Erro ao salvar a imagem: " + e.getMessage());
        }
    }

    public ResponseEntity<byte[]> getImagem(String fileName) {
        try {
            Path imagePath = Paths.get(uploadDir).resolve(fileName);
            if (!Files.exists(imagePath)) {
                return ResponseEntity.notFound().build();
            }

            byte[] imageBytes = Files.readAllBytes(imagePath);

            String contentType = Files.probeContentType(imagePath);
            if (contentType == null) {
                contentType = "application/octet-stream";
            }

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.parseMediaType(contentType));

            return new ResponseEntity<>(imageBytes, headers, HttpStatus.OK);
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    public ResponseEntity<List<Hardware>> listarHardware(){
        try{
            return new ResponseEntity(hardwareRepository.findAll(), HttpStatus.OK);
        }
        catch(Exception exception){
            System.out.println(exception.getMessage());
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    public ResponseEntity<Hardware> getById(Long id) {
        try {
            Optional<Hardware> hardware = hardwareRepository.findById(id);
            return hardware.map(value -> new ResponseEntity<>(value, HttpStatus.OK)).orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
        } catch (Exception exception) {
            System.out.println(exception.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<List<Hardware>> getByCodigoPatrimonial(String codigoPatrimonial) {
        try {
            List<Hardware> hardwareList = hardwareRepository.findByCodigoPatrimonial(codigoPatrimonial);
            if (hardwareList.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
            return new ResponseEntity<>(hardwareList, HttpStatus.OK);
        } catch (Exception exception) {
            System.out.println(exception.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    public ResponseEntity<Hardware> criarHardware(Hardware hardware, MultipartFile imagem) {
        try {
            if (hardware.getComponente() == Componente.GABINETE
                    && (hardware.getCodigoPatrimonial() == null || hardware.getCodigoPatrimonial().isEmpty())) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
            }

            if (imagem != null && !imagem.isEmpty()) {
                String fileExtension = StringUtils.getFilenameExtension(imagem.getOriginalFilename());
                String fileName = "hardware_" + System.currentTimeMillis() + "." + fileExtension;

                Path uploadPath = Paths.get(uploadDir);
                if (!Files.exists(uploadPath)) {
                    Files.createDirectories(uploadPath);
                }

                Path filePath = uploadPath.resolve(fileName);
                Files.copy(imagem.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

                hardware.setImagemUrl(fileName);
            }

            Hardware savedHardware = hardwareRepository.save(hardware);
            return new ResponseEntity<>(savedHardware, HttpStatus.CREATED);
        } catch (IOException exception) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    public ResponseEntity<Map<String, List<Hardware>>> listarHardwareAgrupado() {
        try {
            List<Hardware> hardwares = hardwareRepository.findAll();

            Map<String, List<Hardware>> agrupados = hardwares.stream().collect(Collectors.groupingBy(Hardware::getCodigoPatrimonial));

            return new ResponseEntity<>(agrupados, HttpStatus.OK);
        } catch (Exception exception) {
            System.out.println(exception.getMessage());
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<Hardware> atualizarHardware(Long id, Hardware hardware){
        try {
            Optional<Hardware> hardwareAtualizar = hardwareRepository.findById(id);
            if (hardwareAtualizar.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }

            Hardware hardwareParaAtualizar = hardwareAtualizar.get();
            hardwareParaAtualizar.setCodigoPatrimonial(hardware.getCodigoPatrimonial());
            hardwareParaAtualizar.setFabricante(hardware.getFabricante());
            hardwareParaAtualizar.setModelo(hardware.getModelo());
            hardwareParaAtualizar.setNumeroSerial(hardware.getNumeroSerial());
            hardwareParaAtualizar.setPrecoTotal(hardware.getPrecoTotal());
            hardwareParaAtualizar.setVelocidade(hardware.getVelocidade());
            hardwareParaAtualizar.setDataFabricacao(hardware.getDataFabricacao());
            hardwareParaAtualizar.setCapacidadeArmazenamento(hardware.getCapacidadeArmazenamento());
            hardwareParaAtualizar.setComponente(hardware.getComponente());
            hardwareParaAtualizar.setEstatus(hardware.getEstatus());

            Hardware updatedHardware = hardwareRepository.save(hardwareParaAtualizar);
            return new ResponseEntity<>(updatedHardware, HttpStatus.OK);
        } catch (Exception exception) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<Void> deletarHardware(Long id){
        try {
            if (!hardwareRepository.existsById(id)) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
            hardwareRepository.deleteById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception exception) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
