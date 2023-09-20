package com.neoris.turnosrotativos.services;

import com.neoris.turnosrotativos.entities.Concepto;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


public interface ServiceConcepto {
    List<Concepto> allConcepto(); //Retorna todos los conceptos


}
