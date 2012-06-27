/**
 * 
 */
package cn.ursun.platform.core.util.compress.compressor;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

import cn.ursun.platform.core.util.compress.CompressException;

/**
 * @author duanp
 * 
 */
public class GZIPCompressor implements Compressor {

	/*
	 * (non-Javadoc)
	 * 
	 * @see compress.compressor.Compressor#compress(byte[])
	 */
	public byte[] compress(byte[] data) throws CompressException {
		// TODO Auto-generated method stub
		ByteArrayInputStream bis = new ByteArrayInputStream(data);
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		try {
			GZIPOutputStream gos = new GZIPOutputStream(bos);
			byte[] buffer = new byte[1024];
			int n;
			while ((n = bis.read(buffer)) != -1) {
				gos.write(buffer, 0, n);
				gos.flush();
			}
			bis.close();
			bos.close();
			gos.finish();
			gos.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new CompressException(e);
		}

		return bos.toByteArray();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see compress.compressor.Compressor#decompress(byte[])
	 */
	public byte[] decompress(byte[] compressedData) throws CompressException {
		ByteArrayInputStream bis = new ByteArrayInputStream(compressedData);
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		try {
			GZIPInputStream gis = new GZIPInputStream(bis);
			byte[] buffer = new byte[1024];
			int n;
			while ((n = gis.read(buffer)) != -1) {
				bos.write(buffer, 0, n);
				bos.flush();
			}
			bis.close();
			bos.close();
			gis.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new CompressException(e);
		}

		return bos.toByteArray();
	}

}
