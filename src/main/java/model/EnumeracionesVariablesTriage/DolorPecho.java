package model.EnumeracionesVariablesTriage;

public enum DolorPecho {
    NoPresnte(0),Presente(1);

    private int valor;
    DolorPecho(int valor) {
        this.valor = valor;
    }

    public int getValor(){
        return this.valor;
    }
}
