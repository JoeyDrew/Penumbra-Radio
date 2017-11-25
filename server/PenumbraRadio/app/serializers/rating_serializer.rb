class RatingSerializer < ActiveModel::Serializer
  attributes :id, :rating
  has_one :user
  has_one :song
end
