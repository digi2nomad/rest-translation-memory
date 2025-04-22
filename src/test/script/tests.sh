#
# Description: This contains the curl commands used to test the API endpoints.
#         
#    start the service with "mvn spring-boot:run" then run one of the following commands.
#
#    The following is the information of endpoints available:
#       http://localhost:8080/swagger-ui/index.html
#		http://localhost:8080/api-docs
#
#    The following is the information of the health check:
#       http://localhost:8080/actuator/health
#       http://localhost:8080/actuator/env
#       http://localhost:8080/actuator/metrics
#       http://localhost:8080/actuator/beans
#       http://localhost:8080/actuator/mappings
#       http://localhost:8080/actuator/configprops
#       http://localhost:8080/actuator/info
#       http://localhost:8080/actuator/loggers
#       http://localhost:8080/actuator/scheduledtasks
#       http://localhost:8080/actuator/threaddump
#       http://localhost:8080/actuator/heapdump 
#
#    The following is the information of the h2 database:
#       http://localhost:8080/h2-console
#		
#
date=$(date '+%Y-%m-%d-%s')
#    
#create a project
#
curl -s -X POST localhost:8080/projects \
-H 'Content-type:application/json' \
--data-binary @- << EOF
{ 
   "name": "EV Update", 
   "description": "trending news of electric vehicles" 
}
EOF

#
#retrieve all projects
#
curl -s localhost:8080/projects | tee /tmp/projects-output-$date

#
#retrieve a project
#
projId=`cat /tmp/projects-output-$date | grep id | cut -d':' -f2 | cut -c2-20`
curl -s localhost:8080/projects/$projId

#
#update a project
#
curl -s -X PUT localhost:8080/projects/$projId \
-H 'Content-type:application/json' \
--data-binary @- << EOF
{
	"name": "EV Update 2", 
	"description": "another news of electric vehicles"
}
EOF

#
#create an unit
#	
curl -s -X POST localhost:8080/projects/$projId/units \
-H 'Content-type:application/json' \
--data-binary @- << EOF
{
	"projId": $projId,
	"segmentType": { 
		"type": "sentence"
	 }
}
EOF

#
#retrieve all units
#
curl -s localhost:8080/projects/$projId/units | tee /tmp/units-output-$date

#
#retrieve a unit
#
unitId=`cat /tmp/units-output-$date | grep -m1 id | cut -d':' -f2 | cut -c2-20`
curl -s localhost:8080/projects/$projId/units/$unitId

#
#update a unit
#
#curl -X PUT localhost:8080/projects/$projId/units/$unitId \
#-H 'Content-type:application/json' \
#--data-binary @- << EOF
#{
#    "projId": $projId,
#    "segmentType": { 
#        "type": "sentence"
#     }
#}

#
#create a variant
#
curl -s -X POST localhost:8080/projects/$projId/units/$unitId/variants \
-H 'Content-type:application/json' \
--data-binary @- << EOF
{
    "tuId": $unitId,
    "language": {
	    "langcode": "en-US"
	}, 
	"segment": "string" 
}
EOF

#
#retrieve an unit and all of its variants
#	
curl -s localhost:8080/projects/$projId/units/$unitId/variants | tee /tmp/variants-output-$date

#
#retrieve a variant
# 
#variantId=`cat /tmp/variants-output-$date | grep -m1 id | cut -d':' -f2 | cut -c2-20`
curl -s localhost:8080/projects/$projId/units/$unitId/variants/$variantId

#
#retrieve a matched unit and associated variant
#
curl -s -X POST localhost:8080/projects/$projId/matchedunit/80/language/en-US \
-H 'Content-type:application/json' \
--data-binary @- << EOF
string
EOF

#
#update a variant
#
curl -s -X PUT localhost:8080/projects/$projId/units/$unitId/variants/$variantId \
-H 'Content-type:application/json' \
--data-binary @- << EOF
{
    "tuId": $unitId,
    "language": {
        "langcode": "en-US"
    }, 
    "segment": "another string" 
}
EOF

#
#delete a variant
#
curl -s -X DELETE localhost:8080/projects/$projId/units/$unitId/variants/$variantId

#
#delete a unit
#
curl -s -X DELETE localhost:8080/projects/$projId/units/$unitId

#	
#delete a project
# 
curl -s -X DELETE localhost:8080/projects/$projId

#	
#delete temporary files
#	
rm -f /tmp/projects-output-$date
rm -f /tmp/units-output-$date
rm -f /tmp/variants-output-$date
