package example.app.controllers;

import java.util.Date;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import example.app.model.Customer;

@RestController
public class EndPoints {

	@RequestMapping("/")
	public String home() {
		return "Run /getCacheable to test using the Cache. Run /get to test not using. Both call a timer (20 secs), /getCacheable should only call it the first time, after that it will return in ms";
	}
	
	@Cacheable(cacheNames = "Customer", key="{ #root.methodName, #id }")
	@RequestMapping("/getCacheable")
	public Customer getClientCache(String id) {
		return simulateSlowService();
	}
	
	private Customer simulateSlowService() {
        try {
            long time = 20000L;
            Thread.sleep(time);
            Customer customer = new Customer();
            customer.setBirthday(new Date());
            customer.setCity("Toronto");
            customer.setEmailAddress("email@email.com");
            customer.setName("Jimbo");
            customer.setId("JimboId");
            return customer;
        } catch (InterruptedException e) {
            throw new IllegalStateException(e);
        }
    }
	
}
