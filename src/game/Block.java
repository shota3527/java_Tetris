package game;

import java.awt.*;

public class Block {
	public int xx,yy;
	public int pattern;
	public int block;
	public boolean[][] Status;


	public Block(int i_pattern,int i_block,int i_xx,int i_yy) {
		pattern=i_pattern;
		block=i_block;
		xx=i_xx;
		yy=i_yy;
		Status = new boolean[4][4];
		for(int i=0;i<4;i++) {
			for(int j=0;j<4;j++) {
				Status[i][j] = false;
			}
		}
		switch(pattern) {
		case 0:
			Status[0][1]=true;
			Status[1][1]=true;
			Status[2][1]=true;
			Status[3][1]=true;
			break;
		case 1:
			Status[1][1]=true;
			Status[1][2]=true;
			Status[2][2]=true;
			Status[3][2]=true;
			break;
		case 2:
			Status[3][1]=true;
			Status[1][2]=true;
			Status[2][2]=true;
			Status[3][2]=true;
			break;
		case 3:
			Status[1][1]=true;
			Status[1][2]=true;
			Status[2][1]=true;
			Status[2][2]=true;
			break;
		case 4:
			Status[1][1]=true;
			Status[2][1]=true;
			Status[0][2]=true;
			Status[1][2]=true;
			break;
		case 5:
			Status[1][1]=true;
			Status[0][2]=true;
			Status[1][2]=true;
			Status[2][2]=true;
			break;
		case 6:
			Status[0][1]=true;
			Status[1][1]=true;
			Status[1][2]=true;
			Status[2][2]=true;
			break;
		}
	}
	public void rotate() {
		boolean[][] temp_Status;
		temp_Status=new boolean[4][4];
		for(int i=0;i<4;i++) {
			for(int j=0;j<4;j++) {
				temp_Status[rotation_table.x[i][j]][rotation_table.y[i][j]] = Status[i][j];
			}
		}
		if(collision_detect_side(0,temp_Status)==0){
			if(!collision_detect_bottom(temp_Status)){
				for(int i=0;i<4;i++) {
					for(int j=0;j<4;j++) {
						Status[i][j]=temp_Status[i][j];
					}
				}
			}
		}
	}
	public void polygon(Graphics offG) {
		for(int i=0;i<4;i++) {
			for(int j=0;j<4;j++) {
				if(Status[i][j]) {
					offG.setColor(Clr.clr[pattern]);
					offG.fillRect((xx+i)*block,(yy+j)*block,block,block);

					offG.setColor(Clr.clr[7]);
					offG.drawRect((xx+i)*block,(yy+j)*block,block,block);
				}
			}
		}
	}
	public boolean collision_detect_bottom(boolean[][] l_Status) {
		boolean collision=false;
		for(int i=0;i<4;i++) {
			for(int j=0;j<4;j++) {
				if(l_Status[i][j]&&game_mian.Status[xx+i][yy+j]) {
					collision=true;
				}
			}
		}
		return collision;
	}
	public int collision_detect_side(int direction,boolean[][] l_Status) {
		int ret=0;
		//0 is no collision;-1 is left;1 is right
		for(int i=0;i<4;i++) {
			for(int j=0;j<4;j++) {
				if(l_Status[i][j]&&ret==0) {
					if((direction+xx+i)<0||(direction+xx+i)>=game_mian.bw) ret=1;
					else if(l_Status[i][j]&&game_mian.Status[direction+xx+i][yy+j]) ret=1;
				}
			}
		}
		return ret;
	}
	public void freezeblock() {
		yy-=1;
		for(int i=0;i<4;i++) {
			for(int j=0;j<4;j++) {
				if(Status[i][j]) {
					game_mian.Status[xx+i][yy+j]=true;
					game_mian.ColorStatus[xx+i][yy+j]=pattern;
				}
			}
		}
	}
	public void move_to_side(int direction) {
		if(collision_detect_side(direction,Status)==0) xx+=direction;
	}
}