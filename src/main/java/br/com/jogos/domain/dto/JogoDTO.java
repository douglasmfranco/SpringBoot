package br.com.jogos.domain.dto;

import org.modelmapper.ModelMapper;

import br.com.jogos.domain.Jogo;
import lombok.Data;

@Data
public class JogoDTO {
	private Long id;
	private String nome;
	private String tipo;
	
	
	
	
	public static JogoDTO create(Jogo j) { //gera um jogo a partir do jogoDTO
		
		ModelMapper modelMapper = new ModelMapper();
		return modelMapper.map(j, JogoDTO.class);
	}
	
	
}
