import java.util.Scanner;

void main() {
    Scanner input = new Scanner(System.in);

    int escolha = 0;
    int talhoes[][] = new int[4][4];

    System.out.println("""
            ==========================
                MENU DE ESCOLHAS
            
            1 - Cadastrar dados
            2 - Consultar histórico
            3 - Exibir mapa do campo
            4 - Relatório de Alertas de irrigação 
            5 - Sair
            
            ==========================
            """);
    switch (escolha){
        case 1:
            System.out.println("========================");
            System.out.println("  CADASTRO DE DADOS");
            System.out.println("========================");

            int data[] = new int[3];
            int chuva[] = new int[7];
            double media = 0;
            double maior = 0;
            double TtChuva = 0;

            for (int i = 0; i < 3; i++) {
                if (i == 0){
                    System.out.print("Digite o numero do dia da semana: ");
                    data[i] = input.nextInt();
                }
                if (i == 1){
                    System.out.print("Digite o numero do mês: ");
                    data[i] = input.nextInt();
                }
                if (i == 3){
                    System.out.print("Digite o ano: ");
                    data[i] = input.nextInt();
                }



            }

            for (int i = 0; i < 7; i++) {
                IO.println(String.format("Digite o volume de chuva(mm) do dia " + (i + 1)));
                chuva[i] = input.nextInt();

                if (maior < chuva[i]) {
                    maior = i;
                }

                TtChuva = chuva[i];
            }
            media = TtChuva/7;

            break;
        case 2:
            System.out.println("Terça-feira");

            break;

        case 3:
            System.out.println("Terça-feira");

            break;

        case 4:
            for (int i = 0; i < 4; i++) {
                for (int j = 0; j < 4; j++) {
                    System.out.print("Digite o percentual de umidade  do solo do talhão da linha " + (i + 1) + " da coluna " + (1 + j) +": ");
                    talhoes[i][j] = input.nextInt();
                }
            }
            System.out.println("==============================");
            System.out.println("  TALHÕES COM BAIXA UMIDADE");
            System.out.println("==============================");

            for (int i = 0; i < 4; i++) {
                for (int j = 0; j < 4; j++) {
                    if (talhoes[i][j] < 30){
                        System.out.println("Talhão da linha " + (i + 1) + " da coluna " + (j + 1) +" está com " + talhoes[i][j] +"%");
                    }
                }
            }
            break;

        case 5:
            System.out.println("==========================");
            System.out.println("  ENCERRANDO SISTEMA...");
            System.out.println("==========================");
            break;

        default:
            System.out.println("Outro dia");
    }
}