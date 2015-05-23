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

import java.io.File;
import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScan.Filter;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import edu.teilar.jcrop.owl.CROPOntologyController;
import edu.teilar.jcrop.service.event.KObjectBuildNotifier;
import edu.teilar.jcrop.service.event.KObjectBuilderService;
import edu.teilar.jcrop.service.traverse.KObjectBreadthFirstTraverseOfXGraph;

@Configuration
@ComponentScan(basePackages={"edu.teilar.jcrop.service.client"},
	excludeFilters={@Filter(type=FilterType.ANNOTATION, value=EnableWebMvc.class)})
@PropertySource("app.properties")
public class RootConfig {
	
	@Autowired
	Environment env; 
	
	@Bean
	public CROPOntologyController controller() throws IOException {
		String os = System.getProperty("os.name").toLowerCase().startsWith("win") ?  
				"win" : "linux";
		String cropfile = env.getProperty("cropfile_" + os);
		return new CROPOntologyController(new File(cropfile));
	}
	
	/**
	 * Calls the publishEvent() method on an ApplicationEventPublisher
	 * in order to create a KObjectBuildEvent 
	 */
	@Bean
	public KObjectBuilderService kobjectBuilderService() {
		return new KObjectBuilderService();
	}
	
	/** 
	 * KObjectBuildNotifier is a listener of KObjectBuildEvent
	 * It will call the kobject director to build a kobject
	 * 
	 * One the begining of the app, there is no need to wait and to build all available
	 * kobjects. This notifier will call for the building of a kobject (xmodel, xgraph) 
	 * upon request. 
	 *  
	 * */
	@Bean
	public KObjectBuildNotifier kobjectBuildNotifier() {
		return new KObjectBuildNotifier();
	}

	/**
	 * 
	 * 
	 */
	@Bean
	public KObjectBreadthFirstTraverseOfXGraph breadthFirstTraverseOfXGraph() {
		return new KObjectBreadthFirstTraverseOfXGraph();
	}
}
