package hexlet.code.controllers;

import hexlet.code.model.Url;
import hexlet.code.model.query.QUrl;
import io.ebean.PagedList;
import io.javalin.http.Handler;
import io.javalin.http.NotFoundResponse;

import java.net.URL;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public final class UrlController {

    private static Handler show = ctx -> {
        int id = ctx.pathParamAsClass("id", Integer.class).getOrDefault(null);

        Url dbUrl = new QUrl()
                .id.equalTo(id)
                .findOne();

        if (dbUrl == null) {
            throw new NotFoundResponse();
        }

        ctx.attribute("url", dbUrl);
        ctx.render("url/show.html");
    };

    private static Handler list = ctx -> {
        int page = ctx.queryParamAsClass("page", Integer.class).getOrDefault(1) - 1;
        final int rowsPerPage = 10;

        PagedList<Url> pagedUrls = new QUrl()
                .setFirstRow(page * rowsPerPage)
                .setMaxRows(rowsPerPage)
                .orderBy()
                .id.asc()
                .findPagedList();

        List<Url> dbUrls = pagedUrls.getList();

        int lastPage = pagedUrls.getTotalPageCount() + 1;
        int currentPage = pagedUrls.getPageIndex() + 1;
        List<Integer> pages = IntStream
                .range(1, lastPage)
                .boxed()
                .collect(Collectors.toList());

        ctx.attribute("pages", pages);
        ctx.attribute("currentPage", currentPage);
        ctx.attribute("urls", dbUrls);
        ctx.render("url/list.html");
    };

    private static Handler create = ctx -> {
        String name = ctx.formParam("url");

        URL url;
        try {
            url = new URL(name);
        } catch (Exception e) {
            final int found = 302;
            ctx.status(found);
            ctx.sessionAttribute("flash", "Некорректный URL");
            ctx.sessionAttribute("flash-type", "danger");
            ctx.redirect("/");
            return;
        }

        String parsedUrl = url.getProtocol() + "://" +  url.getHost();
        if (url.getPort() > 0) {
            parsedUrl = parsedUrl + ":" +  url.getPort();
        }

        Url dbUrl = new QUrl()
                .name.equalTo(parsedUrl)
                .findOne();

        if (dbUrl != null) {
            ctx.sessionAttribute("flash", "Страница уже существует");
            ctx.sessionAttribute("flash-type", "danger");
            ctx.attribute("url", url);
            ctx.redirect("/urls/" + dbUrl.getId());
            return;
        }

        Url modelUrl = new Url(parsedUrl);
        modelUrl.save();

        ctx.sessionAttribute("flash", "Страница успешно добавлена");
        ctx.sessionAttribute("flash-type", "success");
        ctx.redirect("/urls");
    };

    public static Handler listUrl() {
        return list;
    }

    public static Handler createUrl() {
        return create;
    }

    public static Handler showUrl() {
        return show;
    }
}
