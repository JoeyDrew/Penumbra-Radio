class CreateLogins < ActiveRecord::Migration
  def change
    create_table :logins do |t|
      t.string :deviceid
      t.string :location
      t.integer :year
      t.integer :month
      t.integer :day
      t.integer :time
      t.references :user, index: true, foreign_key: true

      t.timestamps null: false
    end
  end
end
