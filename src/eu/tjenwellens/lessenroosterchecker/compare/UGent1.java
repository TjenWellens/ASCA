package eu.tjenwellens.lessenroosterchecker.compare;

import eu.tjenwellens.lessenroosterchecker.elements.Klas;
import eu.tjenwellens.lessenroosterchecker.elements.Les;
import eu.tjenwellens.lessenroosterchecker.elements.LesCreator;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author Tjen
 */
public class UGent1 extends VakSelector {
    public UGent1(Object notify) {
        super(notify);
    }

    @Override
    protected List<Klas> loadVakken() {
        List<Klas> klassen = new LinkedList<>();
        Klas klas = new Klas("BI1");
        klas.add(entry1());
        klas.add(entry2());
        klassen.add(klas);
        return klassen;
    }

    private Les entry1() {
        LesCreator lc = new LesCreator("BI1", "maandag");
        lc.setNaam("BI/Analyse 1");
        lc.setDetails("");
        lc.setBeginUur("14:30");
        lc.setEindUur("17:15");
        lc.setLesvorm("?");
        lc.setWeken("21-35");
        return lc.createLes();
    }

    private Les entry2() {
        LesCreator lc = new LesCreator("BI1", "woensdag");
        lc.setNaam("BI/Analyse 1");
        lc.setDetails("");
        lc.setBeginUur("8:30");
        lc.setEindUur("11:15");
        lc.setLesvorm("?");
        lc.setWeken("21-35");
        return lc.createLes();
    }
}