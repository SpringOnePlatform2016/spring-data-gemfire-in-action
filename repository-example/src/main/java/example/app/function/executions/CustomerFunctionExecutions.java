package example.app.function.executions;

import java.util.List;

import org.springframework.data.gemfire.function.annotation.OnRegion;

import example.app.model.Customer;

/**
 * The CustomerFunctionExecutions class...
 *
 * @author John Blum
 * @since 1.0.0
 */
@OnRegion(region = "Customers")
@SuppressWarnings("unused")
public interface CustomerFunctionExecutions {

	List<Customer> findAllCustomersWithContactInformation();

}
