import java.util.Scanner;
import java.util.InputMismatchException;
import java.io.FileNotFoundException;

// Soal 3
class SksTidakCukupException extends RuntimeException {
    public SksTidakCukupException(String message) {
        super(message);
    }
}

// Soal 4
class KelasPenuhException extends Exception {
    public KelasPenuhException(String message) {
        super(message);
    }
}

class Mahasiswa {
    // Soal 3
    private int sisaSks;

    // Soal 2
    public void setSksMaksimal(int sks) {
        if (sks < 2 || sks > 24) {
            throw new IllegalArgumentException("Kesalahan sistem: Batas SKS tidak valid (harus antara 2 - 24 SKS)!");
        }
        this.sisaSks = sks;
    }

    // Soal 3
    public void ambilMataKuliah(String namaMatkul, int bebanSks) {
        if (bebanSks > sisaSks) {
            throw new SksTidakCukupException("SKS tidak cukup untuk mengambil mata kuliah " + namaMatkul);
        }
        sisaSks -= bebanSks;
        System.out.println("Berhasil mengambil " + namaMatkul + ". Sisa SKS: " + sisaSks);
    }
}

class SistemAkademik {
    // Soal 4
    public void gabungKelas(String kodeKelas, int kuotaTersedia) throws KelasPenuhException {
        if (kuotaTersedia <= 0) {
            throw new KelasPenuhException("Gagal bergabung ke kelas " + kodeKelas + ": Kapasitas kelas penuh!");
        }
        System.out.println("Berhasil bergabung ke kelas " + kodeKelas);
    }

    // Soal 5
    public void cetakDokumenKrs(String namaFile) throws FileNotFoundException {
        if (!namaFile.equals("krs_valid.txt")) {
            throw new FileNotFoundException("File KRS tidak ditemukan!");
        }
        System.out.println("Mencetak dokumen KRS...");
    }
}

public class App {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        
        // Soal 1
        int[] kuotaMatkul = new int[3];
        
        // Soal 1
        for (int i = 0; i < 4; i++) {
            try {
                System.out.print("Masukkan kuota untuk mata kuliah ke-" + (i + 1) + ": ");
                kuotaMatkul[i] = scanner.nextInt();
            } catch (InputMismatchException e) {
                // Soal 1
                System.out.println("Kesalahan: Input harus berupa angka!");
                scanner.next(); 
            } catch (ArrayIndexOutOfBoundsException e) {
                // Soal 1
                System.out.println("Kesalahan: Kapasitas array kuota mata kuliah sudah penuh!");
            }
        }
        
        Mahasiswa mhs = new Mahasiswa();
        try {
            // Soal 2
            mhs.setSksMaksimal(20);
            // Soal 3
            mhs.ambilMataKuliah("Pemrograman Berorientasi Objek", 24);
        } catch (IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage());
        } catch (SksTidakCukupException e) {
            System.out.println("Error: " + e.getMessage());
        }
        
        SistemAkademik sistem = new SistemAkademik();
        try {
            // Soal 4
            sistem.gabungKelas("CS101", 0);
        } catch (KelasPenuhException e) {
            System.out.println("Error: " + e.getMessage());
        }
        
        try {
            // Soal 5
            sistem.cetakDokumenKrs("krs_salah.txt");
        } catch (FileNotFoundException e) {
            System.out.println("Error: " + e.getMessage());
        } finally {
            // Soal 5
            if (scanner != null) {
                scanner.close();
            }
            System.out.println("Sesi Sistem Rencana Studi telah ditutup. Koneksi database diputuskan.");
        }
    }
}
