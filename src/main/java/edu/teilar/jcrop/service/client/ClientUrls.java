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

package edu.teilar.jcrop.service.client;

public class ClientUrls {

	public static final String SERVERADDRESS  = "http://localhost:8080/rest";
	
	public static final String REST_SERVICE_ADDRESS  = "http://localhost:8080";
	
	/** root of restful services */
	public static final String ROOT_CLIENT_URL = "/web";
	
	/** list of all kobjects */
	public static final String CLIENT_KOBJECTS_URL = "/kobjects";
	
	/** details of a kobject */
	public static final String CLIENT_KOBJECT_URL = "/kobjects/{kobj}";
	
	/** execute of a kobject */
	public static final String CLIENT_KOBJECT_KORDER_URL = "/kobjects/{kobj}/korder";
	
	/** list of all concepts */
	public static final String CLIENT_CONCEPTS_URL = "/concepts";
	
	/** list of all kobjects with target concept */
	public static final String CLIENT_KOBJECTS_CONCEPT_URL = "/concepts/{concept}/kobjects";
	
	/** details of a concepts, i.e. name, prerequisites */
	public static final String CLIENT_CONCEPT_URL = "/concepts/{concept}";


}
