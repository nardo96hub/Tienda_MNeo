package com.neoris.turnosrotativos.services.imp;

import com.neoris.turnosrotativos.entities.Concepto;
import com.neoris.turnosrotativos.exception.Excepciones;
import com.neoris.turnosrotativos.repositories.RepositoryConcepto;
import com.neoris.turnosrotativos.services.ServiceConcepto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ServiceImpConcepto implements ServiceConcepto {

    @Autowired
    private RepositoryConcepto repoConcepto;

    // Retorna todos los conceptos
    @Override
    public List<Concepto> allConcepto() {
      return repoConcepto.findAll();
    }



}
