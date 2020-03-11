/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package program4;
import java.io.*;
import java.net.*;
import java.util.logging.Level;
import java.util.logging.Logger;
       
/**
 *
 * @author thems7874
 */
public class FTPServerThread extends Thread{
    Socket controlSock;
    File folder = new File("RemoteFiles");
    File[] remoteFiles = folder.listFiles();
    byte[] dataBuffer;
    BufferedInputStream bufferedFileStream;
    PrintWriter writeSock;
    BufferedReader readSock;
    BufferedInputStream dataInput;
    BufferedOutputStream dataOutput;
    
    public FTPServerThread(Socket s){
        try {
            controlSock = s;
            writeSock = new PrintWriter(controlSock.getOutputStream(), true);
            readSock = new BufferedReader(new InputStreamReader(controlSock.getInputStream()));
        } catch (IOException ex) {
            Logger.getLogger(FTPServerThread.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void run(){
        listFiles();
        try {
            while(true){
            String request = readSock.readLine();
            if (request != null){
            String[] temp = request.split(" ");
            String command = temp[0].trim();
            String file = temp[1].trim();
            if(command.equals("GET")){
                sendFile(file);
                }
            if(command.equals("PUT")){
                getFile(file);
                }
            }
            }
        } catch (IOException ex) {
            try {
                Logger.getLogger(FTPServerThread.class.getName()).log(Level.SEVERE, null, ex);
                controlSock.close();
            } catch (IOException ex1) {
                Logger.getLogger(FTPServerThread.class.getName()).log(Level.SEVERE, null, ex1);
            }
        }
    }
    
    private void listFiles(){
        String output = "";
        File dir = new File("RemoteFiles");
        File[] files = dir.listFiles();
        for(int i = 0; i < files.length; i++){
            if(files[i].isFile()){
                output += (files[i].getName() + " ");
            }
        }
        writeSock.println(output);
    }

    private void sendFile(String fileName){
         
        try {
            ServerSocket dataSocket = new ServerSocket(5520);
            writeSock.println(5520);
            Socket dataSock = dataSocket.accept();
            System.out.println("Data connection: Local port#: " + dataSock.getLocalPort() + " Remote port#: " +
                    dataSock.getPort());
            System.out.println("Sending the file: " + fileName);
            File toSend = new File("RemoteFiles//" + fileName);
            dataInput  = new BufferedInputStream(new FileInputStream(toSend));
            dataOutput = new BufferedOutputStream(dataSock.getOutputStream());
            dataBuffer = new byte[1024];
            int bytesRead = 0;
            int totalBytes = 0;
            while((bytesRead = dataInput.read(dataBuffer)) != -1){
                totalBytes += bytesRead;
                dataOutput.write(dataBuffer, 0, bytesRead);
            }
            dataOutput.flush();
            dataOutput.close();
            dataInput.close();
            System.out.println("Successfully sent file: " + fileName + " Total bytes: " + totalBytes);
            dataSock.close();
            dataSocket.close();
            System.out.println("Connection closed.");
        } catch (IOException ex) {
            Logger.getLogger(FTPServerThread.class.getName()).log(Level.SEVERE, null, ex);
            try {
                controlSock.close();
            } catch (IOException ex1) {
                Logger.getLogger(FTPServerThread.class.getName()).log(Level.SEVERE, null, ex1);
            }
        }
    }
    private void getFile(String fileName){
        
        try {
            ServerSocket dataSocket = new ServerSocket(5520);
            writeSock.println(5520);
            Socket dataSock = dataSocket.accept();
            System.out.println("Data connection: Local port#: " + dataSock.getLocalPort() + " Remote port#: " +
                    dataSock.getPort());
            System.out.println("Getting the file: " + fileName);
            File toGet = new File("RemoteFiles//" + fileName);
            toGet.createNewFile();
            dataBuffer = new byte[1024];
            dataInput = new BufferedInputStream(dataSock.getInputStream());
            dataOutput = new BufferedOutputStream(new FileOutputStream(toGet));
            int bytesRead = 0;
            int totalBytes = 0;
            while((bytesRead = dataInput.read(dataBuffer)) != -1){
                totalBytes += bytesRead;
                dataOutput.write(dataBuffer, 0, bytesRead);
            }
            dataOutput.flush();
            dataOutput.close();
            dataInput.close();
            System.out.println("Successfully uploaded the file: " + fileName + " Total bytes: " + totalBytes);
            dataSock.close();
            dataSocket.close();
            System.out.println("Connection closed.");
        } catch (IOException ex) {
            Logger.getLogger(FTPServerThread.class.getName()).log(Level.SEVERE, null, ex);
            try {
                controlSock.close();
            } catch (IOException ex1) {
                Logger.getLogger(FTPServerThread.class.getName()).log(Level.SEVERE, null, ex1);
            }
        }
        
        
    }
    
}
