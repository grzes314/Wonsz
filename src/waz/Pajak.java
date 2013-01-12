
package waz;

import java.awt.Image;

/**
 * Klasa do reprezentowania pajaka -- ruchomego obiektu, ktory zabija weza,
 * gdy sie z nim zetknie.
 */
public class Pajak extends ObiektRuchomy
{
    public Pajak(int w, int k, int kier)
    {
        super(pajakV, w, k);
        this.ustawKier(kier);
    }
    
    
    public Pajak(int w, int k, int kier, double predkosc)
    {
        super(predkosc, w, k);
        this.ustawKier(kier);
    }
    
    @Override
    public void reaguj(Waz waz)
    {
        waz.zabijWeza();
    }

    @Override
    public Image obrazek(int w, int h)
    {
        return ObiektNaPlanszy.obrazy.dajSkalowany("pajak", w, h);
    }
    
    /**
     * Domyslna predkosc pajaka.
     */
    public static final double pajakV = 0.05; 
}
