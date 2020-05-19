import java.awt.*;
import java.awt.Dimension;
import javax.swing.*;
import java.awt.event.*;
import java.util.*;
 
public class MineFieldClass extends JFrame implements ActionListener, ContainerListener {
 
    int fw, fh, satir_block, sütun_block, var1, var2, mayin_sayisi, bulunan_mayin = 0, savedlevel = 1,
            kaydedilen_satir_block, kaydedilen_sütün_block, kaydedilen_mayin_sayisi = 10;
    int[] r = {-1, -1, -1, 0, 1, 1, 1, 0};
    int[] c = {-1, 0, 1, 1, 1, 0, -1, -1};
    JButton[][] bloklar;
    int[][] mayin_sayaci;
    int[][] renk;
    ImageIcon[] ic = new ImageIcon[14];
    JPanel panelb = new JPanel();
    JPanel panelmt = new JPanel();
    JTextField tf_mayin, tf_zaman;
    JButton reset = new JButton("");
    Random randomsatir = new Random();
    Random randomsütun = new Random();
    boolean check = true, starttime = false;
    Point framelocation;
    Stopwatch sw;
    MouseHendeler mh;
    Point p;
 
    MineFieldClass() {
        super("Mayin Tarlası Oyunu");
        setLocation(400, 300);
 
        setic();
        setpanel(1, 0, 0, 0);
        setmanuel();
 
        sw = new Stopwatch();
 
        reset.addActionListener(new ActionListener() {
 
            public void actionPerformed(ActionEvent ae) {
                try {
                    sw.stop();
                    setpanel(savedlevel, kaydedilen_satir_block, kaydedilen_sütün_block, kaydedilen_mayin_sayisi);
                } catch (Exception ex) {
                    setpanel(savedlevel, kaydedilen_satir_block, kaydedilen_sütün_block, kaydedilen_mayin_sayisi);
                }
                reset();
 
            }
        });
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        show();
    }
 
    public void reset() {
        check = true;
        starttime = false;
        for (int i = 0; i < satir_block; i++) {
            for (int j = 0; j < sütun_block; j++) {
                renk[i][j] = 'w';
            }
        }
    }
 
    public void setpanel(int level, int setr, int setc, int setm) {
        if (level == 1) {
            fw = 300;
            fh = 400;
            satir_block = 15;
            sütun_block = 15;
            mayin_sayisi = 10;
        } else if (level == 2) {
            fw = 400;
            fh = 500;
            satir_block = 20;
            sütun_block = 20;
            mayin_sayisi = 80;
        } else if (level == 3) {
            fw = 500;
            fh = 600;
            satir_block = 30;
            sütun_block = 30;
            mayin_sayisi = 100;
        } else if (level == 4) {
            fw = (20 * setc);
            fh = (24 * setr);
            satir_block = setr;
            sütun_block = setc;
            mayin_sayisi = setm;
        }
 
        kaydedilen_satir_block = satir_block;
        kaydedilen_sütün_block = sütun_block;
        kaydedilen_mayin_sayisi = mayin_sayisi;
 
        setSize(fw, fh);
        setResizable(false);
        bulunan_mayin = mayin_sayisi;
        p = this.getLocation();
 
        bloklar = new JButton[satir_block][sütun_block];
        mayin_sayaci = new int[satir_block][sütun_block];
        renk = new int[satir_block][sütun_block];
        mh = new MouseHendeler();
 
        getContentPane().removeAll();
        panelb.removeAll();
 
        tf_mayin = new JTextField("" + mayin_sayisi, 3);
        tf_mayin.setEditable(false);
        tf_mayin.setFont(new Font("DigtalFont.TTF", Font.BOLD, 25));
        tf_mayin.setBackground(Color.BLACK);
        tf_mayin.setForeground(Color.RED);
        tf_mayin.setBorder(BorderFactory.createLoweredBevelBorder());
        tf_zaman = new JTextField("000", 3);
        tf_zaman.setEditable(false);
        tf_zaman.setFont(new Font("DigtalFont.TTF", Font.BOLD, 25));
        tf_zaman.setBackground(Color.BLACK);
        tf_zaman.setForeground(Color.RED);
        tf_zaman.setBorder(BorderFactory.createLoweredBevelBorder());
        reset.setIcon(ic[11]);
        reset.setBorder(BorderFactory.createLoweredBevelBorder());
 
        panelmt.removeAll();
        panelmt.setLayout(new BorderLayout());
        panelmt.add(tf_mayin, BorderLayout.WEST);
        panelmt.add(reset, BorderLayout.CENTER);
        panelmt.add(tf_zaman, BorderLayout.EAST);
        panelmt.setBorder(BorderFactory.createLoweredBevelBorder());
 
        panelb.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10), BorderFactory.createLoweredBevelBorder()));
        panelb.setPreferredSize(new Dimension(fw, fh));
        panelb.setLayout(new GridLayout(0, sütun_block));
        panelb.addContainerListener(this);
 
        for (int i = 0; i < satir_block; i++) {
            for (int j = 0; j < sütun_block; j++) {
                bloklar[i][j] = new JButton("");
 
                bloklar[i][j].addMouseListener(mh);
 
                panelb.add(bloklar[i][j]);
 
            }
        }
        reset();
 
        panelb.revalidate();
        panelb.repaint();
         
        getContentPane().setLayout(new BorderLayout());
        getContentPane().addContainerListener(this);
        getContentPane().repaint();
        getContentPane().add(panelb, BorderLayout.CENTER);
        getContentPane().add(panelmt, BorderLayout.NORTH);
        setVisible(true);
    }
 
    public void setmanuel() {
        JMenuBar bar = new JMenuBar();
 
        JMenu game = new JMenu("MineField Oyna");
 
        JMenuItem menuitem = new JMenuItem("Yeni Oyun");
        final JCheckBoxMenuItem beginner = new JCheckBoxMenuItem("Başlangıç");
        final JCheckBoxMenuItem intermediate = new JCheckBoxMenuItem("Orta Düzey");
        final JCheckBoxMenuItem expert = new JCheckBoxMenuItem("Üst Düzey");
        final JCheckBoxMenuItem custom = new JCheckBoxMenuItem("Kendin Ayarla");
        final JMenuItem exit = new JMenuItem("Çıkış");
        final JMenu help = new JMenu("*-*");
        final JMenuItem helpitem = new JMenuItem("( •_- )");
 
        ButtonGroup status = new ButtonGroup();
 
        menuitem.addActionListener(
                new ActionListener() {
 
                    public void actionPerformed(ActionEvent e) {
 
                        setpanel(1, 0, 0, 0);
                       
                    }
                });
 
        beginner.addActionListener(
                new ActionListener() {
 
                    public void actionPerformed(ActionEvent e) {
                        panelb.removeAll();
                        reset();
                        setpanel(1, 0, 0, 0);
                        panelb.revalidate();
                        panelb.repaint();
                        beginner.setSelected(true);
                        savedlevel = 1;
                    }
                });
        intermediate.addActionListener(
                new ActionListener() {
 
                    public void actionPerformed(ActionEvent e) {
                        panelb.removeAll();
                        reset();
                        setpanel(2, 0, 0, 0);
                        panelb.revalidate();
                        panelb.repaint();
                        intermediate.setSelected(true);
                        savedlevel = 2;
                    }
                });
        expert.addActionListener(
                new ActionListener() {
 
                    public void actionPerformed(ActionEvent e) {
                        panelb.removeAll();
                        reset();
                        setpanel(3, 0, 0, 0);
                        panelb.revalidate();
                        panelb.repaint();
                        expert.setSelected(true);
                        savedlevel = 3;
                    }
                });
 
        custom.addActionListener(
                new ActionListener() {
 
                    public void actionPerformed(ActionEvent e) {
                      
                        Customizetion cust = new Customizetion();
                        reset();
                        panelb.revalidate();
                        panelb.repaint();
 
                        custom.setSelected(true);
                        savedlevel = 4;
                    }
                });
 
        exit.addActionListener(new ActionListener() {
 
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
 
        helpitem.addActionListener(new ActionListener() {
 
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(null, "Salih ÖZER \n 190541082");
 
            }
        });
 
        setJMenuBar(bar);
 
        status.add(beginner);
        status.add(intermediate);
        status.add(expert);
        status.add(custom);
 
        game.add(menuitem);
        game.addSeparator();
        game.add(beginner);
        game.add(intermediate);
        game.add(expert);
        game.add(custom);
        game.addSeparator();
        game.add(exit);
        help.add(helpitem);
 
        bar.add(game);
        bar.add(help);
 
    }
 
    public void componentAdded(ContainerEvent ce) {
    }
 
    public void componentRemoved(ContainerEvent ce) {
    }
 
    public void actionPerformed(ActionEvent ae) {
    }
 
    class MouseHendeler extends MouseAdapter {
 
        public void mouseClicked(MouseEvent me) {
            if (check == true) {
                for (int i = 0; i < satir_block; i++) {
                    for (int j = 0; j < sütun_block; j++) {
                        if (me.getSource() == bloklar[i][j]) {
                            var1 = i;
                            var2 = j;
                            i = satir_block;
                            break;
                        }
                    }
                }
 
                setmine();
                hesaplama();
                check = false;
 
            }
 
            degerigöster(me);
            kazanan();
 
            if (starttime == false) {
                sw.Start();
                starttime = true;
            }
 
        }
    }
 
    public void kazanan() {
        int q = 0;
        for (int k = 0; k < satir_block; k++) {
            for (int l = 0; l < sütun_block; l++) {
                if (renk[k][l] == 'w') {
                    q = 1;
                }
            }
        }
 
        if (q == 0) {
            
            for (int k = 0; k < satir_block; k++) {
                for (int l = 0; l < sütun_block; l++) {
                    bloklar[k][l].removeMouseListener(mh);
                }
            }
 
            sw.stop();
            JOptionPane.showMessageDialog(this, "Kazandınız Tebrikler!!");
        }
    }
 
    public void degerigöster(MouseEvent e) {
        for (int i = 0; i < satir_block; i++) {
            for (int j = 0; j < sütun_block; j++) {
 
                if (e.getSource() == bloklar[i][j]) {
                    if (e.isMetaDown() == false) {
                        if (bloklar[i][j].getIcon() == ic[10]) {
                            if (bulunan_mayin < mayin_sayisi) {
                                bulunan_mayin++;
                            }
                            tf_mayin.setText("" + bulunan_mayin);
                        }
 
                        if (mayin_sayaci[i][j] == -1) {
                            for (int k = 0; k < satir_block; k++) {
                                for (int l = 0; l < sütun_block; l++) {
                                    if (mayin_sayaci[k][l] == -1) {
 
                                        bloklar[k][l].setIcon(ic[9]);
                                        
                                        bloklar[k][l].removeMouseListener(mh);
                                    }
                                    bloklar[k][l].removeMouseListener(mh);
                                }
                            }
                            sw.stop();
                            reset.setIcon(ic[12]);
                            JOptionPane.showMessageDialog(null, "Kaybettiniz, Tekrar Deneyin!");
                        } else if (mayin_sayaci[i][j] == 0) {
                            dfs(i, j);
                        } else {
                            bloklar[i][j].setIcon(ic[mayin_sayaci[i][j]]);
                           
                            renk[i][j] = 'b';
                            
                            break;
                        }
                    } else {
                        if (bulunan_mayin != 0) {
                            if (bloklar[i][j].getIcon() == null) {
                                bulunan_mayin--;
                                bloklar[i][j].setIcon(ic[10]);
                            }
                            tf_mayin.setText("" + bulunan_mayin);
                        }
 
                    }
                }
 
            }
        }
 
    }
 
    public void hesaplama() {
        int row, column;
 
        for (int i = 0; i < satir_block; i++) {
            for (int j = 0; j < sütun_block; j++) {
                int value = 0;
                int R, C;
                row = i;
                column = j;
                if (mayin_sayaci[row][column] != -1) {
                    for (int k = 0; k < 8; k++) {
                        R = row + r[k];
                        C = column + c[k];
 
                        if (R >= 0 && C >= 0 && R < satir_block && C < sütun_block) {
                            if (mayin_sayaci[R][C] == -1) {
                                value++;
                            }
 
                        }
 
                    }
                    mayin_sayaci[row][column] = value;
 
                }
            }
        }
    }
 
    public void dfs(int row, int col) {
 
        int R, C;
        renk[row][col] = 'b';
 
        bloklar[row][col].setBackground(Color.GRAY);
 
        bloklar[row][col].setIcon(ic[mayin_sayaci[row][col]]);
        
        for (int i = 0; i < 8; i++) {
            R = row + r[i];
            C = col + c[i];
            if (R >= 0 && R < satir_block && C >= 0 && C < sütun_block && renk[R][C] == 'w') {
                if (mayin_sayaci[R][C] == 0) {
                    dfs(R, C);
                } else {
                    bloklar[R][C].setIcon(ic[mayin_sayaci[R][C]]);
                    
                    renk[R][C] = 'b';
 
                }
            }
 
        }
    }
 
    public void setmine() {
        int row = 0, col = 0;
        Boolean[][] flag = new Boolean[satir_block][sütun_block];
 
        for (int i = 0; i < satir_block; i++) {
            for (int j = 0; j < sütun_block; j++) {
                flag[i][j] = true;
                mayin_sayaci[i][j] = 0;
            }
        }
 
        flag[var1][var2] = false;
        renk[var1][var2] = 'b';
 
        for (int i = 0; i < mayin_sayisi; i++) {
            row = randomsatir.nextInt(satir_block);
            col = randomsütun.nextInt(sütun_block);
 
            if (flag[row][col] == true) {
 
                mayin_sayaci[row][col] = -1;
                renk[row][col] = 'b';
                flag[row][col] = false;
            } else {
                i--;
            }
        }
    }
 
    public void setic() {
        String name;
 
        for (int i = 0; i <= 8; i++) {
            name = i + ".gif";
            ic[i] = new ImageIcon(name);
        }
        ic[9] = new ImageIcon("mine.gif");
        ic[10] = new ImageIcon("flag.gif");
        ic[11] = new ImageIcon("new game.gif");
        ic[12] = new ImageIcon("crape.gif");
    }
 
    public class Stopwatch extends JFrame implements Runnable {
 
        long startTime;
        Thread updater;
        boolean isRunning = false;
        long a = 0;
        Runnable displayUpdater = new Runnable() {
 
            public void run() {
                displayElapsedTime(a);
                a++;
            }
        };
 
        public void stop() {
            long elapsed = a;
            isRunning = false;
            try {
                updater.join();
            } catch (InterruptedException ie) {
            }
            displayElapsedTime(elapsed);
            a = 0;
        }
 
        private void displayElapsedTime(long elapsedTime) {
 
            if (elapsedTime >= 0 && elapsedTime < 9) {
                tf_zaman.setText("00" + elapsedTime);
            } else if (elapsedTime > 9 && elapsedTime < 99) {
                tf_zaman.setText("0" + elapsedTime);
            } else if (elapsedTime > 99 && elapsedTime < 999) {
                tf_zaman.setText("" + elapsedTime);
            }
        }
 
        public void run() {
            try {
                while (isRunning) {
                    SwingUtilities.invokeAndWait(displayUpdater);
                    Thread.sleep(1000);
                }
            } catch (java.lang.reflect.InvocationTargetException ite) {
                ite.printStackTrace(System.err);
            } catch (InterruptedException ie) {
            }
        }
 
        public void Start() {
            startTime = System.currentTimeMillis();
            isRunning = true;
            updater = new Thread(this);
            updater.start();
        }
    }
 
    class Customizetion extends JFrame implements ActionListener {
 
        JTextField t1, t2, t3;
        JLabel lb1, lb2, lb3;
        JButton b1, b2;
        int cr, cc, cm, actionc = 0;
 
        Customizetion() {
            super("Kendin Ayarla");
            setSize(180, 200);
            setResizable(false);
            setLocation(p);
 
            t1 = new JTextField();
            t2 = new JTextField();
            t3 = new JTextField();
 
            b1 = new JButton("Tamam");
            b2 = new JButton("Çıkış");
 
            b1.addActionListener(this);
            b2.addActionListener(this);
 
            lb1 = new JLabel("Yükseklik");
            lb2 = new JLabel("Genişlik");
            lb3 = new JLabel("Mayınlar");
 
            getContentPane().setLayout(new GridLayout(0, 2));
 
            getContentPane().add(lb1);
            getContentPane().add(t1);
            getContentPane().add(lb2);
            getContentPane().add(t2);
            getContentPane().add(lb3);
            getContentPane().add(t3);
 
            getContentPane().add(b1);
            getContentPane().add(b2);
 
            show();
        }
 
        public void actionPerformed(ActionEvent e) {
            if (e.getSource() == b1) {
                try {
                    cr = Integer.parseInt(t1.getText());
                    cc = Integer.parseInt(t2.getText());
                    cm = Integer.parseInt(t3.getText());
                    setpanel(4, row(), column(), mine());
                    dispose();
                } catch (Exception any) {
                    JOptionPane.showMessageDialog(this, "Yanlış!");
                    t1.setText("");
                    t2.setText("");
                    t3.setText("");
                }
                
            }
 
            if (e.getSource() == b2) {
                dispose();
            }
        }
 
        public int row() {
            if (cr > 30) {
                return 30;
            } else if (cr < 10) {
                return 10;
            } else {
                return cr;
            }
        }
 
        public int column() {
            if (cc > 30) {
                return 30;
            } else if (cc < 10) {
                return 10;
            } else {
                return cc;
            }
        }
 
        public int mine() {
            if (cm > ((row() - 1) * (column() - 1))) {
                return ((row() - 1) * (column() - 1));
            } else if (cm < 10) {
                return 10;
            } else {
                return cm;
            }
        }
    }
}