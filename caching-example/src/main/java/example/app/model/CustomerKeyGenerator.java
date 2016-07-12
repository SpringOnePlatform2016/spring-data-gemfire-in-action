/**
 * 
 */
package example.app.model;

import java.lang.reflect.Method;

import org.springframework.cache.interceptor.KeyGenerator;

/**
 * @author lshannon
 *
 */
public class CustomerKeyGenerator implements KeyGenerator {

	@Override
	public Object generate(Object arg0, Method arg1, Object... arg2) {
		return (String)arg2[0];
	}

}
