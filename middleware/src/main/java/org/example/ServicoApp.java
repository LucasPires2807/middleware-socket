package org.example;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ServicoApp {
    private Socket socket;

    // TODO implementar funcao para subscrever serviço no serviço de nomes

    public Object receberSolicitacaoServico(){
        try{
            conectarSockets(90);
            // Recebendo o endereço que o nome foi passado para o stub pelo usuário
            BufferedReader stubParaApp = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            List<Object> endereco = new ArrayList<>();
            endereco.add(stubParaApp.readLine());
            endereco.add(stubParaApp.readLine());
            assert (endereco.get(0) instanceof String);
            assert (endereco.get(1) instanceof Integer);
            List<Float> parametros = Collections.singletonList(Float.valueOf(stubParaApp.readLine()));
            // O endereço é suposto de ser o localhost, vamos verificar de acordo com a porta
            switch ((int) endereco.get(1)){
                // Ver como os parâmetros serão passados para o serviço app
                case 8002:
                    return somar(parametros);
                case 8003:
                    return subtrair(parametros);
            }
            // Enviando o ip e a porta
            PrintWriter appParaStub = new PrintWriter(socket.getOutputStream(), true);
            appParaStub.println();

        } catch (Exception e){
            e.printStackTrace();
        }
        return 0;
    }

    private float somar(List<Float> nums){
        return nums.get(0) + nums.get(1);
    }

    private float subtrair(List<Float> nums){
        return nums.get(0) - nums.get(1);
    }

    private void conectarSockets(int port) throws IOException {
        ServerSocket serverStubSocket = new ServerSocket(port);
        socket = serverStubSocket.accept();
    }

}
