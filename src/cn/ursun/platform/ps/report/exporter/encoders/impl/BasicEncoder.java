/**
 * 文件名：BasicEncoder.java
 *
 * 创建人：【周洋】 - 【zhouyang@neusoft.com】
 *
 * 创建时间：Dec 23, 2009 1:55:23 PM
 *
 * 版权所有：东软软件股份有限公司
 */
package cn.ursun.platform.ps.report.exporter.encoders.impl;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Iterator;

import javax.imageio.IIOImage;
import javax.imageio.ImageIO;
import javax.imageio.ImageWriter;
import javax.imageio.stream.FileImageOutputStream;
import javax.imageio.stream.ImageOutputStream;
import javax.servlet.jsp.JspWriter;

import cn.ursun.platform.ps.report.exporter.encoders.Encoder;

/**
 * <p>【请将此部分整体替换为对此类型的描述】</p>
 * @author 【周洋】 - 【zhouyang@neusoft.com】
 * @version 1.0
 */
public class BasicEncoder implements Encoder {


    public BasicEncoder()
    {
        defaultFormat = "JPEG";
    }

    public void encode(BufferedImage bufferedImage, OutputStream outputStream, float quality)
        throws Throwable
    {
        encode(bufferedImage, outputStream, quality, defaultFormat);
    }

    public void encode(BufferedImage bufferedImage, OutputStream outputStream)
        throws Throwable
    {
        encode(bufferedImage, outputStream, 1.0F);
    }

    public void encode(BufferedImage bufferedImage, OutputStream outputStream, float quality, String format)
        throws Throwable
    {
        ImageOutputStream ios = null;
        try
        {
            Iterator<?> writers = ImageIO.getImageWritersByFormatName(format);
            ImageWriter writer = (ImageWriter)writers.next();
            javax.imageio.ImageWriteParam iwp = writer.getDefaultWriteParam();
            ios = ImageIO.createImageOutputStream(outputStream);
            writer.setOutput(ios);
            writer.write(null, new IIOImage(bufferedImage, null, null), iwp);
            ios.close();
        }
        catch(IllegalArgumentException e)
        {
            if(ios != null)
                ios.close();
            throw new Throwable();
        }
        catch(IOException e)
        {
            if(ios != null)
                ios.close();
            throw new Throwable();
        }
    }

    public void encode(BufferedImage bufferedImage, JspWriter out, float quality, String format)
        throws Throwable
    {
        ImageOutputStream ios = null;
        try
        {
            Iterator<?> writers = ImageIO.getImageWritersByFormatName(format);
            ImageWriter writer = (ImageWriter)writers.next();
            javax.imageio.ImageWriteParam iwp = writer.getDefaultWriteParam();
            ios = ImageIO.createImageOutputStream(out);
            writer.setOutput(ios);
            writer.write(null, new IIOImage(bufferedImage, null, null), iwp);
            ios.close();
        }
        catch(IllegalArgumentException e)
        {
            if(ios != null)
                ios.close();
            throw new Throwable();
        }
        catch(IOException e)
        {
            if(ios != null)
                ios.close();
            throw new Throwable();
        }
    }

    public void encode(BufferedImage bufferedImage, FileImageOutputStream fileImageOutputStream, float quality)
        throws Throwable
    {
        encode(bufferedImage, fileImageOutputStream, quality, defaultFormat);
    }

    public void encode(BufferedImage bufferedImage, FileImageOutputStream fileImageOutputStream)
        throws Throwable
    {
        encode(bufferedImage, fileImageOutputStream, 1.0F);
    }

    public void encode(BufferedImage bufferedImage, FileImageOutputStream fileImageOutputStream, float quality, String format)
        throws Throwable
    {
        Iterator<?> writers = ImageIO.getImageWritersByFormatName(format);
        ImageWriter writer = (ImageWriter)writers.next();
        javax.imageio.ImageWriteParam iwp = writer.getDefaultWriteParam();
        writer.setOutput(fileImageOutputStream);
        writer.write(null, new IIOImage(bufferedImage, null, null), iwp);
        fileImageOutputStream.close();
    }

    String defaultFormat;
}
