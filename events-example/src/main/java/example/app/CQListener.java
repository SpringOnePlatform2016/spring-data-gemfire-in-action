package example.app;

import org.springframework.stereotype.Component;
import com.gemstone.gemfire.cache.query.CqEvent;

@Component("cqListener")
public class CQListener {
	
	public void handleEvent(CqEvent event) {
		System.out.println("Key: " + event.getKey() + " Value: " + event.getNewValue());
    }

}
