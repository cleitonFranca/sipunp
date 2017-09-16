package br.unp.web.html;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/relatorio")
public class Relatorio {
	
	@RequestMapping("/index")
	public String index() {
		
		return "index";
		
	}

}
