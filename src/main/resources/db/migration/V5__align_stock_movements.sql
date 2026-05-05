-- V5: align stock_movements with current entity (add supplier_id).
-- Idempotent: only adds when missing.

DECLARE
    v_count NUMBER;
BEGIN
    SELECT COUNT(*) INTO v_count FROM user_tab_columns
     WHERE table_name = 'STOCK_MOVEMENTS' AND column_name = 'SUPPLIER_ID';
    IF v_count = 0 THEN
        EXECUTE IMMEDIATE 'ALTER TABLE stock_movements ADD (supplier_id NUMBER(19, 0))';
        EXECUTE IMMEDIATE
            'ALTER TABLE stock_movements ADD CONSTRAINT fk_move_supplier ' ||
            'FOREIGN KEY (supplier_id) REFERENCES suppliers (id)';
    END IF;
END;
/
