package br.com.dlbca.dynamicforms.infrastructure.validation;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/**
 * 
 * Simple ValueObject for
 * validation errors presentation.
 * 
 * @author Mateus
 *
 */
public class ValidatorResult {
	
	private Set<ConstraintFailure> errors = new HashSet<ConstraintFailure>();
	
	public void addError(ConstraintFailure failure){
		this.errors.add(failure);
	}
	
	public Set<ConstraintFailure> getErrors(){
		return Collections.unmodifiableSet(this.errors);
	}
	

}
