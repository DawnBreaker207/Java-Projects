package org.dawn.backend.controller;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.dawn.backend.config.annotation.Post;
import org.dawn.backend.config.annotation.Put;
import org.dawn.backend.config.response.ResponseObject;
import org.dawn.backend.controller.config.AbstractController;
import org.dawn.backend.dto.request.CreateWarrantyRequest;
import org.dawn.backend.dto.request.UpdateWarrantyRequest;
import org.dawn.backend.entity.Warranty;
import org.dawn.backend.service.WarrantyService;

import java.util.List;

@RequiredArgsConstructor
public class WarrantyController extends AbstractController {
    private final WarrantyService warrantyService;

    @Post("/create")
    public ResponseObject<List<Warranty>> create(HttpServletRequest req) {
        CreateWarrantyRequest dto = body(req, CreateWarrantyRequest.class);
        return ResponseObject.created(warrantyService.createClaim(dto));
    }

    @Put("/update")
    public ResponseObject<Warranty> update(HttpServletRequest req) {
        UpdateWarrantyRequest dto = body(req, UpdateWarrantyRequest.class);
        return ResponseObject.success(warrantyService.updateStatus(dto));
    }
}
