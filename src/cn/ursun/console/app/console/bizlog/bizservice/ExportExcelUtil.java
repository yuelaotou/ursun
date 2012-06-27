package cn.ursun.console.app.console.bizlog.bizservice;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;

import jxl.Workbook;
import jxl.format.Colour;
import jxl.format.UnderlineStyle;
import jxl.write.Label;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;
import jxl.write.biff.RowsExceededException;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.Node;

import cn.ursun.console.app.domain.BizLog;
import cn.ursun.platform.core.exception.SystemException;
import cn.ursun.platform.ps.security.WeeSecurityInfo;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.LocaleProvider;
import com.opensymphony.xwork2.TextProvider;
import com.opensymphony.xwork2.TextProviderFactory;


public class ExportExcelUtil implements LocaleProvider{

	private static final Log log = LogFactory.getLog(ExportExcelUtil.class);
	
	private final transient TextProvider textProvider = new TextProviderFactory().createInstance(getClass(),this);

	private WritableCellFormat detailFormat;

	private WritableSheet sheet = null;

	private SimpleDateFormat dateformat;

	public Locale getLocale() {
		return ActionContext.getContext().getLocale();
	}
	
	public ExportExcelUtil() {
		dateformat = new SimpleDateFormat("yyyy-MM-dd H:mm:ss");
		detailFormat = new WritableCellFormat();
		try {
			detailFormat.setWrap(true);
			detailFormat.setAlignment(jxl.format.Alignment.LEFT);
		} catch (WriteException e) {
			e.printStackTrace();
		}
	}

	/**
	 * <p>生成Excel文件</p>
	 * 
	 * @param bizLogList 日志类别
	 * @return 生成的excel文件
	 * @author: 陈乃明 - chennm@neusoft.com
	 * @param tempPath 
	 * @date: Created on Feb 20, 2009 7:31:25 PM
	 */
	public File createExcelByFile(List<BizLog> bizLogList, String tempPath) {
		File dir = null;
		File file = null;
		dir = new File(tempPath);
		if (!dir.exists()) {
			dir.mkdirs();
		}
		String fileName = WeeSecurityInfo.getInstance().getUserId() + "_" + System.currentTimeMillis() + "_"+textProvider.getText("bizlog.exportfilename")+".xls";
		file = new File(tempPath + fileName);
		WritableWorkbook book = null;
		try {
			book = Workbook.createWorkbook(file);
			// 生成名为“第一页”的工作表，参数0表示这是第一页
			sheet = book.createSheet(textProvider.getText("bizlog.exportsheetname"), 0);
			if (bizLogList == null || bizLogList.size() == 0) {
				book.write();
				book.close();
				return file;
			}
			// 表头格式
			WritableFont cnContentBlockFont = new WritableFont(WritableFont.createFont(textProvider.getText("bizlog.exportfont")), 10, WritableFont.BOLD,
					false, UnderlineStyle.NO_UNDERLINE, Colour.BLACK);
			WritableCellFormat headerFormat = new WritableCellFormat(cnContentBlockFont);
			// 水平居中
			headerFormat.setAlignment(jxl.format.Alignment.CENTRE);
			// 背景色
			headerFormat.setBackground(jxl.format.Colour.GRAY_25);
			// Label(列, 行, 文字, 样式);
			addData(0, 0, textProvider.getText("bizlog.exportopername"), headerFormat);
			addData(1, 0, textProvider.getText("bizlog.exportsex"), headerFormat);
			addData(2, 0, textProvider.getText("bizlog.exportdept"), headerFormat);
			addData(3, 0, textProvider.getText("bizlog.exportloginname"), headerFormat);
			addData(4, 0, textProvider.getText("bizlog.exportloginip"), headerFormat);
			addData(5, 0, textProvider.getText("bizlog.exportopertime"), headerFormat);
			/////////////////////add by wangmin 20100621 begin 增加功能等级一列
			addData(6, 0, textProvider.getText("bizlog.function"), headerFormat);
			/////////////////////add by wangmin 20100621 end 增加功能等级一列	
			addData(7, 0, textProvider.getText("bizlog.exportdescription"), headerFormat);
			//addData(8, 0, "日志信息", headerFormat);

			// 内容格式
			WritableCellFormat dataFormat = new WritableCellFormat();
			// 水平居中
			dataFormat.setAlignment(jxl.format.Alignment.CENTRE);
			int lineNum;
			for (int j = 0; j < bizLogList.size(); j++) {
				BizLog log = bizLogList.get(j);
				lineNum = j + 1;
				addData(0, lineNum, log.getOperatorName(), dataFormat);
				addData(1, lineNum, showSex(log.getSex()), dataFormat);
				addData(2, lineNum, log.getDept(), dataFormat);
				addData(3, lineNum, log.getLoginName(), dataFormat);
				addData(4, lineNum, log.getIp(), dataFormat);
				addData(5, lineNum, dateformat.format(log.getOperationDate()), dataFormat);
				/////////////////////add by wangmin 20100621 begin 增加功能等级一列				
				creatFunction(lineNum, log, dataFormat);
				/////////////////////add by wangmin 20100621 end 增加功能等级一列	
				createDetailInfo(lineNum, log.getDetail(), dataFormat);
			}
			sheet.setColumnView(0, 15);
			sheet.setColumnView(1, 5);
			sheet.setColumnView(2, 10);
			sheet.setColumnView(3, 15);
			sheet.setColumnView(4, 15);
			sheet.setColumnView(5, 25);
			sheet.setColumnView(6, 40);
			sheet.setColumnView(7, 50);
			//sheet.setColumnView(8, 80);
			book.write();
			book.close();
		} catch (IOException e) {
			log.error("create excel file error:" + e);
			throw new SystemException(e);
		} catch (WriteException e) {
			log.error("create excel file error:" + e);
			throw new SystemException(e);
		} catch (Exception e) {			
			log.error(e.getMessage());
			throw new SystemException(e);
		}
		return file;
	}

	/**
	 * 生成功能等级
	 * @param lineNum
	 * @param log
	 * @param dataFormat
	 */
	private void creatFunction(int lineNum, BizLog bizlogrow, WritableCellFormat dataFormat){
		String module = bizlogrow.getModuleLevel1();
		if(!StringUtils.isEmpty(bizlogrow.getModuleLevel2())){
			module += "->";
			module += bizlogrow.getModuleLevel2().trim();
		}
		if(!StringUtils.isEmpty(bizlogrow.getModuleLevel3())){
			module += "->";
			module += bizlogrow.getModuleLevel3().trim();
		}
		if(!StringUtils.isEmpty(bizlogrow.getModuleLevel4())){
			module += "->";
			module += bizlogrow.getModuleLevel4().trim();
		}
		if(!StringUtils.isEmpty(bizlogrow.getModuleLevel5())){
			module += "->";
			module += bizlogrow.getModuleLevel5().trim();
		}
		if(!StringUtils.isEmpty(bizlogrow.getModuleLevel6())){
			module += "->";
			module += bizlogrow.getModuleLevel6().trim();
		}
		if(!StringUtils.isEmpty(bizlogrow.getModuleLevel7())){
			module += "->";
			module += bizlogrow.getModuleLevel7().trim();
		}
		//bizlogrow.setModuleLevel1(module);
		addData(6, lineNum, module.equals("null") ? "" : module, dataFormat);
		//addData(8, lineNum, sb.toString(), detailFormat);
	}
	
	/**
	 * <p>显示性别中文名</p>
	 * 
	 * @param sex
	 * @return
	 * @author: 陈乃明 - chennm@neusoft.com
	 * @date: Created on Mar 11, 2009 4:13:40 PM
	 */
	private String showSex(String sex) {
		if ("MALE".equals(sex)) {
			sex = textProvider.getText("bizlog.exportmale");
		} else if ("FEMALE".equals(sex)) {
			sex = textProvider.getText("bizlog.exportfemale");
		} else if ("UNKNOWN".equals(sex)){
			sex = textProvider.getText("bizlog.exportunknow");
		}
		return sex;
	}

	/**
	 * <p>显示操作类型的中文名称</p>
	 * 
	 * @param operationType
	 * @return
	 * @author: 陈乃明 - chennm@neusoft.com
	 * @date: Created on Mar 11, 2009 3:51:16 PM
	 */
	public String showOper(String operationType) {
		if(operationType != null ){
			if (operationType.equals("USE")) {
				operationType = textProvider.getText("bizlog.exportuse");
			} else if (operationType.equals("SEARCH")) {
				operationType = textProvider.getText("bizlog.exportsearch");
			} else if (operationType.equals("MAINTAIN")) {
				operationType = textProvider.getText("bizlog.exportmaintain");
			} else if (operationType.equals("VIEW")) {
				operationType = textProvider.getText("bizlog.exportview");
			} else if (operationType.equals("LOGIN")) {
				operationType = textProvider.getText("bizlog.exportlogin");
			} else if (operationType.equals("LOGOUT")) {
				operationType = textProvider.getText("bizlog.exportlogout");
			} else if (operationType.equals("IMPORT")) {
				operationType = textProvider.getText("bizlog.exportimport");
			} else if (operationType.equals("EXPORT")) {
				operationType = textProvider.getText("bizlog.exportexport");
			} else if (operationType.equals("GROUP")) {
				operationType = textProvider.getText("bizlog.exportsort");
			} else if (operationType.equals("WEBSPIDER")) {
				operationType = textProvider.getText("bizlog.exportwebspider");
			}
		}else{
		}
		return operationType;
	}

	/**
	 * 
	 * <p>【请将此部分整体替换为对此类型的描述】</p>
	 * @param lineNum
	 * @param datail
	 * @param dataFormat
	 * @author: 【作者名称】 - 【作者邮箱】
	 * @data: Create on 2010-1-13 下午02:43:33
	 */
	@SuppressWarnings("unchecked")
	private void createDetailInfo(int lineNum, String datail, WritableCellFormat dataFormat) {
		try {
			Document document = DocumentHelper.parseText(datail);
			StringBuffer sb = new StringBuffer();
			//Element el = document.getRootElement();
			Node description = document.selectSingleNode("/log/description");
			String desc = description.getText();
			List<Object[]> groups = document.selectNodes("/log/group");
			Iterator it = groups.iterator();
			while (it.hasNext()) {
				Node group = (Node) it.next();
				String gropNamec = group.selectSingleNode("./@name").getText();
				sb.append(gropNamec).append(":").append("\r\n");
				List propertys = (List) group.selectNodes("./property");
				Iterator it2 = propertys.iterator();
				while (it2.hasNext()) {
					Element property = (Element) it2.next();
					String prop = property.selectSingleNode("./@name").getText();
					String val = property.selectSingleNode("./@value").getText();
					sb.append(StringUtils.defaultIfEmpty(prop, "")).append("   : ").append(
							StringUtils.defaultIfEmpty(val, "")).append(";  ");
					if (it2.hasNext()) {
						sb.append("\r\n");
					}
				}
				if (it.hasNext()) {
					sb.append("\r\n\r\n");
				}
			}
			addData(7, lineNum, desc.equals("null") ? textProvider.getText("bizlog.exportnomsg") : desc, dataFormat);
			//addData(8, lineNum, sb.toString(), detailFormat);
		} catch (DocumentException e) {
			log.error("create excel file error:" + e);
			throw new SystemException(e);
		}
	}

	/**
	 * <p>添加行数据</p>
	 * 
	 * @param col 添加数据的列
	 * @param row 添加数据的行
	 * @param data 要添加的数据
	 * @param format 数据的样式
	 * @author: 陈乃明 - chennm@neusoft.com
	 * @date: Created on Feb 23, 2009 5:07:28 PM
	 */
	private void addData(int col, int row, String data, WritableCellFormat format) {
		Label label80 = new Label(col, row, data, format);
		try {
			sheet.addCell(label80);
		} catch (RowsExceededException e) {
			log.error("create excel file error:" + e);
			throw new SystemException(e);
		} catch (WriteException e) {
			log.error("create excel file error:" + e);
			throw new SystemException(e);
		}
	}
}
