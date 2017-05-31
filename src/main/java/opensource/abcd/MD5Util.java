package opensource.abcd;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * md5加密
 */
public class MD5Util {
	
	private final static char[] hexDigits = { '0', '1', '2', '3', '4', '5',
			'6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F' };

	/**
	 * 32位MD5加密，返回大写字母结果
	 * @param input
	 * @return
	 */
	public static String md5(String input) {
		return code(input, 32);
	}
	
	/**
	 * 32位MD5加密，返回小写字母结果
	 * @param input
	 * @return
	 */
	public static String md5LowerCase(String input) {
		return code(input, 32).toLowerCase();
	}

	/**
	 * 16位的md5加密，返回大写字母结果
	 * @param input
	 * @return
	 */
	public static String md516(String input) {
		return code(input, 16);
	}
	/**
	 * 16位的md5加密，返回小写字母结果
	 * @param input
	 * @return
	 */
	public static String md516LowerCase(String input) {
		return code(input, 16).toLowerCase();
	}

	private static String code(String input, int bit) {
		MessageDigest md = null;
		String encodeStr = null;
		try {
			md = MessageDigest.getInstance(System.getProperty("MD5.algorithm",
					"MD5"));
			if (bit == 16) {
				encodeStr = bytesToHex(md.digest(input.getBytes("utf-8")))
						.substring(8, 24);
			} else {
				encodeStr = bytesToHex(md.digest(input.getBytes("utf-8")));
			}
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return encodeStr;
	}

	private static String bytesToHex(byte[] bytes) {
		StringBuilder sb = new StringBuilder();
		int t;
		for (int i = 0; i < 16; i++) {
			t = bytes[i];
			if (t < 0){
				t += 256;
			}
			sb.append(hexDigits[(t >>> 4)]);
			sb.append(hexDigits[(t % 16)]);
		}
		return sb.toString();
	}
}
