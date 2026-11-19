import java.io.*;
import java.util.Scanner;

public class RF_3 {

    // Arquivos CSV para persistência
    private static final String ARQUIVO_CHUVA = "/C://Users//57041669890//Documents//relatorio_chuvas.txt/";
    private static final String ARQUIVO_TALHOES = "C:/Users/57041669890/Documents/historico_chuvas.xlsx";

    public static void main(String[] args) {
        Scanner input = new Scanner(System.in == null ? System.in : System.in); // Compatível com terminal padrão
        int escolha;
        int[][] talhoes = new int[4][4];
        boolean talhoesCadastrados = false;

        // Garantir cabeçalho do arquivo de chuvas se não existir
        inicializarArquivoChuva();

        do {
            exibirMenu();
            escolha = input.nextInt();
            input.nextLine(); // Limpar buffer

            switch (escolha) {
                case 1:
                    cadastrarChuvas(input);
                    break;
                case 2:
                    consultarHistoricoChuvas();
                    break;
                case 3:
                    identificarSemanaMaisChuvosa();
                    break;
                case 4:
                    gerarDashboardClimatico();
                    break;
                case 5:
                    talhoes = cadastrarTalhoes(input);
                    talhoesCadastrados = true;
                    break;
                case 6:
                    if (talhoesCadastrados) {
                        exibirMapaEAlertas(talhoes);
                    } else {
                        System.out.println("\n[AVISO] Cadastre os talhões primeiro (Opção 5)!\n");
                    }
                    break;
                case 7:
                    if (talhoesCadastrados) {
                        gerarRelatorioGerencial(talhoes);
                    } else {
                        System.out.println("\n[AVISO] Cadastre os talhões primeiro (Opção 5)!\n");
                    }
                    break;
                case 8:
                    System.out.println("\n==========================");
                    System.out.println("  ENCERRANDO SISTEMA...");
                    System.out.println("==========================\n");
                    break;
                default:
                    System.out.println("\n[ERRO] Opção inválida! Tente novamente.\n");
            }
        } while (escolha != 8);

        input.close();
    }

    // --- MENU PRINCIPAL ---
    public static void exibirMenu() {
        System.out.println("""
                ==========================================
                    SISTEMA INTEGRADO DO AGRONEGÓCIO
                ==========================================
                1 - Cadastrar Semana Agrícola (Chuva) [RF04]
                2 - Consultar Histórico de Chuvas [RF05]
                3 - Identificar Semana Mais Chuvosa [RF06]
                4 - Dashboard Climático [RF07]
                5 - Cadastrar Matriz de Talhões (Umidade) [RF02/08]
                6 - Exibir Mapa e Alertas de Irrigação [RF02/10]
                7 - Relatório Gerencial da Fazenda [RF11/12]
                8 - Sair
                ==========================================
                Escolha uma opção:\s""");
    }

    // --- INICIALIZAR ARQUIVO DE CHUVAS ---
    public static void inicializarArquivoChuva() {
        File arquivo = new File(ARQUIVO_CHUVA);
        if (!arquivo.exists()) {
            try (BufferedWriter bw = new BufferedWriter(new FileWriter(arquivo))) {
                bw.write("data,seg,ter,qua,qui,sex,sab,dom,media,total\n");
            } catch (IOException e) {
                System.out.println("Erro ao criar arquivo de histórico: " + e.getMessage());
            }
        }
    }

    // --- RF01 & RF04: CADASTRAR CHUVAS E SALVAR EM CSV ---
    public static void cadastrarChuvas(Scanner input) {
        System.out.println("\n========================");
        System.out.println("  CADASTRO DE CLIMA");
        System.out.println("========================");
        System.out.print("Digite a data da semana (ex: 10/11/2026): ");
        String data = input.nextLine();

        double[] chuva = new double[7];
        String[] dias = {"Segunda", "Terça", "Quarta", "Quinta", "Sexta", "Sábado", "Domingo"};
        double total = 0;

        for (int i = 0; i < 7; i++) {
            System.out.print("Digite o volume de chuva (mm) de " + dias[i] + ": ");
            chuva[i] = input.nextDouble();
            total += chuva[i];
        }
        input.nextLine(); // Limpar buffer

        double media = total / 7;

        // Salvando no CSV (RF04)
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(ARQUIVO_CHUVA, true))) {
            StringBuilder linha = new StringBuilder(data);
            for (double c : chuva) {
                linha.append(",").append(c);
            }
            linha.append(",").append(String.format("%.1f", media)).append(",").append(String.format("%.1f", total));
            bw.write(linha.toString());
            bw.newLine();
            System.out.println("\n[SUCESSO] Registro salvo com sucesso no histórico!\n");
        } catch (IOException e) {
            System.out.println("[ERRO] Não foi possível salvar o arquivo: " + e.getMessage());
        }
    }

    // --- RF05: CONSULTAR HISTÓRICO ---
    public static void consultarHistoricoChuvas() {
        System.out.println("\n========================================");
        System.out.println("        HISTÓRICO DE CHUVAS (CSV)");
        System.out.println("========================================");
        try (BufferedReader br = new BufferedReader(new FileReader(ARQUIVO_CHUVA))) {
            String linha;
            boolean cabecalho = true;
            while ((linha = br.readLine()) != null) {
                if (cabecalho) {
                    cabecalho = false;
                    continue; // Pula o cabeçalho
                }
                String[] partes = linha.split(",");
                System.out.println("Semana: " + partes[0] + " | Média: " + partes[8] + " mm | Total: " + partes[9] + " mm");
            }
        } catch (IOException e) {
            System.out.println("[ERRO] Erro ao ler histórico: " + e.getMessage());
        }
        System.out.println("========================================\n");
    }

    // --- RF06: IDENTIFICAR SEMANA MAIS CHUVOSA ---
    public static void identificarSemanaMaisChuvosa() {
        try (BufferedReader br = new BufferedReader(new FileReader(ARQUIVO_CHUVA))) {
            String linha;
            boolean cabecalho = true;
            String semanaMaisChuvosa = "";
            double maiorTotal = -1;

            while ((linha = br.readLine()) != null) {
                if (cabecalho) {
                    cabecalho = false;
                    continue;
                }
                String[] partes = linha.split(",");
                double total = Double.parseDouble(partes[9]);
                if (total > maiorTotal) {
                    maiorTotal = total;
                    semanaMaisChuvosa = partes[0];
                }
            }

            if (maiorTotal != -1) {
                System.out.println("\n===== SEMANA MAIS CHUVOSA =====");
                System.out.println("Semana: " + semanaMaisChuvosa);
                System.out.println("Total: " + maiorTotal + " mm\n");
            } else {
                System.out.println("\n[AVISO] Nenhum registro encontrado no histórico.\n");
            }
        } catch (IOException e) {
            System.out.println("[ERRO] Erro ao processar arquivo: " + e.getMessage());
        }
    }

    // --- RF07: DASHBOARD CLIMÁTICO ---
    public static void gerarDashboardClimatico() {
        try (BufferedReader br = new BufferedReader(new FileReader(ARQUIVO_CHUVA))) {
            String linha;
            boolean cabecalho = true;
            int qtdSemanas = 0;
            double volumeAcumulado = 0;
            double maiorChuva = -1;
            double menorChuva = Double.MAX_VALUE;
            double somaMediasGerais = 0;

            while ((linha = br.readLine()) != null) {
                if (cabecalho) {
                    cabecalho = false;
                    continue;
                }
                String[] partes = linha.split(",");
                double total = Double.parseDouble(partes[9]);
                double media = Double.parseDouble(partes[8]);

                qtdSemanas++;
                volumeAcumulado += total;
                somaMediasGerais += media;

                if (total > maiorChuva) maiorChuva = total;
                if (total < menorChuva) menorChuva = total;
            }

            if (qtdSemanas > 0) {
                double mediaGeral = somaMediasGerais / qtdSemanas;
                System.out.println("\n===== DASHBOARD CLIMÁTICO =====");
                System.out.println("Semanas cadastradas: " + qtdSemanas);
                System.out.println("Volume acumulado: " + volumeAcumulado + " mm");
                System.out.println("Maior chuva (total semanal): " + maiorChuva + " mm");
                System.out.println("Menor chuva (total semanal): " + menorChuva + " mm");
                System.out.printf("Média geral: %.1f mm\n", mediaGeral);
                System.out.println("===============================\n");
            } else {
                System.out.println("\n[AVISO] Sem dados suficientes para gerar o Dashboard.\n");
            }
        } catch (IOException e) {
            System.out.println("[ERRO] Erro ao ler dados para o dashboard: " + e.getMessage());
        }
    }

    // --- RF02 & RF08: CADASTRAR TALHÕES E SALVAR CSV ---
    public static int[][] cadastrarTalhoes(Scanner input) {
        int[][] talhoes = new int[4][4];
        System.out.println("\n========================================");
        System.out.println("    CADASTRO DE UMIDADE DOS TALHÕES");
        System.out.println("========================================");

        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                char letraLinha = (char) ('A' + i);
                System.out.print("Digite a umidade (%) do talhão " + letraLinha + (j + 1) + ": ");
                talhoes[i][j] = input.nextInt();
            }
        }
        input.nextLine(); // Limpar buffer

        // Salvar automaticamente em CSV (RF08)
        salvarMapaTalhoes(talhoes);
        return talhoes;
    }

    public static void salvarMapaTalhoes(int[][] talhoes) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter("mapa_talhoes.csv"))) {
            bw.write("talhao,umidade\n");
            for (int i = 0; i < 4; i++) {
                for (int j = 0; j < 4; j++) {
                    char letraLinha = (char) ('A' + i);
                    bw.write(letraLinha + String.valueOf(j + 1) + "," + talhoes[i][j] + "\n");
                }
            }
            System.out.println("\n[SUCESSO] Mapa de talhões exportado para 'mapa_talhoes.csv'!\n");
        } catch (IOException e) {
            System.out.println("[ERRO] Erro ao salvar mapa de talhões: " + e.getMessage());
        }
    }

    // --- RF02, RF09 & RF10: EXIBIR MAPA, CLASSIFICAR E ALERTAS ---
    public static void exibirMapaEAlertas(int[][] talhoes) {
        System.out.println("\n========================================");
        System.out.println("         MAPA DE UMIDADE (4x4)");
        System.out.println("========================================");

        System.out.println("     1    2    3    4");
        for (int i = 0; i < 4; i++) {
            char letraLinha = (char) ('A' + i);
            System.out.print(letraLinha + " | ");
            for (int j = 0; j < 4; j++) {
                System.out.printf("%2d%%  ", talhoes[i][j]);
            }
            System.out.println();
        }

        System.out.println("\n==============================");
        System.out.println("  ALERTAS DE IRRIGAÇÃO (RF10)");
        System.out.println("==============================");
        int totalAlertas = 0;

        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                int umidade = talhoes[i][j];
                char letraLinha = (char) ('A' + i);
                String nomeTalhao = letraLinha + String.valueOf(j + 1);

                // Classificação (RF09)
                if (umidade < 20) {
                    System.out.println(nomeTalhao + " - Crítico (" + umidade + "%)");
                    totalAlertas++;
                } else if (umidade >= 20 && umidade <= 30) {
                    System.out.println(nomeTalhao + " - Atenção (" + umidade + "%)");
                    totalAlertas++;
                }
            }
        }
        System.out.println("Total de áreas que exigem intervenção: " + totalAlertas);
        System.out.println("========================================\n");
    }

    // --- RF11 & RF12: RELATÓRIO GERENCIAL DA FAZENDA ---
    public static void gerarRelatorioGerencial(int[][] talhoes) {
        int somaUmidade = 0;
        int talhoesAdequados = 0;
        int talhoesCriticos = 0;
        int talhoesAtencao = 0;

        int menorUmidade = Integer.MAX_VALUE;
        int maiorUmidade = Integer.MIN_VALUE;
        String talhaoSeco = "";
        String talhaoUmido = "";

        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                int u = talhoes[i][j];
                somaUmidade += u;
                char letraLinha = (char) ('A' + i);
                String nome = letraLinha + String.valueOf(j + 1);

                if (u < 20) {
                    talhoesCriticos++;
                } else if (u <= 30) {
                    talhoesAtencao++;
                } else {
                    talhoesAdequados++;
                }

                if (u < menorUmidade) {
                    menorUmidade = u;
                    talhaoSeco = nome + " - " + u + "%";
                }
                if (u > maiorUmidade) {
                    maiorUmidade = u;
                    talhaoUmido = nome + " - " + u + "%";
                }
            }
        }

        double mediaGeral = (double) somaUmidade / 16;
        double percIrrigacao = ((double) (talhoesCriticos + talhoesAtencao) / 16) * 100;

        // Montando o texto do relatório
        StringBuilder relatorio = new StringBuilder();
        relatorio.append("===== RELATORIO DA FAZENDA =====\n");
        relatorio.append("Media Geral: ").append(String.format("%.1f", mediaGeral)).append("%\n");
        relatorio.append("Talhoes Criticos: ").append(talhoesCriticos).append("\n");
        relatorio.append("Talhoes de Atencao: ").append(talhoesAtencao).append("\n");
        relatorio.append("Talhoes Adequados: ").append(talhoesAdequados).append("\n");
        relatorio.append("Necessidade de Irrigacao: ").append(String.format("%.1f", percIrrigacao)).append("%\n");
        relatorio.append("Talhao mais seco: ").append(talhaoSeco).append("\n");
        relatorio.append("Talhao mais umido: ").append(talhaoUmido).append("\n");
        relatorio.append("================================");

        // Exibir na tela
        System.out.println("\n" + relatorio.toString() + "\n");

        // Exportar para arquivo relatorio_fazenda.txt (RF12)
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(ARQUIVO_TALHOES))) {
            bw.write(relatorio.toString());
            System.out.println("[SUCESSO] Relatório gerencial exportado para '" + ARQUIVO_TALHOES + "'!\n");
        } catch (IOException e) {
            System.out.println("[ERRO] Erro ao exportar relatório gerencial: " + e.getMessage());
        }
    }
}