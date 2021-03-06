package lt.vv.risk.api;

import org.junit.experimental.theories.DataPoints;
import org.junit.experimental.theories.FromDataPoints;
import org.junit.experimental.theories.Theories;
import org.junit.experimental.theories.Theory;
import org.junit.runner.RunWith;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;


@RunWith(Theories.class)
public class EvaluateRiskRequestValidationTest {

	private static final Validator VALIDATOR = Validation.buildDefaultValidatorFactory().getValidator();

	@DataPoints("invalidEmails")
	public static List<String> invalidEmails = Arrays
			.asList("", " ", "\t", "\n", "asdfasdf", "@adsf", "asd@", " test@test.com ", "test@test.com\n", null);
	@DataPoints("invalidFirstNames")
	public static List<String> invalidFirstNames = Arrays.asList("", " ", "\t", "\n", null);
	@DataPoints("invalidLastNames")
	public static List<String> invalidLastNames = Arrays.asList("", " ", "\t", "\n", null);
	@DataPoints("invalidAmounts")
	public static List<Integer> invalidAmounts = Arrays.asList(Integer.MIN_VALUE, -1, -1000);

	@DataPoints("validEmails")
	public static List<String> validEmails = Arrays.asList("a@b.c", "a@b", "test@test.com", "test@test.com", "email@example.com");
	@DataPoints("validFirstNames")
	public static List<String> validFirstNames = Arrays.asList("firstName", "a", " a ", "asdf asdf", "aaa1234", "45 d", "$%^#$");
	@DataPoints("validLastNames")
	public static List<String> validLastNames = Arrays.asList("lastName", "a", " a ", "asdf asdf", "aaa1234", "45 d", "$%^#$");
	@DataPoints("validAmounts")
	public static List<Integer> validAmounts = Arrays.asList(0, 1, 5, 5000, 100000, Integer.MAX_VALUE);

	@Theory
	public void acceptsValidRiskRequest(
			@FromDataPoints("validEmails") String email,
			@FromDataPoints("validFirstNames") String firstName,
			@FromDataPoints("validLastNames") String lastName,
			@FromDataPoints("validAmounts") Integer amount)
	{
		Set<ConstraintViolation<EvaluateRiskRequest>> violations =
				VALIDATOR.validate(new EvaluateRiskRequest(email, firstName, lastName, amount));

		assertThat(violations).isEmpty();
	}

	@Theory
	public void rejectsInvalidEmails(
			@FromDataPoints("invalidEmails") String email,
			@FromDataPoints("validFirstNames") String firstName,
			@FromDataPoints("validLastNames") String lastName,
			@FromDataPoints("validAmounts") Integer amount)
	{
		validateConstraint("email", new EvaluateRiskRequest(email, firstName, lastName, amount));
	}

	@Theory
	public void rejectsInvalidFirstNames(
			@FromDataPoints("validEmails") String email,
			@FromDataPoints("invalidFirstNames") String firstName,
			@FromDataPoints("validLastNames") String lastName,
			@FromDataPoints("validAmounts") Integer amount)
	{
		validateConstraint("firstName", new EvaluateRiskRequest(email, firstName, lastName, amount));
	}

	@Theory
	public void rejectsInvalidLastNames(
			@FromDataPoints("validEmails") String email,
			@FromDataPoints("validFirstNames") String firstName,
			@FromDataPoints("invalidLastNames") String lastName,
			@FromDataPoints("validAmounts") Integer amount)
	{
		validateConstraint("lastName", new EvaluateRiskRequest(email, firstName, lastName, amount));
	}

	@Theory
	public void rejectsInvalidAmounts(
			@FromDataPoints("validEmails") String email,
			@FromDataPoints("validFirstNames") String firstName,
			@FromDataPoints("validLastNames") String lastName,
			@FromDataPoints("invalidAmounts") Integer amount)
	{
		validateConstraint("amount", new EvaluateRiskRequest(email, firstName, lastName, amount));
	}

	private void validateConstraint(String constraint, EvaluateRiskRequest request) {
		Set<ConstraintViolation<EvaluateRiskRequest>> violations = VALIDATOR.validate(request);

		assertThat(violations).hasSize(1);
		ConstraintViolation<EvaluateRiskRequest> emailViolation = violations.iterator().next();
		assertThat(emailViolation.getPropertyPath().toString()).isEqualTo(constraint);
	}
}
