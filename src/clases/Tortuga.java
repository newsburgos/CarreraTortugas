
package clases;

import java.util.Random;


public class Tortuga implements Runnable
{
    private String nombre;
    private int numero;

    private Carrera currentRace;

    public String getNombre()
    {
        return nombre;
    }

    public int getNumero()
    {
        return numero;
    }

    public String getStringNumber()
    {
        return String.valueOf(numero);
    }

    public Tortuga(String name, int number)
    {
        this.nombre = name;
        this.numero = number;
    }

    public Tortuga(String name, String number)
    {
        this.nombre = name;
        this.numero = Integer.parseInt(number);
    }


    public void PrepareToRace(Carrera race)
    {
        this.currentRace = race;
    }

    @Override
    public void run()
    {
        try {
            
            int random = new Random().nextInt(500);
            Thread.sleep(random);

            //Empieza la carrera, guardamos el recorrido
            float path = 0f;

            //Aleatoriamente vamos avanzando la tortuga
            while(path < currentRace.GetRaceDistance())
            {
                if(currentRace.GetRaceWinner() !=null)
                    return;
                path += random*0.2f;
            }

            //seteamos ganador
            currentRace.SetRaceWinner(this);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    } 
    
}
