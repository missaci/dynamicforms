package br.com.dlbca.dynamicforms.core.validation.constraints;

import org.springframework.stereotype.Component;

import br.com.dlbca.dynamicforms.core.Field;

@Component
public class MaxLengthConstraint implements IDataConstraint{
	
	private Field field;
	
	protected MaxLengthConstraint(){}
	
	public MaxLengthConstraint(Field field){
		this.field = field;
	}
	
	@Override
	public boolean isValidFor(Object value) {
		if(field == null || field.getMaxLength() == null || field.getMaxLength() <= 0) return true;
		String stringValue = value == null ? "" : value.toString();
		
		return stringValue.length() <= field.getMaxLength();
	}

	@Override
	public String constraintMessage() {
		return "Campo " + field.getLabel() + " deve ter tamanho máximo de " + field.getMaxLength() + ".";
	}

}
