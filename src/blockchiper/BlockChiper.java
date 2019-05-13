/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package blockchiper;

import java.math.BigInteger;

/**
 *
 * @author intan
 */
public class BlockChiper {

    /**
     * @param args the command line arguments
     */
    String message;
    int x_count;

    public BlockChiper(String message) {
        this.message = message;
        this.x_count = 0;
    }

    public static void main(String[] args) {
        BlockChiper bc = new BlockChiper("Haaai");
        String dec = bc.codec(true);
        System.out.println("result enc: " + dec);

        BlockChiper bc2 = new BlockChiper(dec);
        String resultdec = (bc2.codec(false));
        System.out.println("result dec: " + resultdec);
    }

    public String codec(boolean encrypt) {
        Block[] blocks;
        if (encrypt) {
            int mod = message.length() % 8;
            // tidak habis dibagi per block (8 huruf),
            // maka ditambah dengan 'x' sebanyak mod
            if (mod != 0) {
                StringBuilder sb = new StringBuilder();
                sb.append(message);
                x_count = 8 - mod;
                for (int s = 0; s < x_count; s++) {
                    sb.append("x");
                }
                message = sb.toString();
            }
            // group to 8
            int len = message.length() / 8;
            String[] messageChunk = StringHelper.groupTo8(message, len);
            blocks = new Block[len];
            for (int i = 0; i < len; i++) {
                byte[] bs = StringHelper.stringToBytesASCII(messageChunk[i]);
                String bits = StringHelper.toBitString(bs);
                int[] arr = StringHelper.toIntArr(bits);
                blocks[i] = new Block(arr);
            }

            cbc(blocks, encrypt);
            return print(blocks, encrypt) + x_count;
        } else {
            x_count = Integer.parseInt(message.substring(message.length() - 1));
            String[] hexs = message.split(" ");
            
            message = "";
            for (String hex : hexs) {
                message += StringHelper.hexToBin(hex);
            }

            int len = message.length() / 64;
            String[] messageChunk = StringHelper.groupTo64(message, len);
            blocks = new Block[len];
            for (int i = 0; i < len; i++) {
                int[] arr = StringHelper.toIntArr(messageChunk[i]);
                blocks[i] = new Block(arr);
            }

            cbc(blocks, encrypt);
            String s = StringHelper.bitStringToString(print(blocks, encrypt));
            return s.substring(0, s.length() - x_count);
        }
    }

    public static void cbc(Block[] blocks, boolean encrypt) {
        if (encrypt) {
            blocks[0].encryptBlock();
            for (int i = 1; i < blocks.length; i++) {
                blocks[i].xor(blocks[i - 1]);
                blocks[i].encryptBlock();
            }
        } else {
            for (int i = blocks.length - 1; i >= 1; i--) {
                blocks[i].decryptBlock();
                blocks[i].xor(blocks[i - 1]);
            }
            blocks[0].decryptBlock();
        }
    }

    public String print(Block[] bs, boolean encrypt) {
        String str = "";
        StringBuilder sb = new StringBuilder();
        for (Block b : bs) {
            str += b.toString();
        }
        String[] arr = StringHelper.groupTo8(str, str.length() / 8);

        for (String s : arr) {
            if (encrypt) {
                sb.append(new BigInteger(s, 2).toString(16));// to hex
                sb.append(" ");
            } else {
                sb.append(s);
            }
//            sb.append(s);
        }
        return sb.toString();
    }
}
