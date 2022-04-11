# expense-reimbursement-system

## Description
The Expense Reimbursement System (ERS) will manage the process of reimbursing employees for expenses incurred while on company time. All employees in the company can login and submit requests for reimbursement and view their past tickets and pending requests. Finance managers can log in and view all reimbursement requests and past history for all employees in the company. Finance managers are authorized to approve and deny requests for expense reimbursement.

## Technologies Used
* Java - v17.0.1
* JDBC - v
* JJwt - v0.11.2
* Javalin - v4.3.0
* Jackson Databind - v2.13.2
* gradle - v7.4
* postgresql - v42.3.3
* logback - v1.2.11
* tika - v2.3.0
* junit(jupiter) - v5.8.2
* mockito - v4.4.0
use vanilla JS, Java, & postgresql

## Features
* Login/Logout Functionality
* Employee can log in and see all reimbursements
 - can view all, pending, and previous reimbursements
 - can add a reimbusrement
* Manager can log in and view all reimbursements
 - can view all & pending reimbursements
 - can change status of a pending reimbursement

### TO-DO List
* Deploy front-end to firebase
* Host backend to an instance on GCP Compute Engine
* Use GCP Cloud Storage to handle incoming BLOBs
* Host db on GCP Cloud SQL
* Use SonarCloud to catch code bugs & code smells

## Getting Started
* use "git clone <repo-link>" to clone the repo
* setup schmea and a new SQLScript with ersSampleScript in postgres
* populate script with sample data
* setup your env variables in environment variables
  - db_url - url of database
  - db_user - databse username 
  - db_pass - database password
* open project folder and use cmd "gradle build"
  - go into build/libs and run "java -jar <jar-file>

 ### App should be fully running 
  
 ## Usage
  Use it for your employees to add a reimbursement claim with a picture attached. Claims can be made for services such as lodging, food, or other company reimbursements. Employees can view all, previous, & pending reimbursements. Managers can login to view all & pending reimbursements. They are allowed to review all the details for a reimbursement, and change the status to Accepted or Denied. Reimbursement updates for both employees & managers happen in realtime. When each user is finished they can logout.
  
 ## Contributors
  Javanshir Pashayev
 
 ## License
  MIT License
  

