/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package com.palmal.javabasics;

import java.text.DecimalFormat;

/**
 *
 * @author vk_wh
 */
public class CalcForm extends javax.swing.JFrame {
    
    
    /**
     * Init variables
     */
        private String parsedString;
        private double parsedDouble;
        private double firstValue = 0.0;
        private double secondValue = 0.0;
        private boolean isFirstSolve = true;
        private boolean isIntermidiateOperation = false;
        
        //String
        private void setDisplay(String display){
            //переводим тчк в зпт
            if (display.contains(".")){
                display = display.replace(".", ",");
            }
            
            //удаляем последние нули
            if (display.contains(",")){
                while (display.endsWith("0") && !display.endsWith(",0")){
                    display = display.substring(0, display.length() - 1);
                }
            }
            
            //сокращаем если есть экспонента
            if (display.contains("E") && display.length() > 16){
                String expo = display.substring(display.indexOf("E"));
                display = display.substring(0, 17 - expo.length()) + expo;
            } else if (display.length() > 18){
                display = display.substring(0, 18);
            }
            
            
            displayText.setText(display);
        }
        
        //double
        private double getDoubleDisplay(){
                parsedString = displayText.getText();
            if (parsedString.contains(",")){
                parsedString = parsedString.replace(",", ".");
            }
            parsedDouble = Double.parseDouble(parsedString);
            return parsedDouble;
        }
        
        private void setDisplayIfComma(){
            parsedString = displayText.getText();
            if (!parsedString.contains(",")){
                setDisplay(parsedString + ",");
            }
        }
        
        private void eraseAllCalc(){
            firstValue = 0.0;
            secondValue = 0.0;
            setDisplay("0");
            setSign("");
            isFirstSolve = true;
            isIntermidiateOperation = false;
        }
        
        private boolean isZeroOnDisplay(){
            return displayText.getText().equals("0");
        }
        
        private void setDisplayIfZero(int value){
            setDisplay(value + "");
        }
        
        private boolean isDisplayOfflimit(){
            return displayText.getText().length() > 15;
        }
        
        private void addDigitOnDisplay(int value){
            displayText.setText(displayText.getText() + value);
        }
        
        private void negateDisplay(){
            if (getDoubleDisplay() > 0){
                parsedDouble = (-1) * getDoubleDisplay();
                setDisplay(parsedDouble + "");
            } else if (getDoubleDisplay() < 0){
                parsedDouble = (-1) * getDoubleDisplay();
                setDisplay(parsedDouble + "");
            }
        }
        
        private void setSign(String sign){
            displaySign.setText(sign);
        }
        
        private String getSign(){
            return displaySign.getText();
        }
        
        private void solveAll(){
            if (!isFirstSolve) secondValue = getDoubleDisplay();
            if (getSign().contains("+")){
                    firstValue += secondValue;
                    setDisplay(firstValue + "");
                } else if (getSign().contains("-")){
                    firstValue -= secondValue;
                    setDisplay(firstValue + "");
                } else if (getSign().contains("*")){
                    firstValue *= secondValue;
                    setDisplay(firstValue + "");
                } else if (getSign().contains("/")){
                    if (secondValue == 0){
                        eraseAllCalc();
                        setDisplay("Деление на 0");
                        calcButtonDivide.setEnabled(false);
                        turnOffButtonsOnDivideError(true);
                        setSign("");
                    } else if ((firstValue == 0) && (secondValue > 0)){
                        eraseAllCalc();
                        setDisplay("+бесконечность");
                        turnOffButtonsOnDivideError(true);
                        setSign("");
                    } else if ((firstValue == 0) && (secondValue < 0)){
                        eraseAllCalc();
                        setDisplay("-бесконечность");
                        turnOffButtonsOnDivideError(true);
                        setSign("");
                    } else {
                        firstValue /= secondValue;
                        setDisplay(firstValue + "");
                    }
                }
            isFirstSolve = true;
            isIntermidiateOperation = true;
        }
        
        private void plusOperation(){
            if (isFirstSolve){
                firstValue = getDoubleDisplay();
                isFirstSolve = false;
            } else {
                secondValue = getDoubleDisplay();
                firstValue += secondValue;
            }
            setDisplay(firstValue + "");
            setSign("+");
            isIntermidiateOperation = true;
        }
        
        private void minusOperation(){
            if (isFirstSolve){
                firstValue = getDoubleDisplay();
                isFirstSolve = false;
            } else {
                secondValue = getDoubleDisplay();
                firstValue = firstValue - secondValue;
            }
            setDisplay(firstValue + "");
            setSign("-");
            isIntermidiateOperation = true;
        }
        
        private void multiplyOperation(){
            if (isFirstSolve){
                firstValue = getDoubleDisplay();
                isFirstSolve = false;
            } else {
                secondValue = getDoubleDisplay();
                firstValue *= secondValue;
            }
            setDisplay(firstValue + "");
            setSign("*");
            isIntermidiateOperation = true;
        }
        
        private void divideOperation(){
            if (isFirstSolve){
                firstValue = getDoubleDisplay();
                isFirstSolve = false;
            } else {
                secondValue = getDoubleDisplay();
                if (secondValue == 0){
                    eraseAllCalc();
                    setDisplay("Деление на 0");
                    turnOffButtonsOnDivideError(true);
                } else if ((firstValue == 0) && (secondValue > 0)){
                    eraseAllCalc();
                    setDisplay("+бесконечность");
                    turnOffButtonsOnDivideError(true);
                } else if ((firstValue == 0) && (secondValue < 0)){
                    eraseAllCalc();
                    setDisplay("-бесконечность");
                    turnOffButtonsOnDivideError(true);
                } else {
                    firstValue /= secondValue;
                    setDisplay(firstValue + "");
                }
            }
            setSign("/");
            isIntermidiateOperation = true;
        }
        
        private void turnOffButtonsOnDivideError(boolean divideError){
            calcButtonDivide.setEnabled(!divideError);
            calcButtonMinus.setEnabled(!divideError);
            calcButtonPlus.setEnabled(!divideError);
            calcButtonMultiply.setEnabled(!divideError);
            calcButtonPercent.setEnabled(!divideError);
            calcButtonPlusMinus.setEnabled(!divideError);
            calcButtonSolve.setEnabled(!divideError);
            calcButtonComma.setEnabled(!divideError);
        }
        
        private void percentOperation(){
            if (isFirstSolve){
                setDisplay("Ошибка");
            } else {
                secondValue = getDoubleDisplay();
                if (getSign().contains("+")){
                    firstValue += (firstValue / 100) * secondValue;
                    setDisplay(firstValue + "Worked");
                } else if (getSign().contains("-")){
                    firstValue -= (firstValue / 100) * secondValue;
                } else if (getSign().contains("*")){
                    firstValue *= (firstValue / 100) * secondValue;
                } else if (getSign().contains("/")){
                    firstValue /= (firstValue / 100) * secondValue;
                }
                setDisplay(firstValue + "");
                setSign("");
                isIntermidiateOperation = true;
                isFirstSolve = true;
            }
        }
        
    
    /**
     * Creates new form CalcForm
     */
    public CalcForm() {
        initComponents();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        calcButton0 = new javax.swing.JButton();
        calcButton1 = new javax.swing.JButton();
        calcButton2 = new javax.swing.JButton();
        calcButton3 = new javax.swing.JButton();
        calcButton4 = new javax.swing.JButton();
        calcButton5 = new javax.swing.JButton();
        calcButton6 = new javax.swing.JButton();
        calcButton7 = new javax.swing.JButton();
        calcButton8 = new javax.swing.JButton();
        calcButton9 = new javax.swing.JButton();
        calcButtonComma = new javax.swing.JButton();
        calcButtonSolve = new javax.swing.JButton();
        calcButtonPlus = new javax.swing.JButton();
        calcButtonMinus = new javax.swing.JButton();
        calcButtonMultiply = new javax.swing.JButton();
        calcButtonDivide = new javax.swing.JButton();
        calcButtonPercent = new javax.swing.JButton();
        calcButtonPlusMinus = new javax.swing.JButton();
        calcButtonAC = new javax.swing.JButton();
        jPanel3 = new javax.swing.JPanel();
        displayText = new javax.swing.JLabel();
        displaySign = new javax.swing.JLabel();
        jPanel4 = new javax.swing.JPanel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Калькулятор");
        setResizable(false);

        jPanel1.setBackground(new java.awt.Color(50, 51, 52));

        jPanel2.setBackground(new java.awt.Color(43, 44, 45));
        jPanel2.setBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED));

        calcButton0.setBackground(new java.awt.Color(25, 25, 25));
        calcButton0.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        calcButton0.setForeground(new java.awt.Color(101, 224, 255));
        calcButton0.setText("0");
        calcButton0.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        calcButton0.setFocusPainted(false);
        calcButton0.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                calcButton0ActionPerformed(evt);
            }
        });

        calcButton1.setBackground(new java.awt.Color(25, 25, 25));
        calcButton1.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        calcButton1.setForeground(new java.awt.Color(101, 224, 255));
        calcButton1.setText("1");
        calcButton1.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        calcButton1.setFocusPainted(false);
        calcButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                calcButton1ActionPerformed(evt);
            }
        });

        calcButton2.setBackground(new java.awt.Color(25, 25, 25));
        calcButton2.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        calcButton2.setForeground(new java.awt.Color(101, 224, 255));
        calcButton2.setText("2");
        calcButton2.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        calcButton2.setFocusPainted(false);
        calcButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                calcButton2ActionPerformed(evt);
            }
        });

        calcButton3.setBackground(new java.awt.Color(25, 25, 25));
        calcButton3.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        calcButton3.setForeground(new java.awt.Color(101, 224, 255));
        calcButton3.setText("3");
        calcButton3.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        calcButton3.setFocusPainted(false);
        calcButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                calcButton3ActionPerformed(evt);
            }
        });

        calcButton4.setBackground(new java.awt.Color(25, 25, 25));
        calcButton4.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        calcButton4.setForeground(new java.awt.Color(101, 224, 255));
        calcButton4.setText("4");
        calcButton4.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        calcButton4.setFocusPainted(false);
        calcButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                calcButton4ActionPerformed(evt);
            }
        });

        calcButton5.setBackground(new java.awt.Color(25, 25, 25));
        calcButton5.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        calcButton5.setForeground(new java.awt.Color(101, 224, 255));
        calcButton5.setText("5");
        calcButton5.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        calcButton5.setFocusPainted(false);
        calcButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                calcButton5ActionPerformed(evt);
            }
        });

        calcButton6.setBackground(new java.awt.Color(25, 25, 25));
        calcButton6.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        calcButton6.setForeground(new java.awt.Color(101, 224, 255));
        calcButton6.setText("6");
        calcButton6.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        calcButton6.setFocusPainted(false);
        calcButton6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                calcButton6ActionPerformed(evt);
            }
        });

        calcButton7.setBackground(new java.awt.Color(25, 25, 25));
        calcButton7.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        calcButton7.setForeground(new java.awt.Color(101, 224, 255));
        calcButton7.setText("7");
        calcButton7.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        calcButton7.setFocusPainted(false);
        calcButton7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                calcButton7ActionPerformed(evt);
            }
        });

        calcButton8.setBackground(new java.awt.Color(25, 25, 25));
        calcButton8.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        calcButton8.setForeground(new java.awt.Color(101, 224, 255));
        calcButton8.setText("8");
        calcButton8.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        calcButton8.setFocusPainted(false);
        calcButton8.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                calcButton8ActionPerformed(evt);
            }
        });

        calcButton9.setBackground(new java.awt.Color(25, 25, 25));
        calcButton9.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        calcButton9.setForeground(new java.awt.Color(101, 224, 255));
        calcButton9.setText("9");
        calcButton9.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        calcButton9.setFocusPainted(false);
        calcButton9.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                calcButton9ActionPerformed(evt);
            }
        });

        calcButtonComma.setBackground(new java.awt.Color(25, 25, 25));
        calcButtonComma.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        calcButtonComma.setForeground(new java.awt.Color(101, 224, 255));
        calcButtonComma.setText(",");
        calcButtonComma.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        calcButtonComma.setFocusPainted(false);
        calcButtonComma.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                calcButtonCommaActionPerformed(evt);
            }
        });

        calcButtonSolve.setBackground(new java.awt.Color(25, 25, 25));
        calcButtonSolve.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        calcButtonSolve.setForeground(new java.awt.Color(101, 224, 255));
        calcButtonSolve.setText("=");
        calcButtonSolve.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        calcButtonSolve.setFocusPainted(false);
        calcButtonSolve.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                calcButtonSolveActionPerformed(evt);
            }
        });

        calcButtonPlus.setBackground(new java.awt.Color(25, 25, 25));
        calcButtonPlus.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        calcButtonPlus.setForeground(new java.awt.Color(101, 224, 255));
        calcButtonPlus.setText("+");
        calcButtonPlus.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        calcButtonPlus.setFocusPainted(false);
        calcButtonPlus.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                calcButtonPlusActionPerformed(evt);
            }
        });

        calcButtonMinus.setBackground(new java.awt.Color(25, 25, 25));
        calcButtonMinus.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        calcButtonMinus.setForeground(new java.awt.Color(101, 224, 255));
        calcButtonMinus.setText("-");
        calcButtonMinus.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        calcButtonMinus.setFocusPainted(false);
        calcButtonMinus.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                calcButtonMinusActionPerformed(evt);
            }
        });

        calcButtonMultiply.setBackground(new java.awt.Color(25, 25, 25));
        calcButtonMultiply.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        calcButtonMultiply.setForeground(new java.awt.Color(101, 224, 255));
        calcButtonMultiply.setText("*");
        calcButtonMultiply.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        calcButtonMultiply.setFocusPainted(false);
        calcButtonMultiply.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                calcButtonMultiplyActionPerformed(evt);
            }
        });

        calcButtonDivide.setBackground(new java.awt.Color(25, 25, 25));
        calcButtonDivide.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        calcButtonDivide.setForeground(new java.awt.Color(101, 224, 255));
        calcButtonDivide.setText("/");
        calcButtonDivide.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        calcButtonDivide.setFocusPainted(false);
        calcButtonDivide.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                calcButtonDivideActionPerformed(evt);
            }
        });

        calcButtonPercent.setBackground(new java.awt.Color(25, 25, 25));
        calcButtonPercent.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        calcButtonPercent.setForeground(new java.awt.Color(101, 224, 255));
        calcButtonPercent.setText("%");
        calcButtonPercent.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        calcButtonPercent.setFocusPainted(false);
        calcButtonPercent.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                calcButtonPercentActionPerformed(evt);
            }
        });

        calcButtonPlusMinus.setBackground(new java.awt.Color(25, 25, 25));
        calcButtonPlusMinus.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        calcButtonPlusMinus.setForeground(new java.awt.Color(101, 224, 255));
        calcButtonPlusMinus.setText("+/-");
        calcButtonPlusMinus.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        calcButtonPlusMinus.setFocusPainted(false);
        calcButtonPlusMinus.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                calcButtonPlusMinusActionPerformed(evt);
            }
        });

        calcButtonAC.setBackground(new java.awt.Color(25, 25, 25));
        calcButtonAC.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        calcButtonAC.setForeground(new java.awt.Color(101, 224, 255));
        calcButtonAC.setText("AC");
        calcButtonAC.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        calcButtonAC.setFocusPainted(false);
        calcButtonAC.setFocusable(false);
        calcButtonAC.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                calcButtonACActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(calcButton7, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(calcButton4, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(calcButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(calcButtonAC, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(calcButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(calcButton8, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(calcButton5, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(calcButtonPlusMinus, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(calcButton0, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(calcButtonComma, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(calcButton9, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(calcButton6, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(calcButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(calcButtonPercent, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(7, 7, 7)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(calcButtonSolve, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(calcButtonMultiply, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(calcButtonMinus, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(calcButtonPlus, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(calcButtonDivide, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        jPanel2Layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {calcButton1, calcButton2, calcButton3, calcButton4, calcButton5, calcButton6, calcButton7, calcButton8, calcButton9, calcButtonAC, calcButtonComma, calcButtonDivide, calcButtonMinus, calcButtonMultiply, calcButtonPercent, calcButtonPlus, calcButtonPlusMinus, calcButtonSolve});

        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(5, 5, 5)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(calcButtonDivide, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(calcButtonMultiply, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(calcButtonMinus, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(calcButtonPlus, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(calcButtonSolve, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(calcButtonPercent, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(calcButton9, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(calcButton6, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(calcButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(calcButtonComma, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(calcButtonPlusMinus, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(calcButton8, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(calcButton5, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(calcButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(calcButtonAC, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(calcButton7, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(calcButton4, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(calcButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(calcButton0)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel2Layout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {calcButton0, calcButton1, calcButton2, calcButton3, calcButton4, calcButton5, calcButton6, calcButton7, calcButton8, calcButton9, calcButtonAC, calcButtonComma, calcButtonDivide, calcButtonMinus, calcButtonMultiply, calcButtonPercent, calcButtonPlus, calcButtonPlusMinus, calcButtonSolve});

        jPanel3.setBackground(new java.awt.Color(43, 44, 45));

        displayText.setFont(new java.awt.Font("Segoe UI", 0, 48)); // NOI18N
        displayText.setForeground(new java.awt.Color(101, 224, 255));
        displayText.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        displayText.setText("0");

        displaySign.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        displaySign.setForeground(new java.awt.Color(101, 224, 255));
        displaySign.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(displayText, javax.swing.GroupLayout.PREFERRED_SIZE, 458, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(displaySign, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(displaySign, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(displayText, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel4.setBackground(new java.awt.Color(43, 44, 45));

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 289, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void calcButton0ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_calcButton0ActionPerformed
        if (isIntermidiateOperation) {
            setDisplay("");
            isIntermidiateOperation = false;
        }
        if (!isDisplayOfflimit() && !isZeroOnDisplay()){
            addDigitOnDisplay(0);
            turnOffButtonsOnDivideError(false);
        }
    }//GEN-LAST:event_calcButton0ActionPerformed

    private void calcButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_calcButton1ActionPerformed
        if (isIntermidiateOperation) {
            setDisplay("");
            isIntermidiateOperation = false;
        }
        if (isZeroOnDisplay()){
            setDisplayIfZero(1);
        } else if (!isDisplayOfflimit()){
            addDigitOnDisplay(1);
            turnOffButtonsOnDivideError(false);
        }
    }//GEN-LAST:event_calcButton1ActionPerformed

    private void calcButtonACActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_calcButtonACActionPerformed
        eraseAllCalc();
        
        turnOffButtonsOnDivideError(false);
    }//GEN-LAST:event_calcButtonACActionPerformed

    private void calcButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_calcButton2ActionPerformed
        if (isIntermidiateOperation) {
            setDisplay("");
            isIntermidiateOperation = false;
        }
        if (isZeroOnDisplay()){
            setDisplayIfZero(2);
        } else if (!isDisplayOfflimit()){
            addDigitOnDisplay(2);
            turnOffButtonsOnDivideError(false);
        }
    }//GEN-LAST:event_calcButton2ActionPerformed

    private void calcButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_calcButton3ActionPerformed
        if (isIntermidiateOperation) {
            setDisplay("");
            isIntermidiateOperation = false;
        }
        if (isZeroOnDisplay()){
            setDisplayIfZero(3);
        } else if (!isDisplayOfflimit()){
            addDigitOnDisplay(3);
            turnOffButtonsOnDivideError(false);
        }
    }//GEN-LAST:event_calcButton3ActionPerformed

    private void calcButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_calcButton4ActionPerformed
        if (isIntermidiateOperation) {
            setDisplay("");
            isIntermidiateOperation = false;
        }
        if (isZeroOnDisplay()){
            setDisplayIfZero(4);
        } else if (!isDisplayOfflimit()){
            addDigitOnDisplay(4);
            turnOffButtonsOnDivideError(false);
        }
    }//GEN-LAST:event_calcButton4ActionPerformed

    private void calcButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_calcButton5ActionPerformed
        if (isIntermidiateOperation) {
            setDisplay("");
            isIntermidiateOperation = false;
        }
        if (isZeroOnDisplay()){
            setDisplayIfZero(5);
        } else if (!isDisplayOfflimit()){
            addDigitOnDisplay(5);
            turnOffButtonsOnDivideError(false);
        }
    }//GEN-LAST:event_calcButton5ActionPerformed

    private void calcButton6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_calcButton6ActionPerformed
        if (isIntermidiateOperation) {
            setDisplay("");
            isIntermidiateOperation = false;
        }
        if (isZeroOnDisplay()){
            setDisplayIfZero(6);
        } else if (!isDisplayOfflimit()){
            addDigitOnDisplay(6);
            turnOffButtonsOnDivideError(false);
        }
    }//GEN-LAST:event_calcButton6ActionPerformed

    private void calcButton7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_calcButton7ActionPerformed
        if (isIntermidiateOperation) {
            setDisplay("");
            isIntermidiateOperation = false;
        }
        if (isZeroOnDisplay()){
            setDisplayIfZero(7);
        } else if (!isDisplayOfflimit()){
            addDigitOnDisplay(7);
            turnOffButtonsOnDivideError(false);
        }
    }//GEN-LAST:event_calcButton7ActionPerformed

    private void calcButton8ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_calcButton8ActionPerformed
        if (isIntermidiateOperation) {
            setDisplay("");
            isIntermidiateOperation = false;
        }
        if (isZeroOnDisplay()){
            setDisplayIfZero(8);
        } else if (!isDisplayOfflimit()){
            addDigitOnDisplay(8);
            turnOffButtonsOnDivideError(false);
        }
    }//GEN-LAST:event_calcButton8ActionPerformed

    private void calcButton9ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_calcButton9ActionPerformed
        if (isIntermidiateOperation) {
            setDisplay("");
            isIntermidiateOperation = false;
        }
        if (isZeroOnDisplay()){
            setDisplayIfZero(9);
        } else if (!isDisplayOfflimit()){
            addDigitOnDisplay(9);
            turnOffButtonsOnDivideError(false);
        }
    }//GEN-LAST:event_calcButton9ActionPerformed

    private void calcButtonCommaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_calcButtonCommaActionPerformed
        if(!isDisplayOfflimit()){
           setDisplayIfComma(); 
        }
    }//GEN-LAST:event_calcButtonCommaActionPerformed

    private void calcButtonPlusMinusActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_calcButtonPlusMinusActionPerformed
        if (isIntermidiateOperation) {
            setDisplay("0");
            isIntermidiateOperation = false;
        }
        negateDisplay();
    }//GEN-LAST:event_calcButtonPlusMinusActionPerformed

    private void calcButtonPlusActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_calcButtonPlusActionPerformed
        plusOperation();
    }//GEN-LAST:event_calcButtonPlusActionPerformed

    private void calcButtonSolveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_calcButtonSolveActionPerformed
        solveAll();
    }//GEN-LAST:event_calcButtonSolveActionPerformed

    private void calcButtonPercentActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_calcButtonPercentActionPerformed
        percentOperation();
    }//GEN-LAST:event_calcButtonPercentActionPerformed

    private void calcButtonMinusActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_calcButtonMinusActionPerformed
        minusOperation();
    }//GEN-LAST:event_calcButtonMinusActionPerformed

    private void calcButtonMultiplyActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_calcButtonMultiplyActionPerformed
        multiplyOperation();
    }//GEN-LAST:event_calcButtonMultiplyActionPerformed

    private void calcButtonDivideActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_calcButtonDivideActionPerformed
        divideOperation();
    }//GEN-LAST:event_calcButtonDivideActionPerformed

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
            java.util.logging.Logger.getLogger(CalcForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(CalcForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(CalcForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(CalcForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new CalcForm().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton calcButton0;
    private javax.swing.JButton calcButton1;
    private javax.swing.JButton calcButton2;
    private javax.swing.JButton calcButton3;
    private javax.swing.JButton calcButton4;
    private javax.swing.JButton calcButton5;
    private javax.swing.JButton calcButton6;
    private javax.swing.JButton calcButton7;
    private javax.swing.JButton calcButton8;
    private javax.swing.JButton calcButton9;
    private javax.swing.JButton calcButtonAC;
    private javax.swing.JButton calcButtonComma;
    private javax.swing.JButton calcButtonDivide;
    private javax.swing.JButton calcButtonMinus;
    private javax.swing.JButton calcButtonMultiply;
    private javax.swing.JButton calcButtonPercent;
    private javax.swing.JButton calcButtonPlus;
    private javax.swing.JButton calcButtonPlusMinus;
    private javax.swing.JButton calcButtonSolve;
    private javax.swing.JLabel displaySign;
    private javax.swing.JLabel displayText;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    // End of variables declaration//GEN-END:variables
}
