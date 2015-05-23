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

package edu.teilar.jcrop.service.traverse;

import java.util.ArrayList;

import org.junit.Assert;
import org.springframework.beans.factory.annotation.Autowired;

import edu.teilar.jcrop.domain.graph.XGraph;
import edu.teilar.jcrop.domain.graph.group.ParXGroup;
import edu.teilar.jcrop.domain.graph.node.ControlNode;
import edu.teilar.jcrop.domain.graph.node.DialogueNode;
import edu.teilar.jcrop.domain.graph.node.LearningActNode;
import edu.teilar.jcrop.domain.graph.node.Node;
import edu.teilar.jcrop.domain.resource.AssociatableResource;
import edu.teilar.jcrop.domain.resource.KObject;
import edu.teilar.jcrop.domain.resource.KResourceAssessment;
import edu.teilar.jcrop.domain.resource.KResourceSupport;
import edu.teilar.jcrop.domain.resource.PhysicalLocation;
import edu.teilar.jcrop.iterator.BreadthFirstXGraphIterator;
import edu.teilar.jcrop.service.client.KObjectAsTraversable;
import edu.teilar.jcrop.service.event.KObjectBuilderService; 
/**
 * 
 * KObjectBreadthFirstTraverseOfXGraph 
 * traverse the xgraph of the kobject by breadthfirst, and with 
 * respect to the prerequisite restriction 
 * 
 * also, if a kobject is not yet build, calls the 
 * kobjectBuilderService (Observer) to aware about its build. 
 * 
 * 
 * @version 0.1 2014
 * @author Maria Tsiakmaki
 */
public class KObjectBreadthFirstTraverseOfXGraph {

	@Autowired
	private KObjectBuilderService kobjectBuilderService; 
	
	public KObjectBreadthFirstTraverseOfXGraph() {
		
	}
	
	
	public ArrayList<KObjectAsTraversable> traverse(KObject kobj) {
		ArrayList<KObjectAsTraversable> l = new ArrayList<KObjectAsTraversable>();
		
		if(kobj.getExecutionModel() == null) {
			kobjectBuilderService.buildKObject(kobj.getName());
		}
		Assert.assertNotNull("kobj should have xmodel", kobj.getExecutionModel());
		Assert.assertNotNull("xmodel should have xgraph", kobj.getExecutionModel().getxGraph());
		
		breadthFirstTraverse(kobj, kobj.getExecutionModel().getxGraph(), l);
		return l; 
	}
	
	
	private void breadthFirstTraverse(KObject kobj, XGraph g, ArrayList<KObjectAsTraversable> l) {
		
		BreadthFirstXGraphIterator i = new BreadthFirstXGraphIterator(g);
		
		if(!(g instanceof ParXGroup)) {
			l.add(new KObjectAsTraversable(kobj.getName(), kobj.getName(), "kobject", "begin"));
			l.add(new KObjectAsTraversable(kobj.getName(), kobj.getName(), "kobject", "in"));
		}
		
		for (; i.hasNext();) {
			Node o = i.next();

			if(o instanceof LearningActNode) {
				LearningActNode act = (LearningActNode)o;
				AssociatableResource r = act.getAssociated(); 
				l.add(new KObjectAsTraversable(act.getName(), kobj.getName(), "act", "begin"));
				l.add(new KObjectAsTraversable(act.getName(), kobj.getName(), "act", "in"));
				if(r instanceof PhysicalLocation) {
					if(kobj instanceof KResourceSupport) {
						l.add(new KObjectAsTraversable(r.getName(), kobj.getName(), "support", "begin"));
						l.add(new KObjectAsTraversable(r.getName(), kobj.getName(), "support", "in"));
						l.add(new KObjectAsTraversable(r.getName(), kobj.getName(), "support", "end"));
					} else if(kobj instanceof KResourceAssessment) {
						l.add(new KObjectAsTraversable(r.getName(), kobj.getName(), "assessment", "begin"));
						l.add(new KObjectAsTraversable(r.getName(), kobj.getName(), "assessment", "in"));
						l.add(new KObjectAsTraversable(r.getName(), kobj.getName(), "assessment", "end"));
					}
				} else if(r instanceof KObject) { 
					KObject nested = (KObject)r;
					if(nested.getExecutionModel() == null) {
						kobjectBuilderService.buildKObject(nested.getName());
					}
					breadthFirstTraverse(nested, nested.getExecutionModel().getxGraph(), l);
				}
				l.add(new KObjectAsTraversable(act.getName(), kobj.getName(), "act", "end"));
			} else if (o instanceof ParXGroup) {
				ParXGroup p = (ParXGroup)o;
				l.add(new KObjectAsTraversable(p.getName(), kobj.getName(), "pargroup", "begin"));
				l.add(new KObjectAsTraversable(p.getName(), kobj.getName(), "pargroup", "in"));
				breadthFirstTraverse(kobj, p, l);
				l.add(new KObjectAsTraversable(p.getName(), kobj.getName(), "pargroup", "end"));
			} else if (o instanceof ControlNode) {
				l.add(new KObjectAsTraversable(o.getName(), kobj.getName(), "control", "begin"));
				l.add(new KObjectAsTraversable(o.getName(), kobj.getName(), "control", "in"));
				l.add(new KObjectAsTraversable(o.getName(), kobj.getName(), "control", "end"));
			} else if (o instanceof DialogueNode) {
				l.add(new KObjectAsTraversable(o.getName(), kobj.getName(), "dialog", "begin"));
				l.add(new KObjectAsTraversable(o.getName(), kobj.getName(), "dialog", "in"));
				l.add(new KObjectAsTraversable(o.getName(), kobj.getName(), "dialog", "end"));
			} else {
				Assert.assertTrue("Could not find a nice type of node : " +
						o.getName() + " " + o.getClass() , false);
			}
		}
		
		if(!(g instanceof ParXGroup))
			l.add(new KObjectAsTraversable(kobj.getName(), kobj.getName(), "kobject", "end"));
	}
	
	
}
