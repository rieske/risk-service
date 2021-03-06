package lt.vv.risk;

import lt.vv.risk.repository.CustomerCreditRepository;
import lt.vv.risk.services.RiskManager;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.Environment;

@SpringBootApplication
public class RiskApplication {

	public static void main(String[] args) {
		SpringApplication.run(RiskApplication.class);
	}

	@Bean
	public RiskManager riskManager(Environment environment) {
		return new RiskManager(
				new CustomerCreditRepository(),
				environment.getRequiredProperty("riskManager.lowRiskAmount", Integer.class),
				environment.getRequiredProperty("riskManager.creditLimit", Integer.class));
	}

}
