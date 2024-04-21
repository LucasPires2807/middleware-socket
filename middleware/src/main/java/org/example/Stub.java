package org.example;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.io.InputStreamReader;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class Stub {
    private ServicoNome servicoNome;
    private ServicoApp servicoApp;
    private Socket socketServicoNome;
    private Socket socketApp;
    private PrintWriter stubParaNomes;
    private BufferedReader nomesParaStub;
    private PrintWriter stubParaApp;
    private  BufferedReader appParaStub;

    public Object receberSolicitacaoServico(String servicoSolicitado, List<String> parametros) {
        try{
            servicoNome.receberSolicitacaoServico();
            // Conectar o Stub com o serviço de nomes
            conectarNomesSockets(80);
            // Manda para o serviço de nomes o serviço solicitado
            stubParaNomes = new PrintWriter(socketServicoNome.getOutputStream(), true);
            stubParaNomes.println(servicoSolicitado);
            nomesParaStub = new BufferedReader(new InputStreamReader(socketServicoNome.getInputStream()));
            // Pega o endereço do serviço
            List<Object> enderecoServico = Collections.singletonList(nomesParaStub.readLine());
            servicoApp.receberSolicitacaoServico();
            conectarAppSockets(90);
            stubParaApp = new PrintWriter(socketApp.getOutputStream(), true);
            stubParaApp.println(enderecoServico);
            stubParaApp.println(parametros);
            appParaStub = new BufferedReader(new InputStreamReader(socketApp.getInputStream()));
            Object output = appParaStub.readLine();
            closeConnection();
            return output;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private void conectarNomesSockets(int port) throws IOException {
        socketServicoNome =  new Socket("localhost", port);
    }

    private void conectarAppSockets(int port) throws IOException {
        socketApp =  new Socket("localhost", port);
    }

    public void closeConnection() throws IOException {
        socketServicoNome.close();
        socketApp.close();
        socketServicoNome.close();
        nomesParaStub.close();
        appParaStub.close();
        stubParaApp.close();
        stubParaNomes.close();
    }

}
