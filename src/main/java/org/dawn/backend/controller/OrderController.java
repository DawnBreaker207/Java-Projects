package org.dawn.backend.controller;

import lombok.RequiredArgsConstructor;
import org.dawn.backend.dto.request.OrderRequest;
import org.dawn.backend.service.OrderService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("order")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @PostMapping("")
    @PreAuthorize("hasRole('SALE')")
    public ResponseEntity<?> create(@RequestBody OrderRequest req) {
        return ResponseEntity.ok(orderService.create(req));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> cancel(@PathVariable Long id) {
        return ResponseEntity.ok(orderService.cancelOrder(id));
    }
}
