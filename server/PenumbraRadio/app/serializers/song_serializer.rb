class SongSerializer < ActiveModel::Serializer
  attributes :id, :title, :artist, :publisher, :year, :path
end
