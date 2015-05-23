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
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

/**
 * JCropServiceAppInitializer replaces the historically web.xml 
 * 
 * Configuration of the Servlet Container
 *  
 * Here lies the configuration of jcrop service spring application 
 * context, i.e.: 
 * * the dispatcher  
 * * the spring application's servlet context 
 * 
 * @author m.tsiakmaki
 *
 */
public class JCropServiceAppInitializer extends 
	AbstractAnnotationConfigDispatcherServletInitializer {

	// the ContextLoaderListener
	// i.e. beans for the back end
	@Override
	protected Class<?>[] getRootConfigClasses() {
		return new Class<?>[] { RootConfig.class };
	}
	
	// load application context with beans from RestConfig, WebConfig
	// i.e. web components: controllers, view resolvers, handler mappings
	@Override
	protected Class<?>[] getServletConfigClasses() {
		 return new Class<?>[] {RestConfig.class , WebConfig.class};
	}

	// Paths that the dispatcher will be mapped to  
	@Override
	protected String[] getServletMappings() {
		return new String[] {"/rest", "/web"};
	}

}
