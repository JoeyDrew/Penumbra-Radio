class UserSerializer < ActiveModel::Serializer
  attributes :id, :email, :name, :ratings
end
