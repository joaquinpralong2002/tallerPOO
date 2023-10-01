package model.EnumeracionesVariablesTriage;

public enum Sangrado {
    NoPresente(0),SangradoModerado(1),SangradoIntenso(2);
    private int valor;

    Sangrado(int valor){
        this.valor = valor;
    }

    public int getValor(){
        return this.valor;
    }
}
