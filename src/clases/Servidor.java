package clases;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class Servidor {

    //Puerto
    private final int port = 6100;
    private ServerSocket serverSocket;
    
    //Socket
    private Socket socket;

    //Lista de tortugas que participaran
    private ArrayList<Tortuga> tortgs = new ArrayList<Tortuga>();

    
    public Servidor() throws IOException {
        System.out.println("Inicializando servidor..");
        serverSocket = new ServerSocket(port);
        socket = new Socket();
    }

    public void RunServer() throws IOException, InterruptedException {
        do {
            //Espera socket
            System.out.println("Esperando...");
            socket = serverSocket.accept();

            //Lo recibimos y guardamos referencias
            System.out.println("Socket recibido ");
            
            //Introducimos informacion por el teclado 
            DataOutputStream output = new DataOutputStream(socket.getOutputStream());
            BufferedReader buffer = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            //Separamos el mensaje "codificado"
            String[] message = SplitMessage(buffer);

            //Menu de acciones desde el teclado
            switch (message[0]) {
                case "1":
                    CreateTurtle(output, message);
                    break;

                case "2":
                    DeleteTurtle(output, message[1]);
                    break;

                case "3":
                    MostrarTortugas(output);
                    break;

                case "4":
                    if (tortgs.size() > 1) {
                        StartRace(output);
                    } else {
                        output.writeUTF("Error. No hay tortugas \n");
                    }
                    break;

                case "5":
                    //Cerramos todo
                    socket.close();
                    serverSocket.close();
                    System.out.print("\nServidor cerrado\n");
                    break;
            }
            
        } while (true && serverSocket.isClosed() == false);
    }

    private String[] SplitMessage(BufferedReader buffer) throws IOException {
        String[] message = buffer.readLine().split("-");
        for (int i = 0; i < message.length; i++) {
            message[i] = message[i].trim();
        }
        return message;
    }

    private void CreateTurtle(DataOutputStream output, String[] message) throws IOException {
        //Creamos la tortuga
        Limpiar.Log("Creando nueva tortuga...\n");

        Tortuga newTurtle = new Tortuga(message[1], message[2]);
        tortgs.add(newTurtle);

        //Enviamos al cliente
        output.writeUTF("Nueva tortuga creada \n"
                + "________________________\n"
                + "Nombre: " + message[1]
                + "\nDorsal: " + message[2]
                + "\n________________________");
    }

    private void DeleteTurtle(DataOutputStream output, String s) throws IOException {
        //Guardamos el indice de la tortuga
        int deleteIndex = Integer.parseInt(s) - 1;
        if (tortgs.size() > deleteIndex && deleteIndex > -1) {
            //borramos la tortuga y se lo decimos al cliente
            tortgs.remove(deleteIndex);
            output.writeUTF("Tortuga nº: " + (deleteIndex + 1) + " eliminada\n");
        } else {
            output.writeUTF("La tortuga que buscas no existe\n");
        }
    }

    private void MostrarTortugas(DataOutputStream output) throws IOException {
        
        //Mostramos las tortugas 
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < tortgs.size(); i++) {
            Tortuga currentTurtle = tortgs.get(i);
            stringBuilder.append("________________________\n"
                    + "Nombre: " + currentTurtle.getNombre()
                    + "\nDorsal: " + currentTurtle.getStringNumber()
                    + "\n________________________\n");
        }
        output.writeUTF(stringBuilder.toString());
    }

    private void StartRace(DataOutputStream output) throws IOException, InterruptedException {
        //Creamos una carrera 
        Carrera race = new Carrera(tortgs);

        race.StartRace();

        Limpiar.Log("Simulando carrera...");

        while (race.GetRaceWinner() == null) {
            Limpiar.Log(".");
        }

        //La ganadora es
        output.writeUTF("\n La ganadora es : " + race.GetRaceWinner().getNombre());
        race.FinishRace();
    }
}
