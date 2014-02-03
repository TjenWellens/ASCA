package eu.tjenwellens.lessenroosterchecker.elements;

import java.util.Set;

/**
 *
 * @author Tjen
 */
public interface Les {
    String getFaculteit();

    String getRichting();

    String getKlas();

    WeekDag getDag();

    String getCursusNaam();

    String getActiviteitOmschrijving();

    Set<Integer> getWeken();

    Duration getDuur();

    String getLesvorm();

    Set<TimeStamp> getTimeStamps();
}
