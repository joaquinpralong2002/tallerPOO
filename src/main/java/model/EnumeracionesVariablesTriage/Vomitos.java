package model.EnumeracionesVariablesTriage;

public enum Vomitos {
    SinVomito(0),Moderado(1),Intenso(2);
    private int valor;
    Vomitos(int valor){
        this.valor = valor;
    }
    public int getValor(){
        return this.valor;
    }
}
