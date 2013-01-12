

package waz;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.*;

/**
 * Klasa z ramka gry. Zawiera tez punkt startowy aplikacji -- statyczna 
 * funkcje main.
 */
public class Gra extends JFrame
{    
    /**
     * Funkcja main wczytuje obrazki uzywane w grze, a nastepnie tworzy
     * ramke z gra.
     * @param args zadne argumenty wywolania nie sa uzywane.
     */
    public static void main(String[] args)
    {
        Obrazy obrazy = new Obrazy();
        obrazy.wczytaj("/obrazy");
        ObiektNaPlanszy.obrazy = obrazy;
        
        JFrame frame = new Gra();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);    
        frame.setTitle("Wonsz");
    }
    
    /**
     * Konstruktor ramki.
     */
    public Gra()
    {
        setSize(480,520);
        stworzMenu();
        ustawKeyListener();
    }
    
    /**
     * Tworzy pasek menu.
     */
    private void stworzMenu()
    {
        JMenuBar bar = new JMenuBar();
        
        JMenu gra = createGraMenu();
        bar.add(gra);
        
        JMenu planszam = createPlanszaMenu();
        bar.add(planszam);
        
        JMenu wielkosc = createWielkoscMenu();
        bar.add(wielkosc);
        
        setJMenuBar(bar);
    }
    
    /**
     * Tworzy menu "Gra.
     * @return menu "Gra. 
     */
    private JMenu createGraMenu()
    {
        JMenu gra = new JMenu("Gra");
        JMenuItem nowa = new JMenuItem("Nowa gra");
        nowa.addActionListener( new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                zakonczGre();
                zacznijGre();
            }
        });
        JMenuItem zakoncz = new JMenuItem("Zakończ");
        zakoncz.addActionListener( new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                zakonczGre();
                System.exit(0);
            }
        });
        gra.add(nowa);
        gra.add(zakoncz);
        return gra;
    }
    
    /**
     * Tworzy menu "Plansza".
     * @return menu "Plansza".
     */
    private JMenu createPlanszaMenu()
    {
        JMenu planszam = new JMenu("Plansza");
        ButtonGroup bg = new ButtonGroup();
        
        JRadioButtonMenuItem item = new JRadioButtonMenuItem("Zwykła");
        item.setSelected(true);
        item.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                nazwaPlanszy = "zwykla";
            }
        });
        bg.add(item);
        planszam.add(item);
    
        item = new JRadioButtonMenuItem("Ogrodzona");
        item.setSelected(false);
        item.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                nazwaPlanszy = "ogrodzona";
            }
        });
        bg.add(item);
        planszam.add(item);
    
        item = new JRadioButtonMenuItem("Tron");
        item.setSelected(false);
        item.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                nazwaPlanszy = "tron";
            }
        });
        bg.add(item);
        planszam.add(item);
    
        item = new JRadioButtonMenuItem("Wiele przeszkód");
        item.setSelected(false);
        item.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                nazwaPlanszy = "przeszkody";
            }
        });
        bg.add(item);
        planszam.add(item);
        
        return planszam;
    }
    
    /**
     * Tworzy menu "Wielkosc".
     * @return menu "Wielkosc".
     */
    private JMenu createWielkoscMenu()
    {
        JMenu wielkosc = new JMenu("Wielkość");
        ButtonGroup bg = new ButtonGroup();
        
        JRadioButtonMenuItem item = new JRadioButtonMenuItem("Mala");
        item.setSelected(true);
        item.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Gra.this.setSize(480,520);
                wymiarPlanszy = new Poz(12,12);
            }
        });
        bg.add(item);
        wielkosc.add(item);
        
        item = new JRadioButtonMenuItem("Srednia");
        item.setSelected(false);
        item.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Gra.this.setSize(625,540);
                wymiarPlanszy = new Poz(20,25);
            }
        });
        bg.add(item);
        wielkosc.add(item);
        
        item = new JRadioButtonMenuItem("Duza");
        item.setSelected(false);
        item.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Gra.this.setSize(800,540);
                wymiarPlanszy = new Poz(25,40);
            }
        });
        bg.add(item);
        wielkosc.add(item);
        
        return wielkosc;
    }
 
    /**
     * Tworzy timer, ktory w rownych odstepach czasu, bedzie aktualizowal logike
     * gry, ale go nie uruchamia.
     * @return (nieuruchomiony) timer aktualizujacy logike.
     */
    private Timer timerLogika()
    {
        int delay = 50; //50ms -- aktualizacja 20 razy na sekunde
        ActionListener taskPerformer = new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent evt) {
                if (plansza.koniecGry()) {
                    zakonczGre();
                    pokazWynik();
                }
                else plansza.uaktualnij();
            }
        };
        return new Timer(delay, taskPerformer);
    }
     
    /**
     * Tworzy timer, ktory w rownych odstepach czasu, bedzie rysowal plansze
     * gry, ale go nie uruchamia.
     * @return (nieuruchomiony) timer rysujacy plansze.
     */
    private Timer timerRysownik()
    {
        int delay = 50; //50ms -- rysowanie 20 razy na sekunde
        ActionListener taskPerformer = new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent evt) {
                plotno.repaint();
            }
        };
        return new Timer(delay, taskPerformer);
    }
    
    /**
     * Funkcja rozpoczynajaca gry. Wywoluje metody tworzace plaszne, timery, 
     * plotno. Dla duzych plansz ustawia wieksza predkosc weza. Uruchamia
     * timery.
     */
    private void zacznijGre()
    {
        wybierzMape();
        if (wymiarPlanszy.w > 20) {
            Waz waz = plansza.dajWeza();
            waz.przyspiesz(40);
        } else if (wymiarPlanszy.w > 15) {
            Waz waz = plansza.dajWeza();
            waz.przyspiesz(100);            
        }
        logika = timerLogika();
        rysownik = timerRysownik();
        stworzPlotno();
        rysownik.start();
        logika.start();
    }
    
    /**
     * Tworzy obiekt na ktorym rysowana jest plansza i ustawia go jaka glowny
     * panel aplikacji.
     */
    private void stworzPlotno()
    {
        plotno = new Plotno(plansza);
        setContentPane(plotno);
        revalidate();
    }
   
    /**
     * Na podstawie opcji wybranych w menu przez gracza utowrzy odpowiednia
     * mape. Inicuje ja.
     */
    private void wybierzMape()
    {
        Poz p = wymiarPlanszy;
        switch (nazwaPlanszy)
        {
            case "tron":
                plansza = factory.zwykla(p.w, p.k);
                plansza.init();
                plansza.dajWeza().wydluz(p.w * p.k);
                break;
            case "ogrodzona":
                plansza = factory.ogrodzona(p.w, p.k);
                plansza.init();
                break;
            case "przeszkody":
                plansza = factory.wielePrzeszkod(p.w, p.k);
                plansza.init();
                break;
            default:
                plansza = factory.zwykla(p.w, p.k);
                plansza.init();
        }
    }
    
    /**
     * Wyswietla okienko dialogowe z wynikiem.
     */
    private void pokazWynik()
    {
        JOptionPane.showMessageDialog(this,
                "Wonsz umarl :( Zdobyles " + plansza.ilePunktow() + " punktów!",
                "Koniec gry", JOptionPane.INFORMATION_MESSAGE);        
    }
    
    /**
     * Zatrzymuje timery.
     */
    private void zakonczGre()
    {
        if (plansza != null)
        {
            logika.stop();
            rysownik.stop();
        }
    }
    
    /**
     * Ustawia nasluch na naciskane klawisze.
     */
    private void ustawKeyListener()
    {
        addKeyListener( new KeyAdapter() {            
            @Override
            public void keyPressed(KeyEvent e)
            {
                if (plansza == null) return ;
                if (e.getKeyCode() == KeyEvent.VK_UP)
                    plansza.dajWeza().ustawKier(ObiektRuchomy.UP);
                else if (e.getKeyCode() == KeyEvent.VK_RIGHT)
                    plansza.dajWeza().ustawKier(ObiektRuchomy.RIGHT);
                else if (e.getKeyCode() == KeyEvent.VK_DOWN)
                    plansza.dajWeza().ustawKier(ObiektRuchomy.DOWN);
                else if (e.getKeyCode() == KeyEvent.VK_LEFT)
                    plansza.dajWeza().ustawKier(ObiektRuchomy.LEFT);                    
            }            
        });
    }
    
    /**
     * Fabryka plansza potrzebna do tworzenia plansz.
     */
    private FabrykaPlansz factory = new FabrykaPlansz();
    
    /**
     * Plotno na ktorym rysowana jest plansza. Przy kazdym rozpoczeciu nowej 
     * gry tworzone jest nowe plotno.
     */
    private Plotno plotno;   
    
    /**
     * Obiekt planszy. Przy kazdym rozpoczeciu nowej gry tworzona jest nowa.
     */
    private Plansza plansza;
    
    /**
     * Timer aktualizujacy logike gry. Przy kazdym rozpoczeciu nowej gry
     * tworzony jest nowy.
     */
    private Timer logika;
        
    /**
     * Timer przerysowujacy plansze. Przy kazdym rozpoczeciu nowej gry
     * tworzony jest nowy.
     */
    private Timer rysownik;
    
    /**
     * Nazwa rodzaju planszy, ktora zostanie utworzona przy wybraniu nowej gry.
     */
    private String nazwaPlanszy = "zwykla";
    
    /**
     * Wymiary planszy, ktora zostanie utworzona przy wybraniu nowej gry. 
     */
    private Poz wymiarPlanszy = new Poz(12,12);
}
