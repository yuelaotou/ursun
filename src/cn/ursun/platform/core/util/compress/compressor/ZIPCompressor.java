/**
 * 
 */
package cn.ursun.platform.core.util.compress.compressor;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

import cn.ursun.platform.core.util.compress.CompressException;

/**
 * @author duanp
 * 
 */
public class ZIPCompressor implements Compressor {

	private String defaultZipEntry = "defalut.file";

	/*
	 * (non-Javadoc)
	 * 
	 * @see compress.compressor.Compressor#compress(byte[])
	 */
	public byte[] compress(byte[] data) throws CompressException {
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		ZipOutputStream zos = new ZipOutputStream(bos);
		ByteArrayInputStream bis = new ByteArrayInputStream(data);
		int n = 0;
		byte[] buffer = new byte[1024];

		try {
			zos.putNextEntry(new ZipEntry(defaultZipEntry));
			while ((n = bis.read(buffer)) != -1) {
				zos.write(buffer, 0, n);
				zos.flush();
			}
			bis.close();
			zos.finish();
			zos.close();
			bos.close();

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new CompressException(e);
		}
		return bos.toByteArray();
		// TODO Auto-generated method stub
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see compress.compressor.Compressor#decompress(byte[])
	 */
	public byte[] decompress(byte[] compressedData) throws CompressException {
		ByteArrayInputStream bis = new ByteArrayInputStream(compressedData);

		ZipInputStream zis = new ZipInputStream(bis);

		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		int n = 0;
		byte[] buffer = new byte[1024];
		try {
			ZipEntry entry = zis.getNextEntry();
			while ((n = zis.read(buffer)) != -1) {
				bos.write(buffer, 0, n);
				bos.flush();
			}
			bis.close();
			zis.close();
			bos.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new CompressException(e);
		}
		return bos.toByteArray();
	}

}
