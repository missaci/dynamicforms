package br.com.dlbca.dynamicforms.infrastructure.validation;

/**
 * 
 * Simple valueObject for constraint failures
 * presentation
 * 
 * @author Mateus
 *
 */
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
