/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package blockchiper;

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;

/**
 *
 * @author intan
 */
public class StringHelper {

    public static byte[] stringToBytesASCII(String str) {
        byte[] b = new byte[str.length()];
        for (int i = 0; i < b.length; i++) {
            b[i] = (byte) str.charAt(i);
        }
        return b;
    }
    
    // to 8 bit
    public static String toBitString(final byte[] b) {
        final char[] bits = new char[8 * b.length];
        for (int i = 0; i < b.length; i++) {
            final byte byteval = b[i];
            int bytei = i << 3;
            int mask = 0x1;
            for (int j = 7; j >= 0; j--) {
                final int bitval = byteval & mask;
                if (bitval == 0) {
                    bits[bytei + j] = '0';
                } else {
                    bits[bytei + j] = '1';
                }
                mask <<= 1;
            }
        }
        return String.valueOf(bits);
    }
    
    //from string to int
    public static int[] toIntArr(String bits){
        int[] result = new int[bits.length()];
        for (int i = 0; i < bits.length(); i++){
            result[i] = Integer.parseInt(bits.substring(i, i+1));
        }
        return result;
    }
    
    public static String bitStringToString(String raw){
        // to byte
        byte[] bval = new BigInteger(raw, 2).toByteArray();
        // to string
        return new String(bval, StandardCharsets.UTF_8);
    }

    static String[] groupTo8(String s, int div) {
        String [] res = new String[div];
        for (int i = 0; i < div; i++) {
            res[i] = s.substring(8*i, (i+1)*8);
        }
        return res;
    }
}
