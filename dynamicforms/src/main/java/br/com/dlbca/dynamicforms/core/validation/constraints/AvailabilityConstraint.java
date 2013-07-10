package br.com.dlbca.dynamicforms.core.validation.constraints;

import org.springframework.stereotype.Component;

import br.com.dlbca.dynamicforms.core.Field;

@Component
public class AvailabilityConstraint implements IDataConstraint{
	
	private Field field;
	
	protected AvailabilityConstraint(){}
	
	public AvailabilityConstraint(Field field){
		this.field = field;
	}
	
	@Override
	public boolean isValidFor(Object value) {
		return field != null;
	}

	@Override
	public String constraintMessage() {
		return "Inserido um campo não reconhecido.";
	}

}
