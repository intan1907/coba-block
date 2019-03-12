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
    public static void main(String[] args) {
        // TODO code application logic here
        byte[] bs = StringHelper.stringToBytesASCII("activity");
        String bits = StringHelper.toBitString(bs);
        int[] arr = StringHelper.toIntArr(bits);
        System.out.println("bits: " + bits);
        System.out.println("");
        System.out.println("encrypt: ");
        Block b = new Block(arr);
        b.encryptBlock();
        
        
        System.out.println(b.toString());
//        byte[] bs = StringHelper.stringToBytesASCII("A");
//        System.out.println("A byte: " + bs[0]);
//        System.out.println("A bit: " + StringHelper.toBitString(bs));
    }

}
