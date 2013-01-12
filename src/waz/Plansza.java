
package waz;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

/**
 * Klasa sluzaca do reprezentowania planszy. Przechowuje tablice pol, obiekty
 * znajdujace sie na planszy, obiekt weza. Zawiera metody do uaktualnia logiki
 * gry.
 */
public class Plansza
{
    /**
     * Utowrzy plansze o zadanej liczbie wierszy i kolumn 
     * @param ileWierszy liczba wierszy na planszy.
     * @param ileKol liczba kolumn na planszy.
     */
    public Plansza(int ileWierszy, int ileKol)
    {
        this.ileWierszy = ileWierszy;
        this.ileKol = ileKol;
        pola = new ObiektNaPlanszy[ileWierszy][ileKol];
    }
    
    /**
     * Metoda przygotowuje plansze do gry. MUSI byc wywolana przed 
     * rozpoczeciem gry. 
     */
    public void init()
    {
        Poz p = wolnaPoz();
        GlowaWeza cz = new GlowaWeza(p.w, p.k);
        pola[p.w][p.k] = cz;
        add(cz);
        waz = new Waz(cz);        
        losujOwoc();        
    }
    
    /**
     * Uaktualnianie logiki gry (przesuwanie obiektow, itd.).
     */
    public void uaktualnij()
    {
        mozePrzesun();
        usunNiepotrzebne();
    }
    
    /**
     * Funkcja przesuwa obiekty ruchome. To czy obiekt zostanie naprawde 
     * przesuniety zalezy od tego, czy z jego predkosci wynika ze juz czas
     * skoczyc na koljne pole.
     */
    private void mozePrzesun()
    {
        for (ObiektRuchomy x: ruchome)
            x.mozePrzesun(this);
    }
    
    /**
     * Funkcja usuwa z planszy i listy obiektow te rzeczy ktore sa juz
     * niepotrzebne. U nas beda to zjedzone owoce, ale w ogolnosci mozna
     * by wymyslic inne obiekty, ktore w pewnym momencie moglibysmy chciec
     * usunac z mapy. Jesli zostaje usuniety owoc, to wywolywana jest metoda
     * umieszczajaca na planszy nowy owoc.
     */
    private void usunNiepotrzebne()
    {
        boolean owocZjedzony = false;
        
        Iterator<ObiektNaPlanszy> it = obiekty.iterator();
        while (it.hasNext())
        {
            ObiektNaPlanszy ob = it.next();
            if (ob.doUsuniecia())
            {
                if (ob instanceof Owoc)
                    owocZjedzony = true;
                if (pola[ob.wiersz][ob.kol] == ob) //moze sie zdarzyc ze na tym
                    pola[ob.wiersz][ob.kol] = null; //polu juz jest cos innego
                it.remove();
            }
        }
        
        Iterator<ObiektRuchomy> it2 = ruchome.iterator();
        while (it2.hasNext())
        {
            if (it2.next().doUsuniecia())
                it2.remove();
        }
        
        if (owocZjedzony)
            losujOwoc();
    }
    
    /**
     * Metoda umieszcza w losowym miejscu na planszy nowy owoc. Z
     * prawdopodobienstwem 1/3 bedzie to banan, z 2/3 bedzie to jablko.
     */
    private void losujOwoc()
    {
        Poz poz = wolnaPoz();
        if (rand.nextInt(3) == 0) {
            add(new Banan(poz.w, poz.k));
        } else {
            add(new Jablko(poz.w, poz.k));
        }
    }
    
    /**
     * Zwroci losowa, wolna pozycje na planszy.
     * @return Punkt wskazujacy wolna pozycje.
     */
    public Poz wolnaPoz()
    {
        int w = rand.nextInt(ileWierszy),
            k = rand.nextInt(ileKol);
        for (int i = 0; i < ileWierszy; ++i)
            for (int j = 0; j < ileKol; ++j)
                if (pola[ (w+i)%ileWierszy ][ (k+j)%ileKol ] == null)
                    return new Poz((w+i)%ileWierszy, (k+j)%ileKol);       
        //skoro tu jestesmy wiec na planszy nie wolnych pol
        waz.zabijWeza();
        return new Poz(0,0);
    }
    
    /**
     * Zwraca odpowiedz na pytanie czy nastapil koniec gry, czyli czy waz 
     * zginal.
     * @return true wtedy i tylko wtedy gdy waz zginal.
     */
    public boolean koniecGry()
    {
        return !waz.czyZywy();
    }

    /**
     * Zwraca liczbe zdobytych przez gracza punktow.
     * @return liczba zdobytych przez gracza punktow.
     */
    public int ilePunktow()
    {
        return waz.ilePunktow();
    }

    /**
     * Zwraca obiekt weza zwiazanego z ta plansza.
     * @return obiekt weza.
     */
    public Waz dajWeza()
    {
        return waz;
    }

    /**
     * Do obiektow na planszy dodaje nowy obiekt. 
     * @param ob nowy obiekt na planszy.
     */
    public void add(ObiektNaPlanszy ob)
    {
        obiekty.add(ob);
        pola[ob.wiersz][ob.kol] = ob;
        if (ob instanceof ObiektRuchomy)
            ruchome.add((ObiektRuchomy) ob);
    }
    
    /**
     * Zwraca liste pol planszy.
     * @return pola planszy.
     */
    public ObiektNaPlanszy[][] dajPola()
    {
        return pola;
    }

    public List<ObiektNaPlanszy> dajObiekty()
    {
        return obiekty;
    }
    
    /**
     * Obiekty znajdujace sie na planszy (bez ogona weza).
     */
    private List<ObiektNaPlanszy> obiekty = new ArrayList<>();
    /**
     * Obiekty ruchome, podzbior wszystkich obiektow.
     */
    private List<ObiektRuchomy> ruchome = new ArrayList<>();
    /**
     * Glowny bohater.
     */
    private Waz waz;
    
    /**
     * Liczba wierszy na planszy.
     */
    public final int ileWierszy;
    
    /**
     * Liczba kolumn na planszy.
     */
    public final int ileKol;
    
    /**
     * Potrzebne do losowania pozycji owocow.
     */
    private Random rand = new Random(System.currentTimeMillis());

    /**
     * Przechowuje informacje o tym, co znajduje sie na polach.
     */
    private ObiektNaPlanszy[][] pola;
}
