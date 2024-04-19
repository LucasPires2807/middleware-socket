package org.example;

import java.util.ArrayList;
import java.util.List;

public class Cliente {

    Stub stub = new Stub();

    public void solicitarServico(){
        List<String> valores = new ArrayList<>();
        valores.add("Soma");
        valores.add("5");
        valores.add("2");
        stub.receberSolicitacaoServico(valores);

    }
}
