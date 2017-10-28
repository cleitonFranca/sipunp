package br.unp.relatorio;

import javax.persistence.EntityManager;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.env.Environment;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import br.unp.UnpsipApp;
import br.unp.domain.ControleAtendimento;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = UnpsipApp.class)
@SpringBootTest
public class RelatorioTest {
	
	@Autowired
	private EntityManager em;
	
	@Test
	public void conn() {
		Assert.assertTrue(em.isOpen());
		
	}
	
	@Test
	public void  controle_de_atendimento() {
		ControleAtendimento c =  (ControleAtendimento) em.createQuery("select c from ControleAtendimento c").getSingleResult();
		
		System.out.println(c);
		
	}
	
	

}
