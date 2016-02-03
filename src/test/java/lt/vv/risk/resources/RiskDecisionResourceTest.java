package lt.vv.risk.resources;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import lt.vv.risk.api.RiskDecision;
import lt.vv.risk.resources.RiskDecisionResource;
import lt.vv.risk.services.RiskManager;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

@RunWith(MockitoJUnitRunner.class)
public class RiskDecisionResourceTest {

	private static final String RISK_DECISION_REQUEST_JSON = 
			"{\"email\":\"a@b.se\",\"first_name\":\"a\",\"last_name\":\"b\",\"amount\":100}";

	@Mock
	private RiskManager riskManager;

	@InjectMocks
	private RiskDecisionResource riskDecisionResource;

	private MockMvc mvc;

	@Before
	public void setUpMockMvc() {
		// @formatter:off
		mvc = MockMvcBuilders
					.standaloneSetup(riskDecisionResource)
					.build();
		// @formatter:on
	}

	@Test
	public void delegatesRiskDecisionMakingToRiskManager() throws Exception {
		when(riskManager.evaluateRisk("a@b.se", 100)).thenReturn(RiskDecision.accept());

		// @formatter:off
		mvc.perform(
				post("/decision").contentType(MediaType.APPLICATION_JSON).content(RISK_DECISION_REQUEST_JSON))
				.andExpect(status().isOk())
				.andExpect(content().json("{\"accepted\":true,\"reason\":\"ok\"}"));
		// @formatter:on
	}

	@Test
	public void requiresContentTypeHeader() throws Exception {
		// @formatter:off
		mvc.perform(
				post("/decision").content(RISK_DECISION_REQUEST_JSON))
				.andExpect(status().isUnsupportedMediaType());
		// @formatter:on
	}

	@Test
	public void rejectsRequestsWithNoContent() throws Exception {
		// @formatter:off
		mvc.perform(
				post("/decision").contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isBadRequest());
		// @formatter:on
	}

	@Test
	public void rejectsRequestsWithEmptyContent() throws Exception {
		// @formatter:off
		mvc.perform(
				post("/decision").contentType(MediaType.APPLICATION_JSON).content("{}"))
				.andExpect(status().isBadRequest());
		// @formatter:on
	}

	@Test
	public void rejectsRequestsWithMalformedJsonContent() throws Exception {
		// @formatter:off
		mvc.perform(
				post("/decision").contentType(MediaType.APPLICATION_JSON).content("{asdf\\}"))
				.andExpect(status().isBadRequest());
		// @formatter:on
	}

}
