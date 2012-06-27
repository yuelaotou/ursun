/**
 * 文件名：ExportBean.java
 *
 * 创建人：【周洋】 - 【zhouyang@neusoft.com】
 *
 * 创建时间：Dec 23, 2009 1:50:34 PM
 *
 * 版权所有：东软软件股份有限公司
 */
package cn.ursun.platform.ps.report.exporter.beans;

import java.util.HashMap;
import java.util.Iterator;

/**
 * <p>【导出图形实体类】</p>
 * @author 【周洋】 - 【zhouyang@neusoft.com】
 * @version 1.0
 */
public class ExportBean {

    public ExportBean()
    {
        exportParameters = null;
        exportParameters = new HashMap<String, Object>();
        exportParameters.put("exportfilename", "FusionCharts");
        exportParameters.put("exportaction", "download");
        exportParameters.put("exportargetwindow", "_self");
        exportParameters.put("exportformat", "PDF");
    }

    public ChartMetadata getMetadata()
    {
        return metadata;
    }

    public void setMetadata(ChartMetadata metadata)
    {
        this.metadata = metadata;
    }

    public String getStream()
    {
        return stream;
    }

    public void setStream(String stream)
    {
        this.stream = stream;
    }

    public HashMap<String, Object> getExportParameters()
    {
        return new HashMap<String, Object>(exportParameters);
    }

    public Object getExportParameterValue(String key)
    {
        return exportParameters.get(key);
    }

    public void setExportParameters(HashMap<String, Object> exportParameters)
    {
        this.exportParameters = exportParameters;
    }

    public void addExportParameter(String parameterName, Object value)
    {
        exportParameters.put(parameterName.toLowerCase(), value);
    }

    public void addExportParametersFromMap(HashMap<String, ?> moreParameters)
    {
        exportParameters.putAll(moreParameters);
    }

    public String getParametersAndMetadataAsQueryString()
    {
        String queryParams = "";
        queryParams = (new StringBuilder(String.valueOf(queryParams))).append("?width=").append(metadata.getWidth()).toString();
        queryParams = (new StringBuilder(String.valueOf(queryParams))).append("&height=").append(metadata.getHeight()).toString();
        queryParams = (new StringBuilder(String.valueOf(queryParams))).append("&bgcolor=").append(metadata.getBgColor()).toString();
        for(Iterator<String> iter = exportParameters.keySet().iterator(); iter.hasNext();)
        {
            String key = iter.next();
            String value = (String)exportParameters.get(key);
            queryParams = (new StringBuilder(String.valueOf(queryParams))).append("&").append(key).append("=").append(value).toString();
        }

        return queryParams;
    }

    public String getMetadataAsQueryString(String filePath, boolean isError, boolean isHTML)
    {
        String queryParams = "";
        if(isError)
        {
            queryParams = (new StringBuilder(String.valueOf(queryParams))).append(isHTML ? "<BR>" : "&").append("width=0").toString();
            queryParams = (new StringBuilder(String.valueOf(queryParams))).append(isHTML ? "<BR>" : "&").append("height=0").toString();
        } else
        {
            queryParams = (new StringBuilder(String.valueOf(queryParams))).append(isHTML ? "<BR>" : "&").append("width=").append(metadata.getWidth()).toString();
            queryParams = (new StringBuilder(String.valueOf(queryParams))).append(isHTML ? "<BR>" : "&").append("height=").append(metadata.getHeight()).toString();
        }
        queryParams = (new StringBuilder(String.valueOf(queryParams))).append(isHTML ? "<BR>" : "&").append("DOMId=").append(metadata.getDOMId()).toString();
        if(filePath != null)
            queryParams = (new StringBuilder(String.valueOf(queryParams))).append(isHTML ? "<BR>" : "&").append("fileName=").append(filePath).toString();
        return queryParams;
    }

    private ChartMetadata metadata;
    private String stream;
    private HashMap<String, Object> exportParameters;
}
