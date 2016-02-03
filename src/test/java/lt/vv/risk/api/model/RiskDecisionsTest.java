package lt.vv.risk.api.model;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import org.junit.Test;

public class RiskDecisionsTest {

	@Test
	public void acceptsRisk() {
		assertThat(RiskDecision.accept().getAccepted(), is(true));
		assertThat(RiskDecision.accept().getReason(), equalTo("ok"));
	}

	@Test
	public void rejectsAmount() {
		assertThat(RiskDecision.rejectAmount().getAccepted(), is(false));
		assertThat(RiskDecision.rejectAmount().getReason(), equalTo("amount"));
	}

	@Test
	public void rejectsDeby() {
		assertThat(RiskDecision.rejectDebt().getAccepted(), is(false));
		assertThat(RiskDecision.rejectDebt().getReason(), equalTo("debt"));
	}

}
