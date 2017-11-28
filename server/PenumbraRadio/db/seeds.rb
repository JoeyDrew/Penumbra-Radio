# This file should contain all the record creation needed to seed the database with its default values.
# The data can then be loaded with the rake db:seed (or created alongside the db with db:setup).
#
# Examples:
#
#   cities = City.create([{ name: 'Chicago' }, { name: 'Copenhagen' }])
#   Mayor.create(name: 'Emanuel', city: cities.first)

u1 = User.create(email: 'user1@example.com', name: 'John')
u2 = User.create(email: 'user2@example.com', name: 'Doe')

l1 = u1.logins.create(deviceid: 'abc123', location: 'Texas', year: 2018, month: 11, day: 24, time: 12345)
l2 = u1.logins.create(deviceid: 'abc123', location: 'Texas', year: 2018, month: 11, day: 24, time: 67890)

l3 = u2.logins.create(deviceid: 'def345', location: 'New York', year: 2018, month: 10, day: 16, time: 12345)
l4 = u2.logins.create(deviceid: 'ghi678', location: 'New York', year: 2018, month: 10, day: 16, time: 28403)

s1 = Song.create(title: 'The Number of The Beast', artist: 'Iron Maiden', publisher: 'EMI', year: 1982, audioname: 'TheNumberOfTheBeast.mp3', artname: 'TheNumberOfTheBeast.jpg')
s2 = Song.create(title: 'Electric Eye', artist: 'Judas Priest', publisher: 'Columbia', year: 1982, audioname: 'ElectricEye.mp3', artname: 'ElectricEye.jpg')

r1 = u1.ratings.create(song: s1, rating: 10)
r2 = u1.ratings.create(song: s2, rating: 8)

r3 = u2.ratings.create(song: s1, rating: 6)
r4 = u2.ratings.create(song: s2, rating: 4)

n1 = Article.create(name: "First News Article", body: "This is the first news article that is seeded in the database")
n2 = Article.create(name: "Second News Article", body: "And this is the second")
