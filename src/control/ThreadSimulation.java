package control;

import graphique.MainFrame;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;

/**
 *
 * @author Maxime BLAISE
 */
public class ThreadSimulation implements Runnable {

    /**
     * Série de données pour la température de la pièce.
     */
    private final LineChart.Series<Double, Double> serieInterieure;
    
    private double temps = 0;
    
    private int pas = 1000;

    /**
     * Série de données pour la température extérieure.
     */
    private final LineChart.Series<Double, Double> serieExterieure;

    public ThreadSimulation(LineChart.Series<Double, Double> serieInterieure, LineChart.Series<Double, Double> serieExterieure) {
        // Thread t = new Thread(new ThredSimulation()); t.start();
        this.serieInterieure = serieInterieure;
        this.serieExterieure = serieExterieure;
    }

    public double getTemps() {
        return temps;
    }
    
    @Override
    public void run() {
        
        while (true) {
            // Mise à jour du graphique
            serieInterieure.getData().add(new XYChart.Data<>(
                    temps, MainFrame.systeme.getPiece().getTemperatureAmbiante()
            ));
            serieExterieure.getData().add(new XYChart.Data<>(
                    temps, MainFrame.systeme.getEnvironnement().getTemperatureExterieure(temps)
            ));
            
            // Préparation de l'étape suivante
            temps++;
            MainFrame.systeme.evolutionTemperature();
            try {
                Thread.sleep(this.pas);
            } catch (InterruptedException ex) {
                Logger.getLogger(ThreadSimulation.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public void setPas(int pas) {
        this.pas = pas;
    }

    public int getPas() {
        return pas;
    }

    
}
