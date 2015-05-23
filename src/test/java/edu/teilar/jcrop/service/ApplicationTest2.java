/*
 * (C) Copyright 2010-2013 Maria Tsiakmaki.
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

package edu.teilar.jcrop.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.ObjectMapper;

import edu.teilar.jcrop.domain.graph.group.ParXGroup;
import edu.teilar.jcrop.domain.graph.node.Node;
import edu.teilar.jcrop.domain.resource.KObject;
import edu.teilar.jcrop.iterator.BreadthFirstXGraphIterator;

public class ApplicationTest2 {
	
	public static void main(String[] args) {
		
		RestTemplate restTemplate = new RestTemplate();
    	// model to return  
    	Map<String, Object> model = new HashMap<String, Object>();
    	
    	// set up json mapper
    	MappingJackson2HttpMessageConverter converter = 
    			new MappingJackson2HttpMessageConverter();
    	ObjectMapper mapper = new ObjectMapper();
    	converter.setObjectMapper(mapper);
    	List<HttpMessageConverter<?>> messageConverters = 
    			new ArrayList<HttpMessageConverter<?>>();
    	messageConverters.add(converter);
    	restTemplate.setMessageConverters(messageConverters);
    	
    	
	    
	    String uri = "http://localhost:8080/jcrop-service/rest/learningobjects/{kobj}";
				 
	    KObject kobj = restTemplate.getForObject(uri, KObject.class, "complex1");
	   
	    
	    System.out.println("Learning Object: " + kobj.getName());
	    System.out.println("Target Concept: " + kobj.getTargetEducationalObjective().getName());
	    
	    ArrayList<Node> traverseArrayList = new ArrayList<Node>();
		
	    traverseKObject(kobj, traverseArrayList);
	    System.out.println("sss" + traverseArrayList);
	}
	
	
	
	public static void traverseKObject(KObject kobj, ArrayList<Node> traverseArrayList) {
		
		Iterator<Node> i = new BreadthFirstXGraphIterator(
				kobj.getExecutionModel().getxGraph());
		while(i.hasNext()) {
			Node n = i.next();
			//System.out.println(o.getType());
			if (n instanceof ParXGroup) {
				//System.out.println("KObject: " + ((XGraph)o).getkObjectName());
				//traverseKObject(o, traverseArrayList);
			} else {
				System.out.println(n.getName());
				traverseArrayList.add(n);
			} 
		}
	}
	
}
