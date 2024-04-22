package org.example;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.*;

public class ServicoNome {
    Map<String, List<Object>> listaServicos = new HashMap<>();
    private static ServerSocket serverStubSocket;
    private static Socket socket;
    private BufferedReader stubParaNomes;
    private PrintWriter nomesParaStub;

    private ExecutorService executor = Executors.newSingleThreadExecutor();

    // TODO implementar a subscrição de serviço no serviço de nomes
    public ServicoNome() {
        List<Object> valores1 = new ArrayList<>();
        valores1.add("localhost");
        valores1.add(8001);
        List<Object> valores2 = new ArrayList<>();
        valores2.add("localhost");
        valores2.add(8002);
        List<Object> valores3 = new ArrayList<>();
        valores3.add("localhost");
        valores3.add(8003);
        listaServicos.put("Login", valores1);
        listaServicos.put("Somar", valores2);
        listaServicos.put("Subtrair", valores3);
    }

    public Map<String, List<Object>> getMeuHashMap() {
        return listaServicos;
    }

    public static void main(String[] args) {
        ServicoNome servicoNome = new ServicoNome();
        servicoNome.receberSolicitacaoServico();
    }

    public void receberSolicitacaoServico(){
        executor.submit(() -> {
            try{
                conectarSockets(80);
                // Recebendo o nome que o usuário passou para o stub
                stubParaNomes = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                System.out.println("Serviço de nomes lendo a função solicitada enviada ao stub...");
                String nome = stubParaNomes.readLine();
                // Enviando o ip e a porta
                nomesParaStub = new PrintWriter(socket.getOutputStream(), true);
                System.out.println("Serviço de nomes enviando o endereço da função solicitada...");
                System.out.println("O endereço é: " + listaServicos.get(nome));
                nomesParaStub.println(listaServicos.get(nome));
                System.out.println("Serviço de nomes fechando conexão...");
                closeConnection();
            } catch (Exception e){
                e.printStackTrace();
            }
        });
    }

    private static void conectarSockets(int port) throws IOException {
        System.out.println("Serviço de nomes abrindo um server socket...");
        serverStubSocket = new ServerSocket(port);
        socket = serverStubSocket.accept();
        System.out.println("Conexão aceita!");
    }

    public void closeConnection() throws IOException {
        nomesParaStub.close();
        stubParaNomes.close();
        serverStubSocket.close();
        socket.close();
    }
}
