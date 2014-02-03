package eu.tjenwellens.lessenroosterchecker.comparev4;

import eu.tjenwellens.lessenroosterchecker.compare.Jaar;
import eu.tjenwellens.lessenroosterchecker.comparev2.Course;
import eu.tjenwellens.lessenroosterchecker.comparev2.CourseComparer;
import eu.tjenwellens.lessenroosterchecker.comparev2.Courses;
import eu.tjenwellens.lessenroosterchecker.comparev3.GrowingCourse;
import eu.tjenwellens.lessenroosterchecker.elements.Klas;
import eu.tjenwellens.lessenroosterchecker.elements.Vak;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author Tjen
 */
public class MultiGrowingCourseComparer extends CourseComparer {
    private static final boolean goCrazy = false;

    @Override
    public void doneWaiting() {
        printSelectedVakken();
        System.out.println("\n========================\n");
        // convert jaren to courses
        Courses courses = new Courses();
        for (Jaar jaar : jaren) {
            for (Klas klas : jaar.getKlassen()) {
                courses.addAll(klas);
            }
        }
        System.out.println("\n========================\n");
        // convert courses
        Collection<Course> cc = courses.getAllCourses();
        System.out.println("Total: " + cc.size());
        // MERGE
        doMultyMerge(cc);
        GrowingCourse gc = gcs.getFirst();
        if (gc.isEmpty()) {
            System.out.println("Geen mogelijke oplossingen gevonden");
            System.exit(0);
        }
        System.out.println("Aantal oplossingen: " + gc.size());
        if (!goCrazy) {
            System.exit(0);
        }
        int teller = 1;
        for (Collection<Vak> vakkenMatch : gc.getJuisteVakken()) {
            System.out.print("Oplossing " + teller + ":\t");
            for (Vak vak : vakkenMatch) {
                System.out.print(vak.getKlasNaam());
                System.out.print('\t');
            }
            System.out.println();
            teller++;
        }
        System.exit(0);
    }
    private final LinkedList<GrowingCourse> gcs = new LinkedList<>();
    private final Collection<Merger> mergers = new LinkedList<>();

    private void doMultyMerge(Collection<Course> cc) {
        initGcs(cc);
        synchronized (gcs) {
            int totalSize;
            synchronized (mergers) {
                totalSize = gcs.size() + mergers.size();
            }
            while (totalSize > 1) {
                while (gcs.size() < 2) {
                    try {
                        gcs.wait();
                    } catch (InterruptedException ex) {
                    }
                }
                System.out.println("CurrentSize: " + totalSize);
                GrowingCourse destination = gcs.removeFirst();
                GrowingCourse other = gcs.removeFirst();
                Merger m = new Merger(destination, other);
                synchronized (mergers) {
                    mergers.add(m);
                    totalSize = gcs.size() + mergers.size();
                }
                m.start();
            }
            while (gcs.isEmpty()) {
                try {
                    gcs.wait();
                } catch (InterruptedException ex) {
                }
            }
        }
    }

    private void initGcs(Collection<Course> cc) {
        synchronized (gcs) {
            for (Course course : cc) {
                course.fix();
                gcs.add(new GrowingCourse(course.getAllVakken()));
            }
        }
    }

    private void addGrowingCourse(GrowingCourse c) {
        synchronized (gcs) {
            gcs.add(c);
            gcs.notify();
        }
    }

    private void fail() {
        System.out.println("Geen mogelijke oplossingen gevonden, bye");
        System.exit(0);
    }

    private void done(Merger m) {
        synchronized (mergers) {
            mergers.remove(m);
        }
    }

    public static void main(String[] args) {
        new MultiGrowingCourseComparer().start();
    }

    private class Merger extends Thread {
        GrowingCourse destination;
        GrowingCourse other;

        public Merger(GrowingCourse destination, GrowingCourse other) {
            this.destination = destination;
            this.other = other;
        }

        @Override
        public void run() {
            long time = System.currentTimeMillis();
            if (tryMerge(destination, other)) {
                System.out.println("Done:" + (System.currentTimeMillis() - time));
                addGrowingCourse(destination);
                done(this);
            } else {
                fail();
            }
        }

        boolean tryMerge(GrowingCourse destination, GrowingCourse other) {
            if (!destination.merge(other)) {
                return false;
            }
            return true;
        }
    }
}
