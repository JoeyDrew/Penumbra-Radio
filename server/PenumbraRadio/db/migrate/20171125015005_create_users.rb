class CreateUsers < ActiveRecord::Migration
  def change
    create_table :users do |t|
      t.string :email
      t.string :name
      t.string :auth_token

		t.references :rating, index: true, foreign_key: true

      t.timestamps null: false
    end
  end
end
