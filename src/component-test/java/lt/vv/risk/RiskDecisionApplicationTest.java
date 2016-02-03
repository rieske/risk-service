package lt.vv.risk;

import static com.jayway.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.Matchers.is;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import com.jayway.restassured.http.ContentType;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@SpringApplicationConfiguration(classes = RiskApplication.class)
@IntegrationTest("server.port:0")
public class RiskDecisionApplicationTest {

	@Value("${local.server.port}")
	private int port;

	private String riskDecisionServicePath() {
		return "http://localhost:" + port + "/decision";
	}

	@Test
	public void acceptsAmountWithinLimits() {
		// @formatter:off
		given()
			.body("{\"email\":\"withinLimits@email.com\",\"first_name\":\"a\",\"last_name\":\"b\",\"amount\":500}")
			.contentType(ContentType.JSON)
		.when()
			.post(riskDecisionServicePath())
		.then()
			.statusCode(equalTo(HttpStatus.OK.value()))
			.body("accepted", is(true))
			.body("reason", equalTo("ok"));
		// @formatter:on
	}

	@Test
	public void rejectsAmountHigherThanCreditLimit() {
		// @formatter:off
		given()
			.body("{\"email\":\"aboveLimit@email.com\",\"first_name\":\"a\",\"last_name\":\"b\",\"amount\":1001}")
			.contentType(ContentType.JSON)
		.when()
			.post(riskDecisionServicePath())
		.then()
			.statusCode(equalTo(HttpStatus.OK.value()))
			.body("accepted", is(false))
			.body("reason", equalTo("amount"));
		// @formatter:on
	}

	@Test
	public void acceptsAmountEqualToCreditLimit() {
		// @formatter:off
		given()
			.body("{\"email\":\"equalToLimit@email.com\",\"first_name\":\"a\",\"last_name\":\"b\",\"amount\":1000}")
			.contentType(ContentType.JSON)
		.when()
			.post(riskDecisionServicePath())
		.then()
			.statusCode(equalTo(HttpStatus.OK.value()))
			.body("accepted", is(true))
			.body("reason", equalTo("ok"));
		// @formatter:on
	}

	@Test
	public void rejectsAmountsForCustomersWithDebt() {
		// @formatter:off
		given()
			.body("{\"email\":\"accumulateAndExceed@email.com\",\"first_name\":\"a\",\"last_name\":\"b\",\"amount\":1000}")
			.contentType(ContentType.JSON)
		.when()
			.post(riskDecisionServicePath())
		.then()
			.statusCode(equalTo(HttpStatus.OK.value()))
			.body("accepted", is(true))
			.body("reason", equalTo("ok"));
		// @formatter:on

		// @formatter:off
		given()
			.body("{\"email\":\"accumulateAndExceed@email.com\",\"first_name\":\"a\",\"last_name\":\"b\",\"amount\":10}")
			.contentType(ContentType.JSON)
		.when()
			.post(riskDecisionServicePath())
		.then()
			.statusCode(equalTo(HttpStatus.OK.value()))
			.body("accepted", is(false))
			.body("reason", equalTo("debt"));
		// @formatter:on
	}
	
	@Test
	public void allowsLowRiskAmountsForCustomersWithDebt() {
		// @formatter:off
		given()
			.body("{\"email\":\"accumulateAndLowRisk@email.com\",\"first_name\":\"a\",\"last_name\":\"b\",\"amount\":1000}")
			.contentType(ContentType.JSON)
		.when()
			.post(riskDecisionServicePath())
		.then()
			.statusCode(equalTo(HttpStatus.OK.value()))
			.body("accepted", is(true))
			.body("reason", equalTo("ok"));
		// @formatter:on

		// @formatter:off
		given()
			.body("{\"email\":\"accumulateAndLowRisk@email.com\",\"first_name\":\"a\",\"last_name\":\"b\",\"amount\":9}")
			.contentType(ContentType.JSON)
		.when()
			.post(riskDecisionServicePath())
		.then()
			.statusCode(equalTo(HttpStatus.OK.value()))
			.body("accepted", is(true))
			.body("reason", equalTo("ok"));
		// @formatter:on
	}

}
