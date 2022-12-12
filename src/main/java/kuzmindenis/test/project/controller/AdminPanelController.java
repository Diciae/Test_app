package kuzmindenis.test.project.controller;

import kuzmindenis.test.project.model.Category;
import kuzmindenis.test.project.service.CategoryService;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

//Html-Админка для категорий
@Controller
public class AdminPanelController {

    private final CategoryService categoryService;

    public AdminPanelController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    //Список категорий
    @GetMapping("/admin/categories")
    @ResponseBody
    public String listCategories(
            HttpServletRequest request,
            HttpServletResponse response

    ) throws IOException {
        if (!checkAuthorization(request, response)) {
            return getHtmlTemplate("Пожалуйста авторизщуйтесь!");
        }
        List<Category> categories = categoryService.getCategories();
        return getCategoriesHtml(categories);
    }

    //Одна категория по ID
    @GetMapping("/admin/categories/{id}")
    @ResponseBody
    public String getCategory(
            @PathVariable("id") int categoryId,
            HttpServletRequest request,
            HttpServletResponse response
    ) throws IOException {
        if (!checkAuthorization(request, response)) {
            return getHtmlTemplate("Пожалуйста авторизщуйтесь!");
        }
        Category category = categoryService.getCategory(categoryId);
        return getCategoryHtml(category);
    }

    //Обновление категории через html form
    @PostMapping("/admin/categories/{id}")
    @ResponseBody
    public void updateCategory(
            @PathVariable("id") long categoryId,
            @RequestParam("name") String name,
            @RequestParam("file") MultipartFile file,
            HttpServletRequest request,
            HttpServletResponse response
    ) throws IOException {
        if (!checkAuthorization(request, response)) {
            //do nothing
            return;
        }
        categoryService.saveCategory(new Category(
                categoryId,
                name,
                file.getBytes()
        ));
        response.sendRedirect("/admin/categories/" + categoryId);
    }

    private static String getCategoriesHtml(List<Category> categories) {
        List<String> categoriesElems = new ArrayList<>();
        categoriesElems.add("<h2>List of categories:</h2>");
        for (Category category : categories) {
            String imageData = new String(Base64.encodeBase64(category.getImage()));
            categoriesElems.add(
                    "<p>" +
                            "<p>" +
                            "<a href=\"/admin/categories/" + category.getId() + "\">" + category.getId() + "</a>" +
                            "</p>" +
                            "<p>" +
                            category.getName() +
                            "</p>" +
                            "<img src=\"data:image/jpeg;base64, " + imageData + "\" />" +
                            "</li>"
            );
        }
        return getHtmlTemplate(String.join("", categoriesElems));
    }

    private static String getCategoryHtml(Category category) {
        String imageData = new String(Base64.encodeBase64(category.getImage()));
        String body =

                "<h2>Category:</h2>" +
                        "<p><img src=\"data:image/jpeg;base64, " + imageData + "\" /></p>" +
                        "<form action=\"/admin/categories/" + category.getId() + "\" method=\"post\" enctype=\"multipart/form-data\">\n" +
                        "  <input type=\"file\" name=\"file\">\n" +
                        "  <input name=\"name\" value=\"" + category.getName() + "\" />" +
                        "  <input type=\"submit\">" +
                        "</form>";

        return getHtmlTemplate(body);
    }

    private static String getHtmlTemplate(String body) {
        return "<!doctype html>\n" +
                "<html>\n" +
                "<head>\n" +
                "<title>Admin</title>\n" +
                "</head>\n" +
                "<body>\n" +
                body +
                "</body>\n" +
                "</html>";
    }

    //https://developer.mozilla.org/en-US/docs/Web/HTTP/Authentication
    private static boolean checkAuthorization(
            HttpServletRequest request,
            HttpServletResponse response
    ) throws IOException {
        String authorization = request.getHeader("Authorization");

        if (authorization == null || !authorization.equalsIgnoreCase("Basic YWRtaW46YWRtaW4=")) {
            response.setStatus(401);
            response.setHeader("WWW-Authenticate", "Basic realm=\"myRealm\"");
            return false;
        }
        return true;
    }
}
