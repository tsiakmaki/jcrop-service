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

import org.springframework.context.ApplicationEvent;

/**
 * 
 * Event in order a kobject to be fully build 
 * When controller is up, the list of all learnign objects is build
 * but the objects are built till their prerequisites. 
 * (no need to build their xmodels from the beginning)   
 * 
 * @version 0.1 2010
 * @author Maria Tsiakmaki
 */
public class KObjectBuildEvent extends ApplicationEvent {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1662234532952663814L;
	
	private String kobjName; 
	
	public String getKObjectName() {
		return this.kobjName;
	}
	
	public KObjectBuildEvent(Object source, String kobjName) {
		super(source);
		this.kobjName = kobjName;
	}

	

}
