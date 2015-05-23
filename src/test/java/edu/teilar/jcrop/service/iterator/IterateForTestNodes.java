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

package edu.teilar.jcrop.service.iterator;

import java.io.File;
import java.util.HashSet;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;

import edu.teilar.jcrop.domain.builder.graph.KObjectBuilderCreator;
import edu.teilar.jcrop.domain.builder.resource.KObjectBuilder;
import edu.teilar.jcrop.domain.director.KObjectDirector;
import edu.teilar.jcrop.domain.graph.edge.Edge;
import edu.teilar.jcrop.domain.graph.group.ParXGroup;
import edu.teilar.jcrop.domain.graph.node.ControlNode;
import edu.teilar.jcrop.domain.graph.node.DialogueNode;
import edu.teilar.jcrop.domain.graph.node.LearningActNode;
import edu.teilar.jcrop.domain.graph.node.Node;
import edu.teilar.jcrop.domain.resource.AssociatableResource;
import edu.teilar.jcrop.domain.resource.KObject;
import edu.teilar.jcrop.domain.resource.KProduct;
import edu.teilar.jcrop.domain.resource.KResourceAssessment;
import edu.teilar.jcrop.domain.resource.KResourceSupport;
import edu.teilar.jcrop.domain.resource.PhysicalLocation;
import edu.teilar.jcrop.owl.CROPOntologyController;
import edu.teilar.jcrop.service.client.KObjectAsTraversable;

public class IterateForTestNodes {

	/**
	 * 
	 */
	private File cropOntologyFolder = 
			new File("C:\\git\\java\\jcrop-core\\src\\test\\resources\\crop-projects\\jcropeditor");

	
	private String kobjName = "jcropeditor";

	private CROPOntologyController controller = new CROPOntologyController(cropOntologyFolder); 
	
	private KObject p; 
	
	@Before
	public void setUp() {
		
		KObjectBuilderCreator builderCreator = 
				new KObjectBuilderCreator(controller);
		KObjectDirector director = new KObjectDirector();
		
		for(KObject kobj : controller.getKObjects().values()) {
			KObjectBuilder builder = builderCreator.createBuilder(kobj);
			director.createLearningObject(builder, kobj.getName());
		}
		
		p = controller.getLearningObjectByName(kobjName);
	}
	
	@Test
	public void test() {
		
		String controlNode = "Control_1";
		
		Node control = p.getExecutionModel().getxGraph().getNodeByName(controlNode);
		
		Set<KResourceAssessment> result = new HashSet<KResourceAssessment>();
		
		Set<Node> nodes =  getStartOfNodes(control, p);
		
		for(Node n : nodes) {
			
			iter(n, p, result);
		}
	
		for(KResourceAssessment a : result) {
			System.out.println(a.getName());
		}
	}

	private Set<Node> getStartOfNodes(Node n, KObject kobj) {
		Set<Node> result = new HashSet<Node>();
		
		for(Edge edge : n.getIsEndOf()) {
			for(Node node : p.getExecutionModel().getxGraph().getNodes()) {
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
 	
	private void iter(Node node, KObject kobj, Set<KResourceAssessment> result) {
			
		if(node instanceof LearningActNode) {
			
			LearningActNode act = (LearningActNode)node;
			AssociatableResource r = act.getAssociated(); 
	//				System.out.println("From: " + kobj.getName() + " act: " + act.getName());
	//				l.add(new KObjectAsTraversable(act.getName(), kobj.getName(), "act", "begin"));
	//				
			if(r instanceof PhysicalLocation) {
	//					// end of 
	//					l.add(new KObjectAsTraversable(r.getName(), kobj.getName(), "physicalLocation", "in"));
	//					System.out.println("From: " + kobj.getName() + " physLoc: " + r.getName());
			} else if(r instanceof KResourceAssessment) { 
				result.add((KResourceAssessment)r);
	//					KObject nested = (KObject)r;
	//					traverseKObject(nested, nested.getExecutionModel().getxGraph(), l);
			} else if(r instanceof KProduct) { 
				KProduct nestedKObj = (KProduct)r;
				for(Node nestedNode : nestedKObj.getExecutionModel().getxGraph().getNodes()) {
					iter(nestedNode, nestedKObj, result);
				}
			}
	//				
	//				l.add(new KObjectAsTraversable(act.getName(), kobj.getName(), "act", "end"));
	
		} else if (node instanceof ParXGroup) {
			
					ParXGroup p = (ParXGroup)node;
					for(Node np : p.getNodes()) {
						iter(np, kobj, result);
					}
	//				System.out.println("From: " + kobj.getName() + " pargroup: " + p.getName());
	//				l.add(new KObjectAsTraversable(p.getName(), kobj.getName(), "pargroup", "begin"));
	//				traverseKObject(kobj, p, l);
	//				l.add(new KObjectAsTraversable(p.getName(), kobj.getName(), "pargroup", "end"));
		} else if (node instanceof ControlNode) {
	//				System.out.println("From: " + kobj.getName() + " control: " +o.getName());
	//				l.add(new KObjectAsTraversable(o.getName(), kobj.getName(), "control", "in"));
		} else if (node instanceof DialogueNode) {
	//				System.out.println("From: " + kobj.getName() + " dialogue: " +o.getName());
	//				l.add(new KObjectAsTraversable(o.getName(), kobj.getName(), "dialog", "in"));
		} else {
	//				System.out.println("From: " + kobj.getName() + " xNode: " + o.getName());
	//				l.add(new KObjectAsTraversable(o.getName(), kobj.getName(), "unknown", "in"));
		}
	}
		
	
}

