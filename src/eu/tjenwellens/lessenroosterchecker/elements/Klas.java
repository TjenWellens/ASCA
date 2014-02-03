package eu.tjenwellens.lessenroosterchecker.elements;

import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Map;
import java.util.Set;

/**
 *
 * @author Tjen
 */
public class Klas implements Collection<Les> {
    private String faculteit;
    private String richting;
    private String klasNaam;
    private Map<String, Vak> courseName_course = new HashMap<>();

    public Klas(String naam) {
        String[] arr = naam.split("/", 3);
        if (arr.length < 3) {
            klasNaam = naam;
        } else {
            faculteit = arr[0];
            richting = arr[1];
            klasNaam = arr[2];
        }
    }

    public Klas(Klas source) {
        this.faculteit = source.faculteit;
        this.richting = source.richting;
        this.klasNaam = source.klasNaam;
        for (Map.Entry<String, Vak> entry : source.courseName_course.entrySet()) {
            this.courseName_course.put(entry.getKey(), new Vak(entry.getValue()));
        }
    }

    @Override
    public boolean add(Les les) {
        String courseName = les.getCursusNaam();
        Vak course = courseName_course.get(courseName);
        if (course == null) {
            course = new Vak(courseName);
            courseName_course.put(courseName, course);
        }
        return course.add(les);
    }

    @Override
    public boolean addAll(Collection<? extends Les> lessen) {
        boolean succes = false;
        for (Les les : lessen) {
            succes = add(les) || succes;
        }
        return succes;
    }

    public Vak getCourse(String courseName) {
        return courseName_course.get(courseName);
    }

    public Collection<Vak> getAllCourses() {
        return courseName_course.values();
    }

    public Set<String> getCourseNames() {
        return courseName_course.keySet();
    }

    @Override
    public int size() {
        int size = 0;
        for (Vak course : courseName_course.values()) {
            size += course.size();
        }
        return size;
    }

    @Override
    public boolean isEmpty() {
        boolean empty = courseName_course.isEmpty();
        for (Vak course : courseName_course.values()) {
            if (empty) {
                return empty;
            }
            empty = course.isEmpty();
        }
        return empty;
    }

    public boolean containsCourse(String courseName) {
        return courseName_course.containsKey(courseName);
    }

    @Override
    public boolean contains(Object o) {
        boolean contains = false;
        for (Vak course : courseName_course.values()) {
            if (contains) {
                return contains;
            }
            contains = course.contains(o);
        }
        return contains;
    }

    private Collection<Les> getAllLessen() {
        Collection<Les> lessen = new LinkedList<>();
        for (Vak vak : courseName_course.values()) {
            lessen.addAll(vak);
        }
        return lessen;
    }

    @Override
    public Iterator<Les> iterator() {
        return getAllLessen().iterator();
    }

    @Override
    public Object[] toArray() {
        return getAllLessen().toArray();
    }

    @Override
    public <T> T[] toArray(T[] a) {
        return getAllLessen().toArray(a);
    }

    @Override
    public boolean remove(Object o) {
        boolean remove = false;
        for (Vak vak : courseName_course.values()) {
            while (vak.remove(o)) {
                remove = true;
            }
        }
        return remove;
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        return getAllLessen().containsAll(c);
    }

    @Override
    public boolean removeAll(Collection<?> c) {

        boolean remove = false;
        for (Object course : c) {
            remove = remove(course) || remove;
        }
        if (remove) {
            removeEmptyCourses();
        }
        return remove;
    }

    private void removeEmptyCourses() {
        LinkedList<String> remove = new LinkedList<>();
        for (Map.Entry<String, Vak> entry : courseName_course.entrySet()) {
            if (entry.getValue().isEmpty()) {
                remove.add(entry.getKey());
            }
        }
        for (String key : remove) {
            courseName_course.remove(key);
        }
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        throw new UnsupportedOperationException("Not yet implemented");
//        return lessen.retainAll(c);
    }

    @Override
    public void clear() {
        courseName_course.clear();
    }
}