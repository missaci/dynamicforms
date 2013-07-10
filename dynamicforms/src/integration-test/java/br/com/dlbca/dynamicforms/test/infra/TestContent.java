package br.com.dlbca.dynamicforms.test.infra;

public class TestContent {
	
	public static final String SIMPLE_DATA = "{"
			+"	\"title\":\"Títulodoformulário\","
			+"	\"fields\":[{"
			+"		\"label\":\"Nome\","
			+"		\"type\":\"text\","
			+"		\"required\":true,"
			+"		\"placeholder\":\"Nome:\""
			+"	},{"
			+"		\"label\":\"Sexo\","
			+"		\"type\":\"radio\","
			+"		\"required\":true,"
			+"		\"radios\":[{"
			+"			\"label\":\"M\","
			+"			\"value\":\"masculino\""
			+"		},{"
			+"			\"label\":\"F\","
			+"			\"value\":\"feminino\""
			+"		}],"
			+"		\"value\":1"
			+"	},{"
			+"		\"label\":\"E-mail\","
			+"		\"type\":\"email\","
			+"		\"required\":false,"
			+"		\"placeholder\":\"Digiteume-mail\""
			+"	},{"
			+"		\"label\":\"idade\","
			+"		\"type\":\"number\","
			+"		\"required\":false"
			+"	}],"
			+"	\"data\":["
			+"		{"
			+"			\"nome\":\"Guilherme\","
			+"			\"sexo\":\"masculino\","
			+"			\"email\":\"guilherme@email.com\","
			+"			\"idade\":27"
			+"		},"
			+"		{"
			+"			\"nome\":\"Rafael\","
			+"			\"sexo\":\"masculino\""
			+"		}"
			+"	]"
			+"}";
	
	public static final String VALID_POST_FORM = "{"
			+"	\"title\":\"Form Inserido\","
			+"	\"fields\":[{"
			+"		\"label\":\"Nome\","
			+"		\"type\":\"text\","
			+"		\"required\":true,"
			+"		\"placeholder\":\"Nome:\""
			+"	},{"
			+"		\"label\":\"Sexo\","
			+"		\"type\":\"radio\","
			+"		\"required\":true,"
			+"		\"radios\":[{"
			+"			\"label\":\"M\","
			+"			\"value\":\"masculino\""
			+"		},{"
			+"			\"label\":\"F\","
			+"			\"value\":\"feminino\""
			+"		}],"
			+"		\"value\":1"
			+"	}]"
			+"}";
	
	public static final String INVALID_POST_FORM = "{"
			+"	\"title\":\"Form Inserido\","
			+"	\"fields\":[{"
			+"		\"label\":\"\","
			+"		\"type\":\"text\","
			+"		\"required\":true,"
			+"		\"placeholder\":\"Nome:\""
			+"	},{"
			+"		\"label\":\"Sexo\","
			+"		\"type\":\"radio\","
			+"		\"required\":true"
			+"	}]"
			+"}";
	
	public static final String ERROR_MESSAGES[] = {
				"{\"message\":\"Definições de campo inválidas. Cheque o tipo e os valores do atributo radios\",\"category\":\"form.fields[1]\"}",
				"{\"message\":\"Label de um campo não pode ser vazio\",\"category\":\"form.fields[0].label\"}"
				};
	
	public static final String FORM_DATA = "{\"Nome\":\"Mateus\",\"Sexo\":\"masculino\",\"E-mail\":\"mateus@email.com\",\"idade\":28}";

}
