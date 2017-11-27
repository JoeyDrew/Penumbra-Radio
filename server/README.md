Below is a (hopefully) comprehensive list of dependant software for the server
as well as the versions for said software

ruby 2.3.1p112 (2016-04-26) [x86_64-linux-gnu]
Rails 4.2.6
gem 2.5.1
Bundler version 1.11.2

After you get the requisite software, you will also have to run the command
'bundle install' inside of the root directory of the server, and make sure you 
that you have all of the appropriate gems also installed on your instance.


Setting the server up after pulling from git:

Inside of the PenumbraRadio directory below this README, run the following
commands in this order --

rake db:migrate
rake db:seed

If you need to reset the databases, run this command --

rake db:drop

This will drop the tables, and you can run migrate and seed again to start over


To actually host the server on your localhost run the following command --

rails s > /dev/null &

This will run the server in the background of your terminal instance, and pipe
any output to the /dev/null/ file where it will be deleted. To close the server
you can bring it to the foreground with this command:

fg

And from there you can kill it with ctrl-c like any other program

If you want to see the output of the server of course leave off the pipe to /dev/null


After the server is up and running, you can access it by going to 
http://localhost:3000/ and from there you can specify ~/users or whatever
it is you want to visit.


To interact with the server via command line you can use a linux utility called
curl, to see examples of this read curlExamples.md

If you want to add things to the database outside of the command line you can
edit the seeds.rb file found in config, and from there you can reseed the data
base using the rake commands above

To see a list of routes that you can travel to on the server type the following

rake routes
