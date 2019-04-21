package entry;

import java.util.Random;

public class Mutation {
	private int[] initialGroup;
	private int scope;
	public Mutation(int[] initialGroup,int scope) {
		this.initialGroup = initialGroup;
		this.scope = scope;
		  Random x = new Random();
	        for (int i = 0 ; i<scope; i++){
	            int tmploc = x.nextInt(20);
	            int tmpvar = x.nextInt(10);
	            if (tmpvar == 1){
	                int tx = initialGroup[i]%10000;
	                int ty = initialGroup[i]/10000;
	                if (tmploc < 10){
	                    tx =  mutate(tx,tmploc);
	                    initialGroup[i] = ty * 10000 + tx;
	                }
	                else{
	                    ty =  mutate(tx,tmploc-10);
	                    initialGroup[i] = ty * 10000 + tx;
	                }
	            }
	        }
	}

	 public int mutate(int x,int loc){// the function use to take the inverse
		    
	    	return x^(1<<loc);
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
