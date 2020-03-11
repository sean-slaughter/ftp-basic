/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package program4;
import java.io.*;
import java.net.*;
import java.util.Locale;
import java.util.StringTokenizer;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
/**
 *
 * @author thems7874
 */
public class FTPClient extends javax.swing.JFrame {
    Socket clientSocket = null;
    File localFiles = new File("LocalFiles");
    String remoteFiles;
    Vector clientFileVec = new Vector();
    Vector serverFileVec = new Vector();
    
    
    PrintWriter writeSocket;
    BufferedReader readSocket;

    /**
     * Creates new form FTPClient
     */
    public FTPClient() {
        initComponents();
        serverFiles.setListData(serverFileVec);
        listLocalFiles();
    }
    private void clearServerList(){
        String clearString = "";
        String[] clear = new String[1];
        clear[0] = clearString;
        serverFiles.setListData(clear);
    }
    private void clearClientList(){
        String clearString = "";
        String[] clear = new String[1];
        clear[0] = clearString;
        clientFiles.setListData(clear);
    }
    private void listLocalFiles(){
        File[] files = localFiles.listFiles();
        for(File e : files){
            clientFileVec.add(e.getName());
        }
        clientFiles.setListData(clientFileVec);
    }
    private void listRemoteFiles(){
        StringTokenizer splitter = new StringTokenizer(remoteFiles, " ");
        while(splitter.hasMoreTokens()){
            serverFileVec.add(splitter.nextToken());
        }
        serverFiles.setListData(serverFileVec);
        
    }
    private void sendFile(String fileName){
        output.append("Sending the file: " + fileName + "\n");
        BufferedInputStream dataInput = null;
        BufferedOutputStream dataOutput = null;
        byte[] dataBuffer = new byte[1024];
        
        try {
            String putRequest = ("PUT " + fileName);
            writeSocket.println(putRequest);
            String dataPort = readSocket.readLine();
            Socket dataSocket = new Socket(clientSocket.getInetAddress(), Integer.parseInt(dataPort));
            File toSend = new File("LocalFiles//" + fileName);
            dataOutput  = new BufferedOutputStream(dataSocket.getOutputStream());
            dataInput =  new BufferedInputStream(new FileInputStream(toSend));
            int bytesRead = 0;
            int totalBytes = 0;
            while((bytesRead = dataInput.read(dataBuffer)) != -1){
                totalBytes += bytesRead;
                dataOutput.write(dataBuffer, 0, bytesRead);
            }
            dataOutput.flush();
            dataOutput.close();
            dataInput.close();
            output.append("File successfully sent. " + totalBytes + " bytes sent. \n");
            dataSocket.close();
            output.append("Data connection closed.\n");
            clearServerList();
            serverFileVec.add(fileName);
            serverFiles.setListData(serverFileVec);
            
            
        } catch (IOException ex) {
            Logger.getLogger(FTPClient.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    private void getFile(String fileName){
        output.append("Recieving the file: " + fileName + "\n");
        BufferedInputStream dataInput = null;
        BufferedOutputStream dataOutput = null;
        byte[] dataBuffer = new byte[1024];
        try {
            String getRequest = "GET " + fileName;
            writeSocket.println(getRequest);
            String dataPort = readSocket.readLine();
            Socket dataSocket = new Socket(clientSocket.getInetAddress(), Integer.parseInt(dataPort));
            File toGet = new File("LocalFiles//" + fileName);
            toGet.createNewFile();
            dataOutput  = new BufferedOutputStream(new FileOutputStream(toGet));
            dataInput = new BufferedInputStream(dataSocket.getInputStream());
            int bytesRead = 0;
            int totalBytes = 0;
            while((bytesRead = dataInput.read(dataBuffer)) != -1){
                totalBytes += bytesRead;
                dataOutput.write(dataBuffer, 0, bytesRead);
            }
            dataOutput.flush();
            dataOutput.close();
            dataInput.close();
            output.append("File successfuly received. " + "Total bytes: " + totalBytes +"\n");
            dataSocket.close();
            output.append("Data connection closed.");
            clearClientList();
            clientFileVec.add(fileName);
            clientFiles.setListData(clientFileVec);
            
            
            
        } catch (IOException ex) {
            Logger.getLogger(FTPClient.class.getName()).log(Level.SEVERE, null, ex);
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

        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        hostName = new javax.swing.JTextField();
        portNumber = new javax.swing.JTextField();
        connectButton = new javax.swing.JButton();
        jLabel3 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        serverFiles = new javax.swing.JList<>();
        jLabel4 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        clientFiles = new javax.swing.JList<>();
        jScrollPane3 = new javax.swing.JScrollPane();
        output = new javax.swing.JTextArea();
        getButton = new javax.swing.JButton();
        putButton = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabel1.setText("Host:");

        jLabel2.setText("Port:");

        hostName.setText("localhost");
        hostName.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                hostNameActionPerformed(evt);
            }
        });

        portNumber.setText("5521");

        connectButton.setText("CONNECT");
        connectButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                connectButtonActionPerformed(evt);
            }
        });

        jLabel3.setText("Server files: ");

        serverFiles.setModel(new javax.swing.AbstractListModel<String>() {
            String[] strings = { "Item 1", "Item 2", "Item 3", "Item 4", "Item 5" };
            public int getSize() { return strings.length; }
            public String getElementAt(int i) { return strings[i]; }
        });
        jScrollPane1.setViewportView(serverFiles);

        jLabel4.setText("Client files: ");

        clientFiles.setModel(new javax.swing.AbstractListModel<String>() {
            String[] strings = { "Item 1", "Item 2", "Item 3", "Item 4", "Item 5" };
            public int getSize() { return strings.length; }
            public String getElementAt(int i) { return strings[i]; }
        });
        jScrollPane2.setViewportView(clientFiles);

        output.setEditable(false);
        output.setColumns(20);
        output.setRows(5);
        jScrollPane3.setViewportView(output);

        getButton.setText("GET");
        getButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                getButtonActionPerformed(evt);
            }
        });

        putButton.setText("PUT");
        putButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                putButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(96, 96, 96)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(26, 26, 26)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(portNumber, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(50, 50, 50)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel4)
                                    .addComponent(connectButton, javax.swing.GroupLayout.PREFERRED_SIZE, 135, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 204, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addComponent(hostName, javax.swing.GroupLayout.PREFERRED_SIZE, 260, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(42, 42, 42)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel3)
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 187, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 469, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(39, Short.MAX_VALUE))
            .addGroup(layout.createSequentialGroup()
                .addGap(99, 99, 99)
                .addComponent(getButton)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(putButton)
                .addGap(104, 104, 104))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(39, 39, 39)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(hostName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(portNumber, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(connectButton))
                    .addComponent(jLabel2))
                .addGap(37, 37, 37)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(jLabel4))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 201, Short.MAX_VALUE)
                    .addComponent(jScrollPane2))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(getButton)
                    .addComponent(putButton))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 12, Short.MAX_VALUE)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 167, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void hostNameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_hostNameActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_hostNameActionPerformed

    private void connectButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_connectButtonActionPerformed
        String buttonText = connectButton.getText();
        if(buttonText.equals("CONNECT")){
            try {
                clientSocket = new Socket(hostName.getText(), Integer.parseInt(portNumber.getText()));
                writeSocket = new PrintWriter(clientSocket.getOutputStream(), true);
                readSocket = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                remoteFiles = readSocket.readLine();
                listRemoteFiles();
                connectButton.setText("DISCONNECT");
                output.append("Connection successful. \n");
            } catch (IOException ex) {
                Logger.getLogger(FTPClient.class.getName()).log(Level.SEVERE, null, ex);
            }   
        }
        if(buttonText.equals("DISCONNECT")){
            try{
                writeSocket.close();
                readSocket.close();
                clientSocket.close();
                clientSocket = null;
                clearServerList();
                serverFileVec.clear();
                connectButton.setText("CONNECT");
                output.append("Disconnected from server. \n");
        }
        catch(IOException e){
            output.append("Error with disconnecting");
        }
    }//GEN-LAST:event_connectButtonActionPerformed
    }
    private void getButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_getButtonActionPerformed
        if(clientSocket != null){
                String fileName = serverFiles.getSelectedValue();
                getFile(fileName);
            } 
        
    }//GEN-LAST:event_getButtonActionPerformed

    private void putButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_putButtonActionPerformed
        if(clientSocket != null){
                String fileName = clientFiles.getSelectedValue();
                sendFile(fileName);
        }
    }//GEN-LAST:event_putButtonActionPerformed

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
            java.util.logging.Logger.getLogger(FTPClient.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(FTPClient.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(FTPClient.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(FTPClient.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new FTPClient().setVisible(true);
                
            }
        });
        
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JList<String> clientFiles;
    private javax.swing.JButton connectButton;
    private javax.swing.JButton getButton;
    private javax.swing.JTextField hostName;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JTextArea output;
    private javax.swing.JTextField portNumber;
    private javax.swing.JButton putButton;
    private javax.swing.JList<String> serverFiles;
    // End of variables declaration//GEN-END:variables
}
