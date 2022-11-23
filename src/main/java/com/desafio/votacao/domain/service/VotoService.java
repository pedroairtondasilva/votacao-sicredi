package com.desafio.votacao.domain.service;

import com.desafio.votacao.domain.exception.EntidadeNaoEncontradaException;
import com.desafio.votacao.domain.exception.NegocioExcpetion;
import com.desafio.votacao.domain.model.Pauta;
import com.desafio.votacao.domain.model.Voto;
import com.desafio.votacao.domain.repository.PautaRepository;
import com.desafio.votacao.domain.repository.VotoRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class VotoService {
 
    private static final String urlBaseToCheckCpf = "https://user-info.herokuapp.com/users/{cpf}/";

    @Autowired
    private VotoRepository votoRepository;

    @Autowired
    private PautaRepository pautaRepository;    


    public VotoRepository getRepo() {
        return votoRepository;
    }
    
    private boolean cpfPodeVotar(String cpf) {
        try {
            String urlToCheckCpf = urlBaseToCheckCpf.replace("{cpf}", cpf);

            RestTemplate restTemplate = new RestTemplate();
            log.info("Fazendo consulta para verificar se o CPF '{}'' está habilitado a votar.", cpf);
            log.info(urlToCheckCpf);
            String result = restTemplate.getForObject(urlToCheckCpf, String.class);
            log.info("Resultado da Consulta: '{}'", result);
            if (result.contains("\"status\":\"ABLE_TO_VOTE\"")) {
                log.info("CPF habilitado para votar.");
                return true;
            }
            log.info("CPF não habilitado a votar.");
            return false;            
        } catch (Exception e) {
            throw new NegocioExcpetion(
                String.format("Não foi possível checar se o cpf '%s' está habilitado ou não a votar.", cpf),
                e);
        }
    }

    public Voto save(Voto voto) {
        if (!cpfPodeVotar(voto.getCpf())) {
            throw new NegocioExcpetion(String.format("O cpf '%s' não está habilitado a votar.", voto.getCpf()));
        }

        Pauta pauta = pautaRepository.findById(voto.getPauta().getId())
                .orElseThrow(() -> new EntidadeNaoEncontradaException("Pauta não encontrada."));

        if (!pauta.isEmPeriodoVotacao()) {
            throw new NegocioExcpetion("A pauta em questão não está em período de votação.");
        }
        
        voto.setPauta(pauta);

        Voto votoComMesmoCpf = votoRepository.findByCpfAndPauta(voto.getCpf(), pauta);
        if ((votoComMesmoCpf != null) && (!votoComMesmoCpf.equals(voto))) {
            throw new NegocioExcpetion(String.format(
                "Já existe um voto registrado para o cpf '%s' para a pauta '%s'(id: %d).",
                voto.getCpf(),
                voto.getPauta().getTitulo(),
                voto.getPauta().getId()));
        }

        return votoRepository.save(voto);
    }

}
