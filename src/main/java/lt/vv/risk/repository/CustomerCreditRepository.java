package lt.vv.risk.repository;

import java.util.Map;

import org.springframework.stereotype.Repository;

import com.google.common.base.Optional;
import com.google.common.collect.Maps;

@Repository
public class CustomerCreditRepository {

	private final Map<String, Integer> customerCredits = Maps.newHashMap();

	public Integer getCustomerCredit(String emailAddress) {
		return Optional.fromNullable(customerCredits.get(emailAddress)).or(0);
	}

	public void creditCustomerWithAmount(String emailAddress, Integer creditAmount) {
		customerCredits.put(emailAddress, getCustomerCredit(emailAddress) + creditAmount);
	}
}
