
package waz;

import java.awt.Image;

public class Jablko extends Owoc
{
    public Jablko()
    {
        this(0, 0);
    }

    public Jablko(int wiersz, int kol)
    {
        super(wiersz, kol);
    }

    @Override
    void zmienWeza(Waz waz)
    {
        waz.dodajPunkty(2);
        waz.wydluz(2);
    }

    @Override
    public Image obrazek(int w, int h)
    {
        return ObiektNaPlanszy.obrazy.dajSkalowany("jablko", w, h);
    }
}
