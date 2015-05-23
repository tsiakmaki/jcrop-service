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

import java.util.Locale;
import java.util.ResourceBundle;

import org.junit.Test;

public class ResourceBundleTest {
	@Test
	public void test() {
		
		String l = System.getProperty("os.name").toLowerCase().startsWith("win") ?  
				"win" : "linux";
	
		
		ResourceBundle bundle = ResourceBundle.getBundle("service.properties", new Locale("","",l));
		
		
		System.out.println("\n\n" + bundle.getString("cropfile"));
		System.out.println("\n\n" + bundle.getBundle("cropfile"));
	}
}
