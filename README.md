**Patient Appointment Application**

A simple REST based API to create appointments for patients for a given date

**PreRequisites:**
java 11
docker

This project has 3 APIs 

```{{baseurl}} = http://localhost:3001/patients```

**1. Create patient appointment API**

A simple POST method to create an appointment for the patient.
This API allows us to create a patient and appointment if the patient does not already exist.
If the patient exist this API checks for the availability of the patients for the given appointment date and time and responds accordingly

This API assumes DATE in the format YYYY-MM-DD

startTime and endTime are in 24hrs format

URL: ``{{baseurl}}/appointments``

**Sample POST request body :** 

``{
"name" : "Johndoe",
"date" : "2023-08-05",
"startTime" : "5.00",
"endTime":"6.00"
}``

**Sample POST response body:**

`{
"patientUuid": "b00b6c01-e51c-4088-b69c-0c9aed65adf6",
"patientName": "Johndoe",
"appointmentCreated": true
}`

**Sample curl request**

` curl --location 'http://localhost:3001/patients/appointments' \
--header 'Content-Type: application/json' \
--data '
{
"name" : "Johndoe",
"date" : "2023-08-05",
"startTime" : "5.00",
"endTime":"6.00"
}'`

**2. Get appointments for given name**

This API uses GET method and returns all the appointments for the given patient name.If there are no appointments for the given name , it returns an empty list

URL: ``{{baseurl}}/:name/appointments``

(:name is a path parameter)

**Sample GET response body**

`{
"2023-08-05": [
{
"startTime": 5.0,
"endTime": 6.0
}
]
}`

**Sample curl request**

`curl --location 'http://localhost:3001/patients/sona/appointments'`

**3. Get appointments for given date**

This API uses GET method to return all the appointments for the patients on the given date.We can also filter the appointments using the patient names

URL: ``{{baseurl}}/appointments/:date``

or

URL: `{{baseurl}}/appointments/:date?patientNames=Johndoe`

(`:date` is a path parameter and `patientNames` is the query parameter)

**Sample GET response body**

`[
{
"patientName": "johndoe",
"patientId": "54874fb2-52f5-47e6-be33-1e06bc06bbf9",
"appointmentList": [
{
"startTime": 5.0,
"endTime": 6.0
}
]
},
{
"patientName": "johndoe1",
"patientId": "20488484-cf2c-402f-8e9b-cb1c12e3a583",
"appointmentList": [
{
"startTime": 14.0,
"endTime": 17.0
}
]
}
]`

**Steps to pull and run the docker image**

`docker pull srujanakasu/appointment-image`

`docker run -d --name appointmentapp -p 3001:8080 srujanakasu/appointment-image`

`docker ps -a`

You can now see that the container is created and running in the port 3001

**Postman Collections**

The postman collections and environment JSON file are located in `src/main/resources/postman collection and env`

