import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

public class Main {

    private static final int MAX_DEPTH = 3;

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Inserisci l'URL iniziale: ");
        String startURL = scanner.nextLine();
        scanner.close();

        // Avvia il web crawler con DFS
        crawlLinks(new HashSet<>(), startURL, MAX_DEPTH, 0);
    }

    public static Set<String> crawlLinks(Set<String> visitedLinks, String currentURL, int maxDepth, int currentDepth) {
        // Aggiungi l'URL corrente al set di link visitati
        visitedLinks.add(currentURL);
        System.out.println("Profondità: " + currentDepth + " | URL: " + currentURL);

        // Procedi se non si è superata la profondità massima
        if (currentDepth < maxDepth) {
            try {
                // Recupera il contenuto della pagina
                Document document = Jsoup.connect(currentURL).get();
                Elements linksOnPage = document.select("a[href]");

                // Esplora ricorsivamente i link non ancora visitati
                for (Element link : linksOnPage) {
                    String nextURL = link.absUrl("href");
                    if (!visitedLinks.contains(nextURL)) {
                        crawlLinks(visitedLinks, nextURL, maxDepth, currentDepth + 1);
                    }
                }
            } catch (IOException e) {
                System.err.println("Errore nell'accesso a: " + currentURL + " | Messaggio: " + e.getMessage());
            }
        }
        return visitedLinks;
    }
}
