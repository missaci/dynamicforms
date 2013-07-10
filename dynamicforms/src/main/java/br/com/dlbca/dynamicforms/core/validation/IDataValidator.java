package br.com.dlbca.dynamicforms.core.validation;

import java.util.Map;

public interface IDataValidator {

	 void validateContentOf(Map<String, Object> data);
	
}