/**
 * 文件名：FCExporter.java
 *
 * 创建人：【周洋】 - 【zhouyang@neusoft.com】
 *
 * 创建时间：Dec 23, 2009 10:23:52 AM
 *
 * 版权所有：东软软件股份有限公司
 */
package cn.ursun.platform.ps.report.exporter;

import java.io.InputStream;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.ursun.platform.core.exception.BizException;
import cn.ursun.platform.ps.report.exporter.beans.ExportBean;
import cn.ursun.platform.ps.report.exporter.resources.FCExporter_Format;

/**
 * <p>【报表导出入口类】</p>
 * @author 【周洋】 - 【zhouyang@neusoft.com】
 * @version 1.0
 */
public class Exporter {

	public static boolean availiable = true;

	private static Exporter exporter = null;
	
    public static Exporter getInstance()
    {
       if(exporter == null&&(availiable)){
    	   exporter = new Exporter();
       }
       return exporter;
    }
    
    public InputStream getImage(HttpServletRequest request, HttpServletResponse response)
        throws BizException
    {
    	InputStream is = null;
        String WEB_ROOT_PATH = this.getClass().getClassLoader().getResource("/").getPath();
        ExportBean localExportBean = FusionChartsExportHelper.parseExportRequestStream(request);
        String exportFormat = (String)localExportBean.getExportParameterValue("exportformat");
        FusionChartsExportHelper.getExporterFilePath(exportFormat);
        StringBuffer err_warn_Codes = new StringBuffer();
        if(localExportBean.getMetadata().getWidth() == -1 || localExportBean.getMetadata().getHeight() == -1 || localExportBean.getMetadata().getWidth() == 0 || localExportBean.getMetadata().getHeight() == 0)
            throw new BizException("没有获取到报表的宽与高");
        if(localExportBean.getMetadata().getBgColor() == null)
        	throw new BizException("没有获取到报表的背景颜色");
        if(localExportBean.getStream() == null)
        	throw new BizException("没有获取到报表的图像流数据");
        String exportAction = (String)localExportBean.getExportParameterValue("exportaction");
        
        if(!exportAction.equals("download"))
        {
            String fileNameWithoutExt = (String)localExportBean.getExportParameterValue("exportfilename");
            String extension = FusionChartsExportHelper.getExtensionFor(exportFormat.toLowerCase());
            String fileName = fileNameWithoutExt + "." + extension;
            err_warn_Codes.append(ErrorHandler.checkServerSaveStatus(WEB_ROOT_PATH, fileName));
        }
        String pathToWebAppRoot = this.getClass().getClassLoader().getResource("/").getPath();;
        localExportBean.addExportParameter("webapproot", pathToWebAppRoot);
        String path = FusionChartsExportHelper.getExporterFilePath(exportFormat);
        int indexOfDot = path.lastIndexOf(".");
        String exporterClassName = path.substring(0, indexOfDot);
        try
        {
            Class<?> exporterClass = Class.forName(exporterClassName);
            FCExporter_Format fcExporter = (FCExporter_Format)exporterClass.newInstance();
            Object exportObject = fcExporter.exportProcessor(localExportBean);
            fcExporter.exportOutput(exportObject, response);
        }
        catch(ClassNotFoundException e)
        {
        	throw new BizException("无法找到跳转类");
        }
        catch(InstantiationException e){
        	e.printStackTrace();
        } catch (IllegalAccessException e) {
			e.printStackTrace();
		}
        return is;
    }


    private static final long serialVersionUID = 1L;
}
