package br.com.alura.leilao.dao;

import java.math.BigDecimal;
import java.time.LocalDate;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;

import org.junit.Assert;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import br.com.alura.leilao.model.Leilao;
import br.com.alura.leilao.model.Usuario;
import br.com.alura.leilao.util.JPAUtil;

class LeilaoDaoTest {
	
	private LeilaoDao dao;
	private EntityManager em;
	
	@BeforeEach
	public void beforeEach() {
		this.em = JPAUtil.getEntityManager();
		this.dao = new LeilaoDao(em);
		em.getTransaction().begin();
	}
	
	@AfterEach
	public void afterEach() {
		em.getTransaction().rollback();
	}
	
	@Test
	void deveriaCadastrarUmLeilao() {
		Usuario usuario = criarUsuario();
		Leilao leilao = new Leilao ("Mochila", new BigDecimal("70"), LocalDate.now(), usuario);
		
		leilao = dao.salvar(leilao);
		
		Leilao salvo = dao.buscarPorId (leilao.getId());
		Assert.assertNotNull(salvo);
	}
	
	@Test
	void deveriaAtualizarUmLeilao() {
		Usuario usuario = criarUsuario();
		Leilao leilao = new Leilao ("Mochila", new BigDecimal("70"), LocalDate.now(), usuario);
		
		leilao = dao.salvar(leilao);
		
		leilao.setNome("Caderno");
		leilao.setValorInicial(new BigDecimal("30"));
		
		leilao = dao.salvar(leilao);
		
		Leilao salvo = dao.buscarPorId (leilao.getId());
		Assert.assertEquals("Caderno", salvo.getNome());
		Assert.assertEquals(new BigDecimal("30"), salvo.getValorInicial());
	}
	
	private Usuario criarUsuario() {
		Usuario usuario = new Usuario ("fulano", "fulano@fulano.com", "do1ao6");
		em.persist(usuario);
		return usuario;
	}

}
