package model.EnumeracionesVariablesTriage;

public enum LesionLeve {
    NoPresente(0),Presente(3);
    private int valor;

    LesionLeve(int valor){
        this.valor = valor;
    }

    public int getValor(){
        return this.valor;
    }
}
