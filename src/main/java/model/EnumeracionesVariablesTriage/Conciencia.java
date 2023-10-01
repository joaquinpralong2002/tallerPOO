package model.EnumeracionesVariablesTriage;

public enum Conciencia {
    Consciente(0),PerdidaConsciencia(3);
    private int valor;

    Conciencia(int valor){
        this.valor = valor;
    }

    public int getValor(){
        return this.valor;
    }
}
