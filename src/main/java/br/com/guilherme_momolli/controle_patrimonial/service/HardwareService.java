package br.com.guilherme_momolli.controle_patrimonial.service;

import br.com.guilherme_momolli.controle_patrimonial.model.Hardware;


import br.com.guilherme_momolli.controle_patrimonial.model.enums.Componente;
import br.com.guilherme_momolli.controle_patrimonial.repository.HardwareRepository;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.stereotype.Service;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.Optional;

@Service
public class HardwareService {

    @Autowired
    private HardwareRepository hardwareRepository;

    public HardwareService(HardwareRepository hardwareRepository){this.hardwareRepository = hardwareRepository;}

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

    public ResponseEntity<Hardware> criarHardware(@RequestBody Hardware hardware) {
        try {
            if (hardware.getComponente() == Componente.GABINETE && (hardware.getCodigoPatrimonial() == null || hardware.getCodigoPatrimonial().isEmpty())) {
                return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
            }

            Hardware saveHardware = hardwareRepository.save(hardware);
            return new ResponseEntity<>(saveHardware, HttpStatus.CREATED);
        } catch (Exception exception) {
            System.out.println(exception.getMessage());
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    public ResponseEntity<Map<String, List<Hardware>>> listarHardwareAgrupado() {
        try {
            List<Hardware> hardwares = hardwareRepository.findAll();

            // Agrupa os hardwares pelo código patrimonial
            Map<String, List<Hardware>> agrupados = hardwares.stream()
                    .collect(Collectors.groupingBy(Hardware::getCodigoPatrimonial));

            return new ResponseEntity<>(agrupados, HttpStatus.OK);
        } catch (Exception exception) {
            System.out.println(exception.getMessage());
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<Hardware> atualizarHardware(@PathVariable Long id, @RequestBody Hardware hardware){
        try{
            Optional<Hardware> hardwareAtualizar = hardwareRepository.findById(id);
            Hardware hardwareParaAtualizar = hardwareAtualizar.get();
            hardwareParaAtualizar.setCodigoPatrimonial(hardware.getCodigoPatrimonial());
            //hardwareParaAtualizar.setComponente(hardware.getComponente());
            hardwareParaAtualizar.setFabricante(hardware.getFabricante());
            hardwareParaAtualizar.setModelo(hardware.getFabricante());
            hardwareParaAtualizar.setNumeroSerial(hardware.getNumeroSerial());
            hardwareParaAtualizar.setPrecoTotal(hardware.getPrecoTotal());
            hardwareParaAtualizar.setVelocidade(hardware.getVelocidade());
            hardwareParaAtualizar.setPrecoTotal(hardware.getPrecoTotal());
            hardwareParaAtualizar.setDataFabricacao(hardware.getDataFabricacao());
            hardwareParaAtualizar.setCapacidadeArmazenamento(hardware.getCapacidadeArmazenamento());
            //hardwareParaAtualizar.setEstatus(hardware.getEstatus());

            Hardware update = hardwareRepository.save(hardwareParaAtualizar);
            return new ResponseEntity<>(update, HttpStatus.OK);
        }catch (Exception exception){
            System.out.println(exception.getMessage());
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<Hardware> deletarHardware(@PathVariable Long id){
        try {
            hardwareRepository.deleteById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }catch (Exception exception){
            System.out.println(exception.getMessage());
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
