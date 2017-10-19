package com.example.breno.tangram;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

public class VertexBuffer
{
    //construtor privado de uma classe que não pode ser estanciada e nem herdada
    private VertexBuffer()
    {
    }

    //método de classe (não necessita de um objeto para ser chamado)
    public static FloatBuffer criaFloatBuffer(float[] vetorVertices)
    {
        ByteBuffer vrByteBuffer = ByteBuffer.allocateDirect(vetorVertices.length*4);
        vrByteBuffer.order(ByteOrder.nativeOrder());

        FloatBuffer vrFloatBuffer = vrByteBuffer.asFloatBuffer();
        vrFloatBuffer.clear();

        vrFloatBuffer.put(vetorVertices);
        vrFloatBuffer.flip();

        return vrFloatBuffer;
    }
}
