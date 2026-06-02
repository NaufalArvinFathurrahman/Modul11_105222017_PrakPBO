import java.util.Scanner;
import java.util.InputMismatchException;

// Soal 3
class KopiHabisException extends RuntimeException {
    public KopiHabisException(String message) {
        super(message);
    }
}

// Soal 4
class UangKurangException extends Exception {
    public UangKurangException(String message) {
        super(message);
    }
}

class Pelanggan {
    // Soal 3
    private int stokKopi = 5;

    // Soal 2
    public void daftarMember(int umur) {
        if (umur < 17) {
            throw new IllegalArgumentException("Maaf, umur Anda belum mencukupi untuk menjadi Member VIP");
        }
        System.out.println("Pendaftaran Member VIP berhasil.");
    }

    // Soal 3
    public void pesanKopi(int jumlahPesanan) {
        if (jumlahPesanan > stokKopi) {
            throw new KopiHabisException("Stok kopi habis atau tidak mencukupi pesanan Anda!");
        }
        stokKopi -= jumlahPesanan;
        System.out.println("Pesanan kopi berhasil. Sisa stok: " + stokKopi);
    }
}

class MesinKasir {
    // Soal 4
    public void bayar(int totalBelanja, int uangDiberikan) throws UangKurangException {
        if (uangDiberikan < totalBelanja) {
            throw new UangKurangException("Uang pembayaran tidak mencukupi!");
        }
        System.out.println("Pembayaran berhasil. Kembalian: Rp " + (uangDiberikan - totalBelanja));
    }

    // Soal 5
    public void cetakStruk(boolean statusPrinter) throws Exception {
        if (!statusPrinter) {
            throw new Exception("Printer error: Kertas struk habis!");
        }
        System.out.println("Mencetak struk...");
    }
}

public class App {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        
        // Soal 1
        int[] hargaMenu = new int[3];

        // Soal 1
        for (int i = 0; i < 4; i++) {
            try {
                System.out.print("Masukkan harga menu ke-" + (i + 1) + ": ");
                hargaMenu[i] = scanner.nextInt();
            } catch (InputMismatchException e) {
                // Soal 1
                System.out.println("Error: Input harga harus berupa angka!");
                scanner.next(); 
            } catch (ArrayIndexOutOfBoundsException e) {
                // Soal 1
                System.out.println("Error: Kapasitas memori harga sudah penuh!");
            }
        }

        Pelanggan pelanggan = new Pelanggan();
        try {
            // Soal 2
            pelanggan.daftarMember(15);
        } catch (IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage());
        }

        try {
            // Soal 3
            pelanggan.pesanKopi(10);
        } catch (KopiHabisException e) {
            System.out.println("Error: " + e.getMessage());
        }

        MesinKasir kasir = new MesinKasir();
        try {
            // Soal 4
            kasir.bayar(50000, 30000);
        } catch (UangKurangException e) {
            System.out.println("Error: " + e.getMessage());
        }

        try {
            // Soal 5
            kasir.cetakStruk(false);
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        } finally {
            // Soal 5
            if (scanner != null) {
                scanner.close();
            }
            System.out.println("Terima kasih telah berkunjung ke Cafe Java Bean. Program kasir ditutup.");
        }
    }
}
