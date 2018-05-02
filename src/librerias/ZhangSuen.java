package librerias;

import java.awt.Point;
import java.util.*;

import huellas.HuellaDactilar;
 
public class ZhangSuen {
  
    final static int[][] nbrs = {{0, -1}, {1, -1}, {1, 0}, {1, 1}, {0, 1}, {-1, 1}, {-1, 0}, {-1, -1}, {0, -1}};
 
    final static int[][][] nbrGroups = {{{0, 2, 4}, {2, 4, 6}}, {{0, 2, 6}, {0, 4, 6}}};
 
    static List<Point> toWhite = new ArrayList<>();
    static int[][] grid;
    
    public ZhangSuen(HuellaDactilar imagen){
	    	grid = new int[imagen.getWidth()][imagen.getHeight()];
	    	
	    	for (int i=0; i<imagen.getWidth(); i++){
	    		for (int j=0; j<imagen.getHeight(); j++){
	    			grid[i][j] = imagen.getPixel(i, j);
	    		}
	    	}
    }
 
    public HuellaDactilar thinImage() {
        boolean firstStep = false;
        boolean hasChanged;
 
        do {
            hasChanged = false;
            firstStep = !firstStep;
 
            for (int r = 1; r < grid.length - 1; r++) {
                for (int c = 1; c < grid[0].length - 1; c++) {
 
                    if (grid[r][c] != 0)
                        continue;
 
                    int nn = numNeighbors(r, c);
                    if (nn < 2 || nn > 6)
                        continue;
 
                    if (numTransitions(r, c) != 1)
                        continue;
 
                    if (!atLeastOneIsWhite(r, c, firstStep ? 0 : 1))
                        continue;
 
                    toWhite.add(new Point(c, r));
                    hasChanged = true;
                }
            }
 
            for (Point p : toWhite)
                grid[p.y][p.x] = 1;
            toWhite.clear();
 
        } while (firstStep || hasChanged);
        
        HuellaDactilar salida = new HuellaDactilar(grid.length, grid[0].length);
        
        for (int i=0; i<grid.length; i++){
	    		for (int j=0; j<grid[0].length; j++){
	    			salida.setPixel(i, j, grid[i][j]);;
	    		}
        }
        
        return salida;
    }
 
    static int numNeighbors(int r, int c) {
        int count = 0;
        for (int i = 0; i < nbrs.length - 1; i++)
            if (grid[r + nbrs[i][1]][c + nbrs[i][0]] == 0)
                count++;
        return count;
    }
 
    static int numTransitions(int r, int c) {
        int count = 0;
        for (int i = 0; i < nbrs.length - 1; i++)
            if (grid[r + nbrs[i][1]][c + nbrs[i][0]] == 1) {
                if (grid[r + nbrs[i + 1][1]][c + nbrs[i + 1][0]] == 0)
                    count++;
            }
        return count;
    }
 
    static boolean atLeastOneIsWhite(int r, int c, int step) {
        int count = 0;
        int[][] group = nbrGroups[step];
        for (int i = 0; i < 2; i++)
            for (int j = 0; j < group[i].length; j++) {
                int[] nbr = nbrs[group[i][j]];
                if (grid[r + nbr[1]][c + nbr[0]] == 1) {
                    count++;
                    break;
                }
            }
        return count > 1;
    }

}