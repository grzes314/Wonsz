
package waz;

/**
 *
 * @author grzes
 */
public class FabrykaPlansz
{
    /*
     * Tworzy plansze, na ktorej nie ma nic oprocz weza i owocow.
     */
    public Plansza zwykla(int w, int k)
    {
        Plansza pl = new Plansza(w,k);
        return pl;
    }
   
    /*
     * Tworzy plansze na ktorej oprocz weza i owocu znajduja sie sciany 
     * na brzegach planszy.
     */
    public Plansza ogrodzona(int w, int k)
    {
        Plansza pl = new Plansza(w,k);
        for (int i = 0; i < w; ++i)
        {
            pl.add( new Sciana(i,0) );
            pl.add( new Sciana(i,k-1) );
        }
        for (int j = 1; j < k-1; ++j)
        {
            pl.add( new Sciana(0,j) );
            pl.add( new Sciana(w-1,j) );
        }
        return pl;
    }
    
    /*
     * Tworzy plansze, na ktorej oprocz weza i owocow znajda sie w losowych
     * miejscach pajaki i sciany.
     */
    public Plansza wielePrzeszkod(int w, int k)
    {
        Plansza pl = new Plansza(w,k);
        int pajaki = 1 + w*k / 400;
        int sciany = w*k / 30;
        for (int i = 0; i < pajaki; ++i)
        {
            Poz poz = pl.wolnaPoz();
            pl.add( new Pajak(poz.w, poz.k, i%4) );
        }
        for (int i = 0; i < sciany; ++i)
        {
            Poz poz = pl.wolnaPoz();
            pl.add( new Sciana(poz.w, poz.k) );
        }
        return pl;   
    }
}
