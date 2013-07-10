package br.com.dlbca.dynamicforms.core;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.dlbca.dynamicforms.core.validation.IDataValidator;
import br.com.dlbca.dynamicforms.core.validation.IDataValidatorFactory;

@Service
/**
 * Class responsible for managing all form inputs and outputs 
 * 
 * @author Mateus
 *
 */
public class FormManager {

	private IFormRepository repository;
	private IDataValidatorFactory validatorFactory;

	@Autowired
	public FormManager(IFormRepository repository, IDataValidatorFactory validatorFactory){
		this.repository = repository;
		this.validatorFactory = validatorFactory;
	}
	
	public Form persist(Form form) {
		this.repository.persist(form);
		return form;
	}
	
	private Form update(Form form) {
		this.repository.update(form);
		return form;
	}
	
	public Form find(String id) {
		return this.repository.find(id);
	}
	
	public void delete(String id) {
		this.repository.delete(find(id));
	}
	
	public List<Form> list() {
		return this.repository.list();
	}

	public FormDataModifier onFormWithId(String id) {
		return new FormDataModifier(id, this);
	}
	
	
	/**
	 * Inner Class for form data management, used to limit
	 * context and responsibility and to improve readability
	 * on the client code.
	 * 
	 * This class could be externalized in the future
	 * 
	 * @author Mateus
	 *
	 */
	public class FormDataModifier{
		private String formId;
		private FormManager manager;
		
		private FormDataModifier(String id, FormManager manager){
			this.manager = manager;
			this.formId = id;
		}
		
		public void addData(Map<String, Object> data){
			Form form = manager.find(this.formId);
			checkIfDataIsApplicable(form, data);
			
			form.addData(data);
			manager.update(form);
		}
		
		public Form mergeContentWith(Form formToMerge){
			formToMerge.setId(this.formId);
			return manager.update(formToMerge);
		}

		private void checkIfDataIsApplicable(Form form, Map<String, Object> data) {
			IDataValidator validator = validatorFactory.createDataValidatorFor(form);
			validator.validateContentOf(data);
			
		}
		
	}
	
}
	