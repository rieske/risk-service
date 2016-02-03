package lt.vv.risk.api.model;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;

import org.junit.Test;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class RiskDecisionSerializationTest {

	private static final ObjectMapper MAPPER = new ObjectMapper();

	@Test
	public void serializesRiskDecisionToJson() throws JsonProcessingException {
		String jsonRiskDecision = MAPPER.writeValueAsString(RiskDecision.accept());

		assertThat(jsonRiskDecision, equalTo("{\"accepted\":true,\"reason\":\"ok\"}"));
	}

}
