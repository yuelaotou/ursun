//上传文件扩展名限制
var extType='.zip|.rar|.jar|.war|.bmp|.gif|.jpg|.jpeg|.tif|.tiff|.png|.doc|.xls|.ppt|.ods|.odt|.txt|.pdf|.docx|.xlsx|.pptx';
var globalContentLimit = 2000;
var globalContentMin = 5;
Array.prototype.inArray = function(e){
	for(i=0;i<this.length && this[i]!=e;i++);
	return !(i==this.length);
}

function extValidation(){
	//文件名（不包括 /\ <> *?: "| 之一）+"."+3至4位后缀（字母、数字）
	var regx=/^[^\/\\<>\*\?\:"\|,#]+.{1}[A-Za-z1-9]{3,4}$/;
	var extTypeArray = extType.split('|');
	var ch = $("input[type=file]");
	if(ch.length==0) return true; 
	for(var i=0;i<ch.length;i++){
		val = ch[i].value;
		if(val!=''){
			var filename=val.substring(val.lastIndexOf("\\")+1,val.length);
			var ext=/\.[^\.]+$/.exec(val);
			if(!extTypeArray.inArray(ext.toString().toLowerCase())){
				alert('上传附件的扩展名只能为'+extType+'，请重新选择');
				return false;
			}
			if(!regx.test(filename)){
				alert("附件【"+filename+"】名称中含有非法字符，请重新选择");
				return false;
			}
		}
	}
	return true;
}
function deleteAttachment(attId){
	if(!confirm("确定要删除该附件吗？")) return;
	$.ajax( {
		type : "POST",
		url : contextPath
				+ "/solution/attachmentAC!deleteAttachmentById.do?attachment.attachmentId="
				+ attId,
		dataType : "JSON",
		success : function(data) {
			window.location.reload();
		}
	});
}

function compareDate(beginTime,endTime) {
		var regxDate = /^(?:(?!0000)[0-9]{4}-(?:(?:0[1-9]|1[0-2])-(?:0[1-9]|1[0-9]|2[0-8])|(?:0[13-9]|1[0-2])-(?:29|30)|(?:0[13578]|1[02])-31)|(?:[0-9]{2}(?:0[48]|[2468][048]|[13579][26])|(?:0[48]|[2468][048]|[13579][26])00)-02-29)$/
		if (beginTime && !regxDate.test(beginTime)) {
			alert("起始日期格式不正确！");
			return;
		}
		if (endTime && !regxDate.test(endTime)) {
			alert("截止日期格式不正确！");
			return;
		}
		if (beginTime && endTime) {
			if (beginTime > endTime) {
				alert("起始日期不能晚于截止日期！");
				return;
			}
		}
		return true;
}
function validateCollectionTime(nowTime,endTime) {
		var regxDate = /^(?:(?!0000)[0-9]{4}-(?:(?:0[1-9]|1[0-2])-(?:0[1-9]|1[0-9]|2[0-8])|(?:0[13-9]|1[0-2])-(?:29|30)|(?:0[13578]|1[02])-31)|(?:[0-9]{2}(?:0[48]|[2468][048]|[13579][26])|(?:0[48]|[2468][048]|[13579][26])00)-02-29)$/
		if (endTime && !regxDate.test(endTime)) {
			alert("截止日期格式不正确！");
			return;
		}
		if (nowTime && endTime) {
			if (nowTime > endTime) {
				alert("截止日期不能早于当前日期（"+nowTime+"）！");
				return;
			}
		}
		return true;
}
function htmlEncode(html){
	return html.replace(/</g,'&lt;').replace(/>/g,'&gt;');;
}
/**
 * 限制图片显示大小 by 付斌
 * @param {容器cssClass} containerClass
 * @param {图片最大宽度} imgWidthToLimit
 */
function limitImageWidth(containerClass,imgWidthToLimit){
		$(containerClass+" img").each(function() {
			var img = $(this);
			if (img.attr("width") > imgWidthToLimit) {
					img.attr("width", imgWidthToLimit).addClass("pointer").click(function() {
					window.open(img.attr("src"));
				})
			}
		})
}
/**
 * 验证附件大小 by 付斌
 * @param {表单ID} formId
 */
function fileSizeValidation(formId){
	return true;
}
Date.prototype.format = function(format) // 
{
	var o = {
		"M+" : this.getMonth() + 1, // month
		"d+" : this.getDate(), // day
		"h+" : this.getHours(), // hour
		"m+" : this.getMinutes(), // minute
		"s+" : this.getSeconds(), // second
		"q+" : Math.floor((this.getMonth() + 3) / 3), // quarter
		"S" : this.getMilliseconds()
		// millisecond
	}
	if (/(y+)/.test(format))
		format = format.replace(RegExp.$1, (this.getFullYear() + "").substr(4
						- RegExp.$1.length));
	for (var k in o)
		if (new RegExp("(" + k + ")").test(format))
			format = format.replace(RegExp.$1, RegExp.$1.length == 1
							? o[k]
							: ("00" + o[k]).substr(("" + o[k]).length));
	return format;
}

function __reset(formId){
	var selects = $("#"+formId+" select");
	selects.each(function(){
		$(this).val($(this).children().val());
	});
	var inputs = $("#"+formId+" input");
	inputs.each(function(){
		if($(this)[0].type!='hidden' && $(this)[0].type!='button'){
			$(this).val('');
		}
	});
}
//删除HTML代码
function delHtml(Word) {
	a = Word.indexOf("<");
	b = Word.indexOf(">");
	len = Word.length;
	c = Word.substring(0, a);
	if (b == -1)
		b = a;
	d = Word.substring((b + 1), len);
	Word = c + d;
	tagCheck = Word.indexOf("<");
	if (tagCheck != -1)
		Word = delHtml(Word);
		
	Word = Word.replace(/&nbsp;/g,'')
	Word = Word.replace(/	/g,'')
	return Word.replace(/ /g,'');
}

function   over(element) 
        { 
                element.style.backgroundColor= "#F5ECCD"; 
                element.style.cursor= "hand "; 
        } 
 function   out(element) 
        { 
                element.style.backgroundColor="#F3F7FA"; 
                element.style.cursor= "default "; 
        } 
        
 function isWindow( obj ){ 
	return typeof(obj) != "undefined";
} 

function countChar(){
	var text = $($("#xhe0_iframe")[0].contentWindow.document.body);
	var editor = $("#xh_editor");
	var charCount=editor.val().length;
	var sp = $("#charCount");
	text.bind('keydown',countIt).bind('keyup',countIt).bind('keypress',countIt).bind('beforeeditfocus',countIt);
	function countItWait(){
		setTimeout(countIt,10);
	}
	function countIt(e){
		charCount = delHtml(editor.val()).length;
		sp.html(charCount);
	}
	countIt();
}
//键盘快速提交表单-ctrl+enter 参数：提交函数
function quickSubmit(submitFunc){
	$($("#xhe0_iframe")[0].contentWindow.document.body).bind('keydown',function(event){
		 var ie =navigator.appName=="Microsoft Internet Explorer"?true:false;
	    if(ie){
	        if(event.ctrlKey && event.keyCode==13){submitFunc();}
	    }
	});
}