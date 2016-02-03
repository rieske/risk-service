package lt.vv.risk.api;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;

import lt.vv.risk.api.RiskDecision;

public class RiskDecisionsTest {

	@Test
	public void acceptsRisk() {
		assertThat(RiskDecision.accept().accepted).isTrue();
		assertThat(RiskDecision.accept().reason).isEqualTo("ok");
	}

	@Test
	public void rejectsAmount() {
		assertThat(RiskDecision.rejectAmount().accepted).isFalse();
		assertThat(RiskDecision.rejectAmount().reason).isEqualTo("amount");
	}

	@Test
	public void rejectsDeby() {
		assertThat(RiskDecision.rejectDebt().accepted).isFalse();
		assertThat(RiskDecision.rejectDebt().reason).isEqualTo("debt");
	}

}
