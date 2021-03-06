/*
 * Inline Form Validation Engine, jQuery plugin
 * 
 * Copyright(c) 2009, Cedric Dugas http://www.position-relative.net
 * 
 * Form validation engine witch allow custom regex rules to be added. Licenced
 * under the MIT Licence
 */

jQuery.fn.validation = function(settings) {

	var validateObject = this;
	if ($.validationEngineLanguage) { // IS THERE A LANGUAGE
		// LOCALISATION ?
		allRules = $.validationEngineLanguage.allRules
	} else {
		allRules = {
			"required" : { // Add your regex rules
				// here, you can take
				// telephone as an example
				"regex" : "none",
				"alertText" : "* This field is required",
				"alertTextCheckboxMultiple" : "* Please select an option",
				"alertTextCheckboxe" : "* This checkbox is required"
			},
			"length" : {
				"regex" : "none",
				"alertText" : "*Between ",
				"alertText2" : " and ",
				"alertText3" : " characters allowed"
			},
			"minCheckbox" : {
				"regex" : "none",
				"alertText" : "* Checks allowed Exceeded"
			},
			"confirm" : {
				"regex" : "none",
				"alertText" : "* Your field is not matching"
			},
			"telephone" : {
				"regex" : "/^[0-9\-\(\)\ ]+$/",
				"alertText" : "* Invalid phone number"
			},
			"email" : {
				"regex" : "/^[a-zA-Z0-9_\.\-]+\@([a-zA-Z0-9\-]+\.)+[a-zA-Z0-9]{2,4}$/",
				"alertText" : "* Invalid email address"
			},
			"date" : {
				"regex" : "/^[0-9]{4}\-\[0-9]{1,2}\-\[0-9]{1,2}$/",
				"alertText" : "* Invalid date, must be in YYYY-MM-DD format"
			},
			"onlyNumber" : {
				"regex" : "/^[0-9\ ]+$/",
				"alertText" : "* Numbers only"
			},
			"noSpecialCaracters" : {
				"regex" : "/^[0-9a-zA-Z]+$/",
				"alertText" : "* No special caracters allowed"
			},
			"onlyLetter" : {
				"regex" : "/^[a-zA-Z\ \']+$/",
				"alertText" : "* Letters only"
			},
			"identityCard" : {
				"alertText" : "* Invalid IdentityCard Number",
				"alertLengthText" : "* Invalid IdentityCard Number Length",
				"alertLengthErrorText" : "* Invalid IdentityCard Number Format,Must {0}",
				"alertDateText" : "* Year Between 1900 and 2200 characters allowed",
				"alertDateYearText" : "* Year Between 00 and 99 characters allowed",
				"alertDateMonthText" : "* Month Between 1 and 12 characters allowed",
				"alertDateDayText" : "* Day Between 1 and 31 characters allowed",
				"alertDateErrorText" : "* {0}Month has't {1} day"
			}
		}
	}

	settings = jQuery.extend({
				allrules : allRules
			}, settings);

	var buildPrompt = function(caller, promptText) { // ERROR PROMPT
		var divFormError = document.createElement('div')
		var formErrorContent = document.createElement('div')
		var arrow = document.createElement('div')

		$(divFormError).addClass("formError")
		$(divFormError).addClass($(caller).attr("name"))
		$(divFormError).css({
					opacity : 0.9
				});
		$(formErrorContent).addClass("formErrorContent")
		$(arrow).addClass("formErrorArrow")

		$("body").append(divFormError);
		$(divFormError).append(arrow)
		$(divFormError).append(formErrorContent)
		$(arrow)
				.html('<div class="line1"></div><div class="line2"></div><div class="line3"></div><div class="line4"></div><div class="line5"></div><div class="line6"></div><div class="line7"></div><div class="line8"></div><div class="line9"></div><div class="line10"></div>')
		$(formErrorContent).html(promptText)

		$(divFormError).hide();

		if ($(caller).is("select") && $.browser.msie) {
			$(caller).wrap("<span class='formError_input'></span> ");
		} else {
			$(caller).addClass("formError_input");
		}

		$(caller).hover(function() {
			var closingPrompt = $(this).attr("name").replace(/\./g, "\\.")
					.replace(/\[/g, "\\[").replace(/\]/g, "\\]");
			callerTopPosition = $(this).offset().top;
			callerleftPosition = $(this).offset().left;
			callerHeight = $(this).height()
			callerTopPosition = callerTopPosition + callerHeight + 7;
			var divFormError = $("." + closingPrompt);
			divFormError.css({
						top : callerTopPosition,
						left : callerleftPosition
					})
			divFormError.show();
		}, function() {
			var closingPrompt = $(this).attr("name").replace(/\./g, "\\.")
					.replace(/\[/g, "\\[").replace(/\]/g, "\\]");
			$("." + closingPrompt).hide();
		});
	};
	var updatePromptText = function(caller, promptText) { // UPDATE TEXT ERROR
		// IF AN ERROR IS
		// ALREADY DISPLAYED
		updateThisPrompt = $(caller).attr("name").replace(/\./g, "\\.")
				.replace(/\[/g, "\\[").replace(/\]/g, "\\]");
		$("." + updateThisPrompt).find(".formErrorContent").html(promptText)

		callerTopPosition = $(caller).offset().top;
		callerHeight = $(caller).height();

		callerTopPosition = callerTopPosition + callerHeight + 7
		$("." + updateThisPrompt).animate({
					top : callerTopPosition
				});
		if ($(caller).is("select") && $.browser.msie) {
			if (!$(caller).parent().is(".formError_input"))
				$(caller).wrap("<span class='formError_input'></span> ");
		} else {
			$(caller).addClass("formError_input");
		}
		$(caller).hover(function() {
			var closingPrompt = $(this).attr("name").replace(/\./g, "\\.")
					.replace(/\[/g, "\\[").replace(/\]/g, "\\]");
			callerTopPosition = $(this).offset().top;
			callerleftPosition = $(this).offset().left;
			callerHeight = $(this).height()
			callerTopPosition = callerTopPosition + callerHeight + 7;
			var divFormError = $("." + closingPrompt);
			divFormError.css({
						top : callerTopPosition,
						left : callerleftPosition
					})
			divFormError.show();
		}, function() {
			var closingPrompt = $(this).attr("name").replace(/\./g, "\\.")
					.replace(/\[/g, "\\[").replace(/\]/g, "\\]");
			$("." + closingPrompt).hide();
		});
	}
	var loadValidation = function(caller) { // GET VALIDATIONS TO BE
		// EXECUTED
		rulesParsing = $(caller).attr('class');
		rulesRegExp = /\[(.*)\]/;
		getRules = rulesRegExp.exec(rulesParsing);
		str = getRules[1]
		pattern = /\W+/;
		result = str.split(pattern);

		var validateCalll = validateCall(caller, result)
		return validateCalll

	};
	var validateCall = function(caller, rules) { // EXECUTE VALIDATION
		// REQUIRED
		// BY THE USER FOR THIS FILED

		var promptText = ""
		var prompt = $(caller).attr("name").replace(/\./g, "\\.").replace(
				/\[/g, "\\[").replace(/\]/g, "\\]");
		var caller = caller;
		isError = false;
		callerType = $(caller).attr("type");

		for (i = 0; i < rules.length; i++) {
			switch (rules[i]) {
				case "optional" :
					if (!$(caller).val()) {
						closePrompt(caller)
						return isError
					}
					break;
				case "required" :
					_required(caller, rules);
					break;
				case "date" :
					_dateValidation(caller, rules, i);
					break;
				case "custom" :
					_customRegex(caller, rules, i);
					break;
				case "length" :
					_length(caller, rules, i);
					break;
				case "minCheckbox" :
					_minCheckbox(caller, rules, i);
					break;
				case "confirm" :
					_confirm(caller, rules, i);
					break;
				case "identityCard" :
					_checkIdentityCard(caller, rules, i);
					break;
				case "userDefined" :
					_userDefined(caller, rules, i);
					break;
				default :
					;
			};
		};
		if (isError == true) {

			if ($("input[name=" + prompt + "]").size() > 1
					&& callerType == "radio") { // Hack
				// for
				// radio
				// group
				// button,
				// the
				// validation
				// go
				// the
				// first
				// radio
				caller = $("input[name=" + prompt + "]:first")
			}
			($("." + prompt).size() == 0)
					? buildPrompt(caller, promptText)
					: updatePromptText(caller, promptText);
		} else {
			closePrompt(caller)
		}

		/* VALIDATION FUNCTIONS */
		function _required(caller, rules) { // VALIDATE BLANK FIELD
			callerType = $(caller).attr("type")

			if (callerType == "text" || callerType == "password"
					|| callerType == "textarea") {

				if (!$.trim($(caller).val())) {
					isError = true
					promptText += settings.allrules[rules[i]].alertText
							+ "<br>"
				}
			}
			if (callerType == "radio" || callerType == "checkbox") {
				callerName = $(caller).attr("name")

				if ($("input[name=" + callerName + "]:checked").size() == 0) {
					isError = true
					if ($("input[name=" + callerName + "]").size() == 1) {
						promptText += settings.allrules[rules[i]].alertTextCheckboxe
								+ "<br>"
					} else {
						promptText += settings.allrules[rules[i]].alertTextCheckboxMultiple
								+ "<br>"
					}
				}
			}
			if (callerType == "select-one") { // added by paul@kinetek.net for
				// select boxes, Thank you
				callerName = $(caller).attr("name");

				if (!$("select[name=" + callerName + "]").val()) {
					isError = true;
					promptText += settings.allrules[rules[i]].alertText
							+ "<br>";
				}
			}
			if (callerType == "select-multiple") { // added by paul@kinetek.net
				// for select boxes, Thank
				// you
				callerName = $(caller).attr("id");

				if (!$("#" + callerName).val()) {
					isError = true;
					promptText += settings.allrules[rules[i]].alertText
							+ "<br>";
				}
			}
		}
		function _customRegex(caller, rules, position) { // VALIDATE
			// REGEX RULES
			customRule = rules[position + 1]
			pattern = eval(settings.allrules[customRule].regex)
			if ($(caller).attr('value') != "") {
				if (!pattern.test($(caller).attr('value'))) {
					isError = true
					promptText += settings.allrules[customRule].alertText
							+ "<br>"
				}
			}
		}
		function _confirm(caller, rules, position) { // VALIDATE FIELD
			// MATCH
			confirmField = rules[position + 1]

			if ($(caller).attr('value') != $("#" + confirmField).attr('value')) {
				isError = true
				promptText += settings.allrules["confirm"].alertText + "<br>"
			}
		}
		function _length(caller, rules, position) { // VALIDATE LENGTH

			startLength = eval(rules[position + 1])
			endLength = eval(rules[position + 2])
			feildLength = $(caller).attr('value').length

			if (feildLength < startLength || feildLength > endLength) {
				isError = true
				promptText += settings.allrules["length"].alertText
						+ startLength + settings.allrules["length"].alertText2
						+ endLength + settings.allrules["length"].alertText3
						+ "<br>"
			}
		}
		function _minCheckbox(caller, rules, position) { // VALIDATE CHECKBOX
			// NUMBER

			nbCheck = eval(rules[position + 1])
			groupname = $(caller).attr("name")
			groupSize = $("input[name=" + groupname + "]:checked").size()

			if (groupSize > nbCheck) {
				isError = true
				promptText += settings.allrules["minCheckbox"].alertText
						+ "<br>"
			}
		}

		function _checkIdentityCard(caller, rules, i) {
			var inputStr = $(caller).attr('value');
			var format = inputStr.length;
			if (!_is0AndPosInteger(inputStr)) {
				promptText = settings.allrules["identityCard"].alertText
						+ "<br>";
				isError = true
				return false;
			}
			if (inputStr == "") {
				return true;
			}
			if (format != 15 && format != 18) {
				promptText = settings.allrules["identityCard"].alertLengthText
						+ "<br>";
				isError = true
				return false;
			}
			var temp;
			var year, month, day;
			if (inputStr.length != format) {
				promptText = settings.allrules["identityCard"].alertLengthErrorText
						.replace("{0}", format)
						+ "<br>";
				isError = true
				return false;
			} else {
				// 妫�鏌ュ勾鐨勬牸寮�
				if (format == 18) {
					temp = inputStr.substring(6, 10);
					year = parseInt(temp, 10);
					if (year < 1900 || year > 2200) {
						promptText = settings.allrules["identityCard"].alertDateText
								+ "<br>";
						isError = true
						return false;
					}
				} else if (format == 15) {
					temp = inputStr.substring(6, 8);
					year = parseInt(temp, 10);
					if (year < 00 || year > 99) {
						promptText = settings.allrules["identityCard"].alertDateYearText
								+ "<br>";
						isError = true
						return false;
					}
				}
				// 妫�鏌ユ湀鐨勬牸寮�
				if (format == 18) {
					temp = inputStr.substring(10, 12);
				} else if (format == 15) {
					temp = inputStr.substring(8, 10);
				}
				month = parseInt(temp, 10);
				if (month < 1 || month > 12) {
					promptText = settings.allrules["identityCard"].alertDateMonthText
							+ "<br>";
					isError = true
					return false;
				}
				// 妫�鏌ユ棩鐨勬牸寮�
				if (format == 18) {
					temp = inputStr.substring(12, 14);
				} else if (format == 15) {
					temp = inputStr.substring(10, 12);
				}
				day = parseInt(temp, 10);
				if ((day == 0) || (day > 31)) {
					promptText = settings.allrules["identityCard"].alertDateDayText
							+ "<br>";
					isError = true
					return false;
				} else if (day > 28 && day < 31) {
					if (month == 2) {
						if (day != 29) {
							promptText = settings.allrules["identityCard"].alertDateErrorText
									.replace("{0}", month).replace("{1}", day)
									+ "<br>";
							isError = true
							return false;
						} else {
							if ((year % 4) != 0) {
								promptText = settings.allrules["identityCard"].alertDateErrorText
										.replace("{0}", month).replace("{1}",
												day)
										+ "<br>";
								isError = true
								return false;
							} else {
								if ((year % 100 == 0) && (year % 400 != 0)) {
									promptText = settings.allrules["identityCard"].alertDateErrorText
											.replace("{0}", month).replace(
													"{1}", day)
											+ "<br>";
									isError = true
									return false;
								}
							}
						}
					}
				} else if (day == 31) {
					if ((month == 2) || (month == 4) || (month == 6)
							|| (month == 9) || (month == 11)) {
						promptText = settings.allrules["identityCard"].alertDateErrorText
								.replace("{0}", month).replace("{1}", day)
								+ "<br>";
						isError = true
						return false;
					}
				}
			}
		}

		function _is0AndPosInteger(inputVal) {
			// 如果是18位身份证，最后一位允许是X
			var format = inputVal.length;
			if (format == 18) {
				var lastChar = inputVal.charAt(inputVal.length - 1)
				if (lastChar == "X") {
					inputVal = inputVal.substring(0, inputVal.length - 1);
				}
			}
			for (var i = 0; i < inputVal.length; i++) {
				var oneChar = inputVal.charAt(i)
				if (oneChar < "0" || oneChar > "9") {
					return false;
				}
			}
			return true;
		}

		function _dateValidation(caller, rules, i) {

			var str = $.trim($(caller).val());
			if (!str || str.length == 0) {
				return true;
			}
			var str = str.match(/^(\d{2,4})(-|\/)(\d{1,2})\2(\d{1,2})$/);
			if (str == null) {
				isError = true;
				promptText = settings.allrules["date"].alertText
			} else if (str[3] > 12 || str[3] < 1) {
				isError = true;
				promptText = settings.allrules["date"].alertText
			} else if (str[4] > 31 || str[4] < 1) {
				isError = true;
				promptText = settings.allrules["date"].alertText
			}
		}

		function _userDefined(caller, rules, position) { // VALIDATE FIELD
			var str = $.trim($(caller).val());
			fun = rules[position + 1];
			returnValue = eval(fun + "('" + str + "')");
			if (returnValue.value) {
				isError = true;
				promptText += returnValue.alertText + "<br>"
			}
		}

		return (isError) ? isError : false;
	};
	var closePrompt = function(caller) { // CLOSE PROMPT WHEN ERROR CORRECTED
		closingPrompt = $(caller).attr("name").replace(/\./g, "\\.").replace(
				/\[/g, "\\[").replace(/\]/g, "\\]");
		if ($("." + closingPrompt).size() > 0) {
			$("." + closingPrompt).fadeTo("fast", 0, function() {
						$(this).remove();
					});

			if ($(caller).is("select") && $.browser.msie) {
				var span = $(caller).parent(".formError_input");
				span.after($(caller));
				span.remove();
			} else {
				$(caller).removeClass("formError_input");
			}
		}
	};

	var submitValidation = function(caller) {

		var stopForm = false
		$(caller).find("[class^=validate]:visible").each(function() {
					var validationPass = loadValidation(this);
					if (!stopForm && validationPass)
						$(this).focus();
					(validationPass) ? stopForm = true : "";
				});
		if (stopForm) {
			destination = $(".formError:first").offset().top;
			$("html:not(:animated),body:not(:animated)").animate({
						scrollTop : destination
					}, 1100);
			return false;
		} else {
			return true;
		}
	};

	return submitValidation(validateObject);
};

jQuery.fn.clearValidationPrompt = function() { // CLOSE All PROMPT WHEN ERROR
	// CORRECTED
	$(".formError").fadeTo("fast", 0, function() {
				$(".formError").remove();
			});
	if ($.browser.msie) {
		$(".formError_input").each(function(index) {
					if ($(this).children().is("select") ) {
						var children =$(this).children();
						$(this).after(children);
						$(this).remove();
					} else {
						$(this).removeClass("formError_input");
					}
				});
	} else {
		$(".formError_input").removeClass("formError_input");
	}
};
