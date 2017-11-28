A get request to a base address (i.e. /users or /ratings) will give a dump of
the entire database inside of a root JSON object

To make an entry to the database make a post request to the base address of the
database you want to make an entry to

To access an individual element of the database go to the base address followed
by the id of the element you want to access, for example /users/1 -- From here
you display it with a get request, you can edit with a put or patch request,
and delete it with a delete request

Logging in for this server means to get the user id for a user by querying the
user database with the user provided email and name. To do this perform a get
request at /users/signin with name and email in a JSON structure

To get a music download, send a get request to /songs/###/listen and to acquire
the art send a get request to /songs/###/art, where ### is the ID of the song
