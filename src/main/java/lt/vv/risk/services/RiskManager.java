package lt.vv.risk.services;

import lt.vv.risk.api.model.RiskDecision;
import lt.vv.risk.repository.CustomerCreditRepository;

public class RiskManager {

	private final CustomerCreditRepository customerCreditRepository;

	private final int lowRiskAmount;
	private final int creditLimit;

	public RiskManager(CustomerCreditRepository customerCreditRepository, int amountLowerBound, int amountUpperBound) {
		this.customerCreditRepository = customerCreditRepository;
		this.lowRiskAmount = amountLowerBound;
		this.creditLimit = amountUpperBound;
	}

	public RiskDecision evaluateRisk(String emailAddress, Integer amount) {
		if (amount >= lowRiskAmount) {
			if (amount > creditLimit) {
				return RiskDecision.rejectAmount();
			}
			if ((customerCreditRepository.getCustomerCredit(emailAddress) + amount) > creditLimit) {
				return RiskDecision.rejectDebt();
			}
		}
		customerCreditRepository.creditCustomerWithAmount(emailAddress, amount);
		return RiskDecision.accept();
	}

}
