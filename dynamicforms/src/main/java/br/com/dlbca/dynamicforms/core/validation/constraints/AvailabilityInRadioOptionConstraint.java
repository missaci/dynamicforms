package br.com.dlbca.dynamicforms.core.validation.constraints;

import org.springframework.stereotype.Component;

import br.com.dlbca.dynamicforms.core.Field;
import br.com.dlbca.dynamicforms.core.FieldType;
import br.com.dlbca.dynamicforms.core.RadioOption;

@Component
public class AvailabilityInRadioOptionConstraint implements IDataConstraint{
	
	private Field field;
	
	protected AvailabilityInRadioOptionConstraint(){}
	
	public AvailabilityInRadioOptionConstraint(Field field){
		this.field = field;
	}
	
	@Override
	public boolean isValidFor(Object value) {
		if(field == null || !field.getType().equals(FieldType.RADIO)) return true;
		
		boolean found = true;
		
		for (RadioOption option : field.getRadios()) {
			found = option.getValue().equals(value);
			
			if(found) break;
			
		}
		
		return found;
		
	}

	@Override
	public String constraintMessage() {
		return "Campo " + field.getLabel() + " recebeu um valor não disponível nas opções do campo.";
	}

}
