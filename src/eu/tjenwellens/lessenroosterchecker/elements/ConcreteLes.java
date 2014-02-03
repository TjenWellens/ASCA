package eu.tjenwellens.lessenroosterchecker.elements;

import eu.tjenwellens.lessenroosterchecker.filters.Filter;
import java.util.HashSet;
import java.util.Set;
import java.util.TreeSet;

/**
 *
 * @author Tjen
 */
class ConcreteLes implements Les {
    private String faculteit;
    private String richting;
    private String klas;
    private WeekDag dag;
    private String cursusNaam;
    private String activiteitOmschrijving;
    private TreeSet<Integer> weken;
    private Duration duur;
    private String lesvorm;
    private Set<TimeStamp> timeStamps;

    public ConcreteLes(String faculteit, String richting, String klas, WeekDag dag, String cursusNaam, String activiteitOmschrijving, TreeSet<Integer> weken, Duration duur, String lesvorm) {
        this.faculteit = faculteit;
        this.richting = richting;
        this.klas = klas;
        this.dag = dag;
        this.cursusNaam = cursusNaam;
        this.activiteitOmschrijving = activiteitOmschrijving;
        this.weken = weken;
        this.duur = duur;
        this.lesvorm = lesvorm;
        try {
            initTimeStamps();
        } catch (TimeStampOverlapException ex) {
            System.err.println(ex);
        }
    }

    public ConcreteLes(Les les) {
        this(les.getFaculteit(), les.getRichting(), les.getKlas(), les.getDag(), les.getCursusNaam(), les.getActiviteitOmschrijving(), new TreeSet<>(les.getWeken()), les.getDuur(), les.getLesvorm());
        timeStamps = new HashSet<>(les.getTimeStamps());
    }

    private void initTimeStamps() throws TimeStampOverlapException {
        this.timeStamps = new HashSet();
        boolean overlap = false;
        for (TimeStamp timeStamp : TimeStamp.createTimeStamps(weken, dag, duur.getBegin(), duur.getEind())) {
            overlap = !timeStamps.add(timeStamp) || overlap;
        }
        if (overlap) {
            throw new TimeStampOverlapException(this);
        }
    }

    @Override
    public String toString() {
        return klas + "\t" + dag + "\t" + cursusNaam + "\t" + activiteitOmschrijving + "\t" + weken + "\t" + duur + "\t" + lesvorm;
    }

    @Override
    public int hashCode() {
        return 1;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (obj instanceof Les) {
            // Les: check timestamps overlap
//            System.out.println("Les equals les?");
            final Les other = (Les) obj;
            Set<TimeStamp> sameStamps = new HashSet<>(this.timeStamps);
            sameStamps.retainAll(other.getTimeStamps());
            return !sameStamps.isEmpty();
        } else if (obj instanceof Filter) {
            // Filter: check if filter applies
//            System.out.println("Les equals filter?");
            final Filter other = (Filter) obj;
            return !other.accept(this);
        }
        return false;
    }

    @Override
    public String getFaculteit() {
        return faculteit;
    }

    @Override
    public String getRichting() {
        return richting;
    }

    @Override
    public String getKlas() {
        return klas;
    }

    @Override
    public WeekDag getDag() {
        return dag;
    }

    @Override
    public String getCursusNaam() {
        return cursusNaam;
    }

    @Override
    public String getActiviteitOmschrijving() {
        return activiteitOmschrijving;
    }

    @Override
    public TreeSet<Integer> getWeken() {
        return weken;
    }

    @Override
    public Duration getDuur() {
        return duur;
    }

    @Override
    public String getLesvorm() {
        return lesvorm;
    }

    @Override
    public Set<TimeStamp> getTimeStamps() {
        return timeStamps;
    }
}
