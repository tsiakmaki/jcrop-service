package edu.teilar.jcrop.service.rest.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.StringWriter;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.apache.batik.dom.svg.SAXSVGDocumentFactory;
import org.apache.batik.util.XMLResourceDescriptor;
import org.apache.log4j.Logger;
import org.eclipse.mylyn.wikitext.core.parser.MarkupParser;
import org.eclipse.mylyn.wikitext.core.parser.builder.HtmlDocumentBuilder;
import org.eclipse.mylyn.wikitext.mediawiki.core.MediaWikiLanguage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.w3c.dom.Document;
import org.w3c.dom.svg.SVGDocument;

import edu.teilar.jcrop.domain.builder.concept.ConceptBuilder;
import edu.teilar.jcrop.domain.builder.graph.KObjectBuilderCreator;
import edu.teilar.jcrop.domain.builder.ontology.DomainOntologyBuilder;
import edu.teilar.jcrop.domain.builder.resource.KObjectBuilder;
import edu.teilar.jcrop.domain.concept.Concept;
import edu.teilar.jcrop.domain.director.DomainOntologyDirector;
import edu.teilar.jcrop.domain.director.KObjectDirector;
import edu.teilar.jcrop.domain.director.KObjectFinder;
import edu.teilar.jcrop.domain.execution.ExecutionModel;
import edu.teilar.jcrop.domain.graph.XGraph;
import edu.teilar.jcrop.domain.graph.edge.Edge;
import edu.teilar.jcrop.domain.graph.group.ParXGroup;
import edu.teilar.jcrop.domain.graph.node.LearningActNode;
import edu.teilar.jcrop.domain.graph.node.Node;
import edu.teilar.jcrop.domain.ontology.DomainOntology;
import edu.teilar.jcrop.domain.resource.AssociatableResource;
import edu.teilar.jcrop.domain.resource.KObject;
import edu.teilar.jcrop.domain.resource.KProduct;
import edu.teilar.jcrop.domain.resource.KResourceAssessment;
import edu.teilar.jcrop.domain.resource.KResourceSupport;
import edu.teilar.jcrop.domain.resource.PhysicalLocation;
import edu.teilar.jcrop.owl.CROPOntologyController;
import edu.teilar.jcrop.service.rest.utils.AssessmentResourceParser;

/**
 * 
 * 
 * 
 * @version 0.1 2010
 * @author Maria Tsiakmaki
 */
@Service
public class KObjectsService {

	private static final Logger logger = Logger.getLogger(KObjectsService.class);

	@Autowired
	private CROPOntologyController controller;
	
	/**
	 * 
	 */
	public KObjectsService() {
		
	}

	/**
	 * 
	 * @return the domain ontology of the ontology 
	 */
	public DomainOntology getDomainOntology() {
		DomainOntologyDirector director = new DomainOntologyDirector();
		long start = System.nanoTime();
		
		DomainOntologyBuilder builder = new DomainOntologyBuilder(controller);
		DomainOntology domainOntology = director.createDomainOntology(builder);

		if(domainOntology == null) {
			logger.error("DomainOntology not found");
			return null; 
		} 
		
		logger.info("Domain Ontology: " + domainOntology.getName() + 
				", Num of Concepts: " + domainOntology.getDefines().size() 
				+ " [" +  ((System.nanoTime() - start) / 1_000_000_000) +  "s]");
		
		return domainOntology;
	}
	
	/**
	 * TODO: the concept name is one match, later we should take care the prerequisites 
	 * 
	 * @param conceptName
	 * @return the kobjects that target the specified concept
	 */
	public List<KObject> getKObjectsWithTarget(String conceptName) {
		long start = System.nanoTime();
		KObjectFinder finder = new KObjectFinder(controller);
		KObjectBuilderCreator builderCreator = new KObjectBuilderCreator(controller);
		KObjectDirector director = new KObjectDirector();
		
		List<KObject> result = director.createLearningObjectsWithTarget(
				finder, builderCreator, conceptName);
		
		logger.info("Target: " + conceptName + ", Num of kobjects constracted: " 
				+ result.size() + 
				 " [" +  ((System.nanoTime() - start) / 1_000_000_000) +  "s]");
		
		return result;
	}

	/**
	 * 
	 * @return all the kobjects in the ontology 
	 */
	public Collection<KObject> getKObjects() {
		long start = System.nanoTime();
		
		KObjectBuilderCreator builderCreator = new KObjectBuilderCreator(controller);
		KObjectDirector director = new KObjectDirector();
		
		Map<String, KObject> kobjects = controller.getKObjects();
		for (KObject kobj : kobjects.values()) {
			KObjectBuilder builder = builderCreator.createBuilder(kobj);
			director.createLearningObjectTillPrerequisites(builder, kobj.getName());
		}
		
		logger.info("Num of kobjects: " + kobjects.size() + 
			" [" +  ((System.nanoTime() - start) / 1_000_000_000) +  "s]");
		
		return kobjects.values();
	}
	
	/**
	 * TODO: take care null
	 * 
	 * @param kobjName
	 * @return the kobject with the specified name 
	 */
	public KObject getKObjectByName(String kobjName) {
		long start = System.nanoTime();
		
		KObject result = controller.getLearningObjectByName(kobjName);
		
		if(result == null) {
			logger.error("KObject not found: " + kobjName);
			return null; 
		} 
		
		logger.info("KObject returned: " + result.getName() + 
				" [" +  ((System.nanoTime() - start) / 1_000_000_000) +  "s]");
		
		return result;
	}

	/**
	 * TODO: ConceptBuilder is unfinished 
	 * 
	 * @param conceptName
	 * @return the concept with the specifies name 
	 */
	public Concept getConceptByName(String conceptName) {
		long start = System.nanoTime();
		
		ConceptBuilder builder = new ConceptBuilder();
		builder.buildEducationalObjective(conceptName);
		
		Concept result = (Concept)builder.getEducationalObjective();
		
		if(result == null) {
			logger.error("Concept not found: " + conceptName);
			return null; 
		} 
		
		logger.info("Concept build: " + result.getName() + 
				" [" +  ((System.nanoTime() - start) / 1_000_000_000) +  "s]");
		
		return result;
	}
	
	/**
	 * If the xmodel is null, get the KObjectDirector to build the rest of the kobj
	 * 
	 * @param kobjName
	 * @return the xmodel of the specified kobject 
	 */
	public ExecutionModel getXModelOfKObject(String kobjName) {
		long start = System.nanoTime();
		
		KObject kobj = controller.getLearningObjectByName(kobjName);
		if(kobj.getExecutionModel() == null) {
			KObjectBuilderCreator builderCreator = new KObjectBuilderCreator(controller);
			KObjectDirector director = new KObjectDirector();
			KObjectBuilder builder = builderCreator.createBuilder(kobj);
			director.createLearningObject(builder, kobjName);
		}
		
		logger.info("XModel build for: " + kobj.getName() + 
				" [" +  ((System.nanoTime() - start) / 1_000_000_000) +  "s]");
		
		if(kobj.getExecutionModel() == null) {
			logger.error("ExecutionModel not found for: " + kobjName);
			return null; 
		} 
		
		return kobj.getExecutionModel();
	}

	/**
	 * 
	 * @param kobjName
	 * @param xmodel
	 * @return
	 */
	public XGraph getXGraphOfXModelOfKObject(String kobjName, String xmodel) {
		
		KObject kobj = controller.getLearningObjectByName(kobjName);
		
		getXModelOfKObject(kobjName);
		
		if(kobj.getExecutionModel() == null) {
			logger.error("XGraph not found for: " + kobjName);
			return null; 
		} 
		
		return kobj.getExecutionModel().getxGraph();
	}

	/**
	 * 
	 * @param kobjName
	 * @param xmodel
	 * @return
	 */
	public Set<Node> getXNodesOfXGraphOfXModelOfKObject(String kobjName,
			String xmodel) {
		KObject kobj = controller.getLearningObjectByName(kobjName);
		
		getXModelOfKObject(kobjName);
		
		if(kobj.getExecutionModel() == null) {
			logger.error("XNodes of XGraph not found for: " + kobjName);
			return null; 
		} 
		
		return kobj.getExecutionModel().getxGraph().getNodes();
	}

	/**
	 * 
	 * @param kobjName
	 * @param xmodel
	 * @param xnodeName
	 * @return
	 */
	public Node getXNodeOfXGraphOfXModelOfKObject(String kobjName,
			String xmodel, String xnodeName) {
		KObject kobj = controller.getLearningObjectByName(kobjName);
		
		getXModelOfKObject(kobjName);
		
		if(kobj.getExecutionModel() == null) {
			logger.error("XNode " + xnodeName + " of XGraph not found for: " + kobjName);
			return null; 
		} 
		
		return kobj.getExecutionModel().getxGraph().getNodeByName(xnodeName);
	}

	
	/**
	 * 
	 * @param kobjName
	 * @param xmodel
	 * @return
	 */
	public String getSVGOfXGraphOfXModelOfKObject(String kobjName, String xmodel) {
		long start = System.nanoTime();
		
		// complex1_XGraph.svg
		String uri = controller.getCropProjectPath() + 
				System.getProperty("file.separator") + "mxGraphs" +
				System.getProperty("file.separator") + kobjName + "_XGraph.svg";
		
		if(!new File(uri).exists()) {
			logger.error("svg graph not found for " + kobjName);
			return null;
		}
			
		try {
		    String parser = XMLResourceDescriptor.getXMLParserClassName();
		    SAXSVGDocumentFactory f = new SAXSVGDocumentFactory(parser);
		    Document doc = f.createDocument("file:///" + uri);
		    SVGDocument svgDoc = (SVGDocument)doc;
		    
		    logger.info("SVGDocument parsed : " + kobjName + 
					" [" +  ((System.nanoTime() - start) / 1_000_000_000) +  "s]");
		    
		    Source source = new DOMSource(svgDoc);
		    StringWriter out = new StringWriter();
		    Result result = new StreamResult(out);

		    TransformerFactory tFactory = TransformerFactory.newInstance();
		    Transformer transformer = tFactory.newTransformer();
		    transformer.transform(source, result);

		    String xml = out.toString(); 
		    //return ClientUtils.cleanUpSvgString(xml);
		    return xml;
		} catch (IOException ex) {
			ex.printStackTrace();
		} catch (TransformerConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (TransformerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return null;
	}
	
	private PhysicalLocation getPhysicalLocationOfKResource(String kobjName) {
		KObject kobj = controller.getLearningObjectByName(kobjName);
		Assert.isTrue(kobj instanceof KResourceAssessment
				|| kobj instanceof KResourceSupport,
				kobjName + " kobj should be KResourceAssessment | KResourceSupport, it is " + kobj.getClass());
		ExecutionModel xmodel = getXModelOfKObject(kobjName);
		
		for (Node xnode : xmodel.getxGraph().getNodes()) {
			if(xnode instanceof ParXGroup) {
				ParXGroup pGroupNode = (ParXGroup)xnode;
				Assert.isTrue(pGroupNode.getNodes().size() == 1,
						"The par group should be only one and have only one physical location node");
				
				Node phlocnode = pGroupNode.getNodes().iterator().next();
				Assert.isTrue(phlocnode instanceof LearningActNode, 
						"The par group should have only one physical location node");
				LearningActNode act = (LearningActNode) phlocnode;
				AssociatableResource resource = act.getAssociated();
				Assert.isTrue(resource instanceof PhysicalLocation,
						"The associated resource should be of type Physical Location");
				return (PhysicalLocation)resource; 
			}
			logger.error("No PhysicalLocation found in the xgraph of kobj " + kobjName );
		}
		return null; 
	}
	
	
	public String getSupportResourceOfKObject(String kobjName) {
		
		PhysicalLocation pl = getPhysicalLocationOfKResource(kobjName); 
		if(pl == null) {
			return "The Physical Location of the Resource is not found. Please contact the administrator.";
		}

		String filepath =  pl.getPhysicalLocation();
		try {
			byte[] encoded = Files.readAllBytes(Paths.get(filepath));
			
			StringWriter writer = new StringWriter();
			HtmlDocumentBuilder builder = new HtmlDocumentBuilder(writer);
			// avoid the <html> and <body> tags 
			builder.setEmitAsDocument(false);
			MarkupParser parser = new MarkupParser(new MediaWikiLanguage());
			parser.setBuilder(builder);
			parser.parse(new String(encoded, StandardCharsets.UTF_8));
			// 
			return writer.toString();
		} catch (IOException e) {
			logger.error("Error with the PhysicalLocation found in the xgraph of kobj " + kobjName );
			e.printStackTrace();
		} 
		
		logger.error("Resource not found in the xgraph of kobj " + kobjName );
		
		return "Resource not found. Please contact the administrator.";
	}
	
	
	public String getAssessmentResourceOfKObject(String kobjName) {
		PhysicalLocation pl = getPhysicalLocationOfKResource(kobjName); 
		if(pl == null) {
			return "The Physical Location of the Resource is not found. Please contact the administrator.";
		}
		
		String filepath =  pl.getPhysicalLocation();
		try {
			FileInputStream fis = new FileInputStream(filepath);
			ObjectInputStream ois = new ObjectInputStream(fis);
			Object obj = ois.readObject();
			if(obj instanceof List<?>) {
				List<?> data = (List<?>)obj;
				ois.close();
				AssessmentResourceParser asp = new AssessmentResourceParser();
				return asp.parse(data);	
			} else {
				ois.close();
			}
			
		} catch (IOException | ClassNotFoundException e) {
			logger.error("Error with the PhysicalLocation found in the xgraph of kobj " + kobjName );
			e.printStackTrace();
		} 
			
		logger.error("Resource not found in the xgraph of kobj " + kobjName );
		//a-server-error-occurred-please-contact-the-administrator
		return "Resource not found. Please contact the administrator.";
	}
	
	public Set<KResourceAssessment> getAssociatedAssessmentResourcesOfControl(String kobjName, 
			String controlName) {
		KObject kobj = controller.getLearningObjectByName(kobjName);
		Node control = kobj.getExecutionModel().getxGraph().getNodeByName(controlName);
		Set<KResourceAssessment> result = new HashSet<KResourceAssessment>();
		Set<Node> nodes =  getStartOfNodes(control, kobj);
		for(Node n : nodes) {
			traverseForAssessmentResources(n, kobj, result);
		}
		
		return result;
	}
	
	private void traverseForAssessmentResources(Node node, KObject kobj, 
			Set<KResourceAssessment> result) {
		if (node instanceof LearningActNode) {
			LearningActNode act = (LearningActNode) node;
			AssociatableResource r = act.getAssociated();
			if (r instanceof PhysicalLocation) {
			} else if (r instanceof KResourceAssessment) {
				result.add((KResourceAssessment) r);
			} else if (r instanceof KProduct) {
				KProduct nestedKObj = (KProduct) r;
				for (Node nestedNode : nestedKObj.getExecutionModel()
						.getxGraph().getNodes()) {
					traverseForAssessmentResources(nestedNode, nestedKObj, result);
				}
			}

		} else if (node instanceof ParXGroup) {
			ParXGroup p = (ParXGroup) node;
			for (Node np : p.getNodes()) {
				traverseForAssessmentResources(np, kobj, result);
			}
		}
	}
		
	
	private Set<Node> getStartOfNodes(Node n, KObject kobj) {
		Set<Node> result = new HashSet<Node>();
		for(Edge edge : n.getIsEndOf()) {
			for(Node node : kobj.getExecutionModel().getxGraph().getNodes()) {
				Set<Edge> edges = node.getIsStartOf();
				for(Edge e : edges) {
					if(e.getName().equals(edge.getName())) {
						result.add(node);
					}
				}
			}
		}
		// 
		return result;
	}
}