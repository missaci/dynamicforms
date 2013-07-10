package br.com.dlbca.dynamicforms.core.validation.constraints;


public interface IDataConstraint {

	public boolean isValidFor(Object value);
	public String constraintMessage();
	
}
