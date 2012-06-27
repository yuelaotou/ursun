

(function($) {
	$.fn.validationEngineLanguage = function() {};
	$.validationEngineLanguage = {
		newLang: function() {
			$.validationEngineLanguage.allRules = {"required":{    
						"regex":"none",
						"alertText":"* 该项为必填项",
						"alertTextCheckboxMultiple":"* 请选择一个选项",
						"alertTextCheckboxe":"* 该复选框为必填项"},
					"length":{
						"regex":"none",
						"alertText":"* 长度必须在 ",
						"alertText2":" - ",
						"alertText3": " 之间 "},
					"minCheckbox":{
						"regex":"none",
						"alertText":"* 被选择项超出允许的最大值"},	
					"confirm":{
						"regex":"none",
						"alertText":"* 您填写的信息不配置"},		
					"telephone":{
						"regex":"/^[0-9\-\(\)\ ]+$/",
						"alertText":"* 电话号码不合法"},	
					"email":{
						"regex":"/^[a-zA-Z0-9_\.\-]+\@([a-zA-Z0-9\-]+\.)+[a-zA-Z0-9]{2,4}$/",
						"alertText":"* 邮件地址不合法"},	
					"date":{
                         "regex":"/^[0-9]{4}\-\[0-9]{1,2}\-\[0-9]{1,2}$/",
                         "alertText":"* 日期格式不合法, 格式必须为 YYYY-MM-DD "},
					"onlyNumber":{
						"regex":"/^[0-9\ ]+$/",
						"alertText":"* 该项只能是数字"},	
					"noSpecialCaracters":{
						"regex":"/^[0-9a-zA-Z]+$/",
						"alertText":"* 该项不能包含特殊字符"},	
					"onlyLetter":{
						"regex":"/^[a-zA-Z\ \']+$/",
						"alertText":"* 该项只能为字母"}
				}	
		}
	}
})(jQuery);

$(document).ready(function() {	
	$.validationEngineLanguage.newLang()
});