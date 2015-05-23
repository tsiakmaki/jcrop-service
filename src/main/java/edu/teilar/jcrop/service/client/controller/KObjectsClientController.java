/*
 * (C) Copyright 2010-2013 m.tsiakmaki.
 * 
 * This file is part of jcropeditor.
 *
 * jcropeditor is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License (LGPL) 
 * as published by the Free Software Foundation, version 3.
 * 
 * jcropeditor is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 * 
 * You should have received a copy of the GNU Lesser General Public License
 * along with jcropeditor.  If not, see <http://www.gnu.org/licenses/>.
 */
package edu.teilar.jcrop.service.client.controller;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.client.RestTemplate;

import edu.teilar.jcrop.domain.concept.Concept;
import edu.teilar.jcrop.domain.ontology.DomainOntology;
import edu.teilar.jcrop.domain.resource.KObject;
import edu.teilar.jcrop.service.client.ClientUrls;
import edu.teilar.jcrop.service.client.ClientUtils;
import edu.teilar.jcrop.service.rest.ApiUrls;

/**
 * @author m.tsiakmaki
 *
 */
@Controller
@RequestMapping(value=ClientUrls.ROOT_CLIENT_URL)
public class KObjectsClientController {
	
	@RequestMapping(method=RequestMethod.GET)
	public String home() {
		return "home";
	}
	

	@RequestMapping(value="/concepts", method=RequestMethod.GET)
	public String getConcepts(Model model) {
		RestTemplate restTemplate = new RestTemplate();
		String url = ClientUrls.SERVERADDRESS +	ApiUrls.CONCEPTS_URL;
		DomainOntology domain = restTemplate.getForObject(
				url, DomainOntology.class);
		model.addAttribute("domain", domain);
		return "concepts";
	}
	
	@RequestMapping(value="/concepts/{concept}", method=RequestMethod.GET)
	public String getConcept(@PathVariable String concept, Model model) {
		RestTemplate restTemplate = new RestTemplate();
		String url = ClientUrls.SERVERADDRESS +	ApiUrls.CONCEPT_URL;
		Concept c = restTemplate.getForObject(url, Concept.class, concept);
		
		url = ClientUrls.SERVERADDRESS + ApiUrls.KOBJECTS_CONCEPT_URL;
		List<KObject> kobjects = (List<KObject>)restTemplate.getForObject(url, List.class, concept);
		model.addAttribute("concept", c);
		model.addAttribute("kobjects", kobjects);
		return "concept";
	}
	
	
	@RequestMapping(value="/kobjects/{kobject}", method=RequestMethod.GET)
	public String getKObject(@PathVariable String kobject, Model model) {
		
		RestTemplate restTemplate = new RestTemplate();
		
		String url = ClientUrls.SERVERADDRESS + ApiUrls.KOBJECT_XMODELS_XGRAPH_SVG_URL;
		Map<String, Object> svgDocument = restTemplate.getForObject(url, Map.class, kobject, "default");
	    model.addAllAttributes(svgDocument);
	    
		url = ClientUrls.SERVERADDRESS +	ApiUrls.KOBJECT_URL;
		KObject kobj = restTemplate.getForObject(url, KObject.class, kobject);
		model.addAttribute("kobject", kobj);
		
		return "kobject";
	}
	
	@RequestMapping(value="/kobjects", method=RequestMethod.GET)
	public String getKObjects(Model model) {
		RestTemplate restTemplate = new RestTemplate();
		String url = ClientUrls.SERVERADDRESS +	ApiUrls.KOBJECTS_URL;
		List<KObject> kobjects = (List<KObject>)restTemplate.getForObject(url, List.class);
		model.addAttribute("kobjects", kobjects);
		return "kobjects";
	}
	
	
	
}
