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
public class UGent extends VakSelector {
    public UGent(Object notify) {
        super(notify);
    }

    @Override
    protected Collection<Klas> loadVakken() {
        Collection<Klas> klassen = new LinkedList<>();
        Klas klas = new Klas("uni");
        klas.add(entry1());
        klas.add(entry2());
        klassen.add(klas);
        return klassen;
    }

    private Les entry1() {
        LesCreator lc = new LesCreator("uni", "maandag");
        lc.setNaam("Computerarchitectuur");
        lc.setDetails("");
        lc.setBeginUur("10:00");
        lc.setEindUur("12:45");
        lc.setLesvorm("theorie");
        lc.setWeken("21-35");
        return lc.createLes();
    }

    private Les entry2() {
        LesCreator lc = new LesCreator("uni", "donderdag");
        lc.setNaam("Computerarchitectuur");
        lc.setDetails("");
        lc.setBeginUur("14:30");
        lc.setEindUur("17:15");
        lc.setLesvorm("oefeningen");
        lc.setWeken("21-35");
        return lc.createLes();
    }
}
