package eu.tjenwellens.lessenroosterchecker.elements;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 *
 * @author Tjen
 */
public class TimeStamp {
    int totalStamp;
    ConcreteLes les;

    public TimeStamp(ConcreteLes les, int week, int day, int hour, int minute) {
        this(les, week, day, hour * 100 + minute);
    }

    public TimeStamp(ConcreteLes les, int week, int day, int hour_minute) {
        totalStamp = week * 100000 + day * 10000 + hour_minute;
        this.les = les;
    }

    public static List<TimeStamp> createTimeStamps(ConcreteLes les, Collection<Integer> weeks, WeekDag weekDay, Tijd begin, Tijd end) {
        List<TimeStamp> timeStamps = new ArrayList<>();
        for (Integer week : weeks) {
            int dag = weekDay.ordinal();
            for (int hour_minute = begin.getTotal() + 25; hour_minute < end.getTotal(); hour_minute += 25) {
                timeStamps.add(new TimeStamp(les, week, dag, hour_minute));
            }
        }
        return timeStamps;
    }

    @Override
    public int hashCode() {
        return this.totalStamp;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final TimeStamp other = (TimeStamp) obj;
        if (this.totalStamp != other.totalStamp) {
            return false;
        }
        return true;
    }
}