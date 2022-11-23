package com.desafio.votacao.api.controller;

import java.util.List;

import javax.validation.Valid;

import com.desafio.votacao.api.model.VotoDto;
import com.desafio.votacao.api.model.VotoInputDto;
import com.desafio.votacao.domain.model.Voto;
import com.desafio.votacao.domain.service.VotoService;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/voto")
public class VotoController {

    @Autowired
    private VotoService votoService;

    @Autowired
    private ModelMapper modelMapper;

    @RequestMapping(method = RequestMethod.GET)
    public List<Voto> get() {
        return votoService.getRepo().findAll();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public VotoDto insert(@Valid @RequestBody VotoInputDto votoInputDto) {
        Voto voto = getDomainModel(votoInputDto);
        VotoDto votoDto = getRepresentantionModel(votoService.save(voto));
        return votoDto;
    }

    private Voto getDomainModel(VotoInputDto votoInputDto) {
        return modelMapper.map(votoInputDto, Voto.class);
    }

    private VotoDto getRepresentantionModel(Voto voto) {
        return modelMapper.map(voto, VotoDto.class);
    }

}