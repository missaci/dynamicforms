package br.com.dlbca.dynamicforms.core;

import java.util.List;

import javax.validation.constraints.NotNull;

import org.codehaus.jackson.annotate.JsonCreator;
import org.codehaus.jackson.annotate.JsonProperty;
import org.codehaus.jackson.map.annotate.JsonDeserialize;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.ScriptAssert;

import br.com.dlbca.dynamicforms.infrastructure.json.FieldTypeDeserializer;

//Checks if type = RADIO and radios are not empty
//FIXME: Validation logic should be in javascript
@ScriptAssert(lang = "javascript", 
			  script = "_this.validateRadios()",
			  message = "Definições de campo inválidas. Cheque o tipo e os valores do atributo radios")
public class Field {
	
	@NotBlank(message="Label de um campo não pode ser vazio")
	private String label;
	
	@NotNull(message="Tipo de um campo não pode ser vazio")
	private FieldType type;
	private boolean required;
	private boolean readOnly;
	private String value;
	private Integer maxLength;
	private String placeholder;
	private List<RadioOption> radios;
	
	@JsonCreator
	public Field(@JsonProperty("label") String label, @JsonDeserialize(using=FieldTypeDeserializer.class) @JsonProperty("type")FieldType type){
		this.label = label;
		this.type = type;
	}


	public boolean isRequired() {
		return required;
	}


	public void setRequired(boolean required) {
		this.required = required;
	}


	public boolean isReadOnly() {
		return readOnly;
	}


	public void setReadOnly(boolean readOnly) {
		this.readOnly = readOnly;
	}


	public String getValue() {
		return value;
	}


	public void setValue(String value) {
		this.value = value;
	}


	public Integer getMaxLength() {
		return maxLength;
	}


	public void setMaxLength(Integer maxLength) {
		this.maxLength = maxLength;
	}


	public String getPlaceholder() {
		return placeholder;
	}


	public void setPlaceholder(String placeholder) {
		this.placeholder = placeholder;
	}


	public List<RadioOption> getRadios() {
		return radios;
	}


	public void setRadios(List<RadioOption> radios) {
		this.radios = radios;
	}


	public String getLabel() {
		return label;
	}


	public FieldType getType() {
		return type;
	}
	
	public boolean validateRadios(){
		return this.type == null ? false : (!this.type.equals(FieldType.RADIO))	|| (this.type.equals(FieldType.RADIO) && (this.radios != null && !this.radios.isEmpty()));
	}

}
