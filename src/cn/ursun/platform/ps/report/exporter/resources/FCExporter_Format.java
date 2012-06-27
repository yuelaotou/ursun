package cn.ursun.platform.ps.report.exporter.resources;

import java.io.InputStream;

import javax.servlet.http.HttpServletResponse;

import cn.ursun.platform.core.exception.BizException;
import cn.ursun.platform.ps.report.exporter.beans.ExportBean;

public abstract class FCExporter_Format {

    public FCExporter_Format()
    {
    }

    public abstract Object exportProcessor(ExportBean exportbean);

    public abstract InputStream exportOutput(Object obj, HttpServletResponse response)throws BizException;

}
