/**
 * 
 */
package cn.ursun.platform.core.util.compress;

import cn.ursun.platform.core.util.compress.compressor.Compressor;
import cn.ursun.platform.core.util.compress.compressor.DeflateCompressor;

/**
 * @author duanp
 * 
 */
public class CompressUtils {

	private static CompressUtils instance = null;

	private static Compressor compressor = null;

	private CompressUtils() {
		super();

	}

	public synchronized static CompressUtils getInstance() {
		if (instance == null)
			instance = new CompressUtils();
		if (compressor == null)
			compressor = new DeflateCompressor();
		return instance;
	}

	public byte[] compress(String data, String charsetName) throws CompressException {
		byte[] result = null;
		try {
			result = compressor.compress(data.getBytes(charsetName));
		} catch (Exception e) {
			e.printStackTrace();
			throw new CompressException(e);
		}
		System.out.println(result.length);
		return result;
	}

	public String decompress(byte[] compressedData, String charsetName) throws CompressException {
		String result = null;
		byte[] data = null;
		try {
			result = new String(data = compressor.decompress(compressedData), charsetName);

		} catch (Exception e) {
			e.printStackTrace();
			throw new CompressException(e);
		}
		System.out.println(data.length);
		return result;
	}

	public static void setCompressor(Compressor compressor) {
		CompressUtils.compressor = compressor;
	}
}
