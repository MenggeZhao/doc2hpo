function parse() {
	var note = $("#note").val();
	note = formatText(note);
	var value = "";
	// default engine is act.
	if($("#parsingEngine").dropdown('get value') == ""){
		value = "act";
	}else{
		value = $("#parsingEngine").dropdown('get value');
		console.log(value);
	}
	
	if (value == "act") {
		parseACT(note);
	}
	if (value == "mmp") {
		parsingJson = parseMetamap(note);
	}
	if (value == "ncbo") {
		parseNcbo(note);
	}
}

function parseMetamap(note) {
	var all_acros_abbrs = $('#all_acros_abbrs').is(':checked');
	var allow_concept_gaps = $('#allow_concept_gaps').is(':checked');
	var ignore_word_order = $('#ignore_word_order').is(':checked');
	var ignore_stop_phrases = $('#ignore_stop_phrases').is(':checked');
	var formData = {
		'note' : note,
		'mmpgeneral' : {
			'aaa' : all_acros_abbrs,
			'acg' : allow_concept_gaps,
			'iwo' : ignore_word_order,
			"isp" : ignore_stop_phrases,
		},
	};
	$.blockUI({
		message : '<div class="ui segment"><div class="ui active dimmer"><div class="ui text loader">Loading...It may take up to few minutes.</div><p></p><p></p><p></p><p></p></div></div>',
		css: { 
            border: 'none',
            '-webkit-border-radius': '40px', 
            '-moz-border-radius': '40px', 
            opacity: .5, 
        },
	});
	$.ajax({
		headers : {
			'Accept' : 'application/json',
			'Content-Type' : 'application/json'
		},
		type : 'POST',
		url :  "/doc2hpo/parse/metamap",
		data : JSON.stringify(formData),
		dataType : "json",
		success : function(data) {
			var terms = data["hmName2Id"];
			if (jQuery.isEmptyObject(terms)) {
				alert("No UMLS or HPO terms found!");
			} else {
				if(terms == 'ERROR'){
					alert("ERROR: Something wrong with metamap engine. Please check the configuration on server end and make sure metamap server is running.");
				}else{
					highlight(terms);
					updateTable(terms);
					var t = $(window).scrollTop();
					$('body,html').animate({
						'scrollTop' : t + 1000
					}, 200)
					$("#phenolyzer").show();
				}
			}

		},
		error : function(XMLHttpRequest, textStatus, errorThrown) {
			console.log(url);
		}
	});
}

function parseACT(note) {
	var formData = {
		'note' : note,
	};
	$.blockUI({
		message : '<div class="ui segment"><div class="ui active dimmer"><div class="ui text loader">Loading...It may take up to few minutes.</div><p></p><p></p><p></p><p></p></div></div>',
		css: { 
            border: 'none',
            '-webkit-border-radius': '40px', 
            '-moz-border-radius': '40px', 
            opacity: .5, 
        },
	});
	$.ajax({
		headers : {
			'Accept' : 'application/json',
			'Content-Type' : 'application/json'
		},
		type : 'POST',
		url : "/doc2hpo/parse/acdat",
		data : JSON.stringify(formData),
		dataType : "json",
		success : function(data) {
			var parsingJson = data["hmName2Id"];
			var terms = longestParsingJson(parsingJson);
			if(terms == 'ERROR'){
				alert("ERROR: Something wrong with act engine. Please check the configuration on server end.");
			}else{
				highlight(terms);
				updateTable(terms);
				var t = $(window).scrollTop();
				$('body,html').animate({
					'scrollTop' : t + 1000
				}, 200)
				$("#phenolyzer").show();
			}

		},
		error : function(XMLHttpRequest, textStatus, errorThrown) {
			console.log(url);
		}
	});
}

function parseNcbo(note) {
	var longest_only = $('#longest_only').is(':checked');
	var whole_word_only = $('#whole_word_only').is(':checked');
	var exclude_numbers = $('#exclude_numbers').is(':checked');
	var formData = {
		'note' : note,
		'ncbogeneral' : {
			'lo' : longest_only,
			'wwo' : whole_word_only,
			'en' : exclude_numbers
		}
	};
	$.blockUI({
		message : '<div class="ui segment"><div class="ui active dimmer"><div class="ui text loader">Loading...It may take up to few minutes.</div><p></p><p></p><p></p><p></p></div></div>',
		css: { 
            border: 'none',
            '-webkit-border-radius': '40px', 
            '-moz-border-radius': '40px', 
            opacity: .5, 
        },
	});
	$.ajax({
		headers : {
			'Accept' : 'application/json',
			'Content-Type' : 'application/json'
		},
		type : 'POST',
		url : "/doc2hpo/parse/ncbo",
		data : JSON.stringify(formData),
		dataType : "json",
		success : function(data) {
			var terms = data["hmName2Id"];
			if (jQuery.isEmptyObject(terms)) {
				alert("No UMLS or HPO terms found!");
			} else {
				if(terms == 'ERROR'){
					alert("ERROR: Something wrong with ncbo engine. Please check the configuration on server end.");
				}else{
					highlight(terms);
					updateTable(terms);
					var t = $(window).scrollTop();
					$('body,html').animate({
						'scrollTop' : t + 1000
					}, 200)
					$("#phenolyzer").show();
				}
			}
			return terms

		},
		error : function(XMLHttpRequest, textStatus, errorThrown) {
			console.log(url);
		}
	});
}

function testController() {
	var search = {
		pName : "bhanu",
		lName : "prasad"
	}
	$.ajax({
		type : "POST",
		headers : {
			'Accept' : 'application/json',
			'Content-Type' : 'application/json'
		},

		dataType : 'json',
		url : "/doc2hpo/test/test1",
		data : JSON.stringify(search), // Note it is important
		success : function(result) {
			// do what ever you want with data
		}
	})
}
