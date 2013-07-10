package br.com.dlbca.dynamicforms.infrastructure.validation;

import javax.validation.ConstraintViolation;
import javax.validation.Path;
import javax.validation.metadata.ConstraintDescriptor;

import org.hibernate.validator.internal.engine.path.PathImpl;
import org.springframework.validation.ObjectError;


/**
 * 
 * Basic Wrapper for transforming ObjectErrors in
 * ConstraintViolations.
 * 
 * This class permits that the validation handlers
 * use the same structure for notifications
 * 
 * @author Mateus
 *
 */
@SuppressWarnings("rawtypes")
public class ObjectErrorWrapper implements ConstraintViolation{

	private ObjectError error;
	
	public ObjectErrorWrapper (ObjectError error){
		this.error = error;
	}
	
	@Override
	public String getMessage() {
		return error.getDefaultMessage();
	}

	@Override
	public String getMessageTemplate() {
		return error.getDefaultMessage();
	}

	@Override
	public Object getRootBean() {
		return error.getObjectName();
	}

	@Override
	public Class getRootBeanClass() {
		return null;
	}

	@Override
	public Object getLeafBean() {
		return null;
	}

	@Override
	public Object[] getExecutableParameters() {
		return null;
	}

	@Override
	public Object getExecutableReturnValue() {
		return null;
	}

	@Override
	public Path getPropertyPath() {
		String code = error.getCodes()[0];
		code = code.substring(code.indexOf('.')+1);
		return PathImpl.createPathFromString(code);
	}

	@Override
	public Object getInvalidValue() {
		return null;
	}

	@Override
	public ConstraintDescriptor getConstraintDescriptor() {
		return null;
	}

	@Override
	public Object unwrap(Class type) {
		return null;
	}

}
