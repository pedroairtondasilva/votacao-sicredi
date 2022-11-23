package com.desafio.votacao.domain.repository;

import com.desafio.votacao.domain.model.OpcaoVoto;
import com.desafio.votacao.domain.model.Pauta;
import com.desafio.votacao.domain.model.Voto;

import org.springframework.data.jpa.repository.JpaRepository;

public interface VotoRepository extends JpaRepository<Voto, Long> {

    long countByPauta(Pauta pauta);
    long countByPautaAndOpcapVoto(Pauta pauta, OpcaoVoto opcapVoto);
    Voto findByCpfAndPauta(String cpf, Pauta pauta);
}

