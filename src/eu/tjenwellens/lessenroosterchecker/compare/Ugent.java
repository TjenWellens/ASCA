package eu.tjenwellens.lessenroosterchecker.compare;

import eu.tjenwellens.lessenroosterchecker.elements.Klas;
import eu.tjenwellens.lessenroosterchecker.elements.Les;
import eu.tjenwellens.lessenroosterchecker.elements.LesCreator;
import java.util.Collection;
import java.util.LinkedList;

/**
 *
 * @author Tjen
 */
public class Ugent implements KlassenHolder {
    private Collection<Klas> klassen;

    public Ugent() {
        this.klassen = new LinkedList<>();
        Klas klas = new Klas("unief");
        klas.add(entry1());
        klas.add(entry2());
        klassen.add(klas);
    }

    public final Les entry1() {
        LesCreator lc = new LesCreator("unief", "maandag");
        lc.setNaam("Computerarchitectuur");
        lc.setDetails("");
        lc.setBeginUur("10:00");
        lc.setEindUur("12:45");
        lc.setLesvorm("theorie");
        lc.setWeken("21-35");
        return lc.createLes();
    }

    public final Les entry2() {
        LesCreator lc = new LesCreator("unief", "donderdag");
        lc.setNaam("Computerarchitectuur");
        lc.setDetails("");
        lc.setBeginUur("14:30");
        lc.setEindUur("17:15");
        lc.setLesvorm("oefeningen");
        lc.setWeken("21-35");
        return lc.createLes();
    }

    @Override
    public Collection<Klas> getKlassen() {
        return klassen;
    }

    @Override
    public boolean isReady() {
        return true;
    }
}
