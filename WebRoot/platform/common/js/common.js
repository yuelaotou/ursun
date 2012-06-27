/**
 * 前台提交前的公共组件 组件：common.js 说明：1. moveSelected -----------------左右两个对应的多选下拉列表之间传值
 * 2. selectAll-------------------------具有全选功能的主复选框联动控制子复选框 3.
 * checkboxValueAppend-----------多个被选中复选框中值的拼接 4.
 * checkboxValueAppendTwo------------多个被选中复选框中值的拼接 - 用引号把每个值封装 5.
 * clearAllSelect----------------子复选框联动控制有权选功能的主复选框 6.
 * trimAll---------------------------去除整个fomr表单的左右空格 7.
 * trimText----------------------去除单行输入框的左右空格 8.
 * proxyRequest----------------------局部刷新技术 9.
 * openDlg-----------------------打开一个非模态窗口
 * 10.jsTrim----------------------------去掉输入域的空格，包括文字中间的空格
 * 11.openNDlg----------------------打开一个模态窗口
 * 12.openpage--------------------------打开一个没有工具栏，菜单栏等的窗口
 * 13.ok----------------------------显示一个确认对话框
 * 14.lTrim-----------------------------去处左空格
 * 15.rTrim-------------------------去处右空格
 * 16.enterToTab------------------------回车键换为tab
 * 17.earlierCurrentTime------------检查输入日期是否大于当前系统日期
 * 18.isNoChinese-----------------------校验非中文及全角字符
 * 19.toUpCase----------------------把输入字符自动转成大写，使用于要转换文本中的onblur()事件中
 * 20.replaceToUTF8---------------------转变为utf8字符标准的字符串 21.checkLength
 * ---------------- 检查某个文本框的输入值的长度
 * 22.checkHaveSpecial---------------------检查文本框是否含有特殊字符
 * 23.checkIsNumOrWordOrLine--------------- 检查是否为数字或字母空格或“/”
 * 24.checkIsWordOrSpace------------检查是否为字母或空格
 * 25.replaceDoubleQuotation------------替换超链接的title或者qtip中的“"”
 * 26.checkIsNum--------------------检查是否为数字 27.gotoPage-------------------- 翻页
 * 28.doSort-------------------- 排序 /** 左右两个对应的多选下拉列表之间传值的组件
 * 组件：multipleSelect.js 说明 1,调用主体: "-->"和"<--"形式的按钮 2,适用方法:onClick()
 * 3,参数均为两个需要传值的下拉列表的name
 */
jQuery.extend({
			handleError : function(s, xhr, status, e) {
				var ct = xhr.getResponseHeader("content-type"), xml = s.type == "xml"
						|| !s.type && ct && ct.indexOf("xml") >= 0, data = xml
						? xhr.responseXML
						: xhr.responseText;
				if (xml && data.documentElement.tagName == "parsererror")
					throw "parsererror";
				var errorHandle = function() {
					// If a local callback was specified, fire it
					if (s.error)
						s.error(xhr, status, e);

					// Fire the global callback
					if (s.global)
						$.event.trigger("ajaxError", [xhr, s, e]);

				}
				if (IS_SESSION_TIMEOUT) {
					popErrorMsg({
								error_msg : wee.platform.timeout
							}, null, null);
					return;
				}
				// The filter can actually parse the response
				if (typeof data === "string") {
					// Get the JavaScript object, if JSON is used.
					try {
						data = window["eval"]("(" + data + ")");
						if (data && data.error_msg) {
							popErrorMsg(data, null, errorHandle);
							return
						}
					} catch (e) {
					};
				}
				errorHandle();
			},
			httpSuccess : function(xhr) {
				try {
					// IE error sometimes returns 1223 when it should be 204 so
					// treat it as success, see #1450
					var result = !xhr.status && location.protocol == "file:"
							|| (xhr.status >= 200 && xhr.status < 300)
							|| xhr.status == 304 || xhr.status == 1223;

					IS_SESSION_TIMEOUT = !(getCookie('WEE_SID') === CUR_WEE_SID);
					return result && !(IS_SESSION_TIMEOUT&&IS_LOGIN==="true");

				} catch (e) {
				}
				return false;
			}
		});
function moveSelected(oSourceSel, oTargetSel) {
	// 建立存储value和text的缓存数组
	var arrSelValue = new Array();
	var arrSelText = new Array();
	// 此数组存贮选中的options，以value来对应
	var arrSelOption = new Array();

	// 存储源列表框中所有的数据到缓存中，并建立value和选中option的对应关系
	for (var i = 0; i < oSourceSel.options.length; i++) {
		if (oSourceSel.options[i].selected) {
			// 存储
			arrSelValue[arrSelValue.length] = oSourceSel.options[i].value;
			arrSelText[arrSelText.length] = oSourceSel.options[i].text;
			// 建立value和选中option的对应关系
			arrSelOption[arrSelOption.length] = oSourceSel.options[i];
		}
	}

	// 增加缓存的数据到目的列表框中，并删除源列表框中的对应项
	for (var i = 0; i < arrSelValue.length; i++) {
		// 增加
		var oOption = document.createElement("option");
		oOption.text = arrSelText[i];
		oOption.value = arrSelValue[i];
		// 添加选中的项到恰当的位置
		var k = 0;
		for (k = 0; k < oTargetSel.options.length; k++) {
			if (oTargetSel.options[k].value > arrSelValue[i]) {
				break;
			}
		}
		oTargetSel.add(oOption, k);

		// 删除源列表框中的对应项
		oSourceSel.removeChild(arrSelOption[i]);
	}
}
/**
 * 具有全选功能的checkbox与子checkbox之间联动的组件 组件：multipleSelect.js 说明 1,调用主体: checkbox 元素
 * 2,适用方法:onClick() 3,参数参照方法说明
 */

/*******************************************************************************
 * 方法说明：具有全选功能的主复选框联动控制 子复选框 调用方法：onclick="selectAll('check',this)" 'check'为
 * checkBoxName参数，使用时请使用‘’ this为具有全选功能的checkbox对象
 ******************************************************************************/
function selectAll(checkBoxName, obj) {
	// 全选复选框选中时，子复选框全部选中
	if (obj.checked) {
		var checkList = document.getElementsByName(checkBoxName);
		for (var i = 0; i < checkList.length; i++) {
			checkList[i].checked = true;
		}
	} else {
		// 全选复选框不被选中时，子复选框全部置为不被选中的状态
		var checkList = document.getElementsByName(checkBoxName);
		for (var i = 0; i < checkList.length; i++) {
			checkList[i].checked = false;
		}
	}
}
/*******************************************************************************
 * 方法说明：多个被选中复选框中值的拼接 调用方法：checkboxValueAppend('check','|') 'check'为
 * checkBoxName参数，使用时请使用‘’ '|'为各字符串之间的间隔符 返回值：字符串拼接结果result
 ******************************************************************************/
function checkboxValueAppend(checkBoxName, temp) {
	var checkList = document.getElementsByName(checkBoxName);
	// result变量用于存放拼接结果
	var result = "";
	for (var i = 0; i < checkList.length; i++) {
		if (checkList[i].checked) {
			// 被选中则拼接字符串
			result += checkList[i].value + temp;
		}
	}
	return result;

}
/*******************************************************************************
 * 方法说明：多个被选中复选框中值的拼接 - 用引号把每个值封装 调用方法：checkboxValueAppend('check','|') 'check'为
 * checkBoxName参数，使用时请使用‘’ '|'为各字符串之间的间隔符 返回值：字符串拼接结果result
 ******************************************************************************/
function checkboxValueAppendTwo(checkBoxName, temp) {
	var checkList = document.getElementsByName(checkBoxName);
	// result变量用于存放拼接结果
	var result = "";
	for (var i = 0; i < checkList.length; i++) {
		if (checkList[i].checked) {
			// 被选中则拼接字符串
			result += "'" + checkList[i].value + "'" + temp;
		}
	}
	return result;

}
/*******************************************************************************
 * 方法说明：子复选框联动控制有权选功能的主 复选框 调用方法：clearAllSelect('check',allSelect) 'check'为
 * checkBoxName参数，使用时请使用‘’ allSelect为具有全选功能的checkbox对象的name
 ******************************************************************************/
function clearAllSelect(checkBoxName, allCheckboxName) {
	var checkList = document.getElementsByName(checkBoxName);
	// 初始化变量allChecked为true
	var allChecked = true;
	for (var i = 0; i < checkList.length; i++) {
		if (!checkList[i].checked) {
			// 当有子复选框不被选中时变量allChecked置为false
			allChecked = false;
		}
	}
	// 对具有全选功能的checkbox赋值
	allCheckboxName.checked = allChecked;
}

/** ************************************************************************************ */
/**
 * 去除整个fomr表单的左右空格 调用说明：在提交之前加上去空格函数 author muxw
 */
function trimAll(form) {
	for (var i = 0; i < form.elements.length; i++)
		if (form.elements[i].type == "text"
				|| form.elements[i].type == "textarea") {
			var flag = false;
			var val = form.elements[i].value;
			var val1 = "";
			if (form.elements[i].value != null && form.elements[i].value != "") {
				flag = true;
			}
			while (flag) {
				for (j = 0; j < form.elements[i].value.length; j++) {
					var letter = form.elements[i].value.charAt(j)
					if (" ".indexOf(letter) != -1 || "　".indexOf(letter) != -1) {
						val = form.elements[i].value.substring(j + 1,
								form.elements[i].value.length);
						if (j == (form.elements[i].value.length - 1)) {
							flag = false;
							break;
						}
					} else {
						flag = false;
						break;
					}
				}
			}
			val1 = val;
			if (val.length > 0)
				flag = true;
			while (flag) {
				for (k = val.length - 1; k < val.length; k--) {
					var letter = val.charAt(k)
					if (" ".indexOf(letter) != -1 || "　".indexOf(letter) != -1) {
						val1 = val.substring(0, k);
					} else {
						flag = false;
						break;
					}
				}
			}
			form.elements[i].value = jsTrim(val1);
		}

}

/**
 * 去掉输入域的空格，包括文字中间的空格 return false or true.
 */
function jsTrim(str) {
	var re;
	re = /^[ \t]*|[ \t]*$/g;
	str = str.replace(re, '');
	// modify by fupeng on 2007-12-11.remove \r\n
	// var p=/[ \t\v\f]/g;
	var p = /\s/g;
	str = str.replace(p, '');
	return (str);
}

/**
 * 去除单行输入框的左右空格 调用说明：在提交之前加上去空格函数 author muxw
 */
function trimText(obj) {
	var flag = false;
	var val = obj.value;
	var val1 = "";
	if (obj.value != null && obj.value != "")
		flag = true;
	while (flag) {
		for (j = 0; j < obj.value.length; j++) {
			var letter = obj.value.charAt(j)
			if (" ".indexOf(letter) != -1 || "　".indexOf(letter) != -1) {
				val = obj.value.substring(j + 1, obj.value.length);
				if (j == (obj.value.length - 1)) {
					flag = false;
					break;
				}
			} else {
				flag = false;
				break;
			}
		}
	}
	val1 = val;
	if (val.length > 0)
		flag = true;
	while (flag) {
		for (k = val.length - 1; k < val.length; k--) {
			var letter = val.charAt(k)
			if (" ".indexOf(letter) != -1 || "　".indexOf(letter) != -1) {
				val1 = val.substring(0, k);
			} else {
				flag = false;
				break;
			}
		}
	}
	obj.value = val1;
}

/**
 * add by muxw@neusoft.com 调用说明：局部刷新请求代理
 */
function proxyRequest(url, papameter) {
	if (url == "")
		return;

	if (window.ActiveXObject) {
		objXMLReq = new ActiveXObject("Microsoft.XMLHTTP");
	} else {
		objXMLReq = new XMLHttpRequest();
	}

	if (papameter != null && papameter != "") {
		url = url + "&" + papameter;
	}
	objXMLReq.open("POST", url, false);

	objXMLReq.send("");

	var strResult = objXMLReq.responseText;
	eval(strResult);
}
// 打开一个非模态窗口
function openDlg(loc, width, height) {
	showModalDialog(loc, window, "status:false;scroll:yes;dialogWidth:" + width
					+ "px;dialogHeight:" + height + "px");
}

/**
 * 打开一个模态窗口
 */
function openNDlg(loc, width, height) {
	showModalessDialog(loc, window, "status:false;dialogWidth:" + width
					+ "px;dialogHeight:" + height + "px");
}

/**
 * 打开一个没有工具栏，菜单栏等的窗口
 */
function openpage(page, width, height) {
	window.open(page, "open", "toolbar=0,location=0,directories=0,status=0,"
					+ "menubar=0,scrollbars=1,resizable=1,copyhistory=0,width="
					+ width + ",height=" + height);
}

/**
 * 显示一个确认对话框
 */
function ok(loc, msg) {
	if (confirm(msg)) {
		location = loc;
	}
	return 6;
}

/**
 * 去除左空格
 */
function lTrim(str) {
	return str.replace(/(^[\s　]*)/g, "");
}

/**
 * 去除右空格
 */
function rTrim(str) {
	return str.replace(/([\s　]*$)/g, "");
}

/**
 * 回车键换为tab, 放入控件的 onkeydown 事件中
 */
function enterToTab() {
	if (event.srcElement.type != 'button'
			&& event.srcElement.type != 'textarea' && event.keyCode == 13) {
		event.keyCode = 9;
	}
}

/**
 * 检查输入日期是否大于当前系统日期 如果大于，返回false，否则，返回true。
 */
function earlierCurrentTime(oRealDate, defaultDate, tag) {
	// 如果tag为true，则判断输入日期是否早于或等于当前日期，如果是，返回true
	// 如果tag为false，则判断输入日期是否早于当前日期，如果是，返回true
	// 如果传进的tag为null，则默认是早于或等于
	var equalTag = tag == null ? true : false;
	// 获得系统时间
	var arrDefDate = defaultDate.split("/");
	var strDefDate = arrDefDate.join("");

	// 获得用户输入时间
	realDate = oRealDate.value;
	var arrRealDate = realDate.split("-");
	var strRealDate = arrRealDate.join("");

	if (equalTag) {
		if (parseInt(strRealDate) > parseInt(strDefDate))
			return false;
		else
			return true;
	} else {
		if (parseInt(strRealDate) >= parseInt(strDefDate))
			return false;
		else
			return true;
	}
}

/**
 * added by liu_xc 2002.12.15 校验非中文及全角字符
 */
function isNoChinese(oObject, message) {
	if (oObject.value == "") {
		return true;
	}
	var reg = /[\u4E00-\u9FA5]|[\uFE30-\uFFA0]/gi;
	if (reg.test(oObject.value)) {
		oObject.select()
		alert("[" + message + "]" + "不能输入中文及全角字符,请重新输入!");
		return false;
	}
	return true;
}

/**
 * 把输入字符自动转成大写，使用于要转换文本中的onblur()事件中 用法：在要转换的文本中加入onblur="toUpCase(this)"
 */
function toUpCase(oObject) {
	oObject.value = oObject.value.toUpperCase();
}

/**
 * 转变为utf8字符标准的字符串
 */
function replaceToUTF8(str) {
	str = str.replace(/%/g, "%25");
	str = str.replace(/&/g, "%26");
	str = str.replace(/\\/g, "&#92;");
	str = str.replace(/</g, "&#60;");
	str = str.replace(/>/g, "&#62;");
	str = str.replace(/\"/g, "&#34;");
	str = str.replace(/ /g, "&nbsp;");
	str = str.replace(/\n/g, "%0D%0A;");
	return str;
}

/**
 * 检查某个文本框的输入值的长度 value--控件value；length--控件允许的最大长度 返回true or false
 * 若返回false，需要在EXT的if中加上obj.focus();和提示信息 eg:XXX 只能输入 XX 个字符(一个汉字相当于两个字符)！
 */
function checkLength(value, length) {
	var len = getBLength(value);
	if (len > length) {
		return false;
	}
	return true;
}
/**
 * 取得文本框的输入值的长度
 */
function getBLength(checkField) {
	var len = 0;
	for (var i = 0; i < checkField.length; i++) { // 对于特殊字符 × § ÷ 也需要按两个字符长度处理
		if (checkField.charAt(i).charCodeAt() > 255
				|| checkField.charAt(i) == '×' || checkField.charAt(i) == '§'
				|| checkField.charAt(i) == '÷') {
			len += 2;
		} else {
			len += 1;
		}
	}
	return len;
}
/**
 * 取得文本框的输入值的长度
 */
function getBLengthFor3ByteChar(checkField) {
	var len = 0;
	for (var i = 0; i < checkField.length; i++) { // 对于特殊字符 × § ÷ 也需要按两个字符长度处理
		if (checkField.charAt(i).charCodeAt() > 255) {
			len += 3;
		} else if (checkField.charAt(i) == '×' || checkField.charAt(i) == '§'
				|| checkField.charAt(i) == '÷') {
			len += 2;
		} else {
			len += 1;
		}
	}
	return len;
}
/**
 * 检查某个文本框的输入值的长度 value--控件value；length--控件允许的最大长度 返回true or false
 * 若返回false，需要在EXT的if中加上obj.focus();和提示信息 eg:XXX 只能输入 XX 个字符(一个汉字相当于两个字符)！
 */
function checkLengthForResource(value, length) {
	var len = getBLengthForResource(value);
	if (len > length) {
		return false;
	}
	return true;
}
/**
 * 取得文本框的输入值的长度
 */
function getBLengthForResource(checkField) {
	var len = 0;
	for (var i = 0; i < checkField.length; i++) { // 对于特殊字符 × § ÷ 也需要按两个字符长度处理
		if (checkField.charAt(i).charCodeAt() > 255
				|| checkField.charAt(i) == '×' || checkField.charAt(i) == '§'
				|| checkField.charAt(i) == '÷') {
			len += 3;
		} else {
			len += 1;
		}
	}
	return len;
}
/**
 * 校验单行文本框是否有特殊字符 value--控件value 返回输入框含有的特殊字符，若res.length=0，则不包含特殊字符，校验通过;
 * 若res.length>0需要在EXT的if中加上obj.focus();和提示信息 eg:XXX 中含有 XXX 特殊字符！
 */
function checkHaveSpecial(value) {
	// 特殊字符
	var SPECIAL_STR = "<>";
	// 存放输入框包含的特殊字符
	var res = "";
	for (i = 0; i < value.length; i++) {
		var cc = value.charAt(i);
		if (SPECIAL_STR.indexOf(cc) != -1) {
			if (res.indexOf(cc) == -1) {
				res = res + cc;
			}
		}
	}
	return res;
}
/**
 * 对于输入文本是否为数字或字母或“/”进行判断 应用：检索准备--分类号 返回 false or true. 65~90---A~Z;
 * 97~122---a~z; 48~57---0~9; 47----/; 32--space; 45--"-"; 59--; 44--, 64--@
 * 59--: 46--. 38--& 若返回false，需要在EXT的if中加上obj.focus();和提示信息 eg:XXX
 * 只能输入数字字母或/-;,@:.&和空格！
 */
function checkIsNumOrWordOrLine(value) {
	var len = value.length;
	if (len == 0) {
		return [true];
	}
	for (var i = 0; i < len; i++) {
		var c = value.charAt(i).charCodeAt();
		if ((c >= 65 && c <= 90) || (c >= 97 && c <= 122)
				|| (c >= 48 && c <= 57) || (c == 47) || (c == 32) || (c == 45)
				|| (c == 59) || (c == 44) || (c == 64) || (c == 58)
				|| (c == 46) || (c == 38)) {
			continue;
		} else {
			return [false, '只能输入数字字母或/-;,@:.&和空格。'];
		}

	}
	return [true];
}
/**
 * 对于输入文本是否是字母或空格进行判断 应用：1.检索准备--精确选取关键词--中英文联合检索--英文关键词
 * 2.检索准备--模糊选取关键词--拼音检索--拼音 返回 false or true.
 * 若返回false，需要在EXT的if中加上obj.focus();和提示信息 eg:XXX 只能输入字母或空格！
 */
function checkIsWordOrSpace(value) {
	var len = value.length;
	if (len == 0) {
		return true;
	}
	for (var i = 0; i < len; i++) {
		var c = value.charAt(i).charCodeAt();
		if ((c >= 65 && c <= 90) || (c >= 97 && c <= 122) || (c == 32)) {
			continue;
		} else {
			return false;
		}

	}
	return true;
}

/**
 * 鼠标放上以后，进行图片样式转换的相关JS
 */
function MM_swapImgRestore() { // v3.0
	var i, x, a = document.MM_sr;
	for (i = 0; a && i < a.length && (x = a[i]) && x.oSrc; i++)
		x.src = x.oSrc;
}

function MM_preloadImages() { // v3.0
	var d = document;
	if (d.images) {
		if (!d.MM_p)
			d.MM_p = new Array();
		var i, j = d.MM_p.length, a = MM_preloadImages.arguments;
		for (i = 0; i < a.length; i++)
			if (a[i].indexOf("#") != 0) {
				d.MM_p[j] = new Image;
				d.MM_p[j++].src = a[i];
			}
	}
}

function MM_findObj(n, d) { // v4.01
	var p, i, x;
	if (!d)
		d = document;
	if ((p = n.indexOf("?")) > 0 && parent.frames.length) {
		d = parent.frames[n.substring(p + 1)].document;
		n = n.substring(0, p);
	}
	if (!(x = d[n]) && d.all)
		x = d.all[n];
	for (i = 0; !x && i < d.forms.length; i++)
		x = d.forms[i][n];
	for (i = 0; !x && d.layers && i < d.layers.length; i++)
		x = MM_findObj(n, d.layers[i].document);
	if (!x && d.getElementById)
		x = d.getElementById(n);
	return x;
}

function MM_swapImage() { // v3.0
	var i, j = 0, x, a = MM_swapImage.arguments;
	document.MM_sr = new Array;
	for (i = 0; i < (a.length - 2); i += 3)
		if ((x = MM_findObj(a[i])) != null) {
			document.MM_sr[j++] = x;
			if (!x.oSrc)
				x.oSrc = x.src;
			x.src = a[i + 2];
		}
}
/**
 * 替换单引号和双引号
 */
function replaceQuotation(quotdata) {
	return !quotdata ? quotdata : String(quotdata).replace(/\"/g, "\\\"")
			.replace(/\'/g, "&#39;");
}

/**
 * 字符截断函数
 */
function truncate(original, length, replacement) {
	var r = /[^\x00-\xff]/g;
	if (!original)
		return "undefined-params";
	if (original.replace(r, "..").length <= length)
		return original;
	var m = Math.floor(length / 2);
	for (var i = m; i < original.length; i++) {
		if (original.substr(0, i).replace(r, "..").length >= length) {
			return original.substr(0, i) + (replacement ? replacement : "...");
		}
	}
	return original;
};

/**
 * 字符截断
 */
String.prototype.truncate = function(length, replacement) {
	return truncate(this, length, replactment);
}

/**
 * 替换HTML标签
 */
String.prototype.replaceHtmlLabel = function() {
	var tag = /<(?:.|\s)*?>/g;
	return this.replace(tag, "");
}
/**
 * 显示完整的grid列信息（title对HTML标签做处理）
 */
function showFullStr(str) {
	var doubleQuotationMarkSignReg = new RegExp('"', 'ig');
	var returnValue = '';
	if (str != null && str != "") {
		returnValue = '<acronym style="font:10pts;cursor:hand;" TITLE ="'
				+ str.replace(doubleQuotationMarkSignReg, '&quot;')
						.replaceHtmlLabel() + '">' + str + '</acronym>';
	}
	return returnValue;
}
/**
 * 显示完整的grid列信息（qtip对HTML标签做处理）
 */
function showFullStrWithQtip(str) {
	var doubleQuotationMarkSignReg = new RegExp('"', 'ig');
	var returnValue = '';
	if (str != null && str != "") {
		returnValue = '<acronym style="font:10pts;cursor:hand;" ext:qwitdth="500" ext:qtip ="'
				+ str.replace(doubleQuotationMarkSignReg, '&quot;')
						.replaceHtmlLabel().replace(/&/gm, "&amp;")
				+ '">'
				+ str + '</acronym>';
	}
	return returnValue;
}
/**
 * 提示信息的简写版本
 */
function popMsg(_msg) {
	neusipo.utils.popMsg({
				title : '提示信息',
				msg : _msg
			});
}
/**
 * 性别转换显示
 */
function showSex(str) {
	var returnValue;
	if (str == "MALE") {
		str = "男";
	} else if (str == "FEMALE") {
		str = "女";
	} else if (str == "UNKNOWN") {
		str = "未知";
	}
	if (str != null && str != "") {
		returnValue = '<acronym style="font:10pts;cursor:hand;">' + str
				+ '</acronym>';
	}
	return returnValue;
}
/**
 * 显示完整的名称（列和title均不对HTML标签做处理）
 * 
 * @param {}
 *            str
 * @return {}
 */
function showWholeName(str) {
	var doubleQuotationMarkSignReg = new RegExp('"', 'ig');
	var returnValue;
	if (str != null && str != "") {
		returnValue = '<acronym style="font:10pts;cursor:hand;" TITLE ="'
				+ str.replace(doubleQuotationMarkSignReg, '&quot;').replace(
						/&/gm, "&amp;") + '">' + str.replace(/&/gm, "&amp;")
				+ '</acronym>';
	}
	return returnValue;
}

function showFullNameWithoutHtmlLabel(str) {
	var doubleQuotationMarkSignReg = new RegExp('"', 'ig');
	var returnValue;
	if (str != null && str != "") {
		returnValue = '<acronym style="font:10pts;cursor:hand;" TITLE ="'
				+ str.replace(doubleQuotationMarkSignReg, '&quot;')
						.replaceHtmlLabel() + '">' + str.replaceHtmlLabel()
				+ '</acronym>';
	}
	return returnValue;
}
/**
 * 替换超链接的title或者qtip中的“"”
 */
function replaceDoubleQuotation(str) {
	var doubleQuotationMarkSignReg = new RegExp('"', 'ig');
	var returnValue = '';
	if (str != null && str != '') {
		returnValue = str.replace(doubleQuotationMarkSignReg, '&quot;');
	}
	return returnValue;
}
/**
 * 替换“&”的显示问题
 */
function replaceAndSign(str) {
	var returnValue = '';
	if (str != null && str != '') {
		returnValue = str.replace(new RegExp("&", "gm"), "&amp;");
	}
	return returnValue;
}
/**
 * 替换超链接的title或者qtip中的“&”
 */
function replaceAndSignTip(str) {
	var returnValue = '';
	if (str != null && str != '') {
		returnValue = str.replace(new RegExp("&", "gm"), "&amp;amp;");
	}
	return returnValue;
}
/**
 * 创建一个遮住applet，activeX的iframe,来正常显示其他Ext的控件.
 * 
 * @param {}
 *            coordinate
 */
function createShim(coordinate) {
	var shimmer = document.createElement('iframe');
	if (coordinate['maskId'])
		shimmer.id = coordinate['maskId'];
	else
		shimmer.id = 'shimmer';
	shimmer.style.position = 'absolute';
	shimmer.style.top = coordinate.top;
	shimmer.style.left = coordinate.left;
	shimmer.style.width = coordinate.width;
	shimmer.style.height = coordinate.height;
	// shimmer.ALLOWTRANSPARENCY = "true";
	shimmer.style.filter = 'progid:DXImageTransform.Microsoft.Alpha(style=0,opacity=0)';
	shimmer.style.zIndex = '0';
	shimmer.setAttribute('frameborder', '0');
	document.body.appendChild(shimmer);
}

// 将<>转义
function showInnerText(val) {
	if (val != null && val != "") {
		return val.replace(/</g, '&lt;').replace(/>/g, '&gt;');
	} else {
		return val;
	}
}
// 将<>转义，并具有showFullStrWithQtip的效果
function showInnerTextWithFullStr(val) {
	if (val != null && val != "") {
		return showFullStrWithQtip(val.replace(/</g, '&lt;').replace(/>/g,
				'&gt;').replace(/&/gm, "&amp;"));
	} else {
		return val;
	}
}
// 将&转义，并具有showFullStr的效果
function showAndSignFullStr(val) {
	if (val != null && val != "") {
		return showFullStr(val.replace(new RegExp("&", "gm"), "&amp;"));
	} else {
		return val;
	}
}
/**
 * 对于输入文本是否为数字进行判断 应用：表格检索--算符输入时对“n”的替换 返回 false or true. 48~57---0~9;
 */
function checkIsNum(value) {
	var len = value.length;
	if (len == 0) {
		return false;
	}
	for (var i = 0; i < len; i++) {
		var c = value.charAt(i).charCodeAt();
		if ((c >= 48 && c <= 57)) {
			continue;
		} else {
			return false;
		}
	}
	return true;
}
var addCookie = function(objName, objValue, objHours) {// 添加cookie
	var str = objName + "=" + encodeURIComponent(objValue);
	if (objHours > 0) {// 为0时不设定过期时间，浏览器关闭时cookie自动消失
		var date = new Date();
		var ms = objHours * 3600 * 1000;
		date.setTime(date.getTime() + ms);
		str += "; expires=" + date.toGMTString();
	}
	document.cookie = str;
};
var getCookie = function(objName) {// 获取指定名称的cookie的值
	var arrStr = document.cookie.split("; ");
	for (var i = 0; i < arrStr.length; i++) {
		var temp = arrStr[i].split("=");
		if (temp[0] == objName) {
			return decodeURIComponent(temp[1]);
		}
	}
};
var delCookie = function(name) {// 为了删除指定名称的cookie，可以将其过期时间设定为一个过去的时间
	var date = new Date();
	date.setTime(date.getTime() - 10000);
	document.cookie = name + "=a; expires=" + date.toGMTString();
};

var gotoPage = function(id, start, isAjax, target) {

	if (isAjax) {
		document.forms[id].start.value = start;
		$.ajax({
					type : "POST",
					url : document.forms[id].action,
					data : $("#" + id).serialize(),
					dataType : "html",
					success : function(data) {
						$("#" + target).html(data);
					},
					error : function(data) {
						data = eval("(" + data.responseText + ")");
						popErrorMsg(data);
					}
				});
	} else {
		document.forms[id].start.value = start;
		document.forms[id].submit();
	}

};

var gotoPageNum = function(id, start, limit, totalPage, cpage, isAjax, target) {
	if (isNaN(start)) {
		start = cpage;
	} else {
		if (parseInt(start) > totalPage) {
			start = totalPage;
		}

		if (parseFloat(start) < 1) {
			start = 1;
		}
	}

	gotoPage(id, (start - 1) * limit, isAjax, target);
}

var popErrorMsg = function(data) {
	var errorWindow = $("<div class='errorMessage'><p>"
			+ data.error_msg
			+ "</p><div style='padding-left:42%,padding-top:15%'><div class='button_white color_Gray'><strong></strong><div><a href='#' class='windowClose'>&nbsp;"
			+ wee.platform.closebutton
			+ "&nbsp;</a></div><span></span></div></div></div>").createWindow({
				title : wee.platform.errorMsgTitle,
				modal : true
			});
	errorWindow.showWindow();
}
var CUR_WEE_SID = getCookie('WEE_SID');
var IS_LOGIN=getCookie('IS_LOGIN');
// 提示信息
function popMessage(msg, title, callFun, popModal, popwidth, popheight, zIndex) {
	var popWindow = null;
	if (zIndex) {
		popWindow = $("<div class='popMessage'><p>"
				+ msg
				+ "</p><div class='button_white color_Gray windowClose'><strong></strong><div><a href='#'>&nbsp;"
				+ wee.platform.okbutton
				+ "&nbsp;</a></div><span></span></div></div>").createWindow({
					title : title ? title : wee.platform.popDefaultTitle,
					top : '30%',
					left : '40%',
					width : popwidth ? popwidth : 350,
					height : popheight ? popheight : 170,
					modal : popModal!=null ? popModal : true,
					close : callFun ? callFun : function() {
					},
					"z-index" : zIndex
				});
	} else {
		popWindow = $("<div class='popMessage'><p>"
				+ msg
				+ "</p><div class='button_white color_Gray windowClose'><strong></strong><div><a href='#'>&nbsp;"
				+ wee.platform.okbutton
				+ "&nbsp;</a></div><span></span></div></div>").createWindow({
					title : title ? title : wee.platform.popDefaultTitle,
					top : '30%',
					left : '40%',
					width : popwidth ? popwidth : 350,
					height : popheight ? popheight : 170,
					modal : popModal!=null ? popModal : true,
					close : callFun ? callFun : function() {
					}
				});
	}
	popWindow.showWindow();
}

// 确认信息
function confirmMessage(msg) {
	return confirm(msg);
}
