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
import java.util.ArrayList;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;

import edu.teilar.jcrop.domain.builder.graph.KObjectBuilderCreator;
import edu.teilar.jcrop.domain.builder.resource.KObjectBuilder;
import edu.teilar.jcrop.domain.director.KObjectDirector;
import edu.teilar.jcrop.domain.graph.XGraph;
import edu.teilar.jcrop.domain.graph.group.ParXGroup;
import edu.teilar.jcrop.domain.graph.node.ControlNode;
import edu.teilar.jcrop.domain.graph.node.DialogueNode;
import edu.teilar.jcrop.domain.graph.node.LearningActNode;
import edu.teilar.jcrop.domain.graph.node.Node;
import edu.teilar.jcrop.domain.resource.AssociatableResource;
import edu.teilar.jcrop.domain.resource.KObject;
import edu.teilar.jcrop.domain.resource.PhysicalLocation;
import edu.teilar.jcrop.iterator.BreadthFirstXGraphIterator;
import edu.teilar.jcrop.owl.CROPOntologyController;
import edu.teilar.jcrop.service.client.KObjectAsTraversable;
import edu.teilar.jcrop.service.traverse.KObjectBreadthFirstTraverseOfXGraph;

public class BreadthFirstNodeIteratorTest {

	/**
	 * 
	 */
	private File cropOntologyFolder = 
			new File("C:\\git\\java\\jcrop-core\\src\\test\\resources\\crop-projects\\complex");

	
	private String kobjName = "complex";

	private CROPOntologyController controller = 
			new CROPOntologyController(cropOntologyFolder); 
	
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
		KObjectBreadthFirstTraverseOfXGraph b = 
				new KObjectBreadthFirstTraverseOfXGraph();
		
		ArrayList<KObjectAsTraversable> l = b.traverse(p);
		for (KObjectAsTraversable t : l) {
			if(t.getType().equals("kobject")) {
				System.out.println("name: " + t.getName() + " kobj: " + 
					t.getKobject() + " type: " + t.getType() + 
					" status: " + t.getStatus());
			} else {
				System.out.println("\tname: " + t.getName() + " kobj: " + 
						t.getKobject() + " type: " + t.getType() + 
						" status: " + t.getStatus());
			}
		}
		System.out.println(l.size());
	}
	
	//@Test
	public void testBreadthFirstIterator1() {
		System.out.println("testBreadthFirstIterator");
		
		ArrayList<KObjectAsTraversable> l = new ArrayList<KObjectAsTraversable>();
		traverseKObject(p, p.getExecutionModel().getxGraph(), l);
		//System.out.println(l);
	}
	
	private void printNodes(Set<Node> nodes, String kobj) {
		System.out.println("\tkobj: " + kobj + " start");
		for (Node n: nodes) {
			System.out.println("\t"+n.getName() + " " + n.getClass());
		}
		System.out.println("\tkobj: " + kobj + " end");
	} 
	
	private void traverseKObject(KObject kobj, XGraph g, ArrayList<KObjectAsTraversable> l) {
		//System.out.println("\nTraversing " + kobj.getName() + " with root nodes:");
		BreadthFirstXGraphIterator i = new BreadthFirstXGraphIterator(g);
		
		//printRootNodes(i.getRootNodes());
		if(!(g instanceof ParXGroup))
			l.add(new KObjectAsTraversable(kobj.getName(), kobj.getName(), "kobject", "begin"));

		for (; i.hasNext();) {
			Node o = i.next();
			printNodes(i.getSeen(), kobj.getName());

			if(o instanceof LearningActNode) {
				LearningActNode act = (LearningActNode)o;
				AssociatableResource r = act.getAssociated(); 
				System.out.println("From: " + kobj.getName() + " act: " + act.getName());
				l.add(new KObjectAsTraversable(act.getName(), kobj.getName(), "act", "begin"));
				
				if(r instanceof PhysicalLocation) {
					// end of 
					l.add(new KObjectAsTraversable(r.getName(), kobj.getName(), "physicalLocation", "in"));
					System.out.println("From: " + kobj.getName() + " physLoc: " + r.getName());
				} else if(r instanceof KObject) { 
					KObject nested = (KObject)r;
					traverseKObject(nested, nested.getExecutionModel().getxGraph(), l);
				}
				
				l.add(new KObjectAsTraversable(act.getName(), kobj.getName(), "act", "end"));

			} else if (o instanceof ParXGroup) {
				ParXGroup p = (ParXGroup)o;
				System.out.println("From: " + kobj.getName() + " pargroup: " + p.getName());
				l.add(new KObjectAsTraversable(p.getName(), kobj.getName(), "pargroup", "begin"));
				traverseKObject(kobj, p, l);
				l.add(new KObjectAsTraversable(p.getName(), kobj.getName(), "pargroup", "end"));
			} else if (o instanceof ControlNode) {
				System.out.println("From: " + kobj.getName() + " control: " +o.getName());
				l.add(new KObjectAsTraversable(o.getName(), kobj.getName(), "control", "in"));
			} else if (o instanceof DialogueNode) {
				System.out.println("From: " + kobj.getName() + " dialogue: " +o.getName());
				l.add(new KObjectAsTraversable(o.getName(), kobj.getName(), "dialog", "in"));
			} else {
				System.out.println("From: " + kobj.getName() + " xNode: " + o.getName());
				l.add(new KObjectAsTraversable(o.getName(), kobj.getName(), "unknown", "in"));
			}
		}
		
		if(!(g instanceof ParXGroup))
			l.add(new KObjectAsTraversable(kobj.getName(), kobj.getName(), "kobject", "end"));
	}
		
	
}
