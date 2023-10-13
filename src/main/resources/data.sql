/*
 Add Customer Data
 */
insert into customer values (1,'Bob','Smith')
insert into customer values (2, 'Steve', 'Mathew')
insert into customer values (3, 'Robin', 'Singh')

/*
Add Loan Data
*/

insert into loan(customer_id, id, terms, total_amount, creation_date, status ) values (1,1,3,25.0, '2023-05-17', 'PENDING')
insert into loan(customer_id, id, terms, total_amount, creation_date, status ) values (2,2,4,	35.4, '2023-07-30', 'PENDING')
insert into loan(customer_id, id, terms, total_amount, creation_date, status ) values (1,3,6,102.1, '2023-06-01', 'APPROVED')

/*
Add Payments Data
*/

insert into payment(amount, id, loan_id, due_date, status) values (8.333333, 1, 1, '2023-05-24', 'PENDING')
insert into payment(amount, id, loan_id, due_date, status) values (8.333333, 2, 1, '2023-05-31', 'PENDING')
insert into payment(amount, id, loan_id, due_date, status) values (8.333334, 3, 1, '2023-06-07', 'PENDING')
insert into payment(amount, id, loan_id, due_date, status) values (8.85, 4, 2, '2023-08-06', 'PENDING')
insert into payment(amount, id, loan_id, due_date, status) values (8.85, 5, 2, '2023-08-13', 'PENDING')
insert into payment(amount, id, loan_id, due_date, status) values (8.85, 6, 2, '2023-08-20', 'PENDING')
insert into payment(amount, id, loan_id, due_date, status) values (8.85, 7, 2, '2023-08-27', 'PENDING')
insert into payment(amount, id, loan_id, due_date, status) values (17.016666, 8, 3, '2023-06-08', 'PENDING')
insert into payment(amount, id, loan_id, due_date, status) values (17.016666, 9, 3, '2023-06-15', 'PENDING')
insert into payment(amount, id, loan_id, due_date, status) values (17.016666, 10, 3, '2023-06-22', 'PENDING')
insert into payment(amount, id, loan_id, due_date, status) values (17.016666, 11, 3, '2023-06-29', 'PENDING')
insert into payment(amount, id, loan_id, due_date, status) values (17.016666, 12, 3, '2023-07-06', 'PENDING')
insert into payment(amount, id, loan_id, due_date, status) values (17.01667, 13, 3, '2023-07-13', 'PENDING')






