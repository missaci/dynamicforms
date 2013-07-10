package br.com.dlbca.dynamicforms.core.validation;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

import javax.validation.ConstraintViolation;

import org.springframework.validation.ObjectError;

import br.com.dlbca.dynamicforms.core.Field;
import br.com.dlbca.dynamicforms.core.validation.constraints.IDataConstraint;
import br.com.dlbca.dynamicforms.infrastructure.validation.ObjectErrorWrapper;

/**
 * 
 * FieldValidator validates each field in
 * a chain of Constraints. This is based on the Chain of Responsibility pattern
 * but with some modifications from the original implementation, since constraints
 * must not control flow (All constraints must be executed).
 * 
 * @author Mateus
 *
 */
public class FieldValidator {
	
	private Field field;
	private Object value;
	private List<ConstraintViolation<?>> violations;
	private List<Class<IDataConstraint>> constraints;
	
	public FieldValidator(Field field, Object value, List<Class<IDataConstraint>> constraints) {
		super();
		this.field = field;
		this.value = value;
		this.constraints = constraints;
	}
	
	public List<ConstraintViolation<?>> getViolations() {
		return violations;
	}
	
	public Field getField() {
		return field;
	}
	
	public Object getValue() {
		return value;
	}
	
	public boolean isValid() throws InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, SecurityException, NoSuchMethodException{
		boolean result = true;
		
		for (Class<IDataConstraint> constraint : constraints) {
			IDataConstraint dataConstraint = (IDataConstraint) constraint.getConstructor(Field.class).newInstance(field);
			
			if(!dataConstraint.isValidFor(value)){
				addConstraintViolation(dataConstraint);
				result = false;
			}
		}
		
		return result;
	}
	
	private void addConstraintViolation(IDataConstraint constraint){
		initializeViolationsCollection();
		
		String value = "null";
		
		if(this.value != null){
			this.value = value.toString();
		}
		
		ConstraintViolation<?> violation = new ObjectErrorWrapper(new ObjectError("br.com.dlbca.dynamicforms.core.Form", new String[]{"form.data"}, new String[]{value}, constraint.constraintMessage()));
		this.violations.add(violation);
	}
	
	private void initializeViolationsCollection(){
		if(violations == null){
			violations = new ArrayList<ConstraintViolation<?>>();
		}
	}

}
