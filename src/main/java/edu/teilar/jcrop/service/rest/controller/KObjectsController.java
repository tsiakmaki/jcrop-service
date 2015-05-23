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
package edu.teilar.jcrop.service.rest.controller;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.w3c.dom.svg.SVGDocument;

import edu.teilar.jcrop.domain.concept.Concept;
import edu.teilar.jcrop.domain.execution.ExecutionModel;
import edu.teilar.jcrop.domain.graph.XGraph;
import edu.teilar.jcrop.domain.graph.node.Node;
import edu.teilar.jcrop.domain.ontology.DomainOntology;
import edu.teilar.jcrop.domain.resource.KObject;
import edu.teilar.jcrop.service.client.ClientUtils;
import edu.teilar.jcrop.service.client.KObjectAsTraversable;
import edu.teilar.jcrop.service.rest.ApiUrls;
import edu.teilar.jcrop.service.rest.service.KObjectsService;
import edu.teilar.jcrop.service.rest.utils.CropResourceNotFoundException;
import edu.teilar.jcrop.service.rest.utils.ServiceError;
import edu.teilar.jcrop.service.traverse.KObjectBreadthFirstTraverseOfXGraph;

/**
 * @author m.tsiakmaki
 *
 */
@RestController //expose a controller as RESTFul resource
@RequestMapping(value=ApiUrls.ROOT_KOBJECTS_URL, produces=MediaType.APPLICATION_JSON_VALUE)
public class KObjectsController {

	private static final Logger logger = Logger.getLogger(KObjectsController.class);
	
	@Autowired
	private KObjectsService kobjectsService;
	
	@Autowired
	KObjectBreadthFirstTraverseOfXGraph breadthFirstTraverseOfXGraph;
	
	public KObjectsController() {
		
	}
	
	/**
	 * DomainOntology defines a set of concepts 
	 * 
	 * @return the domain ontology of the controller
	 */
	@RequestMapping(value = ApiUrls.CONCEPTS_URL, method = RequestMethod.GET)
	public DomainOntology getDomainOntology() {
		DomainOntology result = kobjectsService.getDomainOntology();
		if (result == null) throw new CropResourceNotFoundException("", "DomainOntology"); 
		return result;
    }

	@RequestMapping(value = ApiUrls.CONCEPT_URL, method = RequestMethod.GET)
	public Concept getConcept(@PathVariable String concept) {
		Concept result = kobjectsService.getConceptByName(concept);
		if (result == null) throw new CropResourceNotFoundException(concept, "Concept");  
		return result;
    }
	
	/**
	 * 
	 * @param concept
	 * @return
	 */
	@RequestMapping(value = ApiUrls.KOBJECTS_CONCEPT_URL, method = RequestMethod.GET)
	public List<KObject> getKObjectsWithTarget(@PathVariable String concept) {
		List<KObject> result = kobjectsService.getKObjectsWithTarget(concept);
		return result;
	}
	
	/**
	 * 
	 * @return
	 */
	@RequestMapping(value = ApiUrls.KOBJECTS_URL, method = RequestMethod.GET)
	public Collection<KObject> getKObjects() {
		Collection<KObject> result = kobjectsService.getKObjects();
		return result;
	}
	
	
	/**
	 * 
	 * @param kobj
	 * @return
	 */
	@RequestMapping(value = ApiUrls.KOBJECT_URL, method = RequestMethod.GET)
	public KObject getKObject(@PathVariable String kobj) {
		KObject result = kobjectsService.getKObjectByName(kobj);
		if (result == null) throw new CropResourceNotFoundException(kobj, "KObject");  
		return result;
	}
	
	/**
	 * TODO should be a list 
	 * 
	 * @param kobj
	 * @return
	 */
	@RequestMapping(value = ApiUrls.KOBJECT_XMODELS_URL, method = RequestMethod.GET)
	public ExecutionModel getExecutionModelOfKObject(@PathVariable String kobj) {
		ExecutionModel result = kobjectsService.getXModelOfKObject(kobj);
		if (result == null) throw new CropResourceNotFoundException(kobj, "ExecutionModel");  
		return result;
	}
	
	/**
	 * 
	 * @param kobj
	 * @param xmodel
	 * @return
	 */
	@RequestMapping(value = ApiUrls.KOBJECT_XMODELS_XGRAPH_URL, method = RequestMethod.GET)
	public XGraph getXGraphOfExecutionModelOfKObject(@PathVariable String kobj, 
			@PathVariable String xmodel) {
		XGraph result = kobjectsService.getXGraphOfXModelOfKObject(kobj, xmodel);
		if (result == null) throw new CropResourceNotFoundException(xmodel + " " + kobj, "XGraph");  
		return result;
	}
	
	/**
	 * 
	 * @param kobj
	 * @param xmodel
	 * @return
	 */
	@RequestMapping(value = ApiUrls.KOBJECT_XMODELS_XGRAPH_XNODES_URL, method = RequestMethod.GET)
	public Set<Node> getXNodesOfXGraphOfExecutionModelOfKObject(
			@PathVariable String kobj, 
			@PathVariable String xmodel) {
		Set<Node> result = kobjectsService.getXNodesOfXGraphOfXModelOfKObject(kobj, xmodel);
		if (result.size()==0) throw new CropResourceNotFoundException(xmodel + " " + kobj, "XNodes");  
		return result;
	}
	
	/**
	 * 
	 * @param kobj
	 * @param xmodel
	 * @param xnode
	 * @return
	 */
	@RequestMapping(value = ApiUrls.KOBJECT_XMODELS_XGRAPH_XNODE_URL, method = RequestMethod.GET)
	public Node getXNodeOfXGraphOfExecutionModelOfKObject(
			@PathVariable String kobj, @PathVariable String xmodel, 
			@PathVariable String xnode) {
		Node result = kobjectsService.getXNodeOfXGraphOfXModelOfKObject(kobj, xmodel, xnode);
		if (result == null) throw new CropResourceNotFoundException(xnode + " " + xmodel + " " + kobj, "XNode");  
		return result;
	}
	
	/**
	 * Document doc = f.createDocument(uri);
	 * @param kobj
	 * @param xmodel
	 * @return
	 */
	@RequestMapping(value = ApiUrls.KOBJECT_XMODELS_XGRAPH_SVG_URL, method = RequestMethod.GET)
	public Map<String, Object> getSVGOfXGraphOfExecutionModelOfKObject(
			@PathVariable String kobj, @PathVariable String xmodel) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		resultMap.put("kobject", getKObject(kobj)); 
		resultMap.put("graph", 
				kobjectsService.getSVGOfXGraphOfXModelOfKObject(kobj, xmodel));
		return resultMap;
	}
	
	
	/**
	 * 
	 * @param kobj
	 * @param xmodel
	 * @return
	 */
	@RequestMapping(value = ApiUrls.KOBJECT_RESOURCE_SUPPORT_URL, method = RequestMethod.GET)
	public Map<String, String> getSupportResourceOfKObject(@PathVariable String kobj) {
		String result = kobjectsService.getSupportResourceOfKObject(kobj);
		if (result == null) throw new CropResourceNotFoundException(kobj, "SupportResource");  
		
		Map<String, String> resultMap = new HashMap<String, String>();
		resultMap.put("text", result);
		
		return resultMap;
	}
	
	
	/**
	 * 
	 * @param kobj
	 * @param xmodel
	 * @return
	 */
	@RequestMapping(value = ApiUrls.KOBJECT_RESOURCE_ASSESSMENT_URL, method = RequestMethod.GET)
	public Map<String, String> getAssessmentResourceOfKObject(
			@PathVariable String kobj) {
		String result = kobjectsService.getAssessmentResourceOfKObject(kobj);
		if (result == null) throw new CropResourceNotFoundException(kobj, "AssessmentResource");  
		
		Map<String, String> resultMap = new HashMap<String, String>();
		resultMap.put("text", result);
		
		return resultMap;
	}
	
	@RequestMapping(value = ApiUrls.KOBJECT_XMODELS_XGRAPH_BREADTHFIRST_URL, 
			method = RequestMethod.GET)
	public ArrayList<KObjectAsTraversable> getBreadthFirstTraversalListOfXGraph(
			@PathVariable String kobj, @PathVariable String xmodel) {
		
		KObject kobject = kobjectsService.getKObjectByName(kobj);
		ArrayList<KObjectAsTraversable> result = breadthFirstTraverseOfXGraph.traverse(kobject);	
		
		if (result.isEmpty()) throw new CropResourceNotFoundException(kobj, 
				"BreadthFirst Traverse Of XGraph failure");  
		
		return result;
	}
	/**
	 * 
	 * @param e
	 * @return
	 */
	@ExceptionHandler(CropResourceNotFoundException.class)
	@ResponseStatus(HttpStatus.NOT_FOUND)
	public ServiceError cropResourceNotFound(CropResourceNotFoundException e) {
		return new ServiceError(4, e.getCropResourceType() 
				+ " " +  e.getCropResourceName() + " not found");
	}
}
