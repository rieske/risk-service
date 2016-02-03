package lt.vv.risk.services;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.greaterThanOrEqualTo;
import static org.hamcrest.Matchers.lessThan;
import static org.junit.Assume.assumeThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

import java.util.Set;

import org.junit.Before;
import org.junit.experimental.theories.DataPoints;
import org.junit.experimental.theories.FromDataPoints;
import org.junit.experimental.theories.Theories;
import org.junit.experimental.theories.Theory;
import org.junit.runner.RunWith;

import com.google.common.collect.ContiguousSet;
import com.google.common.collect.DiscreteDomain;
import com.google.common.collect.Range;

import lt.vv.risk.api.model.RiskDecision;
import lt.vv.risk.repository.CustomerCreditRepository;

@RunWith(Theories.class)
public class RiskManagerTest {

	@DataPoints("lowRiskAmounts")
	public static Set<Integer> lowRiskAmounts = ContiguousSet.create(Range.closed(0, 10), DiscreteDomain.integers());

	@DataPoints("creditLimits")
	public static Set<Integer> creditLimits = ContiguousSet.create(Range.closed(0, 10), DiscreteDomain.integers());

	@DataPoints("amounts")
	public static Set<Integer> amounts = ContiguousSet.create(Range.closed(0, 10), DiscreteDomain.integers());

	@DataPoints("currentCreditAmounts")
	public static Set<Integer> currentCreditAmounts = ContiguousSet.create(Range.closed(0, 10), DiscreteDomain.integers());

	private static final String CUSTOMER = "customerEmail";

	private CustomerCreditRepository creditRepository;

	@Before
	public void setUp() {
		creditRepository = mock(CustomerCreditRepository.class);
	}

	@Theory
	public void amountsLowerThanLowRiskAmountAreAccepted(
			@FromDataPoints("lowRiskAmounts") Integer lowRiskAmount,
			@FromDataPoints("creditLimits") Integer creditLimit,
			@FromDataPoints("amounts") Integer amount,
			@FromDataPoints("currentCreditAmounts") Integer currentCreditAmount)
	{
		assumeThat(lowRiskAmount, lessThan(creditLimit));
		assumeThat(amount, lessThan(lowRiskAmount));
		when(creditRepository.getCustomerCredit(CUSTOMER)).thenReturn(currentCreditAmount);

		RiskManager riskManager = new RiskManager(creditRepository, lowRiskAmount, creditLimit);

		RiskDecision decision = riskManager.evaluateRisk(CUSTOMER, amount);

		assertThat(decision.getAccepted()).isTrue();
		assertThat(decision.getReason()).isEqualTo("ok");
		verify(creditRepository).creditCustomerWithAmount(CUSTOMER, amount);
	}

	@Theory
	public void amountsLowerThanCreditLimitAreAccepted(
			@FromDataPoints("lowRiskAmounts") Integer lowRiskAmount,
			@FromDataPoints("creditLimits") Integer creditLimit,
			@FromDataPoints("amounts") Integer amount,
			@FromDataPoints("currentCreditAmounts") Integer currentCreditAmount)
	{
		assumeThat(lowRiskAmount, lessThan(creditLimit));
		assumeThat(amount, lessThan(creditLimit));
		assumeThat(currentCreditAmount, lessThan(creditLimit - amount));
		when(creditRepository.getCustomerCredit(CUSTOMER)).thenReturn(currentCreditAmount);

		RiskManager riskManager = new RiskManager(creditRepository, lowRiskAmount, creditLimit);

		RiskDecision decision = riskManager.evaluateRisk(CUSTOMER, amount);

		assertThat(decision.getAccepted()).isTrue();
		assertThat(decision.getReason()).isEqualTo("ok");
		verify(creditRepository).creditCustomerWithAmount(CUSTOMER, amount);
	}

	@Theory
	public void amountsEqualToCreditLimitAreAccepted(
			@FromDataPoints("lowRiskAmounts") Integer lowRiskAmount,
			@FromDataPoints("creditLimits") Integer creditLimit,
			@FromDataPoints("amounts") Integer amount,
			@FromDataPoints("currentCreditAmounts") Integer currentCreditAmount)
	{
		assumeThat(lowRiskAmount, lessThan(creditLimit));
		assumeThat(amount, equalTo(creditLimit));
		assumeThat(currentCreditAmount, equalTo(0));
		when(creditRepository.getCustomerCredit(CUSTOMER)).thenReturn(currentCreditAmount);

		RiskManager riskManager = new RiskManager(creditRepository, lowRiskAmount, creditLimit);

		RiskDecision decision = riskManager.evaluateRisk(CUSTOMER, amount);

		assertThat(decision.getAccepted()).isTrue();
		assertThat(decision.getReason()).isEqualTo("ok");
		verify(creditRepository).creditCustomerWithAmount(CUSTOMER, amount);
	}

	@Theory
	public void amountsHigherThanCreditLimitAreRejected(
			@FromDataPoints("lowRiskAmounts") Integer lowRiskAmount,
			@FromDataPoints("creditLimits") Integer creditLimit,
			@FromDataPoints("amounts") Integer amount,
			@FromDataPoints("currentCreditAmounts") Integer currentCreditAmount)
	{
		assumeThat(lowRiskAmount, lessThan(creditLimit));
		assumeThat(amount, greaterThan(creditLimit));
		assumeThat(currentCreditAmount, equalTo(0));
		when(creditRepository.getCustomerCredit(CUSTOMER)).thenReturn(currentCreditAmount);

		RiskManager riskManager = new RiskManager(creditRepository, lowRiskAmount, creditLimit);

		RiskDecision decision = riskManager.evaluateRisk(CUSTOMER, amount);

		assertThat(decision.getAccepted()).isFalse();
		assertThat(decision.getReason()).isEqualTo("amount");
		verifyNoMoreInteractions(creditRepository);
	}

	@Theory
	public void amountsExceedingCustomersCreditLimitWithAccumulatedDebtAreRejected(
			@FromDataPoints("lowRiskAmounts") Integer lowRiskAmount,
			@FromDataPoints("creditLimits") Integer creditLimit,
			@FromDataPoints("amounts") Integer amount,
			@FromDataPoints("currentCreditAmounts") Integer currentCreditAmount)
	{
		assumeThat(lowRiskAmount, lessThan(creditLimit));
		assumeThat(amount, lessThan(creditLimit));
		assumeThat(amount, greaterThanOrEqualTo(lowRiskAmount));
		assumeThat(currentCreditAmount, greaterThan(creditLimit));
		when(creditRepository.getCustomerCredit(CUSTOMER)).thenReturn(currentCreditAmount);

		RiskManager riskManager = new RiskManager(creditRepository, lowRiskAmount, creditLimit);

		RiskDecision decision = riskManager.evaluateRisk(CUSTOMER, amount);

		assertThat(decision.getAccepted()).isFalse();
		assertThat(decision.getReason()).isEqualTo("debt");
		verify(creditRepository).getCustomerCredit(CUSTOMER);
		verifyNoMoreInteractions(creditRepository);
	}
}
