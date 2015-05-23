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

public class ClientUtils {

	/** never thought that this will be needed */
	public static String cleanUpSvgString(String svg) {
		if(svg == null) {
			return ""; 
		}
		
		String xml = "\"<?xml version=\\\"1.0\\\" encoding=\\\"UTF-16\\\"?>\\n";
		if(svg.startsWith(xml)) {
			int len = xml.length();
			svg = svg.substring(len);
		}
		svg = svg.substring(0, svg.length()-1);
		svg = svg.replaceAll("\\\\\\\"", "\"");
		
		return svg;
	}
	
}
