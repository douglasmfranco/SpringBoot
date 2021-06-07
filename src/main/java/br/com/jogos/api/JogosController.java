package br.com.jogos.api;

import java.net.URI;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import br.com.jogos.domain.Jogo;
import br.com.jogos.domain.JogoService;
import br.com.jogos.domain.dto.JogoDTO;

@RestController
@RequestMapping("/api/v1/jogos")
public class JogosController {

	@Autowired
	private JogoService service;

	@GetMapping() // lista todos os jogos
	public ResponseEntity<List<JogoDTO>> get() {
		return ResponseEntity.ok(service.getJogos());

	}

	@GetMapping("/{id}") // procura pelo id
	public ResponseEntity getJogosById(@PathVariable("id") Long id) {
		JogoDTO jogo = service.getJogosById(id);

		return  ResponseEntity.ok(jogo); // retorna 200 ok

	}

	@GetMapping("/tipo/{tipo}") // procura pelo tipo e lista todos do mesmo tipo
	public ResponseEntity<List<JogoDTO>> getByTipo(@PathVariable("tipo") String tipo) {
		List<JogoDTO> jogos = service.getJogosByTipo(tipo);

		return jogos.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(jogos);

	}
	


	@PostMapping // adiciona um novo objeto e o salva no banco
	@Secured({"ROLE_ADMIN"})
	// # Security  / spring.security.user.name=user / spring.security.user.password=user
	public ResponseEntity post(@RequestBody Jogo jogo) {
			
			JogoDTO j = service.insert(jogo);
			URI location = getUri(j.getId());
			return ResponseEntity.created(location).build(); // ao criar o objeto mostra o location dele no header

	}

	private URI getUri(Long id) {
		return ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(id).toUri();
	}

	@PutMapping("/{id}") // altera o objeto, buscando pelo id
	public ResponseEntity put(@PathVariable("id") Long id, @RequestBody Jogo jogo) {
		JogoDTO j = service.update(jogo, id);

		return j != null ? 
				ResponseEntity.ok(j) : 
				ResponseEntity.notFound().build();
	}

	@DeleteMapping("/{id}")
	public ResponseEntity delete(@PathVariable("id") Long id) {

		service.delete(id);
		return 	ResponseEntity.ok().build();  // se encontrar o jogo, ele ira deleta-lo do banco
		
	}

}
