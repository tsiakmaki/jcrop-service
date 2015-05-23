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

package edu.teilar.jcrop.service.util;

import java.net.URI;

import org.junit.Test;
import org.semanticweb.owlapi.io.XMLUtils;
import org.semanticweb.owlapi.model.IRI;

public class UriTest {

	@Test
	public void test() {
		// ""http://www.cs.teilar.gr/ontologies/XGraph.owl#1_ControlNode_Default_cns_ControlNode
		URI uri = URI.create("http://www.cs.teilar.gr/ontologies/XGraph.owl#1_ControlNode_Default_cns_ControlNode");
		System.out.println(uri.getFragment());
		
		IRI iri = IRI.create("http://www.cs.teilar.gr/ontologies/XGraph.owl#1_ControlNode_Default_cns_ControlNode");
		System.out.println(iri.getFragment());
		
		IRI iri1 = IRI.create("http://www.cs.teilar.gr/ontologies/XGraph.owl#", "1_ControlNode_Default_cns_ControlNode"); 
		System.out.println(iri1.getFragment());
		getNCNameSuffixIndex(iri);
	}

	public static int getNCNameSuffixIndex(CharSequence s) {
        // identify bnode labels and do not try to split them
        if (s.length() > 1 && s.charAt(0) == '_' && s.charAt(1) == ':') {
        	
            return -1;
        }
        int index = -1;
        for (int i = s.length() - 1; i > -1; i--) {
            if (!Character.isLowSurrogate(s.charAt(i))) {
            	System.out.println("2 " + s.charAt(i));
                int codePoint = Character.codePointAt(s, i);
                if (XMLUtils.isNCNameStartChar(codePoint)) {
                	System.out.println("3 " + s.charAt(i));
                    index = i;
                }
                if (!XMLUtils.isNCNameChar(codePoint)) {
                	System.out.println("4 " +s.charAt(i));
                    break;
                }
            }
        }
        System.out.println(index + " " + s.charAt(index));
        return index;
    }
}
