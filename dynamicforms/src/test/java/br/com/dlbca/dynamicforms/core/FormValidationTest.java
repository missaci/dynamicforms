package br.com.dlbca.dynamicforms.core;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import org.junit.Test;

import br.com.dlbca.dynamicforms.core.Field;
import br.com.dlbca.dynamicforms.core.FieldType;
import br.com.dlbca.dynamicforms.core.Form;
import br.com.dlbca.dynamicforms.core.RadioOption;

public class FormValidationTest {
	
	@Test
	public void mustValidateTheForm(){
		List<Field> fields = new LinkedList<Field>();
		fields.add(createSimpleField("teste"));
		
		Form form = new Form("Teste", fields);

		checkIfIsValid(form);
	}
	
	@Test(expected=ConstraintViolationException.class)
	public void mustFailToValidateTheFormDueInvalidRadioField(){
		List<Field> fields = new LinkedList<Field>();
		fields.add(createInvalidRadioField("teste"));
		
		Form form = new Form("Teste", fields);

		this.checkIfIsValid(form);
	}
	
	@Test
	public void mustValidateTheFormDueRadioFieldCorrection(){
		List<Field> fields = new LinkedList<Field>();
		fields.add(createValidRadioField("teste"));
		
		Form form = new Form("Teste", fields);

		this.checkIfIsValid(form);
	}
	
	
	//============= Métodos Auxiliares ==============
	private Field createSimpleField(String label){
		Field field = new Field(label, FieldType.TEXT);
		field.setMaxLength(200);
		
		return field;
	}
	
	private Field createInvalidRadioField(String label){
		Field field = new Field(label, FieldType.RADIO);
		
		return field;
	}
	
	private Field createValidRadioField(String label){
		Field field = new Field(label, FieldType.RADIO);
		List<RadioOption> radios = new ArrayList<RadioOption>();
		radios.add(new RadioOption("teste", "teste"));
		field.setRadios(radios);
		return field;
	}
	
	private void checkIfIsValid(Form form){
		ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();
        
        Set<ConstraintViolation<Form>> constraintViolations = validator.validate(form);
        
        if(!constraintViolations.isEmpty()){
        	throw new ConstraintViolationException(constraintViolations);
        }
	}

}
