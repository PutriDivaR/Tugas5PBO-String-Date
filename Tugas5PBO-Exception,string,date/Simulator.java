import javax.swing.*;
import java.awt.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Simulator {
    public static void main(String[] args) {
        String username, password, captcha, namaKasir = "Putri Diva Riyanti";  //String untuk isi data login

        // bagian LOGIN
        while (true) {
            try {
                JPanel loginPanel = new JPanel(new GridLayout(3, 2, 5, 5));
                JTextField usernameField = new JTextField();
                JPasswordField passwordField = new JPasswordField();
                JTextField captchaField = new JTextField();

                String generatedCaptcha = generateCaptcha();

                loginPanel.add(new JLabel("Username:"));
                loginPanel.add(usernameField);
                loginPanel.add(new JLabel("Password:"));
                loginPanel.add(passwordField);
                loginPanel.add(new JLabel("Captcha (" + generatedCaptcha + "):"));
                loginPanel.add(captchaField);

                int loginResult = JOptionPane.showConfirmDialog(
                        null,
                        loginPanel,
                        "Log in",
                        JOptionPane.OK_CANCEL_OPTION,
                        JOptionPane.PLAIN_MESSAGE
                );

                if (loginResult != JOptionPane.OK_OPTION) {
                    JOptionPane.showMessageDialog(null, "Login dibatalkan!", "Info", JOptionPane.INFORMATION_MESSAGE);
                    return;
                }

                username = usernameField.getText().trim();
                password = new String(passwordField.getPassword()).trim();
                captcha = captchaField.getText().trim();

                if (username.isEmpty() || password.isEmpty()) {
                    throw new Exception("Username dan Password tidak boleh kosong!");
                }

                if (!captcha.equals(generatedCaptcha)) {
                    throw new Exception("Captcha tidak sesuai!");
                }

                JOptionPane.showMessageDialog(null, "Login berhasil!", "Info", JOptionPane.INFORMATION_MESSAGE);
                break;
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, e.getMessage(), "Login Gagal", JOptionPane.ERROR_MESSAGE);
            }
        }

        // bagian INPUT DATA
        while (true) {
            try {
                JPanel panel = new JPanel(new GridLayout(5, 2, 5, 5));
                JTextField noFakturField = new JTextField();
                JTextField kodeBarangField = new JTextField();
                JTextField namaBarangField = new JTextField();
                JTextField hargaBarangField = new JTextField();
                JTextField jumlahBeliField = new JTextField();

                panel.add(new JLabel("No Faktur:"));
                panel.add(noFakturField);
                panel.add(new JLabel("Kode Barang:"));
                panel.add(kodeBarangField);
                panel.add(new JLabel("Nama Barang:"));
                panel.add(namaBarangField);
                panel.add(new JLabel("Harga Barang:"));
                panel.add(hargaBarangField);
                panel.add(new JLabel("Jumlah Beli:"));
                panel.add(jumlahBeliField);

                int inputResult = JOptionPane.showConfirmDialog(
                        null,
                        panel,
                        "Input Data Transaksi",
                        JOptionPane.OK_CANCEL_OPTION,
                        JOptionPane.PLAIN_MESSAGE
                );

                if (inputResult != JOptionPane.OK_OPTION) {
                    JOptionPane.showMessageDialog(null, "Input dibatalkan!", "Info", JOptionPane.INFORMATION_MESSAGE);
                    return;
                }

                String noFaktur = noFakturField.getText().trim();
                String kodeBarang = kodeBarangField.getText().trim();
                String namaBarang = namaBarangField.getText().trim();
                double hargaBarang = Double.parseDouble(hargaBarangField.getText().trim());
                int jumlahBeli = Integer.parseInt(jumlahBeliField.getText().trim());

                if (noFaktur.isEmpty() || kodeBarang.isEmpty() || namaBarang.isEmpty()) {
                    throw new Exception("No Faktur, Kode Barang, dan Nama Barang tidak boleh kosong!");
                }
                if (hargaBarang < 0) {
                    throw new Exception("Harga Barang tidak boleh negatif!");
                }
                if (jumlahBeli < 0) {
                    throw new Exception("Jumlah Beli tidak boleh negatif!");
                }

                // Bagian Tampilan FAKTUR
                Transaksi transaksi = new Transaksi(noFaktur, kodeBarang, namaBarang, hargaBarang, jumlahBeli);

                //Menambahkan Date dan Time terkini
                LocalDateTime now = LocalDateTime.now();   //Date dan Time terkini
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss"); //format date dan time
                String formattedDate = now.format(formatter); //string untuk menampilkan date dan time

                JOptionPane.showMessageDialog(null,
                            "Selamat Datang di Supermarket DivaRy\n" +
                                "Tanggal dan Waktu : " + formattedDate + "\n" +
                                "+----------------------------------------------------+\n" +
                                "No. Faktur      : " + transaksi.getNoFaktur() + "\n" +
                                "Kode Barang     : " + transaksi.getKodeBarang() + "\n" +
                                "Nama Barang     : " + transaksi.getNamaBarang() + "\n" +
                                "Harga Barang    : " + transaksi.getHargaBarang() + "\n" +
                                "Jumlah Beli     : " + transaksi.getJumlahBeli() + "\n" +
                                "TOTAL           : " + transaksi.calculateTotal() + "\n" +
                                "+----------------------------------------------------+\n" +
                                "Kasir           : " + namaKasir + "\n" +
                                "+----------------------------------------------------+",
                        "Faktur Transaksi", JOptionPane.INFORMATION_MESSAGE);
                break;
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(null, "Harga Barang dan Jumlah Beli harus berupa angka!", "Exception", JOptionPane.ERROR_MESSAGE);
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, e.getMessage(), "Exception", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    // Method captcha
    private static String generateCaptcha() {
        int captcha = (int) (Math.random() * 9000) + 1000; // Random 4 digit angka
        return String.valueOf(captcha);
    }
}
