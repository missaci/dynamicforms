package br.com.dlbca.dynamicforms.core.validation;

import br.com.dlbca.dynamicforms.core.Form;

public interface IDataValidatorFactory {

	public abstract IDataValidator createDataValidatorFor(Form form);

}