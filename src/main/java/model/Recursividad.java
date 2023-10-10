package model;

public class Recursividad {
    public static void main(String[] args) {
        int[] vector = {1,2,3,4,5};
        System.out.println(sumarVector(vector, 0));
    }


    public static int sumarVector(int[] vector, int indice) {
        if (indice == vector.length) {
            return 0;
        } else {
            return vector[indice] + sumarVector(vector, indice + 1);
        }
    }
}
