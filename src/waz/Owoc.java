
package waz;

/**
 * Abstrajcyjna klasa do reprezentowania owocow. Konkretne klasy musza 
 * implementowac metode zmienWeza definiujaca co zrobic z wezem.
 */
abstract public class Owoc extends ObiektNaPlanszy
{
    public Owoc()
    {
        super();
    }

    public Owoc(int wiersz, int kol)
    {
        super(wiersz, kol);
    }
    
    /**
     * Zwraca true wtedy i tylko wtedy gdy owoc nalezy usunac z mapy, co dzieje
     * sie wtedy i tylko wtedy, gdy owoc zostal zjedzony.
     * @return true wtedy i tylko wtedy, gdy owoc zostal zjedzony.
     */
    @Override
    public boolean doUsuniecia()
    {
        return zjedzony;
    }
    
    abstract void zmienWeza(Waz waz);
    
    @Override
    public void reaguj(Waz waz)
    {
        zjedzony = true;
        zmienWeza(waz);        
    }
    boolean zjedzony = false;

}
