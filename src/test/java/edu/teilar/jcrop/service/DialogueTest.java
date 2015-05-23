package edu.teilar.jcrop.service;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.ObjectMapper;

import edu.teilar.jcrop.service.client.controller.KOrderClientController;

public class DialogueTest {

	@Test
	public void test() {
		String kobj = "complex1";
		String dialog = "DialogueNode_1_Default_complex1_DialogueNode";
		RestTemplate restTemplate = new RestTemplate();
		// set up json mapper
		MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter();
		StringHttpMessageConverter s = new StringHttpMessageConverter();
		ObjectMapper mapper = new ObjectMapper();
		converter.setObjectMapper(mapper);
		List<HttpMessageConverter<?>> messageConverters = new ArrayList<HttpMessageConverter<?>>();
		messageConverters.add(converter);
		messageConverters.add(s);
		restTemplate.setMessageConverters(messageConverters);

		String learningdialogUri =  "http://localhost:8080/jcrop-service/rest/learningobjects/{kobj}/dialog/{dialog}";

		String learningdialog = restTemplate.getForObject(learningdialogUri,
				String.class, kobj, dialog);

		System.out.println(learningdialog);

		assertTrue(learningdialog.startsWith("Nice"));
		;
	}

}
