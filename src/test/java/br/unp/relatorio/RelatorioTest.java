package br.unp.relatorio;

import java.util.List;

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
	public void controle_de_atendimento() {
		List<ControleAtendimento> c =  
				em.createNativeQuery("select * from controle_atendimento c inner join cliente cl ON c.cliente_id = cl.id", ControleAtendimento.class)
				.getResultList();
		
		System.out.println(c.get(0).getCliente());
		System.out.println(c.get(0).getCliente().getEndereco());
		
	}
	
	

}
