package eu.tjenwellens.lessenroosterchecker.elements;

/**
 *
 * @author Tjen
 */
public class Tijd implements Comparable<Tijd> {
    private int uur;
    private int minuten;
    private int total;

    public Tijd(int uur, int minuten) {
        this.uur = uur;
        this.minuten = minuten;
        this.total = uur * 100 + (minuten / 15 * 25);
    }

    public static Tijd parseTijd(String tijdString) {
        try {
            String[] uur_min = tijdString.split(":");
            return new Tijd(Integer.parseInt(uur_min[0]), Integer.parseInt(uur_min[1]));
        } catch (ArrayIndexOutOfBoundsException | NumberFormatException e) {
            return null;
        }
    }

    public int getTotal() {
        return total;
    }

    @Override
    public String toString() {
        return uur + ":" + minuten;
    }

    @Override
    public int compareTo(Tijd o) {
        return Long.compare(total, o.total);
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 97 * hash + (int) (this.total ^ (this.total >>> 32));
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
        final Tijd other = (Tijd) obj;
        if (this.total != other.total) {
            return false;
        }
        return true;
    }
}
