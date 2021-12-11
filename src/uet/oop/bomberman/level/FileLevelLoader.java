package uet.oop.bomberman.level;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

import uet.oop.bomberman.Board;
import uet.oop.bomberman.Game;
import uet.oop.bomberman.entities.LayeredEntity;
import uet.oop.bomberman.entities.character.Bomber;
import uet.oop.bomberman.entities.character.enemy.Balloon;
import uet.oop.bomberman.entities.character.enemy.Oneal;
import uet.oop.bomberman.entities.tile.Grass;
import uet.oop.bomberman.entities.tile.Portal;
import uet.oop.bomberman.entities.tile.Wall;
import uet.oop.bomberman.entities.tile.destroyable.Brick;
import uet.oop.bomberman.entities.tile.item.BombItem;
import uet.oop.bomberman.entities.tile.item.FlameItem;
import uet.oop.bomberman.entities.tile.item.SpeedItem;
import uet.oop.bomberman.exceptions.LoadLevelException;
import uet.oop.bomberman.graphics.Screen;
import uet.oop.bomberman.graphics.Sprite;

public class FileLevelLoader extends LevelLoader {

	/**
	 * Ma trận chứa thông tin bản đồ, mỗi phần tử lưu giá trị kí tự đọc được
	 * từ ma trận bản đồ trong tệp cấu hình
	 */
	private static char[][] _map;
	
	public FileLevelLoader(Board board, int level) throws LoadLevelException {
		super(board, level);
	}
	
	@Override
	public void loadLevel(int level) throws LoadLevelException {
		// TODO: đọc dữ liệu từ tệp cấu hình /levels/Level{level}.txt
		String text = "";
		try{
			FileReader file = new FileReader("res\\levels\\Level" + level + ".txt");
			BufferedReader br = new BufferedReader(file);
			String line = br.readLine();
			while(line != null) {
				text = text + line + "\n";
				line = br.readLine();
			}

			br.close();
			file.close();
		}
		catch (IOException e) {
			throw new LoadLevelException();
		}
		// TODO: cập nhật các giá trị đọc được vào _width, _height, _level, _map
		Scanner scanner = new Scanner(text);
		_level = scanner.nextInt();
		_height = scanner.nextInt();
		_width = scanner.nextInt();
		String tmp = scanner.nextLine();
		_map = new char[_height][_width];
		try {
			for(int i = 0; i< _height; i++) {
				tmp = scanner.nextLine();
				for(int j = 0; j < _width; j++){
					_map[i][j] = tmp.charAt(j);
				}
			}
		} catch (Exception e) {
			System.out.println("Level format is incorrect");
		}
		scanner.close();

	}

	@Override
	public void createEntities() {
		// TODO: tạo các Entity của màn chơi
		// TODO: sau khi tạo xong, gọi _board.addEntity() để thêm Entity vào game
		for (int i = 0; i < _height; i++) {
			for (int j = 0; j < _width; j++) {
				createLevelEntities(_map[i][j],j,i);          
			}
			
		}
		// TODO: phần code mẫu ở dưới để hướng dẫn cách thêm các loại Entity vào game
		// TODO: hãy xóa nó khi hoàn thành chức năng load màn chơi từ tệp cấu hình
		/*// thêm Wall
		for (int x = 0; x < 20; x++) {
			for (int y = 0; y < 20; y++) {
				int pos = x + y * _width;
				Sprite sprite = y == 0 || x == 0 || x == 10 || y == 10 ? Sprite.wall : Sprite.grass;
				_board.addEntity(pos, new Grass(x, y, sprite));
			}
		}

		// thêm Bomber
		int xBomber = 1, yBomber = 1;
		_board.addCharacter( new Bomber(Coordinates.tileToPixel(xBomber), Coordinates.tileToPixel(yBomber) + Game.TILES_SIZE, _board) );
		Screen.setOffset(0, 0);
		_board.addEntity(xBomber + yBomber * _width, new Grass(xBomber, yBomber, Sprite.grass));

		// thêm Enemy
		int xE = 2, yE = 1;
		_board.addCharacter( new Balloon(Coordinates.tileToPixel(xE), Coordinates.tileToPixel(yE) + Game.TILES_SIZE, _board));
		_board.addEntity(xE + yE * _width, new Grass(xE, yE, Sprite.grass));

		// thêm Brick
		int xB = 3, yB = 1;
		_board.addEntity(xB + yB * _width,
				new LayeredEntity(xB, yB,
					new Grass(xB, yB, Sprite.grass),
					new Brick(xB, yB, Sprite.brick)
				)
		);

		// thêm Item kèm Brick che phủ ở trên
		int xI = 1, yI = 2;
		_board.addEntity(xI + yI * _width,
				new LayeredEntity(xI, yI,
					new Grass(xI ,yI, Sprite.grass),
					new SpeedItem(xI, yI, Sprite.powerup_flames),
					new Brick(xI, yI, Sprite.brick)
				)
		);*/
	}

	public  void createLevelEntities(char c,int x,int y){
		int pos = x + y * _width;// Tinh toan vi tri trong board
	 
		switch(c) {
			case '#': //Wall
				_board.addEntity(pos, new Wall(x, y, Sprite.wall));  
				break;
			case 'b': // BombTitem
				_board.addEntity(x + y * _width,
				new LayeredEntity(x, y,
					new Grass(x ,y, Sprite.grass),
					new BombItem(x, y, Sprite.powerup_bombs),
					new Brick(x, y, Sprite.brick)
				)
				);
				break;
			case 's':// SpeedItem
				_board.addEntity(x + y * _width,
				new LayeredEntity(x, y,
					new Grass(x ,y, Sprite.grass),
					new SpeedItem(x, y, Sprite.powerup_speed),
					new Brick(x, y, Sprite.brick)
				)
				);
				break;
			case 'f': // FlameItem
				_board.addEntity(x + y * _width,
				new LayeredEntity(x, y,
					new Grass(x ,y, Sprite.grass),
					new FlameItem(x, y, Sprite.powerup_flames),
					new Brick(x, y, Sprite.brick)
					)
				);
				break;
			case '*': // brick
				_board.addEntity(pos, new LayeredEntity(x, y, 
					new Grass(x ,y, Sprite.grass), 
					new Brick(x ,y, Sprite.brick)) );
				break;
			case 'x': // Portal
				_board.addEntity(pos, new LayeredEntity(x, y, 
					new Grass(x ,y, Sprite.grass), 
					new Portal(x ,y,_board, Sprite.portal), 
					new Brick(x ,y, Sprite.brick)) );
				break;
			case ' ': // Grass
				_board.addEntity(pos, new Grass(x, y, Sprite.grass) );
				break;
			case 'p': // Boomber
				_board.addCharacter( new Bomber(Coordinates.tileToPixel(x), Coordinates.tileToPixel(y) + Game.TILES_SIZE, _board) );
				Screen.setOffset(0, 0);
				
				_board.addEntity(pos, new Grass(x, y, Sprite.grass) );
				break;
			//Enemies
			case '1': // Balloon
				_board.addCharacter( new Balloon(Coordinates.tileToPixel(x), Coordinates.tileToPixel(y) + Game.TILES_SIZE, _board));
				_board.addEntity(x + y * _width, new Grass(x, y, Sprite.grass));
				break;
			case '2': // Oneal
				_board.addCharacter( new Oneal(Coordinates.tileToPixel(x), Coordinates.tileToPixel(y) + Game.TILES_SIZE, _board));
				_board.addEntity(x + y * _width, new Grass(x, y, Sprite.grass));
				break;
			
			default: 
				_board.addEntity(pos, new Grass(x, y, Sprite.grass) );
				break;
			}
	}

}
