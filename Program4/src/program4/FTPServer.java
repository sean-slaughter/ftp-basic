/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package program4;
import java.net.*;
import java.io.*;

/**
 *
 * @author thems7874
 */
public class FTPServer {

    public void run(){
       
        int controlPort = 5521;
        try{
            ServerSocket controlSock = new ServerSocket(controlPort);
            System.out.println("FTP Server running.....");
            while(true){
                Socket sock = controlSock.accept();
                System.out.println("Got a connection: " + sock.getInetAddress() + " Port number: " + sock.getPort());
                FTPServerThread thread = new FTPServerThread(sock);
                thread.start();
            }
        }
        catch(Exception e){
            System.out.println(e);
        }
        
    }
    public static void main(String[] args){
        FTPServer server = new FTPServer();
        server.run();
    }
    
}
