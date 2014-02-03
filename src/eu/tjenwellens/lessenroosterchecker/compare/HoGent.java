package eu.tjenwellens.lessenroosterchecker.compare;

import eu.tjenwellens.lessenroosterchecker.LessenroosterChecker;
import eu.tjenwellens.lessenroosterchecker.elements.Klas;
import eu.tjenwellens.lessenroosterchecker.gui.CourseSelectionPanel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Collection;

/**
 *
 * @author Tjen
 */
public abstract class HoGent implements KlassenHolder, ActionListener {
    private Collection<Klas> klassen;
    private Collection<String> allVakken;
    private Collection<String> selection;
    private CourseSelectionPanel panel;
    private boolean ready = false;
    private final Object notify;

    public HoGent(Object notify) {
        this.notify = notify;
        loadVakken(getPaths());
    }

    private void loadVakken(Collection<String> paths) {
        klassen = LessenroosterChecker.getKlassen(paths);
        filter();
    }

    @Override
    public Collection<Klas> getKlassen() {
        return klassen;
    }

    private void filter() {
        LessenroosterChecker.filter(klassen, LessenroosterChecker.createExamenFilters());
        panel = CourseSelectionPanel.create();
        allVakken = LessenroosterChecker.extractVakken(klassen);
        panel.addBntOkListener(this);
        panel.setOptions(allVakken);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        selection = panel.getSelection();
        LessenroosterChecker.filter(klassen, LessenroosterChecker.createFilters(allVakken, selection));
        panel.hideFrame();
        ready();
    }

    @Override
    public boolean isReady() {
        return ready;
    }

    protected void ready() {
        ready = true;
        synchronized (notify) {
            notify.notify();
        }
    }

    protected abstract Collection<String> getPaths();
}