
package clases;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;


public class ConexionCliente {
    
    //Creamos host y puerto
    private String host = "localhost";
    private int port = 6100;

    //Declaramos nuevo socket
    private Socket socket;
    
    //Constructor sin parametros
    public ConexionCliente()
    {

    }

    //Constructor con parametros
    public ConexionCliente(String host, int port)
    {
        this.host = host;
        this.port = port;
    }
    
    //Creamos socket
    public void CreateSocket() throws IOException
    {
        socket = new Socket(host, port);
    }

    //Cierra el socket
    public void CloseSocket() throws IOException
    {
        CreateSocket();
        DataOutputStream output = new DataOutputStream(socket.getOutputStream());

        output.writeBytes("5");
        socket.close();
    }
    

    public void SendMessage(String message) throws Exception
    {
        
        CreateSocket();
        
        DataOutputStream output = new DataOutputStream(socket.getOutputStream());
        DataInputStream input = new DataInputStream(socket.getInputStream());

       
        output.writeBytes(message+"\n");

        System.out.println(input.readUTF());
    }
    
}
