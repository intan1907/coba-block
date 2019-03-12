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
public class Matrix {

    private final int[][] M;
    private final int len;

    public Matrix(int[] array) {
        len = (int) Math.sqrt(array.length);
        M = new int[len][len];
        for (int i = 0; i < array.length; i++) {
            int div = i / len;
            int mod = i % len;
            M[div][mod] = array[i];
        }
    }

    public Matrix(int[][] other) {
        len = other.length;
        M = new int[len][len];
        for (int i = 0; i < len; i++) {
            System.arraycopy(other[i], 0, M[i], 0, len);
        }
    }

    public int[][] getM() {
        return M;
    }

    public void inverse() {
        for (int i = 0; i < len; i++) {
            for (int j = 0; j < len; j++) {
                M[i][j] = M[i][j] == 1 ? 0 : 1;
            }
        }
    }

    public void shiftRow(boolean up) {
        int[][] other = copyMatrix(M);
        for (int row = 0; row < len; row++) {
            System.arraycopy(other[(up ? row + 1 : row + (len - 1)) % len], 0, M[row], 0, len);
        }
    }

    public void shiftColumn(boolean left) {
        int[][] other = copyMatrix(M);
        for (int row = 0; row < len; row++) {
            for (int col = 0; col < len; col++) {
                M[row][col] = other[row][(left ? col + 1 : col + (len - 1)) % len];
            }
        }
    }

    public void print() {
        for (int i = 0; i < len; i++) {
            for (int j = 0; j < len; j++) {
                System.out.print(M[i][j] + " ");
            }
            System.out.println("");
        }
    }

    public int[][] copyMatrix(int[][] other) {
        int[][] result = new int[other.length][other.length];
        for (int i = 0; i < other.length; i++) {
            System.arraycopy(other[i], 0, result[i], 0, other.length);
        }
        return result;
    }

    public void transpose() {
        int[][] other = copyMatrix(M);
        for (int i = 0; i < len; i++) {
            for (int j = 0; j < len; j++) {
                M[i][j] = other[j][i];
            }
        }
    }

    public void xor(Matrix other) {
        int[][] m = other.getM();
        for (int i = 0; i < len; i++) {
            for (int j = 0; j < len; j++) {
                M[i][j] = (M[i][j] == m[i][j]) ? 0 : 1;
            }
        }
    }

    /*  operasi ke-1: shift column,
        operasi ke-2: inverse
        operasi ke-3: transpose
        operasi ke-4: shift row */
    public void operate(int operation, boolean encode) {
//        System.out.println(operation);
        switch (operation) {
            case 1:
                shiftColumn(encode);
                break;
            case 2:
                inverse();
                break;
            case 3:
                transpose();
                break;
            case 0:
                shiftRow(encode);
        }
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < len; i++) {
            for (int j = 0; j < len; j++) {
                sb.append(M[i][j]);
            }
        }
        return sb.toString();
    }
}
