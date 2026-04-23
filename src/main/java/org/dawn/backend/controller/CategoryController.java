package org.dawn.backend.controller;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.dawn.backend.config.annotation.Get;
import org.dawn.backend.config.annotation.Post;
import org.dawn.backend.config.annotation.Put;
import org.dawn.backend.config.response.ResponseObject;
import org.dawn.backend.controller.config.AbstractController;
import org.dawn.backend.dto.request.CategoryRequest;
import org.dawn.backend.dto.response.CategoryResponse;
import org.dawn.backend.service.CategoryService;

import java.util.List;

@RequiredArgsConstructor
public class CategoryController extends AbstractController {

    private final CategoryService categoryService;

    @Get("/")
    public ResponseObject<List<CategoryResponse>> getCategories(HttpServletRequest req) {
        int page = Integer.parseInt(req.getParameter("page") != null ? req.getParameter("page") : "0");
        int size = Integer.parseInt(req.getParameter("size") != null ? req.getParameter("size") : "10");
        return ResponseObject.success(categoryService.getAll());
    }

    @Get("/{id}")
    public ResponseObject<CategoryResponse> getProduct(HttpServletRequest req) {
        return ResponseObject.success(categoryService.getOne(getPathId(req)));
    }

    @Post("/")
    public ResponseObject<CategoryResponse> addProduct(HttpServletRequest req) {
        CategoryRequest dto = body(req, CategoryRequest.class);
        return ResponseObject.success(categoryService.create(dto));
    }

    @Put("/{id}")
    public ResponseObject<CategoryResponse> updateProduct(HttpServletRequest req) {
        CategoryRequest dto = body(req, CategoryRequest.class);
        return ResponseObject.success(categoryService.updateCategory(getPathId(req), dto));
    }

}
