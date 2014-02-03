package eu.tjenwellens.lessenroosterchecker.elements;

import java.util.TreeSet;

/**
 *
 * @author Tjen
 */
public class LesCreator {
    private String klas;
    private String dag;
    private String naam;
    private String details;
    private String weken;
    private String beginUur;
    private String eindUur;
    private String lesvorm;

    public LesCreator(String klas, String dag) {
        this.klas = klas;
        this.dag = dag;
    }

    @Override
    public String toString() {
        return klas + "\t" + dag + "\t" + naam + "\t" + details + "\t" + weken + "\t" + beginUur + "\t" + eindUur + "\t" + lesvorm;
    }

    public void setNaam(String naam) {
        this.naam = naam;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public void setWeken(String weken) {
        this.weken = weken;
    }

    public void setBeginUur(String beginUur) {
        this.beginUur = beginUur;
    }

    public void setEindUur(String eindUur) {
        this.eindUur = eindUur;
    }

    public void setLesvorm(String lesVorm) {
        this.lesvorm = lesVorm;
    }

    public Les createLes() {
        String faculteit = "", richting = "", klas;
        String[] arr = this.klas.split("/", 3);
        if (arr.length < 3) {
            klas = this.klas;
        } else {
            faculteit = arr[0];
            richting = arr[1];
            klas = arr[2];
        }
        WeekDag dag;
        try {
            dag = WeekDag.valueOf(this.dag);
        } catch (IllegalArgumentException e) {
            System.err.println("Failed to create les: " + e);
            return null;
        }
        String cursusNaam = this.naam.substring(this.naam.lastIndexOf('/') + 1);
//        String cursusNaam = this.naam;
        String activiteitOmschrijving = this.details;
        TreeSet<Integer> weken = new TreeSet<>();
        for (String weekEntry : this.weken.split(", ")) {
            String[] weekRange = weekEntry.split("-");
            int begin = Integer.parseInt(weekRange[0]);
            int eind = weekRange.length > 1 ? Integer.parseInt(weekRange[1]) : begin;
            for (int week = begin; week <= eind; week++) {
                weken.add(Integer.valueOf(week));
            }
        }
        Tijd begin = Tijd.parseTijd(this.beginUur);
        Tijd eind = Tijd.parseTijd(this.eindUur);
        if (begin == null || eind == null) {
            System.err.println("Failed to create les: " + begin + "-" + eind);
            return null;
        }
        Duration duur = new Duration(begin, eind);
        String lesvorm = this.lesvorm;

        Les les = new ConcreteLes(faculteit, richting, klas, dag, cursusNaam, activiteitOmschrijving, weken, duur, lesvorm);
        return les;
    }
}
