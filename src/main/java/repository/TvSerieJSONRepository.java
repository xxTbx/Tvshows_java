package repository;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import model.Episode;
import model.TvSerie;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

public class TvSerieJSONRepository implements TvSerieRepository{


    private final ArrayList<TvSerie> tvSerier = new ArrayList<>();
    //private ArrayList<TvSerie> newEp = new ArrayList<>();



    public TvSerieJSONRepository(String filename) {
        File file = new File(filename);

        ArrayList<TvSerie> serierLestFraJson = readFromJson("src/tvshows_10.json");
        tvSerier.addAll(serierLestFraJson);

        //System.out.println(newEp);
        //serierLestFraJson.addAll(newEp);
        //tvSerier.addAll(newEp);

    }

    public static ArrayList<TvSerie> readFromJson(String filepath) {
        ArrayList<TvSerie> serierReturListe = new ArrayList<>();
        ObjectMapper objectMapper = new ObjectMapper();

        try {
            objectMapper.registerModule(new JavaTimeModule());
            TvSerie[] tvserieArray = objectMapper.readValue(new File(filepath), TvSerie[].class);

            serierReturListe.addAll(Arrays.asList(tvserieArray));

        } catch (IOException e) {
            e.printStackTrace();
        }
        return serierReturListe;
    }


    public void writeToJson(ArrayList<TvSerie> tvSerier, String filepath) {
        ObjectMapper objectMapper = new ObjectMapper();

        File file = new File(filepath);

        try {
            objectMapper.registerModule(new JavaTimeModule());
            objectMapper.writerWithDefaultPrettyPrinter().writeValue(file, tvSerier);


        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    @Override
    public ArrayList<TvSerie> getTVSerier() {
        return new ArrayList<>(tvSerier);
    }

    @Override
    public TvSerie getTvSerie(String tvSerieTittel) {
        for (TvSerie tvSerie: tvSerier) {
            if (tvSerie.getTittel().equals(tvSerieTittel))
                return tvSerie;
        }

        return null;
    }

    @Override
    public ArrayList<Episode> getEpisoderISesong(String tvSerieTittel, int sesongNr) {
        return getTvSerie(tvSerieTittel).hentEpisoderISesong(sesongNr);
    }

    @Override
    public Episode getEpisode(String tvSerieTittel, int sesongNr, int episodeNr) {
        return getTvSerie(tvSerieTittel).getEpisode(sesongNr, episodeNr);
    }

    @Override
    public void opprettEnEpisode(String tvSerietittel, int sesongNr, int episodeNr) {
        TvSerie tvSerie = getTvSerie(tvSerietittel);
        Episode episode = new Episode();
        tvSerie.leggTilEpisode(episode);
        //newEp.add(tvSerie);

        //writeToJson(newEp, "src/tvshows_10.json");
    }



    @Override
    public void oppdaterEnEpisode(Episode episode) {
        //TvSerie tvSerie = getTvSerie(tvSerietittel);
        //tvSerie.leggTilEpisode(episode);
        //newEp.add(tvSerie);

        //writeToJson(newEp, "src/tvshows_10.json");

    };

    @Override
    public void slettEnEpisode(String tvSerieTittel, int sesongNr, int episodeNr) {
        TvSerie tvSerie = getTvSerie(tvSerieTittel);
        Episode episode = tvSerie.getEpisode(sesongNr, episodeNr);


        tvSerier.remove(episode);


        readFromJson("src/tvshows_10.json");

    }

    //public ArrayList<TvSerie> getNewEp() {
        //return newEp;
    //}

}
