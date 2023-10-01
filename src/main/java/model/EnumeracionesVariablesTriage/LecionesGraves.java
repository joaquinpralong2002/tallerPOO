package model.EnumeracionesVariablesTriage;

public enum LecionesGraves {
    NoPresentes(0),Presentes(2);
    private int valor;
    LecionesGraves(int valor){
        this.valor = valor;
    }

    public int getValor(){
        return this.valor;
    }
}
