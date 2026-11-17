import java.util.Scanner;

void main() {
    Scanner input = new Scanner(System.in);


    //Declarando variáveis
    int talhoes[][] = new int[4][4];

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
}