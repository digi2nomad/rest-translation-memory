#
# Description: This contains the curl commands used to test the API endpoints.
#         
#    start the service with "mvn spring-boot:run" then run one of the following commands.
#
#    The following is the information of endpoints available:
#       http://localhost:8080/swagger-ui/index.html
#		http://localhost:8080/api-docs
#       http://localhost:8080/actuator/health
#
#    The following is the information of the database:
#       http://localhost:8080/h2-console
#		
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

#create an unit	
curl -X POST localhost:8080/projects/449877032980072746/units -H 'Content-type:application/json' -d '{"name": "EV Update", "description": "trending news of electric vehicles"}'

#retrieve all units
curl -v localhost:8080/projects/449877032980072746/units

#retrieve a unit
curl -v localhost:8080/projects/449877032980072746/units/449877032980072746

#update a unit
curl -X PUT localhost:8080/projects/449877032980072746/units/449877032980072746 -H 'Content-type:application/json' -d '{"name": "EV Update 2", "description": "another news of electric vehicles"}'

#delete a unit
curl -X DELETE localhost:8080/projects/449877032980072746/units/449877032980072746

#create a variant
curl -X POST localhost:8080/projects/449877032980072746/units/449877032980072746/variants -H 'Content-type:application/json' -d '{"name": "EV Update", "description": "trending news of electric vehicles"}'

#retrieve all variants
curl -v localhost:8080/projects/449877032980072746/units/449877032980072746/variants

#retrieve a variant
curl -v localhost:8080/projects/449877032980072746/units/449877032980072746/variants/449877032980072746

#retrieve a matched unit and associated variant
curl -v localhost:8080/projects/449877032980072746/matchedunit/80/language/EN

#update a variant
curl -X PUT localhost:8080/projects/449877032980072746/units/449877032980072746/variants/449877032980072746 -H 'Content-type:application/json' -d '{"name": "EV Update 2", "description": "another news of electric vehicles"}'

#delete a variant
curl -X DELETE localhost:8080/projects/449877032980072746/units/449877032980072746/variants/449877032980072746


