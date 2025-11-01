import java.util.ArrayList;
import java.util.Scanner;

public class AplikasiFotoStudio {
    private ArrayList<Admin> daftarAdmin = new ArrayList<>();
    private ArrayList<User> daftarUser = new ArrayList<>();
    private ArrayList<PaketFoto> daftarPaket = new ArrayList<>();
    private ArrayList<String> riwayatPemesanan = new ArrayList<>();
    private Admin adminAktif;
    private Pemesanan pemesanan; // ✅ ditambahkan

    public void inisialisasiData() {
        daftarAdmin.add(new Admin("A1", "Admin Studio", "admin", "123"));
        daftarUser.add(new User("U1", "Budi", "budi", "111"));
        daftarUser.add(new User("U2", "Siti", "siti", "222"));

        daftarPaket.add(new PaketFoto("P01", "Paket Personal", 100000, 5));
        daftarPaket.add(new PaketFoto("P02", "Paket Couple", 150000, 3));
        daftarPaket.add(new PaketFoto("P03", "Paket Keluarga", 250000, 2));

        pemesanan = new Pemesanan(daftarPaket, riwayatPemesanan); // ✅ inisialisasi Pemesanan
    }

    public boolean loginAdmin(String username, String password) {
        for (Admin admin : daftarAdmin) {
            if (admin.login(username, password)) {
                adminAktif = admin;
                return true;
            }
        }
        return false;
    }

    public User loginUser(String username, String password) {
        for (User u : daftarUser) {
            if (u.login(username, password)) {
                return u;
            }
        }
        return null;
    }

    // ====== MENU ADMIN ======
    public void menuAdmin(Scanner scanner) {
        while (true) {
            System.out.println("\n=== MENU ADMIN FOTO STUDIO ===");
            System.out.println("1. Tambah Paket Foto");
            System.out.println("2. Lihat Daftar Paket");
            System.out.println("3. Lihat Riwayat Pemesanan");
            System.out.println("0. Logout");
            System.out.print("Pilih: ");
            int pilih = scanner.nextInt();
            scanner.nextLine();

            if (pilih == 1) {
                System.out.print("ID Paket: ");
                String id = scanner.nextLine();
                System.out.print("Nama Paket: ");
                String nama = scanner.nextLine();
                System.out.print("Harga: Rp");
                int harga = scanner.nextInt();
                System.out.print("Slot Tersedia: ");
                int slot = scanner.nextInt();
                scanner.nextLine();
                daftarPaket.add(new PaketFoto(id, nama, harga, slot));
                System.out.println("✅ Paket foto baru berhasil ditambahkan!");
            } else if (pilih == 2) {
                tampilkanDaftarPaket();
            } else if (pilih == 3) {
                tampilkanRiwayat();
            } else {
                break;
            }
        }
    }

    public void tampilkanDaftarPaket() {
        System.out.println("\n=== DAFTAR PAKET FOTO ===");
        for (PaketFoto p : daftarPaket) {
            System.out.println(p);
        }
    }

    public void tampilkanRiwayat() {
        System.out.println("\n=== RIWAYAT PEMESANAN ===");
        if (riwayatPemesanan.isEmpty()) System.out.println("Belum ada pemesanan.");
        for (String s : riwayatPemesanan) System.out.println(s);
    }

    // ====== MENU USER ======
    public void menuUser(Scanner scanner, User user) {
        while (true) {
            System.out.println("\n=== MENU USER FOTO STUDIO ===");
            System.out.println("1. Booking Paket (tanpa bayar)");
            System.out.println("2. Beli Paket (langsung bayar)");
            System.out.println("3. Pembatalan");
            System.out.println("4. Lihat Riwayat / Struk Pembelian");
            System.out.println("0. Logout");
            System.out.print("Pilih: ");
            int pilih = scanner.nextInt();
            scanner.nextLine();

            if (pilih == 1) mulaiPemesanan(scanner, user);
            else if (pilih == 2) pemesanan.mulaiPembelian(scanner, user);
            else if (pilih == 3) new Pembatalan(daftarPaket, riwayatPemesanan).batalkan(scanner, user);
            else if (pilih == 4) tampilkanRiwayat();
            else break;
        }
    }

    // PEMESANAN TANPA PEMBAYARAN
    public void mulaiPemesanan(Scanner scanner, User pembeli) {
        tampilkanDaftarPaket();
        System.out.print("\nMasukkan ID Paket yang ingin dipesan: ");
        String id = scanner.nextLine();

        for (PaketFoto p : daftarPaket) {
            if (p.getId().equalsIgnoreCase(id) && p.getSlot() > 0) {
                p.kurangiSlot();
                pembeli.tambahPaket(p);
                riwayatPemesanan.add(pembeli.getNama() + " booking " + p.getNamaPaket());
                System.out.println("✅ Pemesanan berhasil!");
                return;
            }
        }
        System.out.println("❌ Paket tidak ditemukan atau slot habis!");
    }
}
