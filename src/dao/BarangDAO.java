package dao;

import koneksi.Koneksi;
import model.Barang;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BarangDAO {

    public boolean simpan(Barang b) {
        String sql = "INSERT INTO barang VALUES (?, ?, ?, ?)";

        Connection conn = Koneksi.getConnection();

        if (conn == null) return false;

        try (Connection c = conn;
             PreparedStatement ps = c.prepareStatement(sql)) {

            ps.setString(1, b.getKodeBarang());
            ps.setString(2, b.getNamaBarang());
            ps.setString(3, b.getKategori());
            ps.setInt(4, b.getStok());

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return false;
        }
    }

    public boolean ubah(Barang b) {
        String sql = "UPDATE barang SET nama_barang=?, kategori=?, "
                   + "stok=? WHERE kode_barang=?";

        Connection conn = Koneksi.getConnection();

        if (conn == null) return false;

        try (Connection c = conn;
             PreparedStatement ps = c.prepareStatement(sql)) {

            ps.setString(1, b.getNamaBarang());
            ps.setString(2, b.getKategori());
            ps.setInt(3, b.getStok());
            ps.setString(4, b.getKodeBarang());

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return false;
        }
    }

    public boolean hapus(String kode) {
        String sql = "DELETE FROM barang WHERE kode_barang=?";

        Connection conn = Koneksi.getConnection();

        if (conn == null) return false;

        try (Connection c = conn;
             PreparedStatement ps = c.prepareStatement(sql)) {

            ps.setString(1, kode);

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return false;
        }
    }

    public List<Barang> getAll() {
        List<Barang> list = new ArrayList<>();

        String sql = "SELECT * FROM barang";

        Connection conn = Koneksi.getConnection();

        if (conn == null) return list;

        try (Connection c = conn;
             PreparedStatement ps = c.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Barang b = new Barang(
                    rs.getString("kode_barang"),
                    rs.getString("nama_barang"),
                    rs.getString("kategori"),
                    rs.getInt("stok")
                );

                list.add(b);
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return list;
    }
}