package com.desafio.votacao.api.controller;

import java.util.List;

import javax.validation.Valid;

import com.desafio.votacao.api.model.ApuracaoDto;
import com.desafio.votacao.api.model.PautaAberturaSessaoInputDto;
import com.desafio.votacao.api.model.PautaDto;
import com.desafio.votacao.api.model.PautaInputDto;
import com.desafio.votacao.api.model.PautaInputUpdateDto;
import com.desafio.votacao.domain.model.OpcaoVoto;
import com.desafio.votacao.domain.model.Pauta;
import com.desafio.votacao.domain.service.PautaService;
import com.desafio.votacao.domain.service.VotoService;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/pauta")
public class PautaController {

    @Autowired
    private PautaService pautaService;
    @Autowired
    private VotoService votoService;

    @Autowired
    private ModelMapper modelMapper;

    @RequestMapping(method = RequestMethod.GET)
    public List<Pauta> get() {
        return pautaService.getRepo().findAll();
    }

    // @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    // public ResponseEntity<Pauta> getById(@PathVariable(value = "id") long id) {
    // Optional<Pauta> pauta = pautaService.getRepo().findById(id);
    // if (pauta.isPresent())
    // return new ResponseEntity<Pauta>(pauta.get(), HttpStatus.OK);
    // else
    // return new ResponseEntity<>(HttpStatus.NOT_FOUND);''
    // }

    @GetMapping("/{id}")
    public PautaDto getById(@PathVariable(value = "id") long id) {
        Pauta pauta = pautaService.findByIdOrRaiseException(id);
        PautaDto pautaDto = getRepresentantionModel(pauta);
        return pautaDto;
    }

    @GetMapping("apuracao/{id}")
    public ApuracaoDto getApuracao(@PathVariable(value = "id") long id) {
        Pauta pauta = pautaService.findByIdOrRaiseException(id);
        
        ApuracaoDto apuracaoDto = new ApuracaoDto();
        apuracaoDto.setPauta(getRepresentantionModel(pauta));
        apuracaoDto.setQtdVotos(votoService.getRepo().countByPauta(pauta));
        apuracaoDto.setQtdVotosSim(votoService.getRepo().countByPautaAndOpcapVoto(pauta, OpcaoVoto.SIM));
        apuracaoDto.setQtdVotosNao(votoService.getRepo().countByPautaAndOpcapVoto(pauta, OpcaoVoto.NAO));
        return apuracaoDto;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public PautaDto insert(@Valid @RequestBody PautaInputDto pautaInputDto) {
        Pauta pauta = getDomainModel(pautaInputDto);
        PautaDto pautaDto = getRepresentantionModel(pautaService.save(pauta));
        return pautaDto;
    }

    // @RequestMapping(value = "sessao/abrir/", method = RequestMethod.PATCH)
    // public ResponseEntity<PautaDto> abrirSessao(@RequestBody
    // PautaAberturaSessaoInputDto pautaAberturaSessaoInputDto) {
    // Optional<Pauta> optPauta =
    // pautaService.getRepo().findById(pautaAberturaSessaoInputDto.getId());
    // if (optPauta.isPresent()) {
    // Pauta pauta = optPauta.get();
    // PautaDto pautaDto = null;

    // // Se a pauta já encontra-se aberta, ela será retornada.
    // if (pauta.getDataHoraAberturaSessaoVotacao() != null) {
    // pautaDto = getRepresentantionModel(pauta);
    // return new ResponseEntity<PautaDto>(pautaDto, HttpStatus.OK);
    // }

    // pauta.setDataHoraAberturaSessaoVotacao(OffsetDateTime.now());
    // if (pautaAberturaSessaoInputDto.getDataHoraFechamentoSessaoVotacao() == null)
    // {
    // pauta.setDataHoraFechamentoSessaoVotacao(pauta.getDataHoraAberturaSessaoVotacao().plusMinutes(1));
    // } else {
    // pauta.setDataHoraFechamentoSessaoVotacao(pautaAberturaSessaoInputDto.getDataHoraFechamentoSessaoVotacao());
    // }
    // pautaDto = getRepresentantionModel(pautaService.save(pauta));
    // return new ResponseEntity<PautaDto>(pautaDto, HttpStatus.OK);
    // } else {
    // return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    // }

    // }

    @PatchMapping("sessao/abrir/{id}")
    public PautaDto abrirSessao(@PathVariable(value = "id") long id,
            @RequestBody PautaAberturaSessaoInputDto pautaAberturaSessaoInputDto) {
        Pauta pauta = pautaService.abrirSessao(id, pautaAberturaSessaoInputDto.getDataHoraFechamentoSessaoVotacao());
        PautaDto pautaDto = getRepresentantionModel(pauta);
        return pautaDto;
    }

    @PutMapping("/{id}")
    public PautaDto update(@PathVariable(value = "id") long id,
            @Valid @RequestBody PautaInputUpdateDto pautaInputUpdateDto) {
        Pauta pauta = pautaService.findByIdOrRaiseException(id);
        pauta.setTitulo(pautaInputUpdateDto.getTitulo());
        pauta.setDataHoraAberturaSessaoVotacao(pautaInputUpdateDto.getDataHoraAberturaSessaoVotacao());
        pauta.setDataHoraFechamentoSessaoVotacao(pautaInputUpdateDto.getDataHoraFechamentoSessaoVotacao());
        pautaService.save(pauta);

        PautaDto pautaDto = getRepresentantionModel(pauta);
        return pautaDto;
    }

    // @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    // public ResponseEntity<Pauta> delete(@PathVariable(value = "id") long id) {
    //     Optional<Pauta> pautaOpt = pautaService.getRepo().findById(id);
    //     if (pautaOpt.isPresent()) {
    //         Pauta pauta = pautaOpt.get();
    //         pautaService.getRepo().delete(pauta);
    //         return ResponseEntity.noContent().build();
    //     } else {
    //         return ResponseEntity.notFound().build();
    //     } 
    // }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable(value = "id") long id) {
        Pauta pauta = pautaService.findByIdOrRaiseException(id);
        pautaService.getRepo().delete(pauta);
    }

    private Pauta getDomainModel(PautaInputDto pautaInputDto) {
        return modelMapper.map(pautaInputDto, Pauta.class);
    }

    private PautaDto getRepresentantionModel(Pauta pauta) {
        return modelMapper.map(pauta, PautaDto.class);
    }

}