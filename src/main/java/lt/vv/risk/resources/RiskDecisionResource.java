package lt.vv.risk.resources;

import javax.validation.Valid;

import lt.vv.risk.api.EvaluateRiskRequest;
import lt.vv.risk.api.RiskDecision;
import lt.vv.risk.services.RiskManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RiskDecisionResource {
	
	@Autowired
	private RiskManager riskManager;

	@RequestMapping(
			value = "decision",
			method = RequestMethod.POST,
			consumes = MediaType.APPLICATION_JSON_VALUE,
			produces = MediaType.APPLICATION_JSON_VALUE)
	public RiskDecision evaluateRisk(@Valid @RequestBody EvaluateRiskRequest evaluateRiskRequest) {
		return riskManager.evaluateRisk(evaluateRiskRequest.email, evaluateRiskRequest.amount);
	}

}
