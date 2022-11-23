package com.desafio.votacao.api.model;

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
public class PautaInputDto {
    
    @Valid
    @Size(min = 3, max = 100)
    private String titulo;
}
