class SongSerializer < ActiveModel::Serializer
  attributes :id, :title, :artist, :publisher, :year, :audioname, :artname
end
