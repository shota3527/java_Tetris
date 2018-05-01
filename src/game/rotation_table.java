package game;

public class rotation_table {
	static int[][] x;
	static int[][] y;
	public static void generate_table() {
		x=new int[4][4];
		y=new int[4][4];
        for(int i=0;i<4;i++) {
            for(int j=0;j<4;j++) {
                x[i][j]=3-j;
                y[i][j]=i;
            }
        }
	}
			

}
