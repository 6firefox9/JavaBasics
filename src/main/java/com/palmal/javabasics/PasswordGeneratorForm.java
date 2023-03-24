/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package com.palmal.javabasics;

import java.util.ArrayList;
import java.util.List;



/**
 *
 * @author vk_wh
 */
public class PasswordGeneratorForm extends javax.swing.JFrame {
    
    StringBuilder password;
    
    private final char[] allowedChars = {'!', '@','#','$','%','^',
        '&','*','_','<','>','№','?'};
    private final char[] allowedCapitalChars = {'A','B','C','D','E','F','G',
        'H','J','K','L','M','N','P','Q','R','S','T','U','V','W','X','Y','Z'};
    private final char[] allowedLowerChars = {'a','b','c','d','e','f','g',
        'h','j','k','m','n','p','q','r','s','t','u','v','w','x','y','z'};
    private final char[] allowedDigits = {'1','2','3','4','5','6','7','8','9'};
        
    private void generatePassword(){
        password = new StringBuilder();
        List<char[]> abc = new ArrayList<>();
        if (jCheckBoxLowers.isSelected()) abc.add(allowedLowerChars);
        if (jCheckBoxCapitals.isSelected()) abc.add(allowedCapitalChars);
        if (jCheckBoxSpecials.isSelected()) abc.add(allowedChars);
        if (jCheckBoxDigits.isSelected()) abc.add(allowedDigits);
        int passwordSize = Integer.parseInt(jTextFieldLength.getText());
        if (passwordSize > 16){
            passwordSize = 16;
            jTextFieldLength.setText("16");
        }
        while(password.length() < passwordSize && !abc.isEmpty()){
            char[] temp = abc.get((int)(Math.random() * (abc.size()) ));
            char randomChar = temp[(int)(Math.random() * (temp.length))];
            password.append(String.valueOf(randomChar));
        }
        display.setText(password.toString());
    }
    
    
    /**
     * Creates new form CreditCardValidator
     */
    public PasswordGeneratorForm() {
        initComponents();
        generatePassword();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        mainPanel = new javax.swing.JPanel();
        titlePanel = new javax.swing.JPanel();
        titleText = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        display = new javax.swing.JTextField();
        clearButton = new javax.swing.JButton();
        jPanel1 = new javax.swing.JPanel();
        jCheckBoxCapitals = new javax.swing.JCheckBox();
        jCheckBoxSpecials = new javax.swing.JCheckBox();
        jCheckBoxDigits = new javax.swing.JCheckBox();
        jCheckBoxLowers = new javax.swing.JCheckBox();
        jLabel1 = new javax.swing.JLabel();
        jTextFieldLength = new javax.swing.JTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Проверка кредитной карты");
        setAlwaysOnTop(true);
        setResizable(false);

        mainPanel.setBackground(new java.awt.Color(37, 37, 37));

        titlePanel.setBackground(new java.awt.Color(43, 44, 45));

        titleText.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        titleText.setForeground(new java.awt.Color(255, 105, 0));
        titleText.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        titleText.setText("Генератор паролей");
        titleText.setFocusable(false);

        javax.swing.GroupLayout titlePanelLayout = new javax.swing.GroupLayout(titlePanel);
        titlePanel.setLayout(titlePanelLayout);
        titlePanelLayout.setHorizontalGroup(
            titlePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(titlePanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(titleText, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        titlePanelLayout.setVerticalGroup(
            titlePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(titlePanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(titleText, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        jPanel3.setBackground(new java.awt.Color(43, 44, 45));

        display.setEditable(false);
        display.setBackground(new java.awt.Color(25, 25, 25));
        display.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        display.setForeground(new java.awt.Color(101, 224, 255));
        display.setHorizontalAlignment(javax.swing.JTextField.CENTER);

        clearButton.setBackground(new java.awt.Color(25, 25, 25));
        clearButton.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        clearButton.setForeground(new java.awt.Color(255, 105, 0));
        clearButton.setText("Следующий пароль");
        clearButton.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        clearButton.setRequestFocusEnabled(false);
        clearButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                clearButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(display)
                    .addComponent(clearButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(display, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(clearButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        jPanel1.setBackground(new java.awt.Color(43, 44, 45));
        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Настройки", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 0, 12), new java.awt.Color(255, 105, 0))); // NOI18N
        jPanel1.setName("Настройки"); // NOI18N

        jCheckBoxCapitals.setBackground(new java.awt.Color(43, 44, 45));
        jCheckBoxCapitals.setForeground(new java.awt.Color(255, 105, 0));
        jCheckBoxCapitals.setSelected(true);
        jCheckBoxCapitals.setText("Заглавные буквы");

        jCheckBoxSpecials.setBackground(new java.awt.Color(43, 44, 45));
        jCheckBoxSpecials.setForeground(new java.awt.Color(255, 105, 0));
        jCheckBoxSpecials.setSelected(true);
        jCheckBoxSpecials.setText("Специальные символы");

        jCheckBoxDigits.setBackground(new java.awt.Color(43, 44, 45));
        jCheckBoxDigits.setForeground(new java.awt.Color(255, 105, 0));
        jCheckBoxDigits.setSelected(true);
        jCheckBoxDigits.setText("Цифры");

        jCheckBoxLowers.setBackground(new java.awt.Color(43, 44, 45));
        jCheckBoxLowers.setForeground(new java.awt.Color(255, 105, 0));
        jCheckBoxLowers.setSelected(true);
        jCheckBoxLowers.setText("Строчные буквы");

        jLabel1.setBackground(new java.awt.Color(0, 0, 0));
        jLabel1.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 105, 0));
        jLabel1.setText("Длина пароля");

        jTextFieldLength.setBackground(new java.awt.Color(43, 44, 45));
        jTextFieldLength.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jTextFieldLength.setForeground(new java.awt.Color(255, 105, 0));
        jTextFieldLength.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        jTextFieldLength.setText("16");
        jTextFieldLength.setCaretColor(new java.awt.Color(255, 255, 255));
        jTextFieldLength.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextFieldLengthActionPerformed(evt);
            }
        });
        jTextFieldLength.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jTextFieldLengthKeyTyped(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jCheckBoxLowers)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jCheckBoxCapitals))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jCheckBoxSpecials)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jCheckBoxDigits))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jTextFieldLength)))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jCheckBoxLowers)
                    .addComponent(jCheckBoxCapitals))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jCheckBoxSpecials)
                    .addComponent(jCheckBoxDigits))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextFieldLength, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout mainPanelLayout = new javax.swing.GroupLayout(mainPanel);
        mainPanel.setLayout(mainPanelLayout);
        mainPanelLayout.setHorizontalGroup(
            mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(mainPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(titlePanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        mainPanelLayout.setVerticalGroup(
            mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, mainPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(titlePanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(mainPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(mainPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void clearButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_clearButtonActionPerformed
        generatePassword();
    }//GEN-LAST:event_clearButtonActionPerformed

    private void jTextFieldLengthKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextFieldLengthKeyTyped
        if (!Character.isDigit(evt.getKeyChar()) || jTextFieldLength.getText().length() > 1) evt.consume();
    }//GEN-LAST:event_jTextFieldLengthKeyTyped

    private void jTextFieldLengthActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextFieldLengthActionPerformed
        
    }//GEN-LAST:event_jTextFieldLengthActionPerformed

    /**
     * Init standart var & methods
     * 
     */
    
    String startMessage = "Введите номер карты";
    
    private String cardCheck(String cardNumber){
        //Add code here
        return "";
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
            java.util.logging.Logger.getLogger(PasswordGeneratorForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(PasswordGeneratorForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(PasswordGeneratorForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(PasswordGeneratorForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new PasswordGeneratorForm().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton clearButton;
    private javax.swing.JTextField display;
    private javax.swing.JCheckBox jCheckBoxCapitals;
    private javax.swing.JCheckBox jCheckBoxDigits;
    private javax.swing.JCheckBox jCheckBoxLowers;
    private javax.swing.JCheckBox jCheckBoxSpecials;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JTextField jTextFieldLength;
    private javax.swing.JPanel mainPanel;
    private javax.swing.JPanel titlePanel;
    private javax.swing.JLabel titleText;
    // End of variables declaration//GEN-END:variables
}

