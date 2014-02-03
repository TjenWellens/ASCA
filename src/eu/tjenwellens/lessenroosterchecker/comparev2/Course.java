package eu.tjenwellens.lessenroosterchecker.comparev2;

import eu.tjenwellens.lessenroosterchecker.elements.Les;
import eu.tjenwellens.lessenroosterchecker.elements.Vak;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Tjen
 */
public class Course {
    boolean fixed = false;
    Map<String, Vak> klas_vak = new HashMap<>();
    List<Vak> vakPerKlas;
    String name;
    int index = -1;

    public int size() {
        return klas_vak.size();
    }

    public Course(String name) {
        this.name = name;
    }

    public Vak getVak() {
        if (fixed) {
            return vakPerKlas.get(index);
        } else {
            throw new CourseNotFixedException(this.name);
        }
    }

    public List<Vak> getAllVakken() {
        if (fixed) {
            return vakPerKlas;
        } else {
            throw new CourseNotFixedException(this.name);
        }
    }

    public boolean next() {
        if (fixed) {
            index = (index + 1) % vakPerKlas.size();
            return index == 0;
        } else {
            throw new CourseNotFixedException(this.name);
        }
    }

    public void fix() {
        fixed = true;
        index = 0;
        this.vakPerKlas = new ArrayList<>(klas_vak.values());
    }

    public boolean isFixed() {
        return fixed;
    }

    public boolean addLes(Les les) {
        fixed = false;
        if (!name.equals(les.getCursusNaam())) {
            return false;
        }
        Vak vak = klas_vak.get(les.getKlas());
        if (vak == null) {
            vak = new Vak(name, les.getKlas());
            klas_vak.put(les.getKlas(), vak);
        }
        return vak.add(les);
    }

    public String getName() {
        return name;
    }
}
