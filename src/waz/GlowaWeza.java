
package waz;

import java.awt.Image;

/**
 * GlowaWeza sluzy reprezentowaniu poczatkowej czesci weza.
 */
public class GlowaWeza extends CzescWeza
{
    /**
     * Kostruktor ustawiajacy glowe na zadanej pozycji.
     * @param wiersz
     * @param kol 
     */
    public GlowaWeza(int wiersz, int kol)
    {
        super(wiersz, kol);
        doOgona = this;
    }

    @Override
    public Image obrazek(int w, int h)
    {
        return ObiektNaPlanszy.obrazy.dajSkalowany("waz", w, h);
    }
    
    /**
     * Przesuwanie weza musi byc nadpisane, bo weza przesuwa sie inaczej niz
     * zwykly obiekt.
     * @param plansza plansza z gra.
     */
    @Override
    protected void przesun(Plansza plansza)
    {
        ObiektNaPlanszy[][] pola = plansza.dajPola();
        if (poprzednia != null)
            poprzednia.przesunOgon(plansza, wiersz,kol);
        else pola[wiersz][kol] = null;
        Poz p = nowaPozycja(pola);
        wiersz = p.w;
        kol = p.k;
        if (pola[p.w][p.k] != null)
            pola[p.w][p.k].reaguj(plansza.dajWeza());
        pola[wiersz][kol] = this; 
        wolnoZmKier = true;
    }
    
    /**
     * Wydluzy weza o zadana dlugosc.
     * @param oIle ile czesci dodac do weza.
     */
    public void wydluz(int oIle)
    {
        CzescWeza cz = this;
        while (!cz.czyOgon())
        {
            cz.doOgona = cz.doOgona.doOgona;
            cz = cz.doOgona;
        }
        while (oIle > 0)
        {
            cz.poprzednia = new CzescWeza(cz.wiersz, cz.kol);
            cz.doOgona = cz.poprzednia;
            cz = cz.poprzednia;
            oIle--;
        }
    }
    
    /**
     * Zmiana kierunku jest nadpisana, aby uniknac obracania weza o 180 stopni.
     * @param kier kierunek w ktorym teraz waz ma isc.
     */
    @Override
    public void ustawKier(int kier)
    {
        if (wolnoZmKier && (kier + kierunek()) % 2 != 0 )
        {
            super.ustawKier(kier);
            wolnoZmKier = false;
        }
        //gdy (kier + kierunek()) % 2 == 0 to zmiana kierunku bylaby o 180
        //stopni, a tego wezowi robic nie wolno        
    }    
   
    /**
     * Zmienna oznaczajaca czy wezowi wolno zmienic kierunek. Kierunek wolno
     * zmienic raz pomiedzy przesunieciem weza.
     */
    boolean wolnoZmKier;
}
