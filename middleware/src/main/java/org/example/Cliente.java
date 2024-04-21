package org.example;

import java.util.ArrayList;
import java.util.List;

public class Cliente {

    Stub stub = new Stub();

    public void solicitarServico(){
        String funcao = "Soma";
        List<String> valores = new ArrayList<>();
        valores.add("5");
        valores.add("2");
        stub.receberSolicitacaoServico(funcao, valores);

    }
}
