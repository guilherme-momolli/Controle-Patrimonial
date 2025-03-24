package br.com.guilherme_momolli.controle_patrimonial.service;

import br.com.guilherme_momolli.controle_patrimonial.configs.FileStorageConfig;
import br.com.guilherme_momolli.controle_patrimonial.excepitions.file_storage.FileStorageException;
import br.com.guilherme_momolli.controle_patrimonial.excepitions.file_storage.MyFileNotFoyndException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;

@Service
public class FileStorageService {

    private final Path fileStorageLocation;

    @Autowired
    public FileStorageService(FileStorageConfig fileStorageConfig) {
        this.fileStorageLocation = Path.of(fileStorageConfig.getUploadDir())
                .toAbsolutePath().normalize();
        try {
            Files.createDirectories(this.fileStorageLocation);  // Criação do diretório onde os arquivos serão armazenados
        } catch (Exception ex) {
            throw new FileStorageException("Não foi possível criar o diretório onde os arquivos serão armazenados.", ex);
        }
    }

    public String storeFile(MultipartFile file) {
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());  // Limpeza do nome do arquivo
        try {
            if (fileName.contains("..")) {  // Verificação de nome de arquivo inválido
                throw new FileStorageException("O nome do arquivo contém uma sequência de caminho inválida " + fileName);
            }


            // Resolve o caminho completo do arquivo e o copia
            Path targetLocation = this.fileStorageLocation.resolve(fileName);
            Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);  // Substitui se o arquivo já existir

            return fileName;
        } catch (Exception ex) {
            throw new FileStorageException("Não foi possível armazenar o arquivo " + fileName + ". Tente novamente!", ex);
        }
    }

    public byte[] getImagem(String fileName) {
        // Recupera o arquivo como bytes
        Path targetLocation = this.fileStorageLocation.resolve(fileName);
        try {
            return Files.readAllBytes(targetLocation);
        } catch (Exception ex) {
            throw new FileStorageException("Não foi possível recuperar o arquivo " + fileName + ". Tente novamente!", ex);
        }
    }

    public Resource loadFileAsResource(String fileName) {
        // Carrega o arquivo como um recurso (útil para download ou exibição)
        try {
            Path filePath = this.fileStorageLocation.resolve(fileName).normalize();
            Resource resource = new UrlResource(filePath.toUri());
            if (resource.exists()) {
                return resource;
            } else {
                throw new MyFileNotFoyndException("Arquivo não encontrado " + fileName);
            }
        } catch (Exception ex) {
            throw new MyFileNotFoyndException("Arquivo não encontrado " + fileName, ex);
        }
    }

    public String getCaminhoImagem(String fileName) {
        return this.fileStorageLocation.resolve(fileName).toString();
    }
}
