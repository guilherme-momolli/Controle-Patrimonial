package br.com.guilherme_momolli.controle_patrimonial.excepitions.file_storage;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
public class FileStorageException extends RuntimeException{

    private static final long serialVersionUID = 1L;

    public FileStorageException(String exceptionString) {
        super(exceptionString);
    }

    public FileStorageException(String exceptionString, Throwable cause) {
        super(exceptionString, cause);
    }

    public FileStorageException(Throwable cause) {
        super();
    }
}

