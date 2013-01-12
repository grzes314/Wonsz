
package waz;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import javax.swing.JPanel;

/**
 * Plotno to klasa na ktorej malujemy plansze gry.
 */
public class Plotno extends JPanel
{
    public Plotno(Plansza plansza)
    {
        this.plansza = plansza;
    }
    
    @Override
    public void paint(Graphics gr)
    {
        int ileWierszy = plansza.ileWierszy;
        int ileKol = plansza.ileKol;
        double dw = (double)getSize().height / ileWierszy ;
        double dk = (double)getSize().width / ileKol;
        
        gr.setColor(Color.WHITE);
        gr.fillRect(0, 0, getSize().width, getSize().height);
        gr.setColor(Color.PINK);
        for (ObiektNaPlanszy ob: plansza.dajObiekty())
        {            
            Image img = ob.obrazek((int)dk + 1, (int)dw + 1);
            gr.drawImage(img, (int)(ob.getKol()*dk),
                              (int)(ob.getWiersz()*dw), null);
        }
        for (CzescWeza cz: plansza.dajWeza().dajGlowe())
        {
            Image img = cz.obrazek((int)dk + 1, (int)dw + 1);
            gr.drawImage(img, (int)(cz.getKol()*dk),
                              (int)(cz.getWiersz()*dw), null);
        }
    }
    
    /**
     * Plansza ktora bedzie rysowana na tym plotnie
     */
    Plansza plansza;
}
