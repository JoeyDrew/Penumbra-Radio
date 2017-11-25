class User < ActiveRecord::Base
	has_many :logins
   has_many :ratings
end
