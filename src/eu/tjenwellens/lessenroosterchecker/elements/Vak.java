package eu.tjenwellens.lessenroosterchecker.elements;

import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Objects;
import java.util.Set;

/**
 *
 * @author Tjen
 */
public class Vak implements Collection<Les> {
    private String courseName;
    private String klasNaam;
    private Collection<Les> lessen = new LinkedList<>();

    public String getCourseName() {
        return courseName;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 29 * hash + Objects.hashCode(this.courseName);
        hash = 29 * hash + Objects.hashCode(this.klasNaam);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Vak other = (Vak) obj;
        if (!Objects.equals(this.courseName, other.courseName)) {
            return false;
        }
        if (!Objects.equals(this.klasNaam, other.klasNaam)) {
            return false;
        }
        return true;
    }

    public Vak(String courseName, String klasNaam) {
        this.courseName = courseName;
        this.klasNaam = klasNaam;
    }

    public Vak(Vak source) {
        this.courseName = source.courseName;
        for (Les les : source.lessen) {
            lessen.add(new ConcreteLes(les));
        }
    }

    public String getKlasNaam() {
        return klasNaam;
    }

    @Override
    public int size() {
        return lessen.size();
    }

    @Override
    public boolean isEmpty() {
        return lessen.isEmpty();
    }

    @Override
    public boolean contains(Object o) {
        return lessen.contains(o);
    }

    @Override
    public Iterator<Les> iterator() {
        return lessen.iterator();
    }

    @Override
    public Object[] toArray() {
        return lessen.toArray();
    }

    @Override
    public <T> T[] toArray(T[] a) {
        return lessen.toArray(a);
    }

    @Override
    public boolean add(Les e) {
        if (this.courseName.equals(e.getCursusNaam())) {
            return lessen.add(e);
        } else {
            return false;
        }
    }

    @Override
    public boolean remove(Object o) {
        return lessen.remove(o);
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        return lessen.containsAll(c);
    }

    @Override
    public boolean addAll(Collection<? extends Les> lessen) {
        boolean succes = false;
        for (Les les : lessen) {
            succes = add(les) || succes;
        }
        return succes;
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        return lessen.removeAll(c);
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        return lessen.retainAll(c);
    }

    @Override
    public void clear() {
        lessen.clear();
    }

    public Collection<TimeStamp> getAllTimeStamps() {
        Set<TimeStamp> timeStamps = new HashSet<>();
        for (Les les : lessen) {
            timeStamps.addAll(les.getTimeStamps());
        }
        return timeStamps;
    }
}
