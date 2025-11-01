public class PaketFoto {
    private String id;
    private String namaPaket;
    private int harga;
    private int slot;

    public PaketFoto(String id, String namaPaket, int harga, int slot) {
        this.id = id;
        this.namaPaket = namaPaket;
        this.harga = harga;
        this.slot = slot;
    }

    public String getId() { return id; }
    public String getNamaPaket() { return namaPaket; }
    public int getHarga() { return harga; }
    public int getSlot() { return slot; }

    public void kurangiSlot() {
        if (slot > 0) slot--;
    }

    @Override
    public String toString() {
        return id + " | " + namaPaket + " | Rp" + harga + " | Slot Tersedia: " + slot;
    }
}
