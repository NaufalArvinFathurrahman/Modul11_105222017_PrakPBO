import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

class DataPenumpangTidakValidException extends RuntimeException {
    public DataPenumpangTidakValidException(String message) {
        super(message);
    }
}

class RuteTidakDitemukanException extends Exception {
    public RuteTidakDitemukanException(String message) {
        super(message);
    }
}

class TiketHabisException extends Exception {
    private String namaKereta;
    private int sisaKursi;

    public TiketHabisException(String message, String namaKereta, int sisaKursi) {
        super(message);
        this.namaKereta = namaKereta;
        this.sisaKursi = sisaKursi;
    }

    public String getNamaKereta() {
        return namaKereta;
    }

    public int getSisaKursi() {
        return sisaKursi;
    }
}

class KeretaApi {
    private String kodeKereta;
    private String namaKereta;
    private String rutePerjalanan;
    private int sisaKursi;

    public KeretaApi(String kodeKereta, String namaKereta, String rutePerjalanan, int kapasitas) {
        this.kodeKereta = kodeKereta;
        this.namaKereta = namaKereta;
        this.rutePerjalanan = rutePerjalanan;
        this.sisaKursi = kapasitas;
    }

    public String getKodeKereta() { return kodeKereta; }
    public String getNamaKereta() { return namaKereta; }
    public String getRutePerjalanan() { return rutePerjalanan; }
    public int getSisaKursi() { return sisaKursi; }

    public void kurangiKursi(int jumlah) {
        this.sisaKursi -= jumlah;
    }
    
    public void tampilkanInfo() {
        System.out.printf("| %-4s | %-12s | %-9s | %-10d |\n", kodeKereta, namaKereta, rutePerjalanan, sisaKursi);
    }
}

class PusatReservasi {
    private List<KeretaApi> daftarKereta;

    public PusatReservasi() {
        this.daftarKereta = new ArrayList<>();
        daftarKereta.add(new KeretaApi("K01", "Argo Bromo", "JKT - SBY", 50));
        daftarKereta.add(new KeretaApi("K02", "Parahyangan", "JKT - BDG", 15));
    }

    public void lihatJadwal() {
        System.out.println("\n=================================================");
        System.out.println("| KODE | NAMA KERETA  | RUTE      | SISA KURSI |");
        System.out.println("=================================================");
        for (KeretaApi kereta : daftarKereta) {
            kereta.tampilkanInfo();
        }
        System.out.println("=================================================");
    }

    public void pesanTiket(String kodeKereta, String nik, String namaPenumpang, int jumlahTiket) 
            throws RuteTidakDitemukanException, TiketHabisException {
        
        if (nik == null || nik.length() != 16 || !nik.matches("[0-9]+")) {
            throw new DataPenumpangTidakValidException("NIK tidak valid! Pastikan NIK terdiri dari tepat 16 digit angka tanpa huruf/simbol.");
        }

        KeretaApi keretaPilihan = null;
        for (KeretaApi kereta : daftarKereta) {
            if (kereta.getKodeKereta().equalsIgnoreCase(kodeKereta)) {
                keretaPilihan = kereta;
                break;
            }
        }

        if (keretaPilihan == null) {
            throw new RuteTidakDitemukanException("Kereta dengan kode '" + kodeKereta + "' tidak ditemukan dalam sistem kami.");
        }

        if (jumlahTiket > keretaPilihan.getSisaKursi()) {
            throw new TiketHabisException(
                "Maaf, kursi tidak mencukupi untuk jumlah tiket pesanan Anda.",
                keretaPilihan.getNamaKereta(),
                keretaPilihan.getSisaKursi()
            );
        }

        keretaPilihan.kurangiKursi(jumlahTiket); 
        
        System.out.println("\n-------------------------------------------------");
        System.out.println("             STRUK RESERVASI TIKET               ");
        System.out.println("-------------------------------------------------");
        System.out.println("Status       : BERHASIL \u2705"); 
        System.out.println("Nama         : " + namaPenumpang);
        System.out.println("NIK          : " + nik);
        System.out.println("Kereta       : " + keretaPilihan.getNamaKereta() + " (" + keretaPilihan.getRutePerjalanan() + ")");
        System.out.println("Jumlah Tiket : " + jumlahTiket);
        System.out.println("-------------------------------------------------");
    }
}

public class App {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        PusatReservasi reservasi = new PusatReservasi();
        boolean isRunning = true;

        System.out.println("*************************************************");
        System.out.println("    SELAMAT DATANG DI SISTEM JAVA EXPRESS        ");
        System.out.println("*************************************************");

        try {
            while (isRunning) {
                System.out.println("\n[MENU UTAMA]");
                System.out.println("1. Lihat Jadwal Kereta");
                System.out.println("2. Pesan Tiket");
                System.out.println("3. Keluar");
                System.out.print("Pilih menu (1/2/3): ");

                int pilihan = 0;
                
                try {
                    pilihan = scanner.nextInt();
                    scanner.nextLine(); 
                } catch (InputMismatchException e) {
                    System.out.println(">>> [ERROR] Input menu tidak valid! Harap masukkan angka bulat (1-3).");
                    scanner.nextLine(); 
                    continue; 
                }

                switch (pilihan) {
                    case 1:
                        reservasi.lihatJadwal();
                        break;

                    case 2:
                        try {
                            System.out.println("\n--- FORM PEMESANAN TIKET ---");
                            System.out.print("Masukkan Kode Kereta: ");
                            String kode = scanner.nextLine();
                            
                            System.out.print("Masukkan NIK Penumpang (16 digit): ");
                            String nik = scanner.nextLine();
                            
                            System.out.print("Masukkan Nama Penumpang: ");
                            String nama = scanner.nextLine();
                            
                            System.out.print("Masukkan Jumlah Tiket: ");
                            int jumlah = scanner.nextInt();
                            scanner.nextLine(); 
                            
                            reservasi.pesanTiket(kode, nik, nama, jumlah);

                        } catch (InputMismatchException e) {
                            System.out.println(">>> [ERROR] Format jumlah tiket salah! Harus berupa angka numerik.");
                            scanner.nextLine(); 
                        } catch (DataPenumpangTidakValidException e) {
                            System.out.println(">>> [GAGAL - NIK SALAH] " + e.getMessage());
                        } catch (RuteTidakDitemukanException e) {
                            System.out.println(">>> [GAGAL - KODE SALAH] " + e.getMessage());
                        } catch (TiketHabisException e) {
                            System.out.println(">>> [GAGAL - KURSI KURANG] " + e.getMessage());
                            System.out.println("    Info tambahan: Kereta " + e.getNamaKereta() + 
                                               " saat ini hanya menyisakan " + e.getSisaKursi() + " kursi.");
                        } catch (Exception e) {
                            System.out.println(">>> [FATAL ERROR] Terjadi kesalahan tak terduga: " + e.getMessage());
                        }
                        break;

                    case 3:
                        System.out.println("\nMempersiapkan proses keluar dari sistem...");
                        isRunning = false;
                        break;

                    default:
                        System.out.println(">>> [PERINGATAN] Menu tidak tersedia. Silakan ketik angka 1, 2, atau 3.");
                        break;
                }
            }
        } finally {
            if (scanner != null) {
                scanner.close(); 
            }
            System.out.println("\n=================================================");
            System.out.println("Terima kasih telah menggunakan layanan JAVA EXPRESS!");
            System.out.println("Koneksi ke database server terminal telah diputus aman.");
            System.out.println("=================================================");
        }
    }
}
