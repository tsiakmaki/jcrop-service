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

import java.io.IOException;
import java.io.StringWriter;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.eclipse.mylyn.wikitext.core.parser.MarkupParser;
import org.eclipse.mylyn.wikitext.core.parser.builder.HtmlDocumentBuilder;
import org.eclipse.mylyn.wikitext.mediawiki.core.MediaWikiLanguage;

public class MediaMarkUp {

	public static void main(String[] args) {
		
		String filepath = "C:\\git\\java\\jcrop-core\\src\\test\\resources\\crop-projects\\jcropeditor\\resources\\ContentOntologyBuildingSteps.txt";
		try {
			byte[] encoded = Files.readAllBytes(Paths.get(filepath));
			
			StringWriter writer = new StringWriter();
			HtmlDocumentBuilder builder = new HtmlDocumentBuilder(writer);
			// avoid the <html> and <body> tags 
			builder.setEmitAsDocument(false);
			MarkupParser parser = new MarkupParser(new MediaWikiLanguage());
			parser.setBuilder(builder);
			parser.parse(new String(encoded, StandardCharsets.UTF_8));
			// 
			System.out.println(writer.toString() );
			
			
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
	}
}
