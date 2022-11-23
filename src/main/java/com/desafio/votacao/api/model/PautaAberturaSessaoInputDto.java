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
public class PautaAberturaSessaoInputDto {
    
    private OffsetDateTime dataHoraFechamentoSessaoVotacao;

}
