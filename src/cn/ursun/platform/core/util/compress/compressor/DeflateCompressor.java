/**
 * 
 */
package cn.ursun.platform.core.util.compress.compressor;

import java.io.ByteArrayOutputStream;
import java.util.zip.Deflater;
import java.util.zip.Inflater;

import cn.ursun.platform.core.util.compress.CompressException;

/**
 * Deflaterѹ��ʽ
 * 
 * @author duanp
 * 
 */
public class DeflateCompressor implements Compressor {

	/*
	 * ѹ�����
	 * 
	 * @see compress.compressor.Compressor#compress(byte[])
	 */
	public byte[] compress(byte[] data) throws CompressException {
		Deflater compressor = new Deflater();
		compressor.setLevel(Deflater.BEST_COMPRESSION);
		compressor.setInput(data);
		compressor.finish();
		ByteArrayOutputStream bos = new ByteArrayOutputStream(data.length);
		byte[] buf = new byte[1024];
		byte[] compressedData = null;
		try {
			while (!compressor.finished()) {
				int count = compressor.deflate(buf);
				bos.write(buf, 0, count);
			}
			bos.close();
			compressedData = bos.toByteArray();

		} catch (Exception e) {
			throw new CompressException(e);
		}

		return compressedData;
	}

	/*
	 * ��ѹ���
	 * 
	 * @see compress.compressor.Compressor#decompress(byte[])
	 */
	public byte[] decompress(byte[] compressedData) throws CompressException {
		Inflater decompressor = new Inflater();
		decompressor.setInput(compressedData);
		ByteArrayOutputStream bos = new ByteArrayOutputStream(compressedData.length);
		byte[] buf = new byte[1024];
		try {
			while (!decompressor.finished()) {
				int count = decompressor.inflate(buf);
				bos.write(buf, 0, count);
			}
			bos.close();
		} catch (Exception e) {
			throw new CompressException(e);
		}
		byte[] decompressedData = bos.toByteArray();
		return decompressedData;
	}

}
