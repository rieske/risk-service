package lt.vv.risk.api;

public class RiskDecision {

	public final boolean accepted;
	public final String reason;
	
	public static RiskDecision accept() {
		return new RiskDecision(true, "ok");
	}
	
	public static RiskDecision rejectAmount() {
		return new RiskDecision(false, "amount");
	}
	
	public static RiskDecision rejectDebt() {
		return new RiskDecision(false, "debt");
	}

	private RiskDecision(boolean accepted, String reason) {
		this.accepted = accepted;
		this.reason = reason;
	}

}
