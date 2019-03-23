import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;


public class Main {
    public static void main(String[] args) throws FileNotFoundException {
        Scanner scannerPytan = new Scanner(new File("src\\bazaWiedzy\\pytania.txt"));
        Scanner scannerRegul = new Scanner(new File("src\\bazaWiedzy\\reguly.txt"));
        Scanner scannerWnioskow = new Scanner(new File("src\\bazaWiedzy\\wnioski.txt"));

        List<Regula> reguly = new LinkedList<>();

        while(scannerRegul.hasNextLine()){
            String liniaReguly = scannerRegul.nextLine();
            String[] x = liniaReguly.split("->");
            String przeslanki = x[0];
            String wniosek = x[1];
            List<String> listaPrzeslanek = Arrays.asList(przeslanki.split(","));
            reguly.add(new Regula(listaPrzeslanek,wniosek));



        }


        Map<String,Integer> mapaWartosciPrzeslanki = new HashMap<>();
        Map<String, String> mapaPytania = new HashMap<>();

        while(scannerPytan.hasNextLine()){
            String pytanie = scannerPytan.nextLine();
            String[] x =pytanie.split("-");
            mapaPytania.put(x[0],x[1]); //opis pytania
            mapaWartosciPrzeslanki.put(x[0], -1); //nie wiadomo czy cos jest spelnione


        }
        Map<String, String> mapaWnioski = new HashMap<>();
        Map<String,Integer> mapaWartosciWnioski = new HashMap<>();

        while(scannerWnioskow.hasNextLine()){
            String wniosek=scannerWnioskow.nextLine();
            String[] x=wniosek.split("-");
            mapaWnioski.put(x[0], x[1]); //opis wniosku
            mapaWartosciWnioski.put(x[0], -1); //nie wiadomo czy cos jest spelnione

        }

        Scanner scannerOdpowiedzi = new Scanner(System.in);
        while(mapaWartosciWnioski.containsValue(-1)) {
            for (Regula r : reguly) {
                if (mapaWartosciWnioski.get(r.getWniosek()) != -1) {
                    continue;
                }
                int prawda = 1;
                for (String przeslanka : r.getPrzeslanki()) {
                    String przeslanka2 = przeslanka;
                    if (przeslanka.contains("!")) {
                        przeslanka2 = przeslanka.replace("!", "");
                    }
                    //System.out.println(przeslanka2);
                    if(mapaWartosciPrzeslanki.containsKey(przeslanka2)) {
                        if (mapaWartosciPrzeslanki.get(przeslanka2) == -1) {
                            System.out.println("Czy " + mapaPytania.get(przeslanka2) + "?");
                            String odp = scannerOdpowiedzi.nextLine();
                            if (odp.equals("tak")) {
                                mapaWartosciPrzeslanki.replace(przeslanka2, 1);

                            } else {
                                mapaWartosciPrzeslanki.replace(przeslanka2, 0);

                            }
                        }
                    }

                    if (mapaWartosciPrzeslanki.containsKey(przeslanka2)) {
                        if(mapaWartosciPrzeslanki.get(przeslanka2) == 0 && przeslanka2.equals(przeslanka)||
                        mapaWartosciPrzeslanki.get(przeslanka2)==1 && !przeslanka2.equals(przeslanka)) {
                            prawda = 0;
                            break;
                        }
                    }else if (mapaWartosciWnioski.containsKey(przeslanka2) ){
                        if(mapaWartosciWnioski.get(przeslanka2)==0 && przeslanka2.equals(przeslanka)||
                        mapaWartosciWnioski.get(przeslanka2)==1 && !przeslanka2.equals(przeslanka)) {

                            prawda = 0;
                            break;
                        }
                    }else{ //czyli kiedy trafilismy na duza litere ktora nie ma ustalonej wartosci
                        prawda=-1;
                    }

                }
                if (prawda == 1) {
                    mapaWartosciWnioski.replace(r.getWniosek(), 1);
                } else if(prawda == 0){
                    mapaWartosciWnioski.replace(r.getWniosek(), 0);
                }

            }
        }

        for(String wniosek : mapaWnioski.keySet()){
            if(mapaWartosciWnioski.get(wniosek) == 1 && wniosek.length()==2){
                System.out.println("Idz do "+mapaWnioski.get(wniosek));
            }
        }




        //System.out.println(mapaPytania);
        //System.out.println(mapaWartosciPrzeslanki);

    }
}
