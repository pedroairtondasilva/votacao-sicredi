package com.desafio.votacao.domain.repository;

import com.desafio.votacao.domain.model.Pauta;

import org.springframework.data.jpa.repository.JpaRepository;

public interface PautaRepository extends JpaRepository<Pauta, Long> {
    
    Pauta findByTitulo(String titulo);
}

