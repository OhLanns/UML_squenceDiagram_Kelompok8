import java.util.ArrayList;
import java.util.Scanner;

public class Pemesanan {
    private ArrayList<PaketFoto> daftarPaket;
    private ArrayList<String> riwayatPemesanan;

    public Pemesanan(ArrayList<PaketFoto> daftarPaket, ArrayList<String> riwayatPemesanan) {
        this.daftarPaket = daftarPaket;
        this.riwayatPemesanan = riwayatPemesanan;
    }

    // === PEMBELIAN LANGSUNG ===
    public void mulaiPembelian(Scanner scanner, User pembeli) {
        System.out.println("\n=== DAFTAR PAKET FOTO ===");
        for (PaketFoto p : daftarPaket) {
            System.out.println(p);
        }

        System.out.print("\nMasukkan jumlah paket yang ingin dibeli: ");
        int jumlah = scanner.nextInt();
        scanner.nextLine();

        int total = 0;
        ArrayList<PaketFoto> paketDibeli = new ArrayList<>();

        for (int i = 0; i < jumlah; i++) {
            System.out.print("Masukkan ID Paket ke-" + (i + 1) + ": ");
            String id = scanner.nextLine().toUpperCase();

            PaketFoto paketDipilih = null;
            for (PaketFoto p : daftarPaket) {
                if (p.getId().equalsIgnoreCase(id) && p.getSlot() > 0) {
                    paketDipilih = p;
                    break;
                }
            }

            if (paketDipilih != null) {
                paketDipilih.kurangiSlot();
                paketDibeli.add(paketDipilih);
                total += paketDipilih.getHarga();
                riwayatPemesanan.add(pembeli.getNama() + " membeli " + paketDipilih.getNamaPaket() + " (Rp" + paketDipilih.getHarga() + ")");
                System.out.println("✅ " + paketDipilih.getNamaPaket() + " berhasil ditambahkan ke pesanan.");
            } else {
                System.out.println("❌ Paket tidak ditemukan atau slot habis!");
                i--;
            }
        }

        System.out.println("\n=== RINCIAN PEMBELIAN ===");
        for (PaketFoto p : paketDibeli) {
            System.out.println(p.getNamaPaket() + " - Rp" + p.getHarga());
        }
        System.out.println("TOTAL BAYAR: Rp" + total);

        System.out.print("Masukkan nominal pembayaran: Rp");
        int bayar = scanner.nextInt();
        int kembali = bayar - total;

        if (kembali < 0) {
            System.out.println("❌ Uang Anda kurang Rp" + Math.abs(kembali));
        } else {
            System.out.println("✅ Pembayaran berhasil! Kembalian: Rp" + kembali);
            System.out.println("Terima kasih sudah menggunakan jasa Foto Studio kami!");
        }
    }
}
