package com.uol.testBackEndJava.demo.controller;

import com.uol.testBackEndJava.demo.dto.JogadorDto;
import com.uol.testBackEndJava.demo.dto.JogadorInfoDto;
import com.uol.testBackEndJava.demo.persistence.model.Jogador;
import com.uol.testBackEndJava.demo.service.JogadorService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/players")
@Tag(name = "Jogador", description = "API do jogador")
public class JogadorController {

    private final ModelMapper modelMapper;
    private final JogadorService jogadorService;

    public JogadorController(JogadorService jogadorService, ModelMapper modelMapper) {
        this.jogadorService = jogadorService;
        this.modelMapper = modelMapper;
    }
    @Operation(summary = "Busca Jogadores", description = "Busca todos jogadores cadastrados", tags = {"jogador"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Busca realizada com sucesso", content = @Content(array = @ArraySchema(schema = @Schema(implementation = JogadorDto.class))))})
    @GetMapping
    public List<JogadorDto> findAll(){
        List<Jogador> jogadores = jogadorService.obtemJogadores();
        return jogadores.stream().map(this::convertToDto).collect(Collectors.toList());
    }
    @Operation(summary = "Adiciona Jogador", description =  "Retorna um único jogador", tags = {"jogador"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Jogador criado com sucesso", content = @Content(schema = @Schema(implementation = JogadorDto.class)))})
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public JogadorDto create(@io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Jogador para adicionar. Não pode ser nulo ou vazio",
    content = @Content(schema = @Schema(implementation = JogadorInfoDto.class)), required = true)
                             @RequestBody JogadorInfoDto jogador){
        return convertToDto(jogadorService.adicionaJogador(convertToSaveEntity(jogador)));
    }

    private JogadorDto convertToDto(Jogador jogador) {
        JogadorDto jogadorDto = modelMapper.map(jogador, JogadorDto.class);
        return jogadorDto;
    }

    private Jogador convertToSaveEntity(JogadorInfoDto jogadorInfoDto) {
        Jogador jogador = modelMapper.map(jogadorInfoDto, Jogador.class);
        return jogador;
    }

}

