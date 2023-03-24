/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package com.palmal.javabasics;

import java.awt.Color;
import java.awt.Toolkit;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import javax.swing.DefaultListModel;
import javax.swing.JOptionPane;

/**
 *
 * @author vk_wh
 */
public class ClockForm extends javax.swing.JFrame {
    
    //Alarm clock
    private SimpleDateFormat sdfCurrentTime = new SimpleDateFormat("HH:mm:ss");
    private SimpleDateFormat sdfCurrentDate = new SimpleDateFormat("EEEE d MMMM y");
    private Date currentDate;
    private Date currentTime;
    private Date alarmTime;
    private boolean isCharged = false;
    
    //Timer
    private int count = 0;
    private int startingPoint = 0;
    private boolean isCount = false;
    
    //Stopwatch
    private List<String> lapsList = new ArrayList<>();
    private long startTimeSW = 0;
    private long currentTimeSW = 0;
    private boolean isStartSW = false;
    private Calendar calendarSW = new GregorianCalendar();
    private DefaultListModel<String> dlm = new DefaultListModel<>();
            
    void initClock(){
        currentDate = Calendar.getInstance().getTime();
        jTextFieldCurrentTime.setText(sdfCurrentTime.format(currentDate));
        jTextFieldCurrentDate.setText(sdfCurrentDate.format(currentDate));
        try {
            alarmTime = sdfCurrentTime.parse(jTextFieldAlarmTime.getText());
            currentTime = sdfCurrentTime.parse(jTextFieldCurrentTime.getText());
        } catch (ParseException ex) {
            JOptionPane.showMessageDialog(this.getContentPane(),
                "Неправильный формат времени\nУстановите время в формате 01:23:45",
                "Ошибка ввода", JOptionPane.ERROR_MESSAGE);
            jTextFieldAlarmTime.setText(sdfCurrentTime.format(currentDate));
        }
        if (isCharged && currentTime.after(alarmTime)) playAlarm();
        if (isCount) {
            if (count <= 0) {
                Toolkit.getDefaultToolkit().beep();
            }
            else {
                count--;
                updateTimer();
            }
        }
        jListLaps.setModel(dlm);
    }
    
    private void setAlarm(){
        currentDate = Calendar.getInstance().getTime();
        jTextFieldAlarmTime.setText(sdfCurrentTime.format(currentDate));
    }
    
    private void playAlarm(){
        Toolkit.getDefaultToolkit().beep();
            if (jPanelAlarmDisplay.getBackground().equals(Color.RED)){
                jPanelAlarmDisplay.setBackground(Color.BLUE);
            } else {
                jPanelAlarmDisplay.setBackground(Color.RED);
            }
    }
    
    private void paintAlarmButton(){
        if (!isCharged){
            jTextFieldAlarmTime.setEnabled(false);
            isCharged = true;
            jButtonAlarmCharge.setBackground(Color.GREEN);
            jButtonAlarmCharge.setText("Вкл");
            jLabelAlarmStatus.setText("Alarm on");
            jLabelAlarmStatus.setForeground(new Color(0,255,0));
        } else {
            jTextFieldAlarmTime.setEnabled(true);
            isCharged = false;
            jPanelAlarmDisplay.setBackground(new Color(37,37,37));
            jButtonAlarmCharge.setBackground(Color.RED);
            jButtonAlarmCharge.setText("Выкл");
            jLabelAlarmStatus.setText("Alarm off");
            jLabelAlarmStatus.setForeground(new Color(0,0,0));
        }
    }
    
    //Timer
    private void timerStart(){
        isCount = true;
    }
    
    private void timerPause(){
        isCount = false;
    }
    
    private void timerReset(){
        isCount = false;
        count = startingPoint;
        updateTimer();
    }
    
    private void setUpTimer(){
        try{
            int hours = Integer.parseInt(jTextFieldTimerHours.getText());
            int minutes = Integer.parseInt(jTextFieldTimerMinutes.getText());
            int seconds = Integer.parseInt(jTextFieldTimerSeconds.getText());
            count = hours * 3600 + minutes * 60 + seconds;
            startingPoint = count;
        } catch (NumberFormatException | NullPointerException e){
            JOptionPane.showMessageDialog(this.getContentPane(),
                "Неправильный формат таймера\nУстановите время в формате 01 23 45",
                "Ошибка ввода", JOptionPane.ERROR_MESSAGE);
            timerReset();
        }
    }
    
    private void updateTimer(){
        String hours = String.valueOf(count / 3600);
        String minutes = String.valueOf(count % 3600 / 60);
        String seconds = String.valueOf(count % 3600 % 60);
        if (hours.length() < 2) hours = "0" + hours;
        if (minutes.length() < 2) minutes = "0" + minutes;
        if (seconds.length() < 2) seconds = "0" + seconds;
        jTextFieldTimerHours.setText(hours);
        jTextFieldTimerMinutes.setText(minutes);
        jTextFieldTimerSeconds.setText(seconds);
    }
    
    private void paintTimerStartButton(){
        if (isCount){
            jButtonTimerStart.setBackground(new Color(255,105,0));
            jButtonTimerStart.setForeground(new Color(37,37,37));
        } else {
            jButtonTimerStart.setBackground(new Color(37,37,37));
            jButtonTimerStart.setForeground(new Color(255,105,0));
        }
        
    }
    
    //Stopwatch
    private void startSW(){
        if (!isStartSW){
            isStartSW = true;
            if (calendarSW.getTimeInMillis() > 0){
                startTimeSW = System.currentTimeMillis() - calendarSW.getTimeInMillis();
            } else {
                startTimeSW = System.currentTimeMillis();
            }
            paintSWStartButton();
        }
    }
    
    private void stopSW(){
        isStartSW = false;
        paintSWStartButton();
    }
    
    private void resetSW(){
        isStartSW = false;
        startTimeSW = 0;
        currentTimeSW = 0;
        calendarSW.setTimeInMillis(0);
        jTextFieldSWMinutes.setText("00");
        jTextFieldSWSeconds.setText("00");
        jTextFieldSWMillis.setText("000");
        paintSWStartButton();
    }
    
    void updateSW(){
        if (isStartSW){
            currentTimeSW = System.currentTimeMillis() - startTimeSW;
            //parse
            calendarSW.setTimeInMillis(currentTimeSW);
            String millisSW = String.valueOf(calendarSW.get(Calendar.MILLISECOND));
            String secsSW = String.valueOf(calendarSW.get(Calendar.SECOND));
            String minsSW = String.valueOf(calendarSW.get(Calendar.MINUTE));
            if (secsSW.length() < 2) secsSW = "0" + secsSW;
            if (minsSW.length() < 2) minsSW = "0" + minsSW;
            jTextFieldSWMillis.setText(millisSW);
            jTextFieldSWSeconds.setText(secsSW);
            jTextFieldSWMinutes.setText(minsSW);
        }
    }
    
    private void lapSW(){
        if (isStartSW){
            String millis = jTextFieldSWMillis.getText();
            switch (millis.length()) {
                case 0 -> millis = "000";
                case 1 -> millis = "00" + millis;
                case 2 -> millis = "0" + millis;
            }
            String secs = jTextFieldSWSeconds.getText();
            if (secs.length() < 2) secs = "0" + secs;
            String mins = jTextFieldSWMinutes.getText();
            if (mins.length() < 2) mins = "0" + mins;
            dlm.add(0, dlm.size() + 1 + ") " + mins + ":" + secs + ":" + millis);
        }
    }
    
    private void resetLapsSW(){
        dlm.clear();
    }
    
    private void paintSWStartButton(){
        if (isStartSW){
        jButtonStartSW.setBackground(new Color(255,105,0));
        jButtonStartSW.setForeground(new Color(37,37,37));
        } else {
        jButtonStartSW.setBackground(new Color(37,37,37));
        jButtonStartSW.setForeground(new Color(255,105,0));
        }
    }
    
    /**
     * Creates new form ClockForm
     */
    public ClockForm() {
        initComponents();
        initClock();
        setAlarm();
        calendarSW.setTimeInMillis(0);
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
        jLabel1 = new javax.swing.JLabel();
        jTextFieldTimerHours = new javax.swing.JTextField();
        jTextFieldTimerMinutes = new javax.swing.JTextField();
        jTextFieldTimerSeconds = new javax.swing.JTextField();
        jButtonTimerStart = new javax.swing.JButton();
        jButtonTimerPause = new javax.swing.JButton();
        jButtonTimerReset = new javax.swing.JButton();
        jPanel4 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        jTextFieldCurrentTime = new javax.swing.JTextField();
        jTextFieldCurrentDate = new javax.swing.JTextField();
        jPanel5 = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        jTextFieldAlarmTime = new javax.swing.JTextField();
        jButtonAlarmCharge = new javax.swing.JButton();
        jPanelAlarmDisplay = new javax.swing.JPanel();
        jLabelAlarmStatus = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jTextFieldSWMillis = new javax.swing.JTextField();
        jTextFieldSWMinutes = new javax.swing.JTextField();
        jTextFieldSWSeconds = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        jListLaps = new javax.swing.JList<>();
        jButtonStartSW = new javax.swing.JButton();
        jButton5 = new javax.swing.JButton();
        jButton6 = new javax.swing.JButton();
        jButton7 = new javax.swing.JButton();
        jButton8 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Часы-будильник-секундомер-таймер");
        setAlwaysOnTop(true);
        setResizable(false);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                formWindowClosing(evt);
            }
        });

        jPanel1.setBackground(new java.awt.Color(32, 33, 34));

        jPanel2.setBackground(new java.awt.Color(43, 44, 45));

        jLabel1.setBackground(new java.awt.Color(50, 50, 50));
        jLabel1.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 105, 0));
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("Таймер");
        jLabel1.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);

        jTextFieldTimerHours.setBackground(new java.awt.Color(43, 44, 45));
        jTextFieldTimerHours.setFont(new java.awt.Font("Segoe UI", 0, 36)); // NOI18N
        jTextFieldTimerHours.setForeground(new java.awt.Color(255, 105, 0));
        jTextFieldTimerHours.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        jTextFieldTimerHours.setText("00");
        jTextFieldTimerHours.setCaretColor(new java.awt.Color(255, 255, 255));
        jTextFieldTimerHours.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jTextFieldTimerHoursKeyTyped(evt);
            }
        });

        jTextFieldTimerMinutes.setBackground(new java.awt.Color(43, 44, 45));
        jTextFieldTimerMinutes.setFont(new java.awt.Font("Segoe UI", 0, 36)); // NOI18N
        jTextFieldTimerMinutes.setForeground(new java.awt.Color(255, 105, 0));
        jTextFieldTimerMinutes.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        jTextFieldTimerMinutes.setText("01");
        jTextFieldTimerMinutes.setCaretColor(new java.awt.Color(255, 255, 255));
        jTextFieldTimerMinutes.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jTextFieldTimerMinutesKeyTyped(evt);
            }
        });

        jTextFieldTimerSeconds.setBackground(new java.awt.Color(43, 44, 45));
        jTextFieldTimerSeconds.setFont(new java.awt.Font("Segoe UI", 0, 36)); // NOI18N
        jTextFieldTimerSeconds.setForeground(new java.awt.Color(255, 105, 0));
        jTextFieldTimerSeconds.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        jTextFieldTimerSeconds.setText("00");
        jTextFieldTimerSeconds.setCaretColor(new java.awt.Color(255, 255, 255));
        jTextFieldTimerSeconds.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jTextFieldTimerSecondsKeyTyped(evt);
            }
        });

        jButtonTimerStart.setBackground(new java.awt.Color(37, 37, 37));
        jButtonTimerStart.setForeground(new java.awt.Color(255, 105, 0));
        jButtonTimerStart.setText("Start");
        jButtonTimerStart.setFocusPainted(false);
        jButtonTimerStart.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonTimerStartActionPerformed(evt);
            }
        });

        jButtonTimerPause.setBackground(new java.awt.Color(37, 37, 37));
        jButtonTimerPause.setForeground(new java.awt.Color(255, 105, 0));
        jButtonTimerPause.setText("Pause");
        jButtonTimerPause.setFocusPainted(false);
        jButtonTimerPause.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonTimerPauseActionPerformed(evt);
            }
        });

        jButtonTimerReset.setBackground(new java.awt.Color(37, 37, 37));
        jButtonTimerReset.setForeground(new java.awt.Color(255, 105, 0));
        jButtonTimerReset.setText("Reset");
        jButtonTimerReset.setFocusPainted(false);
        jButtonTimerReset.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonTimerResetActionPerformed(evt);
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
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jButtonTimerStart, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jTextFieldTimerHours))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jButtonTimerPause, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jTextFieldTimerMinutes))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jButtonTimerReset, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jTextFieldTimerSeconds)))
                    .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextFieldTimerMinutes, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextFieldTimerHours, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextFieldTimerSeconds, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButtonTimerStart)
                    .addComponent(jButtonTimerPause)
                    .addComponent(jButtonTimerReset))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel4.setBackground(new java.awt.Color(43, 44, 45));

        jLabel3.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(255, 105, 0));
        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel3.setText("Часы");

        jTextFieldCurrentTime.setEditable(false);
        jTextFieldCurrentTime.setBackground(new java.awt.Color(37, 37, 37));
        jTextFieldCurrentTime.setFont(new java.awt.Font("Verdana", 0, 36)); // NOI18N
        jTextFieldCurrentTime.setForeground(new java.awt.Color(255, 105, 0));
        jTextFieldCurrentTime.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        jTextFieldCurrentTime.setText("00:00:00");
        jTextFieldCurrentTime.setFocusable(false);
        jTextFieldCurrentTime.setRequestFocusEnabled(false);

        jTextFieldCurrentDate.setEditable(false);
        jTextFieldCurrentDate.setBackground(new java.awt.Color(37, 37, 37));
        jTextFieldCurrentDate.setForeground(new java.awt.Color(255, 105, 0));
        jTextFieldCurrentDate.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        jTextFieldCurrentDate.setText("понедельник 01 января 1990");
        jTextFieldCurrentDate.setRequestFocusEnabled(false);

        jPanel5.setBackground(new java.awt.Color(60, 60, 60));

        jLabel4.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(255, 105, 0));
        jLabel4.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel4.setText("Установить будильник");

        jTextFieldAlarmTime.setBackground(new java.awt.Color(37, 37, 37));
        jTextFieldAlarmTime.setFont(new java.awt.Font("Verdana", 0, 36)); // NOI18N
        jTextFieldAlarmTime.setForeground(new java.awt.Color(101, 224, 255));
        jTextFieldAlarmTime.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        jTextFieldAlarmTime.setText("00:00:00");

        jButtonAlarmCharge.setBackground(new java.awt.Color(105, 0, 0));
        jButtonAlarmCharge.setForeground(new java.awt.Color(255, 255, 255));
        jButtonAlarmCharge.setText("Выкл");
        jButtonAlarmCharge.setFocusPainted(false);
        jButtonAlarmCharge.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonAlarmChargeActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jButtonAlarmCharge, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jTextFieldAlarmTime, javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel4)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTextFieldAlarmTime, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButtonAlarmCharge)
                .addContainerGap())
        );

        jPanelAlarmDisplay.setBackground(new java.awt.Color(37, 37, 37));

        jLabelAlarmStatus.setBackground(new java.awt.Color(0, 0, 0));
        jLabelAlarmStatus.setFont(new java.awt.Font("Segoe UI", 0, 36)); // NOI18N
        jLabelAlarmStatus.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabelAlarmStatus.setText("Alarm off");
        jLabelAlarmStatus.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);

        javax.swing.GroupLayout jPanelAlarmDisplayLayout = new javax.swing.GroupLayout(jPanelAlarmDisplay);
        jPanelAlarmDisplay.setLayout(jPanelAlarmDisplayLayout);
        jPanelAlarmDisplayLayout.setHorizontalGroup(
            jPanelAlarmDisplayLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelAlarmDisplayLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabelAlarmStatus, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanelAlarmDisplayLayout.setVerticalGroup(
            jPanelAlarmDisplayLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelAlarmDisplayLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabelAlarmStatus, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jPanelAlarmDisplay, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jTextFieldCurrentDate)
                    .addComponent(jTextFieldCurrentTime)
                    .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTextFieldCurrentTime, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTextFieldCurrentDate, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanelAlarmDisplay, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        jPanel3.setBackground(new java.awt.Color(43, 44, 45));

        jLabel2.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 105, 0));
        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel2.setText("Секундомер");

        jTextFieldSWMillis.setEditable(false);
        jTextFieldSWMillis.setBackground(new java.awt.Color(43, 44, 45));
        jTextFieldSWMillis.setFont(new java.awt.Font("Segoe UI", 0, 36)); // NOI18N
        jTextFieldSWMillis.setForeground(new java.awt.Color(255, 255, 255));
        jTextFieldSWMillis.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        jTextFieldSWMillis.setText("000");
        jTextFieldSWMillis.setCaretColor(new java.awt.Color(255, 255, 255));
        jTextFieldSWMillis.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jTextFieldSWMillisKeyTyped(evt);
            }
        });

        jTextFieldSWMinutes.setEditable(false);
        jTextFieldSWMinutes.setBackground(new java.awt.Color(43, 44, 45));
        jTextFieldSWMinutes.setFont(new java.awt.Font("Segoe UI", 0, 36)); // NOI18N
        jTextFieldSWMinutes.setForeground(new java.awt.Color(255, 105, 0));
        jTextFieldSWMinutes.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        jTextFieldSWMinutes.setText("00");
        jTextFieldSWMinutes.setCaretColor(new java.awt.Color(255, 255, 255));
        jTextFieldSWMinutes.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jTextFieldSWMinutesKeyTyped(evt);
            }
        });

        jTextFieldSWSeconds.setEditable(false);
        jTextFieldSWSeconds.setBackground(new java.awt.Color(43, 44, 45));
        jTextFieldSWSeconds.setFont(new java.awt.Font("Segoe UI", 0, 36)); // NOI18N
        jTextFieldSWSeconds.setForeground(new java.awt.Color(255, 105, 0));
        jTextFieldSWSeconds.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        jTextFieldSWSeconds.setText("00");
        jTextFieldSWSeconds.setCaretColor(new java.awt.Color(255, 255, 255));
        jTextFieldSWSeconds.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jTextFieldSWSecondsKeyTyped(evt);
            }
        });

        jListLaps.setBackground(new java.awt.Color(37, 37, 37));
        jListLaps.setForeground(new java.awt.Color(255, 105, 0));
        jListLaps.setFocusable(false);
        jListLaps.setRequestFocusEnabled(false);
        jListLaps.setSelectionBackground(new java.awt.Color(255, 105, 0));
        jScrollPane1.setViewportView(jListLaps);

        jButtonStartSW.setBackground(new java.awt.Color(37, 37, 37));
        jButtonStartSW.setForeground(new java.awt.Color(255, 105, 0));
        jButtonStartSW.setText("Start");
        jButtonStartSW.setFocusPainted(false);
        jButtonStartSW.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonStartSWActionPerformed(evt);
            }
        });

        jButton5.setBackground(new java.awt.Color(37, 37, 37));
        jButton5.setForeground(new java.awt.Color(255, 105, 0));
        jButton5.setText("Stop");
        jButton5.setFocusPainted(false);
        jButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton5ActionPerformed(evt);
            }
        });

        jButton6.setBackground(new java.awt.Color(37, 37, 37));
        jButton6.setForeground(new java.awt.Color(255, 105, 0));
        jButton6.setText("Reset");
        jButton6.setFocusPainted(false);
        jButton6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton6ActionPerformed(evt);
            }
        });

        jButton7.setBackground(new java.awt.Color(37, 37, 37));
        jButton7.setForeground(new java.awt.Color(255, 105, 0));
        jButton7.setText("Lap");
        jButton7.setFocusPainted(false);
        jButton7.setPreferredSize(new java.awt.Dimension(90, 23));
        jButton7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton7ActionPerformed(evt);
            }
        });

        jButton8.setBackground(new java.awt.Color(37, 37, 37));
        jButton8.setForeground(new java.awt.Color(255, 105, 0));
        jButton8.setText("Clear lap");
        jButton8.setFocusPainted(false);
        jButton8.setMargin(new java.awt.Insets(2, 0, 3, 0));
        jButton8.setPreferredSize(new java.awt.Dimension(90, 23));
        jButton8.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton8ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(jButton7, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                            .addComponent(jButtonStartSW, javax.swing.GroupLayout.DEFAULT_SIZE, 80, Short.MAX_VALUE)
                            .addComponent(jTextFieldSWMinutes, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jButton8, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(jButton5, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jTextFieldSWSeconds))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jTextFieldSWMillis, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jButton6, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                        .addContainerGap())))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jTextFieldSWSeconds, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jTextFieldSWMinutes, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jTextFieldSWMillis))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButtonStartSW)
                    .addComponent(jButton5)
                    .addComponent(jButton6))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jButton7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                .addContainerGap(16, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addContainerGap())
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

    private void jButtonTimerResetActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonTimerResetActionPerformed
        timerReset();
        paintTimerStartButton();
    }//GEN-LAST:event_jButtonTimerResetActionPerformed

    private void jButton6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton6ActionPerformed
        resetSW();
    }//GEN-LAST:event_jButton6ActionPerformed

    private void jTextFieldTimerHoursKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextFieldTimerHoursKeyTyped
        if (jTextFieldTimerHours.getText().length() >= 2 || !Character.isDigit(evt.getKeyChar())) evt.consume();
    }//GEN-LAST:event_jTextFieldTimerHoursKeyTyped

    private void jTextFieldTimerMinutesKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextFieldTimerMinutesKeyTyped
        if (jTextFieldTimerMinutes.getText().length() >= 2 || !Character.isDigit(evt.getKeyChar())) evt.consume();
    }//GEN-LAST:event_jTextFieldTimerMinutesKeyTyped

    private void jTextFieldTimerSecondsKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextFieldTimerSecondsKeyTyped
        if (jTextFieldTimerSeconds.getText().length() >= 2 || !Character.isDigit(evt.getKeyChar())) evt.consume();
    }//GEN-LAST:event_jTextFieldTimerSecondsKeyTyped

    private void jTextFieldSWMinutesKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextFieldSWMinutesKeyTyped
        if (jTextFieldSWMinutes.getText().length() >= 2 || !Character.isDigit(evt.getKeyChar())) evt.consume();
    }//GEN-LAST:event_jTextFieldSWMinutesKeyTyped

    private void jTextFieldSWSecondsKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextFieldSWSecondsKeyTyped
        if (jTextFieldSWSeconds.getText().length() >= 2 || !Character.isDigit(evt.getKeyChar())) evt.consume();
    }//GEN-LAST:event_jTextFieldSWSecondsKeyTyped

    private void jTextFieldSWMillisKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextFieldSWMillisKeyTyped
        if (jTextFieldSWMillis.getText().length() >= 2 || !Character.isDigit(evt.getKeyChar())) evt.consume();
    }//GEN-LAST:event_jTextFieldSWMillisKeyTyped

    private void jButtonAlarmChargeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonAlarmChargeActionPerformed
        paintAlarmButton();
    }//GEN-LAST:event_jButtonAlarmChargeActionPerformed

    private void jButtonTimerStartActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonTimerStartActionPerformed
        isCount = true;
        paintTimerStartButton();
        setUpTimer();
    }//GEN-LAST:event_jButtonTimerStartActionPerformed

    private void jButtonTimerPauseActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonTimerPauseActionPerformed
        isCount = false;
        paintTimerStartButton();
    }//GEN-LAST:event_jButtonTimerPauseActionPerformed

    private void jButtonStartSWActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonStartSWActionPerformed
        startSW();
    }//GEN-LAST:event_jButtonStartSWActionPerformed

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed
        stopSW();
    }//GEN-LAST:event_jButton5ActionPerformed

    private void jButton7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton7ActionPerformed
        lapSW();
    }//GEN-LAST:event_jButton7ActionPerformed

    private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing
        SourceFrame.scheduler.shutdown();
                    evt.getWindow().dispose();
                    System.out.println("Executor service is shutdown - " +
                            SourceFrame.scheduler.isShutdown());
                    System.out.println("Executor service is terminated - " +
                            SourceFrame.scheduler.isTerminated());
    }//GEN-LAST:event_formWindowClosing

    private void jButton8ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton8ActionPerformed
        resetLapsSW();
    }//GEN-LAST:event_jButton8ActionPerformed

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
            java.util.logging.Logger.getLogger(ClockForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(ClockForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(ClockForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(ClockForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(() -> {
            new ClockForm().setVisible(true);
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton5;
    private javax.swing.JButton jButton6;
    private javax.swing.JButton jButton7;
    private javax.swing.JButton jButton8;
    private javax.swing.JButton jButtonAlarmCharge;
    private javax.swing.JButton jButtonStartSW;
    private javax.swing.JButton jButtonTimerPause;
    private javax.swing.JButton jButtonTimerReset;
    private javax.swing.JButton jButtonTimerStart;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabelAlarmStatus;
    private javax.swing.JList<String> jListLaps;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanelAlarmDisplay;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextField jTextFieldAlarmTime;
    private javax.swing.JTextField jTextFieldCurrentDate;
    private javax.swing.JTextField jTextFieldCurrentTime;
    private javax.swing.JTextField jTextFieldSWMillis;
    private javax.swing.JTextField jTextFieldSWMinutes;
    private javax.swing.JTextField jTextFieldSWSeconds;
    private javax.swing.JTextField jTextFieldTimerHours;
    private javax.swing.JTextField jTextFieldTimerMinutes;
    private javax.swing.JTextField jTextFieldTimerSeconds;
    // End of variables declaration//GEN-END:variables
}
