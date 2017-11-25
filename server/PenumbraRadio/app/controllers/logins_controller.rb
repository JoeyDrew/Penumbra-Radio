class LoginsController < ApplicationController
  before_action :set_login, only: [:show, :update, :destroy]

  # GET /logins
  # GET /logins.json
  def index
    @logins = Login.all

    render json: @logins
  end

  # GET /logins/1
  # GET /logins/1.json
  def show
    render json: @login
  end

  # POST /logins
  # POST /logins.json
  def create
    @login = Login.new(login_params)

    if @login.save
      render json: @login, status: :created, location: @login
    else
      render json: @login.errors, status: :unprocessable_entity
    end
  end

  # PATCH/PUT /logins/1
  # PATCH/PUT /logins/1.json
  def update
    if @login.update(login_params)
      head :no_content
    else
      render json: @login.errors, status: :unprocessable_entity
    end
  end

  # DELETE /logins/1
  # DELETE /logins/1.json
  def destroy
    @login.destroy

    head :no_content
  end

  private

    def set_login
      @login = Login.find(params[:id])
    end

    def login_params
      params.require(:login).permit(:deviceid, :location, :year, :month, :day, :time, :user_id)
    end
end
