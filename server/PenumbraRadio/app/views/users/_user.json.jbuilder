json.extract! user, :id, :email, :name, :auth_token, :created_at, :updated_at
json.url user_url(user, format: :json)
