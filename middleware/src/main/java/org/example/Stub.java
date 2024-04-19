package org.example;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.io.InputStreamReader;
public class Stub {
    private static Socket socket;
    private static BufferedReader keyboardInput;
    private static BufferedReader serverAnswer;
    private static PrintWriter clientAnswer;
    private String servicoSolicitado;

    public void receberSolicitacaoServico(String _servicoSolicitado){
        servicoSolicitado = _servicoSolicitado;

    }
    private static void initializeStreamVariables() throws IOException {
        keyboardInput = new BufferedReader(new InputStreamReader(System.in));
        clientAnswer = new PrintWriter(socket.getOutputStream(), true);
        serverAnswer = new BufferedReader(new InputStreamReader(socket.getInputStream()));
    }

    public static void connect(int port) throws IOException {
        socket = new Socket("localhost", port);
        initializeStreamVariables();
    }
    public static String sendMessage() throws IOException {
        String str = keyboardInput.readLine();
        return str;
    }

    public static void readMessage() throws IOException {
        System.out.println(serverAnswer.readLine());
    }

    public static void closeConnection() throws IOException {
        clientAnswer.close();
        keyboardInput.close();
        socket.close();
    }

}
