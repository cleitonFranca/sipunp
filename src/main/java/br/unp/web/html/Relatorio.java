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

import com.google.common.collect.Lists;

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
		
		@SuppressWarnings("rawtypes")
		List c = Lists.newArrayList();
		
		try {
			
			c =  em.createNativeQuery("select e.bairro, count(c.id) as quantidadeRegioesAtendidas, "
					+ "avg(extract(year from age(cl.nascimento))) as faixa_etaria, "
					+ "e.cidade "
					+ "from controle_atendimento c "
					+ "inner join cliente cl ON c.cliente_id = cl.id  "
					+ "inner join endereco e on cl.endereco_id = e.id "
					+ "group by e.cidade, e.bairro;").getResultList();
			
		} catch (Exception e) {
			new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}
		

		
		
		return new ResponseEntity<>(c, HttpStatus.OK);
		
	}
	

	@RequestMapping("/index3")
	@ResponseBody
	public ResponseEntity<List<?>> index3() {
		
		@SuppressWarnings("rawtypes")
		List c = Lists.newArrayList();
		
		try {
			
			c =  em.createNativeQuery("select a.nome, count(c.id) as quantidade from controle_atendimento c "
					+ "left join aluno a on c.aluno_id = a.id "
					+ "left join cliente cl on c.cliente_id = cl.id group by a.nome").getResultList();
			
		} catch (Exception e) {
			new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}
		
		return new ResponseEntity<>(c, HttpStatus.OK);
		
	}
	
	@RequestMapping("/faixaEtaria")
	@ResponseBody
	public ResponseEntity<List<?>> index4() {
		
		@SuppressWarnings("rawtypes")
		List c = Lists.newArrayList();
		
		
		try {
			
			c =  em.createNativeQuery("select distinct extract(year from age(cl.nascimento)) from controle_atendimento c "
					+ "left join aluno a on c.aluno_id = a.id "
					+ "left join cliente cl on c.cliente_id = cl.id order by extract(year from age(cl.nascimento)) asc").getResultList();
		
			
		} catch (Exception e) {
			new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}
		
		
		
		return new ResponseEntity<>(c, HttpStatus.OK);
		
	}

	@SuppressWarnings({ "rawtypes", "unused" })
	private List buscaPorFaixaEtaria(Integer de, Integer a) {
		
		try {
			return em.createNativeQuery("select extract(year from age(cl.nascimento)) from controle_atendimento c "
					+ "left join aluno a on c.aluno_id = a.id "
					+ "left join cliente cl on c.cliente_id = cl.id "
					+ "where extract(year from age(cl.nascimento)) between :de and :a ")
					.setParameter("de", de).setParameter("a", a).getResultList();
			
		} catch (Exception e) {
			
		}
		return null;
		
	}

}
