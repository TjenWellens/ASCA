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
    private List<List<Vak>> juisteVakken = new LinkedList<>();

    public boolean merge(List<Vak> tryVakken) {
        if (tryVakken.isEmpty()) {
            return true;
        }
        if (juisteVakken.isEmpty()) {
            for (Vak vak : tryVakken) {
                List<Vak> l = new LinkedList<>();
                l.add(vak);
                juisteVakken.add(l);
            }
            return true;
        }
        List<List<Vak>> ok = new LinkedList<>();
        for (Vak vak : tryVakken) {
            for (List<Vak> juisteEntry : juisteVakken) {
                juisteEntry.add(vak);
                if (check(juisteEntry)) {
                    ok.add(new LinkedList<>(juisteEntry));
                }
                juisteEntry.remove(vak);
            }
        }
        juisteVakken = ok;
        return !juisteVakken.isEmpty();
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

    public List<List<Vak>> getJuisteVakken() {
        return juisteVakken;
    }

    public boolean isEmpty() {
        return juisteVakken.isEmpty();
    }

    public int size() {
        return juisteVakken.size();
    }
}
