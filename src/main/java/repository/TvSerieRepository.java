package repository;

import model.Episode;
import model.TvSerie;

import java.util.ArrayList;

public interface TvSerieRepository {
    ArrayList<TvSerie> getTVSerier();

    TvSerie getTvSerie(String tvSerieId);

    ArrayList<Episode> getEpisoderISesong(String tvSerieTittel, int sesongNr);

    Episode getEpisode(String tvSerieTittel, int sesongNr, int episodeNr);

    public void opprettEnEpisode(String tittel, int sesongNr, int episodeNr);


    public void oppdaterEnEpisode(Episode episode);


    public void slettEnEpisode(String tvSerieTittel, int sesongNr, int episodeNr);


}
