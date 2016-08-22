package example.app.controllers;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1")
public class EndPoints {
	
	private static final Logger log = LoggerFactory.getLogger(EndPoints.class);
	
	private Map<String, String> heroes;
	
	@PostConstruct
	public void initHeroes() {
		heroes = new HashMap<String,String>();
		heroes.put("john", "john_blum");
		heroes.put("josh", "starbuxman");
		heroes.put("stu", "svrc");
		heroes.put("dormain", "DormainDrewitz");
		heroes.put("cornelia", "caseywest");
		heroes.put("saman", "kennybastani");
		heroes.put("casey", "MkHeck");
		heroes.put("kenny", "JavaFXpert");
		heroes.put("jim", "cdavisafc");
		heroes.put("mark", "err_sage");
	}
	
	@Cacheable(cacheNames = "hero")
	@RequestMapping("/getTwitterHandle/{name}")
	public String getHero(@PathVariable String name) {
		String result = expensiveLookUp(name);
		log.debug("+++++++++++++++++++++++ Returning hero: " + result + "+++++++++++++++++++++++");
		return result;
	}

	private String expensiveLookUp(String name) {
		log.debug("$$$$$$$$$$$$$$$ In the expensive look up for the name: " + name + " $$$$$$$$$$$$$$$");
		return heroes.get(name);
	}
	
}

@RestController
class HomeController {

	@RequestMapping("/")
	public String home() {
		return "Run /v1/getHero/{name} to find the twitter handle of your hero";
	}

}
