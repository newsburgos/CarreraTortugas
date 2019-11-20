package clases;

import java.util.Random;
import java.util.Scanner;

public class PricipalCliente {

    //Guardamos las opciones del cliente
    private static final String optionsMessage = "\n Elija la opcion:\n"
            + "1. Introducir nueva tortuga\n"
            + "2. Eliminar una tortuga\n"
            + "3. Mostrar tortugas\n"
            + "4. Iniciar carrera\n"
            + "5. Salir\n\n";

    private static ConexionCliente client;

    public static void main(String[] args) throws Exception {
        while (true) {

            System.out.print(optionsMessage);
            //leemos la tecla pulsada
            Scanner reader = new Scanner(System.in);
            int option = reader.hasNextInt() ? reader.nextInt() : 0;

            //Vamos construyendo el mensaje que despues enviaremos al Servidor
            StringBuilder message = new StringBuilder();
            message.append(option + "-");

            if (client == null) {
                client = new ConexionCliente();
            }

			
            switch (option) {
                case 1:
                    //Solicitamos nombre
                    System.out.print("Introduzca nombre\n");
                    String NombreTortuga = reader.next();
                    message.append(NombreTortuga + "-");

                    //y dorsal
                    System.out.print("Introduzca Dorsal\n");
                    int NumeroTortuga = reader.hasNextInt() ? reader.nextInt() : new Random().nextInt(99);
                    message.append(NumeroTortuga);

                    //Enviamos al servidor
                    client.SendMessage(message.toString().trim());
                    break;

                case 2:
                    //Solicitamos indice para eliminar
                    System.out.print(" Nombre y numero de la tortuga a eliminar\n");
                    String deleteTortuga = reader.hasNext() ? reader.next() : "Sin nombre";
                    message.append(deleteTortuga);

                    //enviamos
                    client.SendMessage(message.toString());
                    break;

                case 3:
                    //solo enviamos la peticion
                    client.SendMessage(message.toString());
                    break;

                case 4:
                    //solo enviamos la peticion
                    System.out.print("\nPreparando tortugas...\n");
                    client.SendMessage(message.toString());
                    break;

                case 5:
                    //Cerramos todo
                    client.CloseSocket();
                    System.out.print("Servidor cerrado\n");
                    System.out.print("Cliente cerrado\n");
                    System.exit(0);
                    break;

                default:
                    //Feedback de que el usuario se ha equivocado
                    System.out.print("Clave no identificada. Vuelve a intentarlo\n");
                    break;
            }
        }
    }

}
