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
package edu.teilar.jcrop.service.rest;

/**
 * @author m.tsiakmaki
 *
 */
public class ApiUrls {
	
	/** root of restful services */
	public static final String ROOT_KOBJECTS_URL = "/rest";
	
	/** list of all concepts */
	public static final String CONCEPTS_URL = "/concepts";
	
	/** list of all kobjects with target concept */
	public static final String KOBJECTS_CONCEPT_URL = "/concepts/{concept}/kobjects";
	
	/** details of a concepts, i.e. name, prerequisites */
	public static final String CONCEPT_URL = "/concepts/{concept}";

	/** list of all kobjects */
	public static final String KOBJECTS_URL = "/kobjects";
	
	/** details of a kobject */
	public static final String KOBJECT_URL = "/kobjects/{kobj}";

	/** the xmodels of a kobject */
	public static final String KOBJECT_XMODELS_URL = "/kobjects/{kobj}/xmodels";
	
	/** the xgraph of a kobject */
	public static final String KOBJECT_XMODELS_XGRAPH_URL = 
			"/kobjects/{kobj}/xmodels/{xmodel}/xgraph";
	
	/** the xnodes of the xgraph of a kobject */
	public static final String KOBJECT_XMODELS_XGRAPH_XNODES_URL = 
			"/kobjects/{kobj}/xmodels/{xmodel}/xgraph/xnodes";
	
	/** details of an xnode of the xgraph of a kobject */
	public static final String KOBJECT_XMODELS_XGRAPH_XNODE_URL = 
			"/kobjects/{kobj}/xmodels/{xmodel}/xgraph/xnodes/{xnode}";
	
	/** the svg image of the xgraph of a kobject */
	public static final String KOBJECT_XMODELS_XGRAPH_SVG_URL = 
			"/kobjects/{kobj}/xmodels/{xmodel}/xgraph/svg";
	
	/** the breadth first traversal list of the xgraph of a kobject */
	public static final String KOBJECT_XMODELS_XGRAPH_BREADTHFIRST_URL = 
			"/kobjects/{kobj}/xmodels/{xmodel}/xgraph/breadthfirst";
	
	/** resource of a kobject */
	public static final String KOBJECT_RESOURCE_SUPPORT_URL = 
			"/kobjects/{kobj}/resource/support";
	
	/** resource of a kobject */
	public static final String KOBJECT_RESOURCE_ASSESSMENT_URL = 
			"/kobjects/{kobj}/resource/assessment";
}
