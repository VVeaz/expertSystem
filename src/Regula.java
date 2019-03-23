import java.util.List;

public class Regula {
    private List<String> przeslanki;
    private String wniosek;

    public Regula(List<String> przeslanki, String wniosek) {
        this.przeslanki = przeslanki;
        this.wniosek = wniosek;
    }

    public List<String> getPrzeslanki() {
        return przeslanki;
    }

    public String getWniosek() {
        return wniosek;
    }

    @Override
    public String toString() {
        return "Regula{" +
                "przeslanki=" + przeslanki +
                ", wniosek='" + wniosek + '\'' +
                '}';
    }
}
