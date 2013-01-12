
package waz;

import java.awt.Image;
import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Obiekt sluzacy do reprezentowania fragmentu weza.
 */
public class CzescWeza extends ObiektRuchomy implements Iterable<CzescWeza>
{
    public CzescWeza(int wiersz, int kol)
    {
        super(wazV /*predkosc czesci i tak jest bez znaczenia*/,
              wiersz, kol);
        doOgona = this;
    }

    class WazIterator implements Iterator<CzescWeza>
    {        
        @Override
        public boolean hasNext()
        {
            if (curr.poprzednia == null)
                return false;
            else return true;
        }

        @Override
        public CzescWeza next() throws NoSuchElementException
        {
            if (curr.poprzednia == null)
                throw new NoSuchElementException();
            else {
                curr = curr.poprzednia;
                return curr;
            }
        }

        @Override
        public void remove()
        {
            throw new UnsupportedOperationException("Nie zaimplementowane.");
        }
        
        CzescWeza curr = CzescWeza.this;
    }
    

    @Override
    public Iterator<CzescWeza> iterator()
    {
        return new WazIterator();
    }
    
    @Override
    public void reaguj(Waz waz)
    {
        waz.zabijWeza();
    }
    
    /**
     * Metoda przesuwa czesci weza po kolei, od pierwszego elementu za glowa,
     * az do ogona.
     * @param plansza
     * @param w
     * @param k 
     */
    protected void przesunOgon(Plansza plansza, int w, int k)
    {
        if (poprzednia != null)
            poprzednia.przesunOgon(plansza, wiersz, kol);
        else {
            //zwalniamy pole zajmowane przez ogon
            plansza.dajPola()[wiersz][kol] = null;
        }
        
        wiersz = w;
        kol = k;
        plansza.dajPola()[w][k] = this;        
    }
    
    /**
     * Czesci weza nie wolno przesuwac bezposrednio. Przesuwanie czesci weza
     * odbywa sie poprzez wywolanie metody przesunOgon w metodzie przesun
     * glowy weza.
     * @param plansza plansza z gra.
     */
    @Override
    protected void przesun(Plansza plansza)
    {
        throw new RuntimeException("Przesun powinno byc wywolana na glowie "
                + "weza, a nie na czesci.");
    }
    
    
    @Override
    public Image obrazek(int w, int h)
    {
        return ObiektNaPlanszy.obrazy.dajSkalowany("waz2", w, h);
    }
    
    /**
     * Czy zadana czesc weza jest ostatnim fragmentem weza.
     * @return true wtedy i tylko wtedy gdy jest to ostatni fragment weza.
     */
    public boolean czyOgon()    
    {
        return doOgona == this;
    }
    
    /**
     * Poprzednia czesc weza (blizsza ogonowi).
     */
    protected CzescWeza poprzednia;
    
    /**
     * Czesc blizsza ogonowi, ale niekoniecznie poprzednia. Uzywane dla optyma
     * lizacji zeby szybciej dochodzic do ogona.
     */
    protected CzescWeza doOgona;
    
    /**
     * Poczatkowa czestosc oczekiwania na przesuniecie weza.
     */
    private static final double wazV = 0.1;
}
