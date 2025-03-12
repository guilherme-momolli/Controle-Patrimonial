package br.com.guilherme_momolli.controle_patrimonial.service;

import org.springframework.stereotype.Service;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.nio.file.InvalidPathException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.Files;

@Service
public class FileService {

    private final Path uploadDir = Paths.get("C:/uploads").toAbsolutePath().normalize();
    private static final String UPLOAD_DIR = "uploads/";


    public ResponseEntity<Resource> getFile(String fileName) {
        try {
            Path filePath = uploadDir.resolve(fileName).normalize();
            if (!filePath.startsWith(uploadDir)) {
                return ResponseEntity.badRequest().body(null);
            }

            if (!Files.exists(filePath)) {
                return ResponseEntity.notFound().build();
            }

            Resource resource = new UrlResource(filePath.toUri());
            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"" + fileName + "\"")
                    .body(resource);

        } catch (InvalidPathException e) {
            return ResponseEntity.badRequest().body(null);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }
}
