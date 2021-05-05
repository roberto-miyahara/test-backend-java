package com.uol.testBackEndJava.demo.service;

import com.uol.testBackEndJava.demo.persistence.model.Jogador;
import com.uol.testBackEndJava.demo.persistence.repository.JogadorRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;

@Component
public class JogadorService {

    private final JogadorRepository jogadorRepository;

    public JogadorService(JogadorRepository jogadorRepository) { this.jogadorRepository = jogadorRepository; }

    public List<Jogador> obtemJogadores() { return (List<Jogador>) jogadorRepository.findAll(); }

    public Jogador getJogador(Long id) {
        Optional<Jogador> jogador = jogadorRepository.findById(id);
        if(jogador.isEmpty()){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Jogador não encontrado na base de dados");
        }
        return jogador.get();
    }
    public Jogador adicionaJogador(Jogador jogador) { return jogadorRepository.save(jogador); }

}
