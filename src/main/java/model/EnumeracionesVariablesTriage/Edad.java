package model.EnumeracionesVariablesTriage;

public enum Edad {
    Adulto(0),NinioAnciano(1);
    private int valor;
    Edad(int valor){
        this.valor = valor;
    }
    public int getValor(){
        return this.valor;
    }
}
