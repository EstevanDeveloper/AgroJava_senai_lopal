package org.example;
import java.util.Scanner;
//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    static void main() {
Scanner input = new Scanner(System.in);

        //DECLARANDO VERIÁVEIS
        double chuva[] = new double[7];
        double maior = 0;
        double TtChuva = 0;
        double media = 0;
// Volume de chuva por semana
        for (int i = 0; i < 7; i++) {
            IO.println(String.format("Digite o volume de chuva(mm) do dia " + (i + 1)));
             chuva[i] = input.nextInt();

             if (maior < chuva[i]){
                 maior = i;
             }
        }
// Calculando a média de chuva semanal
        for (int i = 0; i < 7; i++) {
            TtChuva = chuva[i];
        }
        media = TtChuva/7;

        System.out.println("Dia com maior indice de chuva: " + maior);
        System.out.println("Média semanal de pluviosidade : " + media);
    }
}
