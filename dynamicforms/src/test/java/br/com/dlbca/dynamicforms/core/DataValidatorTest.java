package br.com.dlbca.dynamicforms.core;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.validation.ConstraintViolationException;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import br.com.dlbca.dynamicforms.core.validation.DefaultDataValidatorFactory;

@RunWith(MockitoJUnitRunner.class)
public class DataValidatorTest {
	
	private DefaultDataValidatorFactory factory = new DefaultDataValidatorFactory();
	
	@Test
	public void mustFailForFieldAvailability(){
		ConstraintViolationException result = null;
		
		try {
			Form form = createForm("Teste");
			factory.createDataValidatorFor(form).validateContentOf(createData());
		
		} catch (ConstraintViolationException e) {
			result = e;
		}
		
		Assert.assertTrue(result.getConstraintViolations().iterator().next().getMessage().equals("Inserido um campo não reconhecido."));
	}
	
	@Test
	public void mustFailForFieldRequiredEmpty(){
		Form form = createForm("Teste");
		Field nome = new Field("nome", FieldType.TEXT);
		Field sobrenome = new Field("sobrenome", FieldType.TEXT);
		sobrenome.setRequired(true);
		form.getFields().add(nome);
		form.getFields().add(sobrenome);
		
		ConstraintViolationException result = null;
		
		Map<String, Object> data = createData();
		data.put("sobrenome", " ");
		
		try {
			factory.createDataValidatorFor(form).validateContentOf(data);
		
		} catch (ConstraintViolationException e) {
			result = e;
		}
		
		Assert.assertTrue(result.getConstraintViolations().iterator().next().getMessage().contains("é requerido"));
	}
	
	@Test
	public void mustFailForFieldNotInformed(){
		Form form = createForm("Teste");
		Field nome = new Field("nome", FieldType.TEXT);
		Field sobrenome = new Field("sobrenome", FieldType.TEXT);
		sobrenome.setRequired(true);

		form.getFields().add(nome);
		form.getFields().add(sobrenome);
		
		Map<String, Object> data = createData();
		
		ConstraintViolationException result = null;
		
		try {
			factory.createDataValidatorFor(form).validateContentOf(data);
		
		} catch (ConstraintViolationException e) {
			result = e;
		}
		
		Assert.assertTrue(result.getConstraintViolations().iterator().next().getMessage().contains("é requerido"));
	}
	
	@Test
	public void mustFailForReadOnlyFieldModification(){
		Form form = createForm("Teste");
		Field nome = new Field("nome", FieldType.TEXT);
		Field sobrenome = new Field("sobrenome", FieldType.TEXT);
		sobrenome.setReadOnly(true);
		sobrenome.setValue("Afonso");

		form.getFields().add(nome);
		form.getFields().add(sobrenome);
		
		Map<String, Object> data = createData();
		data.put("sobrenome", "Martineli");
		
		ConstraintViolationException result = null;
		
		try {
			factory.createDataValidatorFor(form).validateContentOf(data);
		
		} catch (ConstraintViolationException e) {
			result = e;
		}
		
		Assert.assertTrue(result.getConstraintViolations().iterator().next().getMessage().contains("não pode ser modificado"));
	}
	
	@Test
	public void mustFailForMaxLengthLimit(){
		Form form = createForm("Teste");
		Field nome = new Field("nome", FieldType.TEXT);
		Field sobrenome = new Field("sobrenome", FieldType.TEXT);
		sobrenome.setMaxLength(5);

		form.getFields().add(nome);
		form.getFields().add(sobrenome);
		
		Map<String, Object> data = createData();
		data.put("sobrenome", "Martineli");
		
		ConstraintViolationException result = null;
		
		try {
			factory.createDataValidatorFor(form).validateContentOf(data);
		
		} catch (ConstraintViolationException e) {
			result = e;
		}
		
		Assert.assertTrue(result.getConstraintViolations().iterator().next().getMessage().contains("deve ter tamanho máximo de"));
	}
	
	@Test
	public void mustFailForNotHavingARadioOptionAvailable(){
		Form form = prepareRadioTest();
		Map<String, Object> data = createData();
		data.put("sobrenome", "Willians");
		
		ConstraintViolationException result = null;
		
		try {
			factory.createDataValidatorFor(form).validateContentOf(data);
		
		} catch (ConstraintViolationException e) {
			result = e;
		}
		
		Assert.assertTrue(result.getConstraintViolations().iterator().next().getMessage().contains("recebeu um valor não disponível nas opções do campo"));
	}
	
	@Test
	public void mustAcceptRadioOptionNotThrowingException(){
		Form form = prepareRadioTest();
		Map<String, Object> data = createData();
		data.put("sobrenome", "Afonso");
		
		factory.createDataValidatorFor(form).validateContentOf(data);
		
	}
	
	private Form prepareRadioTest(){
		List<RadioOption> radios = new ArrayList<RadioOption>();
		
		Form form = createForm("Teste");
		
		Field nome = new Field("nome", FieldType.TEXT);
		Field sobrenome = new Field("sobrenome", FieldType.RADIO);

		RadioOption option1 = new RadioOption("Opção 1", "Martineli");
		RadioOption option2 = new RadioOption("Opção 2", "Afonso");
		
		radios.add(option1);
		radios.add(option2);
		
		sobrenome.setRadios(radios);

		form.getFields().add(nome);
		form.getFields().add(sobrenome);
		
		return form;
	}
	
	private Field createSimpleField(String label){
		Field field = new Field(label, FieldType.TEXT);
		field.setMaxLength(200);
		
		return field;
	}
	
	private Form createForm(String label){
		List<Field> fields = new LinkedList<Field>();
		fields.add(createSimpleField("teste"));
		
		return new Form(label, fields);
	}
	
	private Map<String, Object> createData(){
		Map<String, Object> data = new HashMap<String, Object>();
		data.put("teste", "olá");
		data.put("nome", "Mateus");
		
		return data;
	}

}
