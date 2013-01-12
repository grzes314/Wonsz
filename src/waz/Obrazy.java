package waz;


import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;
import java.util.TreeMap;
import javax.imageio.ImageIO;


/**
 * Klasa sluzaca do wczytania obrazkow potrzebnych w aplikacji i ich
 * przechowywania.
 * 
 * Zazwyczaj jednak potrzebujemy przeskalowanej wersji obrazka. Zeby nie
 * wywolywac skalowania za kazdym razem, gdy obrazek trzeba narysowac,
 * w klasie tej zapamietywany jest tez przeskalowany obrazek. Ponowne skalowanie 
 * odbywa sie tylko wtedy, gdy chcemy skalowana wersje o innych wymiarach.
 * Zauwazmy jednak ze w naszej aplikacji jest typowe ze setki razy z rzedu
 * potrzebujemy skalowanej wersji o tym samym wymiarze, wiec gra jest warta
 * swieczki.
 */
public class Obrazy
{
    public Obrazy()
    {
        Graphics gr = def.img.getGraphics();
        gr.setColor(Color.BLACK);
        gr.fillRect(0, 0, 20, 20);
    }
    
    public void wczytaj(String path)
    {
        String[] nazwy = {"banan", "jablko", "pajak", "waz", "waz2", "sciana"};
        for (String nazwa: nazwy) {
            Image img = new BufferedImage(20,20,BufferedImage.TYPE_INT_ARGB);
            try {
                img = wczytajObraz(path + "/" + nazwa + ".png");
            } catch (Exception ex) {
                try {
                    img = wczytajObraz(path + "/" + nazwa + ".jpg");
                } catch (Exception ex1) {
                    System.err.println("Nie wczytano obrazka dla: \"" + nazwa + "\"");
                }
            }
            map.put(nazwa, img);
            skalowane.put(nazwa,
                   new Skalowany(img.getWidth(null), img.getHeight(null), img));
        }
    }
    
    public Image wczytajObraz(String path) throws Exception
    {
        InputStream in = getClass().getResourceAsStream(path);
        return ImageIO.read(in);
    }
    
    public Image daj(String nazwa)
    {
        Image img = map.get(nazwa);
        if (img == null)
            return def.img;
        return img;
    }
    
    public Image dajSkalowany(String nazwa, int w, int h)
    {
        Skalowany sk = skalowane.get(nazwa);
        if (sk == null)
        {
            if ( !(w == def.w && h == def.h) )
            {
                def.img = def.img.getScaledInstance(w, h, Image.SCALE_SMOOTH);
                def.h = h;
                def.w = w;
            }
            return def.img;
        }
        if ( !(w == sk.w && h == sk.h) )
        {
            sk.img = map.get(nazwa).getScaledInstance(w, h, Image.SCALE_SMOOTH);
            sk.h = h;
            sk.w = w;
        }
        return sk.img;
        
    }

    private Map<String, Image> map = new TreeMap<>();
    private Map<String, Skalowany> skalowane = new TreeMap<>();
    Skalowany def = new Skalowany(20,20,
            new BufferedImage(20,20,BufferedImage.TYPE_INT_ARGB));
}

/**
 * Zapamietuje obrazkek i jego wymiary. W zasadzie moznaby obejsc sie bez tej
 * klasy bo Image ma metody getWidth() i getHeight(), ale uznalem ze tak
 * bedzie wygodniej.
 */
class Skalowany
{
    public Skalowany(int w, int h, Image img)
    {
        this.w = w;
        this.h = h;
        this.img = img;
    }
    public int w;
    public int h; 
    public Image img;
}