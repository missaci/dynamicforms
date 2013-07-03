package br.com.dlbca.dynamicforms.infrastructure.repositories.mongodb;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Repository;

import br.com.dlbca.dynamicforms.core.Form;
import br.com.dlbca.dynamicforms.core.IFormRepository;

@Repository
public class MongoDBFormRepository implements IFormRepository {

	@Autowired private MongoTemplate mongoTemplate;
	
	public static final String COLLECTION_NAME = "form";

	@Override
	public void persist(Form form) {
		createCollection();       
		mongoTemplate.insert(form, COLLECTION_NAME);

	}

	@Override
	public List<Form> list() {
		return mongoTemplate.findAll(Form.class, COLLECTION_NAME);
	}

	@Override
	public Form find(String id) {
		return mongoTemplate.findById(id, Form.class);      
	}

	@Override
	public void delete(Form form) {
		mongoTemplate.remove(form, COLLECTION_NAME);
	}

	@Override
	public void update(Form form) {
		mongoTemplate.save(form, COLLECTION_NAME);      
	}
	
	private void createCollection(){
		if (!mongoTemplate.collectionExists(Form.class)) {
			mongoTemplate.createCollection(Form.class);
		}
	}

}
