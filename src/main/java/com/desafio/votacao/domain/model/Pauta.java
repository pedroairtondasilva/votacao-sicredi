package com.desafio.votacao.domain.model;

import java.time.OffsetDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Transient;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode(of = { "id" })
public class Pauta {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(nullable = false, length = 100)
    private String titulo;

    // @JsonFormat(shape = JsonFormat.Shape.STRING, pattern =
    // "yyyy-MM-dd'T'HH:mm:ss.SSSXXX")
    @Column(nullable = true)
    private OffsetDateTime dataHoraAberturaSessaoVotacao;

    @Column(nullable = true)
    private OffsetDateTime dataHoraFechamentoSessaoVotacao;

    @Transient
    public boolean hasDatasDeAberturaAndFechamentoDefinidas() {
        return getDataHoraAberturaSessaoVotacao() != null && getDataHoraFechamentoSessaoVotacao() != null;
    }

    @Transient
    public boolean hasDataFechamentoPosteriorDataAbertura() {
        return hasDatasDeAberturaAndFechamentoDefinidas()
                && getDataHoraFechamentoSessaoVotacao().isAfter(getDataHoraAberturaSessaoVotacao());
    }

    @Transient
    public boolean isEmPeriodoVotacao() {
        OffsetDateTime now = OffsetDateTime.now();
        if (hasDataFechamentoPosteriorDataAbertura()
                && now.isAfter(getDataHoraAberturaSessaoVotacao())
                && now.isBefore(getDataHoraFechamentoSessaoVotacao())) {
                    return true;
                }
        return false;
    }

}
