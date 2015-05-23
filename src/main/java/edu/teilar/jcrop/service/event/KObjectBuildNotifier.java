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

package edu.teilar.jcrop.service.event;

import org.junit.Assert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;

import edu.teilar.jcrop.domain.builder.graph.KObjectBuilderCreator;
import edu.teilar.jcrop.domain.builder.resource.KObjectBuilder;
import edu.teilar.jcrop.domain.director.KObjectDirector;
import edu.teilar.jcrop.domain.resource.KObject;
import edu.teilar.jcrop.owl.CROPOntologyController;

/**
 * To receive the KObjectBuildEvent
 * 
 * One the begining of the app, there is no need to wait and to build all available
 * kobjects. This notifier will call for the building of a kobject (xmodel, xgraph) 
 * upon request. 
 * 
 * @version 0.1 2014
 * @author Maria Tsiakmaki
 */
public class KObjectBuildNotifier implements ApplicationListener<KObjectBuildEvent> {

	@Autowired
	private CROPOntologyController controller;
	
	@Override
	public void onApplicationEvent(KObjectBuildEvent event) {
		String kobjName = event.getKObjectName();
		KObjectBuilderCreator builderCreator = 
				new KObjectBuilderCreator(controller);
		KObjectDirector director = new KObjectDirector();
		KObject kobj = controller.getLearningObjectByName(kobjName);
		KObjectBuilder builder = builderCreator.createBuilder(kobj);
		kobj = director.createLearningObject(builder, kobjName);
	
		Assert.assertNotNull("kobj should have xmodel", kobj.getExecutionModel());
		Assert.assertNotNull("xmodel should have xgraph", kobj.getExecutionModel().getxGraph());
	}

}
