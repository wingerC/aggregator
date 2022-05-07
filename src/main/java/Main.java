import java.io.IOException;
import java.util.List;

public class Main {

    public static void main(String[] args) throws IOException {
        Aggregator aggregator = new Aggregator();
        aggregator.getMovies();
        /*List<Movie> list = aggregator.getMovies();
       *//* list.forEach(System.out::println);
        System.out.println("---------------------------------");
        System.out.println(list.size());*/
    }
}
