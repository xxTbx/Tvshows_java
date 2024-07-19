package controller;

import io.javalin.http.Context;
import model.Episode;
import model.TvSerie;
import repository.TvSerieRepository;

import java.time.LocalDate;

public class TvSerieController {
    private TvSerieRepository tvSerieRepository;

    public TvSerieController(TvSerieRepository tvSerieRepository) {
        this.tvSerieRepository = tvSerieRepository;
    }

    public void getAlleTvSerier(Context ctx) {
        ctx.json(tvSerieRepository.getTVSerier());
    }

    public void getTVSerie(Context context) {
        String tvSerieId = context.pathParam("tvserie-id");

        context.json(tvSerieRepository.getTvSerie(tvSerieId));
    }

    public void opprettEnEpisode(Context context) {
        String tvSerieTittel = context.pathParam("tvserie-id");

        String tittel = context.formParam("tittel");
        String beskrivelse = context.formParam("beskrivelse");
        int epNr = Integer.parseInt(context.formParam("episodeNummer"));
        int sesongNr = Integer.parseInt(context.formParam("sesongNummer"));
        int spilletid = Integer.parseInt(context.formParam("spilletid"));
        LocalDate utgivelsesdato = LocalDate.parse(context.formParam("utgivelsesdato"));
        String url = context.formParam("bildeUrl");


        Episode episode = new Episode(tittel, beskrivelse, epNr, sesongNr, spilletid, utgivelsesdato, url);

        TvSerie tvSerie = tvSerieRepository.getTvSerie(tvSerieTittel);
        tvSerie.leggTilEpisode(episode);


    }
}
