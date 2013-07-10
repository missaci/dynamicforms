package br.com.dlbca.dynamicforms.core.validation.constraints;

import org.springframework.stereotype.Component;

import br.com.dlbca.dynamicforms.core.Field;

@Component
public class RequiredConstraint implements IDataConstraint{
	
	private Field field;
	
	protected RequiredConstraint(){}
	
	public RequiredConstraint(Field field){
		this.field = field;
	}
	
	@Override
	public boolean isValidFor(Object value) {
		if(field == null || !field.isRequired()) return true;
		
		String stringValue = value == null ? "" : value.toString();
		
		return !stringValue.trim().isEmpty();
	}

	@Override
	public String constraintMessage() {
		return "Campo " + field.getLabel() + " é requerido.";
	}

}
