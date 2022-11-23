package com.desafio.votacao.domain.service;

import java.time.OffsetDateTime;

import com.desafio.votacao.domain.exception.EntidadeNaoEncontradaException;
import com.desafio.votacao.domain.exception.NegocioExcpetion;
import com.desafio.votacao.domain.model.Pauta;
import com.desafio.votacao.domain.repository.PautaRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PautaService {

    @Autowired
    private PautaRepository pautaRepository;

    public PautaRepository getRepo() {
        return pautaRepository;
    }

    public Pauta save(Pauta pauta) {
        Pauta pautaComMesmoTitulo = pautaRepository.findByTitulo(pauta.getTitulo());
        if ((pautaComMesmoTitulo != null) && (!pautaComMesmoTitulo.equals(pauta))) {
            throw new NegocioExcpetion("Já existe um pauta com este mesmo titulo.");
        }

        if (pauta.hasDatasDeAberturaAndFechamentoDefinidas()
            && !pauta.hasDataFechamentoPosteriorDataAbertura()) {
            throw new NegocioExcpetion(String.format(
                    "A data/hora de fechamento da sessão de votação de uma pauta deve ser posterior a data/hora de abertura '%s'.",
                    pauta.getDataHoraAberturaSessaoVotacao()));
        }

        return pautaRepository.save(pauta);
    }

    public Pauta abrirSessao(long id, OffsetDateTime dataHoraFechamentoSessaoVotacao) {
        Pauta pauta = pautaRepository.findById(id)
                .orElseThrow(() -> new EntidadeNaoEncontradaException("Pauta não encontrada."));

        // Se a pauta já encontra-se aberta, ela será retornada.
        if (pauta.getDataHoraAberturaSessaoVotacao() != null) {
            throw new NegocioExcpetion("Pauta já foi aberta.");
        }

        pauta.setDataHoraAberturaSessaoVotacao(OffsetDateTime.now());
        if (dataHoraFechamentoSessaoVotacao == null) {
            pauta.setDataHoraFechamentoSessaoVotacao(pauta.getDataHoraAberturaSessaoVotacao().plusMinutes(1));
        } else {
            pauta.setDataHoraFechamentoSessaoVotacao(dataHoraFechamentoSessaoVotacao);
        }


        return this.save(pauta);
    }

    public void deleteById(Long id) {
        pautaRepository.deleteById(id);
    }

    public Pauta findByIdOrRaiseException(long id) {
        Pauta pauta = pautaRepository.findById(id)
                .orElseThrow(() -> new EntidadeNaoEncontradaException("Pauta não encontrada."));
        return pauta;
    }

}
