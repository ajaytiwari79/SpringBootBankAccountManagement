Bank X has defined a specification for a new Banking Application. In keeping up with the trends 
around the world, Bank X wants to be able to allow both internal and external systems to connect
with the new application. The Application must allow new customers to be onboarded and to obtain
new accounts. Each customer with be provided with a Current and Savings account upon onboarding
and will have their Savings Account credited with a joining bonus of R500.00. Customers should be
able to move money between their accounts. Only the Current Account should be enabled to make payments
to other accounts. All payments made into the Savings Account will be credited with a 0.5% interest of
the current balance. All payments made from the customer’s account will be charged 0.05% of the
transaction amount. The application must keep track of every transaction performed on the accounts and
allow other systems to retrieve these. The system must send out notifications to the customer for every
transaction event. Bank X also want to allow Bank Z to be able debit or credit the customer’s account
for any transactions that were handled by Bank Z on behalf of Bank X. Bank Z should be able to send a
single immediate transaction or a list of transactions which should be processed immediately. Bank Z
should be able to send Bank X a list of transactions that they processed on behalf of Bank X at the 
end of the business day for reconciliation.

API's -> 

Add new customer 
POST /add_new_customer
Add new customers and create two accounts for each customer 
1) savings account : savings account with default amount 500.
2) current account : current account with default amount 0.

Get Balance
GET /get_balance/{c_id}
Description
Get the balance of a bank account for a given customer ID.

Add Balance to Savings Account
PUT /add_balance_to_savings_account
Description
Add balance to the savings account of a customer.


Transfer Balance from Savings to Current Account
PUT /transfer_balance_savings_to_current_account
Description
Transfer balance from the savings account of a customer to their current account.

Transfer Balance Between users
PUT /transfer_amount_bw_users
Transfer balance from the current account of a customer1 to their savings account of customer2.

Add Balance in user account by bankZ
PUT /add_balance

Add Balance from user account by bankZ
PUT /get_balance

For Reconciliation at the end of day
Put /reconciliation
At the end of day bankX and bankZ perform reconciliation process where bankX and bankZ match all the transactions perform by bankZ

For Testing and Documentation of API's we are using Swagger 
SWAGGER URL ->
http://localhost:7979/bankx/swagger-ui/

Database -> MySQL
Need to create database in MySQL db with name bankx as per configuration 
or need to change database configuration in yaml file
