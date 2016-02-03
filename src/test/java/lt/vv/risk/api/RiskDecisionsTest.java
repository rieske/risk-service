package lt.vv.risk.api;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;

import lt.vv.risk.api.RiskDecision;

public class RiskDecisionsTest {

	@Test
	public void acceptsRisk() {
		assertThat(RiskDecision.accept().getAccepted()).isTrue();
		assertThat(RiskDecision.accept().getReason()).isEqualTo("ok");
	}

	@Test
	public void rejectsAmount() {
		assertThat(RiskDecision.rejectAmount().getAccepted()).isFalse();
		assertThat(RiskDecision.rejectAmount().getReason()).isEqualTo("amount");
	}

	@Test
	public void rejectsDeby() {
		assertThat(RiskDecision.rejectDebt().getAccepted()).isFalse();
		assertThat(RiskDecision.rejectDebt().getReason()).isEqualTo("debt");
	}

}
