package br.com.dlbca.dynamicforms.core.validation.constraints;

import org.springframework.stereotype.Component;

import br.com.dlbca.dynamicforms.core.Field;

@Component
public class ReadOnlyConstraint implements IDataConstraint{
	
	private Field field;
	
	protected ReadOnlyConstraint(){}
	
	public ReadOnlyConstraint(Field field){
		this.field = field;
	}
	
	@Override
	public boolean isValidFor(Object value) {
		if(field == null || !field.isReadOnly()) return true;
		
		return value.equals(field.getValue());
	}

	@Override
	public String constraintMessage() {
		return "Campo " + field.getLabel() + " não pode ser modificado.";
	}

}
