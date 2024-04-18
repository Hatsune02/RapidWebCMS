package com.navi.UI;

import com.formdev.flatlaf.intellijthemes.materialthemeuilite.FlatMaterialDarkerIJTheme;
import com.navi.backend.CMSParserLexer.*;
import com.navi.backend.XMLParserLexer.*;
import com.navi.backend.webController.Actions;

import javax.swing.*;
import java.awt.event.*;
import java.io.*;
import java.util.ArrayList;

public class DashBoard extends javax.swing.JFrame {
    XMLLexer xmlLexer;
    XMLParser xmlParser;
    CMSLexer cmsLexer;
    CMSParser cmsParser;

    public static ArrayList<TError> ERRORS = new ArrayList<>();
    public DashBoard() {
        initComponents();
        NumLine numLine = new NumLine(textQuery);
        NumLine numLine2 = new NumLine(textQuery);
        NumLine numLine3 = new NumLine(textQuery);
        scrollConsole.setRowHeaderView(numLine);
        scrollQuery.setRowHeaderView(numLine2);
        scrollXML.setRowHeaderView(numLine3);
        textConsole.setEditable(false);

        textXML.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.isControlDown() && e.getKeyCode() == KeyEvent.VK_P) {
                    if(!textXML.getText().isEmpty()) {
                        xmlLexer = new XMLLexer(new StringReader(textXML.getText()));
                        xmlParser = new XMLParser(xmlLexer);
                        try {
                            xmlParser.parse();
                            /*if(errors.size() == 0) {
                                xmlParser.setValid(true);
                                xmlParser.parse();
                            }*/
                        } catch (Exception ex) {
                            throw new RuntimeException(ex);
                        }
                        showErrConsole();
                    }
                }
            }
        });
        textQuery.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.isControlDown() && e.getKeyCode() == KeyEvent.VK_P) {
                    if(!textQuery.getText().isEmpty()) {
                        cmsLexer = new CMSLexer(new StringReader(textQuery.getText()));
                        cmsParser = new CMSParser(cmsLexer);
                        try {
                            if(ERRORS.isEmpty()) cmsParser.parse();
                        } catch (Exception ex) {
                            throw new RuntimeException(ex);
                        }
                        showErrConsole();
                    }
                }
            }
        });
    }

    private void showErrConsole(){
        if(!ERRORS.isEmpty()){
            StringBuilder error = new StringBuilder();
            for(TError err: ERRORS){
                error.append(err).append("\n");
            }
            ERRORS.clear();
            textConsole.append(error.toString());
        }
        if(!Actions.ERRORS.isEmpty()){
            StringBuilder error = new StringBuilder();
            for(var err: Actions.ERRORS){
                error.append(err).append("\n");
            }
            Actions.ERRORS.clear();
            textConsole.append(error.toString());
        }
        StringBuilder console = new StringBuilder();
        for(var message: Actions.RESPONSES){
            console.append(message).append("\n");
        }
        Actions.RESPONSES.clear();
        textConsole.append(console.toString());
    }

    public static String loadFile() {
        JFileChooser fileChooser = new JFileChooser();
        int result = fileChooser.showOpenDialog(null);

        if (result == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();
            StringBuilder content = new StringBuilder();
            try (BufferedReader reader = new BufferedReader(new FileReader(selectedFile))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    content.append(line).append("\n");
                }
            } catch (IOException e) {
                e.printStackTrace();
                return null;
            }
            return content.toString();
        }

        return "";
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">
    private void initComponents() {

        background = new javax.swing.JPanel();
        console = new javax.swing.JPanel();
        label = new javax.swing.JLabel();
        scrollConsole = new javax.swing.JScrollPane();
        textConsole = new javax.swing.JTextArea();
        txtEditor = new javax.swing.JPanel();
        jTabbedPane = new javax.swing.JTabbedPane();
        scrollXML = new javax.swing.JScrollPane();
        textXML = new javax.swing.JTextPane();
        scrollQuery = new javax.swing.JScrollPane();
        textQuery = new javax.swing.JTextPane();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        openXML = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        label.setText("Consola");

        textConsole.setColumns(20);
        textConsole.setRows(5);
        scrollConsole.setViewportView(textConsole);

        javax.swing.GroupLayout consoleLayout = new javax.swing.GroupLayout(console);
        console.setLayout(consoleLayout);
        consoleLayout.setHorizontalGroup(
                consoleLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(consoleLayout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(label, javax.swing.GroupLayout.PREFERRED_SIZE, 128, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addContainerGap(633, Short.MAX_VALUE))
                        .addComponent(scrollConsole, javax.swing.GroupLayout.Alignment.TRAILING)
        );
        consoleLayout.setVerticalGroup(
                consoleLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(consoleLayout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(label, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(scrollConsole, javax.swing.GroupLayout.DEFAULT_SIZE, 174, Short.MAX_VALUE)
                                .addGap(15, 15, 15))
        );

        scrollXML.setViewportView(textXML);

        jTabbedPane.addTab("   XML   ", scrollXML);

        scrollQuery.setViewportView(textQuery);

        jTabbedPane.addTab("   Query   ", scrollQuery);

        javax.swing.GroupLayout txtEditorLayout = new javax.swing.GroupLayout(txtEditor);
        txtEditor.setLayout(txtEditorLayout);
        txtEditorLayout.setHorizontalGroup(
                txtEditorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, txtEditorLayout.createSequentialGroup()
                                .addComponent(jTabbedPane)
                                .addContainerGap())
        );
        txtEditorLayout.setVerticalGroup(
                txtEditorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jTabbedPane, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 378, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout backgroundLayout = new javax.swing.GroupLayout(background);
        background.setLayout(backgroundLayout);
        backgroundLayout.setHorizontalGroup(
                backgroundLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, backgroundLayout.createSequentialGroup()
                                .addGap(75, 75, 75)
                                .addGroup(backgroundLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                        .addComponent(txtEditor, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(console, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addGap(97, 97, 97))
        );
        backgroundLayout.setVerticalGroup(
                backgroundLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(backgroundLayout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(txtEditor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(console, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addContainerGap())
        );

        jMenu1.setText("  Archivo  ");

        openXML.setText("Abrir XML");
        openXML.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                openXMLActionPerformed(evt);
            }
        });
        jMenu1.add(openXML);

        jMenuBar1.add(jMenu1);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(background, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addComponent(background, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addContainerGap())
        );

        pack();
    }// </editor-fold>

    private void openXMLActionPerformed(java.awt.event.ActionEvent evt) {
        textXML.setText(loadFile());
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        FlatMaterialDarkerIJTheme.setup();

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new DashBoard().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify
    private javax.swing.JPanel background;
    private javax.swing.JPanel console;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JTabbedPane jTabbedPane;
    private javax.swing.JLabel label;
    private javax.swing.JMenuItem openXML;
    private javax.swing.JScrollPane scrollConsole;
    private javax.swing.JScrollPane scrollQuery;
    private javax.swing.JScrollPane scrollXML;
    private javax.swing.JTextArea textConsole;
    private javax.swing.JTextPane textQuery;
    private javax.swing.JTextPane textXML;
    private javax.swing.JPanel txtEditor;
    // End of variables declaration
}
