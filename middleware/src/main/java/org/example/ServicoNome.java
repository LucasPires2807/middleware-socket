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
    public ServicoNome() {
        List<Object> valores1 = new ArrayList<>();
        valores1.add("localhost");
        valores1.add(8001);
        List<Object> valores2 = new ArrayList<>();
        valores2.add("localhost");
        valores2.add(8002);
        List<Object> valores3 = new ArrayList<>();
        valores2.add("localhost");
        valores2.add(8002);
        listaServicos.put("Login", valores1);
        listaServicos.put("Somar", valores2);
        listaServicos.put("Subtrair", valores3);
    }

    public Map<String, List<Object>> getMeuHashMap() {
        return listaServicos;
    }

    public String receberSolicitacaoServico(List<String> _valores){

    }
    private static ServerSocket serverSocket;
    private static Socket socket;
    private static PrintWriter output;
    private static BufferedReader input;
    
    private static void initializeStreamVariables() throws IOException {
        output = new PrintWriter(socket.getOutputStream(), true);
        input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
    }
    public static String recieveMessage() throws IOException {
        String request = input.readLine();
        return request;
    }

    public static void connect(int port) throws IOException {
        serverSocket = new ServerSocket(port);
        socket = serverSocket.accept();
        initializeStreamVariables();
    }

    public static void closeConnection() throws IOException {
        input.close();
        output.close();
        serverSocket.close();
        socket.close();
    }
}
