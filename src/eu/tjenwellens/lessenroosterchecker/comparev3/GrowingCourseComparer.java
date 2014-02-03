package eu.tjenwellens.lessenroosterchecker.comparev3;

import eu.tjenwellens.lessenroosterchecker.compare.Jaar;
import eu.tjenwellens.lessenroosterchecker.comparev2.Course;
import eu.tjenwellens.lessenroosterchecker.comparev2.CourseComparer;
import eu.tjenwellens.lessenroosterchecker.comparev2.Courses;
import eu.tjenwellens.lessenroosterchecker.elements.Klas;
import eu.tjenwellens.lessenroosterchecker.elements.Vak;
import java.util.Collection;

/**
 *
 * @author Tjen
 */
public class GrowingCourseComparer extends CourseComparer {
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
        GrowingCourse gc = new GrowingCourse();
        for (Course course : courses.getAllCourses()) {
            course.fix();
            if (!gc.merge(course.getAllVakken())) {
                break;
            }
        }
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

    public static void main(String[] args) {
        new GrowingCourseComparer().start();
    }
}
