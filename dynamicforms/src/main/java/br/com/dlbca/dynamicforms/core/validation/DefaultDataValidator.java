package br.com.dlbca.dynamicforms.core.validation;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;

import br.com.dlbca.dynamicforms.core.Field;
import br.com.dlbca.dynamicforms.core.Form;
import br.com.dlbca.dynamicforms.core.validation.constraints.IDataConstraint;

/**
 * 
 * Class created to validate data insertions in forms.
 * 
 * @author Mateus
 *
 */
public class DefaultDataValidator implements IDataValidator {
	
	private List<Class<IDataConstraint>> constraints;
	private Form form;
	private Map<String, Field> fields;
	private List<FieldValidator> validators;
	private Set<ConstraintViolation<?>> violations;
	
	protected DefaultDataValidator(Form form, List<Class<IDataConstraint>> constraints){
		this.form = form;
		this.constraints = constraints;
		this.fields = new HashMap<String, Field>();
		this.validators = new ArrayList<FieldValidator>();
		this.violations = new HashSet<ConstraintViolation<?>>();
		
		determineFields();
	}
	
	public void validateContentOf(Map<String, Object> data){
		prepareDataValidation(data);
		validate();
	}
	
	private void validate(){
		for (FieldValidator validator : validators) {
			try {
				if(!validator.isValid()){
					violations.addAll(validator.getViolations());
				}
			} catch (Exception e) {
				throw new RuntimeException("Error on validating data form content", e);
			} 
		}
		
		if(!violations.isEmpty()){
			throw new ConstraintViolationException(violations);
		}
	}
	
	private void prepareDataValidation(Map<String, Object> data){
		Set<String> labels = data.keySet();
		
		for (String label : labels) {
			Field field = null;
			
			if(fields.containsKey(label)){
				field = fields.get(label);
				fields.remove(label);
			}
			
			validators.add(new FieldValidator(field, data.get(label), constraints));
		}
		
		addNotInformedFieldsConstraints();
	}
	
	private void addNotInformedFieldsConstraints(){
		Set<String> notUsedFields = fields.keySet();
		
		for (String field : notUsedFields) {
			validators.add(new FieldValidator(fields.get(field), "", constraints));
		}
	}
	
	private void determineFields(){
		List<Field> formFields = form.getFields();
		
		for (Field field : formFields) {
			fields.put(field.getLabel(), field);
		}
		
	}
}
