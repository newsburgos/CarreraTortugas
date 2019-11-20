package clases;

import java.util.ArrayList;

//Clase de la carrera 
public class Carrera {

    private volatile Tortuga GanadorCarrera;

    private ArrayList<Tortuga> Tortugas;

    private int DistanCarrera;

    public Carrera(ArrayList<Tortuga> tortug) {
        this.Tortugas = tortug;
        DistanCarrera = 2000;
    }

    public Carrera(ArrayList<Tortuga> turtles, int raceDistance) {
        this.Tortugas = turtles;
        this.DistanCarrera = raceDistance;
    }

    public int GetRaceDistance() {
        return DistanCarrera;
    }

    public Tortuga GetRaceWinner() {
        return GanadorCarrera;
    }

    public void SetRaceWinner(Tortuga Ganador) {
        if (GanadorCarrera == null) {
            GanadorCarrera = Ganador;
        }
    }

    public void StartRace() throws InterruptedException {
        Thread[] threads = new Thread[Tortugas.size()];

        //Empieza  carrera
        for (int i = 0; i < threads.length; i++) {
            Tortugas.get(i).PrepareToRace(this);
            threads[i] = new Thread(Tortugas.get(i));
            threads[i].start();
        }
    }

    //Termina carrera
    public void FinishRace() {
        GanadorCarrera = null;
    }

}
