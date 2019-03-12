/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package blockchiper;

/**
 *
 * @author intan
 */
public class BlockChiper {

    /**
     * @param args the command line arguments
     */
    Block[] blocks;

    public BlockChiper() {
        String message = "lorem ipsum lorem ipsum";
        int mod = message.length() % 8;
        // tidak habis dibagi per block (8 huruf),
        // maka ditambah dengan 'x' sebanyak mod
        if (mod != 0) {
            System.out.println("mod != 0");
            StringBuilder sb = new StringBuilder();
            sb.append(message);
            for (int s = 0; s < mod; s++) {
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
//            System.out.println(bits);
            int[] arr = StringHelper.toIntArr(bits);
            blocks[i] = new Block(arr);
        }

    }

    public static void main(String[] args) {
        // TODO code application logic here
//        byte[] bs = StringHelper.stringToBytesASCII();
//        String bits = StringHelper.toBitString(bs);
//        int[] arr = StringHelper.toIntArr(bits);
//        System.out.println("bits: " + bits);

//        Block b = new Block(arr);
//
//        System.out.println("encrypt: ");
//        b.encryptBlock();
//        b.decryptBlock();
//        System.out.println(b.toString());
//        System.out.println("decrypt: ");
//        b.decryptBlock();
//        String dec = b.toString();
//        System.out.println(b.toString());
//        System.out.println(bits.equalsIgnoreCase(dec));
        long startE = System.nanoTime();
        BlockChiper bc = new BlockChiper();
        codec(bc.getBlocks(), 1, true);
        long endE = System.nanoTime();

        long startDec = System.nanoTime();
        
        codec(bc.getBlocks(), 1, false);
        long endDec = System.nanoTime();
        System.out.println("enc time: " + (endE - startE) / 1000 + " microseconds");

        System.out.println("dec time: " + (endDec - startDec) / 1000 + " microseconds");
    }

    public Block[] getBlocks() {
        return blocks;
    }

    public static void codec(Block[] blocks, int mode, boolean encrypt) {
        switch (mode) {
            case 0:
                ebc(blocks, encrypt);
            case 1:
                cbc(blocks, encrypt);
            case 2:
            case 3:
            case 4:
        }
        System.out.println("hasil: ");
        print(blocks);
    }

    public static void ebc(Block[] blocks, boolean encrypt) {
        for (int i = 0; i < blocks.length; i++) {
            if (encrypt) {
                blocks[i].encryptBlock();
            } else {
                blocks[i].encryptBlock();
            }
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

    // cfb 8bit
    public void cfb(boolean encrypt) {
        if (encrypt) {
//            for (int i = 0; i < blocks.length; i++) {
//            }
            
        }
    }

    public void ofb(boolean encrypt) {

    }

    public void counter(boolean encrypt) {

    }

    public static void print(Block[] bs) {
        for (Block b : bs) {
            String str = StringHelper.bitStringToString(b.toString());
            System.out.print(str);
        }
        System.out.println("");
    }
}
