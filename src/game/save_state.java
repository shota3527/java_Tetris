package game;

import java.security.cert.CollectionCertStoreParameters;
import java.util.ArrayList;
import java.util.Queue;

public class save_state {
	ArrayList<boolean[][]> savedstatus;
	ArrayList<int[][]> ColorStatus;
	ArrayList<Integer> blockstatus;
	save_state(int bw,int bh){
		 init();
	}
	public void init() {
		savedstatus = new ArrayList<boolean[][]>();
		ColorStatus = new ArrayList<int[][]>();
		blockstatus = new ArrayList<Integer>();		
	}
	public void saving_state(boolean[][] i_state,int[][] i_cstate,int blocktype) {
		boolean[][] tempstate = new boolean[game_mian.bw][game_mian.bh+1];
		int[][] tempcstate = new int[game_mian.bw][game_mian.bh+1];
		for(int i=0;i<game_mian.bw;i++) {
			for(int j=0;j<game_mian.bh+1;j++) {
				//System.out.println(savedstatus.get(savedstatus.size()-1)[i][j]);
				tempstate[i][j]=i_state[i][j];
				tempcstate[i][j]=i_cstate[i][j];
				//System.out.println(i_state[i][j]);
			}
		}
		savedstatus.add(tempstate);
		ColorStatus.add(tempcstate);
		blockstatus.add(blocktype);
		//System.out.println(savedstatus.get(savedstatus.size()-1)[0][0]);
		// TODO Auto-generated constructor stub
	}
	//public void load_state(boolean[][] i_state,int[][] i_cstate,int blocktype) {
	public void load_state(game_mian input) {
		//		i_state[0][0]=false;
		if(savedstatus.size()>1 && ColorStatus.size()>0 && blockstatus.size()>0) {
			for(int i=0;i<game_mian.bw;i++) {
				for(int j=0;j<game_mian.bh+1;j++) {
					//System.out.println(savedstatus.get(savedstatus.size()-1)[i][j]);
					input.Status[i][j]=savedstatus.get(savedstatus.size()-2)[i][j];
					input.ColorStatus[i][j]=ColorStatus.get(ColorStatus.size()-2)[i][j];
					//System.out.println(i_state[i][j]);
				}
			}
			input.currentblock=new Block(blockstatus.get(blockstatus.size()-2),game_mian.block,game_mian.bw/2-1,0);
			input.nextblock=new Block(blockstatus.get(blockstatus.size()-1),game_mian.block,game_mian.bw+3,2);
			savedstatus.remove(savedstatus.size()-1);
			ColorStatus.remove(ColorStatus.size()-1);
			blockstatus.remove(blockstatus.size()-1);
		}
	}


}
