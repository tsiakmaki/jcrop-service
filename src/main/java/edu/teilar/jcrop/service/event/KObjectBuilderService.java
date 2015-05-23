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

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationEventPublisherAware;
/**
 * Event handling in the ApplicationContext is provided through the ApplicationEvent 
 * class and ApplicationListener interface. If a bean that implements the 
 * ApplicationListener interface is deployed into the context, every time an 
 * ApplicationEvent gets published to the ApplicationContext, 
 * that bean is notified. 
 * http://docs.spring.io/spring/docs/4.0.0.RELEASE/spring-framework-reference/htmlsingle/#context-introduction
 * 
 * To publish a custom ApplicationEvent, call the publishEvent() 
 * method on an ApplicationEventPublisher.
 * 
 * @version 0.1 2014
 * @author Maria Tsiakmaki
 */
public class KObjectBuilderService implements ApplicationEventPublisherAware {

	private ApplicationEventPublisher publisher;
	
	@Override
	public void setApplicationEventPublisher(ApplicationEventPublisher publisher) {
		  this.publisher = publisher;
	}

	
	public void buildKObject(String kobjectName) {
		KObjectBuildEvent event = new KObjectBuildEvent(this, kobjectName);
		publisher.publishEvent(event);
    }
	
}
