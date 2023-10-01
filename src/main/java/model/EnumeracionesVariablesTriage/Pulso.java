package model.EnumeracionesVariablesTriage;

public enum Pulso {
    Normal(0),Anormal(1);
    private int valor;

    Pulso(int valor){
        this.valor = valor;
    }

    public int getValor(){
        return this.valor;
    }
}
