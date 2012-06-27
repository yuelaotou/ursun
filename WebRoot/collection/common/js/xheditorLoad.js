$(document).ready(function(){
			//初始化xhEditor编辑器插件
			$('#xh_editor').xheditor({
				tools:'Blocktag,Fontface,FontSize,Bold,Italic,Underline,Strikethrough,FontColor,BackColor,SelectAll,Removeformat,Separator,Align,Separator,Img,Emot',
				skin:'vista',
				forcePtag:false,
				forcePasteText:true,
                //upImgUrl:'!{editorRoot}xheditor_plugins/multiupload/multiupload.html?uploadurl=solutionAC!submitSolution.do?&params=img&immediate=1&ext=图片文件(*.jpg;*.jpeg;*.gif;*.png)&size=2 MB'
				upImgUrl: "../solution/attachmentAC!uploadPicFile.do",
				upImgExt: "jpg,jpeg,gif,bmp,png",
				onUpload:insertUpload
			});
			
			//xbhEditor编辑器图片上传回调函数
			function insertUpload(msg) {
				var _msg = msg.toString();
				var _picture_name = _msg.substring(_msg.lastIndexOf("/")+1);
				var _picture_path = Substring(_msg);
				var _str = "<input type='checkbox' name='_pictures' value='"+_picture_path+"' checked='checked' onclick='return false'/><label>"+_picture_name+"</label><br/>";
				$("#xh_editor").append(_msg);
			}
			//处理服务器返回到回调函数的字符串内容,格式是JSON的数据格式.
			function Substring(s){
				return s.substring(s.substring(0,s.lastIndexOf("/")).lastIndexOf("/"),s.length);
			}
		});