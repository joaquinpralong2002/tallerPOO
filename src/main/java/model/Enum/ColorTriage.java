package model.Enum;

public enum ColorTriage {
    Rojo(1),Naranja(2),Amarrillo(3),Verde(4),Azul(5), Ninguno(-1);

    private int valor;

    ColorTriage(int valor){
        this.valor = valor;
    }

    public int getValor(){
        return this.valor;
    }
}
