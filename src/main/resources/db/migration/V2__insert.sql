MERGE INTO roles t
    USING (SELECT 'ADMIN' r_name, 'System Administrator' r_desc
           FROM dual
           UNION ALL
           SELECT 'SALES', 'Sales Department'
           FROM dual
           UNION ALL
           SELECT 'STOCK', 'Stock/Warehouse Management'
           FROM dual) s ON (t.name = s.r_name)
    WHEN MATCHED THEN UPDATE SET t.description = s.r_desc, t.updated_at = CURRENT_TIMESTAMP
    WHEN NOT MATCHED THEN INSERT (name, description) VALUES (s.r_name, s.r_desc);

COMMIT;