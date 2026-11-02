package view;

import dao.BarangDAO;
import model.Barang;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.util.List;

public class FormBarang extends JFrame {

    private JTextField txtKode, txtNama, txtStok;
    private JComboBox<String> cmbKategori;
    private JTable tabel;
    private DefaultTableModel model;

    private BarangDAO dao = new BarangDAO();

    public FormBarang() {

        setTitle("Aplikasi Inventori Barang Laboratorium");
        setSize(750, 550);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(null);

        JLabel judul = new JLabel("DATA BARANG LABORATORIUM");
        judul.setFont(new Font("Arial", Font.BOLD, 20));
        judul.setBounds(190, 15, 400, 30);
        add(judul);

        JLabel lblKode = new JLabel("Kode Barang");
        lblKode.setBounds(40, 70, 120, 25);
        add(lblKode);

        txtKode = new JTextField();
        txtKode.setBounds(160, 70, 200, 25);
        add(txtKode);

        JLabel lblNama = new JLabel("Nama Barang");
        lblNama.setBounds(40, 110, 120, 25);
        add(lblNama);

        txtNama = new JTextField();
        txtNama.setBounds(160, 110, 200, 25);
        add(txtNama);

        JLabel lblKategori = new JLabel("Kategori");
        lblKategori.setBounds(40, 150, 120, 25);
        add(lblKategori);

        cmbKategori = new JComboBox<>(new String[]{
            "Komputer",
            "Jaringan",
            "Alat Ukur"
        });

        cmbKategori.setBounds(160, 150, 200, 25);
        add(cmbKategori);

        JLabel lblStok = new JLabel("Stok");
        lblStok.setBounds(40, 190, 120, 25);
        add(lblStok);

        txtStok = new JTextField();
        txtStok.setBounds(160, 190, 200, 25);
        add(txtStok);

        JButton btnSimpan = new JButton("Simpan");
        btnSimpan.setBounds(400, 70, 110, 30);
        add(btnSimpan);

        JButton btnUbah = new JButton("Ubah");
        btnUbah.setBounds(520, 70, 110, 30);
        add(btnUbah);

        JButton btnHapus = new JButton("Hapus");
        btnHapus.setBounds(400, 110, 110, 30);
        add(btnHapus);

        JButton btnBersihkan = new JButton("Bersihkan");
        btnBersihkan.setBounds(520, 110, 110, 30);
        add(btnBersihkan);

        // TABEL BARANG

        model = new DefaultTableModel(
            new String[]{
                "Kode Barang",
                "Nama Barang",
                "Kategori",
                "Stok"
            }, 0
        );

        tabel = new JTable(model);

        JScrollPane scroll = new JScrollPane(tabel);
        scroll.setBounds(40, 260, 650, 200);
        add(scroll);

        // EVENT SIMPAN

        btnSimpan.addActionListener(e -> simpanData());

        // EVENT UBAH

        btnUbah.addActionListener(e -> ubahData());

        // EVENT HAPUS

        btnHapus.addActionListener(e -> hapusData());

        // EVENT BERSIHKAN

        btnBersihkan.addActionListener(e -> bersihkan());

        // KLIK DUA KALI PADA TABEL

        tabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {

                if (e.getClickCount() == 2) {

                    int baris = tabel.getSelectedRow();

                    if (baris != -1) {
                        txtKode.setText(
                            model.getValueAt(baris, 0).toString()
                        );

                        txtNama.setText(
                            model.getValueAt(baris, 1).toString()
                        );

                        cmbKategori.setSelectedItem(
                            model.getValueAt(baris, 2).toString()
                        );

                        txtStok.setText(
                            model.getValueAt(baris, 3).toString()
                        );
                    }
                }
            }
        });

        tampilData();
    }

    // VALIDASI INPUT

    private boolean validasi() {

        if (txtKode.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this,
                "Kode barang tidak boleh kosong!");
            txtKode.requestFocus();
            return false;
        }

        if (txtNama.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this,
                "Nama barang tidak boleh kosong!");
            txtNama.requestFocus();
            return false;
        }

        if (txtStok.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this,
                "Stok tidak boleh kosong!");
            txtStok.requestFocus();
            return false;
        }

        try {
            int stok = Integer.parseInt(txtStok.getText().trim());

            if (stok < 0) {
                JOptionPane.showMessageDialog(this,
                    "Stok tidak boleh kurang dari 0!");
                return false;
            }

        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this,
                "Stok harus berupa angka!");
            txtStok.requestFocus();
            return false;
        }

        return true;
    }

    // SIMPAN DATA

    private void simpanData() {

        if (!validasi()) return;

        Barang b = new Barang(
            txtKode.getText().trim(),
            txtNama.getText().trim(),
            cmbKategori.getSelectedItem().toString(),
            Integer.parseInt(txtStok.getText().trim())
        );

        if (dao.simpan(b)) {

            JOptionPane.showMessageDialog(this,
                "Data berhasil disimpan!");

            tampilData();
            bersihkan();

        } else {

            JOptionPane.showMessageDialog(this,
                "Data gagal disimpan! Periksa kode barang "
                + "atau koneksi database.");
        }
    }

    // UBAH DATA

    private void ubahData() {

        if (!validasi()) return;

        Barang b = new Barang(
            txtKode.getText().trim(),
            txtNama.getText().trim(),
            cmbKategori.getSelectedItem().toString(),
            Integer.parseInt(txtStok.getText().trim())
        );

        if (dao.ubah(b)) {

            JOptionPane.showMessageDialog(this,
                "Data berhasil diubah!");

            tampilData();
            bersihkan();

        } else {

            JOptionPane.showMessageDialog(this,
                "Data gagal diubah atau kode tidak ditemukan!");
        }
    }

    // HAPUS DATA

    private void hapusData() {

        String kode = txtKode.getText().trim();

        if (kode.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                "Pilih data yang akan dihapus!");
            return;
        }

        int jawab = JOptionPane.showConfirmDialog(
            this,
            "Apakah Anda yakin ingin menghapus data ini?",
            "Konfirmasi Hapus",
            JOptionPane.YES_NO_OPTION
        );

        if (jawab == JOptionPane.YES_OPTION) {

            if (dao.hapus(kode)) {

                JOptionPane.showMessageDialog(this,
                    "Data berhasil dihapus!");

                tampilData();
                bersihkan();

            } else {

                JOptionPane.showMessageDialog(this,
                    "Data gagal dihapus atau tidak ditemukan!");
            }
        }
    }

    // TAMPILKAN DATA KE JTable

    private void tampilData() {

        model.setRowCount(0);

        List<Barang> data = dao.getAll();

        for (Barang b : data) {

            model.addRow(new Object[]{
                b.getKodeBarang(),
                b.getNamaBarang(),
                b.getKategori(),
                b.getStok()
            });
        }
    }

    // BERSIHKAN FORM

    private void bersihkan() {

        txtKode.setText("");
        txtNama.setText("");
        txtStok.setText("");
        cmbKategori.setSelectedIndex(0);

        txtKode.requestFocus();
        tabel.clearSelection();
    }

    // MAIN PROGRAM

    public static void main(String[] args) {

        SwingUtilities.invokeLater(() -> {
            new FormBarang().setVisible(true);
        });
    }
}