package br.com.dlbca.dynamicforms.infrastructure.validation;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;

import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;

/**
 * 
 * This class is a Helper for 
 * ConstraintViolationException treatment.
 * 
 * @author Mateus
 *
 */

@Component
public class ValidatorParser {
	
	/**
	 * Build a ValidatorResult from a ConstraintException
	 * This is just a way of keeping the validation messages in a unique way
	 * 
	 * @param validationException
	 * @return ValidatorResult
	 */
	public ValidatorResult generateResultFrom(ConstraintViolationException validationException){
		ValidatorResult result = new ValidatorResult();
		
		Set<ConstraintViolation<?>> violations = validationException.getConstraintViolations();
		
		for (ConstraintViolation<?> constraintViolation : violations) {
			ConstraintFailure failure = new ConstraintFailure(constraintViolation.getMessage(), constraintViolation.getPropertyPath().toString());
			result.addError(failure);
		}
		
		return result;
	}
	
	/**
	 * Throws a new ConstraintException based on the results from the
	 * BindingResult passed
	 * @param result
	 */
	public void generateExceptionFrom(BindingResult result){
		List<ObjectError> errors = result.getAllErrors();
		Set<ConstraintViolation<?>> violations = new HashSet<ConstraintViolation<?>>();
		
		for (ObjectError error : errors) {
			violations.add(new ObjectErrorWrapper(error));
			
		}
		
		throw new ConstraintViolationException(violations);
		
	}

}
