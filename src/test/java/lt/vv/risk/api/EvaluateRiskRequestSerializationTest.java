package lt.vv.risk.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;

public class EvaluateRiskRequestSerializationTest {

	private static final ObjectMapper MAPPER = new ObjectMapper();

	// @formatter:off
	private static final String EVALUATE_RISK_REQUEST_JSON = "{"
				+ "\"email\":\"email@example.com\","
				+ "\"first_name\":\"firstName\","
				+ "\"last_name\":\"lastName\","
				+ "\"amount\":100"
			+ "}";
	// @formatter:on

	@Test
	public void deserializesFromJson() throws IOException {
		EvaluateRiskRequest deserializedRequest = MAPPER.readValue(EVALUATE_RISK_REQUEST_JSON, EvaluateRiskRequest.class);

		assertThat(deserializedRequest.email).isEqualTo("email@example.com");
		assertThat(deserializedRequest.firstName).isEqualTo("firstName");
		assertThat(deserializedRequest.lastName).isEqualTo("lastName");
		assertThat(deserializedRequest.amount).isEqualTo(100);
	}

}
