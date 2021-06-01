package br.com.jogos;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.fail;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import br.com.jogos.domain.Jogo;
import br.com.jogos.domain.JogoService;
import br.com.jogos.domain.dto.JogoDTO;
import br.com.jogos.domain.exception.ObjectNotFoundException;


@RunWith(SpringRunner.class)
@SpringBootTest
public class JogosServiceTest {
	
	@Autowired
	private JogoService service;
	
	
	@Test
	public void testInserirSalvarEmSeguidaDeletar() {
		//teste inserir e deletar
		
		//cenario
		Jogo jogo = new Jogo();
		jogo.setNome("Killer");
		jogo.setTipo("classicos");
		
		//acao
		JogoDTO j = service.insert(jogo);
		
		//verificacao
		assertNotNull(j);
		
		// verifica se o id nao esta vazio
		Long id = j.getId();
		assertNotNull(id);
		
		//busca objeto e verifica se ele existe no banco
		j = service.getJogosById(id);
		assertNotNull(j);
		
		//confirma se possui o mesmo nome e tipo
		assertEquals("Killer", j.getNome());
		assertEquals("classicos", j.getTipo());
		
		//delete objeto
		service.delete(id);
		
		//verifica se foi deletado
		
		try {
			assertNull(service.getJogosById(id));
			fail("O jogo nao foi deletado"); // executado, somente quando há algo de errado no codigo
		}catch(ObjectNotFoundException e) {
			//ok, lanca a excecao
		}
		
		
		
	}
	
	@Test
	public void testListaPorJogos() {
		
		List<JogoDTO> jogos = service.getJogos();
	
		assertEquals(18, jogos.size());
	}
	
	@Test
	public void testListaPorTipo() {
		assertEquals(6, service.getJogosByTipo("classicos").size());
		assertEquals(6, service.getJogosByTipo("rpg").size());
		assertEquals(6, service.getJogosByTipo("fps").size());
		
		assertEquals(0, service.getJogosByTipo("x").size());
	}
	
	@Test
	public void testGet() {
		JogoDTO j = service.getJogosById(15L);	//busca o jogo na posição 15
		
		assertNotNull(j);//verifica se nao é nullo
		
		assertEquals("World of Warcraft", j.getNome()); // verifica se possui os mesmos dados
		
	}
	
}

