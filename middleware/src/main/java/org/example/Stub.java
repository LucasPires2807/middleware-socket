package org.example;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.io.InputStreamReader;
import java.util.Collections;
import java.util.List;

public class Stub {
    private static Socket socketServicoNome;
    private List<String> servicoSolicitado;
    private PrintWriter stubParaNomes;
    private BufferedReader nomesParaStub;
    private List<Object> enderecoServico;

    public void receberSolicitacaoServico(List<String> servicoSolicitado) {
        this.servicoSolicitado = servicoSolicitado;
        // Conectar o Stub com o serviço de nomes
        try{
            conectarSockets(80);
            // Manda para o serviço de nomes o serviço solicitado
            stubParaNomes = new PrintWriter(socketServicoNome.getOutputStream(), true);
            stubParaNomes.println(servicoSolicitado.get(0));
            nomesParaStub = new BufferedReader(new InputStreamReader(socketServicoNome.getInputStream()));
            // Pega o endereço do serviço
            enderecoServico = Collections.singletonList(nomesParaStub.readLine());
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void conectarSockets(int port) throws IOException {
        socketServicoNome =  new Socket("localhost", port);
    }

//    private static void initializeStreamVariables() throws IOException {
//        keyboardInput = new BufferedReader(new InputStreamReader(System.in));
//        clientAnswer = new PrintWriter(socket.getOutputStream(), true);
//        serverAnswer = new BufferedReader(new InputStreamReader(socket.getInputStream()));
//    }


//    public static String sendMessage() throws IOException {
//        String str = keyboardInput.readLine();
//        return str;
//    }
//
//    public static void readMessage() throws IOException {
//        System.out.println(serverAnswer.readLine());
//    }
//
//    public static void closeConnection() throws IOException {
//        clientAnswer.close();
//        keyboardInput.close();
//        socket.close();
//    }

}
