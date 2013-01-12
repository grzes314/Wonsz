
package waz;

import java.awt.Image;

/**
 *
 * @author grzes
 */
public class Banan extends Owoc
{

    public Banan()
    {
        this(0, 0);
    }

    public Banan(int wiersz, int kol)
    {
        super(wiersz, kol);
    }

    @Override
    void zmienWeza(Waz waz)
    {
        waz.dodajPunkty(1);
        waz.przyspiesz(10);
    }

    @Override
    public Image obrazek(int w, int h)
    {
        return ObiektNaPlanszy.obrazy.dajSkalowany("banan", w, h);
    }
}
