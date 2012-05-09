/*  Fuck this shit

package com.escapeNT.acidRain.PailCompat;

import com.escapeNT.acidRain.Config;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.swing.DefaultListModel;
import javax.swing.JOptionPane;
import org.bukkit.Bukkit;
import org.bukkit.World;

*/
/**
 * Settings interface for Pail
 * @author escapeNT
 *//*

public class SettingsInterface extends javax.swing.JPanel {

    */
/** Creates new form SettingsInterface *//*

    public SettingsInterface() {
        initComponents();
        for(World w : Bukkit.getServer().getWorlds()) {
            ((DefaultListModel)worlds.getModel()).addElement(w.getName());
        }
        readConfig();
    }

    private void readConfig() {
        broadcast.setSelected();
        damage.setValue(Config.getRainDamage());
        dissolve.setSelected(Config.willDissolveBlocks());
        interval.setValue(Config.getDamageInterval());
        dissolveRate.setValue(Config.getDissolveBlockChance());
        message.setText(Config.getRainMessage());
        rainChance.setValue(Config.getAcidRainChance());

        List<Integer> i = new ArrayList<Integer>();
        for(String s : Config.getWorldsEnabled()) {
            i.add(((DefaultListModel)worlds.getModel()).indexOf(s));
        }
        int[] indices = new int[i.size()];
        for(int in = 0; in < i.size(); in++) {
            indices[in] = i.get(in);
        }
        worlds.setSelectedIndices(indices);
    }

    */
/** This method is called from within the constructor to
     * initialize the form.
     *//*

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        rainChance = new javax.swing.JSpinner();
        jLabel2 = new javax.swing.JLabel();
        dissolveRate = new javax.swing.JSpinner();
        jLabel3 = new javax.swing.JLabel();
        message = new javax.swing.JTextField();
        broadcast = new javax.swing.JCheckBox();
        dissolve = new javax.swing.JCheckBox();
        jLabel4 = new javax.swing.JLabel();
        damage = new javax.swing.JSpinner();
        jLabel5 = new javax.swing.JLabel();
        interval = new javax.swing.JSpinner();
        jLabel6 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        worlds = new javax.swing.JList();
        save = new javax.swing.JButton();
        revert = new javax.swing.JButton();

        setBorder(javax.swing.BorderFactory.createTitledBorder("Acid Rain Settings"));
        setLayout(null);

        jLabel1.setText("Acid rain chance");
        add(jLabel1);
        jLabel1.setBounds(60, 40, 110, 30);

        rainChance.setModel(new javax.swing.SpinnerNumberModel(25, 0, 100, 1));
        add(rainChance);
        rainChance.setBounds(190, 40, 60, 28);

        jLabel2.setText("Block dissolve rate");
        add(jLabel2);
        jLabel2.setBounds(60, 90, 130, 30);

        dissolveRate.setModel(new javax.swing.SpinnerNumberModel(10, 0, 100, 1));
        add(dissolveRate);
        dissolveRate.setBounds(190, 90, 60, 28);

        jLabel3.setText("Acid rain message");
        add(jLabel3);
        jLabel3.setBounds(60, 140, 120, 16);

        message.setHorizontalAlignment(javax.swing.JTextField.LEFT);
        message.setText("Acid rain has begun in <world>");
        message.setToolTipText("<world> is replaced by world name.");
        add(message);
        message.setBounds(60, 160, 510, 28);

        broadcast.setText("Broadcast message");
        add(broadcast);
        broadcast.setBounds(50, 200, 170, 23);

        dissolve.setText("Damage blocks");
        add(dissolve);
        dissolve.setBounds(240, 200, 140, 23);

        jLabel4.setText("Acid rain damage");
        add(jLabel4);
        jLabel4.setBounds(290, 40, 120, 30);

        damage.setModel(new javax.swing.SpinnerNumberModel(Integer.valueOf(0), Integer.valueOf(0), null, Integer.valueOf(1)));
        add(damage);
        damage.setBounds(410, 40, 50, 28);

        jLabel5.setText("Damage interval");
        add(jLabel5);
        jLabel5.setBounds(290, 90, 110, 30);

        interval.setModel(new javax.swing.SpinnerNumberModel(Integer.valueOf(1), Integer.valueOf(1), null, Integer.valueOf(1)));
        add(interval);
        interval.setBounds(410, 90, 50, 28);

        jLabel6.setText("Worlds enabled");
        add(jLabel6);
        jLabel6.setBounds(60, 240, 100, 30);

        worlds.setModel(new DefaultListModel());
        jScrollPane1.setViewportView(worlds);

        add(jScrollPane1);
        jScrollPane1.setBounds(60, 270, 220, 132);

        save.setText("Save");
        save.setFocusable(false);
        save.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                saveActionPerformed(evt);
            }
        });
        add(save);
        save.setBounds(660, 380, 75, 29);

        revert.setText("Revert");
        revert.setFocusable(false);
        revert.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                revertActionPerformed(evt);
            }
        });
        add(revert);
        revert.setBounds(570, 380, 83, 29);
    }// </editor-fold>//GEN-END:initComponents

    private void revertActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_revertActionPerformed
        readConfig();
    }//GEN-LAST:event_revertActionPerformed

    private void saveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_saveActionPerformed
        Config.setAcidRainChance(((Integer)rainChance.getValue()).intValue());
        Config.setDamageInterval(((Integer)interval.getValue()).intValue());
        Config.setRainDamage(((Integer)damage.getValue()).intValue());
        Config.setDissolveBlockChance(((Integer)dissolveRate.getValue()).intValue());
        Config.setBroadcastMessage(broadcast.isSelected());
        Config.setDissolveBlocks(dissolve.isSelected());
        Config.setRainMessage(message.getText());

        List<Object> w = Arrays.asList(worlds.getSelectedValues());
        List<String> worldNames = new ArrayList<String>();
        for(Object o : w) {
            worldNames.add(o.toString());
        }
        Config.setWorldsEnabled(worldNames);

        Config.save();

        JOptionPane.showMessageDialog(this, "Config saved!", "Save", JOptionPane.INFORMATION_MESSAGE);
    }//GEN-LAST:event_saveActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JCheckBox broadcast;
    private javax.swing.JSpinner damage;
    private javax.swing.JCheckBox dissolve;
    private javax.swing.JSpinner dissolveRate;
    private javax.swing.JSpinner interval;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextField message;
    private javax.swing.JSpinner rainChance;
    private javax.swing.JButton revert;
    private javax.swing.JButton save;
    private javax.swing.JList worlds;
    // End of variables declaration//GEN-END:variables
}*/
