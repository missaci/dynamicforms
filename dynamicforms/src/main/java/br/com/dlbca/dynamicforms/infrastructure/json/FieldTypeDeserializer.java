package br.com.dlbca.dynamicforms.infrastructure.json;

import java.io.IOException;

import org.codehaus.jackson.JsonParser;
import org.codehaus.jackson.JsonProcessingException;
import org.codehaus.jackson.map.DeserializationContext;
import org.codehaus.jackson.map.JsonDeserializer;

import br.com.dlbca.dynamicforms.core.FieldType;

/**
 * Deserializer for enum case insensitive capabilities on fields
 * 
 * @author Mateus
 *
 */
public class FieldTypeDeserializer extends JsonDeserializer<FieldType> {

	@Override
	public FieldType deserialize(JsonParser parser, DeserializationContext context) throws IOException, JsonProcessingException {
		return FieldType.valueOf(parser.getText().toUpperCase());
	}


}