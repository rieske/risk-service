package lt.vv.risk.api.model;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;

import java.io.IOException;

import org.junit.Test;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

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

		assertThat(deserializedRequest.getEmail(), equalTo("email@example.com"));
		assertThat(deserializedRequest.getFirstName(), equalTo("firstName"));
		assertThat(deserializedRequest.getLastName(), equalTo("lastName"));
		assertThat(deserializedRequest.getAmount(), equalTo(100));
	}

}
