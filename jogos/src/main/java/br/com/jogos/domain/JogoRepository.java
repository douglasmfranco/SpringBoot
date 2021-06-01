package br.com.jogos.domain;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;


public interface JogoRepository extends JpaRepository <Jogo, Long>{

	List<Jogo> findByTipo(String tipo);
}
