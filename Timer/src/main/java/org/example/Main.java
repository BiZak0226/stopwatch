package org.example;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Objects;

class Stopwatch extends JFrame{
    JButton start, reset, pause;
    JLabel hs, ms, ss, mss;
    Thread p_display;
    int ht, mt, st, mst= 0;
    long time=0;
    ImageIcon imageIcon;
    JLabel imageLabel;

    public Stopwatch(){
        this.setTitle("StopWatch");

        setGUI();

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLocation(600,300);
        this.pack();
        this.setResizable(false);
        this.setVisible(true);
    }
    public void setGUI(){
        JPanel panel = new JPanel(new BorderLayout());
        JPanel button_panel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JPanel watch_panel = new JPanel(new FlowLayout(FlowLayout.CENTER));

        JLabel c1 = new JLabel(" : ");
        JLabel c2 = new JLabel(" : ");
        JLabel c3 = new JLabel(" . ");

        hs = new JLabel("00");
        ms = new JLabel("00");
        ss = new JLabel("00");
        mss = new JLabel("00");

        start = new JButton("START");
        reset = new JButton("RESET");
        pause = new JButton("PAUSE");
        button_panel.add(start);
        button_panel.add(pause);
        button_panel.add(reset);

        watch_panel.add(hs);
        watch_panel.add(c1);
        watch_panel.add(ms);
        watch_panel.add(c2);
        watch_panel.add(ss);
        watch_panel.add(c3);
        watch_panel.add(mss);

        panel.add(watch_panel, BorderLayout.CENTER);
        panel.add(button_panel, BorderLayout.SOUTH);

        imageIcon = new ImageIcon(Objects.requireNonNull(getClass().getClassLoader().getResource("images/cat2-2.gif")));
        imageLabel = new JLabel(imageIcon);
        watch_panel.add(imageLabel);
        add(panel);

        start.setFont(new Font("courier",Font.BOLD, 30));
        pause.setFont(new Font("courier",Font.BOLD, 30));
        reset.setFont(new Font("courier",Font.BOLD, 30));


        hs.setFont(new Font("courier",Font.BOLD, 40));
        ms.setFont(new Font("courier",Font.BOLD, 40));
        ss.setFont(new Font("courier",Font.BOLD, 40));
        mss.setFont(new Font("courier",Font.BOLD, 40));

        c1.setFont(new Font("courier",Font.BOLD, 40));
        c2.setFont(new Font("courier",Font.BOLD, 40));
        c3.setFont(new Font("courier",Font.BOLD, 40));




        pause.setEnabled(false);
        reset.setEnabled(false);

        start.addActionListener(new ButtonListener());
        pause.addActionListener(new ButtonListener());
        reset.addActionListener(new ButtonListener());

    }
    class ButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String s = e.getActionCommand();
            switch (s) {
                case "START" -> {
                    start.setEnabled(false);
                    pause.setEnabled(true);
                    reset.setEnabled(false);

                    imageLabel.setIcon(new ImageIcon(Objects.requireNonNull(getClass().getResource("/images/cat2.gif"))));

                    p_display = new Thread(() -> {
                        while (!Thread.currentThread().isInterrupted()) {
                            try {
                                Thread.sleep(10);
                                mst++;

                                if (mst == 100) {
                                    mst = 0;
                                    st++;
                                }
                                if (st == 60) {
                                    st = 0;
                                    mt++;
                                }
                                if (mt == 60) {
                                    mt = 0;
                                    ht++;
                                }

                                hs.setText(String.format("%02d", ht));
                                ms.setText(String.format("%02d", mt));
                                ss.setText(String.format("%02d", st));
                                mss.setText(String.format("%02d", mst));
                            } catch (InterruptedException ignored) {
                                break; // 스레드가 interrupted 되면 루프를 종료합니다.
                            }
                        }
                    });
                    p_display.start();
                }
                case "PAUSE" -> {
                    start.setEnabled(true);
                    pause.setEnabled(false);
                    reset.setEnabled(true);
                    p_display.interrupt(); // 스레드를 중단합니다.
                    imageLabel.setIcon(new ImageIcon(Objects.requireNonNull(getClass().getResource("/images/cat2-2.gif"))));

                }
                case "RESET" -> {
                    start.setEnabled(true);
                    pause.setEnabled(false);
                    reset.setEnabled(false);
                    hs.setText("00");
                    ms.setText("00");
                    ss.setText("00");
                    mss.setText("00");
                    ht = 0;
                    mt = 0;
                    st = 0;
                    mst = 0;
                    if (p_display != null) {
                        p_display.interrupt(); // 스레드를 중단합니다.
                    }
                    imageLabel.setIcon(new ImageIcon(Objects.requireNonNull(getClass().getResource("/images/cat2-2.gif"))));


                }
            }
        }
    }
}

public class Main {
    public static void main(String[] args) {
        new Stopwatch();

    }
}