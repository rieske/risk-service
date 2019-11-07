package lt.vv.risk.api;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class RiskDecisionSerializationTest {

	private static final ObjectMapper MAPPER = new ObjectMapper();

	@Test
	public void serializesRiskDecisionToJson() throws JsonProcessingException {
		String jsonRiskDecision = MAPPER.writeValueAsString(RiskDecision.accept());

		assertThat(jsonRiskDecision).isEqualTo("{\"accepted\":true,\"reason\":\"ok\"}");
	}

}
