import util.Translit;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;
import java.util.Locale;
import java.util.Properties;

public class Main {

    public static void main(String[] args) throws IOException {
        Aggregator aggregator = new Aggregator();
        List<Movie> list = aggregator.getMovies();
        list.forEach(System.out::println);
        System.out.println("---------------------------------");
        System.out.println(list.size());
    }
}
