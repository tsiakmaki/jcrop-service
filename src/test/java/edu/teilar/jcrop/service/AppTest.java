package edu.teilar.jcrop.service;
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

import java.io.File;
import java.io.IOException;
import java.util.Iterator;

import org.jgrapht.Graph;
import org.junit.Assert;
import org.junit.Test;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import edu.teilar.jcrop.domain.graph.XGraph;
import edu.teilar.jcrop.domain.graph.node.XNode;
import edu.teilar.jcrop.iterator.BreadthFirstXGraphIterator;
import edu.teilar.jcropeditor.domain.GroupXNode;
import edu.teilar.jcropeditor.domain.XEdge;
import edu.teilar.jcropeditor.domain.XNodeCreator;
import edu.teilar.jcropeditor.view.ExecutionGraph;

public class AppTest {

	@Test
	public void testTraverse() {
		  
		String kobj = "c1";
		String n = "Default_c1_XGraph";
		
		File f = new File("C:/dev/LearningObjects/c1");

		if (!f.exists()) {
			Assert.assertTrue("Cannot procceed test", true);
			return;
		}

		//OntologySynchronizer sync = new OntologySynchronizer(f, false);

		
		
//		XNodeCreator creator = new XNodeCreator();
//		String target = "todo"; //factory.getTargetConceptOfKObject(kobj);
//		
//		XGraph g = creator.makeGraph(n, kobj, target);
//		creator.createGraph(g, kobj, true);
//		
//		traverseKObject(g);
//		System.out.println("Edge Map Size" + g.getEdgeMap().size());
//		System.out.println("Edge Map Size from spe:"  + g.getSpecifics().getEdgeMap().size());
//		System.out.println("Vertex Map Size" + g.getSpecifics().getVertexMapDirected().size());
//		System.out.println("\n\n");
//		  
//		ObjectMapper mapper = new ObjectMapper();
//		//mapper.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);
//		// for all types specified (for no-args, "Object.class" and all non-final classes), 
//		// Java class name information is included, 
//		// using default inclusion mechanism (additional wrapper array in JSON). 
//		// one of:
//		//mapper.enableDefaultTyping(); // default to using DefaultTyping.OBJECT_AND_NON_CONCRETE
//		// mapper.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);
//		
//		try {
//			File ff = new File("xgraph.json");
//			mapper.writeValue(ff, g);
//			XGraph result = mapper.readValue(ff,  XGraph.class);
//			System.out.println("Edge Map Size" + result.getEdgeMap().size());
//			System.out.println("Edge Map Size from spe:"  + result.getSpecifics().getEdgeMap().size());
//			System.out.println("Vertex Map Size" + result.getSpecifics().getVertexMapDirected().size());
//			traverseKObject(result); 
//			Assert.assertEquals(g, result);
//			
//			
//			for (XNode x : result.getSpecifics().allVertexes()) {
//				System.out.println("\n\n");
//				traverseKObject((XGraph)x); 
//			}
//			
//		} catch (JsonGenerationException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} catch (JsonMappingException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		
	}
	
	
	
	private void traverseKObject(Graph<XNode, XEdge> g) {
		
//		Iterator<XNode> i = new BreadthFirstXGraphIterator((XGraph)g);
//		for (;i.hasNext();) {
//			XNode o = i.next();
//			if (o.type() == ExecutionGraph.XGRAGH_XNODE_TYPE) {
//				traverseKObject((XGraph)o);
//			} else if (o.type() == ExecutionGraph.PAR_GROUP_XNODE_TYPE) {
//				traverseKObject((GroupXNode)o);
//			} else {
//				System.out.println(o.getName() + " of " + o.getkObjectName());
//			} 
//		}
	}
}
