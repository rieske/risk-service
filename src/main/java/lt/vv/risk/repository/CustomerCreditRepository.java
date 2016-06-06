package lt.vv.risk.repository;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.stereotype.Repository;


@Repository
public class CustomerCreditRepository {

	private final Map<String, Integer> customerCredits = new ConcurrentHashMap<>();

	public Integer getCustomerCredit(String emailAddress) {
		return customerCredits.getOrDefault(emailAddress, 0);
	}

	public void creditCustomerWithAmount(String emailAddress, Integer creditAmount) {
		customerCredits.put(emailAddress, getCustomerCredit(emailAddress) + creditAmount);
	}
}
