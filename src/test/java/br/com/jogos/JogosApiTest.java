package br.com.jogos;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import br.com.jogos.domain.Jogo;
import br.com.jogos.domain.dto.JogoDTO;

@RunWith(SpringRunner.class)
@SpringBootTest(classes=JogosApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class JogosApiTest {
	@Autowired
	protected TestRestTemplate rest;
	
	private ResponseEntity <JogoDTO> getJogo(String url){
		return 
				rest.withBasicAuth("user","user").getForEntity(url, JogoDTO.class);
	}
	
	private ResponseEntity <List<JogoDTO>> getJogos(String url){
		
		return rest.withBasicAuth("user","user").exchange(url, HttpMethod.GET, null, new ParameterizedTypeReference<List<JogoDTO>>() {});
	}
	
	@Test
	public void testInserirSalvarEmSeguidaDeletar() {
		
		//cenario
		Jogo jogo = new Jogo();
		jogo.setNome("Killer");
		jogo.setTipo("classicos");
		
		//insert
		//postForEntity faz com que a propria API converte o cenario para um json
		ResponseEntity response = rest.withBasicAuth("douglas","admin").postForEntity("/api/v1/jogos", jogo, null);
		
		//verificacao de criacao objeto
		assertEquals(HttpStatus.CREATED, response.getStatusCode());
		
		// busca objeto
		String location = response.getHeaders().get("location").get(0);
		JogoDTO j = getJogo(location).getBody(); // pega o novo jogo que foi salvo pelo location dele
		
		assertNotNull(j); //faz as verificacoes,
		assertEquals("Killer", j.getNome());
		assertEquals("classicos",j.getTipo());
		
		
		//delete objeto
		rest.withBasicAuth("douglas","admin").delete(location);
		
		//verifica se foi deletado
		assertEquals(HttpStatus.NOT_FOUND, getJogo(location).getStatusCode());
				
	}
	
	
	  @Test
	    public void testListaDeJogo() {
	        List<JogoDTO> jogos = getJogos("/api/v1/jogos").getBody(); //faz a requisicao na Api dos jogos
	        assertNotNull(jogos);  // verifica que nao estao vazios
	        assertEquals(18, jogos.size());// faz a comparacao para ver se tem 18 jogos
	    }

	    @Test
	    public void testListaPorTipoDeJogo() {

	        assertEquals(6, getJogos("/api/v1/jogos/tipo/classicos").getBody().size());
	        assertEquals(6, getJogos("/api/v1/jogos/tipo/fps").getBody().size());
	        assertEquals(6, getJogos("/api/v1/jogos/tipo/rpg").getBody().size());
	        
	        //verifica que nao tem o jogo de tipo 'x' e retorna o erro HTTP-204
	        assertEquals(HttpStatus.NO_CONTENT, getJogos("/api/v1/jogos/tipo/x").getStatusCode());
	    }

	    @Test
	    public void testJogoIncluidoBanco() {
	    	//testa um jogo que esta no banco
	        ResponseEntity<JogoDTO> response = getJogo("/api/v1/jogos/15");
	        assertEquals(response.getStatusCode(), HttpStatus.OK);

	        JogoDTO j = response.getBody();
	        assertEquals("World of Warcraft", j.getNome());
	    }

	    @Test
	    public void testJogoNaoIncluidoBanco() {
	    	//testa um jogo que nao consta no banco
	        ResponseEntity response = getJogo("/api/v1/carros/100");
	        assertEquals(response.getStatusCode(), HttpStatus.NOT_FOUND);
	    }
	}
	
	


