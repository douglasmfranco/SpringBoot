package br.com.jogos.domain;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;


import br.com.jogos.domain.dto.JogoDTO;
import br.com.jogos.domain.exception.ObjectNotFoundException;

@Service
public class JogoService {

	@Autowired
	private JogoRepository rep;

	public List<JogoDTO> getJogos() {

		return rep.findAll().stream().map(JogoDTO::create).collect(Collectors.toList());

	}

	public JogoDTO getJogosById(Long id) {
		Optional<Jogo> jogo = rep.findById(id);
		return jogo.map(JogoDTO::create).orElseThrow(() -> new ObjectNotFoundException("Jogo não encontrado!")); // converte um optional para outro

	}

	public List<JogoDTO> getJogosByTipo(String tipo) {
		return rep.findByTipo(tipo).stream().map(JogoDTO::create).collect(Collectors.toList());
	}

	public void delete(Long id) {
		rep.deleteById(id);
	}

	public JogoDTO insert(Jogo jogo) {
		Assert.isNull(jogo.getId(), "nao foi possivel registrar jogo");
		return JogoDTO.create(rep.save(jogo));
	}

	public JogoDTO update(Jogo jogo, Long id) {
		Assert.notNull(id, "nao foi possivel atualizar jogo");

		// busca no banco
		Optional<Jogo> optional = rep.findById(id);
		if (optional.isPresent()) {
			// se o carro existe no banco pelo id
			Jogo db = optional.get();
			// copia as propriedades
			db.setNome(jogo.getNome());
			db.setTipo(jogo.getTipo());
			System.out.println("Jogo id: " + db.getId());

			// atualiza o jogo
			rep.save(db);

			// ele é retornado abaixo
			return JogoDTO.create(db);

		} else {// se nao existir retorna nulo
			return null;
			// throw new RuntimeException("nao foi possivel atualizar jogo");
		}

	}

	public List<Jogo> getJogosFake() {
		List<Jogo> jogos = new ArrayList<Jogo>();

		jogos.add(new Jogo(1L, "Counter Strike"));
		jogos.add(new Jogo(2L, "Dota"));
		jogos.add(new Jogo(3L, "Combat Arms"));

		return jogos;
	}

}