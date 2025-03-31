#
# Description: This contains the curl commands used to test the API endpoints.
#         
#    start the service with "mvn spring-boot:run" then run one of the following commands.
#


#create a project
curl -X POST localhost:8080/projects -H 'Content-type:application/json' -d '{"name": "EV Update", "description": "trending news of electric vehicles"}'

#retrieve all projects
curl -v localhost:8080/projects

#retrieve a project
curl -v localhost:8080/projects/449877032980072746


#update a project
curl -X PUT localhost:8080/projects/449877032980072746 -H 'Content-type:application/json' -d '{"name": "EV Update 2", "description": "another news of electric vehicles"}'

#delete a project
curl -X DELETE localhost:8080/projects/449877032980072746
