package fit.george.sp1.draft;

import org.andengine.entity.primitive.Line;
import org.andengine.entity.scene.CameraScene;
import org.andengine.entity.sprite.Sprite;
import org.andengine.input.touch.TouchEvent;
import org.andengine.util.color.Color;



import android.util.Log;



public class game_Scene extends CameraScene {
	private Sprite [] road, tree1, tree2, tree3;
	private Sprite castle, gen;
	
	private Matrix matrix;
	private Tower [] towers;
	private Money money;
	private int count = 0; //number of builded towers
	private final static int TOWERS_NUM = 20; //max number of towers in a game
	private float x, y;
	public static game_Scene game_instance;
	
	
	@Override
	public boolean onSceneTouchEvent(TouchEvent pSceneTouchEvent) {
		
		
		
		if (pSceneTouchEvent.isActionDown() && count < TOWERS_NUM) {
            
		
			y = pSceneTouchEvent.getY(); 
			x = pSceneTouchEvent.getX(); 
			
			
			
			if(matrix.GetValue(((int) (y / 60)) + 1, ((int) (x / 60)) + 1) == 0 && (money.GetMoney() - Tower.GetPrice()) >= 0) {
			
				towers[count] = new Tower(x - (x % 60), y - (y % 60),  MainActivity.instance.dragon, MainActivity.instance.getVertexBufferObjectManager());
				attachChild(towers[count]);
				towers[count].StartFire();
				money.DeductMoney(Tower.GetPrice());
				count++;
					
				matrix.SetValue(((int) (y / 60)) + 1, ((int) (x / 60)) + 1, Matrix.TOWER);
				
				matrix.print();
				Log.d("Index", Integer.toString(count));
				
			}
			
			
        }
		
		
		return super.onSceneTouchEvent(pSceneTouchEvent);
	}
	
	
	public game_Scene() {
		super(MainActivity.camera);
		game_instance = this;
		setBackground(MainActivity.instance.mGrassBackground);
		
		matrix = new Matrix();
		towers = new Tower[TOWERS_NUM];
		money = new Money(1000);
		
		
		/*
		final Line [] line_vert = new Line[15];
		final Line [] line_horiz = new Line[10];
		
		for(int i = 1; i <= 13; i++) {
			line_vert[i-1] = new Line(i * 60, 0, i * 60, 480, 1, MainActivity.instance.getVertexBufferObjectManager());
			line_vert[i-1].setColor(Color.WHITE);
			attachChild(line_vert[i-1]);
		}
		
		for(int i = 1; i < 8; i++) {
			line_horiz[i-1] = new Line(0, i * 60, 800, i * 60, 1, MainActivity.instance.getVertexBufferObjectManager());
			line_horiz[i-1].setColor(Color.WHITE);
			attachChild(line_horiz[i-1]);
		}
		
		*/
		loadMap();
		
	}
	
private void loadMap() {		
		
		road = new Sprite[ matrix.GetCountOfRoad() ];
		tree1 = new Sprite[ matrix.GetCountOfTree1() ];
		tree2 = new Sprite[ matrix.GetCountOfTree2() ];
		tree3 = new Sprite[ matrix.GetCountOfTree3() ];
		int count_road = 0, count_tree1 = 0, count_tree2 = 0, count_tree3 = 0;
		
		
		for ( int i = 1; i < 9; i++)
			for ( int j = 1; j < 14; j++) {
				if ( matrix.GetValue(i, j) == Constants.ROAD ) {
					road[count_road] = new Sprite((j-1)*60 , (i-1)*60 , MainActivity.instance.mTextureRegionRoad, MainActivity.instance.getVertexBufferObjectManager());
					attachChild( road[count_road++]);
				}
				else if ( matrix.GetValue(i, j) == Constants.CASTLE )  {
					castle = new Sprite((j-1)*60 , (i-1)*60 , MainActivity.instance.mTextureRegionCastle, MainActivity.instance.getVertexBufferObjectManager());
					attachChild( castle );
				}
				
				else if ( matrix.GetValue(i, j) == Constants.NPC_GENERATOR )  {
					gen = new Sprite((j-1)*60 , (i-1)*60 , MainActivity.instance.mTextureRegionGen, MainActivity.instance.getVertexBufferObjectManager());
					attachChild( gen );
				}
				
				else if ( matrix.GetValue(i, j) == Constants.LANDSCAPE_TREE_1 ) {
					tree1[count_tree1] = new Sprite((j-1)*60 , (i-1)*60 , MainActivity.instance.mTextureRegionTree1, MainActivity.instance.getVertexBufferObjectManager());
					attachChild( tree1[count_tree1++]);
				}
				
				else if ( matrix.GetValue(i, j) == Constants.LANDSCAPE_TREE_2 ) {
					tree2[count_tree2] = new Sprite((j-1)*60 , (i-1)*60 , MainActivity.instance.mTextureRegionTree2, MainActivity.instance.getVertexBufferObjectManager());
					attachChild( tree2[count_tree2++]);
				}
				
				else if ( matrix.GetValue(i, j) == Constants.LANDSCAPE_TREE_3 ) {
					tree3[count_tree3] = new Sprite((j-1)*60 , (i-1)*60 , MainActivity.instance.mTextureRegionTree3, MainActivity.instance.getVertexBufferObjectManager());
					attachChild( tree3[count_tree3++]);
				}
			}
		
		
		
	}
	
	
	
	
	public void Show(){
		setVisible(true);
    	setIgnoreUpdate(false);
//		
	}
	
    public void Hide(){
    	setVisible(false);
    	setIgnoreUpdate(true);
//		
	}
}
