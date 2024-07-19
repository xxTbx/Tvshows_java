package repository;

import model.Episode;
import model.Person;
import model.TvSerie;

import java.io.*;
import java.time.LocalDate;
import java.util.*;

public class TvSerieCSVRepository implements TvSerieRepository {




    private HashMap<String, TvSerie> tvr = new HashMap<>();


    public TvSerieCSVRepository(String filepath) {
        File file = new File(filepath);
        HashMap<String,TvSerie> tvserierFromFile = readFromCSVFile("src/tvshows_10.csv", ";");

        tvr.putAll(tvserierFromFile);
    }

    public HashMap<String, TvSerie> readFromCSVFile(String filepath, String seperator) {
        HashMap<String, TvSerie> tvSerierFraCSV = new HashMap<>();


        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(filepath))) {
            String line;

            while ((line = bufferedReader.readLine()) != null) {
                String[] parts = line.split(seperator);
                String name = parts[0];
                String beskrivelse = parts[1];
                LocalDate utgivelsesdato = LocalDate.parse(parts[2]);
                String url = parts[3];
                String episode = parts[4];
                String dd = parts [5];
                int d = Integer.parseInt(parts [6]);
                int c = Integer.parseInt(parts [7]);
                int r = Integer.parseInt(parts [8]);
                LocalDate e_utgivelsesdato = LocalDate.parse(parts [9]);
                String e_url= parts [10];
                String reg_ = parts [11];
                String reg_etter = parts [12];
                LocalDate reg_fodselsdato = LocalDate.parse(parts [13]);

                TvSerie tvSerie = new TvSerie(name, beskrivelse, utgivelsesdato, url);

                Person person = new Person(reg_, reg_etter, reg_fodselsdato);
                Episode episode1 = new Episode(episode, dd, d, c, r , e_utgivelsesdato, person, e_url);

                tvSerie.leggTilEpisode(episode1);


                if (tvSerierFraCSV.containsKey(name)) {
                    tvSerierFraCSV.get(name).leggTilEpisode(episode1);
                } else {
                    tvSerierFraCSV.put(name, tvSerie);
                }


            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return tvSerierFraCSV;
    }

    private static void writeToCSVFile(List<TvSerie> tvSerier, String filepath, String seperator) {
        // Logikk for å skrive superhelter til CSV-fil
        try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(filepath))) {
            for (TvSerie tvSerie : tvSerier) {
                bufferedWriter.write(tvSerie.getTittel() + ";" + tvSerie.getBeskrivelse() + ";" + tvSerie.getUtgivelsesdato() + ";" + tvSerie.getBildeUrl());
                bufferedWriter.newLine();
            }
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }


    @Override
    public ArrayList<TvSerie> getTVSerier() {
        return new ArrayList<>(tvr.values());
    }

    @Override
    public TvSerie getTvSerie(String tvSerieTittel) {
        for (TvSerie tvSerie : tvr.values()) {
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
    public void opprettEnEpisode(String tittel, int sesongNr, int episodeNr) {

    }

    @Override
    public void oppdaterEnEpisode(Episode episode) {

    }



    @Override
    public void slettEnEpisode(String tvSerietittel, int sesongNr, int episodeNr) {

    }



}
