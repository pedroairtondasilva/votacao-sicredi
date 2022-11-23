package com.desafio.votacao.api.model;

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
public class ApuracaoDto {
    
    private PautaDto pauta;
    private long qtdVotos;
    private long qtdVotosSim;
    private long qtdVotosNao;

}
