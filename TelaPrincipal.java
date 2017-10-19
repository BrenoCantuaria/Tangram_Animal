package com.skylist.myappca;

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
    int fps;
    Triangulo vrTriangulo = null;
    Quadrado vrQuadrado = null;


    @Override
    //chamado quando a superficie de desenho for criada (1vez)
    public void onSurfaceCreated(GL10 vrOpengl, EGLConfig eglConfig)
    {
        //CONFIGURANDO A LIMPEZA DE FUNDO (RGBT)
        vrOpengl.glClearColor(1.0F, 0.0F, 0.0F, 1.0F);
        vrTriangulo = new Triangulo(800,800);

        vrTriangulo.setCor(1,1,0);
        vrTriangulo.setZoom(1.0f);
        vrTriangulo.setXY(300,700);

        vrQuadrado = new Quadrado(100);
        vrQuadrado.setCor(0,1,0);
        vrQuadrado.setXY(300,200);
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

        //FloatBuffer vertices = criaFloatBuffer(vetorJava);
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

        vrQuadrado.desenha(vrOpengl);
        vrTriangulo.desenha(vrOpengl);

        vrTriangulo.incAngulo(5);
        vrQuadrado.incAngulo(5);

        /*if(vrTriangulo.getZoom() > 0)
        {
            vrTriangulo.setZoom(vrTriangulo.getZoom() - 0.01f);
        }
        else
        {
            vrTriangulo.setZoom(0);
        }*/
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
