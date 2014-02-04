package eu.tjenwellens.lessenroosterchecker.filters;

import eu.tjenwellens.lessenroosterchecker.elements.Les;

/**
 *
 * @author Tjen
 */
public class InhaallesFilter extends AbstractFilter {
    @Override
    public boolean accept(Les les) {
        return les.getActiviteitOmschrijving().contains("Inhaalles") || les.getActiviteitOmschrijving().contains("inhaalles");
    }

    @Override
    protected boolean equals(Filter otherFilter) {
        if (otherFilter == null) {
            return false;
        }
        if (getClass() != otherFilter.getClass()) {
            return false;
        }
//        final InhaallesFilter other = (FilterCursusNaam) otherFilter;
//        if (!Objects.equals(this.cursusNaam, other.cursusNaam)) {
//            return false;
//        }
        return true;
    }
}
