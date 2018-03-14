
import Enums.AsciLabelYazdirEnum;
import Enums.MasaEnum;
import Enums.MenuadiEnum;
import Enums.SiparisEnum;
import Enums.YemekEnum;
import Properties.AsciLabelYazdirPro;
import Properties.MasaPro;
import Properties.MenuadiPro;
import Properties.SiparisPro;
import Properties.YemekPro;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.TimerTask;
import java.util.Timer;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.plaf.basic.BasicArrowButton;
import javax.swing.table.DefaultTableModel;

public class Mutfak extends javax.swing.JFrame {

    ArrayList<MenuadiPro> map = new ArrayList<>();
    ArrayList<YemekPro> ymp = new ArrayList<>();
    ArrayList<MasaPro> msp = new ArrayList<>();
    ArrayList<SiparisPro> sip = new ArrayList<>();
    ArrayList<AsciLabelYazdirPro> aly = new ArrayList<>();
    DB db = new DB("cafehouse", "root", "");

    public Mutfak() {
        initComponents();
        Asci();
        siparisEkranı();
        masaDoldur();
        asciSiparisGoruntu(secim);
        yemekDoldur();
        kampanyaDoldur();

    }

    public void Asci() {
        //siparisEkranı();

        TimerTask asci = new TimerTask() {
            @Override
            public void run() {
                siparisEkranı();
            }
        };
        Timer tm = new Timer();
        tm.schedule(asci, 0, 5000);

    }

    public void siparisEkranı() {
        try {
            String q = "call asciPanel()";
            ResultSet rs = db.baglan().executeQuery(q);
            DefaultTableModel dtm = new DefaultTableModel();
            dtm.addColumn("ID");
            dtm.addColumn("MASA ADI");
            dtm.addColumn("YEMEK ADI");
            dtm.addColumn("Y.ADET");
            dtm.addColumn("KAMPANYA");
            dtm.addColumn("K.ADET");
            while (rs.next()) {
                dtm.addRow(new Object[]{rs.getInt("masaid"), rs.getString("madi"), rs.getString("yadi"), rs.getInt("sadet"),
                    rs.getString("menuadi"), rs.getInt("madet")});
            }
            jTable1.setModel(dtm);

        } catch (Exception e) {
            System.out.println("");
        }
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
            }

        } catch (SQLException e) {
            System.out.println("masa getirme hatası:" + e);
        }
    }
    int i = 75;

    public void asciSiparisGoruntu(int secim) {
        try {
            System.out.println("secim : " + secim);
            jPanel2.removeAll();
            String q = "call asciSiparisGoruntu('" + secim + "')";
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
                sip.add(sp);

//                JButton button = new JButton("Yemek " + rs.getInt("" + SiparisEnum.smasaid));
                System.out.println("aaaaaaaaaaaaaaaaaaaaaaaaa");
                if (rs.getString("menuresim") != null) {
                    JButton button = new JButton();
                    button.setIcon(new ImageIcon(rs.getString("menuresim")));
                    System.out.println("resim " + rs.getString("menuresim"));
                    button.setSize(50, 50);
                    button.setLocation(i, 0);
                    button.setVisible(true);

//                button.setText(rs.getString("menuresin"));
                    i = i + 75;
                    button.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            // JOptionPane.showMessageDialog(rootPane, "tıklandı : " + button.getText());
                        }
                    });

                    jPanel2.add(button);
                }
                    if (rs.getString("resim") != null) {
                    JButton button = new JButton();
                    button.setIcon(new ImageIcon(rs.getString("resim")));
                    System.out.println("resim " + rs.getString("resim"));
                    button.setSize(50, 50);
                    button.setLocation(i, 0);
                    button.setVisible(true);

//                button.setText(rs.getString("menuresin"));
                    i = i + 75;
                    button.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            // JOptionPane.showMessageDialog(rootPane, "tıklandı : " + button.getText());
                        }
                    });

                    jPanel2.add(button);
                }
                
                
                
                
                
                

            }

            jPanel2.setSize(600, 400);
            jPanel2.setLayout(new GridLayout(0, 5));
            this.invalidate();
            this.validate();
            this.repaint();

        } catch (Exception e) {
        }
    }

    // yemekler
    public void yemekDoldur() {
        ymp.clear();
        try {
            String q = "CALL asciYemekGetir()";
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

        } catch (SQLException e) {
            System.err.println("yemek getirme hatasi:" + e);
        }
    }
//    public void yemekModelDoldur(){
//        for (YemekPro item : ymp) {
//            lblYemekAdi.setText(item.getYadi());
//        }
//    }

    //kampanya getir
    public void kampanyaDoldur() {
        map.clear();
        try {
            String q = "CALL asciMenuGetir()";
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
        } catch (SQLException e) {
            System.out.println("kampanya getirme hatası:" + e);
        }
    }

   

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jPanel3 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        lblMasaAdi = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        lblYemekAdi = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        lblMenuAdi = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jButton1 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder("Sipariş Ekranı"));
        jPanel1.setPreferredSize(new java.awt.Dimension(300, 500));

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
        jTable1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTable1MouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(jTable1);

        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder("Siparişler"));

        jLabel1.setText("MASA ADI:");

        lblMasaAdi.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N

        jLabel3.setText("YEMEK ADI:");

        lblYemekAdi.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N

        jLabel5.setText("ADET:");

        jLabel6.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N

        jLabel7.setText("MENU:");

        lblMenuAdi.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N

        jLabel9.setText("ADET:");

        jLabel10.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(31, 31, 31)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel7)
                    .addComponent(jLabel3)
                    .addComponent(jLabel1))
                .addGap(18, 18, 18)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(lblMasaAdi, javax.swing.GroupLayout.PREFERRED_SIZE, 77, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblYemekAdi, javax.swing.GroupLayout.DEFAULT_SIZE, 139, Short.MAX_VALUE)
                    .addComponent(lblMenuAdi, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel5, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel9, javax.swing.GroupLayout.Alignment.TRAILING))
                .addGap(45, 45, 45)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 58, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 58, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(35, 35, 35))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(26, 26, 26)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 14, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel1)
                            .addComponent(lblMasaAdi, javax.swing.GroupLayout.PREFERRED_SIZE, 14, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(lblYemekAdi, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addComponent(jLabel5))
                .addGap(18, 18, 18)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(jLabel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(lblMenuAdi, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jLabel10, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 14, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel9, javax.swing.GroupLayout.Alignment.TRAILING)))
                .addContainerGap(62, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(27, 27, 27)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jScrollPane1)
                    .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(37, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 206, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(34, 34, 34)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(21, 21, 21))
        );

        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder("Sipariş İçeriği"));

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 603, Short.MAX_VALUE)
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 461, Short.MAX_VALUE)
        );

        jButton1.setBackground(new java.awt.Color(204, 0, 0));
        jButton1.setText("SİPARİŞ HAZIR");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 528, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 50, Short.MAX_VALUE))
            .addGroup(layout.createSequentialGroup()
                .addGap(107, 107, 107)
                .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 1007, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 484, Short.MAX_VALUE)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jButton1)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    int secim;
    private void jTable1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTable1MouseClicked

        //secimi al prosedur yaz siparis tablosundan çek labellere bas
        secim = Integer.valueOf(jTable1.getValueAt(jTable1.getSelectedRow(), 0) + "");
        System.out.println("secim" + secim);
        String resim = "" + secim;
        // jButton2.setIcon(resim);
        asciSiparisGoruntu(secim);
       // System.out.println("secim:"+secim);
        try {
            String q = "call asciLabelYazdir('" + secim + "')";
            ResultSet rs = db.baglan().executeQuery(q);
            while (rs.next()) {
                AsciLabelYazdirPro as = new AsciLabelYazdirPro();
                as.setMasaid(rs.getInt("" + AsciLabelYazdirEnum.masaid));
                as.setYadi(rs.getString("" + AsciLabelYazdirEnum.yadi));
                as.setSadet(rs.getInt("" + AsciLabelYazdirEnum.sadet));
                as.setMadet(rs.getInt("" + AsciLabelYazdirEnum.madet));
                as.setMenuadi(rs.getString("" + AsciLabelYazdirEnum.menuadi));
                as.setResim(rs.getString("" + AsciLabelYazdirEnum.resim));
                as.setMenuresim(rs.getString("" + AsciLabelYazdirEnum.menuresim));
                as.setMadi(rs.getString("" + AsciLabelYazdirEnum.madi));
                aly.add(as);
            }
            lblMasaAdi.setText(aly.get(jTable1.getSelectedRow()).getMadi());
            lblYemekAdi.setText(aly.get(jTable1.getSelectedRow()).getYadi());
            jLabel6.setText(aly.get(jTable1.getSelectedRow()).getSadet() + "");
            lblMenuAdi.setText(aly.get(jTable1.getSelectedRow()).getMenuadi());
            jLabel10.setText(aly.get(jTable1.getSelectedRow()).getMadet() + "");
        } catch (Exception e) {
        }

    }//GEN-LAST:event_jTable1MouseClicked

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed

        try {
            String q = "call asciSşparisHazir('" + secim + "')";
            PreparedStatement ps = db.preBaglan(q);
            int yaz = ps.executeUpdate();
            if (yaz > 0) {
                System.out.println("durum  güncellendi");
                JOptionPane.showMessageDialog(rootPane,"sipariş hazır");
            }
        } catch (Exception e) {
            System.out.println("durum güncelleme hatası:");
        }


    }//GEN-LAST:event_jButton1ActionPerformed

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
            java.util.logging.Logger.getLogger(Mutfak.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Mutfak.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Mutfak.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Mutfak.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Mutfak().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable1;
    private javax.swing.JLabel lblMasaAdi;
    private javax.swing.JLabel lblMenuAdi;
    private javax.swing.JLabel lblYemekAdi;
    // End of variables declaration//GEN-END:variables
}
