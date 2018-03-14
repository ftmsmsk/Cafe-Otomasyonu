
import Enums.GarsonEnum;
import Enums.GorevEnum;
import Enums.KategoriEnum;
import Enums.KullaniciEnum;
import Enums.MasaEnum;
import Enums.SiparisEnum;

import Properties.GarsonPro;
import Properties.GorevPro;
import Properties.KategoriPro;
import Properties.KullaniciPro;
import Properties.MasaPro;
import com.sun.org.apache.xalan.internal.xsltc.compiler.util.StringStack;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.HeadlessException;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

public class Mudur extends javax.swing.JFrame {

    SimpleDateFormat dateFormat = new SimpleDateFormat("YYYY-MM-dd");
    ArrayList<KullaniciPro> grp = new ArrayList<>();
    ArrayList<KategoriPro> ktp = new ArrayList<>();
    ArrayList<MasaPro> msp = new ArrayList<>();
    DB db = new DB("cafehouse", "root", "");
    MyValidator my = new MyValidator();
    ArrayList<GorevPro> gvp = new ArrayList<>();

    public Mudur() {
        initComponents();
        mDataGetir();
        kategoriDoldur();
        yemekListesiTabloDoldur();
        siparisTabloDoldur();
        kampanyaliMenuTabloDoldur();
        gorevDoldur();
        adisyonTakip();
        masaDoldur();
        personelGetir();
    }

    public void mDataGetir() {
        gvp.clear();
        try {
            String q = "CALL personelData()";
            ResultSet rs = db.baglan().executeQuery(q);
            DefaultTableModel dtm = new DefaultTableModel();
            dtm.addColumn("ID");
            dtm.addColumn("ADI");
            dtm.addColumn("SOYADI");
            while (rs.next()) {
                dtm.addRow(new String[]{rs.getString("sifreid"), rs.getString("kullaniciAdi"), rs.getString("kullaniciSoyadi")});
                GorevPro gr = new GorevPro();
        }
            tblMPersonel.setModel(dtm);
        } catch (SQLException e) {
            System.err.println("müdür personel getirme hatasi:" + e);
        }
    }

    public void personelAyrilan() {
        grp.clear();
        try {
            String q = "CALL personelAyrilan()";
            ResultSet rs = db.baglan().executeQuery(q);
            DefaultTableModel dtm = new DefaultTableModel();
            dtm.addColumn("ID");
            dtm.addColumn("ADI");
            dtm.addColumn("SOYADI");
            while (rs.next()) {
                dtm.addRow(new String[]{rs.getString("sifreid"), rs.getString("kullaniciAdi"), rs.getString("kullaniciSoyadi")});

            }
            tblMPersonel.setModel(dtm);
        } catch (SQLException e) {
            System.err.println("ayrılan personel getirme hatasi:" + e);
        }
    }

    // kategori combo doldurmak iÃ§in
    public void kategoriDoldur() {
        ktp.clear();
        try {
            String q = "CALL kategoriGetir()";
            ResultSet rs = db.baglan().executeQuery(q);
            while (rs.next()) {
                KategoriPro kr = new KategoriPro();
                kr.setKatid(rs.getInt("" + KategoriEnum.katid));
                kr.setKadi(rs.getString("" + KategoriEnum.kadi));
                ktp.add(kr);
            }
            kateModelDoldur();
        } catch (SQLException e) {
            System.err.println("kategori getirme hatasi : " + e);
        }
    }

    //combobox doldurma
    public void kateModelDoldur() {
        DefaultComboBoxModel<String> dkm = new DefaultComboBoxModel<>();

        for (KategoriPro item : ktp) {
            dkm.addElement(item.getKadi());
        }
        cmbKategoriler.setModel(dkm);

    }

    public void yemekEkle() {

        String yemekadi = txtYemekAdi.getText().trim();
        int yemekkategori = ktp.get(cmbKategoriler.getSelectedIndex()).getKatid();
        float yemekmaliyet = Float.valueOf(txtMaliyet.getText().trim());
        float yemeksatisfiyati = Float.valueOf(txtSatisFiyati.getText().trim());
        String aciklama = txtAciklama.getText().trim();

        try {
            String q = "call yemekEkle(?,?,?,?,?,?)";
            PreparedStatement pr = db.preBaglan(q);
            pr.setString(1, yemekadi);
            pr.setInt(2, yemekkategori);
            pr.setFloat(3, yemekmaliyet);
            pr.setFloat(4, yemeksatisfiyati);
            pr.setString(5, aciklama);
            pr.setString(6, path);

            int yaz = pr.executeUpdate();
            if (yaz > 0) {
                yemekListesiTabloDoldur();
            } else {
                System.out.println("Yazma hatasÄ±!");
            }
        } catch (Exception e) {
            System.err.println("Yemek ekleme hatasÄ± : " + e);
        }
    }

    public void yemekListesiTabloDoldur() {
        try {
            String q = "call yemekTabloDoldur()";
            ResultSet rs = db.baglan().executeQuery(q);

            DefaultTableModel dtm = new DefaultTableModel();
            dtm.addColumn("ID");
            dtm.addColumn("Yemek AdÄ±");
            dtm.addColumn("Yemek Kategori");
            dtm.addColumn("Y.Maliyet");
            dtm.addColumn("Y.SatÄ±ÅŸ FiyatÄ±");
            dtm.addColumn("AcÄ±klama");

            while (rs.next()) {
                dtm.addRow(new String[]{rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5), rs.getString(6)});
            }
            tblYemekListesi.setModel(dtm);
        } catch (Exception e) {
            System.err.println("Data getirme hatasÄ± : " + e);
        }
    }

    public void kampanyaliMenu() {
        String menuadi = txtMenuAdi.getText().trim();
        String menubaslangic = dateFormat.format(jDateChooser3.getDate()).toString();
        String menubitis = dateFormat.format(jDateChooser4.getDate()).toString();
        System.out.println(menubaslangic);
        Float menufiyat = Float.valueOf(txtMenuFiyat.getText().trim());
        Float menumaliyet = Float.valueOf(txtMenuMaliyet.getText().trim());
        int madet = 0;
        try {
            String q = "call kampanyaliMenuOlustur(?,?,?,?,?,?,?)";
            PreparedStatement pr = db.preBaglan(q);

            pr.setString(1, menuadi);
            pr.setString(2, menubaslangic);
            pr.setString(3, menubitis);
            pr.setFloat(4, menufiyat);
            pr.setFloat(5, menumaliyet);
            pr.setInt(6, madet);
            pr.setString(7, path);
            int yaz = pr.executeUpdate();
            if (yaz > 0) {
                kampanyaliMenuTabloDoldur();
            } else {
                System.out.println("Yazma hatasÄ±!");
            }
        } catch (Exception e) {
            System.err.println("KampanyalÄ± menu oluÅŸturma hatasÄ± : " + e);
        }
    }

    public void kampanyaliMenuTabloDoldur() {
        try {
            String q = "call kampanyaliMenuTabloDoldur()";
            ResultSet rs = db.baglan().executeQuery(q);

            DefaultTableModel dtm = new DefaultTableModel();
            dtm.addColumn("ID");
            dtm.addColumn("Yemek Adi");
            dtm.addColumn("Menu Baslangic");
            dtm.addColumn("Menu Bitis");
            dtm.addColumn("Menu Fiyat");
            dtm.addColumn("Menu Maliyet");

            while (rs.next()) {
                dtm.addRow(new String[]{rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5), rs.getString(6)});
            }
            tblKampanyalar.setModel(dtm);
        } catch (Exception e) {
            System.err.println("Data getirme hatasÄ± : " + e);
        }
    }

    public void siparisTabloDoldur() {
        try {
            String q = "Call siparisGetir()";
            ResultSet rs = db.baglan().executeQuery(q);
            DefaultTableModel dtm = new DefaultTableModel();
            dtm.addColumn("ID");
            dtm.addColumn("MASA ADI");
            dtm.addColumn("YEMEK ADI");
            dtm.addColumn("KAMPANYA");
            dtm.addColumn("M.FİYAT");
            dtm.addColumn("Y.FİYAT");
            while (rs.next()) {
                dtm.addRow(new String[]{rs.getString("sipid"), rs.getString("madi"),
                    rs.getString("yadi"), rs.getString("menuadi"),
                    rs.getString("menufiyat"), rs.getString("satisFiyati")});
            }
            tblMudurSiparisler.setModel(dtm);
        } catch (Exception e) {
        }
    }

    public void gorevDoldur() {
        gvp.clear();
        try {
            String q = "CALL gorevGetir()";
            ResultSet rs = db.baglan().executeQuery(q);
            while (rs.next()) {
                GorevPro kr = new GorevPro();
                kr.setGorevid(rs.getInt("" + GorevEnum.gorevid));
                kr.setGorevadi(rs.getString("" + GorevEnum.gorevadi));
                gvp.add(kr);
            }
            gorevModelDoldur();
        } catch (SQLException e) {
            System.err.println("gorev getirme hatasi : " + e);
        }
    }

    //combobox doldurma
    public void gorevModelDoldur() {
        DefaultComboBoxModel<String> dkm = new DefaultComboBoxModel<>();

        for (GorevPro item : gvp) {
            dkm.addElement(item.getGorevadi());
        }
        cmbGorev.setModel(dkm);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        mdrAnaEkran = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        jPanel14 = new javax.swing.JPanel();
        txtMasaEkle = new javax.swing.JButton();
        cmbMasaListele = new javax.swing.JComboBox<>();
        txtMasEkle = new javax.swing.JTextField();
        jButton16 = new javax.swing.JButton();
        jPanel4 = new javax.swing.JPanel();
        jPanel11 = new javax.swing.JPanel();
        jLabel11 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        jLabel18 = new javax.swing.JLabel();
        jLabel19 = new javax.swing.JLabel();
        jLabel20 = new javax.swing.JLabel();
        txtYemekAdi = new javax.swing.JTextField();
        cmbKategoriler = new javax.swing.JComboBox<>();
        txtMaliyet = new javax.swing.JTextField();
        txtSatisFiyati = new javax.swing.JTextField();
        txtAciklama = new javax.swing.JTextField();
        lblResim = new javax.swing.JLabel();
        btnResimEkle = new javax.swing.JButton();
        btnSil = new javax.swing.JButton();
        btnYemekEkle = new javax.swing.JButton();
        jPanel12 = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        tblYemekListesi = new javax.swing.JTable();
        jButton7 = new javax.swing.JButton();
        jButton15 = new javax.swing.JButton();
        jPanel5 = new javax.swing.JPanel();
        jPanel17 = new javax.swing.JPanel();
        jLabel28 = new javax.swing.JLabel();
        jLabel29 = new javax.swing.JLabel();
        jLabel30 = new javax.swing.JLabel();
        txtKullaniciAdi = new javax.swing.JTextField();
        jButton13 = new javax.swing.JButton();
        txtKullaniciSifre = new javax.swing.JPasswordField();
        txtYeniSifre = new javax.swing.JPasswordField();
        jButton17 = new javax.swing.JButton();
        jPanel6 = new javax.swing.JPanel();
        jPanel18 = new javax.swing.JPanel();
        jLabel31 = new javax.swing.JLabel();
        jLabel32 = new javax.swing.JLabel();
        btnGoster = new javax.swing.JButton();
        jScrollPane4 = new javax.swing.JScrollPane();
        tblRaporlar = new javax.swing.JTable();
        jLabel44 = new javax.swing.JLabel();
        jLabel45 = new javax.swing.JLabel();
        jLabel46 = new javax.swing.JLabel();
        jLabel47 = new javax.swing.JLabel();
        jLabel48 = new javax.swing.JLabel();
        jLabel49 = new javax.swing.JLabel();
        jDateChooser1 = new com.toedter.calendar.JDateChooser();
        jDateChooser2 = new com.toedter.calendar.JDateChooser();
        jButton14 = new javax.swing.JButton();
        jPanel7 = new javax.swing.JPanel();
        jPanel19 = new javax.swing.JPanel();
        jScrollPane5 = new javax.swing.JScrollPane();
        tblMudurSiparisler = new javax.swing.JTable();
        btnYenile = new javax.swing.JButton();
        jButton18 = new javax.swing.JButton();
        jPanel8 = new javax.swing.JPanel();
        jPanel22 = new javax.swing.JPanel();
        jScrollPane6 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jButton23 = new javax.swing.JButton();
        jPanel9 = new javax.swing.JPanel();
        jPanel20 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblMPersonel = new javax.swing.JTable();
        jButton6 = new javax.swing.JButton();
        txtMAra = new javax.swing.JTextField();
        jButton5 = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();
        jPanel21 = new javax.swing.JPanel();
        txtPAd = new javax.swing.JTextField();
        jLabel13 = new javax.swing.JLabel();
        txtPSoyad = new javax.swing.JTextField();
        jLabel14 = new javax.swing.JLabel();
        txtTC = new javax.swing.JTextField();
        jLabel15 = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jLabel12 = new javax.swing.JLabel();
        jLabel33 = new javax.swing.JLabel();
        jLabel34 = new javax.swing.JLabel();
        jLabel35 = new javax.swing.JLabel();
        txtPTel = new javax.swing.JTextField();
        txtPMail = new javax.swing.JTextField();
        txtPAdres = new javax.swing.JTextField();
        cmbGorev = new javax.swing.JComboBox<>();
        jLabel36 = new javax.swing.JLabel();
        jLabel37 = new javax.swing.JLabel();
        jButton19 = new javax.swing.JButton();
        jButton21 = new javax.swing.JButton();
        jButton22 = new javax.swing.JButton();
        jButton20 = new javax.swing.JButton();
        jPanel10 = new javax.swing.JPanel();
        jPanel15 = new javax.swing.JPanel();
        jLabel21 = new javax.swing.JLabel();
        jLabel22 = new javax.swing.JLabel();
        jLabel23 = new javax.swing.JLabel();
        jLabel24 = new javax.swing.JLabel();
        jLabel25 = new javax.swing.JLabel();
        jLabel26 = new javax.swing.JLabel();
        txtMenuAdi = new javax.swing.JTextField();
        txtMenuFiyat = new javax.swing.JTextField();
        txtMenuMaliyet = new javax.swing.JTextField();
        jLabel27 = new javax.swing.JLabel();
        jButton12 = new javax.swing.JButton();
        btnMenuEkle = new javax.swing.JButton();
        jDateChooser3 = new com.toedter.calendar.JDateChooser();
        jDateChooser4 = new com.toedter.calendar.JDateChooser();
        jButton2 = new javax.swing.JButton();
        jPanel16 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tblKampanyalar = new javax.swing.JTable();
        jButton11 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(0, 255, 204));
        jPanel1.setLayout(new java.awt.CardLayout());

        mdrAnaEkran.setBackground(new java.awt.Color(204, 204, 255));
        mdrAnaEkran.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                mdrAnaEkranMouseClicked(evt);
            }
        });

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/icons8_Restaurant_Table_80px.png"))); // NOI18N
        jLabel1.setText("MASALAR");
        jLabel1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel1MouseClicked(evt);
            }
        });

        jLabel3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/icons8_Food_Service_80px.png"))); // NOI18N
        jLabel3.setText("PERSONEL");
        jLabel3.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel3MouseClicked(evt);
            }
        });

        jLabel4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/icons8_Restaurant_Menu_80px.png"))); // NOI18N
        jLabel4.setText("MENÜ");
        jLabel4.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel4MouseClicked(evt);
            }
        });

        jLabel5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/icons8_Accounting_80px.png"))); // NOI18N
        jLabel5.setText("KASA İŞLEMLERİ");
        jLabel5.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel5MouseClicked(evt);
            }
        });

        jLabel6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/icons8_Gears_80px_1.png"))); // NOI18N
        jLabel6.setText("AYARLAR");
        jLabel6.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel6MouseClicked(evt);
            }
        });

        jLabel7.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/icons8_Kitchenwares_80px_1.png"))); // NOI18N
        jLabel7.setText("MUTFAK");
        jLabel7.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel7MouseClicked(evt);
            }
        });

        jLabel8.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/icons8_Discount_80px.png"))); // NOI18N
        jLabel8.setText("KAMPANYALAR");
        jLabel8.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel8MouseClicked(evt);
            }
        });

        jLabel9.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/icons8_Cancel_48px.png"))); // NOI18N
        jLabel9.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel9MouseClicked(evt);
            }
        });

        jLabel10.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/icons8_Increase_80px.png"))); // NOI18N
        jLabel10.setText("RAPORLAR");
        jLabel10.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel10MouseClicked(evt);
            }
        });

        javax.swing.GroupLayout mdrAnaEkranLayout = new javax.swing.GroupLayout(mdrAnaEkran);
        mdrAnaEkran.setLayout(mdrAnaEkranLayout);
        mdrAnaEkranLayout.setHorizontalGroup(
            mdrAnaEkranLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(mdrAnaEkranLayout.createSequentialGroup()
                .addGroup(mdrAnaEkranLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(mdrAnaEkranLayout.createSequentialGroup()
                        .addGap(322, 322, 322)
                        .addComponent(jLabel10)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, mdrAnaEkranLayout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(jLabel9)))
                .addContainerGap())
            .addGroup(mdrAnaEkranLayout.createSequentialGroup()
                .addGroup(mdrAnaEkranLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(mdrAnaEkranLayout.createSequentialGroup()
                        .addGap(163, 163, 163)
                        .addComponent(jLabel1))
                    .addGroup(mdrAnaEkranLayout.createSequentialGroup()
                        .addGap(78, 78, 78)
                        .addComponent(jLabel4))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, mdrAnaEkranLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel6)))
                .addGroup(mdrAnaEkranLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, mdrAnaEkranLayout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel8)
                        .addGap(120, 120, 120))
                    .addGroup(mdrAnaEkranLayout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addComponent(jLabel3)
                        .addGap(46, 46, 46)
                        .addGroup(mdrAnaEkranLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel7)
                            .addComponent(jLabel5))
                        .addContainerGap(253, Short.MAX_VALUE))))
        );
        mdrAnaEkranLayout.setVerticalGroup(
            mdrAnaEkranLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(mdrAnaEkranLayout.createSequentialGroup()
                .addGroup(mdrAnaEkranLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(mdrAnaEkranLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel9)
                        .addGap(88, 88, 88)
                        .addGroup(mdrAnaEkranLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel1)
                            .addComponent(jLabel5)))
                    .addGroup(mdrAnaEkranLayout.createSequentialGroup()
                        .addGap(76, 76, 76)
                        .addComponent(jLabel3)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(mdrAnaEkranLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(jLabel8))
                .addGap(16, 16, 16)
                .addGroup(mdrAnaEkranLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(jLabel7))
                .addGap(18, 18, 18)
                .addComponent(jLabel10)
                .addContainerGap(206, Short.MAX_VALUE))
        );

        jPanel1.add(mdrAnaEkran, "card2");

        jPanel2.setBackground(new java.awt.Color(204, 204, 255));

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 910, Short.MAX_VALUE)
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 713, Short.MAX_VALUE)
        );

        jPanel1.add(jPanel2, "card4");

        jPanel3.setBackground(new java.awt.Color(204, 204, 255));

        jPanel14.setBackground(new java.awt.Color(204, 204, 255));
        jPanel14.setBorder(javax.swing.BorderFactory.createTitledBorder("MASA İŞLEMLERİ"));

        txtMasaEkle.setBackground(new java.awt.Color(51, 51, 255));
        txtMasaEkle.setText("EKLE");
        txtMasaEkle.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtMasaEkleActionPerformed(evt);
            }
        });

        cmbMasaListele.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        cmbMasaListele.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmbMasaListeleActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel14Layout = new javax.swing.GroupLayout(jPanel14);
        jPanel14.setLayout(jPanel14Layout);
        jPanel14Layout.setHorizontalGroup(
            jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel14Layout.createSequentialGroup()
                .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel14Layout.createSequentialGroup()
                        .addGap(207, 207, 207)
                        .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtMasEkle, javax.swing.GroupLayout.PREFERRED_SIZE, 179, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(cmbMasaListele, javax.swing.GroupLayout.PREFERRED_SIZE, 179, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel14Layout.createSequentialGroup()
                        .addGap(235, 235, 235)
                        .addComponent(txtMasaEkle, javax.swing.GroupLayout.PREFERRED_SIZE, 121, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(273, Short.MAX_VALUE))
        );
        jPanel14Layout.setVerticalGroup(
            jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel14Layout.createSequentialGroup()
                .addGap(68, 68, 68)
                .addComponent(cmbMasaListele, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(26, 26, 26)
                .addComponent(txtMasEkle, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(61, 61, 61)
                .addComponent(txtMasaEkle, javax.swing.GroupLayout.PREFERRED_SIZE, 107, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(238, Short.MAX_VALUE))
        );

        jButton16.setText("Ana Ekran");
        jButton16.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jButton16MouseClicked(evt);
            }
        });
        jButton16.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton16ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(297, 297, 297)
                        .addComponent(jButton16))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(109, 109, 109)
                        .addComponent(jPanel14, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(130, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addGap(26, 26, 26)
                .addComponent(jPanel14, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jButton16)
                .addContainerGap(83, Short.MAX_VALUE))
        );

        jPanel1.add(jPanel3, "card3");

        jPanel4.setBackground(new java.awt.Color(204, 204, 255));

        jPanel11.setBackground(new java.awt.Color(204, 204, 255));
        jPanel11.setBorder(javax.swing.BorderFactory.createTitledBorder("MENÜ OLUŞTURMA"));

        jLabel11.setText("Yemek Adı:");

        jLabel16.setText("Yemek Kategorileri");

        jLabel17.setText("Maliyet:");

        jLabel18.setText("Satiş fiyati");

        jLabel19.setText("Açıklama");

        jLabel20.setText("Yemek Resmi");

        cmbKategoriler.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        lblResim.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/icons8_Customer_96px.png"))); // NOI18N

        btnResimEkle.setText("Resim Ekle");
        btnResimEkle.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnResimEkleActionPerformed(evt);
            }
        });

        btnSil.setText("Resim Sil");

        btnYemekEkle.setText("YEMEK EKLE");
        btnYemekEkle.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnYemekEkleActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel11Layout = new javax.swing.GroupLayout(jPanel11);
        jPanel11.setLayout(jPanel11Layout);
        jPanel11Layout.setHorizontalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel11Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel11Layout.createSequentialGroup()
                        .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel16)
                            .addComponent(jLabel11))
                        .addGap(53, 53, 53)
                        .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(txtYemekAdi)
                            .addComponent(cmbKategoriler, 0, 186, Short.MAX_VALUE)))
                    .addGroup(jPanel11Layout.createSequentialGroup()
                        .addGap(26, 26, 26)
                        .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel18)
                            .addComponent(jLabel17)
                            .addComponent(jLabel19)
                            .addComponent(jLabel20))
                        .addGap(53, 53, 53)
                        .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(txtMaliyet, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(txtSatisFiyati, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(txtAciklama, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 186, Short.MAX_VALUE)
                            .addComponent(lblResim))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel11Layout.createSequentialGroup()
                .addGap(0, 112, Short.MAX_VALUE)
                .addComponent(btnResimEkle)
                .addGap(18, 18, 18)
                .addComponent(btnSil)
                .addGap(108, 108, 108))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel11Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnYemekEkle)
                .addGap(143, 143, 143))
        );
        jPanel11Layout.setVerticalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel11Layout.createSequentialGroup()
                .addGap(33, 33, 33)
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtYemekAdi, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel11))
                .addGap(18, 18, 18)
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel16)
                    .addComponent(cmbKategoriler, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel17)
                    .addComponent(txtMaliyet, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel18)
                    .addComponent(txtSatisFiyati, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel19)
                    .addComponent(txtAciklama, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel11Layout.createSequentialGroup()
                        .addGap(121, 121, 121)
                        .addComponent(jLabel20))
                    .addGroup(jPanel11Layout.createSequentialGroup()
                        .addGap(51, 51, 51)
                        .addComponent(lblResim)))
                .addGap(31, 31, 31)
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnSil)
                    .addComponent(btnResimEkle))
                .addGap(38, 38, 38)
                .addComponent(btnYemekEkle)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel12.setBackground(new java.awt.Color(204, 204, 255));
        jPanel12.setBorder(javax.swing.BorderFactory.createTitledBorder("YEMEK LİSTESİ"));

        tblYemekListesi.setBackground(new java.awt.Color(204, 204, 255));
        tblYemekListesi.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane3.setViewportView(tblYemekListesi);

        jButton7.setText("Ana Ekran");

        javax.swing.GroupLayout jPanel12Layout = new javax.swing.GroupLayout(jPanel12);
        jPanel12.setLayout(jPanel12Layout);
        jPanel12Layout.setHorizontalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel12Layout.createSequentialGroup()
                .addGap(0, 14, Short.MAX_VALUE)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel12Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jButton7)
                .addContainerGap())
        );
        jPanel12Layout.setVerticalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel12Layout.createSequentialGroup()
                .addGap(43, 43, 43)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 81, Short.MAX_VALUE)
                .addComponent(jButton7)
                .addContainerGap())
        );

        jButton15.setText("Ana Ekran");
        jButton15.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton15ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel11, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel12, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(379, 379, 379)
                .addComponent(jButton15)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(jPanel12, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel11, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jButton15)
                .addContainerGap(60, Short.MAX_VALUE))
        );

        jPanel1.add(jPanel4, "card4");

        jPanel5.setBackground(new java.awt.Color(204, 204, 255));

        jPanel17.setBackground(new java.awt.Color(204, 204, 255));
        jPanel17.setBorder(javax.swing.BorderFactory.createTitledBorder("ŞİFRE İŞLEMLERİ"));

        jLabel28.setText("Kullanıcı Adı");

        jLabel29.setText("Kullanıcı Şifre");

        jLabel30.setText("Yeni Şifre");

        jButton13.setText("Değiştir");
        jButton13.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton13ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel17Layout = new javax.swing.GroupLayout(jPanel17);
        jPanel17.setLayout(jPanel17Layout);
        jPanel17Layout.setHorizontalGroup(
            jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel17Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jButton13)
                    .addGroup(jPanel17Layout.createSequentialGroup()
                        .addGroup(jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel28)
                            .addComponent(jLabel29)
                            .addComponent(jLabel30))
                        .addGap(80, 80, 80)
                        .addGroup(jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(txtKullaniciAdi)
                            .addComponent(txtKullaniciSifre)
                            .addComponent(txtYeniSifre, javax.swing.GroupLayout.DEFAULT_SIZE, 137, Short.MAX_VALUE))))
                .addGap(67, 179, Short.MAX_VALUE))
        );
        jPanel17Layout.setVerticalGroup(
            jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel17Layout.createSequentialGroup()
                .addGap(37, 37, 37)
                .addGroup(jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel28)
                    .addComponent(txtKullaniciAdi, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel29)
                    .addComponent(txtKullaniciSifre, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGroup(jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel17Layout.createSequentialGroup()
                        .addGap(29, 29, 29)
                        .addComponent(jLabel30))
                    .addGroup(jPanel17Layout.createSequentialGroup()
                        .addGap(13, 13, 13)
                        .addComponent(txtYeniSifre, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(49, 49, 49)
                .addComponent(jButton13)
                .addContainerGap(127, Short.MAX_VALUE))
        );

        jButton17.setText("Ana Ekran");
        jButton17.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jButton17MouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addGap(288, 288, 288)
                        .addComponent(jButton17))
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addGap(192, 192, 192)
                        .addComponent(jPanel17, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(238, Short.MAX_VALUE))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGap(105, 105, 105)
                .addComponent(jPanel17, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(27, 27, 27)
                .addComponent(jButton17)
                .addContainerGap(198, Short.MAX_VALUE))
        );

        jPanel1.add(jPanel5, "card4");

        jPanel6.setBackground(new java.awt.Color(204, 204, 255));

        jPanel18.setBackground(new java.awt.Color(204, 204, 255));
        jPanel18.setBorder(javax.swing.BorderFactory.createTitledBorder("RAPORLAR"));

        jLabel31.setText("Başlangıç Tarihi:");

        jLabel32.setText("Bitiş Tarihi:");

        btnGoster.setText("GÖSTER");
        btnGoster.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGosterActionPerformed(evt);
            }
        });

        tblRaporlar.setBackground(new java.awt.Color(204, 204, 255));
        tblRaporlar.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane4.setViewportView(tblRaporlar);

        jLabel44.setText("Kar-Zarar");

        jLabel45.setText("0.0");

        jLabel46.setText("Gelir:");

        jLabel47.setText("0.0");

        jLabel48.setText("Gider:");

        jLabel49.setText("0.0");

        javax.swing.GroupLayout jPanel18Layout = new javax.swing.GroupLayout(jPanel18);
        jPanel18.setLayout(jPanel18Layout);
        jPanel18Layout.setHorizontalGroup(
            jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel18Layout.createSequentialGroup()
                .addGroup(jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel18Layout.createSequentialGroup()
                        .addGroup(jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel18Layout.createSequentialGroup()
                                .addGap(109, 109, 109)
                                .addGroup(jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jLabel48)
                                    .addComponent(jLabel46)
                                    .addComponent(jLabel44))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(jLabel49, javax.swing.GroupLayout.DEFAULT_SIZE, 40, Short.MAX_VALUE)
                                    .addComponent(jLabel45, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jLabel47, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                            .addGroup(jPanel18Layout.createSequentialGroup()
                                .addGap(89, 89, 89)
                                .addGroup(jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel31)
                                    .addComponent(jDateChooser1, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(74, 74, 74)
                                .addGroup(jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel32)
                                    .addComponent(jDateChooser2, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(61, 61, 61)
                                .addComponent(btnGoster)))
                        .addGap(0, 124, Short.MAX_VALUE))
                    .addGroup(jPanel18Layout.createSequentialGroup()
                        .addGap(70, 70, 70)
                        .addComponent(jScrollPane4)))
                .addContainerGap())
        );
        jPanel18Layout.setVerticalGroup(
            jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel18Layout.createSequentialGroup()
                .addGroup(jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel18Layout.createSequentialGroup()
                        .addGap(15, 15, 15)
                        .addComponent(jLabel32))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel18Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel31)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(btnGoster)
                    .addComponent(jDateChooser1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jDateChooser2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(36, 36, 36)
                .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 225, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(27, 27, 27)
                .addGroup(jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel46)
                    .addComponent(jLabel47))
                .addGap(24, 24, 24)
                .addGroup(jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel48)
                    .addComponent(jLabel49))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 22, Short.MAX_VALUE)
                .addGroup(jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel44)
                    .addComponent(jLabel45))
                .addGap(105, 105, 105))
        );

        jButton14.setText("Ana Ekran");
        jButton14.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jButton14MouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addGap(378, 378, 378)
                        .addComponent(jButton14))
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addGap(39, 39, 39)
                        .addComponent(jPanel18, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(168, Short.MAX_VALUE))
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addGap(37, 37, 37)
                .addComponent(jPanel18, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jButton14)
                .addContainerGap(68, Short.MAX_VALUE))
        );

        jPanel1.add(jPanel6, "card4");

        jPanel7.setBackground(new java.awt.Color(204, 204, 255));

        jPanel19.setBackground(new java.awt.Color(204, 204, 255));
        jPanel19.setBorder(javax.swing.BorderFactory.createTitledBorder("SİPARİŞLER"));

        tblMudurSiparisler.setBackground(new java.awt.Color(204, 204, 255));
        tblMudurSiparisler.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane5.setViewportView(tblMudurSiparisler);

        btnYenile.setText("YENİLE");
        btnYenile.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnYenileActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel19Layout = new javax.swing.GroupLayout(jPanel19);
        jPanel19.setLayout(jPanel19Layout);
        jPanel19Layout.setHorizontalGroup(
            jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel19Layout.createSequentialGroup()
                .addGroup(jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel19Layout.createSequentialGroup()
                        .addGap(67, 67, 67)
                        .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel19Layout.createSequentialGroup()
                        .addGap(247, 247, 247)
                        .addComponent(btnYenile)))
                .addContainerGap(144, Short.MAX_VALUE))
        );
        jPanel19Layout.setVerticalGroup(
            jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel19Layout.createSequentialGroup()
                .addGap(23, 23, 23)
                .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 337, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(btnYenile)
                .addContainerGap(80, Short.MAX_VALUE))
        );

        jButton18.setText("Ana Ekran");
        jButton18.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jButton18MouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addGap(49, 49, 49)
                        .addComponent(jPanel19, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addGap(326, 326, 326)
                        .addComponent(jButton18)))
                .addContainerGap(186, Short.MAX_VALUE))
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addGap(47, 47, 47)
                .addComponent(jPanel19, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(44, 44, 44)
                .addComponent(jButton18)
                .addContainerGap(95, Short.MAX_VALUE))
        );

        jPanel1.add(jPanel7, "card4");

        jPanel8.setBackground(new java.awt.Color(204, 204, 255));

        jPanel22.setBackground(new java.awt.Color(204, 204, 255));
        jPanel22.setBorder(javax.swing.BorderFactory.createTitledBorder("Adisyon tablosu"));

        jTable1.setBackground(new java.awt.Color(204, 204, 255));
        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane6.setViewportView(jTable1);

        javax.swing.GroupLayout jPanel22Layout = new javax.swing.GroupLayout(jPanel22);
        jPanel22.setLayout(jPanel22Layout);
        jPanel22Layout.setHorizontalGroup(
            jPanel22Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel22Layout.createSequentialGroup()
                .addContainerGap(88, Short.MAX_VALUE)
                .addComponent(jScrollPane6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(85, 85, 85))
        );
        jPanel22Layout.setVerticalGroup(
            jPanel22Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel22Layout.createSequentialGroup()
                .addGap(43, 43, 43)
                .addComponent(jScrollPane6, javax.swing.GroupLayout.PREFERRED_SIZE, 379, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(69, Short.MAX_VALUE))
        );

        jButton23.setText("Ana Ekran");
        jButton23.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton23ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel8Layout.createSequentialGroup()
                        .addGap(346, 346, 346)
                        .addComponent(jButton23))
                    .addGroup(jPanel8Layout.createSequentialGroup()
                        .addGap(76, 76, 76)
                        .addComponent(jPanel22, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(197, Short.MAX_VALUE))
        );
        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addGap(70, 70, 70)
                .addComponent(jPanel22, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jButton23)
                .addContainerGap(88, Short.MAX_VALUE))
        );

        jPanel1.add(jPanel8, "card4");

        jPanel9.setBackground(new java.awt.Color(204, 204, 255));

        jPanel20.setBackground(new java.awt.Color(204, 204, 255));
        jPanel20.setBorder(javax.swing.BorderFactory.createTitledBorder("PERSONEL LİSTESİ"));

        tblMPersonel.setBackground(new java.awt.Color(204, 204, 255));
        tblMPersonel.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        tblMPersonel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblMPersonelMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tblMPersonel);

        jButton6.setText("ARA");
        jButton6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton6ActionPerformed(evt);
            }
        });

        jButton5.setText("Çalışanlar");
        jButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton5ActionPerformed(evt);
            }
        });

        jButton4.setText("Ayrılanlar");
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel20Layout = new javax.swing.GroupLayout(jPanel20);
        jPanel20.setLayout(jPanel20Layout);
        jPanel20Layout.setHorizontalGroup(
            jPanel20Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel20Layout.createSequentialGroup()
                .addGroup(jPanel20Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel20Layout.createSequentialGroup()
                        .addGap(62, 62, 62)
                        .addGroup(jPanel20Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(txtMAra, javax.swing.GroupLayout.PREFERRED_SIZE, 251, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel20Layout.createSequentialGroup()
                                .addComponent(jButton5)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jButton4))))
                    .addGroup(jPanel20Layout.createSequentialGroup()
                        .addGap(23, 23, 23)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 341, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel20Layout.createSequentialGroup()
                        .addGap(153, 153, 153)
                        .addComponent(jButton6)))
                .addContainerGap(23, Short.MAX_VALUE))
        );
        jPanel20Layout.setVerticalGroup(
            jPanel20Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel20Layout.createSequentialGroup()
                .addGap(43, 43, 43)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 290, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(55, 55, 55)
                .addGroup(jPanel20Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton5)
                    .addComponent(jButton4))
                .addGap(33, 33, 33)
                .addComponent(txtMAra, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jButton6)
                .addContainerGap(31, Short.MAX_VALUE))
        );

        jPanel21.setBackground(new java.awt.Color(204, 204, 255));

        jLabel13.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel13.setText("AD");

        jLabel14.setText("SOYAD");

        jLabel15.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel15.setText("TC");

        jButton1.setText("Ekle");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jButton3.setText("Sil");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        jLabel12.setText("TELEFON");

        jLabel33.setText("MAİL");

        jLabel34.setText("ADRES");

        jLabel35.setText("GÖREV");

        cmbGorev.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        jLabel36.setText("RESİM");

        jLabel37.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/icons8_Customer_96px.png"))); // NOI18N

        jButton19.setText("Düzenle");
        jButton19.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton19ActionPerformed(evt);
            }
        });

        jButton21.setText("Resim Ekle");
        jButton21.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton21ActionPerformed(evt);
            }
        });

        jButton22.setText("Resim Sil");

        javax.swing.GroupLayout jPanel21Layout = new javax.swing.GroupLayout(jPanel21);
        jPanel21.setLayout(jPanel21Layout);
        jPanel21Layout.setHorizontalGroup(
            jPanel21Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel21Layout.createSequentialGroup()
                .addGap(35, 35, 35)
                .addGroup(jPanel21Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel21Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addComponent(jLabel34)
                        .addComponent(jLabel33)
                        .addComponent(jLabel12)
                        .addComponent(jLabel14)
                        .addGroup(jPanel21Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel13, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel15, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 28, Short.MAX_VALUE)))
                    .addGroup(jPanel21Layout.createSequentialGroup()
                        .addGap(11, 11, 11)
                        .addGroup(jPanel21Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel35)
                            .addComponent(jLabel36)
                            .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGroup(jPanel21Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel21Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel21Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel21Layout.createSequentialGroup()
                                .addGroup(jPanel21Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(txtTC, javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(txtPSoyad, javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(txtPAd, javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(txtPTel, javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(txtPMail, javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(txtPAdres, javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(cmbGorev, javax.swing.GroupLayout.Alignment.LEADING, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addGap(80, 80, 80))
                            .addGroup(jPanel21Layout.createSequentialGroup()
                                .addGap(81, 81, 81)
                                .addComponent(jLabel37)
                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                    .addGroup(jPanel21Layout.createSequentialGroup()
                        .addGap(39, 39, 39)
                        .addGroup(jPanel21Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jButton21)
                            .addComponent(jButton19))
                        .addGroup(jPanel21Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel21Layout.createSequentialGroup()
                                .addGap(54, 54, 54)
                                .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel21Layout.createSequentialGroup()
                                .addGap(18, 18, 18)
                                .addComponent(jButton22)))
                        .addGap(0, 104, Short.MAX_VALUE))))
        );
        jPanel21Layout.setVerticalGroup(
            jPanel21Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel21Layout.createSequentialGroup()
                .addGap(33, 33, 33)
                .addGroup(jPanel21Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtTC, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel15))
                .addGap(18, 18, 18)
                .addGroup(jPanel21Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtPAd, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel13))
                .addGap(18, 18, 18)
                .addGroup(jPanel21Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtPSoyad, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel14))
                .addGap(18, 18, 18)
                .addGroup(jPanel21Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel12)
                    .addComponent(txtPTel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel21Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel33)
                    .addComponent(txtPMail, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel21Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel34)
                    .addComponent(txtPAdres, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel21Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cmbGorev, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel35))
                .addGroup(jPanel21Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel21Layout.createSequentialGroup()
                        .addGap(75, 75, 75)
                        .addComponent(jLabel36))
                    .addGroup(jPanel21Layout.createSequentialGroup()
                        .addGap(36, 36, 36)
                        .addComponent(jLabel37)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel21Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton21)
                    .addComponent(jButton22))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel21Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton1)
                    .addComponent(jButton19)
                    .addComponent(jButton3))
                .addGap(40, 40, 40))
        );

        jButton20.setText("Ana Ekran");
        jButton20.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jButton20MouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanel9Layout = new javax.swing.GroupLayout(jPanel9);
        jPanel9.setLayout(jPanel9Layout);
        jPanel9Layout.setHorizontalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel9Layout.createSequentialGroup()
                        .addGap(23, 23, 23)
                        .addComponent(jPanel20, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jPanel21, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel9Layout.createSequentialGroup()
                        .addGap(387, 387, 387)
                        .addComponent(jButton20)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel9Layout.setVerticalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addGap(27, 27, 27)
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel21, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel20, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(18, 18, 18)
                .addComponent(jButton20)
                .addContainerGap(86, Short.MAX_VALUE))
        );

        jPanel1.add(jPanel9, "card4");

        jPanel10.setBackground(new java.awt.Color(204, 204, 255));

        jPanel15.setBackground(new java.awt.Color(204, 204, 255));
        jPanel15.setBorder(javax.swing.BorderFactory.createTitledBorder("KAMPANYA İŞLEMLERİ"));

        jLabel21.setText("Menu Adı");

        jLabel22.setText("Başlangıç T:");

        jLabel23.setText("Bitiş T.");

        jLabel24.setText("Menu Fiyat");

        jLabel25.setText("Menu Maliyet");

        jLabel26.setText("Menü Resim");

        jLabel27.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/icons8_Restaurant_96px.png"))); // NOI18N

        jButton12.setText("Resim Ekle");
        jButton12.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton12ActionPerformed(evt);
            }
        });

        btnMenuEkle.setText("Menü Ekle");
        btnMenuEkle.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnMenuEkleActionPerformed(evt);
            }
        });

        jButton2.setText("Resim Sil");

        javax.swing.GroupLayout jPanel15Layout = new javax.swing.GroupLayout(jPanel15);
        jPanel15.setLayout(jPanel15Layout);
        jPanel15Layout.setHorizontalGroup(
            jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel15Layout.createSequentialGroup()
                .addGroup(jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel15Layout.createSequentialGroup()
                        .addGap(16, 16, 16)
                        .addGroup(jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel23)
                            .addComponent(jLabel21)
                            .addComponent(jLabel22)
                            .addComponent(jLabel24)))
                    .addGroup(jPanel15Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel25))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel15Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel26)))
                .addGap(29, 29, 29)
                .addGroup(jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtMenuMaliyet, javax.swing.GroupLayout.PREFERRED_SIZE, 212, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(txtMenuFiyat, javax.swing.GroupLayout.Alignment.TRAILING)
                        .addComponent(jDateChooser4, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 212, Short.MAX_VALUE)
                        .addComponent(jDateChooser3, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(txtMenuAdi, javax.swing.GroupLayout.Alignment.TRAILING)
                        .addGroup(jPanel15Layout.createSequentialGroup()
                            .addGap(2, 2, 2)
                            .addComponent(btnMenuEkle)
                            .addGap(0, 0, Short.MAX_VALUE)))
                    .addComponent(jLabel27))
                .addContainerGap(61, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel15Layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(jButton12)
                .addGap(18, 18, 18)
                .addComponent(jButton2)
                .addGap(135, 135, 135))
        );
        jPanel15Layout.setVerticalGroup(
            jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel15Layout.createSequentialGroup()
                .addGap(24, 24, 24)
                .addGroup(jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel21)
                    .addComponent(txtMenuAdi, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel22)
                    .addComponent(jDateChooser3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(21, 21, 21)
                .addGroup(jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel23)
                    .addComponent(jDateChooser4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel24)
                    .addComponent(txtMenuFiyat, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtMenuMaliyet, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel25))
                .addGroup(jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel15Layout.createSequentialGroup()
                        .addGap(40, 40, 40)
                        .addComponent(jLabel27, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel15Layout.createSequentialGroup()
                        .addGap(80, 80, 80)
                        .addComponent(jLabel26)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton2)
                    .addComponent(jButton12))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnMenuEkle)
                .addGap(36, 36, 36))
        );

        jPanel16.setBackground(new java.awt.Color(204, 204, 255));
        jPanel16.setBorder(javax.swing.BorderFactory.createTitledBorder("KAMPANYALAR"));

        tblKampanyalar.setBackground(new java.awt.Color(204, 204, 255));
        tblKampanyalar.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane2.setViewportView(tblKampanyalar);

        javax.swing.GroupLayout jPanel16Layout = new javax.swing.GroupLayout(jPanel16);
        jPanel16.setLayout(jPanel16Layout);
        jPanel16Layout.setHorizontalGroup(
            jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel16Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel16Layout.setVerticalGroup(
            jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel16Layout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(83, Short.MAX_VALUE))
        );

        jButton11.setText("Ana Ekran");
        jButton11.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jButton11MouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanel10Layout = new javax.swing.GroupLayout(jPanel10);
        jPanel10.setLayout(jPanel10Layout);
        jPanel10Layout.setHorizontalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel10Layout.createSequentialGroup()
                        .addComponent(jPanel15, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(28, 28, 28)
                        .addComponent(jPanel16, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel10Layout.createSequentialGroup()
                        .addGap(359, 359, 359)
                        .addComponent(jButton11)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel10Layout.setVerticalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addGap(39, 39, 39)
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel15, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel16, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(18, 18, 18)
                .addComponent(jButton11)
                .addContainerGap(79, Short.MAX_VALUE))
        );

        jPanel1.add(jPanel10, "card4");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void mdrAnaEkranMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_mdrAnaEkranMouseClicked


    }//GEN-LAST:event_mdrAnaEkranMouseClicked

    private void jLabel1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel1MouseClicked

        jPanel1.removeAll();
        jPanel1.repaint();
        jPanel1.revalidate();
        jPanel1.add(jPanel3);
        jPanel1.repaint();
        jPanel1.revalidate();
    }//GEN-LAST:event_jLabel1MouseClicked

    private void jLabel4MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel4MouseClicked

        jPanel1.removeAll();
        jPanel1.repaint();
        jPanel1.revalidate();
        jPanel1.add(jPanel4);
        jPanel1.repaint();
        jPanel1.revalidate();
    }//GEN-LAST:event_jLabel4MouseClicked

    private void jLabel6MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel6MouseClicked

        jPanel1.removeAll();
        jPanel1.repaint();
        jPanel1.revalidate();
        jPanel1.add(jPanel5);
        jPanel1.repaint();
        jPanel1.revalidate();
    }//GEN-LAST:event_jLabel6MouseClicked

    private void jLabel10MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel10MouseClicked

        jPanel1.removeAll();
        jPanel1.repaint();
        jPanel1.revalidate();
        jPanel1.add(jPanel6);
        jPanel1.repaint();
        jPanel1.revalidate();
    }//GEN-LAST:event_jLabel10MouseClicked

    private void jLabel7MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel7MouseClicked

        jPanel1.removeAll();
        jPanel1.repaint();
        jPanel1.revalidate();
        jPanel1.add(jPanel7);
        jPanel1.repaint();
        jPanel1.revalidate();
    }//GEN-LAST:event_jLabel7MouseClicked

    private void jLabel5MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel5MouseClicked

        jPanel1.removeAll();
        jPanel1.repaint();
        jPanel1.revalidate();
        jPanel1.add(jPanel8);
        jPanel1.repaint();
        jPanel1.revalidate();
    }//GEN-LAST:event_jLabel5MouseClicked

    private void jLabel3MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel3MouseClicked

        jPanel1.removeAll();
        jPanel1.repaint();
        jPanel1.revalidate();
        jPanel1.add(jPanel9);
        jPanel1.repaint();
        jPanel1.revalidate();
    }//GEN-LAST:event_jLabel3MouseClicked

    private void jLabel8MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel8MouseClicked

        jPanel1.removeAll();
        jPanel1.repaint();
        jPanel1.revalidate();
        jPanel1.add(jPanel10);
        jPanel1.repaint();
        jPanel1.revalidate();
    }//GEN-LAST:event_jLabel8MouseClicked

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed

        mDataGetir();

    }//GEN-LAST:event_jButton5ActionPerformed

    GarsonPro gr = new GarsonPro();
    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed

        String tc = txtTC.getText().trim();
        String ad = txtPAd.getText().trim();
        String soyad = txtPSoyad.getText().trim();
        String tel = txtPTel.getText().trim();
        String mail = txtPMail.getText().trim();
        String adres = txtPAdres.getText().trim();
        int kgorevid = gvp.get(cmbGorev.getSelectedIndex()).getGorevid();
        String kdurum = "aktif";
        String sifre = "1";

        if (tc.equals("")) {
            JOptionPane.showMessageDialog(rootPane, "lutfen TC nizi gırınız");
            txtTC.requestFocus();
        } else if (ad.equals("")) {
            JOptionPane.showMessageDialog(rootPane, "lutfen adınızı gırınız");
            txtPAd.requestFocus();
        } else if (soyad.equals("")) {
            JOptionPane.showMessageDialog(rootPane, "lutfen soyadınızı gırınız");
            txtPSoyad.requestFocus();
        } else if (tel.equals("")) {
            JOptionPane.showMessageDialog(rootPane, "lutfen telefon numaranızı gırınız");
            txtPTel.requestFocus();
        } else if (mail.equals("")) {
            JOptionPane.showMessageDialog(rootPane, "lutfen mail adresinizi gırınız");
            txtPMail.requestFocus();
        } else if (adres.equals("")) {
            JOptionPane.showMessageDialog(rootPane, "lutfen adres bilgilerinizi gırınız");
            txtPAdres.requestFocus();
        } else {
            if (my.tcknValidator(tc) == 1 && my.adSoyadValidator(ad) == 1 && my.adSoyadValidator(soyad) == 1
                    && my.telefonValidator(tel) == 1 && my.emailValidator(mail) == 1) {
                try {
                    String q = "CALL personelEkle('" + tc + "','" + ad + "','" + soyad + "','" + tel + "','" + mail + "',"
                            + "'" + adres + "','" + kgorevid + "','" + sifre + "','" + path + "','" + kdurum + "')";
                    db.baglan().executeQuery(q);
                    mDataGetir();
                    JOptionPane.showMessageDialog(rootPane, "Kaydınız Başarılıdır.");
                    txtPAd.setText("");
                    txtPSoyad.setText("");
                    txtTC.setText("");
                    txtPTel.setText("");
                    txtPMail.setText("");
                    txtPAdres.setText("");
                    txtTC.requestFocus();
                } catch (SQLException e) {
                    System.out.println("personel yazılamadı!" + e);
                }
            } else {
                if (my.adSoyadValidator(ad) == -1) {
                    JOptionPane.showMessageDialog(rootPane, "Ad alanı sadece harf içermelidir!");
                    txtPAd.setText("");
                    txtPAd.requestFocus();
                } else if (my.adSoyadValidator(soyad) == -1) {
                    JOptionPane.showMessageDialog(rootPane, "Soyad alanı sadece harf içermelidir!");
                    txtPSoyad.setText("");
                    txtPSoyad.requestFocus();
                } else if (my.tcknValidator(tc) == -1) {
                    JOptionPane.showMessageDialog(rootPane, "TCKN 11 haneli olmalıdır");
                    txtTC.setText("");
                    txtTC.requestFocus();
                } else if (my.tcknValidator(tc) == -2) {
                    JOptionPane.showMessageDialog(rootPane, "TCKN de sadece rakamlar bulunmalıdır");
                    txtTC.setText("");
                    txtTC.requestFocus();
                } else if (my.tcknValidator(tc) == -3) {
                    JOptionPane.showMessageDialog(rootPane, "TCKN'niz yasadışıdır!");
                    txtTC.setText("");
                    txtTC.requestFocus();
                } else if (my.emailValidator(mail) == -1) {
                    JOptionPane.showMessageDialog(rootPane, "Mailiniz uygun değildir!");
                    txtPMail.setText("");
                    txtPMail.requestFocus();
                }
            }

        }

    }//GEN-LAST:event_jButton1ActionPerformed

    String mid = "";
    private void tblMPersonelMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblMPersonelMouseClicked

        int row = tblMPersonel.getSelectedRow();
        mid = "" + tblMPersonel.getValueAt(row, 0);
        personelGetir();
        doldur();
        // mid=grp.get(tblMPersonel.getSelectedRow());
    }//GEN-LAST:event_tblMPersonelMouseClicked

    // KullaniciPro gr=new KullaniciPro();
    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed

        String gdurum = "pasif";
        if (mid.equals("")) {
            JOptionPane.showMessageDialog(rootPane, "Lütfen seçim yapınız!");
        } else {
            int cevap = JOptionPane.showConfirmDialog(rootPane, "silmek istediğinizden emin misiniz", "UYARI", JOptionPane.YES_NO_OPTION);
            try {
                String q = "{CALL personelSil('" + mid + "') }";
                PreparedStatement ps = db.preBaglan(q);
                int yaz = ps.executeUpdate();
                if (yaz > 0) {
                    System.out.println("durum  güncellendi");
                    mDataGetir();
                }
            } catch (Exception e) {
                System.out.println("personel silme işlemi başarısızdır:" + e);
            }
        }
    }//GEN-LAST:event_jButton3ActionPerformed

    private void jButton6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton6ActionPerformed
        String ara = txtMAra.getText().trim();
        DefaultTableModel dtm = new DefaultTableModel();
        dtm.addColumn("ID");
        dtm.addColumn("Ad");
        dtm.addColumn("Soyad");

        try {
            ResultSet rs = db.baglan().executeQuery("{call arama('" + ara + "')}");
            while (rs.next()) {
                dtm.addRow(new Object[]{rs.getInt(1), rs.getString(3), rs.getString(4)});

            }
        } catch (Exception e) {
            System.out.println("data getirme hatasi" + e);
        }

        tblMPersonel.setModel(dtm);
    }//GEN-LAST:event_jButton6ActionPerformed

    public void adisyonTakip() {
        try {
            String q = ("call adisyonTakip()");
            ResultSet rs = db.baglan().executeQuery(q);
            DefaultTableModel dtm = new DefaultTableModel();
            dtm.addColumn("ID");
            dtm.addColumn("MASA ADI");
            dtm.addColumn("YEMEK ADI");
            dtm.addColumn("Y.ADET");
            dtm.addColumn("Y.FİYAT");
            dtm.addColumn("KAMPANYA");
            dtm.addColumn("K.ADET");
            dtm.addColumn("K.FİYAT");
            while (rs.next()) {
                dtm.addRow(new Object[]{rs.getInt("masaid"), rs.getString("madi"), rs.getString("yadi"), rs.getInt("sadet"), rs.getInt("satisFiyati"),
                    rs.getString("menuadi"), rs.getInt("madet"), rs.getInt("menufiyat")});
            }
            jTable1.setModel(dtm);
        } catch (SQLException e) {
            System.err.println("adisyon hatasi:" + e);
        }
    }

    String baslangic = "";
    String bitis = "";

    public void siparisRaporlama() {

        baslangic = dateFormat.format(jDateChooser1.getDate()).toString();
        bitis = dateFormat.format(jDateChooser2.getDate()).toString();
        System.out.println("baslangic : " + baslangic);
        System.out.println("bitis : " + bitis);

        try {
            String q = "call siparisRaporlama(?,?)";
            PreparedStatement ps = db.preBaglan(q);
            ps.setString(1, baslangic);
            ps.setString(2, bitis);
            int yaz = ps.executeUpdate();
            if (yaz > 0) {
                siparisRaporlamaTabloDoldur();
            } else {
                System.out.println("Yazma hatası!");
            }
        } catch (Exception e) {
            System.err.println("Siparis raporlama hatası : " + e);
        }
    }

    public void siparisRaporlamaTabloDoldur() {

        try {
            String q = "call siparisRaporlamaTabloDoldur('" + baslangic + "','" + bitis + "')";
            ResultSet rs = db.baglan().executeQuery(q);

            DefaultTableModel dtm = new DefaultTableModel();
            dtm.addColumn("ID");
            dtm.addColumn("Masa Adı");
            dtm.addColumn("Yemek Adı");
            dtm.addColumn("Y.Adet");
            dtm.addColumn("Y.Maliyet");
            dtm.addColumn("Y.Fiyat");
            dtm.addColumn("Menu Adı");
            dtm.addColumn("M.Adet");
            dtm.addColumn("M.Maliyet");
            dtm.addColumn("M.Fiyat");
            float gelir = 1;
            float gider = 1;
            float kz = 1;
            while (rs.next()) {
                dtm.addRow(new Object[]{rs.getInt(1), rs.getString(2), rs.getString(3), rs.getInt(4), rs.getInt(5), rs.getInt(6),
                     rs.getString(7), rs.getInt(8), rs.getFloat(9), rs.getFloat(10)});
                gelir = gelir + (rs.getInt(4) * rs.getInt(5)) + (rs.getInt(8) * rs.getFloat(9));
                gider = gider + (rs.getInt(4) * rs.getInt(6)) + (rs.getInt(8) * rs.getFloat(10));
                kz = (gider - gelir) * 100 / gelir;
            }
            tblRaporlar.setModel(dtm);
            jLabel49.setText(gelir + "");
            jLabel47.setText(gider + "");
            jLabel45.setText(kz + "");
        } catch (Exception e) {
            System.err.println("Siparis raporlama tablo doldurma hatası : " + e);
        }

    }

    String path = "";
    private void btnResimEkleActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnResimEkleActionPerformed

        resimEkle();
        lblResim.setIcon(ResizeImage(path, null));

    }//GEN-LAST:event_btnResimEkleActionPerformed

     static int gorevid=-1;
  static int kullanici=-1;
  static String adi,sifresi=null;
    private void jButton13ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton13ActionPerformed

        String kullaniciadi = txtKullaniciAdi.getText().trim();
        String sifre = txtKullaniciSifre.getText().trim();
        String yeniSifre = txtYeniSifre.getText().trim();


            if (txtKullaniciAdi.equals("")) {
            JOptionPane.showMessageDialog(rootPane, "Lütfen kullanıcı adını giriniz.");
            txtKullaniciAdi.requestFocus();
            } else if (txtKullaniciSifre.equals("")) {
            JOptionPane.showMessageDialog(rootPane, "Lütfen sifreyi giriniz.");
            txtKullaniciSifre.requestFocus();
            }else if (txtYeniSifre.equals("")) {
            JOptionPane.showMessageDialog(rootPane, "Lütfen yeni sifrenizi giriniz.");
            txtYeniSifre.requestFocus();
            }else {
                 if(sifre.equals(yeniSifre)){
                     JOptionPane.showMessageDialog(rootPane,"eski şifreniz yeni şifrenizle aynı olamaz!");
                 }else{
                     try {
                           String q = "call mudurSifre('" + kullaniciadi + "','" + sifre + "','" + yeniSifre + "')";
                           PreparedStatement ps=db.preBaglan(q);
                           int yaz=ps.executeUpdate();
                           if(yaz>0){
                            JOptionPane.showMessageDialog(rootPane,"Şifreniz değiştirilmiştir");   
                           }
                     } catch (HeadlessException | SQLException e) {
                         System.out.println("Şifre Değiştirme Hatası:"+e);
                     }
             }
            }  
        
        
        
    }//GEN-LAST:event_jButton13ActionPerformed

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed

        personelAyrilan();

    }//GEN-LAST:event_jButton4ActionPerformed

    private void jButton11MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton11MouseClicked

        jPanel1.removeAll();
        jPanel1.repaint();
        jPanel1.revalidate();
        jPanel1.add(mdrAnaEkran);
        jPanel1.repaint();
        jPanel1.revalidate();

    }//GEN-LAST:event_jButton11MouseClicked

    private void jButton15ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton15ActionPerformed

        jPanel1.removeAll();
        jPanel1.repaint();
        jPanel1.revalidate();
        jPanel1.add(mdrAnaEkran);
        jPanel1.repaint();
        jPanel1.revalidate();
    }//GEN-LAST:event_jButton15ActionPerformed

    private void jButton16ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton16ActionPerformed

        jPanel1.removeAll();
        jPanel1.repaint();
        jPanel1.revalidate();
        jPanel1.add(mdrAnaEkran);
        jPanel1.repaint();
        jPanel1.revalidate();
    }//GEN-LAST:event_jButton16ActionPerformed

    private void jButton16MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton16MouseClicked
        jPanel1.removeAll();
        jPanel1.repaint();
        jPanel1.revalidate();
        jPanel1.add(mdrAnaEkran);
        jPanel1.repaint();
        jPanel1.revalidate();
    }//GEN-LAST:event_jButton16MouseClicked

    private void jButton17MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton17MouseClicked

        jPanel1.removeAll();
        jPanel1.repaint();
        jPanel1.revalidate();
        jPanel1.add(mdrAnaEkran);
        jPanel1.repaint();
        jPanel1.revalidate();
    }//GEN-LAST:event_jButton17MouseClicked

    private void jButton14MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton14MouseClicked

        jPanel1.removeAll();
        jPanel1.repaint();
        jPanel1.revalidate();
        jPanel1.add(mdrAnaEkran);
        jPanel1.repaint();
        jPanel1.revalidate();
    }//GEN-LAST:event_jButton14MouseClicked

    private void jButton18MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton18MouseClicked

        jPanel1.removeAll();
        jPanel1.repaint();
        jPanel1.revalidate();
        jPanel1.add(mdrAnaEkran);
        jPanel1.repaint();
        jPanel1.revalidate();
    }//GEN-LAST:event_jButton18MouseClicked

    private void jButton20MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton20MouseClicked

        jPanel1.removeAll();
        jPanel1.repaint();
        jPanel1.revalidate();
        jPanel1.add(mdrAnaEkran);
        jPanel1.repaint();
        jPanel1.revalidate();
    }//GEN-LAST:event_jButton20MouseClicked

    private void jButton12ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton12ActionPerformed

        resimEkle();
        jLabel27.setIcon(ResizeImage(path, null));

    }//GEN-LAST:event_jButton12ActionPerformed

    private void btnYemekEkleActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnYemekEkleActionPerformed

        yemekEkle();

    }//GEN-LAST:event_btnYemekEkleActionPerformed

    private void btnMenuEkleActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnMenuEkleActionPerformed
        kampanyaliMenu();
    }//GEN-LAST:event_btnMenuEkleActionPerformed

    private void btnYenileActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnYenileActionPerformed
        siparisTabloDoldur();
    }//GEN-LAST:event_btnYenileActionPerformed

    private void btnGosterActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGosterActionPerformed

        siparisRaporlama();


    }//GEN-LAST:event_btnGosterActionPerformed

    private void jButton21ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton21ActionPerformed

        resimEkle();
        jLabel37.setIcon(ResizeImage(path, null));

    }//GEN-LAST:event_jButton21ActionPerformed

    private void jButton23ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton23ActionPerformed

        jPanel1.removeAll();
        jPanel1.repaint();
        jPanel1.revalidate();
        jPanel1.add(mdrAnaEkran);
        jPanel1.repaint();
        jPanel1.revalidate();
    }//GEN-LAST:event_jButton23ActionPerformed


    private void txtMasaEkleActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtMasaEkleActionPerformed

        String masaadi = txtMasEkle.getText().trim();
        
        try {
            String q = "call masaEkle(?)";
            PreparedStatement ps = db.preBaglan(q);
            ps.setString(1, masaadi);
            int yaz = ps.executeUpdate();
            if (yaz > 0) {
                System.out.println("masa eklendi.");
            } else {
                System.out.println("Yazma hatası!");
            }
            masaDoldur();
        } catch (SQLException ex) {
            System.err.println("masa ekleme hatası : " + ex);
        }


    }//GEN-LAST:event_txtMasaEkleActionPerformed

    public void personelGetir() {
        grp.clear();
        try {
            String q = "call personelGetir('" + mid + "')";
            ResultSet rs = db.baglan().executeQuery(q);
            while (rs.next()) {
                KullaniciPro kp = new KullaniciPro();
                kp.setSifreid(rs.getInt("" + KullaniciEnum.ksifre));
                kp.setkTC(rs.getString("" + KullaniciEnum.kTC));
                kp.setKullaniciAdi(rs.getString("" + KullaniciEnum.kullaniciAdi));
                kp.setKullaniciSoyadi(rs.getString("" + KullaniciEnum.kullaniciSoyadi));
                kp.setKtel(rs.getString("" + KullaniciEnum.ktel));
                kp.setKmail(rs.getString("" + KullaniciEnum.kmail));
                kp.setKadres(rs.getString("" + KullaniciEnum.kadres));
                kp.setKgorevid(rs.getInt("" + KullaniciEnum.kgorevid));
                kp.setKresim(rs.getString("" + KullaniciEnum.kresim));
                kp.setKsifre(rs.getString("" + KullaniciEnum.ksifre));
                kp.setKdurum(rs.getString("" + KullaniciEnum.kdurum));
                grp.add(kp);
            }

        } catch (Exception e) {
        }
    }

    public void doldur() {

        for (KullaniciPro item : grp) {
            txtTC.setText(item.getkTC());
            txtPAd.setText(item.getKullaniciAdi());
            txtPSoyad.setText(item.getKullaniciSoyadi());
            txtPTel.setText(item.getKtel());
            txtPMail.setText(item.getKmail());
            txtPAdres.setText(item.getKadres());
        }
    }

    private void jButton19ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton19ActionPerformed

        String tc = txtTC.getText().trim();
        String ad = txtPAd.getText().trim();
        String soyad = txtPSoyad.getText().trim();
        String tel = txtPTel.getText().trim();
        String mail = txtPMail.getText().trim();
        String adres = txtPAdres.getText().trim();
        int kgorevid = gvp.get(cmbGorev.getSelectedIndex()).getGorevid();
        String kdurum = "aktif";
        String sifre = "1";
        //  int mid=Integer.valueOf(tblMPersonel.getValueAt(tblMPersonel.getSelectedRow(), 0));

        if (tc.equals("")) {
            JOptionPane.showMessageDialog(rootPane, "lutfen TC nizi gırınız");
            txtTC.requestFocus();
        } else if (ad.equals("")) {
            JOptionPane.showMessageDialog(rootPane, "lutfen adınızı gırınız");
            txtPAd.requestFocus();
        } else if (soyad.equals("")) {
            JOptionPane.showMessageDialog(rootPane, "lutfen soyadınızı gırınız");
            txtPSoyad.requestFocus();
        } else if (tel.equals("")) {
            JOptionPane.showMessageDialog(rootPane, "lutfen telefon numaranızı gırınız");
            txtPTel.requestFocus();
        } else if (mail.equals("")) {
            JOptionPane.showMessageDialog(rootPane, "lutfen mail adresinizi gırınız");
            txtPMail.requestFocus();
        } else if (adres.equals("")) {
            JOptionPane.showMessageDialog(rootPane, "lutfen adres bilgilerinizi gırınız");
            txtPAdres.requestFocus();
        } else {

            try {

                String q = "CALL personelDuzenle(?,?,?,?,?,?,?,?,?,?,?)";

                PreparedStatement pr = db.preBaglan(q);
                pr.setInt(1, Integer.valueOf(mid));
                pr.setString(2, tc);
                pr.setString(3, ad);
                pr.setString(4, soyad);
                pr.setString(5, tel);
                pr.setString(6, mail);
                pr.setString(7, adres);
                pr.setInt(8, kgorevid);
                pr.setString(9, sifre);
                pr.setString(10, path);
                pr.setString(11, kdurum);
                int sonuc = pr.executeUpdate();
                if (sonuc > 0) {
                    mDataGetir();

                    JOptionPane.showMessageDialog(rootPane, "Kaydınız Başarılıdır.");
                }
                txtPAd.setText("");
                txtPSoyad.setText("");
                txtTC.setText("");
                txtPTel.setText("");
                txtPMail.setText("");
                txtPAdres.setText("");
                txtTC.requestFocus();
            } catch (SQLException e) {
                System.out.println("personel guncellenemedi!" + e);
            }

        }
    }//GEN-LAST:event_jButton19ActionPerformed

    private void jLabel9MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel9MouseClicked
      
         int secim=JOptionPane.showConfirmDialog(rootPane,"Çıkmak istediğinize emin misiniz?","DİKKAT!", JOptionPane.YES_NO_OPTION);
      if(secim==0){
        dispose();
      }else{
          
      }
        
    }//GEN-LAST:event_jLabel9MouseClicked

    private void cmbMasaListeleActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmbMasaListeleActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cmbMasaListeleActionPerformed

    

    public void masaDoldur() {
        msp.clear();
        try {

            String q = "call masaGetir()";
            ResultSet rs = db.baglan().executeQuery(q);
            while (rs.next()) {
                MasaPro ms = new MasaPro();
                ms.setMasaid(rs.getInt("" + MasaEnum.masaid));
                ms.setMadi(rs.getString("" + MasaEnum.madi));
                ms.setMasaaktif(rs.getBoolean("" + MasaEnum.masaaktif));
                ms.setMsubeid(rs.getInt("" + MasaEnum.msubeid));
                msp.add(ms);
            }
            masaModelDoldur();

        } catch (Exception e) {
            System.err.println("Masa Getirme hatası : " + e);
        }
    }

    public void masaModelDoldur() {
        DefaultComboBoxModel<String> dkm = new DefaultComboBoxModel<>();
        for (MasaPro item : msp) {
            dkm.addElement(item.getMadi());
        }
        cmbMasaListele.setModel(dkm);

    }

    public void resimEkle() {
        JFileChooser file = new JFileChooser();
        file.setCurrentDirectory(new File(System.getProperty("user.home")));
        FileNameExtensionFilter filter = new FileNameExtensionFilter("*.image", "jpg", "png");
        file.addChoosableFileFilter(filter);
        int result = file.showSaveDialog(null);
        if (result == JFileChooser.APPROVE_OPTION) {
            File selectedFile = file.getSelectedFile();
            path = selectedFile.getAbsolutePath().replace("\\", "\\\\");
            System.out.println("path: " + path);

        } else {
            System.out.println("dosya seçilmedi");
        }
    }

    public ImageIcon ResizeImage(String imagePath, byte[] pic) {
        ImageIcon myImage = null;
        if (imagePath != null) {
            myImage = new ImageIcon(imagePath);
        } else {
            myImage = new ImageIcon(pic);
        }
        Image img = myImage.getImage();
        Image img2 = img.getScaledInstance(lblResim.getWidth(), lblResim.getHeight(), Image.SCALE_SMOOTH);

        ImageIcon image = new ImageIcon(img2);

        return image;
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Mudur.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Mudur.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Mudur.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Mudur.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Mudur().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnGoster;
    private javax.swing.JButton btnMenuEkle;
    private javax.swing.JButton btnResimEkle;
    private javax.swing.JButton btnSil;
    private javax.swing.JButton btnYemekEkle;
    private javax.swing.JButton btnYenile;
    private javax.swing.JComboBox<String> cmbGorev;
    private javax.swing.JComboBox<String> cmbKategoriler;
    private javax.swing.JComboBox<String> cmbMasaListele;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton11;
    private javax.swing.JButton jButton12;
    private javax.swing.JButton jButton13;
    private javax.swing.JButton jButton14;
    private javax.swing.JButton jButton15;
    private javax.swing.JButton jButton16;
    private javax.swing.JButton jButton17;
    private javax.swing.JButton jButton18;
    private javax.swing.JButton jButton19;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton20;
    private javax.swing.JButton jButton21;
    private javax.swing.JButton jButton22;
    private javax.swing.JButton jButton23;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
    private javax.swing.JButton jButton6;
    private javax.swing.JButton jButton7;
    private com.toedter.calendar.JDateChooser jDateChooser1;
    private com.toedter.calendar.JDateChooser jDateChooser2;
    private com.toedter.calendar.JDateChooser jDateChooser3;
    private com.toedter.calendar.JDateChooser jDateChooser4;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel26;
    private javax.swing.JLabel jLabel27;
    private javax.swing.JLabel jLabel28;
    private javax.swing.JLabel jLabel29;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel30;
    private javax.swing.JLabel jLabel31;
    private javax.swing.JLabel jLabel32;
    private javax.swing.JLabel jLabel33;
    private javax.swing.JLabel jLabel34;
    private javax.swing.JLabel jLabel35;
    private javax.swing.JLabel jLabel36;
    private javax.swing.JLabel jLabel37;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel44;
    private javax.swing.JLabel jLabel45;
    private javax.swing.JLabel jLabel46;
    private javax.swing.JLabel jLabel47;
    private javax.swing.JLabel jLabel48;
    private javax.swing.JLabel jLabel49;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanel11;
    private javax.swing.JPanel jPanel12;
    private javax.swing.JPanel jPanel14;
    private javax.swing.JPanel jPanel15;
    private javax.swing.JPanel jPanel16;
    private javax.swing.JPanel jPanel17;
    private javax.swing.JPanel jPanel18;
    private javax.swing.JPanel jPanel19;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel20;
    private javax.swing.JPanel jPanel21;
    private javax.swing.JPanel jPanel22;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JScrollPane jScrollPane6;
    private javax.swing.JTable jTable1;
    private javax.swing.JLabel lblResim;
    private javax.swing.JPanel mdrAnaEkran;
    private javax.swing.JTable tblKampanyalar;
    private javax.swing.JTable tblMPersonel;
    private javax.swing.JTable tblMudurSiparisler;
    private javax.swing.JTable tblRaporlar;
    private javax.swing.JTable tblYemekListesi;
    private javax.swing.JTextField txtAciklama;
    private javax.swing.JTextField txtKullaniciAdi;
    private javax.swing.JPasswordField txtKullaniciSifre;
    private javax.swing.JTextField txtMAra;
    private javax.swing.JTextField txtMaliyet;
    private javax.swing.JTextField txtMasEkle;
    private javax.swing.JButton txtMasaEkle;
    private javax.swing.JTextField txtMenuAdi;
    private javax.swing.JTextField txtMenuFiyat;
    private javax.swing.JTextField txtMenuMaliyet;
    private javax.swing.JTextField txtPAd;
    private javax.swing.JTextField txtPAdres;
    private javax.swing.JTextField txtPMail;
    private javax.swing.JTextField txtPSoyad;
    private javax.swing.JTextField txtPTel;
    private javax.swing.JTextField txtSatisFiyati;
    private javax.swing.JTextField txtTC;
    private javax.swing.JTextField txtYemekAdi;
    private javax.swing.JPasswordField txtYeniSifre;
    // End of variables declaration//GEN-END:variables
}
