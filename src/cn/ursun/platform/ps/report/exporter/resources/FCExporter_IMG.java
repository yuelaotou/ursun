/**
 * 文件名：FCExporter_IMG.java
 *
 * 创建人：【周洋】 - 【zhouyang@neusoft.com】
 *
 * 创建时间：Dec 23, 2009 10:26:55 AM
 *
 * 版权所有：东软软件股份有限公司
 */
package cn.ursun.platform.ps.report.exporter.resources;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;

import javax.imageio.ImageIO;
import javax.imageio.stream.ImageOutputStream;
import javax.servlet.http.HttpServletResponse;

import cn.ursun.platform.core.exception.BizException;
import cn.ursun.platform.ps.report.exporter.beans.ChartMetadata;
import cn.ursun.platform.ps.report.exporter.beans.ExportBean;
import cn.ursun.platform.ps.report.exporter.generators.ImageGenerator;

/**
 * <p>【图表处理：图像类】</p>
 * @author 【周洋】 - 【zhouyang@neusoft.com】
 * @version 1.0
 */
public class FCExporter_IMG extends FCExporter_Format {

	public FCExporter_IMG()
    {
        exportBean = null;
    }
	/**
	 * <p>【图表流输出或保存服务器端】</p>
	 * @param obj
	 * @param httpservletresponse
	 * @return
	 * @author: 【周洋】 - 【zhouyang@neusoft.com】
	 * @throws IOException 
	 * @data: Create on Dec 23, 2009 10:26:55 AM
	 */
	@Override
	public InputStream exportOutput(Object exportObj, HttpServletResponse response) throws BizException {
		InputStream is = null;
		String exportFormat;
		BufferedImage chartImage;
        boolean isHTML;
        String action = (String)exportBean.getExportParameterValue("exportaction");
        exportFormat = (String)exportBean.getExportParameterValue("exportformat");
        
        chartImage = (BufferedImage)exportObj;
        isHTML = false;
        if(action.equals("download"))
            isHTML = true;
        
        String meta_values = exportBean.getMetadataAsQueryString(null, false, isHTML);
      	if(!exportFormat.toLowerCase().equalsIgnoreCase("jpg") && !exportFormat.toLowerCase().equalsIgnoreCase("jpeg"))
        {
	        try
		        {
		        	  ByteArrayOutputStream   bs   =new   ByteArrayOutputStream();  
		        	  ImageOutputStream   imOut   =ImageIO.createImageOutputStream(bs);  
		        	  ImageIO.write(chartImage,"jpeg",imOut);   //scaledImage1为BufferedImage，jpg为图像的类型  
		        	  is =new   ByteArrayInputStream(bs.toByteArray());
		        }
		        catch(Throwable e)
		        {
		            throw new BizException("Unable to (JPEG) encode the buffered image");
		           
		        }
		        chartImage = null;
	        }else
	        {
	        	try
		        {
		        	  ByteArrayOutputStream   bs   =new   ByteArrayOutputStream();  
		        	  ImageOutputStream   imOut   =ImageIO.createImageOutputStream(bs);  
		        	  ImageIO.write(chartImage,"png",imOut);   //scaledImage1为BufferedImage，jpg为图像的类型  
		        	  is =new ByteArrayInputStream(bs.toByteArray());
		        }
		        catch(Throwable e)
		        {
		            throw new BizException("Unable to (JPEG) encode the buffered image");
		           
		        }
		        chartImage = null;
	        }
            try
            {
            	
            	PrintWriter out = response.getWriter();
                out.print(meta_values + "&statusCode=1&statusMessage=successful");
            }
            catch(IOException e)
            {
                e.printStackTrace();
            }
		return is;
    }

	/**
	 * <p>【导出图表生成器】</p>
	 * @param exportbean
	 * @return
	 * @author: 【周洋】 - 【zhouyang@neusoft.com】
	 * @data: Create on Dec 23, 2009 10:26:55 AM
	 */
	@Override
	public Object exportProcessor(ExportBean pExportBean) {
		exportBean = pExportBean;
        String stream = exportBean.getStream();
        ChartMetadata metadata = exportBean.getMetadata();
        BufferedImage chartImage = ImageGenerator.getChartImage(stream, metadata);
        return chartImage;
	}
    private ExportBean exportBean;
}
