package com.uol.testBackEndJava.demo.persistence.repository;

import com.uol.testBackEndJava.demo.persistence.model.Jogador;
import org.springframework.data.repository.CrudRepository;

public interface JogadorRepository extends CrudRepository<Jogador, Long>
{
}
