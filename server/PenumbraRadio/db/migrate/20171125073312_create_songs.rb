class CreateSongs < ActiveRecord::Migration
  def change
    create_table :songs do |t|
      t.string :title
      t.string :artist
      t.string :publisher
      t.integer :year
      t.string :audioname
		t.string :artname

      t.timestamps null: false
    end
  end
end
