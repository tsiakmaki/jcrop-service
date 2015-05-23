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
package edu.teilar.jcrop.service;

//import org.springframework.hateoas.Resource;
//import org.springframework.hateoas.Link;
//import org.springframework.hateoas.ResourceAssembler;
//import org.springframework.hateoas.config.EnableHypermediaSupport;
//import org.springframework.hateoas.mvc.ResourceAssemblerSupport;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;

/**
 * @author m.tsiakmaki
 *
*/
@ComponentScan
@EnableAutoConfiguration
public class Application {
	
	public static void main(String[] args) {
		SpringApplication.run(Application.class);
	}
	
	
	//ResourceAssemblerSupport it is impossible to marshal list of HATEOAS resources 
//	@Bean
//    ResourceAssembler<KObject, Resource<KObject>> kobjectResourceAssembler() {
//		Collection<Link> links = new ArrayList<Link>();
//		KObject kobj = new KProduct();
//		ResourceAssemblerSupport result = new 
//				ResourceAssemblerSupport(KObjectController.class, KObject.class);
//		
//        return (k) -> {
//            try {
//                String xmodelRel = "xmodels", xgraphRel = "xgraphs";
//                KObject kobj = new KProduct();
//                kobj.setName(k.getName());
//                
//                String kobjName = kobj.getName();
//                Collection<Link> links = new ArrayList<Link>();
//                links.add(linkTo(methodOn(KObjectController.class).getKObject(kobjName)).withSelfRel());
//                links.add(linkTo(methodOn(KObjectController.class).getKObjectXModels(kobjName)).withRel(xgraphRel));
//                return new Resource(kobj, links);
//            } catch (Exception e) {
//                throw new RuntimeException(e);
//            }
//        };
//    }
	
}
