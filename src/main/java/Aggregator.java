import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import util.Translit;

import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.util.*;

public class Aggregator {

    // URL = HOST + $page + CATEGORY_URL;
    private final String URL_HOST;
    private final String URL_HOST_BROWSE;
    private final String URL_TOP_PEERS;


    public Aggregator() {
        Properties prop = new Properties();
        try {
            prop.load(new FileReader("src/main/resources/settings.properties"));
        } catch (IOException e) {
            e.getMessage();
        }
        URL_HOST = prop.getProperty("URL_HOST");
        URL_HOST_BROWSE = prop.getProperty("URL_HOST_BROWSE");
        URL_TOP_PEERS = prop.getProperty("URL_TOP_PEERS");
    }

    public Elements getElements(String cat, int page) throws IOException {
        List<String> list = new ArrayList<>();
        Document document = Jsoup.parse(new URL(URL_HOST_BROWSE + page + URL_TOP_PEERS), 5000);
        return document.select("#index table tbody tr:not(.backgr)");
    }

    public Movie getElementInfo(Element e) throws IOException {
        String date = e.select("td:first-child").text();
        String title = e.select("td:nth-child(2) a:last-child").text();
        String year = "";
        try {
            year = title.substring(title.indexOf("(") + 1, title.indexOf(")"));
        }catch (StringIndexOutOfBoundsException ex){
            year = "2020";
        }
        String ruTitle = "";
        try {
            ruTitle = title.substring(0, title.indexOf("/")).trim();
        }catch (IndexOutOfBoundsException ex){
            ruTitle = title.substring(0, title.indexOf("(")).trim();
        }
        String enTitle = "";
        try{
            if (title.contains("/")){
                enTitle = title.substring(title.lastIndexOf("/") +1, title.indexOf("(")).trim();
            } else {
                enTitle = Translit.getInstance().translate(ruTitle.toLowerCase());
            }
        }catch (IndexOutOfBoundsException ex){
            enTitle = Translit.getInstance().translate(ruTitle.toLowerCase());
        }
        String torrentLink = e.select("td:nth-child(2) a:nth-child(2)").attr("href");
        String size = e.select("td:last-child").prev().text();
        String peers = e.select("td:last-child .green").text();
        String urlPage = URL_HOST + e.select("td:nth-child(2) a:last-child").attr("href");
        String urlId = urlPage.substring(urlPage.indexOf("torrent/") + 8, urlPage.lastIndexOf("/"));

        Movie movie = new Movie(urlId, date, year, title, ruTitle, enTitle, size, peers, torrentLink, urlPage);

        return movie;
    }

    public List<Movie> getMovies() throws IOException {
        Elements es = getElements("", 0);
        List<Movie> resList = new ArrayList<>();
         es.forEach(e-> {
         try {
         resList.add(getElementInfo(e));
         } catch (IOException ex) {
         ex.printStackTrace();
         }
         });
        /*for (int i = 0; i < 20; i++) {
            resList.add(getElementInfo(es.get(i)));
        }*/
        return resList;
    }

    private String getPosterLink(String urlPage) throws IOException {
        Document doc = Jsoup.parse(new URL(urlPage), 5000);
        return doc.select("#details tbody tr:first-child td:nth-child(2) img:not(a img)")
                .attr("src");
    }
}
