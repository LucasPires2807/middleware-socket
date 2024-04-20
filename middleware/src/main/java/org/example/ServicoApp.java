package org.example;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Collections;
import java.util.List;

public class ServicoApp {
    private ServerSocket serverStubSocket;
    private Socket socket;
    private BufferedReader stubParaApp;
    private PrintWriter appParaStub;
    private List<Object> endereco;

    public void receberSolicitacaoServico(){
        try{
            conectarSockets(90);
            // Recebendo o nome que o usuário passou para o stub
            stubParaApp = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            endereco = Collections.singletonList(stubParaApp.readLine());
            // Tem que fazer a lógica de receber os parâmetros, junto do endereço
            // Enviando o ip e a porta
            appParaStub = new PrintWriter(socket.getOutputStream(), true);
            appParaStub.println();

        } catch (Exception e){
            e.printStackTrace();
        }
    }

    private float somar(float x, float y){
        return x + y;
    }

    private float subtrair(float x, float y){
        return x - y;
    }

    private void conectarSockets(int port) throws IOException {
        serverStubSocket = new ServerSocket(port);
        socket = serverStubSocket.accept();
    }

}
