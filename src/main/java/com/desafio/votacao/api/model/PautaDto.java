package com.desafio.votacao.api.model;

import java.time.OffsetDateTime;

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
public class PautaDto {
    
    private long id;
    private String titulo;
    private OffsetDateTime dataHoraAberturaSessaoVotacao;
    private OffsetDateTime dataHoraFechamentoSessaoVotacao;

}
