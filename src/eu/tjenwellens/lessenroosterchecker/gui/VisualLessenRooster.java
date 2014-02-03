/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package eu.tjenwellens.lessenroosterchecker.gui;

import eu.tjenwellens.lessenroosterchecker.elements.Les;
import eu.tjenwellens.lessenroosterchecker.elements.LesCreator;
import eu.tjenwellens.lessenroosterchecker.elements.TimeStamp;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Point;
import java.util.Collection;
import javax.swing.JFrame;

/**
 *
 * @author Tjen
 */
public class VisualLessenRooster extends javax.swing.JPanel {
    private int weekCriterium;
    private JFrame frame;
    private int[][] lessen;

    /**
     * Creates new form VisualLessenRooster
     */
    public VisualLessenRooster(JFrame frame, int weekCriterium) {
        this.frame = frame;
        this.weekCriterium = weekCriterium;
        initLessen();
        initComponents();
        this.setPreferredSize(new Dimension(500, 850));
    }

    private void initLessen() {
        lessen = new int[7][40];
//        lessen[5][10] = 1;
//        lessen[3][10] = 2;
//        lessen[2][10] = 1;
//        lessen[1][10] = 1;
//        lessen[6][39] = 5;
    }

    public void setLessen(int[][] lessen) {
        this.lessen = lessen;
        invalidate();
        validate();
    }

    public void addTimeStamps(Collection<TimeStamp> timeStamps) {
//        int teller = 1;
        for (TimeStamp timeStamp : timeStamps) {
//            teller++;
//            System.out.println(teller + "/" + timeStamps.size());
            int stamp = timeStamp.hashCode();
            if (stamp < 22 * 100000) {
                System.out.println("yep");
            }
            int week = stamp / 100000;
            if (week != weekCriterium) {
                continue;
            }
            int day = stamp / 10000 - week * 10;
            int hour = stamp / 100 - (week * 10 + day) * 100;
            int min = stamp - ((week * 10 + day) * 100 + hour) * 100;
            int kwart = (hour - 8) * 4 + min / 25;
            lessen[day][kwart]++;
            System.out.println(lessen[day][kwart]);
        }
        invalidate();
        validate();
    }

    public static void main(String[] args) {
        VisualLessenRooster pnl = create(21);
        pnl.addTimeStamps(entry1().getTimeStamps());
        pnl.addTimeStamps(entry2().getTimeStamps());
//        pnl.addTimeStamps(entry1().getTimeStamps());
    }

    private static Les entry1() {
        LesCreator lc = new LesCreator("uni", "maandag");
        lc.setNaam("Computerarchitectuur");
        lc.setDetails("");
        lc.setBeginUur("10:00");
        lc.setEindUur("12:45");
        lc.setLesvorm("theorie");
        lc.setWeken("21-35");
        return lc.createLes();
    }

    private static Les entry2() {
        LesCreator lc = new LesCreator("uni", "donderdag");
        lc.setNaam("Computerarchitectuur");
        lc.setDetails("");
        lc.setBeginUur("14:30");
        lc.setEindUur("17:15");
        lc.setLesvorm("oefeningen");
        lc.setWeken("21-35");
        return lc.createLes();
    }

    public static VisualLessenRooster create(int weekCriterium) {
        JFrame frame = new JFrame("Visual");
        VisualLessenRooster panel = new VisualLessenRooster(frame, weekCriterium);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setContentPane(panel);
        frame.pack();
        frame.setVisible(true);
        return panel;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Dimension size = new Dimension(50, 20);
        Point offset = new Point(100, size.height + 10);
        for (int dag = 0; dag < lessen.length; dag++) {
            for (int kwart = 0; kwart < lessen[dag].length; kwart++) {
                Color c;
                switch (lessen[dag][kwart]) {
                    case 0:
                        c = Color.white;
                        break;
                    case 1:
                        c = Color.green;
                        break;
                    case 2:
                        c = Color.orange;
                        break;
                    case 3:
                        c = Color.red;
                        break;
                    default:
                        c = Color.black;
                }
                g.setColor(c);
                g.fillRect(offset.x + size.width * dag, offset.y + size.height * kwart, offset.x + size.width * (dag + 1), offset.y + size.height * (kwart + 1));
            }
        }
        g.setColor(Color.black);
        for (int dag = 0; dag <= lessen.length; dag++) {
            g.drawLine(offset.x + size.width * dag, offset.y, offset.x + size.width * dag, offset.y + size.height * lessen[0].length);
        }
        for (int kwart = 0; kwart <= lessen[0].length; kwart++) {
            g.drawLine(offset.x, offset.y + size.height * kwart, offset.x + size.width * lessen.length, offset.y + size.height * kwart);
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 400, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 300, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents
    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}
