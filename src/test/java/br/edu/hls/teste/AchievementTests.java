package br.edu.hls.teste;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import br.edu.hls.exception.AchievementJaExisteRuntimeException;
import br.edu.hls.model.Achievement;
import br.edu.hls.model.Badge;
import br.edu.hls.model.Points;
import br.edu.hls.persist.AchievementStorage;
import br.edu.hls.persist.AchievementStorageFactory;
import br.edu.hls.persist.MemoryAchievementStorage;
import br.edu.hls.service.ForumService;
import br.edu.hls.service.ForumServiceGamificationProxy;
import br.edu.hls.service.observer.CreationObserver;
import br.edu.hls.service.observer.ParticipationObserver;
import br.edu.hls.util.Util;

/**
 * Classe de testes do projeto
 * @author Hugo Silva
 *
 */
public class AchievementTests {
	
	private AchievementStorage storage;
	private ForumServiceGamificationProxy serviceProxy;
	private ForumService serviceMock;
	
	@Before
	public void before() {
		AchievementStorageFactory.setAchievementStorage(new MemoryAchievementStorage());
		
		storage = AchievementStorageFactory.getAchievementStorage();
		
		serviceMock = mock(ForumService.class);
		
		serviceProxy = new ForumServiceGamificationProxy(serviceMock);
	}
	
	/*
	 * Teste da Implementação - Tópico 1 
	 * Chamar o método addTopic() e ver se os achievements foram adicionados da forma correta
	 */
	@Test
	public void addTopic_funcionalidadeEQuantidades() {
		List<Achievement> listaAchievements = new ArrayList<>();
		
		Achievement foo1 = new Points("Foo1", 10);
		Achievement foo2 = new Points("Foo2", 40);
		Achievement foo3 = new Points("Foo3", 5);
		Achievement bar1 = new Badge("Bar1");
		Achievement bar2 = new Badge("Bar2");

		foo1.adiciona(listaAchievements);
		foo2.adiciona(listaAchievements);
		foo3.adiciona(listaAchievements);
		bar1.adiciona(listaAchievements);
		bar2.adiciona(listaAchievements);
		
		assertEquals(5, listaAchievements.size());
		assertTrue(listaAchievements.contains(foo1));
		assertTrue(listaAchievements.contains(foo2));
		assertTrue(listaAchievements.contains(foo3));
		assertTrue(listaAchievements.contains(bar1));
		assertTrue(listaAchievements.contains(bar2));
	}
	
	@Test
	public void addTopic_naoAceitaDuplicadosMesmoTipoPoints() {
		List<Achievement> listaAchievements = new ArrayList<>();
		
		Points foo1 = new Points("Foo", 10);
		Points foo2 = new Points("Foo", 5);

		foo1.adiciona(listaAchievements);
		foo2.adiciona(listaAchievements);
		
		assertEquals(1,  listaAchievements.size());
		
		Points foo = (Points) listaAchievements.stream().findFirst().get();
		
		assertEquals(15, foo.getValor());
		
	}
	
	@Test
	public void addTopic_naoAceitaDuplicadosMesmoTipoBadge() {
		List<Achievement> listaAchievements = new ArrayList<>();
		
		Badge foo1 = new Badge("Bar");
		Badge foo2 = new Badge("Bar");

		foo1.adiciona(listaAchievements);
		foo2.adiciona(listaAchievements);
		
		assertEquals(1,  listaAchievements.size());
	}
	
	@Test(expected = AchievementJaExisteRuntimeException.class)
	public void addTopic_naoAceitaDuplicadosTiposDiferente() {
		List<Achievement> listaAchievements = new ArrayList<>();
		
		Achievement foo1 = new Points("Foo", 10);
		Achievement foo2 = new Badge("Foo");

		foo1.adiciona(listaAchievements);
		foo2.adiciona(listaAchievements);
		
	}

	/*
	 * Teste da Implementação - Tópico 2 
	 * Chamar o método addComment() e ver se os achievements foram adicionados da forma correta
	 */
	@Test
	public void addComment_verificaAchievementsAdicionados() {
		String usuario = "Fulano";
		String topico = "Topico";

		serviceProxy.addComment(usuario, topico, "Foo");

		//Verifica se addComment foi chamado
		Mockito.verify(serviceMock, Mockito.times(1)).addComment(usuario, topico, "Foo");
		
		//Busca Achievements do Usuario no storage e verifica quantidade
		List<Achievement> listaAchievements = storage.getAchievements(usuario);
		assertNotNull(listaAchievements);
		assertEquals(2, listaAchievements.size());

		//Verifica Badge
		assertNotNull(storage.getAchievement(usuario, Util.LET_ME_ADD));
		
		//Verifica Points
		Points participation = (Points) storage.getAchievement(usuario, Util.PARTICIPATION);
		assertNotNull(participation);
		assertEquals(3, participation.getValor());
		
		
	}
	
	/*
	 * Teste da Implementação - Tópico 3 
	 * Chamar o método likeTopic() e ver se os achievements foram adicionados da forma correta
	 */
	@Test
	public void likeTopic_verificaAchievementsAdicionados() {
		String usuario = "Fulano";
		String topico = "Topico";
		String usuarioTopico = "Beltrano";
		serviceProxy.likeTopic(usuario, topico, usuarioTopico);
		
		//Verifica se likeTopic foi chamado
		verify(serviceMock, Mockito.times(1)).likeTopic(usuario, topico, usuarioTopico);
		
		//Busca Achievements do Usuario no storage e verifica quantidade
		List<Achievement> listaAchievements = storage.getAchievements(usuario);
		assertNotNull(listaAchievements);
		assertEquals(1, listaAchievements.size());

		//Verifica Points
		Points creation = (Points) storage.getAchievement(usuario, Util.CREATION);
		assertNotNull(creation);
		assertEquals(1, creation.getValor());
	}
	
	/*
	 * Teste da Implementação - Tópico 4 
	 * Chamar o método likeComment() e ver se os achievements foram adicionados da forma correta
	 */
	@Test
	public void likeComment_verificaAchievementsAdicionados() {
		String usuario = "Fulano";
		String topico = "Topico";
		String usuarioComentario = "Beltrano";
		String comentario = "Ipsum Lorem";
		serviceProxy.likeComment(usuario, topico, comentario, usuarioComentario);
		
		//Verifica se likeTopic foi chamado
		verify(serviceMock, Mockito.times(1)).likeComment(usuario, topico, comentario, usuarioComentario);
		
		//Busca Achievements do Usuario no storage  e verifica quantidade
		List<Achievement> listaAchievements = storage.getAchievements(usuario);
		assertNotNull(listaAchievements);
		assertEquals(1, listaAchievements.size());


		//Verifica Points
		Points participation = (Points) storage.getAchievement(usuario, Util.PARTICIPATION);
		assertNotNull(participation);
		assertEquals(1, participation.getValor());
	}
	
	/*
	 * Teste da Implementação - Tópico 5
	 * Chamar o método addTopic() duas vezes e ver se os pontos foram somados e se o badge está presente apenas uma vez
	 */
	@Test
	public void addTopic_chamadoDuasVezes() {
		String usuario = "Fulano";
		String topicoUm = "Topico 1";
		String topicoDois = "Topico 2";
		serviceProxy.addTopic(usuario, topicoUm);
		serviceProxy.addTopic(usuario, topicoDois);
		
		//Verifica se addTopic foi chamado
		verify(serviceMock, Mockito.times(1)).addTopic(usuario, topicoUm);
		verify(serviceMock, Mockito.times(1)).addTopic(usuario, topicoDois);
		
		//Busca Achievements do Usuario no storage e verifica quantidade
		List<Achievement> lsitaAchievements = storage.getAchievements(usuario);
		assertNotNull(lsitaAchievements);
		assertEquals(2, lsitaAchievements.size());
		
		//Verifica Badge
		assertNotNull(storage.getAchievement(usuario, Util.I_CAN_TALK));
		
		//Verifica Points
		Points creation = (Points) storage.getAchievement(usuario, Util.CREATION);
		assertNotNull(creation);
		assertEquals(10, creation.getValor());
		
	}
	
	/*
	 * Teste da Implementação - Tópico 6 
	 * Fazer um teste invocando vários métodos e verificar se o resultado é o esperado.
	 */
	@Test
	public void testaInvocacaoMetodosDiversos() {
		String usuario = "Fulano";
		String topicoUm = "Topico 1";
		String topicoDois = "Topico 2";
		String usuarioComentario = "Beltrano";
		String comentario = "Ipsum Lorem";
		String comentarioDois = "Lorem ipsum dolor sit amet";
		
		serviceProxy.addTopic(usuario, topicoUm);
		serviceProxy.addTopic(usuario, topicoDois);
		serviceProxy.likeTopic(usuarioComentario, topicoUm, usuario);
		serviceProxy.addComment(usuarioComentario, topicoUm, comentario);
		serviceProxy.likeComment(usuario, topicoUm, comentarioDois, usuarioComentario);
		
		//Verifica chamadas aos metodos
		verify(serviceMock, Mockito.times(1)).addTopic(usuario, topicoUm);
		verify(serviceMock, Mockito.times(1)).addTopic(usuario, topicoDois);
		verify(serviceMock, Mockito.times(1)).likeTopic(usuarioComentario, topicoUm, usuario);
		verify(serviceMock, Mockito.times(1)).addComment(usuarioComentario, topicoUm, comentario);
		verify(serviceMock, Mockito.times(1)).likeComment(usuario, topicoUm, comentarioDois, usuarioComentario);
		
		//Busca Achievements de usuario no storage e verifica quantidade
		List<Achievement> listaAchivsUsuario = storage.getAchievements(usuario);
		assertNotNull(listaAchivsUsuario);
		assertEquals(3, listaAchivsUsuario.size());
		
		//Verifica Badges - usuario
		assertNotNull(storage.getAchievement(usuario, Util.I_CAN_TALK));
		
		//Verifica Point - usuario
		Points creationUsuario = (Points) storage.getAchievement(usuario, Util.CREATION);
		assertNotNull(creationUsuario);
		assertEquals(10, creationUsuario.getValor());

		Points participationUsuario = (Points) storage.getAchievement(usuario, Util.PARTICIPATION);
		assertNotNull(participationUsuario);
		assertEquals(1, participationUsuario.getValor());
		
		//Busca Achievements de usuarioComentario no storage e verifica quantidade
		List<Achievement> listaAchivsUsuarioComentario = storage.getAchievements(usuarioComentario);
		assertNotNull(listaAchivsUsuarioComentario);
		assertEquals(3, listaAchivsUsuarioComentario.size());
		
		//Verifica Badges - usuarioComentario
		assertNotNull(storage.getAchievement(usuarioComentario, Util.LET_ME_ADD));
		
		//Verifica Points - usuarioComentario
		Points creationUsuarioComentario = (Points) storage.getAchievement(usuarioComentario, Util.CREATION);
		assertNotNull(creationUsuarioComentario);
		assertEquals(1, creationUsuarioComentario.getValor());

		Points participationUsuarioComentario = (Points) storage.getAchievement(usuarioComentario, Util.PARTICIPATION);
		assertNotNull(participationUsuarioComentario);
		assertEquals(3, participationUsuarioComentario.getValor());
	}
	
	/*
	 * Teste da Implementação - Tópico 7 
	 * Fazer o mock lançar uma exceção para algum método e verificar se os Achievements não foram adicionados.
	 */
	@Test
	public void likeTopic_comportamentoExcecaoLancada() {
		String usuario = "Fulano";
		String topicoUm = "Topico 1";
		String usuarioLike = "Beltrano";
		
		serviceProxy.addTopic(usuario, topicoUm);
		doThrow(new RuntimeException()).when(serviceMock).likeTopic(usuarioLike, topicoUm, usuario);
		try {
			serviceProxy.likeTopic(usuarioLike, topicoUm, usuario);
		} catch (Exception e){
			//do nothing
		}
		
		//Verifica chamadas aos metodos
		verify(serviceMock, Mockito.times(1)).addTopic(usuario, topicoUm);
		verify(serviceMock, Mockito.times(1)).likeTopic(usuarioLike, topicoUm, usuario);
		
		//Busca Achievements do Usuario no storage e verifica quantidade
		List<Achievement> listaAchievements = storage.getAchievements(usuario);
		assertNotNull(listaAchievements);
		assertEquals(2, listaAchievements.size());
		
		//Busca Achievements do Usuario no storage e verifica quantidade
		List<Achievement> listaAchievementsUsuarioLike = storage.getAchievements(usuarioLike);
		assertNull(listaAchievementsUsuarioLike);
		
				
	}
	
	/*
	 * Teste da Implementação - Tópico 8 
	 * Atingir 100 pontos de "CREATION" e verificar se o usuário recebe o badge "INVENTOR"
	 */
	@Test
	public void creationObserver_reachBadgeInventor() {
		//Adiciona o observer ao storage
		storage.addObservable(new CreationObserver());
		
		//Adiciona 20 topicos para totalizar os 100 pts necessarios
		String usuario = "Fulano";
		String topico = "Topico_";
		IntStream.range(0, 20).forEach(i -> serviceProxy.addTopic(usuario, topico + i));

		//Verifica chamadas aos metodos
		IntStream.range(0, 20).forEach(i -> verify(serviceMock, Mockito.times(1)).addTopic(usuario, topico + i));
		
		//Busca Achievements do Usuario no storage e verifica quantidade
		List<Achievement> achievements = storage.getAchievements(usuario);
		assertNotNull(achievements);
		assertEquals(3, achievements.size());
		
		//Verifica Badges
		assertNotNull(storage.getAchievement(usuario, Util.I_CAN_TALK));
		assertNotNull(storage.getAchievement(usuario, Util.INVENTOR));

		//Verifica Points
		assertNotNull(storage.getAchievement(usuario, Util.CREATION));
		assertEquals(100, ((Points) storage.getAchievement(usuario, Util.CREATION)).getValor());
	}
	
	/*
	 * Teste da Implementação - Tópico 9 
	 * Atingir 100 pontos de "PARTICIPATION" e verificar se o usuário recebe o badge "PART OF THE COMMUNITY"
	 */
	@Test
	public void teste_reachBadgePartOfComunity() {
		//Adiciona o observer ao storage
		storage.addObservable(new ParticipationObserver());
		
		//Adiciona um topico
		String usuario = "Fulano";
		String topico = "Topico";
		String comentario = "Comentario_";
		serviceProxy.addTopic(usuario, topico);
		
		//Adiciona 34 comentarios ao topico para atingir os 100pts necessarios
		IntStream.range(0, 34).forEach(i -> serviceProxy.addComment(usuario, topico, comentario+i));

		//Verifica chamadas aos metodos
		verify(serviceMock, Mockito.times(1)).addTopic(usuario, topico);
		IntStream.range(0, 34).forEach(i -> verify(serviceMock, Mockito.times(1)).addComment(usuario, topico, comentario+i));
		
		//Busca Achievements do Usuario no storage e verifica quantidade
		List<Achievement> achievements = storage.getAchievements(usuario);
		assertNotNull(achievements);
		assertEquals(5, achievements.size());
		
		//Verifica Badges
		assertNotNull(storage.getAchievement(usuario, Util.LET_ME_ADD));
		assertNotNull(storage.getAchievement(usuario, Util.PART_OF_THE_COMMUNITY));

		//Verifica Points
		assertNotNull(storage.getAchievement(usuario, Util.PARTICIPATION));
		assertEquals(102, ((Points) storage.getAchievement(usuario, Util.PARTICIPATION)).getValor());
	}
}
