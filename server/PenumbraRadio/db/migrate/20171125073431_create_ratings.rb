class CreateRatings < ActiveRecord::Migration
  def change
    create_table :ratings do |t|
      t.references :user, index: true, foreign_key: true
      t.references :song, index: true, foreign_key: true
      t.integer :rating, limit: 10

      t.timestamps null: false
    end
  end
end
