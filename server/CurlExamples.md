GET REQUEST:

curl localhost:3000/users

This command will return a json object with a list of all the users in a root
json object under the variable "users"

curl localhost:3000/users/###

Where ### is the id of the user you want to get from the the table

POST REQUEST:

curl -d '{"user":{"email":"user@email.com","name":"yourname"}}' -H "Content-Type: application/json" -X POST localhost:3000/users

PUT REQUEST:

curl -d '{"user":{"email":"user@email.com","name":"yourname"}}' -H "Content-Type: application/json" -X PUT localhost:3000/users/###

Where ### is the id of the user you want to edit

DELETE REQUEST:

curl -X DELETE localhost:3000/users/###

Where ### is the id of the user you want to delete from the user table
