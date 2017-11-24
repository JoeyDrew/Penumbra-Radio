json.extract! login, :id, :deviceid, :location, :year, :month, :day, :time, :user_id, :created_at, :updated_at
json.url login_url(login, format: :json)
