package br.com.guilherme_momolli.controle_patrimonial.service;

import br.com.guilherme_momolli.controle_patrimonial.configs.FileStorageConfig;
import br.com.guilherme_momolli.controle_patrimonial.excepitions.file_storage.FileStorageException;
import br.com.guilherme_momolli.controle_patrimonial.excepitions.file_storage.MyFileNotFoyndException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.UUID;

@Service
public class FileStorageService {

    private final Path fileStorageLocation;

    @Autowired
    public FileStorageService(FileStorageConfig fileStorageConfig) {
        this.fileStorageLocation = Path.of(fileStorageConfig.getUploadDir())
                .toAbsolutePath().normalize();
        try {
            Files.createDirectories(this.fileStorageLocation);
        } catch (Exception ex) {
            throw new FileStorageException("Não foi possível criar o diretório onde os arquivos serão armazenados.", ex);
        }
    }

    public String storeFile(MultipartFile file) {
        String originalFileName = StringUtils.cleanPath(file.getOriginalFilename());
        try {
            if (originalFileName.contains("..")) {
                throw new FileStorageException("O nome do arquivo contém uma sequência de caminho inválida " + originalFileName);
            }

            String fileName = UUID.randomUUID().toString() + "_" + originalFileName;

            Path targetLocation = this.fileStorageLocation.resolve(fileName);
            Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);

            return fileName;
        } catch (Exception ex) {
            throw new FileStorageException("Não foi possível armazenar o arquivo " + originalFileName + ". Tente novamente!", ex);
        }
    }

    public byte[] getImagem(String fileName) {
        try {
            Path targetLocation = this.fileStorageLocation.resolve(fileName);
            return Files.readAllBytes(targetLocation);
        } catch (Exception ex) {
            throw new FileStorageException("Não foi possível recuperar o arquivo " + fileName + ". Tente novamente!", ex);
        }
    }

    public Resource loadFileAsResource(String fileName) {
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

    public void deleteFile(String fileName) {
        try {
            Path filePath = this.fileStorageLocation.resolve(fileName);
            Files.deleteIfExists(filePath);
        } catch (Exception ex) {
            throw new FileStorageException("Erro ao excluir o arquivo: " + fileName, ex);
        }
    }

    public String getCaminhoImagem(String fileName) {
        return this.fileStorageLocation.resolve(fileName).toString();
    }
}
