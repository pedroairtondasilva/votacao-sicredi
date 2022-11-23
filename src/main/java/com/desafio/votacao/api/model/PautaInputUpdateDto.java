package com.desafio.votacao.api.model;

import java.time.OffsetDateTime;

import javax.validation.Valid;
import javax.validation.constraints.Size;

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
public class PautaInputUpdateDto {
    
    @Valid
    @Size(min = 3, max = 100)
    private String titulo;
    private OffsetDateTime dataHoraAberturaSessaoVotacao;
    private OffsetDateTime dataHoraFechamentoSessaoVotacao;

}
