
package waz;

import java.awt.Image;

/**
 * Prosty obiekt zabijajacy weza, gdy ten na niego wpadnie.
 */
public class Sciana extends ObiektNaPlanszy
{

    public Sciana(int wiersz, int kol)
    {
        super(wiersz, kol);
    }

    @Override
    public void reaguj(Waz waz)
    {
        waz.zabijWeza();
    }

    @Override
    public Image obrazek(int w, int h)
    {
        return ObiektNaPlanszy.obrazy.dajSkalowany("sciana", w, h);
    }

}
