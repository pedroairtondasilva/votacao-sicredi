package com.desafio.votacao.api.model;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.Valid;
import javax.validation.constraints.Size;

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
public class VotoInputDto {
    
    @Valid
    private PautaIdInputDto pauta;

    @Valid
    @Size(min = 11, max = 11)
    private String cpf;

    @Valid
    @Enumerated(EnumType.STRING)
    private OpcaoVoto opcapVoto;
}
