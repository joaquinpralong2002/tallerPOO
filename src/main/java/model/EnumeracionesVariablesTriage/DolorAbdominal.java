package model.EnumeracionesVariablesTriage;

public enum DolorAbdominal {
    NoPresente(0),DolorAbdominalModerado(1),DolorAbdominalSevero(2);

    private int valor;

    DolorAbdominal(int valor){
        this.valor = valor;
    }

    public int getValor(){
        return this.valor;
    }
}
