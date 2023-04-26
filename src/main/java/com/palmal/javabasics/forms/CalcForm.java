/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package com.palmal.javabasics.forms;

import java.awt.Color;
import java.awt.DefaultKeyboardFocusManager;
import java.awt.KeyboardFocusManager;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.KeyStroke;

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
        private double memoryValue = 0.0;
        private boolean isFirstSolve = true;
        private boolean isIntermidiateOperation = false;
        private boolean isRadians = true;
        private boolean isReversedPow = false;
        
        //String
        private void setDisplay(String display){
            //переводим тчк в зпт
            if (display.contains(".")){
                display = display.replace(".", ",");
            }
            
            //удаляем последние нули
            if (display.contains(",") && !display.contains("E")){
                while (display.endsWith("0") && !display.endsWith(",0")){
                    display = display.substring(0, display.length() - 1);
                }
                if (display.endsWith(",0")){
                    display = display.substring(0, display.indexOf(",0"));
                }
            }
            
            //сокращаем если есть экспонента
            if (display.contains("E") && display.length() > 16){
                String expo = display.substring(display.indexOf("E"));
                display = display.substring(0, 16 - expo.length()) + expo;
            } else if (display.length() > 18){
                display = display.substring(0, 16);
            }
            
            //обрабатываем Infinity и NaN
            if (display.contains("Infinity")){
                display = "Бесконечность";
            } else if (display.contains("NaN")){
                display = "Не достижимо";
            }
            
            displayText.setText(display);
        }
        
        //double
        private double getDoubleDisplay(){
                parsedString = displayText.getText();
            if (parsedString.contains(",")){
                parsedString = parsedString.replace(",", ".");
            }
            try{
                parsedDouble = Double.parseDouble(parsedString);
                return parsedDouble;
            } catch (NumberFormatException e){
                System.out.println("Ошибка ввода");
                e.getStackTrace();
            }
            return 0.0;
        }
        
        private void setDisplayIfComma(){
            if (isIntermidiateOperation) {
                setDisplay("0,");
                isIntermidiateOperation = false;
            }
            parsedString = displayText.getText();
            if (!parsedString.contains(",")){
                setDisplay(parsedString + ",");
            }
        }
        
        private void eraseAllData(){
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
                    eraseAllData();
                    setDisplay("Деление на 0");
                    turnOffButtonsOnDivideError(true);
                } else if ((firstValue == 0) && (secondValue > 0)){
                    eraseAllData();
                    setDisplay("+бесконечность");
                    turnOffButtonsOnDivideError(true);
                } else if ((firstValue == 0) && (secondValue < 0)){
                    eraseAllData();
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
                        eraseAllData();
                        setDisplay("Деление на 0");
                        calcButtonDivide.setEnabled(false);
                        turnOffButtonsOnDivideError(true);
                        setSign("");
                    } else if ((firstValue == 0) && (secondValue > 0)){
                        eraseAllData();
                        setDisplay("+бесконечность");
                        turnOffButtonsOnDivideError(true);
                        setSign("");
                    } else if ((firstValue == 0) && (secondValue < 0)){
                        eraseAllData();
                        setDisplay("-бесконечность");
                        turnOffButtonsOnDivideError(true);
                        setSign("");
                    } else {
                        firstValue /= secondValue;
                        setDisplay(firstValue + "");
                    }
                } else if (getSign().contains("^")){
                    if (isReversedPow){
                        secondValue = getDoubleDisplay();
                        firstValue = Math.pow(secondValue, firstValue);
                        setDisplay(firstValue + "");
                    } else {
                        secondValue = getDoubleDisplay();
                        firstValue = Math.pow(firstValue, secondValue);
                        setDisplay(firstValue + "");
                    }
                } else if (getSign().contains("E")){
                    setSign("");
                    setDisplay(getDoubleDisplay() + "");
                } else if (getSign().contains("log")){
                    setSign("");
                    secondValue = getDoubleDisplay();
                    firstValue = Math.log(secondValue) / Math.log(firstValue);
                    setDisplay(firstValue + "");
                } else if (getSign().contains("√")){
                    setSign("");
                    secondValue = getDoubleDisplay();
                    firstValue = Math.pow(secondValue, (1 / firstValue) );
                    setDisplay(firstValue + "");
                }
            isFirstSolve = true;
            isIntermidiateOperation = true;
        }
        
        private void pressButton0(){
            if (isIntermidiateOperation) {
                setDisplay("");
                isIntermidiateOperation = false;
            }
            if (!isDisplayOfflimit() && !isZeroOnDisplay()){
                addDigitOnDisplay(0);
                turnOffButtonsOnDivideError(false);
            }
        }
        
        private void pressButton1(){
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
        }
        
        private void pressButton2(){
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
        }
        
        private void pressButton3(){
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
        }
        
        private void pressButton4(){
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
        }
        
        private void pressButton5(){
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
        }
        
        private void pressButton6(){
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
        }
        
        private void pressButton7(){
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
        }
        
        private void pressButton8(){
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
        }
        
        private void pressButton9(){
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
        }
        
        private void pressButtonComma(){
            if(!isDisplayOfflimit()){
            setDisplayIfComma();
            }
        }
        
        Action press0;
        Action press1;
        Action press2;
        Action press3;
        Action press4;
        Action press5;
        Action press6;
        Action press7;
        Action press8;
        Action press9;
        Action pressComma;
        Action pressEnter;
        Action pressSolve;
        Action pressPlus;
        Action pressMinus;
        Action pressMultiply;
        Action pressDivide;
        Action pressDel;
        
        public class Press0 extends AbstractAction{
            @Override
            public void actionPerformed(ActionEvent e) {
                pressButton0();
            }
        }
        
        public class Press1 extends AbstractAction{
            @Override
            public void actionPerformed(ActionEvent e) {
                pressButton1();
            }
        }
        
        public class Press2 extends AbstractAction{
            @Override
            public void actionPerformed(ActionEvent e) {
                pressButton2();
            }
        }
        
        public class Press3 extends AbstractAction{
            @Override
            public void actionPerformed(ActionEvent e) {
                pressButton3();
            }
        }
        
        public class Press4 extends AbstractAction{
            @Override
            public void actionPerformed(ActionEvent e) {
                pressButton4();
            }
        }
        
        public class Press5 extends AbstractAction{
            @Override
            public void actionPerformed(ActionEvent e) {
                pressButton5();
            }
        }
        
        public class Press6 extends AbstractAction{
            @Override
            public void actionPerformed(ActionEvent e) {
                pressButton6();
            }
        }
        
        public class Press7 extends AbstractAction{
            @Override
            public void actionPerformed(ActionEvent e) {
                pressButton7();
            }
        }
        
        public class Press8 extends AbstractAction{
            @Override
            public void actionPerformed(ActionEvent e) {
                pressButton8();
            }
        }
        
        public class Press9 extends AbstractAction{
            @Override
            public void actionPerformed(ActionEvent e) {
                pressButton9();
            }
        }
        
        public class PressComma extends AbstractAction{
            @Override
            public void actionPerformed(ActionEvent e) {
                pressButtonComma();
            }
        }
        
        public class PressSolve extends AbstractAction{
            @Override
            public void actionPerformed(ActionEvent e) {
                solveAll();
            }
        }
        
        public class PressPlus extends AbstractAction{
            @Override
            public void actionPerformed(ActionEvent e) {
                plusOperation();
            }
        }
        
        public class PressMinus extends AbstractAction{
            @Override
            public void actionPerformed(ActionEvent e) {
                minusOperation();
            }
        }
        
        public class PressMultiply extends AbstractAction{
            @Override
            public void actionPerformed(ActionEvent e) {
                multiplyOperation();
            }
        }
        
        public class PressDivide extends AbstractAction{
            @Override
            public void actionPerformed(ActionEvent e) {
                divideOperation();
            }
        }
        
        public class PressDel extends AbstractAction{
            @Override
            public void actionPerformed(ActionEvent e) {
                eraseAllData();
                turnOffButtonsOnDivideError(false);
            }
        }
        
        
    /**
     * Creates new form CalcForm
     */
    public CalcForm() {
        initComponents();
        
        press0 = new Press0();
        press1 = new Press1();
        press2 = new Press2();
        press3 = new Press3();
        press4 = new Press4();
        press5 = new Press5();
        press6 = new Press6();
        press7 = new Press7();
        press8 = new Press8();
        press9 = new Press9();
        pressComma = new PressComma();
        pressSolve = new PressSolve();
        pressPlus = new PressPlus();
        pressMinus = new PressMinus();
        pressMultiply = new PressMultiply();
        pressDivide = new PressDivide();
        pressDel = new PressDel();
        
        KeyboardFocusManager.setCurrentKeyboardFocusManager(new DefaultKeyboardFocusManager(){
            @Override
            public boolean dispatchKeyEvent(KeyEvent e) {
                if (e.getKeyLocation() == KeyEvent.KEY_LOCATION_NUMPAD){
                    return true;
                }
                return super.dispatchKeyEvent(e);
            }
        });
        
        jPanel1.getInputMap().put(KeyStroke.getKeyStroke('0'), "press0");
        jPanel1.getActionMap().put("press0", press0);
        jPanel1.getInputMap().put(KeyStroke.getKeyStroke('1'), "press1");
        jPanel1.getActionMap().put("press1", press1);
        jPanel1.getInputMap().put(KeyStroke.getKeyStroke('2'), "press2");
        jPanel1.getActionMap().put("press2", press2);
        jPanel1.getInputMap().put(KeyStroke.getKeyStroke('3'), "press3");
        jPanel1.getActionMap().put("press3", press3);
        jPanel1.getInputMap().put(KeyStroke.getKeyStroke('4'), "press4");
        jPanel1.getActionMap().put("press4", press4);
        jPanel1.getInputMap().put(KeyStroke.getKeyStroke('5'), "press5");
        jPanel1.getActionMap().put("press5", press5);
        jPanel1.getInputMap().put(KeyStroke.getKeyStroke('6'), "press6");
        jPanel1.getActionMap().put("press6", press6);
        jPanel1.getInputMap().put(KeyStroke.getKeyStroke('7'), "press7");
        jPanel1.getActionMap().put("press7", press7);
        jPanel1.getInputMap().put(KeyStroke.getKeyStroke('8'), "press8");
        jPanel1.getActionMap().put("press8", press8);
        jPanel1.getInputMap().put(KeyStroke.getKeyStroke('9'), "press9");
        jPanel1.getActionMap().put("press9", press9);
        
        jPanel1.getInputMap().put(KeyStroke.getKeyStroke(','), "pressComma");
        jPanel1.getActionMap().put("pressComma", pressComma);
        jPanel1.getInputMap().put(KeyStroke.getKeyStroke('.'), "pressComma");
        jPanel1.getActionMap().put("pressComma", pressComma);
        jPanel1.getInputMap().put(KeyStroke.getKeyStroke('\n'), "pressSolve");
        jPanel1.getActionMap().put("pressSolve", pressSolve);
        jPanel1.getInputMap().put(KeyStroke.getKeyStroke('+'), "pressPlus");
        jPanel1.getActionMap().put("pressPlus", pressPlus);
        jPanel1.getInputMap().put(KeyStroke.getKeyStroke('-'), "pressMinus");
        jPanel1.getActionMap().put("pressMinus", pressMinus);
        jPanel1.getInputMap().put(KeyStroke.getKeyStroke('*'), "pressMultiply");
        jPanel1.getActionMap().put("pressMultiply", pressMultiply);
        jPanel1.getInputMap().put(KeyStroke.getKeyStroke('/'), "pressDivide");
        jPanel1.getActionMap().put("pressDivide", pressDivide);
        jPanel1.getInputMap().put(KeyStroke.getKeyStroke((char) 127), "pressDel");
        jPanel1.getActionMap().put("pressDel", pressDel);
        
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
        specButtonRadians = new javax.swing.JButton();
        specButtonMR = new javax.swing.JButton();
        specButtonMPlus = new javax.swing.JButton();
        specButtonMMinus = new javax.swing.JButton();
        specButtonMC = new javax.swing.JButton();
        specButtonSin = new javax.swing.JButton();
        specButtonCos = new javax.swing.JButton();
        specButtonTg = new javax.swing.JButton();
        specButtonCtg = new javax.swing.JButton();
        specButtonX2 = new javax.swing.JButton();
        specButtonX3 = new javax.swing.JButton();
        specButtonXY = new javax.swing.JButton();
        specButtonYX = new javax.swing.JButton();
        specButtonDivideX = new javax.swing.JButton();
        specButtonRootX = new javax.swing.JButton();
        specButton3RootX = new javax.swing.JButton();
        specButtonYRootX = new javax.swing.JButton();
        specButtonLn = new javax.swing.JButton();
        specButtonLog2 = new javax.swing.JButton();
        specButtonLog10 = new javax.swing.JButton();
        specButtonLogYX = new javax.swing.JButton();
        specButtonPi = new javax.swing.JButton();
        specButtonN = new javax.swing.JButton();
        specButtonE = new javax.swing.JButton();
        specButtonEX = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Калькулятор");
        setAlwaysOnTop(true);
        setResizable(false);

        jPanel1.setBackground(new java.awt.Color(50, 51, 52));

        jPanel2.setBackground(new java.awt.Color(43, 44, 45));
        jPanel2.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        calcButton0.setBackground(new java.awt.Color(25, 25, 25));
        calcButton0.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        calcButton0.setForeground(new java.awt.Color(101, 224, 255));
        calcButton0.setText("0");
        calcButton0.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        calcButton0.setFocusPainted(false);
        calcButton0.setFocusable(false);
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
        calcButton1.setFocusable(false);
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
        calcButton2.setFocusable(false);
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
        calcButton3.setFocusable(false);
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
        calcButton4.setFocusable(false);
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
        calcButton5.setFocusable(false);
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
        calcButton6.setFocusable(false);
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
        calcButton7.setFocusable(false);
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
        calcButton8.setFocusable(false);
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
        calcButton9.setFocusable(false);
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
        calcButtonComma.setFocusable(false);
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
        calcButtonSolve.setFocusable(false);
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
        calcButtonPlus.setFocusable(false);
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
        calcButtonMinus.setFocusable(false);
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
        calcButtonMultiply.setFocusable(false);
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
        calcButtonDivide.setFocusable(false);
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
        calcButtonPercent.setFocusable(false);
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
        calcButtonPlusMinus.setFocusable(false);
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
                .addContainerGap()
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
        displaySign.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(displayText, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(displaySign, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(displaySign, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(displayText, javax.swing.GroupLayout.Alignment.TRAILING)))
        );

        jPanel4.setBackground(new java.awt.Color(43, 44, 45));
        jPanel4.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        specButtonRadians.setBackground(new java.awt.Color(25, 25, 25));
        specButtonRadians.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        specButtonRadians.setForeground(new java.awt.Color(101, 224, 255));
        specButtonRadians.setText("RAD");
        specButtonRadians.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        specButtonRadians.setFocusPainted(false);
        specButtonRadians.setFocusable(false);
        specButtonRadians.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                specButtonRadiansActionPerformed(evt);
            }
        });

        specButtonMR.setBackground(new java.awt.Color(25, 25, 25));
        specButtonMR.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        specButtonMR.setForeground(new java.awt.Color(101, 224, 255));
        specButtonMR.setText("MR");
        specButtonMR.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        specButtonMR.setEnabled(false);
        specButtonMR.setFocusPainted(false);
        specButtonMR.setFocusable(false);
        specButtonMR.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        specButtonMR.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                specButtonMRActionPerformed(evt);
            }
        });

        specButtonMPlus.setBackground(new java.awt.Color(25, 25, 25));
        specButtonMPlus.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        specButtonMPlus.setForeground(new java.awt.Color(101, 224, 255));
        specButtonMPlus.setText("M+");
        specButtonMPlus.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        specButtonMPlus.setFocusPainted(false);
        specButtonMPlus.setFocusable(false);
        specButtonMPlus.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                specButtonMPlusActionPerformed(evt);
            }
        });

        specButtonMMinus.setBackground(new java.awt.Color(25, 25, 25));
        specButtonMMinus.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        specButtonMMinus.setForeground(new java.awt.Color(101, 224, 255));
        specButtonMMinus.setText("M-");
        specButtonMMinus.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        specButtonMMinus.setFocusPainted(false);
        specButtonMMinus.setFocusable(false);
        specButtonMMinus.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                specButtonMMinusActionPerformed(evt);
            }
        });

        specButtonMC.setBackground(new java.awt.Color(25, 25, 25));
        specButtonMC.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        specButtonMC.setForeground(new java.awt.Color(101, 224, 255));
        specButtonMC.setText("MC");
        specButtonMC.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        specButtonMC.setFocusPainted(false);
        specButtonMC.setFocusable(false);
        specButtonMC.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                specButtonMCActionPerformed(evt);
            }
        });

        specButtonSin.setBackground(new java.awt.Color(25, 25, 25));
        specButtonSin.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        specButtonSin.setForeground(new java.awt.Color(101, 224, 255));
        specButtonSin.setText("sin");
        specButtonSin.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        specButtonSin.setFocusPainted(false);
        specButtonSin.setFocusable(false);
        specButtonSin.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                specButtonSinActionPerformed(evt);
            }
        });

        specButtonCos.setBackground(new java.awt.Color(25, 25, 25));
        specButtonCos.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        specButtonCos.setForeground(new java.awt.Color(101, 224, 255));
        specButtonCos.setText("cos");
        specButtonCos.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        specButtonCos.setFocusPainted(false);
        specButtonCos.setFocusable(false);
        specButtonCos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                specButtonCosActionPerformed(evt);
            }
        });

        specButtonTg.setBackground(new java.awt.Color(25, 25, 25));
        specButtonTg.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        specButtonTg.setForeground(new java.awt.Color(101, 224, 255));
        specButtonTg.setText("tg");
        specButtonTg.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        specButtonTg.setFocusPainted(false);
        specButtonTg.setFocusable(false);
        specButtonTg.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                specButtonTgActionPerformed(evt);
            }
        });

        specButtonCtg.setBackground(new java.awt.Color(25, 25, 25));
        specButtonCtg.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        specButtonCtg.setForeground(new java.awt.Color(101, 224, 255));
        specButtonCtg.setText("ctg");
        specButtonCtg.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        specButtonCtg.setFocusPainted(false);
        specButtonCtg.setFocusable(false);
        specButtonCtg.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                specButtonCtgActionPerformed(evt);
            }
        });

        specButtonX2.setBackground(new java.awt.Color(25, 25, 25));
        specButtonX2.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        specButtonX2.setForeground(new java.awt.Color(101, 224, 255));
        specButtonX2.setText("x²");
        specButtonX2.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        specButtonX2.setFocusPainted(false);
        specButtonX2.setFocusable(false);
        specButtonX2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                specButtonX2ActionPerformed(evt);
            }
        });

        specButtonX3.setBackground(new java.awt.Color(25, 25, 25));
        specButtonX3.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        specButtonX3.setForeground(new java.awt.Color(101, 224, 255));
        specButtonX3.setText("x³");
        specButtonX3.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        specButtonX3.setFocusPainted(false);
        specButtonX3.setFocusable(false);
        specButtonX3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                specButtonX3ActionPerformed(evt);
            }
        });

        specButtonXY.setBackground(new java.awt.Color(25, 25, 25));
        specButtonXY.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        specButtonXY.setForeground(new java.awt.Color(101, 224, 255));
        specButtonXY.setText("x^y");
        specButtonXY.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        specButtonXY.setFocusPainted(false);
        specButtonXY.setFocusable(false);
        specButtonXY.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                specButtonXYActionPerformed(evt);
            }
        });

        specButtonYX.setBackground(new java.awt.Color(25, 25, 25));
        specButtonYX.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        specButtonYX.setForeground(new java.awt.Color(101, 224, 255));
        specButtonYX.setText("y^x");
        specButtonYX.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        specButtonYX.setFocusPainted(false);
        specButtonYX.setFocusable(false);
        specButtonYX.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                specButtonYXActionPerformed(evt);
            }
        });

        specButtonDivideX.setBackground(new java.awt.Color(25, 25, 25));
        specButtonDivideX.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        specButtonDivideX.setForeground(new java.awt.Color(101, 224, 255));
        specButtonDivideX.setText("1/x");
        specButtonDivideX.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        specButtonDivideX.setFocusPainted(false);
        specButtonDivideX.setFocusable(false);
        specButtonDivideX.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                specButtonDivideXActionPerformed(evt);
            }
        });

        specButtonRootX.setBackground(new java.awt.Color(25, 25, 25));
        specButtonRootX.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        specButtonRootX.setForeground(new java.awt.Color(101, 224, 255));
        specButtonRootX.setText("√x");
        specButtonRootX.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        specButtonRootX.setFocusPainted(false);
        specButtonRootX.setFocusable(false);
        specButtonRootX.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                specButtonRootXActionPerformed(evt);
            }
        });

        specButton3RootX.setBackground(new java.awt.Color(25, 25, 25));
        specButton3RootX.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        specButton3RootX.setForeground(new java.awt.Color(101, 224, 255));
        specButton3RootX.setText("3√x");
        specButton3RootX.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        specButton3RootX.setFocusPainted(false);
        specButton3RootX.setFocusable(false);
        specButton3RootX.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                specButton3RootXActionPerformed(evt);
            }
        });

        specButtonYRootX.setBackground(new java.awt.Color(25, 25, 25));
        specButtonYRootX.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        specButtonYRootX.setForeground(new java.awt.Color(101, 224, 255));
        specButtonYRootX.setText("y√x");
        specButtonYRootX.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        specButtonYRootX.setFocusPainted(false);
        specButtonYRootX.setFocusable(false);
        specButtonYRootX.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                specButtonYRootXActionPerformed(evt);
            }
        });

        specButtonLn.setBackground(new java.awt.Color(25, 25, 25));
        specButtonLn.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        specButtonLn.setForeground(new java.awt.Color(101, 224, 255));
        specButtonLn.setText("ln");
        specButtonLn.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        specButtonLn.setFocusPainted(false);
        specButtonLn.setFocusable(false);
        specButtonLn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                specButtonLnActionPerformed(evt);
            }
        });

        specButtonLog2.setBackground(new java.awt.Color(25, 25, 25));
        specButtonLog2.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        specButtonLog2.setForeground(new java.awt.Color(101, 224, 255));
        specButtonLog2.setText("log₂");
        specButtonLog2.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        specButtonLog2.setFocusPainted(false);
        specButtonLog2.setFocusable(false);
        specButtonLog2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                specButtonLog2ActionPerformed(evt);
            }
        });

        specButtonLog10.setBackground(new java.awt.Color(25, 25, 25));
        specButtonLog10.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        specButtonLog10.setForeground(new java.awt.Color(101, 224, 255));
        specButtonLog10.setText("log₁₀");
        specButtonLog10.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        specButtonLog10.setFocusPainted(false);
        specButtonLog10.setFocusable(false);
        specButtonLog10.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                specButtonLog10ActionPerformed(evt);
            }
        });

        specButtonLogYX.setBackground(new java.awt.Color(25, 25, 25));
        specButtonLogYX.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        specButtonLogYX.setForeground(new java.awt.Color(101, 224, 255));
        specButtonLogYX.setText("logyX");
        specButtonLogYX.setToolTipText("");
        specButtonLogYX.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        specButtonLogYX.setFocusPainted(false);
        specButtonLogYX.setFocusable(false);
        specButtonLogYX.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                specButtonLogYXActionPerformed(evt);
            }
        });

        specButtonPi.setBackground(new java.awt.Color(25, 25, 25));
        specButtonPi.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        specButtonPi.setForeground(new java.awt.Color(101, 224, 255));
        specButtonPi.setText("pi ");
        specButtonPi.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        specButtonPi.setFocusPainted(false);
        specButtonPi.setFocusable(false);
        specButtonPi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                specButtonPiActionPerformed(evt);
            }
        });

        specButtonN.setBackground(new java.awt.Color(25, 25, 25));
        specButtonN.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        specButtonN.setForeground(new java.awt.Color(101, 224, 255));
        specButtonN.setText("n!");
        specButtonN.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        specButtonN.setFocusPainted(false);
        specButtonN.setFocusable(false);
        specButtonN.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                specButtonNActionPerformed(evt);
            }
        });

        specButtonE.setBackground(new java.awt.Color(25, 25, 25));
        specButtonE.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        specButtonE.setForeground(new java.awt.Color(101, 224, 255));
        specButtonE.setText("e");
        specButtonE.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        specButtonE.setFocusPainted(false);
        specButtonE.setFocusable(false);
        specButtonE.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                specButtonEActionPerformed(evt);
            }
        });

        specButtonEX.setBackground(new java.awt.Color(25, 25, 25));
        specButtonEX.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        specButtonEX.setForeground(new java.awt.Color(101, 224, 255));
        specButtonEX.setText("e^x");
        specButtonEX.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        specButtonEX.setFocusPainted(false);
        specButtonEX.setFocusable(false);
        specButtonEX.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                specButtonEXActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(specButtonCos, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(specButtonSin, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(specButtonRadians, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(specButtonDivideX, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(specButtonX2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(specButtonMR, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(specButtonRootX, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(specButtonX3, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(specButtonMPlus, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(specButton3RootX, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(specButtonXY, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(specButtonMMinus, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(specButtonYRootX, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(specButtonYX, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(specButtonMC, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(specButtonCtg, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(specButtonTg, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(specButtonPi, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(specButtonLn, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(specButtonN, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(specButtonLog2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(specButtonE, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(specButtonLog10, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(specButtonEX, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(specButtonLogYX, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(specButtonRadians, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(specButtonSin, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(specButtonCos, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(specButtonMR, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(specButtonX2, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(specButtonDivideX, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(specButtonX3, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(specButtonRootX, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(specButtonMMinus, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(specButtonMPlus, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(specButtonXY, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(specButton3RootX, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(specButtonMC, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(specButtonYX, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(specButtonYRootX, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGap(56, 56, 56)
                        .addComponent(specButtonCtg, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                        .addComponent(specButtonTg, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(specButtonPi, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(specButtonLog2, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(specButtonLn, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(specButtonN, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(specButtonLog10, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(specButtonE, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(specButtonLogYX, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(specButtonEX, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
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
                        .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
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
        pressButton0();
    }//GEN-LAST:event_calcButton0ActionPerformed

    private void calcButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_calcButton1ActionPerformed
        pressButton1();
    }//GEN-LAST:event_calcButton1ActionPerformed

    private void calcButtonACActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_calcButtonACActionPerformed
        eraseAllData();
        turnOffButtonsOnDivideError(false);
    }//GEN-LAST:event_calcButtonACActionPerformed

    private void calcButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_calcButton2ActionPerformed
        pressButton2();
    }//GEN-LAST:event_calcButton2ActionPerformed

    private void calcButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_calcButton3ActionPerformed
        pressButton3();
    }//GEN-LAST:event_calcButton3ActionPerformed

    private void calcButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_calcButton4ActionPerformed
        pressButton4();
    }//GEN-LAST:event_calcButton4ActionPerformed

    private void calcButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_calcButton5ActionPerformed
        pressButton5();
    }//GEN-LAST:event_calcButton5ActionPerformed

    private void calcButton6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_calcButton6ActionPerformed
        pressButton6();
    }//GEN-LAST:event_calcButton6ActionPerformed

    private void calcButton7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_calcButton7ActionPerformed
        pressButton7();
    }//GEN-LAST:event_calcButton7ActionPerformed

    private void calcButton8ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_calcButton8ActionPerformed
        pressButton8();
    }//GEN-LAST:event_calcButton8ActionPerformed

    private void calcButton9ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_calcButton9ActionPerformed
        pressButton9();
    }//GEN-LAST:event_calcButton9ActionPerformed

    private void calcButtonCommaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_calcButtonCommaActionPerformed
        pressButtonComma();
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

    private void specButtonEXActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_specButtonEXActionPerformed
        firstValue = 2.7182818284590452353602874713527;
        setDisplay(firstValue + "");
        setSign("^");
        isFirstSolve = false;
        isIntermidiateOperation = true;
    }//GEN-LAST:event_specButtonEXActionPerformed

    private void specButtonLogYXActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_specButtonLogYXActionPerformed
        setSign("log");
        firstValue = getDoubleDisplay();
        isIntermidiateOperation = true;
    }//GEN-LAST:event_specButtonLogYXActionPerformed

    private void specButtonYRootXActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_specButtonYRootXActionPerformed
        setSign("√");
        firstValue = getDoubleDisplay();
        isIntermidiateOperation = true;
    }//GEN-LAST:event_specButtonYRootXActionPerformed

    private void specButtonYXActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_specButtonYXActionPerformed
        setSign("^");
        isReversedPow = true;
        firstValue = getDoubleDisplay();
        isIntermidiateOperation = true;
    }//GEN-LAST:event_specButtonYXActionPerformed

    private void specButtonMCActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_specButtonMCActionPerformed
        specButtonMR.setEnabled(false);
        memoryValue = 0;
        specButtonMR.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        specButtonMC.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        specButtonMR.setForeground(new Color(101, 224, 255));
    }//GEN-LAST:event_specButtonMCActionPerformed

    private void specButtonMMinusActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_specButtonMMinusActionPerformed
        memoryValue -= getDoubleDisplay();
    }//GEN-LAST:event_specButtonMMinusActionPerformed

    private void specButtonXYActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_specButtonXYActionPerformed
        setSign("^");
        isReversedPow = false;
        firstValue = getDoubleDisplay();
        isIntermidiateOperation = true;
    }//GEN-LAST:event_specButtonXYActionPerformed

    private void specButton3RootXActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_specButton3RootXActionPerformed
        setDisplay(Math.cbrt(getDoubleDisplay()) + "");
    }//GEN-LAST:event_specButton3RootXActionPerformed

    private void specButtonLog10ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_specButtonLog10ActionPerformed
        setDisplay(Math.log10(getDoubleDisplay()) + "");
    }//GEN-LAST:event_specButtonLog10ActionPerformed

    private void specButtonEActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_specButtonEActionPerformed
        setDisplay("2,71828182845904");
    }//GEN-LAST:event_specButtonEActionPerformed

    private void specButtonMPlusActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_specButtonMPlusActionPerformed
        memoryValue += getDoubleDisplay();
        specButtonMR.setEnabled(true);
        specButtonMR.setBorder(javax.swing.BorderFactory.createMatteBorder(2, 0, 0, 0, new Color(255, 105, 0)));
        specButtonMC.setBorder(javax.swing.BorderFactory.createMatteBorder(2, 0, 0, 0, new Color(55, 205, 255)));
        specButtonMR.setForeground(new Color(205,105,0));
    }//GEN-LAST:event_specButtonMPlusActionPerformed

    private void specButtonX3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_specButtonX3ActionPerformed
        setDisplay(Math.pow(getDoubleDisplay(), 3) + "");
    }//GEN-LAST:event_specButtonX3ActionPerformed

    private void specButtonRootXActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_specButtonRootXActionPerformed
        setDisplay(Math.sqrt(getDoubleDisplay()) + "");
    }//GEN-LAST:event_specButtonRootXActionPerformed

    private void specButtonLog2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_specButtonLog2ActionPerformed
        setDisplay( (Math.log10(getDoubleDisplay()) / Math.log10(2)) + "" );
    }//GEN-LAST:event_specButtonLog2ActionPerformed

    private void specButtonNActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_specButtonNActionPerformed
            firstValue = getDoubleDisplay();
            double result = 1;
            if(firstValue % 1 == 0){
                for (int i = 1; i <= (int) firstValue; i++){
                    result *= i;
                }
            } else {
                int digit = (int) firstValue;
                double tail = firstValue % 1;
                int first = 1;
                for (int i = 1; i <= digit; i++){
                    first *= i;
                }
                result = Math.log(first) + tail * Math.log(digit + 1);
                result = Math.pow(Math.E, result);
            }
            setDisplay(result + "");
    }//GEN-LAST:event_specButtonNActionPerformed

    private void specButtonMRActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_specButtonMRActionPerformed
        setDisplay(memoryValue + "");
    }//GEN-LAST:event_specButtonMRActionPerformed

    private void specButtonX2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_specButtonX2ActionPerformed
        setDisplay(Math.pow(getDoubleDisplay(), 2) + "");
    }//GEN-LAST:event_specButtonX2ActionPerformed

    private void specButtonDivideXActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_specButtonDivideXActionPerformed
        setDisplay( (1 / getDoubleDisplay()) + "" );
    }//GEN-LAST:event_specButtonDivideXActionPerformed

    private void specButtonLnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_specButtonLnActionPerformed
        setDisplay(Math.log(getDoubleDisplay()) + "");
    }//GEN-LAST:event_specButtonLnActionPerformed

    private void specButtonPiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_specButtonPiActionPerformed
        setDisplay("3,14159265358979");
    }//GEN-LAST:event_specButtonPiActionPerformed

    private void specButtonRadiansActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_specButtonRadiansActionPerformed
        if (specButtonRadians.getText().equals("RAD")){
            specButtonRadians.setText("DEG");
            specButtonRadians.setBorder(javax.swing.BorderFactory.createMatteBorder(2, 0, 0, 0, new Color(205, 105, 0)));
            specButtonRadians.setForeground(new Color(205,105,0));
            isRadians = false;
        } else {
            specButtonRadians.setText("RAD");
            specButtonRadians.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 0, 0, new Color(0,0,0)));
            specButtonRadians.setForeground(new Color(101, 224, 255));
            isRadians = true;
        }
    }//GEN-LAST:event_specButtonRadiansActionPerformed

    private void specButtonSinActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_specButtonSinActionPerformed
        if (isRadians){
            firstValue = Math.sin(getDoubleDisplay());
        } else {
            firstValue = Math.sin(Math.toRadians(getDoubleDisplay()));
        }
        setDisplay(firstValue + "");
    }//GEN-LAST:event_specButtonSinActionPerformed

    private void specButtonCosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_specButtonCosActionPerformed
        if (isRadians){
            firstValue = Math.cos(getDoubleDisplay());
        } else {
            firstValue = Math.cos(Math.toRadians(getDoubleDisplay()));
        }
        setDisplay(firstValue + "");
    }//GEN-LAST:event_specButtonCosActionPerformed

    private void specButtonTgActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_specButtonTgActionPerformed
        if (isRadians){
            firstValue = Math.tan(getDoubleDisplay());
        } else {
            firstValue = Math.tan(Math.toRadians(getDoubleDisplay()));
        }
        setDisplay(firstValue + "");
    }//GEN-LAST:event_specButtonTgActionPerformed

    private void specButtonCtgActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_specButtonCtgActionPerformed
        if (isRadians){
            firstValue = 1 / Math.tan(getDoubleDisplay());
        } else {
            firstValue = 1 / Math.tan(Math.toRadians(getDoubleDisplay()));
        }
        setDisplay(firstValue + "");
    }//GEN-LAST:event_specButtonCtgActionPerformed

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
    private javax.swing.JButton specButton3RootX;
    private javax.swing.JButton specButtonCos;
    private javax.swing.JButton specButtonCtg;
    private javax.swing.JButton specButtonDivideX;
    private javax.swing.JButton specButtonE;
    private javax.swing.JButton specButtonEX;
    private javax.swing.JButton specButtonLn;
    private javax.swing.JButton specButtonLog10;
    private javax.swing.JButton specButtonLog2;
    private javax.swing.JButton specButtonLogYX;
    private javax.swing.JButton specButtonMC;
    private javax.swing.JButton specButtonMMinus;
    private javax.swing.JButton specButtonMPlus;
    private javax.swing.JButton specButtonMR;
    private javax.swing.JButton specButtonN;
    private javax.swing.JButton specButtonPi;
    private javax.swing.JButton specButtonRadians;
    private javax.swing.JButton specButtonRootX;
    private javax.swing.JButton specButtonSin;
    private javax.swing.JButton specButtonTg;
    private javax.swing.JButton specButtonX2;
    private javax.swing.JButton specButtonX3;
    private javax.swing.JButton specButtonXY;
    private javax.swing.JButton specButtonYRootX;
    private javax.swing.JButton specButtonYX;
    // End of variables declaration//GEN-END:variables
}
