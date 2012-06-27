/**
 * 文件名：FCExporter_PDF.java
 *
 * 创建人：【周洋】 - 【zhouyang@neusoft.com】
 *
 * 创建时间：Dec 23, 2009 10:30:45 AM
 *
 * 版权所有：东软软件股份有限公司
 */
package cn.ursun.platform.ps.report.exporter.resources;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletResponse;

import cn.ursun.platform.ps.report.exporter.FusionChartsExportHelper;
import cn.ursun.platform.ps.report.exporter.beans.ChartMetadata;
import cn.ursun.platform.ps.report.exporter.beans.ExportBean;
import cn.ursun.platform.ps.report.exporter.generators.PDFGenerator;

/**
 * <p>【】</p>
 * @author 【周洋】 - 【zhouyang@neusoft.com】
 * @version 1.0
 */
public class FCExporter_PDF extends FCExporter_Format {

	public FCExporter_PDF()
    {
        exportBean = null;
    }
	/**
	 * <p>【导出为PDF文件流或在服务器端存储为PDF文件】</p>
	 * @param obj
	 * @param httpservletresponse
	 * @return
	 * @author: 【周洋】 - 【zhouyang@neusoft.com】
	 * @data: Create on Dec 23, 2009 10:30:46 AM
	 */
	@Override
	public InputStream exportOutput(Object exportObj, HttpServletResponse response)
    {
		InputStream is = null;    
		byte pdfBytes[] = (byte[])exportObj;
        String exportFormat = (String)exportBean.getExportParameterValue("exportformat");
       
        boolean isHTML = false;
        String meta_values = exportBean.getMetadataAsQueryString(null, false, isHTML);
        
        is = new ByteArrayInputStream(pdfBytes);
    	meta_values = exportBean.getMetadataAsQueryString(null, true, isHTML);
        try
        {
            PrintWriter out = response.getWriter();
            out.print(meta_values  + "&statusCode=1&statusMessage=successful");
            response.setContentType(FusionChartsExportHelper.getMimeTypeFor(exportFormat.toLowerCase()));
        }
        catch(IOException e)
        {
            e.printStackTrace();
        }

        return is;
	}

	/**
	 * <p>【调用PDF生成器】</p>
	 * @param exportbean
	 * @return
	 * @author: 【周洋】 - 【zhouyang@neusoft.com】
	 * @data: Create on Dec 23, 2009 10:30:46 AM
	 */
	@Override
    public Object exportProcessor(ExportBean pExportBean)
    {
        exportBean = pExportBean;
        String stream = exportBean.getStream();
        ChartMetadata metadata = exportBean.getMetadata();
        PDFGenerator pdf = new PDFGenerator(stream, metadata);
        byte pdfBytes[] = pdf.getPDFObjects(true);
        return pdfBytes;
    }

    private ExportBean exportBean;

}
