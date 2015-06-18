package eu.tjenwellens.lessenroosterchecker.compare;

import eu.tjenwellens.lessenroosterchecker.LessenroosterChecker;
import eu.tjenwellens.lessenroosterchecker.elements.Klas;
import eu.tjenwellens.lessenroosterchecker.elements.TimeStamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import javax.swing.JFrame;

/**
 *
 * @author Tjen
 */
public class KlasComparer implements WaiterWaiter {
    protected boolean chatty = true;
    protected Collection<Jaar> jaren = new LinkedList<>();
    private Waiter w;

    public KlasComparer() {
        w = new Waiter(this);
        //jaren.add(new UGent1(w));
        //jaren.add(new UGent2(w));
        jaren.add(new HoGent1(w));
        jaren.add(new HoGent2(w));
        w.setHolders(jaren);
    }

    public void start() {
        w.start();
    }

    @Override
    public void doneWaiting() {
        if (chatty) {
            printSelectedVakken();
            printJaarList(jaren);
            System.out.println("\n========================\n");
        }
        Klas[][] perm = generateKlassenPermutatie(jaren);
        if (chatty) {
            printKlasArray(perm);
            System.out.println("\n========================\n");
        }
        Collection<Collection<Klas>> succes = checkPermutationClash(perm);
        if (succes.isEmpty()) {
            System.out.println("Geen mogelijke oplossingen gevonden");
            System.exit(0);
        }
        if (chatty) {
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
        }
        succes(succes);
//        System.out.println("First Klasses match?" + testFirstKlassen());
        exitFrame();
    }

    protected void succes(Collection<Collection<Klas>> succes) {
    }

    protected void printSelectedVakken() {
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
        int multiplySize = 1;
        for (int jaarPos = 0; jaarPos < jaren.size(); jaarPos++) {
            List<Klas> klassen = new ArrayList<>(jaren.get(jaarPos).getKlassen());
            int size = klassen.size();
            for (int klasPos = 0; klasPos < totalKlassenMultiplier; klasPos++) {
                klassenPermutatie[jaarPos][klasPos] = klassen.get((klasPos / multiplySize) % size);
//                klassenPermutatie[jaarPos][klasPos] = klassen.get(klasPos);
            }
            multiplySize *= size;
        }
        return klassenPermutatie;
    }

    static Object[][] testIntArr(List<List<? extends Object>> jaren) {
//        List<List<Object>> jaren = new ArrayList<>();//this.jaren
        int totalKlassenMultiplier = 1;
        for (List<? extends Object> jaar : jaren) {
            totalKlassenMultiplier *= jaar.size();
        }
        Object[][] klassenPermutatie = new Object[jaren.size()][totalKlassenMultiplier];
        int multiplySize = 1;
        for (int jaarPos = 0; jaarPos < jaren.size(); jaarPos++) {
            List<Object> klassen = new ArrayList<>(jaren.get(jaarPos));
            int size = klassen.size();
            for (int klasPos = 0; klasPos < totalKlassenMultiplier; klasPos++) {
                Object toWrite = klassen.get((klasPos / multiplySize) % size);
                klassenPermutatie[jaarPos][klasPos] = toWrite;
//                klassenPermutatie[jaarPos][klasPos] = klassen.get(klasPos);
            }
            multiplySize *= size;
        }
        return klassenPermutatie;
    }

    public static void main(String[] args) {
        new KlasComparer().start();
//        testIntegers();
    }

    public static void testIntegers() {
        List<List<? extends Object>> jaren = new ArrayList<>();
        List<Object> ugent = new ArrayList<>();

//        ugent.add("BI2");
//        String ho1 = "1A1 1A2 1A3 1B1 1B2 1B3 1C1 1C2 1C3 1D1 1D2 1D3 1E1 1E2 1E3 1F1 1F2 1F3";
//        String ho2 = "2A1 2A2 2B1 2B2 2C1 2C2";
//        List<? extends Object> hogent1 = Arrays.asList(ho1.split(" "));
//        List<? extends Object> hogent2 = Arrays.asList(ho2.split(" "));

        ugent.add(0);
        ugent.add(2);
        ugent.add(3);

        List<Object> hogent1 = new ArrayList<>();
        hogent1.add(0);
        hogent1.add(1);
        hogent1.add(2);

        List<Object> hogent2 = new ArrayList<>();
        hogent2.add(0);
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
        Object[][] klassenPerm = testIntArr(jaren);
        printIntArray(klassenPerm);
    }

    static void printIntList(Collection<List<? extends Object>> jaren) {
        for (List<? extends Object> list : jaren) {
            for (Object integer : list) {
                System.out.print(integer);
                System.out.print(" ");
            }
            System.out.println();
        }
    }

    static void printIntArray(Object[][] klassenPerm) {
        for (int jaar = 0; jaar < klassenPerm.length; jaar++) {
            Object[] integers = klassenPerm[jaar];
            for (int klas = 0; klas < integers.length; klas++) {
                Object integer = integers[klas];
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

    protected static void printJaarList(Collection<Jaar> jaren) {
        for (Jaar jaar : jaren) {
            for (Klas klas : jaar.getKlassen()) {
                System.out.print(klas.getKlasNaam());
                System.out.print(" ");
            }
            System.out.println();
        }
    }

    private void exitFrame() {
        JFrame f = new JFrame("Exit?");
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.pack();
        f.setVisible(true);
    }
}
