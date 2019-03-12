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
public class Block {

    Matrix[] matrices = new Matrix[4];

    public Block(int[] raw) {
        // asumsi panjang raw = 64
        for (int i = 0; i < 4; i++) {
            int[] arr = new int[16];
            System.arraycopy(raw, i * 16, arr, 0, 16);
            matrices[i] = new Matrix(arr);
        }
    }

    public void encryptBlock() {
        for (int i = 1; i <= 8; i++) {
//            System.out.println("======iter " + i + ":");
            // operasi setiap matrix 
            for (int operation = i, j = 0; operation < i + 4; operation++) {
                matrices[j].operate((operation % 4), true);
                j++;
            }

            matrices[0].xor(matrices[1]);
            matrices[1].xor(matrices[2]);
            matrices[2].xor(matrices[3]);


        }
    }

    public void decryptBlock() {
        for (int i = 8; i >= 1; i--) {
//            System.out.println("======iter " + i + ":");

            matrices[2].xor(matrices[3]);
            matrices[1].xor(matrices[2]);
            matrices[0].xor(matrices[1]);
            
            // operasi setiap matrix 
            for (int operation = i, j = 0; operation < i + 4; operation++) {
                matrices[j].operate((operation % 4), false);
                j++;
            }
  
        }
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 4; i++) {
            sb.append(matrices[i].toString());
        }
        return sb.toString();
    }

    public void printMatrices() {
        int j = 1;
        for (Matrix m : matrices) {
            System.out.println("matrix " + j + ":");
            m.print();
            j++;
        }
    }
    
    public void xor (Block other) {
        for (int i = 0; i < 4; i++){
            matrices[i].xor(other.matrices[i]);
        }
    }
}
