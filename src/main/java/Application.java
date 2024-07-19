import controller.EpisodeController;
import controller.TvSerieController;
import io.javalin.Javalin;
import io.javalin.http.Context;
import io.javalin.http.Handler;
import io.javalin.vue.VueComponent;
import org.jetbrains.annotations.NotNull;
import repository.TvSerieCSVRepository;
import repository.TvSerieDataRepository;
import repository.TvSerieJSONRepository;
import repository.TvSerieRepository;

public class Application {

    public static void main(String[] args) {
        Javalin app = Javalin.create(config -> {
            config.staticFiles.enableWebjars();
            config.vue.vueAppName = "app";
        }).start(8100);

        app.before("/", ctx -> ctx.redirect("/tvserie"));

        app.get("/tvserie", new VueComponent("tvserie-overview"));
        app.get("/tvserie/{tvserie-id}/sesong/{sesong-nr}", new VueComponent("tvserie-detail"));
        app.get("/tvserie/{tvserie-id}/sesong/{sesong-nr}/episode/{episode-nr}", new VueComponent("episode-detail"));
        app.get("/tvserie/{tvserie-id}/createepisode", new VueComponent("episode-create"));
        app.get("/tvserie/{tvserie-id}/sesong/{sesong-nr}/episode/{episode-nr}/updateepisode", new VueComponent("episode-update"));



        TvSerieRepository tvSerieRepositoryJSON = new TvSerieJSONRepository("src/tvshows_10.json");
        TvSerieController tvSerieControllerJSON= new TvSerieController(tvSerieRepositoryJSON);
        EpisodeController episodeControllerJSON = new EpisodeController(tvSerieRepositoryJSON);

        TvSerieRepository tvSerieRepositoryCSV = new TvSerieCSVRepository("src/tvshows_10.csv");
        TvSerieController tvSerieControllerCSV = new TvSerieController(tvSerieRepositoryCSV);
        EpisodeController episodeControllerCSV = new EpisodeController(tvSerieRepositoryCSV);


        app.get("api/tvserie", new Handler() {
            @Override
            public void handle(Context ctx) throws Exception {
                //tvSerieController.getAlleTvSerier(ctx);
                tvSerieControllerJSON.getAlleTvSerier(ctx);
            }
        });

        app.get("api/tvserie/{tvserie-id}", context -> tvSerieControllerJSON.getTVSerie(context));


        app.get("api/tvserie/{tvserie-id}/sesong/{sesong-nr}", episodeControllerJSON::getEpisoderISesong);
        app.get("api/tvserie/{tvserie-id}/sesong/{sesong-nr}/episode/{episode-nr}", episodeControllerJSON::getEpisode);


        app.before("/api/tvserie/{tvserie-id}/sesong/{sesong-nr}/episode/{episode-nr}/deleteepisode", ctx -> {
            episodeControllerJSON.slettEnEpisode(ctx);
            String tvserieId = ctx.pathParam("tvserie-id");
            String sesNr = ctx.pathParam("sesong-nr");
            ctx.redirect("/tvserie/" + tvserieId + "/sesong/" + sesNr);
        });

        app.before("/api/tvserie/{tvserie-id}/createepisode", ctx -> {
            tvSerieControllerJSON.opprettEnEpisode(ctx);
            String tvserieId = ctx.pathParam("tvserie-id");
            ctx.redirect("/tvserie/" + tvserieId + "/sesong" + "/1");
        });

        app.before("/api/tvserie/{tvserie-id}/sesong/{sesong-nr}/episode/{episode-nr}/updateepisode", ctx -> {
            episodeControllerJSON.oppdaterEnEpisode(ctx);
            String tvserieId = ctx.pathParam("tvserie-id");
            String sesNr = ctx.pathParam("sesong-nr");
            ctx.redirect("/tvserie/" + tvserieId + "/sesong/" + sesNr);
        });



    }
}
