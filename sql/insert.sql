-- 1) Customers
INSERT INTO customers (first_name, last_name, contact_number, email, address)
VALUES ('John', 'Smith', '1234567890', 'john.smith@example.com', '123 Elm St');

INSERT INTO customers (first_name, last_name, contact_number, email, address)
VALUES ('Mary', 'Johnson', '5551234567', 'mary.johnson@example.com', '456 Oak Ave');

INSERT INTO customers (first_name, last_name, contact_number, email, address)
VALUES ('Mark', 'O\'Connor', '9998887777', 'mark.o.connor@example.com', '789 Pine Rd');

-- 2) Appointments (assuming the above 3 customers have IDs 1, 2, and 3)
INSERT INTO appointments (customer_id, start_time, duration, status)
VALUES (1, '2025-05-01 09:00:00', 30, 'Scheduled');

INSERT INTO appointments (customer_id, start_time, duration, status)
VALUES (2, '2025-05-02 14:30:00', 45, 'Scheduled');

INSERT INTO appointments (customer_id, start_time, duration, status)
VALUES (3, '2025-05-03 10:15:00', 60, 'Completed');

-- 3) Invoices (referencing those same customers)
INSERT INTO invoices (customer_id, total_amount, due_date)
VALUES (1, 150.00, '2025-06-01');

INSERT INTO invoices (customer_id, total_amount, due_date)
VALUES (2, 225.50, '2025-06-05');

INSERT INTO invoices (customer_id, total_amount, due_date)
VALUES (3, 100.00, '2025-06-10');

-- 4) Tuners (independent table, no FK references)
INSERT INTO tuners (first_name, last_name, phone, email)
VALUES ('Alice', 'Williams', '1112223333', 'alice.w@example.com');

INSERT INTO tuners (first_name, last_name, phone, email)
VALUES ('Bob', 'Garcia', '4445556666', 'bob.garcia@example.com');

INSERT INTO tuners (first_name, last_name, phone, email)
VALUES ('Carol', 'Lee', '7771119999', 'carol.lee@example.com');

-- 5) Payments (referencing invoice_id values from the invoices table)
--   Here we assume your first invoice is ID 1, second is ID 2, third is ID 3
INSERT INTO payments (invoice_id, payment_date, amount, payment_method, status)
VALUES (1, '2025-05-05 10:00:00', 100.00, 'Credit Card', 'Completed');

INSERT INTO payments (invoice_id, payment_date, amount, payment_method, status)
VALUES (1, '2025-05-10 14:30:00', 50.00, 'Cash', 'Completed');

INSERT INTO payments (invoice_id, payment_date, amount, payment_method, status)
VALUES (2, '2025-05-12 11:00:00', 225.50, 'Check', 'Completed');

INSERT INTO payments (invoice_id, payment_date, amount, payment_method, status)
VALUES (3, '2025-05-15 15:45:00', 100.00, 'Credit Card', 'Completed');
