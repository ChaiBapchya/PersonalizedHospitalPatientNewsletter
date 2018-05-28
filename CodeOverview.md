# What's Done?

1. FHIR query

> Input - ICD9 code of the Problem

> Output - Summary of NIH description

2. CRON jobs - https://www.techradar.com/how-to/computing/apple/terminal-101-creating-cron-jobs-1305651

## Local Database Schema
```
NextOfKin Email Address, Patient Id, DateOFAdmission,DateOfDischarge,Status,PhoneNumber,ProblemList
```

# To Do

### Function

> Input - Patient ID

> Output - Problems (Diagnonses) corresponding to the patient

Chai
1. Create the DB in MongoDB (Key-value pair)
2. Java program that runs every scheduled time (hourly / daily)
3. Find the patients that are currently in hospital based on fixed data (for test purposes)
4. Use code for getting the problems corresponding to the patients and populate it in the MongoDB
5. Use Ashwin's code (FHIR query) to retrieve Description of the problem

Ashwin and Eric
Java program link to send the description and embed into an email format
