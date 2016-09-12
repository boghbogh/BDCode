package com.loudacre.techdiagnosticgui;

import java.awt.Cursor;
import java.util.Random;

import javax.swing.SwingWorker;

import org.apache.log4j.Logger;

public class MainPanel extends javax.swing.JPanel {
	private static final long serialVersionUID = -5423165331850867562L;

	private static final Logger logger = Logger.getLogger(MainPanel.class);

    private final DiagnosticDataGenerator generator;
    private final Random random;
    
    public MainPanel() {
        initComponents();
        
        generator = new DiagnosticDataGenerator();
        random = new Random();
    }

    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        stationList = new javax.swing.JComboBox();
        dumpButton = new javax.swing.JButton();
        stationLabel = new javax.swing.JLabel();

        stationList.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Berkeley", "Richmond", "San Francisco", "San Jose" }));

        dumpButton.setText("Dump Diagnostic Data");
        dumpButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                dumpButtonActionPerformed(evt);
            }
        });

        stationLabel.setText("Station:");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(stationLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(stationList, 0, 182, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(dumpButton)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(stationList, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(dumpButton)
                    .addComponent(stationLabel))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void dumpButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_dumpButtonActionPerformed
        dumpDiagnosticData();
    }//GEN-LAST:event_dumpButtonActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton dumpButton;
    private javax.swing.JLabel stationLabel;
    private javax.swing.JComboBox stationList;
    // End of variables declaration//GEN-END:variables

    private void dumpDiagnosticData() {
        final String station = (String)stationList.getSelectedItem();
        
       
        setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        
        class StationDataWorker extends SwingWorker<Void, Void> {
            @Override
            protected Void doInBackground() throws Exception {
                // simulate delay in connecting to the station
            	Thread.sleep(random.nextInt(1500) + 500);
                String data = generator.getRecord(station);
                
                logger.info(data);
                return null;
            }

            @Override
            protected void done() {
                setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
            }
        }
        
        new StationDataWorker().execute();
    }
}
