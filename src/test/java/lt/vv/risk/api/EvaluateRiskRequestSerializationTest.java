package lt.vv.risk.api;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.IOException;

import org.junit.Test;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import lt.vv.risk.api.EvaluateRiskRequest;

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
	public void deserializesFromJson() throws JsonParseException, JsonMappingException, IOException {
		EvaluateRiskRequest deserializedRequest = MAPPER.readValue(EVALUATE_RISK_REQUEST_JSON, EvaluateRiskRequest.class);

		assertThat(deserializedRequest.email).isEqualTo("email@example.com");
		assertThat(deserializedRequest.firstName).isEqualTo("firstName");
		assertThat(deserializedRequest.lastName).isEqualTo("lastName");
		assertThat(deserializedRequest.amount).isEqualTo(100);
	}

}
