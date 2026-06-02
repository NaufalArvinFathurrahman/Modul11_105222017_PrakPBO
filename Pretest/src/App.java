import java.util.Scanner;
import java.util.InputMismatchException;

class Kalkulator {
    public int bagi(int pembilang, int penyebut) {
        return pembilang / penyebut;
    }
}

public class App {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Kalkulator kalkulator = new Kalkulator();

        try {
            System.out.print("Masukkan angka pembilang: ");
            int pembilang = scanner.nextInt();

            System.out.print("Masukkan angka penyebut: ");
            int penyebut = scanner.nextInt();

            int hasil = kalkulator.bagi(pembilang, penyebut);
            System.out.println("Hasil pembagian: " + hasil);

        } catch (ArithmeticException e) {
            System.out.println("Peringatan: Tidak dapat melakukan pembagian dengan angka nol.");
        } catch (InputMismatchException e) {
            System.out.println("Peringatan: Input tidak valid. Harap masukkan angka bulat.");
        } finally {
            if (scanner != null) {
                scanner.close();
            }
            System.out.println("Proses kalkulasi selesai dan resource memori telah dibersihkan.");
        }
    }
}
