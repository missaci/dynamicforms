package br.com.dlbca.dynamicforms.core;

import java.util.List;

/**
 * 
 * This interface must be implemented in any Repository intended for
 * Form persistence
 * 
 * @author Mateus
 *
 */
public interface IFormRepository {

	void persist(Form form);

	void update(Form form);

	void delete(Form form);

	Form find(String id);

	List<Form> list();

}