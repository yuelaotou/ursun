/**
 * 
 */
package cn.ursun.platform.core.util.compress.compressor;

import cn.ursun.platform.core.util.compress.CompressException;

/**
 * @author duanp
 * 
 */
public interface Compressor {

	/**
	 * 压缩数据
	 * 
	 * @param data
	 * @return
	 */
	public byte[] compress(byte[] data) throws CompressException;

	/**
	 * 解压数据
	 * 
	 * @param compressedData
	 * @return
	 */
	public byte[] decompress(byte[] compressedData) throws CompressException;
}
