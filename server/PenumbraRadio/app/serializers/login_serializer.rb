class LoginSerializer < ActiveModel::Serializer
  attributes :id, :deviceid, :location, :year, :month, :day, :time
  has_one :user
end
