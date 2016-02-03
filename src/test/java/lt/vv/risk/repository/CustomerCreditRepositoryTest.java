package lt.vv.risk.repository;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;

import org.junit.Test;

public class CustomerCreditRepositoryTest {

	@Test
	public void returnsZeroCreditForNewCustomers() {
		CustomerCreditRepository repository = new CustomerCreditRepository();

		assertThat(repository.getCustomerCredit("new1"), equalTo(0));
		assertThat(repository.getCustomerCredit("new1"), equalTo(0));
		assertThat(repository.getCustomerCredit("new2"), equalTo(0));
	}

	@Test
	public void creditsACustomer() {
		CustomerCreditRepository repository = new CustomerCreditRepository();

		repository.creditCustomerWithAmount("customer", 100);
		
		assertThat(repository.getCustomerCredit("customer"), equalTo(100));
	}
	
	@Test
	public void accumulatesCreditForACustomer() {
		CustomerCreditRepository repository = new CustomerCreditRepository();

		repository.creditCustomerWithAmount("customer", 100);
		repository.creditCustomerWithAmount("customer", 110);

		assertThat(repository.getCustomerCredit("customer"), equalTo(210));
	}
}
