package com.mycompany.radixsort;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

public class RadixSortMSD {

    public static void radixSortMSD(int[] vetor) {
        int maximoDigitos = getMaximoDigitos(vetor);
        radixSortMSD(vetor, 0, vetor.length - 1, maximoDigitos - 1);
    }

    private static void radixSortMSD(int[] vetor, int inicio, int fim, int digito) {
        if (fim <= inicio || digito < 0) {
            return;
        }

        int[][] baldes = new int[19][fim - inicio + 1]; 
        int[] tamanhosBaldes = new int[19];


        for (int i = inicio; i <= fim; i++) {
            int num = vetor[i];
            int digitoAtual;
            if (num >= 0) {
                digitoAtual = (num / (int) Math.pow(10, digito)) % 10 + 9; 
            } else {
                digitoAtual = 9 - (-num / (int) Math.pow(10, digito)) % 10;
            }
            baldes[digitoAtual][tamanhosBaldes[digitoAtual]++] = num;
        }


        int posicaoInicial = inicio;
        for (int i = 0; i < 19; i++) {
            if (tamanhosBaldes[i] > 0) {
                radixSortMSD(baldes[i], 0, tamanhosBaldes[i] - 1, digito - 1);
               
                for (int j = 0; j < tamanhosBaldes[i]; j++) {
                    vetor[posicaoInicial++] = baldes[i][j];
                }
            }
        }
    }

    private static int getMaximoDigitos(int[] vetor) {
        int maximoDigitos = 0;
        for (int num : vetor) {
            int digitos = (int) Math.log10(Math.abs(num)) + 1;
            if (digitos > maximoDigitos) {
                maximoDigitos = digitos;
            }
        }
        return maximoDigitos;
    }

    public static int[] carregarDados(String nomeArquivo) throws FileNotFoundException {
        Scanner scanner = new Scanner(new File(nomeArquivo));
        int[] dados = new int[100000]; 
        int i = 0;
        while (scanner.hasNextInt()) {
            dados[i++] = scanner.nextInt();
        }
        scanner.close();
        return dados;
    }

    public static void main(String[] args) throws FileNotFoundException {
        String nomeArquivo = "C:\\Users\\Administrator\\Downloads\\dados100mil.txt";
        int[] dados = carregarDados(nomeArquivo);

        long inicio = System.currentTimeMillis();
        radixSortMSD(dados); 
        long fim = System.currentTimeMillis();

        long tempoExecucao = fim - inicio;

      
        long horas = TimeUnit.MILLISECONDS.toHours(tempoExecucao);
        long minutos = TimeUnit.MILLISECONDS.toMinutes(tempoExecucao) % 60;
        long segundos = TimeUnit.MILLISECONDS.toSeconds(tempoExecucao) % 60;
        long milissegundos = tempoExecucao % 1000;
        String horaFormatada = String.format("%02d:%02d:%02d:%03d", horas, minutos, segundos, milissegundos);

        System.out.println("Tempo de execução: " + horaFormatada);
        // System.out.println(Arrays.toString(dados)); // Imprimir o array ordenado
    }
}
