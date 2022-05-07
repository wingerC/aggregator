import org.apache.commons.io.FileUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import util.Translit;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.*;

public class Aggregator {

    // URL = HOST + $page + CATEGORY_URL;
    private final String URL_HOST;
    private final String URL_HOST_BROWSE;
    private final String URL_TOP_PEERS;
    private final Properties imageLibrary;

    public Aggregator() {
        imageLibrary = new Properties();
        Properties prop = new Properties();
        try {
            String LIBRARY = "src/main/resources/images.properties";
            String PROPERTIES = "src/main/resources/settings.properties";
            imageLibrary.load(new FileReader(LIBRARY));
            prop.load(new FileReader(PROPERTIES));
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        URL_HOST = prop.getProperty("URL_HOST");
        URL_HOST_BROWSE = prop.getProperty("URL_HOST_BROWSE");
        URL_TOP_PEERS = prop.getProperty("URL_TOP_PEERS");
    }

    public List<Movie> getMovies() throws IOException {
        Elements elements = parseHtmlPage("", 0);
        List<Movie> resList = new ArrayList<>();
         /*es.forEach(e-> {
         try {
         resList.add(getElementInfo(e));
         } catch (IOException ex) {
         ex.printStackTrace();
         }
         });*/
        for (int i = 0; i < 20; i++) {
            resList.add(parseMovie(elements.get(i)));
        }

        try {
            imageLibrary.store(new FileWriter("src/main/resources/images.properties"), "");
        }catch (IOException ex){
            System.out.println(ex.getMessage());
        }
        return resList;

    }

    public Elements parseHtmlPage(String cat, int page) throws IOException {
        Document document = Jsoup.parse(new URL(URL_HOST_BROWSE + page + URL_TOP_PEERS), 5000);
        return document.select("#index table tbody tr:not(.backgr)");
    }

    public Movie parseMovie(Element e) throws IOException {
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
        String poster = getPosterLink(enTitle, urlPage);

        return new Movie(urlId, date, year, title, ruTitle, enTitle, size, peers, torrentLink, urlPage, poster);


    }

    private String getPosterLink(String enTitle, String urlPage) throws IOException {
        String imgKey = enTitle.replaceAll(" ", "-").replaceAll(":", "")
                .toLowerCase().trim();

        if(imageLibrary.containsKey(imgKey)){
            System.out.println("Already exists --- " + enTitle);
            return imageLibrary.getProperty(imgKey);
        }

        String USER_AGENT = "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/56.0.2924.87 Safari/537.36";
        Document doc = Jsoup.parse(new URL(urlPage), 5000);
        String imgLink = doc.select("#details tbody tr:first-child td:nth-child(2) img:not(a img)")
                .attr("src");
        String fileName = "src/main/resources/img/" + imgKey + ".jpg";
        HttpURLConnection con = (HttpURLConnection) new URL(imgLink).openConnection();
        con.setRequestProperty("User-Agent", USER_AGENT);
        con.setRequestProperty("Accept", "application/image");
        File file = new File(fileName);
        FileUtils.copyInputStreamToFile(
                con.getInputStream(),
                file
        );

        if (file.exists() && file.length() > 1000) {
                imageLibrary.put(imgKey, fileName);
                System.out.println("Downloaded --- " + enTitle + " ---- " + file.length());
                return fileName;
        }

        return "src/main/resources/img/no_img.jpg";
    }
}

