/**
 * 文件名：Encoder.java
 *
 * 创建人：【周洋】 - 【zhouyang@neusoft.com】
 *
 * 创建时间：Dec 23, 2009 1:53:44 PM
 *
 * 版权所有：东软软件股份有限公司
 */
package cn.ursun.platform.ps.report.exporter.encoders;

import java.awt.image.BufferedImage;
import java.io.OutputStream;

import javax.imageio.stream.FileImageOutputStream;

/**
 * <p>【请将此部分整体替换为对此类型的描述】</p>
 * @author 【周洋】 - 【zhouyang@neusoft.com】
 * @version 1.0
 */
public interface Encoder {
	 public abstract void encode(BufferedImage bufferedimage, OutputStream outputstream)
	 	throws Throwable;

	 public abstract void encode(BufferedImage bufferedimage, OutputStream outputstream, float f)
	     throws Throwable;
	
	 public abstract void encode(BufferedImage bufferedimage, OutputStream outputstream, float f, String s)
	     throws Throwable;
	
	 public abstract void encode(BufferedImage bufferedimage, FileImageOutputStream fileimageoutputstream)
	     throws Throwable;
	
	 public abstract void encode(BufferedImage bufferedimage, FileImageOutputStream fileimageoutputstream, float f)
	     throws Throwable;
	
	 public abstract void encode(BufferedImage bufferedimage, FileImageOutputStream fileimageoutputstream, float f, String s)
	     throws Throwable;

}
