package edu.teilar.jcrop.service.client.controller;

import java.util.ArrayList;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.ObjectMapper;

import edu.teilar.jcrop.domain.execution.ExecutionModel;
import edu.teilar.jcrop.domain.resource.KObject;
import edu.teilar.jcrop.service.client.ClientUrls;
import edu.teilar.jcrop.service.client.ClientUtils;
import edu.teilar.jcrop.service.client.KObjectAsTraversable;
import edu.teilar.jcrop.service.rest.ApiUrls;


@Controller
@RequestMapping(value=ClientUrls.ROOT_CLIENT_URL)
public class KOrderClientController {
	
	@RequestMapping(value=ClientUrls.CLIENT_KOBJECT_KORDER_URL, method=RequestMethod.GET)
	public String getKOrder(@PathVariable String kobj, Model model) {
		
		RestTemplate restTemplate = new RestTemplate();
		
		// get svg graph
		String url = ClientUrls.SERVERADDRESS + ApiUrls.KOBJECT_XMODELS_XGRAPH_SVG_URL;
		Map<String, Object> svgDocument = restTemplate.getForObject(url, Map.class, kobj, "default");
	    model.addAllAttributes(svgDocument);
			    
		// get kobject
		url = ClientUrls.SERVERADDRESS +	ApiUrls.KOBJECT_URL;
		KObject kobject = restTemplate.getForObject(url, KObject.class, kobj);
		model.addAttribute("kobject", kobject);
		
	    // create first welcome resource 
	    model.addAttribute("resource", 
	    		"Wellcome. Use next button for traversing your Learning Object.");
	   
		url = ClientUrls.SERVERADDRESS + ApiUrls.KOBJECT_XMODELS_XGRAPH_BREADTHFIRST_URL;
		ArrayList<KObjectAsTraversable> traversableList
			= restTemplate.getForObject(url, ArrayList.class, kobj, "default");
		//There are no double quotes in Java List
		// Convert Java List to JSON
		ObjectMapper mapper = new ObjectMapper();
		String json = "";
		try {
			json = mapper.writeValueAsString(traversableList);
		} catch (Exception e) {
			e.printStackTrace();
		}
		model.addAttribute("traversableList", json);
		
		return "korder";
	}


	
//	
//	/**
//	 * 
//	 * @param kobj
//	 * @param cur_kobj
//	 * @return
//	 */
//	@RequestMapping("/xLearningObject/{kobj}/{cur_kobj}")
//	public ModelAndView learningobject(
//			@PathVariable(value = "kobj") String kobj,
//			@PathVariable(value = "cur_kobj") String cur_kobj) {
//
//		RestTemplate restTemplate = new RestTemplate();
//		setUpJsonMapper(restTemplate);
//
//		// xgraph of the main kobj
//		String mainKObjUri = ExecuteXGraphController.SERVERADDRESS + 
//				"/jcrop-service/rest/learningobjects/{kobj}";
//		XGraph mainKObjXGraph = restTemplate.getForObject(mainKObjUri, XGraph.class, kobj);
//		
//		// xgraph of the curent kobj 
//		String curKObjUri = ExecuteXGraphController.SERVERADDRESS + 
//				"/jcrop-service/rest/learningobjects/{cur_kobj}";
//		XGraph curKObjXGraph = restTemplate.getForObject(curKObjUri, XGraph.class, cur_kobj);
//
//		// main kobj svg graph
//		String mainKObjSvgUri = ExecuteXGraphController.SERVERADDRESS + 
//				"/jcrop-service/rest/learningobjects/{kobj}/svg";
//    	String mainKObjSvg = restTemplate.getForObject(mainKObjSvgUri, String.class, kobj);
//    	// current kobj svg graph 
//    	String curKObjSvgUri = ExecuteXGraphController.SERVERADDRESS + 
//				"/jcrop-service/rest/learningobjects/{cur_kobj}/svg";
//    	String curKObjSvg = restTemplate.getForObject(curKObjSvgUri, String.class, cur_kobj);
//    	
//    	// get traversal list 
//    	ArrayList<String> t = new ArrayList<String>();
//		traverseKObject(mainKObjXGraph, t);
//		
//		// model  
//		Map<String, Object> model = new HashMap<String, Object>();
//		
//		model.put("cur_kobj_name", curKObjXGraph.getkObjectName());
//		model.put("cur_kobj_target", curKObjXGraph.getTargetConcept());
//		model.put("cur_kobj_svg", curKObjSvg);
//		
//		model.put("kobj_name", mainKObjXGraph.getkObjectName());
//		model.put("kobj_target", mainKObjXGraph.getTargetConcept());
//		model.put("main_kobj_svg", mainKObjSvg);
//		
//		model.put("traverse", t);
//		
//		return new ModelAndView("xLearningObject", "model", model);
//	}
//
//	/**
//	 * 
//	 * @param g
//	 * @param t
//	 */
//	private void traverseKObject(Graph<XNode, XEdge> g, ArrayList<String> t) {
//		Iterator<XNode> i = new BreadthFirstXGraphIterator((XGraph) g);
//		for (; i.hasNext();) {
//			XNode o = i.next();
//			if (o.type() == ExecutionGraph.XGRAGH_XNODE_TYPE) {
//				traverseKObject((XGraph) o, t);
//			} else if (o.type() == ExecutionGraph.PAR_GROUP_XNODE_TYPE) {
//				t.add(o.getName() + ":" + ExecutionGraph.XGRAGH_XNODE_TYPE
//						+ ":" + o.getkObjectName());
//				traverseKObject((GroupXNode) o, t);
//			} else {
//				t.add(o.getName()+ ":" + o.type() + ":" + o.getkObjectName());
//			}
//		}
//	}
//	
//	
//	/**
//	 * executeLearningObject?kobj=c1&cur_kobj=c1&traverse_list[]=visited_list[]
//	 *  
//	 * @param kobj
//	 * @param cur_kobj
//	 * @param traverse_list String[] of "kobj_name:kobj_type" or null 
//	 * @param visited_list
//	 * @return
//	 */
//    @RequestMapping(value = {"/executeLearningObject"}, method = RequestMethod.POST)
//	public ModelAndView executeLearningObject(
//			@RequestParam(value="kobj", required=true) String kobj,
//			@RequestParam(value="cur_kobj", required=false) String cur_kobj, 
//			@RequestParam(value="traverse_list[]", required=false) String[] traverse_list,
//			@RequestParam(value="visited_list[]", required=false) String[] visited_list) {
//
//    	// the model to return
//    	Map<String, Object> model = new HashMap<String, Object>();
//    	
//    	// the rest service
//		RestTemplate restTemplate = new RestTemplate();
//		// setup json mapper for rest 
//		setUpJsonMapper(restTemplate);
//
//		// xgraph for main kobj 
//		String mainKObjUri = ExecuteXGraphController.SERVERADDRESS + 
//				"/jcrop-service/rest/learningobjects/{kobj}";
//		XGraph mainKObjXGraph = restTemplate.getForObject(mainKObjUri, XGraph.class, kobj);
//
//		// svg graph for main kobj 
//    	String mainKObjSvgUri = ExecuteXGraphController.SERVERADDRESS + 
//				"/jcrop-service/rest/learningobjects/{kobj}/svg";
//    	String mainKObjSvg = restTemplate.getForObject(mainKObjSvgUri, String.class, kobj);
//    			
//    	// if stays false, kobj is finished
//    	boolean nextActFound = false;
//    	boolean isControlOrDialog = false;
//    	
//    	// get traverse list
//    	if(traverse_list == null) { 
//    		// calculate the traverse list
//			ArrayList<String> traverseArrayList = new ArrayList<String>();
//			
//			traverseKObject(mainKObjXGraph, traverseArrayList);
//			System.out.println("\n\n" + traverseArrayList + "\n\n");
//			traverse_list = traverseArrayList.toArray(
//					new String[traverseArrayList.size()]);
//    	}
//    	model.put("traverse_list", traverse_list);
//    	
//    	// update visited nodes: a string with kobj names separated with semi colon ":"
//    	List<String> visitedArrayList = 
//    			new ArrayList<String>(Arrays.asList(visited_list));
//    	
//    	for(String traverse_str : traverse_list) {
//    		String traverse_array[] = traverse_str.split(":"); 
//			if(!visitedArrayList.contains(traverse_str)) {
//				// check if it is a learning act
//				if(Integer.valueOf(traverse_array[1]) 
//						== ExecutionGraph.LEARNING_ACT_XNODE_TYPE) {
//					// this is the next resourse
//					cur_kobj = traverse_array[2];
//					// add as visited 
//					visitedArrayList.add(traverse_str);
//					nextActFound = true;
//					break;
//				} else if(Integer.valueOf(traverse_array[1]) 
//						== ExecutionGraph.CONTROL_XNODE_TYPE) {
//					
//					String controlUri = ExecuteXGraphController.SERVERADDRESS + 
//							"/jcrop-service/rest/learningobjects/{kobj}/control/{control}";
//					// the threshold
//					String learningcontrol= 
//						restTemplate.getForObject(controlUri, 
//									String.class, kobj, traverse_array[0]);
//					
//					model.put("cur_kobj", traverse_array[2]);
//					model.put("cur_kobj_target", "-");
//					model.put("cur_kobj_svg", "");
//					
//					model.put("resource", learningcontrol);
//					
//
//					visitedArrayList.add(traverse_str);
//					isControlOrDialog = true;
//					nextActFound = true;
//					break;
//				} else if(Integer.valueOf(traverse_array[1]) 
//						== ExecutionGraph.DIALOGUE_XNODE_TYPE) {
//					
//					String learningdialogUri = ExecuteXGraphController.SERVERADDRESS + 
//							"/jcrop-service/rest/learningobjects/{kobj}/dialog/{dialog}";
//					System.out.println("\n\n" + "/jcrop-service/rest/learningobjects/" + kobj +"/dialog/"  +traverse_array[0]);
//					String learningdialog = 
//						restTemplate.getForObject(learningdialogUri, 
//									String.class, kobj, traverse_array[0]);
//					model.put("cur_kobj", traverse_array[2]);
//					model.put("cur_kobj_target", "-");
//					model.put("cur_kobj_svg", "");
//					
//					model.put("resource", learningdialog);
//
//					visitedArrayList.add(traverse_str);
//					isControlOrDialog = true;
//					nextActFound = true;
//					break;
//				} else {
//					// add as visited, and go on
//					visitedArrayList.add(traverse_str);
//				}
//			}
//		}
//    	// if cur_kobj is still empty, it is start 
//    	boolean isStart = (cur_kobj == null);
//    	
//    	// second time with the same cur_kobj 
//    	boolean isEnd = !isStart && !nextActFound;
//    	model.put("isEnd", isEnd);
//    	
//    	// add the updated visited list 
//    	model.put("visited_list", visitedArrayList);
//    	
//		
//		if(nextActFound && !isControlOrDialog) {
//			// get the current recourse kobj
//			Map<String, String> curKResourceMap = new HashMap<String, String>();
//			
//			//FIXME: performance issues 
//			// maybe not so good to send a json string with the content of the files 
//			String curKResourceUri = ExecuteXGraphController.SERVERADDRESS + 
//					"/jcrop-service/rest/learningobjects/{cur_kobj}/resource";
//			curKResourceMap = restTemplate.getForObject(curKResourceUri, Map.class, cur_kobj);
//			
//			// svg xgraph of the cur kobj
//			String curKObjSvgUri = ExecuteXGraphController.SERVERADDRESS + 
//					"/jcrop-service/rest/learningobjects/{cur_kobj}/svg";
//	    	String curKObjSvg = restTemplate.getForObject(curKObjSvgUri, String.class, cur_kobj);
//	    	
//			model.put("cur_kobj", cur_kobj);
//			model.put("cur_kobj_target", "todo");
//			model.put("cur_kobj_svg", curKObjSvg);
//			
//			String type = curKResourceMap.get("type"); 
//			model.put("resourcetype", type);
//			
//			if(type.equals("SupportResource")) {
//				model.put("resource", parseSupportResource(curKResourceMap.get("file")));
//			} else if(type.equals("AssessmentResource")) {
//				model.put("resource", curKResourceMap.get("file"));
//			} else {
//				model.put("resource", "Unable to get the contents of the file");
//			}
//		}
//		
//		// a graph of the main kobj
//		model.put("main_kobj", mainKObjXGraph.getkObjectName());
//		model.put("main_kobj_target", mainKObjXGraph.getTargetConcept());
//		model.put("main_kobj_svg", mainKObjSvg);
//
//		// current resource
//		if(isStart) {
//			model.put("cur_kobj", "");
//			model.put("cur_kobj_target", "");
//			model.put("cur_kobj_svg", "");
//		} 
//    	
//		if(isEnd) {
//			model.put("resource", "<p>Finished.</p><p>Go to <a href=\"listLearningObjects\">list of available learning objects</a></p>"); 
//		} 
//		
//		return new ModelAndView("executeLearningObject", "model", model);
//	}
//    
//    /**
//     * Parse resource with the MediaWikiLanguage mark up parser
//     *  
//     * @param fileContent
//     * @return
//     */
//    private String parseSupportResource(String fileContent) {
//    	StringWriter writer = new StringWriter();
//		HtmlDocumentBuilder builder = new HtmlDocumentBuilder(writer);
//		// avoid the <html> and <body> tags 
//		builder.setEmitAsDocument(false);
//		MarkupParser parser = new MarkupParser(new MediaWikiLanguage());
//		parser.setBuilder(builder);
//		parser.parse(fileContent);
//		// 
//		return writer.toString();
//    }
//    
//    //executeLearningObject?kobj=c1&cur_kobj=c1&traverse_list[]=visited_list[]
//    //@param: kobj the name of the main kobject  
//    @RequestMapping(value = {"/executeLearningObject1"}, method = RequestMethod.GET)
//    public ModelAndView executeLearningObject1(
//    		@RequestParam(value="kobj", required=true) String kobj) {
//    	
//    	RestTemplate restTemplate = new RestTemplate();
//    	setUpJsonMapper(restTemplate);
//    	
//    	// model to return  
//    	Map<String, Object> model = new HashMap<String, Object>();
//    	
//    	// get main kobj xgraph
//    	String mainKObjURI = ExecuteXGraphController.SERVERADDRESS + 
//				"/jcrop-service/rest/learningobjects/{kobj}";
//    	XGraph mainKObjXGraph = restTemplate.getForObject(mainKObjURI, XGraph.class, kobj);
//    	
//    	// get main kobj svg graph 
//    	String mainKObjSvgURI = ExecuteXGraphController.SERVERADDRESS + 
//				"/jcrop-service/rest/learningobjects/{kobj}/svg";
//    	String mainKObjSvg = restTemplate.getForObject(mainKObjSvgURI, String.class, kobj);
//    	model.put("main_kobj_svg", mainKObjSvg);
//    	
//    	// set up rest object 
//    	model.put("main_kobj", mainKObjXGraph.getkObjectName());
//    	model.put("main_kobj_target", mainKObjXGraph.getTargetConcept());
//    	
//    	model.put("cur_kobj", "");
//    	model.put("cur_kobj_target", "");
//    	
//    	model.put("traverse_list", "");
//    	model.put("visited_list", "");
//    	
//    	model.put("resource", 
//    			"Wellcome. Use next button for traversing your Learning Object"); 
//    	
//    	return new ModelAndView("executeLearningObject", "model", model);
//    }
}
