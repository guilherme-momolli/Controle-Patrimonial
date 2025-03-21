package br.com.guilherme_momolli.controle_patrimonial.excepitions.file_storage;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class MyFileNotFoyndException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public MyFileNotFoyndException(String exceptionString) {
        super(exceptionString);
    }

    public MyFileNotFoyndException(String exceptionString, Throwable cause) {
        super(exceptionString, cause);
    }
}
