package org.dawn.backend.controller.sales;


import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.dawn.backend.config.web.annotation.Get;
import org.dawn.backend.config.web.response.ResponseObject;
import org.dawn.backend.controller.base.AbstractController;
import org.dawn.backend.entity.ProductItem;
import org.dawn.backend.service.sales.DashboardService;

import java.util.List;

@RequiredArgsConstructor
public class DashboardController extends AbstractController {


    private final DashboardService dashboardService;

    @Get("/low-stock")
    public ResponseObject<?> lowStock(HttpServletRequest req, HttpServletResponse res) {
        return ResponseObject.success(dashboardService.getLowStockAlerts());
    }


    @Get("/trace")
    public ResponseObject<?> trace(HttpServletRequest req, HttpServletResponse res) {
        String imei = req.getParameter("imei");
        return ResponseObject.success(dashboardService.traceImei(imei));
    }

    @Get("/aging-report")
    public ResponseObject<List<ProductItem>> getAgingReport(HttpServletRequest req, HttpServletResponse res) {
        int days = Integer.parseInt(req.getParameter("days"));
        return ResponseObject.success(dashboardService.getAgingStockReport(days));
    }

    @Get("/summary")
    public ResponseObject<?> getSummary(HttpServletRequest req, HttpServletResponse res) {
        return ResponseObject.success(dashboardService.getAdminDashboard());
    }
}
