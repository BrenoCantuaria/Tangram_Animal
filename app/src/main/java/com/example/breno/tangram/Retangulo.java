package com.example.breno.tangram;

//Classes Utilizadas

public class Retangulo extends Geometria
{
    //Construtor da Classe
    public Retangulo(int largura, int altura)
    {
        float[] vetorVertices = {-largura / 2, - altura / 2,
                -largura / 2, altura / 2,
                largura / 2, -altura / 2,
                largura / 2, altura / 2,};

        setVetorVertices(vetorVertices);
    }
}
