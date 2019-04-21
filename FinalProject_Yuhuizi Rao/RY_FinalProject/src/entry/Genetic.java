package entry;
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
//package pkg6205_final_project;

import java.time.Year;
import java.util.Random;

/**
 *
 */
public class Genetic {
	//initial 1000 points
	CrossOver crossOver;
	Mutation mutation;
    int[] sitelist = new int[1000];
    //first 10 selected buildings 
    int[] buildingList = new int[10];
    double[] rat = new double[1000];
   
    int scope = 1000;
    int [] f= new int[10];
    public Genetic(int [] a){
        for (int i = 0; i < 10; i++)
            buildingList[i] = a[i];
       
    }

    public void init(){//random 1000 initial individual

        int[] a = new int [1<<20];
        for (int i = 0 ; i<1024 ; i ++)
            for (int j = 0; j<1024; j++)
                a[i*1024+j] = i*10000+j;

        Random x = new Random();
// f[i] is the random constant C1;
        for (int i = 0 ; i < 10; i++)
            f[i] = x.nextInt(1024);
//shuffle
        for (int i = (1<<20) - 1 ; i>0; i --){
            int tmp = x.nextInt(i+1);
            int tmp2 = a[i];
            a[i] = a[tmp];
            a[tmp] =tmp2;
        }
        //pick 1000 position
        for (int i = 0 ; i<scope ; i ++){
            sitelist[i] = a[i];
        }
        System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
        for (int i = 0 ; i < 1000; i++)
        	//Decode points
            System.out.println(sitelist[i]/10000+" "+sitelist[i]%10000+" "+FuncCalc(sitelist[i]));
        //debug();
    }

    public void CalcWork(){//repeat  evolving 100000 times.
        for (int ci = 0; ci<100000; ci ++){
            SelectWork();
            
            crossOver = new CrossOver(sitelist, scope);
            sitelist = crossOver.getInitialGroup();
            scope = crossOver.getScope();
            
            mutation = new Mutation(sitelist, scope);
            scope = mutation.getScope();
            sitelist = mutation.getInitialGroup();
            
        }
        SelectWork();
    }

    /**
     *  Natural selection process select another 1000 individual based on each's possibility.
     *  the Specific probability calculation method is described at the report.
     */
    public void SelectWork(){
        ratfunc(rat);

        int[] tmpAnswerList = new int[scope];
        for(int i = 0; i < scope; i++) {
        	Random random = new Random();
        	double prob = random.nextDouble();
        	int choose;
        	for(choose = 1; choose < rat.length-1; choose++) {
        		if(prob < rat[choose])
        			break;
        	}
        	
        	tmpAnswerList[i] = sitelist[choose];
        	
        }	
        for(int i = 0; i < scope; i++){
        	sitelist[i] = tmpAnswerList[i];
        }

        
    }

    /**
     *  parameter double[] r is the array of initial ratio
     *  return an arrary after we computing the fitness as ratio2
     */
    public double[] ratfunc(double[] r){//possibility calculation
        double sumrat=0;
        for (int i = 0 ; i<r.length; i++){ //calculate distance from points to points
            r[i] = FuncCalc(sitelist[i]); // every total distance from target points to other 10 points
            sumrat += r[i];
        }
        double sumrat2 = 0;

        for (int i = 0 ; i<r.length; i++){
            r[i] = 1.0/(rat[i]/sumrat); //ratio1
            sumrat2 += rat[i];
        }
        for (int i = 0; i<r.length; i++)
            r[i] = r[i]/sumrat2; //ratio2
        // Calculate the accumulated rate
        for (int i = 1; i < r.length; i++) {
        	r[i] = r[i-1] + r[i];
        }
        return r;
    }



    //calculate S(D) 
    public double fx(double x, double constant){//Default function of distance and weight
//        Random c = new Random();
//    	
//        return 100*x*(x-constant)*+c.nextInt(1000)+ 86*x;
        
    	
        return (x-constant)*(x-constant)+1000+ 86*x;

    }
    public double FuncCalc(int x){//value calculation
        double sum = 0;
        for (int i = 0; i <10; i++){
            sum += fx( Math.sqrt(
            		1.0*( (x/10000) - (buildingList[i]/10000) ) * ( (x/10000) - (buildingList[i]/10000) ) + 
            		1.0*( (x%10000) - (buildingList[i]%10000) ) * ( (x%10000) - (buildingList[i]%10000) )
            		),f[i]);
        }
        
        return 100*sum;
    }

    char[][] s = new char[1024][1024];

    public void PrintWork(){//Print the related data

        System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");


        /*for (int i = 0;i<1024;i++,System.out.println())
            for (int j =0 ; j<1024;j++)
                System.out.print(FuncCalc(i*10000+j)+" ");

        System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");*/

        for (int i = 0 ; i<1000; i++)
            System.out.println(sitelist[i]/10000 +  " " + sitelist[i]%10000 + " " + FuncCalc(sitelist[i]));
    }


    public void debug(){
        for (int i = 0; i<scope; i++)
            System.out.println(sitelist[i]);
    }

}
