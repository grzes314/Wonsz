
package waz;

/**
 * Klasa pomocnicza przechowujaca rozne elementy zwiazne z wezem i graczem.
 * To nie jest obiekt znajdujacy sie na planszy, ale przechowuje referencje 
 * do glowy weza i posredniczy w wykonywaniu zmian na wezu, np. przez owoce
 * i pajaki.
 */
public class Waz
{
    public Waz(GlowaWeza glowa)
    {
        czyZywy = true;
        this.glowa = glowa;
        dlugosc = 1;
    }
    
    public int ilePunktow()
    {
        return punkty;
    }
    
    public void dodajPunkty(int p)
    {
        punkty += p;
    }
    
    public boolean czyZywy()
    {
        return czyZywy;
    }
    
    public void wydluz(int oIle)
    {
        glowa.wydluz(oIle);
        dlugosc += oIle;
    }
    
    public void zabijWeza()
    {
        czyZywy = false;
    }
    
    public void przyspiesz(double procent)
    {
        glowa.przyspiesz(procent);
    }

    public int getDlugosc()
    {
        return dlugosc;
    }

    public void ustawKier(int kier)
    {
        glowa.ustawKier(kier);
    }

    public GlowaWeza dajGlowe()
    {
        return glowa;
    }
    
    /**
     * Czy waz jest zywy. Gra trwa dopoki waz zyje.
     */
    private boolean czyZywy;
    
    /**
     * Referencja do pierwszego fragmentu weza
     */
    private GlowaWeza glowa;
    
    /**
     * Ile punktow zdobyto w trakcie gry.
     */
    private int punkty;
    
    /**
     * Dlugosc weza.
     */
    private int dlugosc;
}
