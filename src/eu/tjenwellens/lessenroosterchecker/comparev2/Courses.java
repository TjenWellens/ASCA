package eu.tjenwellens.lessenroosterchecker.comparev2;

import eu.tjenwellens.lessenroosterchecker.elements.Les;
import eu.tjenwellens.lessenroosterchecker.elements.TimeStamp;
import eu.tjenwellens.lessenroosterchecker.elements.Vak;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 *
 * @author Tjen
 */
public class Courses {
    private static final int MAX_TELLER = -1;
    Map<String, Course> courseName_course = new HashMap<>();

    public Collection<Collection<Vak>> getMatchingCourses() {
        Collection<Collection<Vak>> ok = new LinkedList<>();
        // tel each course to start clean
        for (Course course : courseName_course.values()) {
            course.fix();
        }
        List<Course> courses = new ArrayList<>(courseName_course.values());
        int max = 1;
        for (Course course : courses) {
            max *= course.size();
        }
        System.out.println("Number of iterations (aka permutation size): " + max);
        boolean done = false;
        int teller = 0;
        long iterationTime;
        int percentage = 0;
        while (!done) {
            int newPercentage = (teller * 100) / max;
            if (newPercentage > percentage) {
                percentage = newPercentage;
                System.out.println("Percentage done: " + percentage + "%");
            }
            iterationTime = System.currentTimeMillis();
            Collection<Vak> vakken = new LinkedList<>();
            boolean next = true;
            for (Course course : courses) {
                vakken.add(course.getVak());
                if (next) {
                    next = course.next();
                }
            }
            done = next;
            if (check(vakken)) {
                ok.add(vakken);
            }
            teller++;
            iterationTime = System.currentTimeMillis() - iterationTime;
            if (MAX_TELLER > 0 && teller > MAX_TELLER) {
                System.err.println("Broken iteration, because " + max + " is more than maximum(" + MAX_TELLER + ") iterations.");
                System.out.println("Time per loop in millis: " + iterationTime);
                System.out.println("In other words it would have taken " + new Date(iterationTime));
                break;
            }
        }
        return ok;
    }

    public boolean addAll(Collection<Les> lessen) {
        boolean succes = false;
        for (Les les : lessen) {
            succes = add(les) || succes;
        }
        return true;
    }

    public boolean add(Les les) {
        Course course = courseName_course.get(les.getCursusNaam());
        if (course == null) {
            course = new Course(les.getCursusNaam());
            courseName_course.put(les.getCursusNaam(), course);
        }
        return course.addLes(les);
    }

    private boolean check(Collection<Vak> vakken) {
        Set<TimeStamp> check = new HashSet<>();
        for (Vak vak : vakken) {
            for (TimeStamp timeStamp : vak.getAllTimeStamps()) {
                if (!check.add(timeStamp)) {
                    return false;
                }
            }
        }
        return true;
    }
}
