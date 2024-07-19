package repository;

import model.TvSerie;

import java.time.LocalDate;
import java.util.ArrayList;

public class WriteToFilesThread implements Runnable {

    private TvSerieJSONRepository tvSerieJSONRepository;
    private TvSerieCSVRepository tvSerieCSVRepository;




    public ArrayList<TvSerie> tves = new ArrayList<>();



    public WriteToFilesThread(TvSerieJSONRepository tvSerieJSONRepository, TvSerieCSVRepository tvSerieCSVRepository) {
        this.tvSerieJSONRepository = tvSerieJSONRepository;
        this.tvSerieCSVRepository = tvSerieCSVRepository;
    }

    @Override
    public void run() {
        tves.add(new TvSerie("Eksempel", "ddg", LocalDate.of(2002, 12, 29)));
        //tvSerieJSONRepository.writeToJson(tves, "src/tvshows_10.json");
        System.out.println(tves);
    }


    public static void main(String[] args) {
        TvSerieJSONRepository tvSerieJSONRepository = new TvSerieJSONRepository("");
        TvSerieCSVRepository tvSerieCSVRepository = new TvSerieCSVRepository("");

        WriteToFilesThread writeToFilesThread = new WriteToFilesThread(tvSerieJSONRepository, tvSerieCSVRepository);

        Thread thread1 = new Thread(writeToFilesThread);

        thread1.start();
    }



}
