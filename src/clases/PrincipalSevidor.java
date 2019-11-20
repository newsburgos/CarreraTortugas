
package clases;

import java.io.IOException;

//Clase del Principal del servidor arranca el servidor
public class PrincipalSevidor {
    public static void main(String[] args) throws IOException, InterruptedException
	{
		Servidor server = new Servidor();
		server.RunServer();
	}
    
}
