package org.dawn.backend.controller.warranty;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.dawn.backend.config.web.annotation.Post;
import org.dawn.backend.config.web.annotation.Put;
import org.dawn.backend.config.web.response.ResponseObject;
import org.dawn.backend.controller.base.AbstractController;
import org.dawn.backend.dto.warranty.CreateWarrantyRequest;
import org.dawn.backend.dto.warranty.UpdateWarrantyRequest;
import org.dawn.backend.entity.Warranty;
import org.dawn.backend.service.warranty.WarrantyService;

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
