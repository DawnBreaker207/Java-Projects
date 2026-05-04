package org.dawn.backend.controller.catalog;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.dawn.backend.config.web.annotation.Get;
import org.dawn.backend.config.web.annotation.Post;
import org.dawn.backend.config.web.annotation.Put;
import org.dawn.backend.config.web.response.ResponseObject;
import org.dawn.backend.constant.auth.URole;
import org.dawn.backend.controller.base.AbstractController;
import org.dawn.backend.dto.catalog.ProductRequest;
import org.dawn.backend.dto.catalog.ProductResponse;
import org.dawn.backend.dto.catalog.ProductUpdateRequest;
import org.dawn.backend.service.catalog.ProductService;

import java.util.List;

@RequiredArgsConstructor
public class ProductController extends AbstractController {
    private final ProductService productService;

    @Get("/")
    public ResponseObject<List<ProductResponse>> getProducts(HttpServletRequest req) {
        int page = Integer.parseInt(req.getParameter("page") != null ? req.getParameter("page") : "0");
        int size = Integer.parseInt(req.getParameter("size") != null ? req.getParameter("size") : "10");
        return ResponseObject.success(productService.getAll());
    }

    @Get("/{id}")
    public ResponseObject<ProductResponse> getProduct(HttpServletRequest req) {
        return ResponseObject.success(productService.getOne(getPathId(req)));
    }

    @Post("/")
    public ResponseObject<ProductResponse> addProduct(HttpServletRequest req) {
        checkRole(URole.ADMIN.name());
        ProductRequest dto = body(req, ProductRequest.class);
        return ResponseObject.success(productService.create(dto));
    }

    @Put("/{id}")
    public ResponseObject<ProductResponse> updateProduct(HttpServletRequest req) {
        checkRole(URole.ADMIN.name());
        ProductUpdateRequest dto = body(req, ProductUpdateRequest.class);
        return ResponseObject.success(productService.updateProduct(getPathId(req), dto));
    }
}
