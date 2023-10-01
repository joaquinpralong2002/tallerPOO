package model.EnumeracionesVariablesTriage;

public enum Respiracion {
    Normal(0),DificultadModerada(1),DificultadGrave(2);
    private int valor;

    Respiracion(int valor){
        this.valor = valor;
    }

    public int getValor(){
        return this.valor;
    }
}
