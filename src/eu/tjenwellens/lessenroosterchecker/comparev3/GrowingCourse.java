package eu.tjenwellens.lessenroosterchecker.comparev3;

import eu.tjenwellens.lessenroosterchecker.elements.TimeStamp;
import eu.tjenwellens.lessenroosterchecker.elements.Vak;
import java.util.Collection;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

/**
 *
 * @author Tjen
 */
public class GrowingCourse {
    private List<LinkedList<Vak>> juisteVakken = new LinkedList<>();

    public GrowingCourse() {
    }

    public GrowingCourse(List<Vak> vakken) {
        initJuisteVakken(vakken);
    }

    private void initJuisteVakken(List<Vak> vakken) {
        for (Vak vak : vakken) {
            LinkedList<Vak> l = new LinkedList<>();
            l.add(vak);
            juisteVakken.add(l);
        }
    }

    public boolean merge(List<Vak> tryVakken) {
        if (tryVakken.isEmpty()) {
            return true;
        }
        if (juisteVakken.isEmpty()) {
            for (Vak vak : tryVakken) {
                LinkedList<Vak> l = new LinkedList<>();
                l.add(vak);
                juisteVakken.add(l);
            }
            return true;
        }
        List<LinkedList<Vak>> ok = new LinkedList<>();
        for (Vak vak : tryVakken) {
            for (List<Vak> juisteEntry : juisteVakken) {
                juisteEntry.add(vak);
                if (check(juisteEntry)) {
                    ok.add(new LinkedList<>(juisteEntry));
                }
                juisteEntry.remove(vak);
                if (juisteEntry.contains(vak)) {
                    throw new RuntimeException("Vakken niet uniek");
                }
            }
        }
        if (ok.isEmpty()) {
            System.out.println("errr");
        }
        juisteVakken = ok;
        return !juisteVakken.isEmpty();
    }

    public boolean merge(GrowingCourse gc) {
        if (gc.isEmpty()) {
            return true;
        }
        while (!gc.juisteVakken.get(0).isEmpty()) {
            LinkedList<Vak> tryVakken = new LinkedList<>();
            for (LinkedList<Vak> vakken : gc.juisteVakken) {
                tryVakken.add(vakken.removeFirst());
            }
            if (!merge(tryVakken)) {
                return false;
            }
        }
        return true;
    }

    private boolean check(Collection<Vak> vakken) {
        Set<TimeStamp> check = new HashSet<>();
        for (Vak vak : vakken) {
            for (TimeStamp timeStamp : vak.getAllTimeStamps()) {
                if (!check.add(timeStamp)) {
                    return false;
                }
            }
        }
        return true;
    }

    public List<? extends List<Vak>> getJuisteVakken() {
        return juisteVakken;
    }

    public boolean isEmpty() {
        return juisteVakken.isEmpty();
    }

    public int size() {
        return juisteVakken.size();
    }
}
