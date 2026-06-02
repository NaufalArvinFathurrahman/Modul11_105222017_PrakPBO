class SaldoTidakMencukupiException extends Exception {
    private double kekuranganSaldo;

    public SaldoTidakMencukupiException(String message, double kekuranganSaldo) {
        super(message);
        this.kekuranganSaldo = kekuranganSaldo;
    }

    public double getKekuranganSaldo() {
        return kekuranganSaldo;
    }
}

class BatasTransferHarianException extends Exception {
    public BatasTransferHarianException(String message) {
        super(message);
    }
}

class AkunBank {
    private String nomorRekening;
    private double saldo;
    private double totalTransferHariIni;

    public AkunBank(String nomorRekening, double saldoAwal) {
        this.nomorRekening = nomorRekening;
        this.saldo = saldoAwal;
        this.totalTransferHariIni = 0;
    }

    public void tarikTunai(double nominal) throws SaldoTidakMencukupiException {
        if (nominal > saldo) {
            throw new SaldoTidakMencukupiException("Penarikan gagal: Saldo tidak mencukupi.", nominal - saldo);
        }
        saldo -= nominal;
        System.out.println("Tarik tunai berhasil. Saldo saat ini: Rp" + saldo);
    }

    public void transfer(AkunBank tujuan, double nominal) throws SaldoTidakMencukupiException, BatasTransferHarianException {
        if (totalTransferHariIni + nominal > 10000000) {
            throw new BatasTransferHarianException("Transfer gagal: Melebihi batas harian Rp 10.000.000.");
        }
        if (nominal > saldo) {
            throw new SaldoTidakMencukupiException("Transfer gagal: Saldo tidak mencukupi.", nominal - saldo);
        }
        saldo -= nominal;
        totalTransferHariIni += nominal;
        tujuan.saldo += nominal;
        System.out.println("Transfer berhasil. Saldo saat ini: Rp" + saldo);
    }
}

public class App {
    public static void main(String[] args) {
        AkunBank akunSaya = new AkunBank("12345", 5000000);
        AkunBank akunTujuan = new AkunBank("67890", 0);

        try {
            akunSaya.tarikTunai(3000000);
            akunSaya.tarikTunai(3000000);
        } catch (SaldoTidakMencukupiException e) {
            System.out.println(e.getMessage());
            System.out.println("Kekurangan nominal: Rp" + e.getKekuranganSaldo());
        } 

        try {
            AkunBank akunKaya = new AkunBank("11111", 50000000);
            akunKaya.transfer(akunTujuan, 15000000);
        } catch (SaldoTidakMencukupiException e) {
            System.out.println(e.getMessage());
            System.out.println("Kekurangan nominal: Rp" + e.getKekuranganSaldo());
        } catch (BatasTransferHarianException e) {
            System.out.println(e.getMessage());
        } finally {
            System.out.println("Sesi transaksi ATM Anda telah diakhiri. Kartu dikeluarkan otomatis.");
        }
    }
}
