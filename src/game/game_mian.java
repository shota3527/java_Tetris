package game;



import java.applet.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Random;

public class game_mian extends Applet implements Runnable, KeyListener {
    public static int bw,bh;
    public int LOWSPEED=800,HIGHSPEED=100;
    public int SPEED=LOWSPEED;
    public static int block=10;
    public static int width=100,height=200;
    public static int margin=10;
    Random rnd = new Random();	
    public static boolean[][] Status;
    public static int[][] ColorStatus;
    public Graphics offG;
    public Image img;
    private Thread th;
	Block currentblock;
	Block nextblock;
	public static int next_pattern;
	public static int score=0;
	public boolean game_over;
    
    public void init() {
        setSize(200,270);
        setLayout(null);
        img = createImage(width+300,height+block*4);
        offG = img.getGraphics();
        addKeyListener(this);
        requestFocus();
        bw=width/block;
        bh=height/block+4;
        rotation_table.generate_table();
        Status = new boolean[bh][bh+1];
        ColorStatus = new int[bh][bh+1];
    }
    
    public void start() {
        if(th==null) {
            th = new Thread(this);
            th.start();
        }
    }
    public void paint_field() {
        for(int i=0;i<bw;i++) {
            for(int j=0;j<bh;j++) {
                if(Status[i][j]==true) {
                    offG.setColor(Clr.clr[ColorStatus[i][j]]);
                    offG.fillRect(i*block,j*block,block,block);
                    offG.setColor(Color.black);
                    offG.drawRect(i*block,j*block,block,block);
                }
            }
        }
    }
    public void paint(Graphics G) {
        offG.clearRect(0,0,width+300,height);
        offG.setColor(Color.gray);
        offG.fillRect(0,block*4,width,height);
        paint_field();
        currentblock.polygon(offG);
        nextblock.polygon(offG);
        G.drawImage(img,margin,margin,this);
        G.drawString("Next", 120, 30);
        G.drawString("Score:", 120, 150);
        G.drawString(String.valueOf(score), 120, 180);
        if(game_over) G.drawString("Game Over", 120, 100);
    }
    
    public void update(Graphics g) {
        paint(g);
    }
    
    
    public void fadeblock() {
    	int combo=1000;
    	for(int j=0;j<bh;j++) {
    		boolean exist=true;
    		for(int i=0;i<bw;i++) {
    			exist&=Status[i][j];
    		}
    		if(exist) {
    			score+=combo;
    			combo+=1000;
    			for(int jj=j;jj>0;jj--) {
    				for(int ii=0;ii<bw;ii++) {
    					Status[ii][jj]=Status[ii][jj-1];
    					ColorStatus[ii][jj]=ColorStatus[ii][jj-1];
    					Status[ii][0]=false;
    				}
    			}
    			repaint();
    		}
    	}
    }
    public void generateblock() {
    	currentblock=new Block(next_pattern, block,bw/2-1,0);
    	next_pattern=rnd.nextInt(7);
    	nextblock = new Block(next_pattern, block, bw+3,2);
    	repaint();
    }
    public void restart(){
    	next_pattern=rnd.nextInt(7);
    	game_over=false;
        for(int i=0;i<bw;i++) {
            for(int j=0;j<bh;j++) {
                Status[i][j] = false;
            }
            Status[i][bh] = true;
        }
        score=0;
        generateblock(); 
    }
    public void run() {
    	restart();
        while(th == Thread.currentThread()) {
            try {
                Thread.sleep((long)(SPEED*(5000/(5000+score*0.5))));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            repaint();
            if(!game_over) currentblock.yy += 1;
            if(currentblock.collision_detect_bottom(currentblock.Status)) {
            	currentblock.freezeblock();
        		for(int i=0;i<bw;i++) {
        			for(int j=0;j<4;j++) {
        				if(Status[i][j]) {
        					game_over=true;
        					repaint();
        				}
        			}
        		}
        		generateblock();
            	fadeblock();
            }
        }
    }
    
    public void keyPressed(KeyEvent e) {
        if(e.getKeyCode()==KeyEvent.VK_LEFT) {
            currentblock.move_to_side(-1);
                repaint();
        }
        
        if(e.getKeyCode() == KeyEvent.VK_RIGHT) {
        	currentblock.move_to_side(1);
            repaint();
        }
        
        if(e.getKeyCode() == KeyEvent.VK_DOWN) {
            SPEED = HIGHSPEED;
        }
        if(e.getKeyCode() == KeyEvent.VK_UP) {
            currentblock.rotate();
            repaint();
        }
        if(e.getKeyCode() ==  KeyEvent.VK_F4&&game_over) {
            restart();
        	repaint();
        }
    }
    
    public void keyReleased(KeyEvent e) {
        if(e.getKeyCode() == KeyEvent.VK_DOWN) {
            SPEED = LOWSPEED;
        }
    }
    
    public void keyTyped(KeyEvent e) {
    }
}