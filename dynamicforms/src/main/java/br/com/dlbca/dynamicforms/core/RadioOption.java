package br.com.dlbca.dynamicforms.core;

import org.codehaus.jackson.annotate.JsonCreator;
import org.codehaus.jackson.annotate.JsonProperty;

public class RadioOption {
	
	private String label;
	private String value;
	
	@JsonCreator
	public RadioOption(@JsonProperty("label")String label, @JsonProperty("value")String value){
		this.label = label;
		this.value = value;
	}

	public String getLabel() {
		return label;
	}

	public String getValue() {
		return value;
	}
}
