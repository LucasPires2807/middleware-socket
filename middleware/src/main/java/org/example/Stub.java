package org.example;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.*;

public class Stub {
    private ServicoNome servicoNome;
    private ServicoApp servicoApp;
    private static Socket socketServicoNome;
    private Socket socketApp;
    private PrintWriter stubParaNomes;
    private BufferedReader nomesParaStub;
    private PrintWriter stubParaApp;
    private  BufferedReader appParaStub;
    private ExecutorService executor = Executors.newSingleThreadExecutor();

    public Object receberSolicitacaoServico(String servicoSolicitado, List<Integer> parametros) {
        try {
            conectarNomesSockets(80);
            System.out.println("Stub solicitando uma conexão ao serviço de nomes...");
            // Manda para o serviço de nomes o serviço solicitado
            stubParaNomes = new PrintWriter(socketServicoNome.getOutputStream(), true);
            System.out.println("Stub enviando o serviço solicitado para o serviço de nomes...");
            stubParaNomes.println(servicoSolicitado);
            nomesParaStub = new BufferedReader(new InputStreamReader(socketServicoNome.getInputStream()));
            // Pega o endereço do serviço
            System.out.println("Stub recebendo o endereço do serviço...");
            List<Object> enderecoServico = convertStringToList(nomesParaStub.readLine());
            System.out.println("Stub solicitando conexão ao serviço de aplicação...");

            int porta = (Integer.parseInt((String) enderecoServico.get(1)));
            conectarAppSockets(porta);

            stubParaApp = new PrintWriter(socketApp.getOutputStream(), true);
            System.out.println("Stub enviando a solicitadao da função no endereço enviado...");
            stubParaApp.println(servicoSolicitado);
            stubParaApp.println(parametros);
            appParaStub = new BufferedReader(new InputStreamReader(socketApp.getInputStream()));
            System.out.println("Stub recebendo o resultado...");
            Object output = appParaStub.readLine();
            System.out.println("Stub fechando todas as conexões...");
            closeConnection();
            return output;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private List<Object> convertStringToList(String input) {
        input = input.substring(1, input.length() - 1);
        String[] parts = input.split(", ");
        return new ArrayList<>(Arrays.asList(parts));
    }

    private static void conectarNomesSockets(int port) throws IOException {
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
