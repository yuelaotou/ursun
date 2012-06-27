/**
 * 文件名：FusionChartsExportHelper.java
 *
 * 创建人：【周洋】 - 【zhouyang@neusoft.com】
 *
 * 创建时间：Dec 23, 2009 1:41:14 PM
 *
 * 版权所有：东软软件股份有限公司
 */
package cn.ursun.platform.ps.report.exporter;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Properties;
import java.util.StringTokenizer;
import java.util.UUID;
import java.util.logging.Logger;

import javax.servlet.http.HttpServletRequest;

import cn.ursun.platform.ps.report.exporter.beans.ChartMetadata;
import cn.ursun.platform.ps.report.exporter.beans.ExportBean;

/**
 * <p>【报表导出图表属性配置】</p>
 * @author 【周洋】 - 【zhouyang@neusoft.com】
 * @version 1.0
 */
public class FusionChartsExportHelper {

    public FusionChartsExportHelper()
    {
    }

    public static ExportBean parseExportRequestStream(HttpServletRequest exportRequestStream)
    {
        ExportBean exportBean = new ExportBean();
        String stream = exportRequestStream.getParameter("stream");
        String parameters = exportRequestStream.getParameter("parameters");
        ChartMetadata metadata = new ChartMetadata();
        String strWidth = exportRequestStream.getParameter("meta_width");
        metadata.setWidth(Integer.parseInt(strWidth));
        String strHeight = exportRequestStream.getParameter("meta_height");
        metadata.setHeight(Integer.parseInt(strHeight));
        String bgColor = exportRequestStream.getParameter("meta_bgColor");
        String DOMId = exportRequestStream.getParameter("meta_DOMId");
        metadata.setDOMId(DOMId);
        metadata.setBgColor(bgColor);
        exportBean.setMetadata(metadata);
        exportBean.setStream(stream);
        HashMap<String, String> exportParamsFromRequest = bang(parameters);
        exportBean.addExportParametersFromMap(exportParamsFromRequest);
        return exportBean;
    }

    public static String getExporterFilePath(String strFormat)
    {
        String exporterSuffix = (String)handlerAssociationsMap.get(strFormat) == null ? strFormat : (String)handlerAssociationsMap.get(strFormat);
        String path = (new StringBuilder(String.valueOf(RESOURCEPATH))).append(EXPORTHANDLER).append(exporterSuffix.toUpperCase()).append(".jsp").toString();
        return path;
    }

    public static HashMap<String, String> bang(String strParams)
    {
        HashMap<String, String> params = new HashMap<String, String>();
        for(StringTokenizer stPipe = new StringTokenizer(strParams, "|"); stPipe.hasMoreTokens();)
        {
            String keyValue = stPipe.nextToken();
            String keyValueArr[] = keyValue.split("=");
            if(keyValueArr.length > 1)
                params.put(keyValueArr[0].toLowerCase(), keyValueArr[1]);
        }

        return params;
    }

    public static HashMap<String, String> getMimeTypes()
    {
        return mimeTypes;
    }

    public static String getMimeTypeFor(String format)
    {
        return (String)mimeTypes.get(format);
    }

    public static String getExtensionFor(String format)
    {
        return (String)extensions.get(format);
    }

    public static String getUniqueFileName(String filePath, String extension)
    {
        UUID uuid = UUID.randomUUID();
        String uid = uuid.toString();
        String uniqueFileName = (new StringBuilder(String.valueOf(filePath))).append(".").append(extension).toString();
        do
        {
            uniqueFileName = filePath;
            if(!FILESUFFIXFORMAT.equalsIgnoreCase("TIMESTAMP"))
            {
                uniqueFileName = (new StringBuilder(String.valueOf(uniqueFileName))).append(uid).append("_").append(Math.random()).toString();
            } else
            {
                SimpleDateFormat sdf = new SimpleDateFormat("dMyHms");
                String date = sdf.format(Calendar.getInstance().getTime());
                uniqueFileName = (new StringBuilder(String.valueOf(uniqueFileName))).append(uid).append("_").append(date).append("_").append(Calendar.getInstance().getTimeInMillis()).toString();
            }
            uniqueFileName = (new StringBuilder(String.valueOf(uniqueFileName))).append(".").append(extension).toString();
        } while((new File(uniqueFileName)).exists());
        return uniqueFileName;
    }

    private static HashMap<String, String> mimeTypes;
    private static HashMap<String, String> extensions;
    private static HashMap<String, String> handlerAssociationsMap;
    private static Logger logger;
    public static String EXPORTHANDLER = "FCExporter_";
    public static String RESOURCEPATH = "Resources/";
    public static String SAVEPATH = "./";
    public static String HTTP_URI = "http://yourdomain.com/";
    public static String TMPSAVEPATH = "";
    public static boolean OVERWRITEFILE = false;
    public static boolean INTELLIGENTFILENAMING = true;
    public static String FILESUFFIXFORMAT = "TIMESTAMP";

    static 
    {
        mimeTypes = new HashMap<String, String>();
        extensions = new HashMap<String, String>();
        handlerAssociationsMap = new HashMap<String, String>();
        logger = null;
        handlerAssociationsMap.put("PDF", "PDF");
        handlerAssociationsMap.put("JPEG", "IMG");
        handlerAssociationsMap.put("JPG", "IMG");
        handlerAssociationsMap.put("PNG", "IMG");
        handlerAssociationsMap.put("GIF", "IMG");
        mimeTypes.put("jpg", "image/jpeg");
        mimeTypes.put("jpeg", "image/jpeg");
        mimeTypes.put("png", "image/png");
        mimeTypes.put("gif", "image/gif");
        mimeTypes.put("pdf", "application/pdf");
        extensions.put("jpeg", "jpg");
        extensions.put("jpg", "jpg");
        extensions.put("png", "png");
        extensions.put("gif", "gif");
        extensions.put("pdf", "pdf");
        logger = Logger.getLogger(FusionChartsExportHelper.class.getName());
        Properties props = new Properties();
        try
        {
            props.load(FusionChartsExportHelper.class.getResourceAsStream("/fusioncharts_export.properties"));
            EXPORTHANDLER = props.getProperty("EXPORTHANDLER", "FCExporter_");
            RESOURCEPATH = props.getProperty("RESOURCEPATH", (new StringBuilder("Resources")).append(File.separator).toString());
            SAVEPATH = props.getProperty("SAVEPATH", "./");
            HTTP_URI = props.getProperty("HTTP_URI", "http://yourdomain.com/");
            TMPSAVEPATH = props.getProperty("TMPSAVEPATH", "");
            String OVERWRITEFILESTR = props.getProperty("OVERWRITEFILE", "false");
            OVERWRITEFILE = (new Boolean(OVERWRITEFILESTR)).booleanValue();
            String INTELLIGENTFILENAMINGSTR = props.getProperty("INTELLIGENTFILENAMING", "true");
            INTELLIGENTFILENAMING = (new Boolean(INTELLIGENTFILENAMINGSTR)).booleanValue();
            FILESUFFIXFORMAT = props.getProperty("FILESUFFIXFORMAT", "TIMESTAMP");
        }
        catch(NullPointerException e)
        {
            logger.info("NullPointer: Properties file not FOUND");
        }
        catch(FileNotFoundException e)
        {
            logger.info("Properties file not FOUND");
        }
        catch(IOException e)
        {
            logger.info("IOException: Properties file not FOUND");
        }
    }
}
