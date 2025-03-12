package br.com.guilherme_momolli.controle_patrimonial.controller;

import br.com.guilherme_momolli.controle_patrimonial.service.FileService;
import org.springframework.core.io.Resource;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@CrossOrigin
@RestController
@RequestMapping("/uploads")
public class FileController {

    private FileService fileService;

    public FileController(FileService fileService) {
        this.fileService = fileService;
    }

    @GetMapping("/{fileName}")
    public ResponseEntity<Resource> getFile(@PathVariable String fileName) {
        return fileService.getFile(fileName);
    }
}

