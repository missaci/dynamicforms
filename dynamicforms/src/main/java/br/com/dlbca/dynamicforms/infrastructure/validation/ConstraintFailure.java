package br.com.dlbca.dynamicforms.infrastructure.validation;

public class ConstraintFailure {
	
	private String message;
	private String category;
	
	public ConstraintFailure(String message, String category) {
		this.message = message;
		this.category = category;
	}

	public String getMessage() {
		return message;
	}
	public String getCategory() {
		return category;
	}
	
	

}
