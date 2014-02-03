package eu.tjenwellens.lessenroosterchecker;

import eu.tjenwellens.lessenroosterchecker.comparev3.GrowingCourseComparer;
import eu.tjenwellens.lessenroosterchecker.elements.Klas;
import eu.tjenwellens.lessenroosterchecker.elements.Les;
import eu.tjenwellens.lessenroosterchecker.elements.LesCreator;
import eu.tjenwellens.lessenroosterchecker.filters.*;
import eu.tjenwellens.lessenroosterchecker.gui.CourseSelectionPanel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Collection;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import org.jsoup.select.Elements;

/**
 *
 * @author Tjen
 */
public class LessenroosterChecker {
    private Collection<Klas> klassen;
    private CourseSelectionPanel panel;
    private Collection<String> allVakken;
    private Collection<String> selection;

    public LessenroosterChecker(Collection<Klas> klassen) {
        this.klassen = klassen;
        panel = CourseSelectionPanel.create();
        allVakken = extractVakken(klassen);
        panel.addBntOkListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("actionPerformed");
                selectionMade();
            }
        });
        panel.setOptions(allVakken);
    }

    private void selectionMade() {
        selection = panel.getSelection();
        filter(klassen, createFilters(allVakken, selection));
        panel.hideFrame();
        printEntries(klassen, -1);
        System.out.println();
        System.out.println("Courses: ");
        for (String string : extractVakken(klassen)) {
            System.out.println(string);
        }
        System.exit(0);
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
//        Collection<Klas> klassen = getKlassen(Arrays.asList(args));
//        new LessenroosterChecker(klassen);
//        new KlasComparer();
        GrowingCourseComparer.main(args);
    }

    public static Collection<Filter> createFilters(Collection<String> allVakken, Collection<String> selection) {
        Set<String> not = new HashSet<>(allVakken);
        not.removeAll(selection);
        Collection<Filter> filters = new LinkedList<>();
        for (String courseName : not) {
            filters.add(new Not(new FilterCursusNaam(courseName)));
        }
        return filters;
    }

    public static Collection<String> extractVakken(Collection<Klas> klassen) {
        HashSet<String> vakken = new HashSet<>();
        for (Klas klas : klassen) {
            vakken.addAll(klas.getCourseNames());
        }
        return vakken;
    }

    public static Collection<Klas> getKlassen(Collection<String> paths) {
//        List<String> paths = new ArrayList<>(Arrays.asList(args));
//        paths.add("showtimetable1.html");
//        paths.add("showtimetable2.html");
        Elements es = Parser.mergeBodyContents(paths);
        if (es.size() % 14 != 0) {
            System.err.println("Size is not dividable by 14");
        }
        List<Klas> klas_entries = Parser.getParseKlassen(es);
        filter(klas_entries, createExamenFilters());
//        printEntries(klas_entries, -1);
        return klas_entries;
    }

    public static void printEntries(Collection<Klas> klassen, int amount) {
        System.out.println("Aantal klassen: " + klassen.size());
        int teller = 0;
        System.out.println(makeHeaderEntry());
        for (Klas klas : klassen) {
            teller += printEntry(klas, amount - teller);
        }
    }

    public static int printEntry(Collection<Les> lessen, int amount) {
        System.out.println("Aantal klassen: " + lessen.size());
        int teller = 0;
        System.out.println(makeHeaderEntry());
        for (Les les : lessen) {
            if (amount >= 0 && teller >= amount) {
                break;
            }
            System.out.println(les);
            teller++;
        }
        return teller;
    }

    private static LesCreator makeHeaderEntry() {
        LesCreator vakEntry = new LesCreator("klas", "dag");
        vakEntry.setNaam("Naam");
        vakEntry.setDetails("Details");
        vakEntry.setWeken("Weken");
        vakEntry.setBeginUur("Begin");
        vakEntry.setEindUur("Eind");
        vakEntry.setLesvorm("Lesvorm");
        return vakEntry;
    }

    public static Collection<Filter> createExamenFilters() {
        Collection<Filter> filters = new LinkedList<>();
        filters.add(new Not(new FilterLesvorm("EXSC")));
        filters.add(new Not(new FilterLesvorm("EXPC")));
//        filters.add(new Not(new FilterLesvorm("EXPC")));
        return filters;
    }

    public static void filter(Collection<Klas> klas_entries, Collection<Filter> filters) {
        for (Klas klas : klas_entries) {
            klas.removeAll(filters);
        }
    }
}
