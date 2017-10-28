package br.unp.web.html;

import javax.persistence.EntityManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import br.unp.domain.ControleAtendimento;

@Controller
@RequestMapping("/relatorio")
public class Relatorio {
	
	@Autowired
	private EntityManager em;
	
	@RequestMapping("/index")
	public String index() {
		
		ControleAtendimento c =  (ControleAtendimento) em.createQuery("select c from ControleAtendimento c").getSingleResult();
		
		System.out.println(c);
		
		return "index";
		
	}

}
