public class Movie {

    private String urlId;
    private String date;
    private String year;
    private String title;
    private String ruTitle;
    private String enTitle;
    private String size;
    private String peers;
    private String torrentLink;
    private String urlPage;
    private String poster = "";

    public Movie(String urlId, String date, String year, String title, String ruTitle, String enTitle,
                 String size, String peers, String torrentLink, String urlPage, String poster) {
        this.urlId = urlId;
        this.date = date;
        this.year = year;
        this.title = title;
        this.ruTitle = ruTitle;
        this.enTitle = enTitle;
        this.size = size;
        this.peers = peers;
        this.torrentLink = torrentLink;
        this.urlPage = urlPage;
        this.poster = poster;
    }

    public Movie(String urlId, String date, String year, String title, String ruTitle, String enTitle,
                 String size, String peers, String torrentLink, String urlPage) {
        this.urlId = urlId;
        this.date = date;
        this.year = year;
        this.title = title;
        this.ruTitle = ruTitle;
        this.enTitle = enTitle;
        this.size = size;
        this.peers = peers;
        this.torrentLink = torrentLink;
        this.urlPage = urlPage;
    }

    public Movie() {}

    public String getUrlId() {
        return urlId;
    }

    public void setUrlId(String urlId) {
        this.urlId = urlId;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getRuTitle() {
        return ruTitle;
    }

    public void setRuTitle(String ruTitle) {
        this.ruTitle = ruTitle;
    }

    public String getEnTitle() {
        return enTitle;
    }

    public void setEnTitle(String enTitle) {
        this.enTitle = enTitle;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getPeers() {
        return peers;
    }

    public void setPeers(String peers) {
        this.peers = peers;
    }

    public String getTorrentLink() {
        return torrentLink;
    }

    public void setTorrentLink(String torrentLink) {
        this.torrentLink = torrentLink;
    }

    public String getUrlPage() {
        return urlPage;
    }

    public void setUrlPage(String urlPage) {
        this.urlPage = urlPage;
    }

    public String getPoster() {
        return poster;
    }

    public void setPoster(String poster) {
        this.poster = poster;
    }

    @Override
    public String toString() {
        return "Movie{" +
                "urlId='" + urlId + '\'' +
                ", date='" + date + '\'' +
                ", year='" + year + '\'' +
                ", title='" + title + '\'' +
                ", ruTitle='" + ruTitle + '\'' +
                ", enTitle='" + enTitle + '\'' +
                ", size='" + size + '\'' +
                ", peers='" + peers + '\'' +
                ", torrentLink='" + torrentLink + '\'' +
                ", urlPage='" + urlPage + '\'' +
                ", poster='" + poster + '\'' +
                '}';
    }
}