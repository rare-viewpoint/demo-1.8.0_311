package util;

import java.nio.ByteBuffer;

/**
 * @Desc TODO
 * @Author shm
 * @Date 2021/12/4 22:42
 */
public final class ByteUtil {

    public static String byteBufToHexString(ByteBuffer byteBuf){
        byte[] bytes = new byte[byteBuf.remaining()];
        byteBuf.get(bytes);
        return bytesToHexString(bytes);
    }

    // 16进制\r\n  0D0A
    public static String bytesToHexString(byte[] bytes){
        StringBuilder sb = new StringBuilder();
        for (byte b : bytes) {
            String t = Integer.toHexString(b & 0xFF);
            if(t.length() < 2){
                sb.append(0);
            }
            sb.append(t.toUpperCase());
            sb.append(" ");
        }
        return sb.toString();
    }

    public static String bytesToHexStringLine(byte[] bytes){
        String content = bytesToHexString(bytes);
        return content.replaceAll("0D 0A ", "\r\n");
    }
}
