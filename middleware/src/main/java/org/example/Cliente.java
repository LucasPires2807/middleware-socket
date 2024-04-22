package org.example;

import java.util.ArrayList;
import java.util.List;

public class Cliente {

    Stub stub = new Stub();

    public Object solicitarServico(){
        String funcao = "Somar";
        List<Integer> valores = new ArrayList<>();
        valores.add(5);
        valores.add(2);
        return stub.receberSolicitacaoServico(funcao, valores);
    }

    public static void main(String[] args) {
        Cliente cliente = new Cliente();
        System.out.println(cliente.solicitarServico());
    }

}
