package br.com.guilherme_momolli.controle_patrimonial.excepitions.recource;

public class ResourceNotFoundException extends RuntimeException{

    private static final long serialVersionUID = 1L;

    public ResourceNotFoundException(String exceptionString) {
        super(exceptionString);
    }

    public ResourceNotFoundException(String exceptionString, Throwable cause) {
        super(exceptionString, cause);
    }

    public ResourceNotFoundException(Throwable cause) {
        super(cause);
    }

}
