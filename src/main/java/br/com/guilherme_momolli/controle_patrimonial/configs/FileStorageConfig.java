package br.com.guilherme_momolli.controle_patrimonial.configs;

import org.springframework.boot.context.properties.ConfigurationProperties;


@ConfigurationProperties(prefix = "file")
public class FileStorageConfig {

    private String uploadDir;

    public FileStorageConfig(String uploadDir) {
        this.uploadDir = uploadDir;
    }

    public String getUploadDir() {
        return uploadDir;
    }

    public void setUploadDir(String uploadDir) {
        this.uploadDir = uploadDir;
    }
}
