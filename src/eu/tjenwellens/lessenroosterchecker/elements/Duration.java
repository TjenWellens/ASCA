package eu.tjenwellens.lessenroosterchecker.elements;

/**
 *
 * @author Tjen
 */
public class Duration implements Comparable<Duration> {
    private Tijd begin;
    private Tijd eind;

    public Duration(Tijd begin, Tijd eind) {
        this.begin = begin;
        this.eind = eind;
    }

    public Duration(Duration source) {
        this.begin = source.begin;
        this.eind = source.eind;
    }

    public Tijd getBegin() {
        return begin;
    }

    public Tijd getEind() {
        return eind;
    }

    @Override
    public int compareTo(Duration o) {
        if (eind.compareTo(o.begin) <= 0) {
            return -1;
        } else if (begin.compareTo(o.eind) >= 0) {
            return 1;
        } else {
            return 0;
        }
    }

    @Override
    public int hashCode() {
        int hash = 7;
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
        final Duration other = (Duration) obj;
        return this.compareTo(other) == 0;
    }

    @Override
    public String toString() {
        return begin + "\t" + eind;
    }
}