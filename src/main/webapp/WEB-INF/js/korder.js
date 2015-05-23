var Iterator = function(items) {
	this.index = 0;
	this.items = items;
}

var concepts = new Array(); 


Iterator.prototype = {
	first : function() {
		this.reset();
		return this.next();
	},
	next : function() {
		return this.items[this.index++];
	},
	hasNext : function() {
		return this.index <= this.items.length;
	},
	reset : function() {
		this.index = 0;
	},
	each : function(callback) {
		for (var item = this.first(); this.hasNext(); item = this.next()) {
			callback(item);
		}
	}
}
// status
// begin = 1, in = 2, end = 3
// colors
// begin = red, in = orange, end = green 
// can be applied to : pargroup, act, dialog, control
var TraversableHistory = function(mainkobj) {
		this.mainkobj = mainkobj; 
		// first in -> last out 
		// the last in is the current kobj 
		this.kobjsStack = new Array();
		this.items = new Array();
}

TraversableHistory.prototype = {
		
		getIdx: function(traversable) {
			for(i = 0; i<this.items[traversable.kobject].length; i++) {
    		if(this.items[traversable.kobject][i].kobject == traversable.kobject && 
    			 this.items[traversable.kobject][i].name == traversable.name && 
    			 this.items[traversable.kobject][i].type == traversable.type) {
        	return i;
    		}
			}
			return -1;
		},
		
		pushKObj : function(kobj) {
			this.kobjsStack.push(kobj);
		}, 
		
		popKObj : function() {
			this.kobjsStack.pop();
		} ,

		curKObj : function() {
			return this.kobjsStack[this.kobjsStack.length-1];
		},

		curKObjIsMain: function() {
			return this.kobjsStack[this.kobjsStack.length-1] == this.mainkobj;
		},
		
		// traversable is like: 
		// {name: Division, kobject: Div, type: pargroup, status: end}
		add: function(traversable) {
			
			if(!this.items[traversable.kobject]) {
				this.items[traversable.kobject] = new Array();
				Array.prototype.push.call(this.items[traversable.kobject], traversable);
			} else {
				curInd = this.getIdx(traversable);
				if(curInd != -1) {
					this.items[traversable.kobject][curInd].status = traversable.status;
				} else {
					Array.prototype.push.call(this.items[traversable.kobject], traversable);
				}
			}
		},
		
		gather: function(traversable) {
			this.add(traversable);
		}


}

function quizresults() {
	num_of_correct = 0; 
	num_of_wrong = 0; 
	
	$("form input:radio:checked").each(function() { 
		// radio name
		name = this.getAttribute("name"); 

		// question counter 
		name_arr = name.split("_");
		num = name_arr[1];
		
		checked = this.getAttribute("value"); 

		correct = $("[name='answer_" + num + "']").attr("value"); 
		
		if(checked == correct){
			// <span id="q_mark_0">
			$("#q_mark_" + num).addClass("glyphicon-ok").removeClass("glyphicon-remove");
			num_of_correct++; 
		} else {
			$("#q_mark_" + num).addClass("glyphicon-remove").removeClass("glyphicon-ok");
			num_of_wrong++;
		}
	});
	
	if(num_of_correct) {
		result = num_of_correct*1.0/(num_of_correct + num_of_wrong);
	} else {
		result = 0;
	}
	$("#total_result").text('\t Your score is: ' + result.toFixed(2));
	
}

function enableNextButton() {
	$("#nextbutton").removeAttr('disabled');
}

function disableNextButton() {
	$("#nextbutton").attr('disabled', 'disabled');
}

function resetBreadcrumbs() {
	$("ol.breadcrumb").html("<li class='active'>Your Concepts</li>");
}

function pushConcept(data) {
	
	if(concepts.indexOf(data) == -1) {
		concepts.push(data);
		$("ol.breadcrumb").append("<li class='active'>" + data + "</li>");
	}

}

$(document).ready(function() {

			var host = "";
			// main kobj name
			var mainkobj = $('#mainkobj').text();
			// main target of kobj
			var maintarget = $('#maintarget').text();
			/*
			 * traversable structure { 
			 * name: 
			 * kobject: 
			 * type: act | support | assessment | kobject | control | dialog | pargroup 
			 * status: begin | in | end 
			 * }
			 */
			var iter = new Iterator(traversablelist);

			var history = new TraversableHistory(mainkobj);
			

			function updateCurrentKObjectHtmlFields(curkobj) {
				$("#curkobj").html = curkobj.name;
				$("#curtargethref").setAttribute("href", "/web/concepts/" + curkobj.targetEducationalObjective.name);
				$("#curtarget").html = curkobj.targetEducationalObjective.name;
			}
			
			/* */
			function nextResourceUrl(traversable) {
				if (traversable.type == "support"
						|| traversable.type == "assessment") {
					return host + "/rest/kobjects/"
							+ traversable.kobject + "/resource/"
							+ traversable.type;
				} else {
					// not a resource type
					return null;
				}
			}

			/* */
			function nextXNodeUrl(traversable) {
				if (traversable.type == "control"
						|| traversable.type == "dialog") {
					return host + "/rest/kobjects/" 
						+ traversable.kobject 
						+ "/xmodels/default/xgraph/xnodes/"
						+ traversable.name;
				} else {
					// not a resource type
					return null;
				}
			}
			
			/**/
			function nextXNode(traversable) {
				var u = nextXNodeUrl(traversable);
				if (u) {
					// get the resource
					$.ajax({
						url : u,
						type : 'GET',
						dataType : 'json', 
						success : function(data) {
							if(traversable.type=='control') {
								$('#resource').append('<span id="threshold">Current Threshold: ' + data.threshold + '</span>');
							} else if (traversable.type=='dialog') {
								$('#resource').html(data.explanationParagraph);
							}
						},
						error : function(jqXHR, textStatus, errorThrown) {
							console.log(jqXHR);
							console.log(textStatus);
							console.log(errorThrown);
						}
					});
				}
			}
			
			function nextResource(traversable) {
				var u = nextResourceUrl(traversable);
				if (u) {
					// get the resource
					$.ajax({
						url : u,
						type : 'GET',
						dataType : 'json', /*this is obscene, it is json... */
						success : function(data) {
							$('#resource').html(data.text);
						},
						error : function(jqXHR, textStatus, errorThrown) {
							console.log(jqXHR);
							console.log(textStatus);
							console.log(errorThrown);
						}
					});

				}
			}
			
			function nextCurSvgGraph(kobj) {
				//rest/kobjects/{kobj}/xmodels/{xmodel}/xgraph/svg
				u1 = host + "/rest/kobjects/" 
					+ kobj + "/xmodels/default/xgraph/svg";

				var promise = $.ajax({
						url : u1,
						type : 'GET',
						dataType : 'json', 
						success : function(data, extStatus, jqXHR) {
							// ajax asynch
							if(history.curKObj() == data.kobject.name) {
								$("#cursvg").html(data.graph);
								//TODO: from history mark visited
							}
						},
						error : function(jqXHR, textStatus, errorThrown) {
							console.log(jqXHR);
							console.log(textStatus);
							console.log(errorThrown);
							$("#cursvg").addClass("empty");
							$("#cursvg").html("");
						}

					});

				return promise;
			}

			function getColor(status) {
				switch (status) {
				case "begin":
					return "red";
					break;
				case "in":
					return "orange";	
					break;
				case "end":
					return "green";
					break;
				default:
					break;
				}
			}
			
			function color(svgElemId, status) {
				$("#mainsvg #" + svgElemId).each(function() {
					this.setAttribute("stroke", getColor(status));
					this.setAttribute("stroke-opacity", 0.4);
				});
			}

			$("#resetbutton").click(function() {
				iter.reset();
				history = new TraversableHistory(mainkobj);
				enableNextButton();
				resetBreadcrumbs();
				$("#nextbutton").click();
				$("#cursvg").html("");
				$("#cursvg").addClass("empty");
			});
			
			
			// 
			$(nextbutton).click(function() {
				
				var breakwhile = false; 
				
				while (iter.hasNext() && !breakwhile) {
					
					var t = iter.next();
					
					console.log(t);

					history.gather(t);
										
					switch (t.type) {
					case "kobject":

						if(t.status=="begin") {

							history.pushKObj(t.kobject);
							
							if(history.curKObjIsMain()) {
								$("#cursvg").addClass("empty");
								$("#cursvg").html("");
							} else {
								$("#cursvg").removeClass("empty");
								// get svg graph 
								promise = nextCurSvgGraph(t.name);
								promise.done(function(responseData, status, xhr) {
									if(status == 'success') {
										$("#curkobj").html(responseData.kobject.name);
										$("#curtargethref").attr("href", "/web/concepts/" + responseData.kobject.targetEducationalObjective.name);
										$("#curtarget").html(responseData.kobject.targetEducationalObjective.name);
									}
								});
							}

						} else if(t.status=="end") {

							pushConcept($("#curtarget").text());

							history.popKObj(t.kobject);

							if(history.curKObjIsMain()) {
								$("#cursvg").addClass("empty");
								$("#cursvg").html("");

								$("#curkobj").html($("#mainkobj").text());
								$("#curtargethref").attr("href", "/web/concepts/" + $("#curtarget").text());
								$("#curtarget").html($("#curtarget").text());

							} else {
								$("#cursvg").removeClass("empty");
								// get svg graph 
								nextCurSvgGraph(history.curKObj());
							}

						}
						break;
					case "pargroup":
						color(t.name, t.status);
						break;
					case "act":
						color(t.name, t.status);
						break;
					case "support":
						if(t.status=="in") {
							nextResource(t);
							breakwhile =  true;
						}
						
						break;
					case "assessment":
						if(t.status=="in") {
							nextResource(t);
							breakwhile =  true;
						}

						break;
					case "control":
						if(t.status=="in") {
							nextXNode(t);
							breakwhile =  true;
						}

						break;
					case "dialog":
						if(t.status=="end") {
							nextXNode(t);
							breakwhile = true;
							
						}

						break;
						
					default:
						console.error("Why here?");
						break;
					}
				} // end while
				
				if (!iter.hasNext()) {
					disableNextButton();
				}

			});

		});