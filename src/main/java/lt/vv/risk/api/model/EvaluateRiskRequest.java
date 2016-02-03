package lt.vv.risk.api.model;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.NotEmpty;
import org.hibernate.validator.constraints.Range;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class EvaluateRiskRequest {

	@Email
	@NotEmpty
	private final String email;

	@NotBlank
	private final String firstName;

	@NotBlank
	private final String lastName;

	@NotNull
	@Range(min = 0)
	private final Integer amount;

	@JsonCreator
	public EvaluateRiskRequest(
			@JsonProperty("email") String email,
			@JsonProperty("first_name") String firstName,
			@JsonProperty("last_name") String lastName,
			@JsonProperty("amount") Integer amount)
	{
		this.email = email;
		this.firstName = firstName;
		this.lastName = lastName;
		this.amount = amount;
	}

	public String getEmail() {
		return email;
	}

	public String getFirstName() {
		return firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public Integer getAmount() {
		return amount;
	}

}
