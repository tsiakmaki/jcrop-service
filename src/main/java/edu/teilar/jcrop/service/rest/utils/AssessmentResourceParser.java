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
package edu.teilar.jcrop.service.rest.utils;

import java.util.List;

import edu.teilar.jcropeditor.util.MultipleChoiceQuiz;

public class AssessmentResourceParser {

	public AssessmentResourceParser() {

	}

	public String parse(List<?> quiz) {

		StringBuffer sb = new StringBuffer("<h2>Multiple Choice Quiz</h2>");

		sb.append("<form id=\"quiz\">");
		for (int i = 0; i < quiz.size(); i++) {
			if (quiz.get(i) instanceof MultipleChoiceQuiz) {
				MultipleChoiceQuiz q = (MultipleChoiceQuiz) quiz.get(i);
				
				sb.append("<label for=\"q_" + i + "\">" + (i + 1) + ". " + q.getQuery() + "<span class=\"glyphicon \" id=\"q_mark_" + i + "\"/></label>");
				// <label for="exampleInputEmail1">Email address</label>
				for (String a : q.getAnswers()) {
					sb.append("<div class=\"radio\">");
					sb.append("<label>");
					
					/*
					 <div class="radio">
						  <label>
						    <input type="radio" name="optionsRadios" id="optionsRadios1" value="option1" checked>
						    Option one is this and that&mdash;be sure to include why it's great
						  </label>
						</div>
					 
					 */
					sb.append("<input type=\"radio\" name=\""+"q_" + i +"\" id=\""+"q_" + i +"\" value=\"" + a + "\" >");
					sb.append(a);
					sb.append("</label>");
					sb.append("</div>");
				}
				// this is soooo not good, just for now...  
				sb.append("<input type=\"hidden\" name=\"answer_" + i + "\" value=\"" + q.getCorrectAnswer() + "\" />");
				

//				<div class="radio">
//				  <label>
//				    <input type="radio" name="optionsRadios" id="optionsRadios1" value="option1" checked>
//				    Option one is this and that&mdash;be sure to include why it's great
//				  </label>
//				</div>
				
//				sb.append("<input type=hidden name=answer_" + (i + 1)
//						+ " value=" + q.getCorrectAnswer() + " />");
//
//				for (String a : q.getHints()) {
//					sb.append("<input type=hidden name=hint_" + (i + 1)
//							+ " value=" + a + " />");
//				}
//
//				sb.append("<span id=result_" + (i + 1) + "></span>");

				//sb.append("<br/>");
			}

		}
		//<a class="btn btn-default" href="/web/kobjects/${kobject.name}/korder">Start Course</a>
		sb.append("<a class=\"btn btn-default\" onclick=\"javascript:quizresults()\" id=\"quizsubmit\">Submit</a><span id=\"total_result\"></span></form>");
		
		// sb.append("<button id='quizsubmit' type='submit' class='btn btn-default'>Get Results</button></form>");

		//sb.append("</div>");

		return sb.toString();

	}
}
