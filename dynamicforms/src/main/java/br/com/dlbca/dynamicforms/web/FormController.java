package br.com.dlbca.dynamicforms.web;

import java.util.List;
import java.util.Map;

import javax.validation.ConstraintViolationException;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import br.com.dlbca.dynamicforms.core.Form;
import br.com.dlbca.dynamicforms.core.FormManager;
import br.com.dlbca.dynamicforms.infrastructure.validation.ValidatorParser;
import br.com.dlbca.dynamicforms.infrastructure.validation.ValidatorResult;


/**
 * 
 * All external requests are made trough this controller
 * 
 * @author Mateus
 *
 */

@Controller
@RequestMapping(value="/templates")
public class FormController {
	
	@Autowired private ValidatorParser validatorParser;
	@Autowired private FormManager formManager;
	
	@RequestMapping(method=RequestMethod.GET, value="")
	public @ResponseBody List<Form> list(){
		return formManager.list();
	}
	
	@RequestMapping(method=RequestMethod.GET, value="/{id}")
	public @ResponseBody Form getFormBy(@PathVariable("id") String id){
		return formManager.find(id);
	}
	
	@RequestMapping(method=RequestMethod.POST, value="")
	public @ResponseBody Form createForm(@RequestBody @Valid Form form, BindingResult bindingResult){
		checkValidationResult(bindingResult);
		formManager.persist(form);
		return form;
	}
	
	@RequestMapping(method=RequestMethod.PUT, value="/{id}")
	public @ResponseBody Form updateForm(@PathVariable("id") String id, @RequestBody @Valid Form form, BindingResult bindingResult){
		checkValidationResult(bindingResult);
		return formManager.onFormWithId(id).mergeContentWith(form);
	}
	
	@RequestMapping(method=RequestMethod.DELETE, value="/{id}")
	public @ResponseBody void deleteFormBy(@PathVariable("id") String id){
		formManager.delete(id);
	}
	
	@RequestMapping(method=RequestMethod.GET, value="/{id}/data")
	public @ResponseBody List<Map<String,Object>> getFormDataBy(@PathVariable("id") String idForm){
		return formManager.find(idForm).getData();
	}
	
	@RequestMapping(method=RequestMethod.POST, value="/{id}/data")
	public @ResponseBody void addDataToForm(@PathVariable("id") String idForm, @RequestBody Map<String,Object> data){
		formManager.onFormWithId(idForm).addData(data);
		
	}
	
	//FIXME: Just Validation Exceptions are being Handled
	@ExceptionHandler(ConstraintViolationException.class)
	@ResponseStatus(value = HttpStatus.BAD_REQUEST)
	public @ResponseBody ValidatorResult handleException(ConstraintViolationException validationException) {
		return validatorParser.generateResultFrom(validationException);
	}
	
	/**
	 * This method is able to transform BindingResults in ConstraintViolationException.
	 * This is being made to keep an unique form for handling validation messages   
	 * 
	 * @param bindingResult
	 */
	private void checkValidationResult(BindingResult bindingResult){
		if(bindingResult.hasErrors()){
			validatorParser.generateExceptionFrom(bindingResult);
		}
	}
}
