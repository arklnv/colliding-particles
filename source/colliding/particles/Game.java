// A simple and quick program that demonstrates collision of a number of particles.
// The particles have different radii, but all have the same mass, and collision is considered to be central. 


package colliding.particles;

import java.awt.EventQueue;
import java.util.Timer;
import java.util.TimerTask;


public class Game {
    private static final int TIME_BETWEEN_UPDATES = 10;
    private Timer timer;
    private Simulation sim;
    private GameGUI gui;
    private boolean simIsRunning;
    
    public Game() {  
        timer = new Timer();        
        gui = new GameGUI();       
        //sim = new Simulation(gui.getNumberOfParticles(), gui.getBoxSizeX(), gui.getBoxSizeY());       
        //gui.receivePointerToSimulation(sim); 
        simIsRunning = false;                
        gui.setVisible(true);
    }
    
    public static void main(String[] args) throws InterruptedException {                        
        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                Game game = new Game();                  
                game.loop();
            }
        });
    }    
    
    public void loop() {
        timer.scheduleAtFixedRate(new TimerTask() {
                @Override
                public void run() {
                    if (simIsRunning) {        
                        sim.advanceTime();
                        gui.render();
                    } 
                    
                	// Check if the "Start" button has been pressed during the run of the game
                	// and create a new set of particles. 
                    if (gui.getStartNewGame()) {                     
                        sim = new Simulation(gui.getNumberOfParticles(), gui.getBoxSizeX(), gui.getBoxSizeY());
                        gui.receivePointerToSimulation(sim);                         
                        gui.setStartNewGame(false);                                 
                        simIsRunning = true;
                    }                                     
                    
                    if (!gui.getGUIIsRunning()) timer.cancel();
                }
            }
        , 0, TIME_BETWEEN_UPDATES);
        //System.exit(0); 
    }
}
