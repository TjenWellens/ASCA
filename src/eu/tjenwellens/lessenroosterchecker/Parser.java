package eu.tjenwellens.lessenroosterchecker;

import eu.tjenwellens.lessenroosterchecker.elements.LesCreator;
import eu.tjenwellens.lessenroosterchecker.elements.Klas;
import eu.tjenwellens.lessenroosterchecker.elements.Les;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 *
 * @author Tjen
 */
public class Parser {
    /* 14
     0 <table class="header-border-args" border="0" cellspacing="0" width="100%">
     * 
     1 <p><span class="labelone">maandag</span></p>
     2 <table class="spreadsheet" cellspacing="0" cellpadding="2%" border="t">
     3 <p><span class="labelone">dinsdag</span></p>
     4 <table class="spreadsheet" cellspacing="0" cellpadding="2%" border="t">
     5 <p><span class="labelone">woensdag</span></p>
     6 <table class="spreadsheet" cellspacing="0" cellpadding="2%" border="t">
     7 <p><span class="labelone">donderdag</span></p>
     8 <table class="spreadsheet" cellspacing="0" cellpadding="2%" border="t">
     9 <p><span class="labelone">vrijdag</span></p>
     10 <table class="spreadsheet" cellspacing="0" cellpadding="2%" border="t">
     11 <p><span class="labelone">zaterdag</span></p>
     12 <table class="spreadsheet" cellspacing="0" cellpadding="2%" border="t">
     * 
     13 <table class="footer-border-args" border="0" cellspacing="0" width="100%">
     */
    public static List<Klas> getParseKlassen(Elements es) {
        List<Klas> klassen = new ArrayList<>();
        // per klas
        for (int klasTeller = 0; klasTeller + 13 < es.size(); klasTeller += 14) {
            String klasNaam = es.get(klasTeller).select("span.header-0-0-1").html();
            // per dag, 'header-border-args', <weekdag,entry>(size=6), 'footer-border-args'
            Klas klas = new Klas(klasNaam);
            for (int dagTeller = klasTeller + 1; dagTeller < klasTeller + 14 - 1; dagTeller += 2) {
                String dag = es.get(dagTeller).select("span.labelone").html();
                Element tbody = es.get(dagTeller + 1).select("tbody").first();
                if (tbody == null) {
                    // Dag zonder les (bvb elke zaterdag)
                    continue;
                }
                Elements tabel = tbody.children();
                klas.addAll(parseDagen(klasNaam, dag, tabel));
            }
            klassen.add(klas);
        }
        return klassen;
    }

    public static Collection<? extends Les> parseDagen(String klas, String dag, Elements tabel) {
        List<Les> vakEntries = new LinkedList<>();
        // per vak entry (aka row), sla de titelrij over
        for (int vakTeller = 1; vakTeller < tabel.size(); vakTeller++) {
            Elements lesPops = tabel.get(vakTeller).children();
            vakEntries.add(parseLes(klas, dag, lesPops));
        }
        return vakEntries;
    }

    public static Les parseLes(String klas, String dag, Elements lesPops) {
        LesCreator les = new LesCreator(klas, dag);
        // per property (aka column)
        for (int lesPropTeller = 0; lesPropTeller < lesPops.size(); lesPropTeller++) {
            switch (lesPropTeller) {
                case 0:// naam
                    les.setNaam(lesPops.get(lesPropTeller).html());
                    break;
                case 1:// details
                    les.setDetails(lesPops.get(lesPropTeller).html());
                    break;
                case 3:// weken
                    les.setWeken(lesPops.get(lesPropTeller).html());
                    break;
                case 4:// begin uur
                    les.setBeginUur(lesPops.get(lesPropTeller).html());
                    break;
                case 5:// eind uur
                    les.setEindUur(lesPops.get(lesPropTeller).html());
                    break;
                case 9:// lesvorm
                    les.setLesvorm(lesPops.get(lesPropTeller).html());
                    break;
            }
        }
        return les.createLes();
    }

    /*
     * Parse a Jsoup Document from an input stream.
     */
    public static Document parseStreamToHTML(InputStream is) throws IOException {
        StringBuilder sb;
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(is))) {
            sb = new StringBuilder();
            String line;
            try {
                while ((line = reader.readLine()) != null) {
                    sb.append(line).append(String.format("%n"));
                }
            } catch (IOException ex) {
                throw (ex);
            }
        }
        return Jsoup.parse(sb.toString());
    }

    public static Elements mergeBodyContents(Collection<String> paths) {
        Elements es = new Elements();
        for (String path : paths) {
            try {
                File f = new File(path);
                InputStream is = new FileInputStream(f);
                Document d = parseStreamToHTML(is);
                es.addAll(d.select("body").first().children());
            } catch (FileNotFoundException ex) {
                ex.printStackTrace();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        return es;
    }
}
