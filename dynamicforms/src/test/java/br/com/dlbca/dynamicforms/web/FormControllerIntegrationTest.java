package br.com.dlbca.dynamicforms.web;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

import java.io.IOException;

import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.hamcrest.core.IsEqual;
import org.hamcrest.core.StringContains;
import org.hamcrest.core.StringEndsWith;
import org.hamcrest.core.StringStartsWith;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;

import br.com.dlbca.dynamicforms.core.Form;
import br.com.dlbca.dynamicforms.test.infra.IntegrationTest;
import br.com.dlbca.dynamicforms.test.infra.MongoDBSupport;
import br.com.dlbca.dynamicforms.test.infra.TestContent;

/**
 * Integration test for the FormController
 * It uses integration-test.xml to avoiding data loss.
 * 
 * It uses an embed database, running on the port 27028
 * 
 * @author Mateus
 *
 */

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration("classpath:/integration-test.xml")
@Category(IntegrationTest.class)
public class FormControllerIntegrationTest extends MongoDBSupport{

	@Autowired private MongoTemplate mongoTemplate;
	@Autowired private WebApplicationContext webAppContext;

	private MockMvc mockMvc;
	private String id;

	@Before
	public void setup() throws JsonParseException, JsonMappingException, IOException {
		this.mockMvc = webAppContextSetup(this.webAppContext).build();
		this.id = loadSimpleContentInDB();
	}

	@After
	public void tearDown() throws Exception {
		mongoTemplate.dropCollection("form");
	}

	@Test
	public void mustListAllFormsContent() throws Exception {
		this.mockMvc
				.perform(get("/templates").accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(content().contentType("application/json;charset=UTF-8"))
				.andExpect(content().string(StringStartsWith.startsWith("[")))
				.andExpect(content().string(StringEndsWith.endsWith("]")))
				.andExpect(content().string(StringContains.containsString("\"id\":\""+id+"\"")));
	}

	@Test
	public void mustFindAFormById() throws Exception {
		this.mockMvc
				.perform(get("/templates/"+id).accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(content().contentType("application/json;charset=UTF-8"))
				.andExpect(content().string(StringContains.containsString("\"id\":\""+id+"\"")));
	}
	
	@Test
	public void mustPostANewForm() throws Exception {
		this.mockMvc
				.perform(post("/templates").contentType(MediaType.APPLICATION_JSON).content(TestContent.VALID_POST_FORM))
				.andExpect(status().isOk())
				.andExpect(content().contentType("application/json;charset=UTF-8"))
				.andExpect(content().string(StringContains.containsString("\"title\":\"Form Inserido\"")));
		
	}
	
	@Test
	public void mustFailToPostANewFormDueConstraintViolations() throws Exception {
		this.mockMvc
				.perform(post("/templates").contentType(MediaType.APPLICATION_JSON).content(TestContent.INVALID_POST_FORM))
				.andExpect(status().isBadRequest())
				.andExpect(content().string(StringContains.containsString(TestContent.ERROR_MESSAGES[0])))
				.andExpect(content().string(StringContains.containsString(TestContent.ERROR_MESSAGES[1])));
		
	}
	
	@Test
	public void mustUpdateTheDefaultInsertedValue() throws Exception {
		this.mockMvc
				.perform(put("/templates/"+id).contentType(MediaType.APPLICATION_JSON).content(TestContent.SIMPLE_DATA.replaceAll("Títulodoformulário", "Novo Título")))
				.andExpect(status().isOk())
				.andExpect(content().string(StringContains.containsString("\"id\":\""+id+"\"")))
				.andExpect(content().string(StringContains.containsString("Novo Título")));
		
	}
	
	@Test
	public void mustDeleteTheDefaultInsertedValue() throws Exception {
		this.mockMvc
				.perform(delete("/templates/"+id))
				.andExpect(status().isOk());
		
		this.mockMvc
				.perform(get("/templates").accept(MediaType.APPLICATION_JSON))
				.andExpect(content().string(IsEqual.equalTo("[]")));
		
	}
	
	@Test
	public void mustGetDataFromTheDefaultForm() throws Exception {
		this.mockMvc
				.perform(get("/templates/"+id+"/data"))
				.andExpect(status().isOk())
				.andExpect(content().string(StringContains.containsString("\"nome\":\"Guilherme\"")));
		
		
	}
	
	@Test
	public void mustPostNewDataInTheDefaultForm() throws Exception {
		this.mockMvc
				.perform(post("/templates/"+id+"/data").contentType(MediaType.APPLICATION_JSON).content(TestContent.FORM_DATA))
				.andExpect(status().isOk());
		
		this.mockMvc
		.perform(get("/templates/"+id+"/data"))
		.andExpect(status().isOk())
			.andExpect(content().string(StringContains.containsString("\"Nome\":\"Mateus\"")));
		
		
	}

	//============= Métodos Auxiliares ==================
	private String loadSimpleContentInDB() throws JsonParseException, JsonMappingException, IOException{
		ObjectMapper mapper = new ObjectMapper();
		Form form = mapper.readValue(TestContent.SIMPLE_DATA, Form.class);
		mongoTemplate.insert(form);
		return form.getId();
	}

}