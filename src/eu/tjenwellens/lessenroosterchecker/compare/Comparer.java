package eu.tjenwellens.lessenroosterchecker.compare;

import eu.tjenwellens.lessenroosterchecker.LessenroosterChecker;
import eu.tjenwellens.lessenroosterchecker.elements.Klas;
import eu.tjenwellens.lessenroosterchecker.elements.TimeStamp;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

/**
 *
 * @author Tjen
 */
public class Comparer {
    private Collection<Jaar> jaren = new LinkedList<>();
    private Waiter w;

    public Comparer() {
        w = new Waiter(this);
        jaren.add(new UGent(w));
        jaren.add(new HoGent1(w));
        jaren.add(new HoGent2(w));
        w.setHolders(jaren);
    }

    public void start() {
        w.start();
    }

    public void doneWaiting() {
        printSelectedVakken();
        printJaarList(jaren);
        System.out.println("\n========================\n");
        Klas[][] perm = generateKlassenPermutatie(jaren);
        printKlasArray(perm);
        System.out.println("\n========================\n");
        Collection<Collection<Klas>> succes = checkPermutationClash(perm);
        if (succes.isEmpty()) {
            System.out.println("Geen mogelijke oplossingen gevonden");
            System.exit(0);
        }
        System.out.println("Aantal oplossingen: " + succes.size());
        int teller = 1;
        for (Collection<Klas> klassenMatch : succes) {
            System.out.println("Oplossing " + teller + ": ");
            for (Klas klas : klassenMatch) {
                System.out.print(klas.getKlasNaam());
                System.out.print('\t');
            }
            System.out.println();
            teller++;
        }
//        System.out.println("First Klasses match?" + testFirstKlassen());
        System.exit(0);
    }

    private void printSelectedVakken() {
        Collection<String> totalSelection = new LinkedList();
        for (Jaar jaar : jaren) {
            totalSelection.addAll(LessenroosterChecker.extractVakken(jaar.getKlassen()));
        }
        for (String vakNaam : totalSelection) {
            System.out.println(vakNaam);
        }
    }

    private boolean testFirstKlassen() {
        Collection<Collection<TimeStamp>> bundles = new LinkedList<>();
        for (Jaar jaar : jaren) {
            Collection<Klas> klassen = jaar.getKlassen();
            for (Klas klas : klassen) {
                bundles.add(klas.getAllTimeStamps());
                LessenroosterChecker.printEntry(klas, -1);
                break;
            }
        }

        Set<TimeStamp> check = new HashSet<>();
        for (Collection<TimeStamp> collection : bundles) {
            for (TimeStamp timeStamp : collection) {
                if (!check.add(timeStamp)) {
                    Collection<TimeStamp> c = new LinkedList<>();
                    c.add(timeStamp);
                    check.retainAll(c);
                    return false;
                }
            }
        }
        return true;
    }

    static Collection<Collection<Klas>> checkPermutationClash(Klas[][] perm) {
        Collection<Collection<Klas>> ok = new LinkedList<>();
        for (int klasPos = 0; klasPos < perm[0].length; klasPos++) {
            Collection<Klas> check = new LinkedList<>();
            for (int jaarPos = 0; jaarPos < perm.length; jaarPos++) {
                check.add(perm[jaarPos][klasPos]);
            }
            if (check(check)) {
                ok.add(check);
            }
        }
        return ok;
    }

    static boolean check(Collection<Klas> klassen) {
        Set<TimeStamp> check = new HashSet<>();
        for (Klas klas : klassen) {
            for (TimeStamp timeStamp : klas.getAllTimeStamps()) {
                if (!check.add(timeStamp)) {
                    return false;
                }
            }
        }
        return true;
    }

    static Klas[][] generateKlassenPermutatie(Collection<Jaar> startJaren) {
        List<Jaar> jaren = new ArrayList<>(startJaren);
        // remove empty years
        List<Jaar> removeJaren = new LinkedList();
        for (Jaar jaar : jaren) {
            if (jaar.getKlassen().isEmpty()) {
                removeJaren.add(jaar);
            }
        }
        if (!removeJaren.isEmpty()) {
            jaren.removeAll(removeJaren);
        }
        // do permutation
        int totalKlassenMultiplier = 1;
        for (Jaar jaar : jaren) {
            totalKlassenMultiplier *= jaar.getKlassen().size();
        }
        Klas[][] klassenPermutatie = new Klas[jaren.size()][totalKlassenMultiplier];
        for (int jaarPos = 0; jaarPos < jaren.size(); jaarPos++) {
            List<Klas> klassen = new ArrayList<>(jaren.get(jaarPos).getKlassen());
            int size = klassen.size();
            int multiplySize = 1;
            for (int klasPos = 0; klasPos < totalKlassenMultiplier; klasPos++) {
                klassenPermutatie[jaarPos][klasPos] = klassen.get((klasPos / multiplySize) % size);
//                klassenPermutatie[jaarPos][klasPos] = klassen.get(klasPos);
            }
            multiplySize *= size;
        }
        return klassenPermutatie;
    }

    static Integer[][] testIntArr(List<List<Integer>> jaren) {
//        List<List<Integer>> jaren = new ArrayList<>();//this.jaren
        int totalKlassenMultiplier = 1;
        for (List<Integer> jaar : jaren) {
            totalKlassenMultiplier *= jaar.size();
        }
        Integer[][] klassenPermutatie = new Integer[jaren.size()][totalKlassenMultiplier];
        for (int jaarPos = 0; jaarPos < jaren.size(); jaarPos++) {
            List<Integer> klassen = new ArrayList<>(jaren.get(jaarPos));
            int size = klassen.size();
            int multiplySize = 1;
            for (int klasPos = 0; klasPos < totalKlassenMultiplier; klasPos++) {
                int toWrite = klassen.get((klasPos / multiplySize) % size);
                klassenPermutatie[jaarPos][klasPos] = toWrite;
//                klassenPermutatie[jaarPos][klasPos] = klassen.get(klasPos);
            }
            multiplySize *= size;
        }
        return klassenPermutatie;
    }

    public static void main(String[] args) {
        new Comparer().start();
    }

    public static void testIntegers() {
        List<List<Integer>> jaren = new ArrayList<>();
        List<Integer> ugent = new ArrayList<>();
//        ugent.add(0);
        ugent.add(1);
        ugent.add(2);
        ugent.add(3);

        List<Integer> hogent1 = new ArrayList<>();
//        hogent1.add(0);
        hogent1.add(1);
        hogent1.add(2);

        List<Integer> hogent2 = new ArrayList<>();
//        hogent2.add(0);
        hogent2.add(1);

        if (!ugent.isEmpty()) {
            jaren.add(ugent);
        }
        if (!hogent1.isEmpty()) {
            jaren.add(hogent1);
        }
        if (!hogent2.isEmpty()) {
            jaren.add(hogent2);
        }
        printIntList(jaren);
        System.out.println("\n========================\n");
        Integer[][] klassenPerm = testIntArr(jaren);
        printIntArray(klassenPerm);
    }

    static void printIntList(Collection<List<Integer>> jaren) {
        for (List<Integer> list : jaren) {
            for (Integer integer : list) {
                System.out.print(integer);
                System.out.print(" ");
            }
            System.out.println();
        }
    }

    static void printIntArray(Integer[][] klassenPerm) {
        for (int jaar = 0; jaar < klassenPerm.length; jaar++) {
            Integer[] integers = klassenPerm[jaar];
            for (int klas = 0; klas < integers.length; klas++) {
                Integer integer = integers[klas];
                System.out.print(integer);
                System.out.print(" ");
            }
            System.out.println();
        }
    }

    static void printKlasArray(Klas[][] klassenPerm) {
        for (int jaar = 0; jaar < klassenPerm.length; jaar++) {
            Klas[] integers = klassenPerm[jaar];
            for (int klas = 0; klas < integers.length; klas++) {
                Klas integer = integers[klas];
                System.out.print(integer.getKlasNaam());
                System.out.print(" ");
            }
            System.out.println();
        }
    }

    static void printJaarList(Collection<Jaar> jaren) {
        for (Jaar jaar : jaren) {
            for (Klas klas : jaar.getKlassen()) {
                System.out.print(klas.getKlasNaam());
                System.out.print(" ");
            }
            System.out.println();
        }
    }
}
