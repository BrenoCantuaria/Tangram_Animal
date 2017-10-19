package com.example.breno.tangram;

public class Paralelograma extends Geometria
{
    //Construtor da Classe
    public Paralelograma(int largura, int altura, float iE, float sE, float iD, float sD)
    {
        float[] vetorVertices = {
                -largura / iE, - altura / 2, //inferior esq
                -largura / sE, altura / 2, // superior esq
                largura / iD, -altura / 2, // inferior dir
                largura / sD, altura / 2,}; // superior dir

        setVetorVertices(vetorVertices);
    }
}
