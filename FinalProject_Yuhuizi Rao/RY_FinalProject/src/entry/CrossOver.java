package entry;

import java.util.Random;

public class CrossOver {
	private int[] initialGroup;
	private int scope;
	public CrossOver(int[] initialGroup,int scope) {
		this.initialGroup = initialGroup;
		this.scope = scope;
		 Random x = new Random();
		 for (int i = scope-1; i>0; i--)//shuffle make i and i+1 Random pairing
		 {
		     int tmp = x.nextInt(i+1);
		     int tmp2 = initialGroup[i];
		     initialGroup[i] = initialGroup[tmp];
		     initialGroup[tmp] = tmp2;
		 }
		
		 for (int i = 0 ; i<scope; i+=2){//take the i and i+1 as a pair change one of their genus randomly
		    //randomly pick a reference bit 
		 	int tmpx = x.nextInt(10);
		 	int tmpy = x.nextInt(10);
		     int k = x.nextInt(2);
		     int[] p = new int[2];
		     if (k == 1) {
		         int tmpx1 = initialGroup[i]%10000;
		         int tmpx2 = initialGroup[i+1]%10000;
		         
		         if (tmpx >= tmpy)
		         p=exchange(tmpx1,tmpx2,tmpx,tmpy);
		         else
		 		p=exchange(tmpx1,tmpx2,tmpy,tmpx);
		         initialGroup[i] = (initialGroup[i]/10000)*10000 + p[1];
		         initialGroup[i+1] = (initialGroup[i+1]/10000)*10000 + p[0];
		
		     }
		     if (k == 0) {
		         int tmpx1 = initialGroup[i]/10000;
		         int tmpx2 = initialGroup[i+1]/10000;
		        
		         if (tmpx >= tmpy)
		         	 p=exchange(tmpx1,tmpx2,tmpx,tmpy);
		         else
		         	 p=exchange(tmpx1,tmpx2,tmpy,tmpx);
		         initialGroup[i] = (initialGroup[i]%10000) + p[1] * 10000;
		         initialGroup[i+1] = (initialGroup[i+1]%10000) + p[0] * 10000;
		
		     }
     }
}
	/**
     *parameter a and b is individual
     */
    public int[] exchange(int a,int b,int x,int y){//change the genus, x must >= y
    	//cut off and remain the value after refer bit + 
    	//cut off and remain value before the refer bit +
    	//the refer bit's value
    	int diff = x-y;
    	// change 1 refer bit of a
        int tmpx11 = (b % (1<<x)) + ((b / (1<<(x+1))) * (1<<(x+1))) + ((a & (1<<y))<<diff);
        // change 2 refer bit of a
        int tmpx12 = (tmpx11 % (1<<y)) + ((tmpx11 / (1<<(y+1))) * (1<<(y+1))) + ((a & (1<<x))>>diff);
        // change 1 refer bit of b
        int tmpx21 = (a % (1<<x)) + ((a / (1<<(x+1))) * (1<<(x+1))) + ((b & (1<<y))<<diff);
        // change 1 refer bit of b
        int tmpx22 = (tmpx21 % (1<<y)) + ((tmpx21 / (1<<(y+1))) * (1<<(y+1))) + ((tmpx21 & (1<<x))>>diff);
        int[] re=new int[2];
        re[0]=tmpx12;
        re[1]=tmpx22;
        return re;
        //  System.out.println(initialGroup[i] + " " + initialGroup[i+1] + " " + a + " " + b + " " + tmpx11 + " " + tmpx22 + " " + bit);
    }
	public int[] getInitialGroup() {
		return initialGroup;
	}
	public void setInitialGroup(int[] initialGroup) {
		this.initialGroup = initialGroup;
	}
	public int getScope() {
		return scope;
	}
	public void setScope(int scope) {
		this.scope = scope;
	}
    
}
