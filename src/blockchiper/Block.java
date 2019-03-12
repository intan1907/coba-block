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

            feistel();
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

            feistel();
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

    public void xor(Block other) {
        for (int i = 0; i < 4; i++) {
            matrices[i].xor(other.matrices[i]);
        }
    }

    public void feistel() {
        
        //temp untuk swap
        Matrix temp0 = new Matrix(matrices[0].getM());
        Matrix temp1 = new Matrix(matrices[1].getM());
        //left di-transform
        transform(temp0, temp1);
        Matrix temp2 = new Matrix(matrices[2].getM());
        Matrix temp3 = new Matrix(matrices[3].getM());
        //
        temp0 = new Matrix(temp2.getM());
        temp1 = new Matrix(temp3.getM());
        temp2 = new Matrix(temp0.getM());
        temp3 = new Matrix(temp1.getM());
    }

    public void transform(Matrix m1, Matrix m2) {
        // xor antara left dengan key
        // asumsi key sama panjangnya dengan jumlah elemen matrix
        int[] key = StringHelper.toIntArr("0110000101101100");
        m1.xor(key);
        m2.xor(key);
    }
}
