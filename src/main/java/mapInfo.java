import java.util.List;

public class mapInfo {
    public List<String> destination_address;
    public List<String> origin_address;
    public List<Row> rows;
    public String status;

    public class Row{
        public List<Element> elements;
    }

    public class Element{
        public Distance distance;
        public Duration duration;
        public String status;
    }

    public class Distance{
        public String text;
        public int value;
    }
    public class Duration{
        public String text;
        public int value;
    }

}
