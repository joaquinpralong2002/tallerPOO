package model.EnumeracionesVariablesTriage;

public enum SignoShock {
    NoPresente(0), Presente(3);

    private int valor;

    SignoShock(int valor){
        this.valor = valor;
    }

    public int getValor(){
        return this.valor;
    }
}


