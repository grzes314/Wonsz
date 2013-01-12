
package waz;

/**
 * Klasa reprezentujaca pozycje na planszy (numer wiersza i numer kolumny).
 * @author grzes
 */
public class Poz
{
    public Poz(int w, int k)
    {
        this.w = w;
        this.k = k;
    }
    
    //nie ma sensu robic tych pol jako prywatnych
    public int w;
    public int k;
}
