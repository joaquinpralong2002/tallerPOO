package model.EnumeracionesVariablesTriage;

public enum EstadoMental {
    Normal(0),Leve(1),Grave(2);
    private int valor;

    EstadoMental(int valor){
        this.valor = valor;
    }

    public int getValor(){
        return this.valor;
    }
}