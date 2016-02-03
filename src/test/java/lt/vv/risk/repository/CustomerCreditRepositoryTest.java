package lt.vv.risk.repository;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;

public class CustomerCreditRepositoryTest {

	@Test
	public void returnsZeroCreditForNewCustomers() {
		CustomerCreditRepository repository = new CustomerCreditRepository();

		assertThat(repository.getCustomerCredit("new1")).isZero();
		assertThat(repository.getCustomerCredit("new1")).isZero();
		assertThat(repository.getCustomerCredit("new2")).isZero();
	}

	@Test
	public void creditsACustomer() {
		CustomerCreditRepository repository = new CustomerCreditRepository();

		repository.creditCustomerWithAmount("customer", 100);
		
		assertThat(repository.getCustomerCredit("customer")).isEqualTo(100);
	}
	
	@Test
	public void accumulatesCreditForACustomer() {
		CustomerCreditRepository repository = new CustomerCreditRepository();

		repository.creditCustomerWithAmount("customer", 100);
		repository.creditCustomerWithAmount("customer", 110);

		assertThat(repository.getCustomerCredit("customer")).isEqualTo(210);
	}
}
