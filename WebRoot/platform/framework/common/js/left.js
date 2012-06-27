//默认的菜单选项
$(function(){
	$.ajax({
     	type: "post",
      	url: contextPath + "/console/showMenuAC!queryMainMenuList.do",
      	data: "",
      	async:false,
      	success: function(mainResult){
	      	var mainMenu=eval("("+mainResult+")");
			var html = "";
			var urlList = [];
			var n = 0;
	      	$.each(mainMenu.mainMenuList, function(i, value) {
				if(i==0){
					html = html + "<div id=\"accordion\" class=\"arrowlistmenu\">";
				}
				$.ajax({
			     	type: "post",
			      	url: contextPath + "/console/showMenuAC!queryChildMenuListWithMenuID.do",
			      	data: "menu_id="+value.key,
			      	async:false,
			      	success: function(childResult){
				      	var childMenu=eval("("+childResult+")");
				      	$.each(childMenu.childMenuList, function(j, value) {
							if(j==0){
					      		html = html + "	<h3 id=\"mainMenuList_"+i+"\" class=\"menuheader expandable\">";
								html = html + "		<a>"+value.title+"</a>";
								html = html + "	</h3>";
								html = html + "	<div>";
								html = html + "		<ul class=\"categoryitems\">";
							}
							urlList[n] = value.url;
							html = html + "			<li id=\"childMenuList_" + (n++) + "\">";
							html = html + "				<a>"+value.title+"</a>";
							html = html + "			</li>";
							if(childMenu.childMenuList.length == j+1){
								html = html + "		</ul>";
								html = html + "	</div>";
							}
						});
			      	}
			    });
				if(mainMenu.mainMenuList.length == i+1){
					html = html + "		<div id=\"copyright\" style=\"background: url('"+contextPath+"/platform/framework/common/images/main_69.gif');width: 147px;height:19px;text-align:center\">";
					html = html + "			<span class=\"linkTitle\">版本：2010 v1.0</span>";
					html = html + "		</div>";
					html = html + "	</div>";
				}
			});
			$("body").html(html);
			
			var mouseovercolor = "#d5f4fe";
			var mouseoutcolor = "#FFFFFF";
			var clickcolor = "#51b2f6";
			//子菜单的鼠标经过事件
			$("[id^=childMenuList_]").hover(
				//当鼠标经过
				function () {
					//若当前颜色为鼠标点击的颜色，则不更改它的颜色。否则更新为鼠标经过的颜色
					if($(this).css("background-color")!=clickcolor && $(this).css("background-color")!=hex2rgb(clickcolor)){
						$(this).css("background-color",mouseovercolor);
					}
					$(this).find("a:eq(0)").attr("title",$(this).find("a:eq(0)").html());
				},
				//当鼠标移出
				function () {
					//若当前颜色为鼠标点击的颜色，则不更改它的颜色。否则更新为鼠标移出的颜色
					if($(this).css("background-color")!=clickcolor && $(this).css("background-color")!=hex2rgb(clickcolor)){
						$(this).css("background-color",mouseoutcolor);
					}
				}
			);
			//父菜单的鼠标经过事件
			$("[id^=mainMenuList_]").hover(
				//当鼠标经过
				function () {
					$(this).find("a:eq(0)").attr("title",$(this).find("a:eq(0)").html());
				},
				//当鼠标移出
				function () {
				}
			);
			//子菜单的单击事件
			$("[id^=childMenuList_]").click(function() {
				//设置当前点击的行颜色为鼠标点击，其余的为鼠标移出.siblings()不能取消其他主菜单下被选中的。
				$("[id^=childMenuList_]").css("background-color",mouseoutcolor);
				$(this).css("background-color",clickcolor);
				//找到当前点击的行的序号，根据序号为contenFrame添加src
				var i = $(this).attr("id").replace("childMenuList_","");
				window.parent.$("[name=contentFrame]").attr("src", urlList[i]);
				//设置系统状态栏文字显示
				window.status=$(this).find("a:eq(0)").html();
			});
      	}
    });
    $('#accordion').accordion({
		header: 'h3',
		animated: "bounceslide",
		fillSpace: true
	});
	$(window).resize(function() {
		$('#accordion').accordion( "resize" );
	});
});
/*
ddaccordion.init({
	headerclass: "expandable", //Shared CSS class name of headers group that are expandable
	contentclass: "categoryitems", //Shared CSS class name of contents group
	revealtype: "click", //如何触发展开合并的动作? 值为: "click", "clickgo", or "mouseover"
	mouseoverdelay: 200, //如果revealtype = "mouseover",设定延时多久执行onMouseover的动作毫秒
	collapseprev: true, //一次只能展开一个内容? true/false 
	defaultexpanded: [0], //默认展开的第几个，从0开始 [index1, index2, etc]. [] 不展开内容
	onemustopen: true, //是否永远有一个是展开的 (所以不会全部关闭)true/false
	animatedefault: true, //默认展开用动态效果吗?true/false
	persiststate: true, //是否记忆浏览器session，打开上次的地方?
	toggleclass: ["", "openheader"], //关闭和展开时，头部分别对应的css样式为 ["class1", "class2"]
	togglehtml: ["prefix", "", ""], //额外的HTML添加到头时,它的合并和展开,分别为  ["position", "html1", "html2"] (see docs)
	animatespeed: "fast", //动画的速度在几毫秒内的整数(如:200),或关键字 "fast", "normal", or "slow"
	accordiontype:"split",//手风琴导航，铺开还是靠上紧挨着。"split", "close"
	oninit:function(headers, expandedindices){ //当执行后的自定义代码
		//do nothing
	},
	onopenclose:function(header, index, state, isuseractivated){ //custom code to run whenever a header is opened or closed
		//do nothing
	}
});
*/
/**
*将16进制颜色转换成rgb
*/
function hex2rgb(a){
	a=a.substring(1);
	a=a.toLowerCase();
	b=new Array();
	for(x=0;x<3;x++){
	   b[0]=a.substr(x*2,2);
	   b[3]="0123456789abcdef";
	   b[1]=b[0].substr(0,1);
	   b[2]=b[0].substr(1,1);
	   b[20+x]=b[3].indexOf(b[1])*16+b[3].indexOf(b[2]);
	}
	return "rgb(" +b[20]+", "+b[21]+", "+b[22]+ ")";
}