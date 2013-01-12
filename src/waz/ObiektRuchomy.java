
package waz;

/**
 * Klasa do reprezentowania obiektow zmieniajacych polozenie.
 */
public abstract class ObiektRuchomy extends ObiektNaPlanszy
{
    /**
     * Kostruktor tworzacy obiekt na polu (0,0) o zadanej predkosci.
     * @param predkosc predkosc obiektu.
     */
    public ObiektRuchomy(double predkosc)
    {
        super();
        this.v = predkosc;
    }

    /**
     * Kostruktor tworzacy obiekt o zadanej predkosci i wspolrzednych.
     * @param predkosc predkosc obiektu.
     * @param wiersz numer wiersza w ktorym znajdzie sie obiekt.
     * @param kol numer kolumny w ktorej znajdzie sie obiekt.
     */
    public ObiektRuchomy(double predkosc, int wiersz, int kol)
    {
        super(wiersz, kol);
        this.v = predkosc;
    }
    
    /**
     * Byc moze przesunie obiekt. To jak czesto ta funkcja przesunie obiekt
     * zalezy od jego predkosci.
     * Funkcja powinna byc wywolywana w kazdym takcie.
     * @param pola tablica z zajetoscia pol na planszy.
     * @return true jezeli przesunieto
     */
    public boolean mozePrzesun(Plansza plansza)
    {
        acc += v;
        if (acc >= 1.0)
        {
            while (acc >= 1.0)
            {
                przesun(plansza);
                acc -= 1.0;
            }
            return true;
        }
        else return false;
    }
    
    /**
     * Przesuwa obiekt.
     * @param pola tablica z zajetoscia pol na planszy.
     */
    protected void przesun(Plansza plansza)
    {
        ObiektNaPlanszy[][] pola = plansza.dajPola();
        Poz p = nowaPozycja(pola);
        if (pola[p.w][p.k] == null)
        {
            pola[wiersz][kol] = null;
            wiersz = p.w;
            kol = p.k;
            pola[wiersz][kol] = this;
        }
        else if (pola[p.w][p.k] instanceof CzescWeza) {
            //TODO wywalic instanceof
            reaguj(plansza.dajWeza());
        } else {
            odwroc();
            p = nowaPozycja(pola);
            if (pola[p.w][p.k] == null)
            {
                pola[wiersz][kol] = null;
                wiersz = p.w;
                kol = p.k;
                pola[wiersz][kol] = this;
            }            
        }
    }
    
    /**
     * Funkcja nie przesuwa obiektu, zwraca tylko przyszle polozenie.
     * @param pola
     * @return 
     */
    protected Poz nowaPozycja(ObiektNaPlanszy[][] pola)
    {
        int ileWierszy = pola.length;
        int ileKol = pola[0].length;
        int wiersz2 = wiersz;
        int kol2 = kol;
        switch (kierunek)
        {
            case UP:
                wiersz2 = (wiersz+ileWierszy-1) % ileWierszy;
                break;                
            case RIGHT:
                kol2 = (kol+1) % ileKol;
                break;                
            case DOWN:
                wiersz2 = (wiersz+1) % ileWierszy;
                break;                
            case LEFT:
                kol2 = (kol+ileKol-1) % ileKol;
                break;                
        }
        return new Poz(wiersz2, kol2);
    }
    
    /**
     * Przyspiesza obiekt.
     */
    public void przyspiesz(double procent)
    {
        if (procent < -100)
            throw new RuntimeException("Zly argument dla funkcji przyspiesz: " + procent);
        v *= (1 + procent/100);
    }
    
    public void ustawKier(int kier)
    {
        kierunek = kier;
    }
    
    public int kierunek()
    {
        return kierunek;
    }

    public double getV()
    {
        return v;
    }

    public void setV(double v)
    {
        this.v = v;
    }

    
    /**
     * Zmienia kierunek obiektu na odwrotny.
     */
    public void odwroc()
    {
        kierunek = (kierunek + 2) % 4;
    }
    /**
     * Stale oznaczajace kierunek w ktorym porusza sie obiekt.
     */
    public static final int UP = 0, RIGHT = 1, DOWN = 2, LEFT = 3;
    
    /**
     * Kierunek w ktorym porusza sie obiekt.
     */
    private int kierunek;    
    
    /**
     * Predkosc obiektu.
     */
    private double v;
    
    /**
     * Do tego pola w kazdym wywolaniu metody mozePrzesun dodawane jest v.
     * Gdy acc przeskoczy 1 obiekt przesuwany jest o jedno pole.
     */
    private double acc;
}
