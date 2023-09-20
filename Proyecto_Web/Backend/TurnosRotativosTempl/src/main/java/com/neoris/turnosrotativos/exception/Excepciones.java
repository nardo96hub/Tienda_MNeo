package com.neoris.turnosrotativos.exception;

public class Excepciones extends RuntimeException{
    private Integer status;
    private String statusNom;

    public Excepciones(String message,Integer status,String nom){
        super(message);
        this.status=status;
        this.statusNom=nom;
    }

    public Integer getStatus() {
        return status;
    }

    public String getStatusNom() {
        return statusNom;
    }
}
