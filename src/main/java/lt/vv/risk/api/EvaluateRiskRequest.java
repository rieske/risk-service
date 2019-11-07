package lt.vv.risk.api;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.NotEmpty;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.NotNull;

public class EvaluateRiskRequest {

	@Email
	@NotEmpty
	public final String email;

	@NotBlank
	public final String firstName;

	@NotBlank
	public final String lastName;

	@NotNull
	@Range(min = 0)
	public final Integer amount;

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

}
