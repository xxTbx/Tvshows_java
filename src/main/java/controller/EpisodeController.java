package controller;

import io.javalin.http.Context;
import model.Episode;
import model.Produksjon;
import model.TvSerie;
import repository.TvSerieJSONRepository;
import repository.TvSerieRepository;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class EpisodeController {
    private TvSerieRepository tvSerieRepository;

    public EpisodeController(TvSerieRepository tvSerieRepository) {
        this.tvSerieRepository = tvSerieRepository;
    }



    public void slettEnEpisode(Context context) {
        String tvSerieTittel = context.pathParam("tvserie-id");
        int sesongNr = Integer.parseInt(context.pathParam("sesong-nr"));
        int episodeNr = Integer.parseInt(context.pathParam("episode-nr"));



        TvSerie tvSerie = tvSerieRepository.getTvSerie(tvSerieTittel);
        Episode episode = tvSerie.getEpisode(sesongNr, episodeNr);

        ArrayList<Episode> episodes = tvSerieRepository.getEpisoderISesong(tvSerieTittel, sesongNr);
        episodes.remove(episode);



    }
    public void oppdaterEnEpisode(Context context) {
        String tvSerieTittel = context.pathParam("tvserie-id");
        int sesongNr = Integer.parseInt(context.pathParam("sesong-nr"));
        int episodeNr = Integer.parseInt(context.pathParam("episode-nr"));


        String tittel = context.formParam("tittel");
        String beskrivelse = context.formParam("beskrivelse");
        int epNr = Integer.parseInt(context.formParam("episodeNummer"));
        int sesong = Integer.parseInt(context.formParam("sesongNummer"));
        int spilletid = Integer.parseInt(context.formParam("spilletid"));
        LocalDate utgivelsesdato = LocalDate.parse(context.formParam("utgivelsesdato"));
        String url = context.formParam("bildeUrl");

        TvSerie tvserie = tvSerieRepository.getTvSerie(tvSerieTittel);
        Episode episoden = tvserie.getEpisode(episodeNr, sesongNr);

        episoden.setTittel(tittel);
        episoden.setBeskrivelse(beskrivelse);
        episoden.setEpisodeNummer(epNr);
        episoden.setSesongNummer(sesong);
        episoden.setSpilletid(spilletid);
        episoden.setUtgivelsesdato(utgivelsesdato);
        episoden.setBildeUrl(url);

        tvSerieRepository.oppdaterEnEpisode(episoden);
    }


    public void getEpisoderISesong(Context context) {
        String tvSerieTittel = context.pathParam("tvserie-id");
        String sesong = context.pathParam("sesong-nr");
        String sortering = context.queryParam("sortering");

        int sesongNr = sesong.isEmpty()? 1 : Integer.parseInt(sesong);

        ArrayList<Episode> episoder = tvSerieRepository.getEpisoderISesong(tvSerieTittel, sesongNr);

        if (sortering != null) {
            switch (sortering) {
                case "episodenr" -> Collections.sort(episoder);
                case "tittel" -> episoder.sort((e1, e2) -> e1.getTittel().compareTo(e2.getTittel()));
                case "spilletid" -> episoder.sort(Comparator.comparingInt(Produksjon::getSpilletid));
            }
        }

        context.json(episoder);
    }

    public void getEpisode(Context context) {
        String tvSerieTittel = context.pathParam("tvserie-id");
        String sesongNr = context.pathParam("sesong-nr");
        String episodeNr = context.pathParam("episode-nr");

        context.json(tvSerieRepository.getEpisode(tvSerieTittel, Integer.parseInt(sesongNr), Integer.parseInt(episodeNr)));

    }
}
