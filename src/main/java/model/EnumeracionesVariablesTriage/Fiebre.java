package model.EnumeracionesVariablesTriage;

public enum Fiebre {
    SinFiebre(0),Moderada(1),Alta(2);
    private int valor;
    Fiebre(int valor){
        this.valor = valor;
    }
    public int getValor(){
        return this.valor;
    }
}
