package eu.tjenwellens.lessenroosterchecker.filters;

import eu.tjenwellens.lessenroosterchecker.elements.Les;
import java.util.Objects;

/**
 *
 * @author Tjen
 */
public class FilterCursusNaam extends AbstractFilter {
    private String cursusNaam;

    public FilterCursusNaam(String vakNaam) {
        this.cursusNaam = vakNaam;
    }

    @Override
    public boolean accept(Les les) {
        return cursusNaam.equals(les.getCursusNaam());
    }

    @Override
    protected boolean equals(Filter otherFilter) {
        if (otherFilter == null) {
            return false;
        }
        if (getClass() != otherFilter.getClass()) {
            return false;
        }
        final FilterCursusNaam other = (FilterCursusNaam) otherFilter;
        if (!Objects.equals(this.cursusNaam, other.cursusNaam)) {
            return false;
        }
        return true;
    }
}
