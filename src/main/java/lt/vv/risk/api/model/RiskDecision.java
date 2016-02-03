package lt.vv.risk.api.model;

public class RiskDecision {

	private final Boolean accepted;
	private final String reason;
	
	public static RiskDecision accept() {
		return new RiskDecision(true, "ok");
	}
	
	public static RiskDecision rejectAmount() {
		return new RiskDecision(false, "amount");
	}
	
	public static RiskDecision rejectDebt() {
		return new RiskDecision(false, "debt");
	}

	private RiskDecision(Boolean accepted, String reason) {
		this.accepted = accepted;
		this.reason = reason;
	}

	public Boolean getAccepted() {
		return accepted;
	}

	public String getReason() {
		return reason;
	}

}
