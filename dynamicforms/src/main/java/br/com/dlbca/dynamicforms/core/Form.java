package br.com.dlbca.dynamicforms.core;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.validation.Valid;

import org.codehaus.jackson.annotate.JsonCreator;
import org.codehaus.jackson.annotate.JsonProperty;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * Form is the base Entity of this project.
 * Everything is related to this is some way.
 * 
 * @author Mateus
 *
 */
@Document
public class Form {
	
	@Id
	private String id;
	
	@NotBlank(message="Título de um formulário não pode estar em branco")
	private String title;
	
	@NotEmpty(message="Formulário deve possuir no mínimo um campo")
	@Valid
	private List<Field> fields;
	
	private List<Map<String, Object>> data;
	
	protected Form(){};

	@JsonCreator
	public Form(@JsonProperty("title") String title, @JsonProperty("fields") List<Field> fields) {
		this.id = UUID.randomUUID().toString();
		this.title = title;
		this.fields = fields;
	}
	
	public String getId() {
		return id;
	}
	
	public String getTitle() {
		return title;
	}
	
	public List<Field> getFields() {
		return fields;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public void setFields(List<Field> fields) {
		this.fields = fields;
	}

	public List<Map<String, Object>> getData() {
		return data;
	}
	
	protected void setId(String id){
		this.id = id;
	}

	protected void addData(Map<String, Object> data){
		if(this.data == null){
			this.data = new LinkedList<Map<String,Object>>();
		}
		
		this.data.add(data);
	}
	
}
