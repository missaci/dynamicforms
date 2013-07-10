package br.com.dlbca.dynamicforms.core;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.runners.MockitoJUnitRunner;
import org.mockito.stubbing.Answer;

import br.com.dlbca.dynamicforms.core.Field;
import br.com.dlbca.dynamicforms.core.FieldType;
import br.com.dlbca.dynamicforms.core.Form;
import br.com.dlbca.dynamicforms.core.FormManager;
import br.com.dlbca.dynamicforms.core.IFormRepository;
import br.com.dlbca.dynamicforms.core.validation.IDataValidator;
import br.com.dlbca.dynamicforms.core.validation.IDataValidatorFactory;

@RunWith(MockitoJUnitRunner.class)
public class FormManagerTest extends Mockito{
	
	private final static String FORM_LABEL = "TesteForm";
	
	@Mock private IFormRepository repository;
	@Mock private IDataValidatorFactory validatorFactory;
	@Mock private IDataValidator validator;
	
	private boolean persisted;
	private FormManager manager;
	
	@Before
	public void init(){
		persisted = false;
		manager = new FormManager(repository, validatorFactory);
		preparePersistMethod();
		prepareFindMethod();
		prepareUpdateMethod();
		prepareCreateValidatorMethod();
	}

	@Test
	public void mustCreateAFormTemplate(){
		Assert.assertFalse(persisted);
		manager.persist(createForm(FORM_LABEL));
		Assert.assertTrue(persisted);
	}
	
	@Test
	public void mustFindAFormByThePassedFormId(){
		Form result = manager.find("fb1b8192-ec97-44f0-b677-063f1404e60b");
		Assert.assertEquals(result.getTitle(), FORM_LABEL);
	}
	
	@Test
	public void mustAddDataToAFormAndUpdateIt(){
		Map<String, Object> data = new HashMap<String, Object>();
		
		String id = "fb1b8192-ec97-44f0-b677-063f1404e60b";
		
		Assert.assertFalse(persisted);
		
		manager.onFormWithId(id).addData(data);
		
		Assert.assertTrue(persisted);
	}
	
	@Test
	public void mustMergeAForm(){
		String id = "fb1b8192-ec97-44f0-b677-063f1404e60b";
		Form form = createForm(FORM_LABEL);
		
		Assert.assertFalse(persisted);
		
		Form resulted = manager.onFormWithId(id).mergeContentWith(form);
		
		Assert.assertTrue(persisted);
		Assert.assertEquals(id, resulted.getId());
	}
	
	
	//============= Métodos Auxiliares ==================
	private void preparePersistMethod(){
		doAnswer(new PersistenceAnswer()).when(repository).persist(any(Form.class));
	}
	
	private void prepareUpdateMethod(){
		doAnswer(new PersistenceAnswer()).when(repository).update(any(Form.class));
	}
	
	private void prepareFindMethod(){
		when(repository.find("fb1b8192-ec97-44f0-b677-063f1404e60b")).thenReturn(createForm(FORM_LABEL));
	}
	
	private void prepareCreateValidatorMethod(){
		when(validatorFactory.createDataValidatorFor(any(Form.class))).thenReturn(validator);
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
	
	class PersistenceAnswer implements Answer<Void> {

		@Override
		public Void answer(InvocationOnMock invocation) throws Throwable {
			Form form = (Form)invocation.getArguments()[0];
			
			if(form.getTitle().equals(FORM_LABEL)){
				persisted = true;
			}
			
			return null;
		}
		
	}
}
