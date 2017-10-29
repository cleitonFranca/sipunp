package br.unp.web.html;

import java.util.List;

import javax.persistence.EntityManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import br.unp.domain.ControleAtendimento;
import gherkin.deps.com.google.gson.Gson;

@Controller
@RequestMapping("/relatorio")
public class Relatorio {
	
	@Autowired
	private EntityManager em;
	
	@RequestMapping("/index")
	public String index(Model model) {
		
		return "index";
		
	}
	
	@RequestMapping("/index2")
	@ResponseBody
	public ResponseEntity<List<?>> index2() {
		
		
		
		List c =  em.createNativeQuery("select e.bairro, count(c.id) as quantidadeRegioesAtendidas, "
				+ "avg(extract(year from age(cl.nascimento))) as faixa_etaria, "
				+ "e.cidade "
				+ "from controle_atendimento c "
				+ "inner join cliente cl ON c.cliente_id = cl.id  "
				+ "inner join endereco e on cl.endereco_id = e.id "
				+ "group by e.cidade, e.bairro;").getResultList();

		
		
		return new ResponseEntity<>(c, HttpStatus.OK);
		
	}

}
