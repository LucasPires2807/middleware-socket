package org.example;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.*;

public class ServicoApp {
    private Socket socket;
    private ExecutorService executor = Executors.newSingleThreadExecutor();

    // TODO implementar funcao para subscrever serviço no serviço de nomes

    public static void main(String[] args) {
        ServicoApp servicoApp = new ServicoApp();
        servicoApp.receberSolicitacaoServico();
    }

    public Object receberSolicitacaoServico(){
        return executor.submit(() -> {
            try{
                conectarSockets(90);
                // Recebendo o endereço que o nome foi passado para o stub pelo usuário
                BufferedReader stubParaApp = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                System.out.println("Serviço de aplicação lendo o endereço enviado pelo stub...");
                List<Object> endereco = convertStringToList(stubParaApp.readLine());
                System.out.println(endereco);
                String parametros_cru = stubParaApp.readLine();
                List<Integer> parametros = convertStringToListInteger(parametros_cru);
                // O endereço é suposto de ser o localhost, vamos verificar de acordo com a porta
                Object resultado = null;
                System.out.println("Serviço de aplicação executando a função solicitada...");
                // Ver como os parâmetros serão passados para o serviço app
                if (endereco.get(1).equals("8002")) {
                    resultado = somar(parametros);
                } else if (endereco.get(1).equals("8003")) {
                    resultado = subtrair(parametros);
                }
                PrintWriter appParaStub = new PrintWriter(socket.getOutputStream(), true);
                appParaStub.println(resultado);
            } catch (Exception e){
                e.printStackTrace();
            }
            return 0;
        });
    }

    private List<Integer> convertStringToListInteger(String input) {
        input = input.substring(1, input.length() - 1);
        String[] parts = input.split(", ");

        List<Integer> integerList = new ArrayList<>();
        for (String part : parts) {
            integerList.add(Integer.parseInt(part));
        }
        return integerList;
    }

    private List<Object> convertStringToList(String input) {
        input = input.substring(1, input.length() - 1);
        String[] parts = input.split(", ");
        return new ArrayList<>(Arrays.asList(parts));
    }

    private float somar(List<Integer> nums){
        return nums.get(0) + nums.get(1);
    }

    private float subtrair(List<Integer> nums){
        return nums.get(0) - nums.get(1);
    }

    private void conectarSockets(int port) throws IOException {
        System.out.println("Serviço de aplicação um server socket...");
        ServerSocket serverStubSocket = new ServerSocket(port);
        socket = serverStubSocket.accept();
        System.out.println("Conexão aceita!");
    }

}
