package com.example.breno.tangram;

import android.app.Activity;
import android.opengl.GLSurfaceView;
import android.os.SystemClock;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

//define a classe que vai controlar o comportamento do desenho (o que sera desenhado)
class Renderizador implements GLSurfaceView.Renderer
{
    Long tempoInicial = null;
    Triangulo vrTriangulo = null;
    Triangulo vrTriangulo2 = null;
    Triangulo vrTriangulo3 = null;
    Triangulo vrTriangulo4 = null;
    Triangulo vrTriangulo5 = null;
    Paralelograma vrParalelogramo = null;
    Quadrado vrQuadrado = null;

    @Override
    //chamado quando a superficie de desenho for criada (1vez)
    public void onSurfaceCreated(GL10 vrOpengl, EGLConfig eglConfig)
    {
        //CONFIGURANDO A LIMPEZA DE FUNDO (RGBT)
        vrOpengl.glClearColor(0F, 0F, 0F, 0F);

        //Triângulo 1 (Grande)
        vrTriangulo = new Triangulo(260,130);
        vrTriangulo.setCor(1,1,0);
        vrTriangulo.setXY(390,615);
        vrTriangulo.incAngulo(-0);

        //Triângulo 2 (Grande)
        vrTriangulo2 = new Triangulo(260,130);
        vrTriangulo2.setCor(0,0.55f,0);
        vrTriangulo2.setXY(305,495);
        vrTriangulo2.incAngulo(45);

        //Triângulo 3 (pequeno)
        vrTriangulo3 = new Triangulo(129,65);
        vrTriangulo3.setCor(1,0,0);
        vrTriangulo3.setXY(605,583);
        vrTriangulo3.incAngulo(0);

        //Triângulo 4 (pequeno)
        vrTriangulo4 = new Triangulo(160,80);
        vrTriangulo4.setCor(0.95f,0.45f,0);
        vrTriangulo4.setXY(225,455);
        vrTriangulo4.incAngulo(-135);

        //Triângulo 5 (médio)
        vrTriangulo5 = new Triangulo(125,70);
        vrTriangulo5.setCor(0.50f,0.35f,0);
        vrTriangulo5.setXY(260,320);

        //Paralelogramo
        vrParalelogramo = new Paralelograma( 115, 75, 2.5f, 1f, 1f, 2.5f);
        vrParalelogramo.setCor(0.11f,0.71f,0f);
        vrParalelogramo.setXY(130,360);
        vrParalelogramo.incAngulo(47);

        //Quadrado
        vrQuadrado = new Quadrado(100);
        vrQuadrado.setCor(0.25f,0.55f,1);
        vrQuadrado.setXY(530,620);
        vrQuadrado.incAngulo(45);
    }

    @Override
    public void onSurfaceChanged(GL10 vrOpengl, int width, int heigth)
    {
        //chamado quando as configurações da superficie desenho forem alteradas
        //ex: rotação do aparelho
        Log.i("INFO", "SUPERFICIE ALTERADA" + width+ " " + heigth );

        //INICIAR A PREPARAÇÃO DO OPENGL PARA DESENHO
        //CONFIGURAR O VIEWPORT - AREA DE DESENHO UTILIZADA DA TELA
        vrOpengl.glViewport(0,0,width, heigth);
        //FAZ A OPENGL APONTAR PARA A MATRIZ DE PROJEÇÃO
        vrOpengl.glMatrixMode(GL10.GL_PROJECTION);
        //CARREGA A MATRIZ DE IDENTIDADE NA PROJEÇÃO
        vrOpengl.glLoadIdentity();

        //CHAMA O COMANDO PARA CONFIGURAR A AREA DE DESENHO (VOLUME DE REDENRIZAÇÃO)
        vrOpengl.glOrthof(0,width,0,heigth,-1,1);

        //SOLICITA AO OPENGL APONTAR PARA A MATRIZ DE MODELOS DE CAMERA
        vrOpengl.glMatrixMode(GL10.GL_MODELVIEW);
        vrOpengl.glLoadIdentity();

        //SOLICITA  A BIBLIOTECA QUE HABILITE  O RECURSO DE VETOR DE VERTICES
        vrOpengl.glEnableClientState(GL10.GL_VERTEX_ARRAY);
        //REGISTRAO VETOR DE VERTICES NA MAQUINA OPENGL PARA USO FUTURO
        vrOpengl.glVertexPointer(2, GL10.GL_FLOAT,0, vrTriangulo.getVetorVertices());
    }

    @Override
    public void onDrawFrame(GL10 vrOpengl)
    {
        //SOLICITA A OPENGL QUE LIMPE A MATRIZ DE PIXEL DE CORES
        vrOpengl.glClear(GL10.GL_COLOR_BUFFER_BIT);

        //Chamada dos objetos para apresentar o desenho na tela
        vrTriangulo.desenha(vrOpengl);
        vrTriangulo2.desenha(vrOpengl);
        vrTriangulo3.desenha(vrOpengl);
        vrTriangulo4.desenha(vrOpengl);
        vrTriangulo5.desenha(vrOpengl);
        vrParalelogramo.desenha(vrOpengl);
        vrQuadrado.desenha(vrOpengl);
    }
}

public class TelaPrincipal extends Activity
{
    //cria a referencia para o objeto responsavel pela exibição do desenho
    GLSurfaceView vrSuperficieDesenho = null;
    Renderizador vrRenderizador = null;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        //SOLICITA A RETIRADA DA BARRA DE TITULO
        requestWindowFeature(Window.FEATURE_NO_TITLE);

        //validar variavel de referencia para a superficie do desenho
        vrSuperficieDesenho = new GLSurfaceView(this);
        vrRenderizador = new Renderizador();
        vrSuperficieDesenho.setRenderer(vrRenderizador);

        //CONFIGURA MODO FULLSCREEN NA LARGURA E ALTURA
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN );

        //SOLICITA QUE O APLICATIVO FIQU7E ATIVO NA TELA MESMO POR INATIVIDADE DO USUARIO
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        setContentView(vrSuperficieDesenho);
    }
}
