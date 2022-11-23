package com.desafio.votacao.api.model;

import com.desafio.votacao.domain.model.OpcaoVoto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class VotoDto {
    
    private long Id;
    private PautaDto pauta; 
    private String cpf;
    private OpcaoVoto opcapVoto;
}
