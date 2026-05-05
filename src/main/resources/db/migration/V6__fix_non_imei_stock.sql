-- V8: restore current_stock for non-IMEI products.
-- V7 step 8 derived current_stock from AVAILABLE product_items, but
-- products without IMEI tracking (cables, chargers, cases, mice, ...)
-- have zero rows in product_items, so they ended up at 0. Reset those
-- back to a representative number so dashboards / xuat-kho show stock.

UPDATE products
SET current_stock = CASE sku
    WHEN 'CAB-USBC-2M'   THEN 45
    WHEN 'CHG-GAN65W'    THEN 28
    WHEN 'CAS-IPH15-CLR' THEN 32
    WHEN 'MOU-LGT-MX3'   THEN 12
    WHEN 'SAM-A55-256'   THEN 15
    WHEN 'APD-PRO2-WHT'  THEN 6
    ELSE current_stock
END
WHERE has_imei = 0 OR sku IN ('SAM-A55-256','APD-PRO2-WHT');

COMMIT;
