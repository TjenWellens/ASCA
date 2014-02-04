package eu.tjenwellens.lessenroosterchecker.compare;

import eu.tjenwellens.lessenroosterchecker.LessenroosterChecker;
import eu.tjenwellens.lessenroosterchecker.elements.Klas;
import eu.tjenwellens.lessenroosterchecker.gui.CourseSelectionPanel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author Tjen
 */
public abstract class VakSelector extends Thread implements Jaar, ActionListener {
    private List<Klas> klassen;
    private Collection<String> allVakken;
    private Collection<String> selection;
    private CourseSelectionPanel panel;
    private boolean ready = false;
    private final Object notify;

    public VakSelector(Object notify) {
        this.notify = notify;
    }

    @Override
    public void run() {
        this.klassen = loadVakken();
        filter();
    }

    protected abstract List<Klas> loadVakken();

    @Override
    public List<Klas> getKlassen() {
        Collection<Klas> remove = new LinkedList<>();
        for (Klas klas : klassen) {
            if (klas.isEmpty()) {
                remove.add(klas);
            }
        }
        klassen.removeAll(remove);
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
}