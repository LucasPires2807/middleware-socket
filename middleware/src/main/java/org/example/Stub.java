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
    private Socket socketServicoNome;
    private Socket socketApp;
    private List<String> servicoSolicitado;
    private PrintWriter stubParaNomes;
    private BufferedReader nomesParaStub;
    private PrintWriter stubParaApp;
    private  BufferedReader appParaStub;
    private List<Object> enderecoServico;
    private Object output;

    public Object receberSolicitacaoServico(List<String> servicoSolicitado) {
        this.servicoSolicitado = servicoSolicitado;
        // Conectar o Stub com o serviço de nomes
        try{
            servicoNome.receberSolicitacaoServico();
            conectarNomesSockets(80);
            // Manda para o serviço de nomes o serviço solicitado
            stubParaNomes = new PrintWriter(socketServicoNome.getOutputStream(), true);
            stubParaNomes.println(servicoSolicitado.get(0));
            nomesParaStub = new BufferedReader(new InputStreamReader(socketServicoNome.getInputStream()));
            // Pega o endereço do serviço
            enderecoServico = Collections.singletonList(nomesParaStub.readLine());
            conectarAppSockets(90);
            stubParaApp = new PrintWriter(socketApp.getOutputStream(), true);
            // Tem que fazer a lógica de enviar os parâmetros também
            stubParaApp.println(enderecoServico);
            nomesParaStub = new BufferedReader(new InputStreamReader(socketApp.getInputStream()));
            output = nomesParaStub.readLine();
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
