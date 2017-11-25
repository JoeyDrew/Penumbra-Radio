require 'test_helper'

class LoginsControllerTest < ActionController::TestCase
  setup do
    @login = logins(:one)
  end

  test "should get index" do
    get :index
    assert_response :success
    assert_not_nil assigns(:logins)
  end

  test "should create login" do
    assert_difference('Login.count') do
      post :create, login: { day: @login.day, deviceid: @login.deviceid, location: @login.location, month: @login.month, time: @login.time, user_id: @login.user_id, year: @login.year }
    end

    assert_response 201
  end

  test "should show login" do
    get :show, id: @login
    assert_response :success
  end

  test "should update login" do
    put :update, id: @login, login: { day: @login.day, deviceid: @login.deviceid, location: @login.location, month: @login.month, time: @login.time, user_id: @login.user_id, year: @login.year }
    assert_response 204
  end

  test "should destroy login" do
    assert_difference('Login.count', -1) do
      delete :destroy, id: @login
    end

    assert_response 204
  end
end
