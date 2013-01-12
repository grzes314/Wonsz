
package waz;

import java.awt.Image;

/**
 * Klasa do reprezentowania wszelkich obiektow znajdujacych sie na planszy.
 */
public abstract class ObiektNaPlanszy
{
    /**
     * Kostruktor tworzacy obiekt o zadanych wspolrzednych.
     * @param wiersz numer wiersza w ktorym znajdzie sie obiekt.
     * @param kol numer kolumny w ktorej znajdzie sie obiekt.
     */
    public ObiektNaPlanszy(int wiersz, int kol)
    {
        this.wiersz = wiersz;
        this.kol = kol;
    }

    /**
     * Kostruktor tworzacy obiekt na polu (0,0).
     */
    public ObiektNaPlanszy()
    {
        wiersz = 0;
        kol = 0;
    }
    
    /**
     * Metoda wywolywana gdy obiekt dotknie ciala weza.
     * @param waz 
     */
    abstract public void reaguj(Waz waz);
        
    /**
     * Zwraca obrazek własciwy dla tego obiektu
     */
    abstract public Image obrazek(int w, int h);

    public int getKol()
    {
        return kol;
    }

    public void setKol(int kol)
    {
        this.kol = kol;
    }

    public int getWiersz()
    {
        return wiersz;
    }

    public void setWiersz(int wiersz)
    {
        this.wiersz = wiersz;
    }
    
    /**
     * Czasem niektore obiekty powinny znikac z planszy -- np. owoce po
     * zjedzeniu. Wowczas powinny zmienic te metode tak, aby zwracala 
     * wartosc true, gdy obiekt nalezy usunac.
     * @return zawsze false.
     */
    public boolean doUsuniecia()
    {
        return false;
    }
    
    /**
     * W ktorym wierszu znajduje sie obiekt.
     */
    protected int wiersz;
    /**
     * W ktorej kolumnie znajduje sie obiekt.
     */
    protected int kol;
    
    /**
     * Statyczny obiekt przechowujacy obrazki
     */
    public static Obrazy obrazy;

}
