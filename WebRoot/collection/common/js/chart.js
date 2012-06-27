$(function(){
	createChart();
});
function createChart(){
	var html="";
	html+="<table  align='center' cellpadding='0' cellspacing='0' width='98%' style='font-size:12px;'>";
	$.each(resultList,function(i,data){
		html+="<tr>";
		html+="<td wdith='3%' height='27'><div align='center'><img src='../solution/common/images/icon/jt.gif' width='7' height='9'></div></td>";
		html+="<td width='31%' align='left'>"+data.title+"</td>";
		html+="<td width='46%'>";
		var cssClass = 'vote_result'+i%10;
		if(data.count!=0 && total!=0){
			html+="<div  style='float:left;width:"+((100*parseFloat(data.count)/parseFloat(total))/1.5).toFixed(2)+"%;height:15px;' class="+cssClass+"></div>";
			html+="</td>";
			html+="<td width='20%' align='right'>";
			html+=data.count;
			html+="("+(100*parseFloat(data.count)/parseFloat(total)).toFixed(2)+"%)</td>";
		}else{
			html+="<div style='float:left;width:2px;height:15px;' class="+cssClass+"></div>";
			html+="</td>";
			html+="<td width='20%' align='right'>";
			html+=data.count;
			html+="(0%)</td>";
		}
		html+="</tr>";
	});
	html+="</table>";
	$('#custom_chart').html(html);
}
function createChartJustVoted(){
	if("undefined"==typeof($('#custom_chart_just_voted'))){
		return;
	}
	var html="";
	html+="<table  align='center' cellpadding='0' cellspacing='0' width='98%' border='0' height='28'>";
	$.each(resultList,function(i,data){
		html+="<tr>";
		html+="<td wdith='3%' height='27' background='common/images/linebg.jpg'><div align='center'><img src=''../solution/common/images/icon/jt.gif' width='7' height='9'></div></td>";
		if(data.isAdopt=='1'){
			html+="<td width='31%' background='common/images/linebg.jpg' class='FontBlackEnglish9' style='word-break:break-all'>";
			html+="<a href='#' onclick=showSolutionDetail('"+data.id+"','forVote');><font color='red'>"+data.title+"</font></a></td>";
			html+="<td width='36%' background='common/images/linebg.jpg' class='FontBlackEnglish9'><font color='red'>由&nbsp;&nbsp;"+data.name+"&nbsp;&nbsp;在 <span class='Font9Gray'><font color='red'>"+data.date+"</font></span> 上传</font></td>";
		}else{
			html+="<td width='31%' background='common/images/linebg.jpg' class='FontBlackEnglish9' style='word-break:break-all'>";
			html+="<a href='#' onclick=showSolutionDetail('"+data.id+"','forVote');>"+data.title+"</a></td>";
			html+="<td width='36%' background='common/images/linebg.jpg' class='FontBlackEnglish9'>由&nbsp;&nbsp;"+data.name+"&nbsp;&nbsp;在 <span class='Font9Gray'>"+data.date+"</span> 上传</td>";
		}
		html+="<td id='oper"+data.id+"' width='20%' background='common/images/linebg.jpg' class='FontBlackEnglish9' style='padding-bottom:2px'>";
		var cssClass = 'vote_result'+i%10;
		if(data.count!=0 && total!=0){
			html+="<div  style='float:left;width:"+((100*parseFloat(data.count)/parseFloat(total))/1.5).toFixed(2)+"%;height:15px;' class="+cssClass+"></div>";
			html+="</td>";
			html+="<td width='10%' align='left' background='common/images/linebg.jpg' class='FontBlackEnglish9'>";
			html+=data.count;
			html+="("+(100*parseFloat(data.count)/parseFloat(total)).toFixed(2)+"%)</td>";
		}else{
			html+="<div style='float:left;width:2px;height:15px;' class="+cssClass+"></div>";
			html+="</td>";
			html+="<td width='10%' align='left' background='common/images/linebg.jpg' class='FontBlackEnglish9'>";
			html+=data.count;
			html+="(0%)</td>";
		}
		html+="</tr>";
	});
	html+="</table>";
	$('#custom_chart_just_voted').html(html);
}