
import Enums.KategoriEnum;
import Enums.MasaEnum;
import Enums.MenuadiEnum;
import Enums.SiparisEnum;
import Enums.YemekEnum;
import static Enums.YemekEnum.satisfiyati;
import Properties.KategoriPro;
import Properties.MasaPro;
import Properties.MenuadiPro;
import Properties.SiparisPro;
import Properties.YemekPro;
import com.sun.org.apache.xalan.internal.xsltc.compiler.util.StringStack;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.HeadlessException;
import java.awt.Image;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

public class garson extends javax.swing.JFrame {

    DB db = new DB("cafehouse", "root", "");
    ArrayList<KategoriPro> ktp = new ArrayList<>();
    ArrayList<YemekPro> ymp = new ArrayList<>();
    ArrayList<MasaPro> msp = new ArrayList<>();
    ArrayList<MenuadiPro> map = new ArrayList<>();
    ArrayList<SiparisPro> sip = new ArrayList<>();
    static int katid = -1;

    public garson() {
        initComponents();
        kategoriDoldur();
        //yemekDoldur();
        masaDoldur();
        kampanyaDoldur();
        kampanyaGetir();
        siparisTabloDoldur();
        yemekDoldur(1);
        //adisyonGetir(2);
        siparisDoldur();
        adisyon(1);
        hazirSiparisTabloDoldur();
    }

    // kategoriler
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
            System.err.println("kategori getirme hatasi:" + e);
        }
    }

    public void kateModelDoldur() {
        DefaultComboBoxModel<String> dkm = new DefaultComboBoxModel<>();
        for (KategoriPro item : ktp) {
            dkm.addElement(item.getKadi());
        }
        jComboBox2.setModel(dkm);
        jComboBox4.setModel(dkm);
    }

    // yemekler
    public void yemekDoldur(int katid) {
        ymp.clear();
        try {
            String q = "CALL yemekGetir('" + katid + "')";
            ResultSet rs = db.baglan().executeQuery(q);
            while (rs.next()) {
                // System.out.println("Çalıltı");
                YemekPro kr = new YemekPro();
                kr.setYemekid(rs.getInt("" + YemekEnum.yemekid));
                kr.setYadi(rs.getString("" + YemekEnum.yadi));
                kr.setYkatid(rs.getInt("" + YemekEnum.ykatid));
                kr.setMaliyet(rs.getInt("" + YemekEnum.maliyet));
                kr.setSatisfiyati(rs.getInt("" + YemekEnum.satisfiyati));
                kr.setAciklama(rs.getString("" + YemekEnum.aciklama));
                kr.setResim(rs.getString("" + YemekEnum.resim));
                ymp.add(kr);
            }
            yemekModelDoldur();
        } catch (SQLException e) {
            System.err.println("yemek getirme hatasi:" + e);
        }
    }

    public void yemekModelDoldur() {
        DefaultComboBoxModel<String> dkm = new DefaultComboBoxModel<>();
        for (YemekPro item : ymp) {
            dkm.addElement(item.getYadi());
        }
        jComboBox3.setModel(dkm);
        jComboBox5.setModel(dkm);
    }

    //masalar
    public void masaDoldur() {
        msp.clear();
        try {
            String q = "CALL masaGetir()";
            ResultSet rs = db.baglan().executeQuery(q);
            while (rs.next()) {
                MasaPro mp = new MasaPro();
                mp.setMasaid(rs.getInt("" + MasaEnum.masaid));
                mp.setMadi(rs.getString("" + MasaEnum.madi));
                mp.setMsubeid(rs.getInt("" + MasaEnum.msubeid));
                mp.setMasaaktif(rs.getBoolean("" + MasaEnum.masaaktif));
                msp.add(mp);
                int a = 10;
                int i;
                for (i = mp.getMasaid(); i <= mp.getMasaid(); i++) {
                    JLabel label = new JLabel("masa " + mp.getMasaid());
                    label.setName(label + (i + ""));
                    label.setForeground(Color.green);
                    label.setSize(50, 50);
                    label.setLocation(a, 100);
                    label.setVisible(true);
                    label.setIcon(ResizeImage("img\\restaurant-table-filled.png", null, label));
                    a = a + 75;
                    label.setSize(100, 100);
                    System.out.println("masaid :  " + mp.getMasaid());
                    pnlMasa.add(label);
                    pnlMasa.setLayout(new GridLayout(0, 5));
                    pnlMasa.setSize(801, 121);
                }
                masaModeldoldur();
            }
        } catch (SQLException e) {
            System.out.println("masa getirme hatası:" + e);
        }
    }

    public ImageIcon ResizeImage(String imagePath, byte[] pic, JLabel label) {
        ImageIcon myImage = null;
        if (imagePath != null) {
            myImage = new ImageIcon(imagePath);
        } else {
            myImage = new ImageIcon(pic);
        }
        Image img = myImage.getImage();
        Image img2 = img.getScaledInstance(label.getWidth(), label.getHeight(), Image.SCALE_SMOOTH);
        ImageIcon image = new ImageIcon(img2);
        return image;
    }

    public void masaModeldoldur() {
        DefaultComboBoxModel<String> dkm = new DefaultComboBoxModel<>();
        for (MasaPro item : msp) {
            dkm.addElement(item.getMadi());
        }
        jComboBox7.setModel(dkm);
        jComboBox6.setModel(dkm);
    }

    //kampanya getir
    public void kampanyaDoldur() {
        map.clear();
        try {
            String q = "CALL kampanyaDoldur()";
            ResultSet rs = db.baglan().executeQuery(q);
            while (rs.next()) {
                MenuadiPro mp = new MenuadiPro();
                mp.setMenuid(rs.getInt("" + MenuadiEnum.menuid));
                mp.setMenuadi(rs.getString("" + MenuadiEnum.menuadi));
                mp.setMenubaslangic(rs.getString("" + MenuadiEnum.menubaslangic));
                mp.setMenubitis(rs.getString("" + MenuadiEnum.menubitis));
                mp.setMenufiyat(rs.getFloat("" + MenuadiEnum.menufiyat));
                mp.setMenumaliyet(rs.getFloat("" + MenuadiEnum.menumaliyet));
                mp.setMadet(rs.getInt("" + MenuadiEnum.madet));
                mp.setMenuresim(rs.getString("" + MenuadiEnum.menuresim));
                map.add(mp);
            }
            kampanyaModelDoldur();
        } catch (SQLException e) {
            System.out.println("kampanya getirme hatası:" + e);
        }
    }

    public void kampanyaModelDoldur() {
        DefaultComboBoxModel<String> dkm = new DefaultComboBoxModel<>();
        for (MenuadiPro item : map) {
            dkm.addElement(item.getMenuadi());
        }
        jComboBox1.setModel(dkm);
    }

    public void kampanyaGetir() {
        // map.clear();
        DefaultTableModel dtm = new DefaultTableModel();
        dtm.addColumn("ID");
        dtm.addColumn("Menü Adı");
        dtm.addColumn("Menü Başlangıç");
        dtm.addColumn("Menü Bitiş");
        dtm.addColumn("Fiyat");
        try {
            String q = "call kampanyaGetir()";
            ResultSet rs = db.baglan().executeQuery(q);
            while (rs.next()) {
                dtm.addRow(new Object[]{rs.getInt("" + MenuadiEnum.menuid), rs.getString("" + MenuadiEnum.menuadi),
                    rs.getString("" + MenuadiEnum.menubaslangic), rs.getString("" + MenuadiEnum.menubitis),
                    rs.getString("" + MenuadiEnum.menufiyat)});
            }
            jTable2.setModel(dtm);
        } catch (SQLException e) {
            System.out.println("Kampanya Getirme hatası:" + e);
        }
    }

    public void siparisDoldur() {
        try {
            String q = "call siparisDoldur()";
            ResultSet rs = db.baglan().executeQuery(q);
            while (rs.next()) {
                SiparisPro sp = new SiparisPro();
                sp.setSipid(rs.getInt("" + SiparisEnum.sipid));
                sp.setSubeid(rs.getInt("" + SiparisEnum.subeid));
                sp.setSmasaid(rs.getInt("" + SiparisEnum.smasaid));
                sp.setSgarsonid(rs.getInt("" + SiparisEnum.sgarsonid));
                sp.setSyemekid(rs.getInt("" + SiparisEnum.syemekid));
                sp.setSmenuid(rs.getInt("" + SiparisEnum.smenuid));
                sp.setSadet(rs.getInt("" + SiparisEnum.sadet));
                sp.setMadet(rs.getInt("" + SiparisEnum.madet));
                sp.setSdurum(rs.getString("" + SiparisEnum.sdurum));
                sp.setShazir(rs.getInt("" + SiparisEnum.shazir));
                sip.add(sp);
            }
        } catch (SQLException e) {
            System.err.println("siparis ekleme hatasi:" + e);
        }
    }

    public void adisyon(int masaid) {
        try {
            masaid = msp.get(jComboBox6.getSelectedIndex()).getMasaid();
            String q = ("call adisyon('" + masaid + "')");
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
            float toplamfiyat =0;
            while (rs.next()) {
                dtm.addRow(new Object[]{rs.getInt("masaid"), rs.getString("madi"), rs.getString("yadi"), rs.getInt("sadet"), rs.getInt("satisFiyati"),
                    rs.getString("menuadi"), rs.getInt("madet"), rs.getInt("menufiyat")});
                toplamfiyat = toplamfiyat + rs.getInt("sadet") * rs.getInt("satisFiyati") + rs.getInt("madet") * rs.getInt("menufiyat");
                // System.out.println(toplamfiyat);
            }
            jTable3.setModel(dtm);
            jLabel24.setText(toplamfiyat + "");
        } catch (SQLException e) {
            System.err.println("adisyon hatasi:" + e);
        }
    }

    public void hazirSiparisTabloDoldur() {
        try {
            String q = "call garsonHazirSiparisler()";
            ResultSet rs = db.baglan().executeQuery(q);
            DefaultTableModel dtm = new DefaultTableModel();
            dtm.addColumn("ID");
            dtm.addColumn("MASA ADI");
            dtm.addColumn("GARSON ADI");
            dtm.addColumn("YEMEK ADI");
            dtm.addColumn("Y.ADET");
            dtm.addColumn("KAMPANYA");
            dtm.addColumn("K.ADET");

            while (rs.next()) {
                dtm.addRow(new Object[]{rs.getInt("masaid"), rs.getString("madi"), rs.getString("kullaniciAdi"),
                    rs.getString("yadi"), rs.getInt("sadet"), rs.getString("menuadi"), rs.getInt("madet"),});
            }
            tblHazirSiparisler.setModel(dtm);
        } catch (SQLException e) {
            System.err.println("hazir siparis tablo doldurma hatasi:" + e);
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        pnlMasa = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel28 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        jPanel8 = new javax.swing.JPanel();
        jLabel26 = new javax.swing.JLabel();
        jPanel9 = new javax.swing.JPanel();
        jPanel1 = new javax.swing.JPanel();
        jLabel16 = new javax.swing.JLabel();
        jComboBox4 = new javax.swing.JComboBox<>();
        jLabel15 = new javax.swing.JLabel();
        jComboBox5 = new javax.swing.JComboBox<>();
        jButton6 = new javax.swing.JButton();
        jButton7 = new javax.swing.JButton();
        jLabel17 = new javax.swing.JLabel();
        jLabel19 = new javax.swing.JLabel();
        jLabel20 = new javax.swing.JLabel();
        jLabel18 = new javax.swing.JLabel();
        jPanel7 = new javax.swing.JPanel();
        jLabel8 = new javax.swing.JLabel();
        jComboBox1 = new javax.swing.JComboBox<>();
        jLabel9 = new javax.swing.JLabel();
        jComboBox2 = new javax.swing.JComboBox<>();
        jLabel10 = new javax.swing.JLabel();
        jComboBox3 = new javax.swing.JComboBox<>();
        jLabel11 = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblSiparisler = new javax.swing.JTable();
        jButton2 = new javax.swing.JButton();
        jSpinner2 = new javax.swing.JSpinner();
        jLabel22 = new javax.swing.JLabel();
        jComboBox7 = new javax.swing.JComboBox<>();
        jSpinner1 = new javax.swing.JSpinner();
        jLabel25 = new javax.swing.JLabel();
        jPanel6 = new javax.swing.JPanel();
        jLabel27 = new javax.swing.JLabel();
        jPanel12 = new javax.swing.JPanel();
        jLabel12 = new javax.swing.JLabel();
        jSpinner3 = new javax.swing.JSpinner();
        jLabel13 = new javax.swing.JLabel();
        jSpinner4 = new javax.swing.JSpinner();
        jButton4 = new javax.swing.JButton();
        jPanel5 = new javax.swing.JPanel();
        jLabel21 = new javax.swing.JLabel();
        jScrollPane3 = new javax.swing.JScrollPane();
        jTable3 = new javax.swing.JTable();
        jLabel23 = new javax.swing.JLabel();
        jLabel24 = new javax.swing.JLabel();
        jButton8 = new javax.swing.JButton();
        jComboBox6 = new javax.swing.JComboBox<>();
        jPanel4 = new javax.swing.JPanel();
        jLabel14 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTable2 = new javax.swing.JTable();
        btnResim = new javax.swing.JButton();
        jPanel10 = new javax.swing.JPanel();
        jPanel11 = new javax.swing.JPanel();
        jScrollPane4 = new javax.swing.JScrollPane();
        tblHazirSiparisler = new javax.swing.JTable();
        jButton5 = new javax.swing.JButton();
        jPanel13 = new javax.swing.JPanel();
        jPanel14 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jLabel29 = new javax.swing.JLabel();
        jLabel30 = new javax.swing.JLabel();
        txtKullaniciAdi = new javax.swing.JTextField();
        txtKullaniciSifre = new javax.swing.JPasswordField();
        txtYeniSifre = new javax.swing.JPasswordField();
        jButton9 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setBackground(new java.awt.Color(205, 170, 125));

        pnlMasa.setBackground(new java.awt.Color(205, 170, 125));

        javax.swing.GroupLayout pnlMasaLayout = new javax.swing.GroupLayout(pnlMasa);
        pnlMasa.setLayout(pnlMasaLayout);
        pnlMasaLayout.setHorizontalGroup(
            pnlMasaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        pnlMasaLayout.setVerticalGroup(
            pnlMasaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 121, Short.MAX_VALUE)
        );

        jPanel2.setBackground(new java.awt.Color(205, 170, 125));

        jLabel3.setBackground(new java.awt.Color(255, 165, 79));
        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel3.setText("SİPARİŞ AL");
        jLabel3.setOpaque(true);
        jLabel3.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel3MouseClicked(evt);
            }
        });

        jLabel4.setBackground(new java.awt.Color(255, 165, 0));
        jLabel4.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel4.setText("MASA TAŞI");
        jLabel4.setOpaque(true);
        jLabel4.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel4MouseClicked(evt);
            }
        });

        jLabel5.setBackground(new java.awt.Color(255, 140, 0));
        jLabel5.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel5.setText("ADİSYON");
        jLabel5.setOpaque(true);
        jLabel5.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel5MouseClicked(evt);
            }
        });

        jLabel6.setBackground(new java.awt.Color(255, 127, 36));
        jLabel6.setText("KAMPANYA");
        jLabel6.setOpaque(true);
        jLabel6.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel6MouseClicked(evt);
            }
        });

        jLabel7.setBackground(new java.awt.Color(238, 118, 33));
        jLabel7.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel7.setText("MENÜ");
        jLabel7.setOpaque(true);
        jLabel7.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel7MouseClicked(evt);
            }
        });

        jLabel28.setBackground(new java.awt.Color(210, 105, 30));
        jLabel28.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel28.setText("AŞCI");
        jLabel28.setOpaque(true);
        jLabel28.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel28MouseClicked(evt);
            }
        });

        jLabel1.setBackground(new java.awt.Color(205, 102, 0));
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("AYARLAR");
        jLabel1.setOpaque(true);
        jLabel1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel1MouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 65, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 65, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 65, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 65, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 65, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel28, javax.swing.GroupLayout.PREFERRED_SIZE, 65, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 65, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(20, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(23, 23, 23)
                .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel28, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel3.setLayout(new java.awt.CardLayout());

        jPanel8.setBackground(new java.awt.Color(205, 170, 125));

        jLabel26.setIcon(new javax.swing.ImageIcon(getClass().getResource("/rsm/kahve-1000x600.jpg"))); // NOI18N

        javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel26, javax.swing.GroupLayout.PREFERRED_SIZE, 912, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addComponent(jLabel26)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        jPanel3.add(jPanel8, "card6");

        jPanel9.setBackground(new java.awt.Color(205, 170, 125));

        jPanel1.setBackground(new java.awt.Color(205, 170, 125));

        jLabel16.setText("Yemekler");

        jComboBox4.setBackground(new java.awt.Color(205, 170, 125));
        jComboBox4.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        jLabel15.setText("Kategori");

        jComboBox5.setBackground(new java.awt.Color(205, 170, 125));
        jComboBox5.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        jButton6.setText("Getir");
        jButton6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton6ActionPerformed(evt);
            }
        });

        jButton7.setBackground(new java.awt.Color(205, 170, 125));
        jButton7.setText("Resim");
        jButton7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton7ActionPerformed(evt);
            }
        });

        jLabel17.setText("Fiyat");

        jLabel19.setText("0");

        jLabel20.setText("Açıklama");

        jLabel18.setText("0.0");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(70, 70, 70)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jButton6)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jLabel15)
                                    .addComponent(jLabel16))
                                .addGap(60, 60, 60)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jComboBox4, javax.swing.GroupLayout.PREFERRED_SIZE, 83, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jComboBox5, javax.swing.GroupLayout.PREFERRED_SIZE, 83, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(14, 14, 14)))
                        .addGap(123, 123, 123)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jButton7, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel17)
                                .addGap(26, 26, 26)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel18, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jLabel19, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel20)
                        .addGap(76, 76, 76)))
                .addContainerGap(152, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(49, 49, 49)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel16)
                            .addComponent(jComboBox4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(37, 37, 37)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel15)
                            .addComponent(jComboBox5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(41, 41, 41)
                        .addComponent(jButton7, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(36, 36, 36)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel17)
                    .addComponent(jLabel19))
                .addGap(4, 4, 4)
                .addComponent(jButton6)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel20)
                    .addComponent(jLabel18))
                .addContainerGap(180, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel9Layout = new javax.swing.GroupLayout(jPanel9);
        jPanel9.setLayout(jPanel9Layout);
        jPanel9Layout.setHorizontalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addGap(66, 66, 66)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(381, Short.MAX_VALUE))
        );
        jPanel9Layout.setVerticalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addContainerGap(135, Short.MAX_VALUE)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(47, 47, 47))
        );

        jPanel3.add(jPanel9, "card2");

        jPanel7.setBackground(new java.awt.Color(205, 170, 125));

        jLabel8.setText("Kampanyalar");

        jComboBox1.setBackground(new java.awt.Color(205, 170, 125));
        jComboBox1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        jLabel9.setText("Kategoriler");

        jComboBox2.setBackground(new java.awt.Color(205, 170, 125));
        jComboBox2.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        jComboBox2.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                jComboBox2İtemStateChanged(evt);
            }
        });

        jLabel10.setText("Yemekler");

        jComboBox3.setBackground(new java.awt.Color(205, 170, 125));
        jComboBox3.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        jLabel11.setText("Y.Adet");

        jButton1.setText("Ekle");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        tblSiparisler.setBackground(new java.awt.Color(205, 170, 125));
        tblSiparisler.setModel(new javax.swing.table.DefaultTableModel(
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
        tblSiparisler.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblSiparislerMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tblSiparisler);

        jButton2.setText("Sipariş İptal");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jLabel22.setText("Masa Adı");

        jComboBox7.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        jComboBox7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox7ActionPerformed(evt);
            }
        });

        jLabel25.setText("K.Adet");

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addGap(54, 54, 54)
                        .addComponent(jLabel8))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel7Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel10, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel11, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel9, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel22, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel25, javax.swing.GroupLayout.Alignment.TRAILING))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jButton1)
                    .addComponent(jButton2)
                    .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addComponent(jComboBox7, javax.swing.GroupLayout.Alignment.LEADING, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jComboBox2, 0, 97, Short.MAX_VALUE)
                        .addComponent(jComboBox1, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jComboBox3, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addComponent(jSpinner1, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jSpinner2, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(52, 52, 52)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 432, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(385, Short.MAX_VALUE))
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addGap(42, 42, 42)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 381, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel22)
                            .addComponent(jComboBox7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel8))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jSpinner1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel25))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jComboBox2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel9))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jComboBox3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel10))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jSpinner2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel11))
                        .addGap(39, 39, 39)
                        .addComponent(jButton1)
                        .addGap(18, 18, 18)
                        .addComponent(jButton2)))
                .addContainerGap(177, Short.MAX_VALUE))
        );

        jPanel3.add(jPanel7, "card5");

        jPanel6.setBackground(new java.awt.Color(205, 170, 125));

        jLabel27.setIcon(new javax.swing.ImageIcon(getClass().getResource("/rsm/vector_art_logo_hot_coffee_cup.png"))); // NOI18N

        jPanel12.setBackground(new java.awt.Color(205, 170, 125));

        jLabel12.setText("Taşınacak Masa");

        jLabel13.setText("Yeni Masa");

        jButton4.setText("Hesap Aktar");
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel12Layout = new javax.swing.GroupLayout(jPanel12);
        jPanel12.setLayout(jPanel12Layout);
        jPanel12Layout.setHorizontalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel12Layout.createSequentialGroup()
                .addGap(23, 23, 23)
                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jButton4)
                    .addGroup(jPanel12Layout.createSequentialGroup()
                        .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel13)
                            .addComponent(jLabel12))
                        .addGap(40, 40, 40)
                        .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jSpinner3, javax.swing.GroupLayout.DEFAULT_SIZE, 39, Short.MAX_VALUE)
                            .addComponent(jSpinner4))))
                .addContainerGap(91, Short.MAX_VALUE))
        );
        jPanel12Layout.setVerticalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel12Layout.createSequentialGroup()
                .addGap(36, 36, 36)
                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jSpinner3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel12))
                .addGap(23, 23, 23)
                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel13)
                    .addComponent(jSpinner4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 104, Short.MAX_VALUE)
                .addComponent(jButton4)
                .addGap(100, 100, 100))
        );

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addGap(104, 104, 104)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addGap(235, 235, 235)
                        .addComponent(jLabel27, javax.swing.GroupLayout.PREFERRED_SIZE, 325, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jPanel12, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(428, Short.MAX_VALUE))
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addGap(68, 68, 68)
                        .addComponent(jLabel27, javax.swing.GroupLayout.PREFERRED_SIZE, 288, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addGap(92, 92, 92)
                        .addComponent(jPanel12, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(182, Short.MAX_VALUE))
        );

        jPanel3.add(jPanel6, "card4");

        jPanel5.setBackground(new java.awt.Color(205, 170, 125));

        jLabel21.setText("Masa Adı:");

        jTable3.setBackground(new java.awt.Color(205, 170, 125));
        jTable3.setModel(new javax.swing.table.DefaultTableModel(
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
        jTable3.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTable3MouseClicked(evt);
            }
        });
        jScrollPane3.setViewportView(jTable3);

        jLabel23.setText("Toplam Tutar:");

        jLabel24.setText("0");

        jButton8.setText("Adisyon");
        jButton8.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton8ActionPerformed(evt);
            }
        });

        jComboBox6.setBackground(new java.awt.Color(205, 170, 125));
        jComboBox6.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        jComboBox6.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                jComboBox6İtemStateChanged(evt);
            }
        });
        jComboBox6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox6ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addGap(64, 64, 64)
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel5Layout.createSequentialGroup()
                                .addComponent(jLabel21)
                                .addGap(27, 27, 27)
                                .addComponent(jComboBox6, javax.swing.GroupLayout.PREFERRED_SIZE, 154, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addGap(232, 232, 232)
                        .addComponent(jLabel23)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel24, javax.swing.GroupLayout.PREFERRED_SIZE, 63, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jButton8)))
                .addContainerGap(576, Short.MAX_VALUE))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGap(43, 43, 43)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel21)
                    .addComponent(jComboBox6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 262, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(32, 32, 32)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel23)
                    .addComponent(jLabel24)
                    .addComponent(jButton8))
                .addContainerGap(202, Short.MAX_VALUE))
        );

        jPanel3.add(jPanel5, "card3");

        jPanel4.setBackground(new java.awt.Color(205, 170, 125));

        jLabel14.setText("Kampanyalar");

        jTable2.setBackground(new java.awt.Color(205, 170, 125));
        jTable2.setModel(new javax.swing.table.DefaultTableModel(
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
        jTable2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTable2MouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(jTable2);

        btnResim.setBackground(new java.awt.Color(205, 170, 125));
        btnResim.setText("Resim");

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGap(196, 196, 196)
                        .addComponent(jLabel14))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGap(54, 54, 54)
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(90, 90, 90)
                        .addComponent(btnResim, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(396, Short.MAX_VALUE))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(58, 58, 58)
                .addComponent(jLabel14)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGap(28, 28, 28)
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 225, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGap(64, 64, 64)
                        .addComponent(btnResim, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(275, Short.MAX_VALUE))
        );

        jPanel3.add(jPanel4, "card2");

        jPanel10.setBackground(new java.awt.Color(205, 170, 125));

        jPanel11.setBackground(new java.awt.Color(205, 170, 125));
        jPanel11.setBorder(javax.swing.BorderFactory.createTitledBorder("Hazırlanan Siparişler"));

        tblHazirSiparisler.setBackground(new java.awt.Color(205, 170, 125));
        tblHazirSiparisler.setModel(new javax.swing.table.DefaultTableModel(
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
        tblHazirSiparisler.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblHazirSiparislerMouseClicked(evt);
            }
        });
        jScrollPane4.setViewportView(tblHazirSiparisler);

        jButton5.setText("Alındı");
        jButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton5ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel11Layout = new javax.swing.GroupLayout(jPanel11);
        jPanel11.setLayout(jPanel11Layout);
        jPanel11Layout.setHorizontalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel11Layout.createSequentialGroup()
                .addContainerGap(105, Short.MAX_VALUE)
                .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(104, 104, 104))
            .addGroup(jPanel11Layout.createSequentialGroup()
                .addGap(302, 302, 302)
                .addComponent(jButton5)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel11Layout.setVerticalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel11Layout.createSequentialGroup()
                .addGap(27, 27, 27)
                .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 311, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jButton5)
                .addContainerGap(68, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel10Layout = new javax.swing.GroupLayout(jPanel10);
        jPanel10.setLayout(jPanel10Layout);
        jPanel10Layout.setHorizontalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addGap(26, 26, 26)
                .addComponent(jPanel11, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(393, Short.MAX_VALUE))
        );
        jPanel10Layout.setVerticalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addGap(27, 27, 27)
                .addComponent(jPanel11, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(110, Short.MAX_VALUE))
        );

        jPanel3.add(jPanel10, "card6");

        jPanel13.setBackground(new java.awt.Color(205, 170, 125));

        jPanel14.setBackground(new java.awt.Color(205, 170, 125));
        jPanel14.setBorder(javax.swing.BorderFactory.createTitledBorder("Şifre  İşlemleri"));

        jLabel2.setText("Kullanıcı Adı:");

        jLabel29.setText("Kullanıcı Şifre:");

        jLabel30.setText("Yeni Şifre:");

        jButton9.setText("Değiştir");
        jButton9.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton9ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel14Layout = new javax.swing.GroupLayout(jPanel14);
        jPanel14.setLayout(jPanel14Layout);
        jPanel14Layout.setHorizontalGroup(
            jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel14Layout.createSequentialGroup()
                .addGap(80, 80, 80)
                .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jButton9)
                    .addGroup(jPanel14Layout.createSequentialGroup()
                        .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel30)
                            .addComponent(jLabel29)
                            .addComponent(jLabel2))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(txtKullaniciAdi)
                            .addComponent(txtKullaniciSifre)
                            .addComponent(txtYeniSifre, javax.swing.GroupLayout.DEFAULT_SIZE, 157, Short.MAX_VALUE))))
                .addContainerGap(223, Short.MAX_VALUE))
        );
        jPanel14Layout.setVerticalGroup(
            jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel14Layout.createSequentialGroup()
                .addGap(53, 53, 53)
                .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(txtKullaniciAdi, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(28, 28, 28)
                .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel29)
                    .addComponent(txtKullaniciSifre, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(33, 33, 33)
                .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtYeniSifre, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel30))
                .addGap(60, 60, 60)
                .addComponent(jButton9)
                .addContainerGap(139, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel13Layout = new javax.swing.GroupLayout(jPanel13);
        jPanel13.setLayout(jPanel13Layout);
        jPanel13Layout.setHorizontalGroup(
            jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel13Layout.createSequentialGroup()
                .addGap(117, 117, 117)
                .addComponent(jPanel14, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(114, Short.MAX_VALUE))
        );
        jPanel13Layout.setVerticalGroup(
            jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel13Layout.createSequentialGroup()
                .addGap(82, 82, 82)
                .addComponent(jPanel14, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(99, Short.MAX_VALUE))
        );

        jPanel3.add(jPanel13, "card6");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, 779, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addComponent(pnlMasa, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(pnlMasa, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jLabel3MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel3MouseClicked

        jPanel3.removeAll();
        jPanel3.repaint();
        jPanel3.revalidate();
        jPanel3.add(jPanel7);
        jPanel3.repaint();
        jPanel3.revalidate();;
    }//GEN-LAST:event_jLabel3MouseClicked

    private void jLabel4MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel4MouseClicked

        jPanel3.removeAll();
        jPanel3.repaint();
        jPanel3.revalidate();
        jPanel3.add(jPanel6);
        jPanel3.repaint();
        jPanel3.revalidate();
    }//GEN-LAST:event_jLabel4MouseClicked

    private void jLabel5MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel5MouseClicked

        jPanel3.removeAll();
        jPanel3.repaint();
        jPanel3.revalidate();
        jPanel3.add(jPanel5);
        jPanel3.repaint();
        jPanel3.revalidate();
    }//GEN-LAST:event_jLabel5MouseClicked

    private void jLabel6MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel6MouseClicked

        jPanel3.removeAll();
        jPanel3.repaint();
        jPanel3.revalidate();
        jPanel3.add(jPanel4);
        jPanel3.repaint();
        jPanel3.revalidate();
    }//GEN-LAST:event_jLabel6MouseClicked

    private void jLabel7MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel7MouseClicked

        jPanel3.removeAll();
        jPanel3.repaint();
        jPanel3.revalidate();
        jPanel3.add(jPanel9);
        jPanel3.repaint();
        jPanel3.revalidate();

    }//GEN-LAST:event_jLabel7MouseClicked

    //garson kategori tıklanan id tutmak için
    private void jComboBox2İtemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_jComboBox2İtemStateChanged
        int katid = ktp.get(jComboBox2.getSelectedIndex()).getKatid();
        // System.out.println("katid " + katid);
        yemekDoldur(katid);
    }//GEN-LAST:event_jComboBox2İtemStateChanged


    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed

        int shazir = 0;
        //spnner 1=kamp menu adet,spınner 2=ymk adet
        int subeid = 1;
       int sgarsonid=new Login().kullanici;
                
        String durum = "aktif";
        int masaid = msp.get(jComboBox7.getSelectedIndex()).getMasaid();
        int menuid = map.get(jComboBox1.getSelectedIndex()).getMenuid();
        //int yemekid=ymp.get(jComboBox3.getSelectedIndex()).getYemekid();
        //String adet = "";
        Integer myint = (Integer) jSpinner2.getValue();
        //adet = myint.toString();

        Integer kint = (Integer) jSpinner1.getValue();
        // float mfiyat=

//        String fiyat = ymp.get(jTable2.getSelectedRow()).getMenuresim();
//        //  System.out.println("resim id geldi " + resimid);
//        //System.out.println("resim url = " +resim);
//        
//        jButton5.setIcon(new ImageIcon(resim));
        int yemekid = 1;
        if (jComboBox3.getSelectedIndex() > -1) {
            yemekid = ymp.get(jComboBox3.getSelectedIndex()).getYemekid();
        }

        try {
            String q = "{Call siparisEkle(?,?,?,?,?,?,?,?,?,now()) }";
            PreparedStatement pr = db.preBaglan(q);
            pr.setInt(1, subeid);
            pr.setInt(2, masaid);
            pr.setInt(3, sgarsonid);
            pr.setInt(4, yemekid);
            pr.setInt(5, menuid);
            pr.setInt(6, myint);
            pr.setInt(7, kint);
            pr.setString(8, durum);
            pr.setInt(9, shazir);

            int yaz = pr.executeUpdate();
            if (yaz > 0) {
                siparisTabloDoldur();
                adisyon(masaid);

            }

        } catch (SQLException e) {
            System.out.println("yazma hatası:" + e);
        }

    }//GEN-LAST:event_jButton1ActionPerformed

    private void jComboBox6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox6ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jComboBox6ActionPerformed

    private void jComboBox6İtemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_jComboBox6İtemStateChanged

        int masaid = msp.get(jComboBox6.getSelectedIndex()).getMasaid();
        // adisyonGetir(masaid);
        adisyon(masaid);
    }//GEN-LAST:event_jComboBox6İtemStateChanged

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed

        //spinner kullanımı
        // String tasinacakmasa = "";
        Integer tasinacakmasa = (Integer) jSpinner3.getValue();
        //  tasinacakmasa = myint.t();
        System.out.println("tasinacakmasa : " + tasinacakmasa);

        //  String yenimasa = "";
        Integer yenimasa = (Integer) jSpinner4.getValue();
        //yenimasa = myintt.toString();
        System.out.println("yenimasa : " + yenimasa);

        try {

            String q = ("call siparisTasima(?,?)");
            PreparedStatement ps = db.preBaglan(q);
            ps.setInt(1, tasinacakmasa);
            ps.setInt(2, yenimasa);
            int yaz = ps.executeUpdate();
            if (yaz > 0) {
                JOptionPane.showMessageDialog(rootPane,"Hesap aktarımı başarıyla gerçekleşmiştir");
                System.out.println("masa taşıma başarılıdır.");
            }

        } catch (Exception e) {
            System.err.println("masa taşıma hatası : " + e);
        }

    }//GEN-LAST:event_jButton4ActionPerformed

    private void jTable2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTable2MouseClicked

        String yemekResim = map.get(jTable2.getSelectedRow()).getMenuresim();
        //  System.out.println("resim id geldi " + resimid);
       
        btnResim.setIcon(new ImageIcon(yemekResim));


    }//GEN-LAST:event_jTable2MouseClicked

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed

        
        try {
            String q = ("call siparisIptal('" + sipid + "')");System.out.println("sipid"+sipid);
            PreparedStatement ps = db.preBaglan(q);
            int yaz = ps.executeUpdate();
            if (yaz > 0) {
                System.out.println("silme işlemi başarılı");
                siparisTabloDoldur();
            }

        } catch (Exception e) {
            System.err.println("Silme hatası : " + e);

        }
    }//GEN-LAST:event_jButton2ActionPerformed

    int sipid = -1;
    private void tblSiparislerMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblSiparislerMouseClicked

        sipid = sip.get(tblSiparisler.getSelectedRow()).getSipid();
        System.out.println("sipid: " + sipid);
    }//GEN-LAST:event_tblSiparislerMouseClicked

    private void jButton8ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton8ActionPerformed

        try {
            String q = "call durumGuncelle('" + masaid + "')";
            PreparedStatement ps = db.preBaglan(q);
            int yaz = ps.executeUpdate();
            if (yaz > 0) {
                System.out.println("durum  güncellendi");
                adisyon(masaid);
            }
        } catch (Exception e) {
            System.out.println("durum güncelleme hatası:");
        }
    }//GEN-LAST:event_jButton8ActionPerformed

    private void jButton6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton6ActionPerformed

        String resim = ymp.get(jComboBox5.getSelectedIndex()).getResim();
        System.out.println("resim " + resim);
        jButton7.setIcon(new ImageIcon(resim));
        String yemekFiyat = String.valueOf(ymp.get(jComboBox5.getSelectedIndex()).getSatisfiyati());
        jLabel19.setText(yemekFiyat + "TL");
        String aciklama=ymp.get(jComboBox5.getSelectedIndex()).getAciklama();
        jLabel18.setText(aciklama);

    }//GEN-LAST:event_jButton6ActionPerformed

    private void jComboBox7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox7ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jComboBox7ActionPerformed

    int masaid;
    private void jTable3MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTable3MouseClicked

        masaid = Integer.valueOf(jTable3.getValueAt(jTable3.getSelectedRow(), 0) + "");
    }//GEN-LAST:event_jTable3MouseClicked

    private void jButton7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton7ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton7ActionPerformed

    private void jLabel28MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel28MouseClicked

        jPanel3.removeAll();
        jPanel3.repaint();
        jPanel3.revalidate();
        jPanel3.add(jPanel10);
        jPanel3.repaint();
        jPanel3.revalidate();;
    }//GEN-LAST:event_jLabel28MouseClicked

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed

        try {
            String q = "call garsonHaziriGordu('" + alindi + "')";
            PreparedStatement pr = db.preBaglan(q);
            int yaz = pr.executeUpdate();
            if (yaz > 0) {
                System.out.println("garson haziri gordu basarili");
                hazirSiparisTabloDoldur();
            }
        } catch (Exception e) {
        }


    }//GEN-LAST:event_jButton5ActionPerformed

    int alindi;
    private void tblHazirSiparislerMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblHazirSiparislerMouseClicked

        alindi = Integer.valueOf(tblHazirSiparisler.getValueAt(tblHazirSiparisler.getSelectedRow(), 0) + "");

    }//GEN-LAST:event_tblHazirSiparislerMouseClicked

    private void jLabel1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel1MouseClicked

         jPanel3.removeAll();
        jPanel3.repaint();
        jPanel3.revalidate();
        jPanel3.add(jPanel13);
        jPanel3.repaint();
        jPanel3.revalidate();;
    }//GEN-LAST:event_jLabel1MouseClicked

    static int gorevid=-1;
  static int kullanici=-1;
  static String adi,sifresi=null;
    private void jButton9ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton9ActionPerformed
      
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
    }//GEN-LAST:event_jButton9ActionPerformed

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
            tblSiparisler.setModel(dtm);
        } catch (Exception e) {
        }
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
            java.util.logging.Logger.getLogger(garson.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(garson.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(garson.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(garson.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new garson().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnResim;
    public javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
    private javax.swing.JButton jButton6;
    private javax.swing.JButton jButton7;
    private javax.swing.JButton jButton8;
    private javax.swing.JButton jButton9;
    private javax.swing.JComboBox<String> jComboBox1;
    private javax.swing.JComboBox<String> jComboBox2;
    private javax.swing.JComboBox<String> jComboBox3;
    private javax.swing.JComboBox<String> jComboBox4;
    private javax.swing.JComboBox<String> jComboBox5;
    private javax.swing.JComboBox<String> jComboBox6;
    private javax.swing.JComboBox<String> jComboBox7;
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
    private javax.swing.JLabel jLabel2;
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
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanel11;
    private javax.swing.JPanel jPanel12;
    private javax.swing.JPanel jPanel13;
    private javax.swing.JPanel jPanel14;
    private javax.swing.JPanel jPanel2;
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
    private javax.swing.JSpinner jSpinner1;
    public javax.swing.JSpinner jSpinner2;
    private javax.swing.JSpinner jSpinner3;
    private javax.swing.JSpinner jSpinner4;
    private javax.swing.JTable jTable2;
    private javax.swing.JTable jTable3;
    public static javax.swing.JPanel pnlMasa;
    private javax.swing.JTable tblHazirSiparisler;
    private javax.swing.JTable tblSiparisler;
    private javax.swing.JTextField txtKullaniciAdi;
    private javax.swing.JPasswordField txtKullaniciSifre;
    private javax.swing.JPasswordField txtYeniSifre;
    // End of variables declaration//GEN-END:variables
}
