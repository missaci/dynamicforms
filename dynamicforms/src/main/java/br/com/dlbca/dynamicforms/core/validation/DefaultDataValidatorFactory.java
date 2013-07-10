package br.com.dlbca.dynamicforms.core.validation;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.ClassPathScanningCandidateComponentProvider;
import org.springframework.stereotype.Component;

import br.com.dlbca.dynamicforms.core.Form;
import br.com.dlbca.dynamicforms.core.validation.constraints.IDataConstraint;

/**
 * This factory produces the default DataValidator implementation
 * 
 * @author Mateus
 *
 */
@Component
public class DefaultDataValidatorFactory implements IDataValidatorFactory {
	
	private static final String CONSTRAINT_PACKAGE = "br.com.dlbca.dynamicforms.core.validation.constraints"; 
	
	@Override
	public IDataValidator createDataValidatorFor(Form form){
		return new DefaultDataValidator(form, findConstraints());
	}
	
	@SuppressWarnings("unchecked")
	private List<Class<IDataConstraint>> findConstraints(){
		Set<BeanDefinition> result = new ClassPathScanningCandidateComponentProvider(true).findCandidateComponents(CONSTRAINT_PACKAGE);
		List<Class<IDataConstraint>> constraints = new ArrayList<Class<IDataConstraint>>();
		
		for (BeanDefinition beanDefinition : result) {
			try {
				constraints.add((Class<IDataConstraint>)Class.forName(beanDefinition.getBeanClassName()));
			
			} catch (ClassNotFoundException e) {
				throw new RuntimeException(e);
			}
		}
		
		return constraints;
		
	}

}
