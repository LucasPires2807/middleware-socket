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

public class ServicoNome {
    Map<String, List<Object>> listaServicos = new HashMap<>();
    private static ServerSocket serverStubSocket;
    private static Socket socket;
    private BufferedReader stubParaNomes;
    private PrintWriter nomesParaStub;
    public ServicoNome() {
        List<Object> valores1 = new ArrayList<>();
        valores1.add("localhost");
        valores1.add(8001);
        List<Object> valores2 = new ArrayList<>();
        valores2.add("localhost");
        valores2.add(8002);
        List<Object> valores3 = new ArrayList<>();
        valores2.add("localhost");
        valores2.add(8003);
        listaServicos.put("Login", valores1);
        listaServicos.put("Somar", valores2);
        listaServicos.put("Subtrair", valores3);
    }

    public Map<String, List<Object>> getMeuHashMap() {
        return listaServicos;
    }

    public void receberSolicitacaoServico(){
        try{
            conectarSockets(80);
            // Recebendo o nome que o usuário passou para o stub
            stubParaNomes = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            String nome = stubParaNomes.readLine();
            // Enviando o ip e a porta
            nomesParaStub = new PrintWriter(socket.getOutputStream(), true);
            nomesParaStub.println(listaServicos.get(nome));

        } catch (Exception e){
            e.printStackTrace();
        }
    }

    private void conectarSockets(int port) throws IOException {
        serverStubSocket = new ServerSocket(port);
        socket = serverStubSocket.accept();
    }

    public void closeConnection() throws IOException {
        nomesParaStub.close();
        stubParaNomes.close();
        serverStubSocket.close();
        socket.close();
    }
}
