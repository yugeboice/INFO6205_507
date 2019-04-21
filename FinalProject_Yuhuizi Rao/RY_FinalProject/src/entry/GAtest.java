package entry;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
//package pkg6205_final_project;
import org.junit.Test;
import java.util.*;
import static org.junit.Assert.assertEquals;

/**
 *
 * @author raoyuhuizi
 */

public class GAtest {//UnitTest
    @Test
    public void DataRangeCheck(){//Check if there any answer has an x or y bigger than 1023
        DataGenerator da = new DataGenerator();
        Genetic sol = new Genetic(da.DataReader());
        //Arrange
        sol.init();
        int flag=0;
        for(int j=0;j<1000;j++) {
            if((!(sol.sitelist[j]/10000 <1024))|| (!(sol.sitelist[j]%10000 <1024)))
                flag++;
        }
        //
        int expected=0;
        //
        assertEquals(expected,flag);

    }


    /**
     * This test is to check the crossover method
     */
    @Test
    public void CrossOverCheck(){//if the CrossOver function is right
        DataGenerator da = new DataGenerator();
        Genetic sol = new Genetic(da.DataReader());
        //Selection selection =new Selection(sol.sitelist, sol.buildingList, sol.rat, sol.scope);
        CrossOver crossOver = new CrossOver(sol.sitelist, sol.scope);
        int a=89,b=128; //a(binary)=01011001; b(binary)=10000000
        int[] re=crossOver.exchange(a,b,2,0);
        int r1=re[0]; // (binary)10000001=129  
        int r2=re[1]; //(binary)01011000=88
        System.out.println(" [0]"+r1);
        System.out.println(" [1]"+r2);
        assertEquals(132,r1);
        assertEquals(88,r2);

    }

    /**
     * This test is to check the mutation method
     */
    @Test
    public void MutationCheck(){//if the MutationCheck is right
        DataGenerator da = new DataGenerator();
        Genetic sol = new Genetic(da.DataReader());
        Mutation mutation = new Mutation(sol.sitelist, sol.scope);
        int a=67;//1000011
        int re=mutation.mutate(a,6);//0000011
        assertEquals(3,re);

    }


    /**
     * This test is to check the select process
     * After the select work, the less weight one individual has(which is better), the higher chosen-ratio it has.
     */
    @Test
    public void SelectCheck(){//Check if when the distance is bigger the possibility it will be chosen is smaller
        DataGenerator da = new DataGenerator();
        Genetic sol = new Genetic(da.DataReader());
        sol.init();
        double [] a=new double[1000];
        for(int k=0;k<1000;k++){
            a[k]=sol.FuncCalc(sol.sitelist[k]);
        }
        sol.SelectWork();
        int flag=0;
        if(a[0]<a[1]) {
        	if (sol.rat[0] < sol.rat[1]-sol.rat[0])
        		flag++;
        	
        }
        for(int i=2;i<1000;i++){
            if(a[i-1] < a[i] ){
            	double r1 = sol.rat[i-1] - sol.rat[i-2];
            	double r2 = sol.rat[i] - sol.rat[i-1];
                if(r1 > r2) continue;
                else { flag++;
                    System.out.println("s1"+" "+a[i-1]+" "+sol.rat[i-1]+" "+a[i]+" "+sol.rat[i]);
                    break; }
            }
            else if(a[i-1]>a[i] ){
            	double r1 = sol.rat[i-1] - sol.rat[i-2];
            	double r2 = sol.rat[i] - sol.rat[i-1];
                if(r1 < r2) continue;
                else {flag++;
                    System.out.println("s2"+" "+a[i-1]+" "+r1+" "+a[i]+" "+r2);
                    break;}
            }
        }
        assertEquals(0,flag,0);
    }


    /**
     * This method is to check whether the evolved solution group is better than the original one
     * Compare the average distance of original group and evolved group, the latter should be less
     */
    @Test
    public void Optimize() {//check if the final group better than the initial random group
        DataGenerator da = new DataGenerator();
        Genetic sol = new Genetic(da.DataReader());
        sol.init();
        double[] origin = new double[1000];
        double sum1 = 0, sum2 = 0;
        for (int i = 0; i < 1000; i++) {
            origin[i] = sol.FuncCalc(sol.sitelist[i]);
        }
        for (int j = 0; j < 1000; j++) {
            sum1 = sum1 + origin[j];
        }
        System.out.println("avg1 " + sum1/1000);

        sol.CalcWork();
        for (int k = 0; k < 1000; k++) {
            sum2 = sum2 + sol.FuncCalc(sol.sitelist[k]);
        }
        System.out.println("avg2 " + sum2/1000);
        int flag = 0;
        if ((sum2 / 1000) > (sum1 / 1000) ) flag = 1;
        assertEquals(0, flag);
    }


    /**
     * This method is to check whether the minimum solution in the evolved group
     * is less than the one in the original group
     */
    @Test
    public void AveMiniCheck(){//check if the final answer better than the initial answer
        DataGenerator da = new DataGenerator();
        Genetic sol = new Genetic(da.DataReader());
        sol.init();
        double [] origin= new double[1000];
        for(int i=0;i<1000;i++){
            origin[i]=sol.FuncCalc(sol.sitelist[i]);
        }
        Arrays.sort(origin);//ascending order
        double min1=(origin[0]+origin[1]+origin[2])/3;
        //double min1=origin[0];
       
        sol.CalcWork();
      
        double[] re=new double[1000];
        for(int k=0;k<1000;k++){
            re[k]=sol.FuncCalc(sol.sitelist[k]);
        }
        Arrays.sort(re);
        double min2=(re[0]+re[1]+re[2])/3;
       // double min2=re[0];

        int flag=0;
        if((min2-min1)>0) flag=1; //fail marked
        // else if(min2-min1<0 && ((min1-min2)/min1)<0.003)
        System.out.println(origin[0]+" "+origin[1]+" "+origin[2]);
        System.out.println(re[0]+" "+re[1]+" "+re[2]);
        assertEquals(0,flag);

    }

   

    /**
     * This method is to compare the ideal value and minimum value we got
     * the ideal value is the minimum value in the 1024*1024 data source
     * We take 0.002 as the rate of deviation
     */
    @Test
    public void EstimateSolution(){//Check if the final answer is close to the best answer
        DataGenerator da = new DataGenerator();
        Genetic sol = new Genetic(da.DataReader());
        sol.init();
        sol.CalcWork();
        double min1=2.0E11,min2=2.0E11;
        // the minimum we actually got
        for(int i=0;i<sol.sitelist.length;i++){
            double tmp1=sol.FuncCalc(sol.sitelist[i]);
            if (tmp1< min1)  min1 =tmp1;
        }
        System.out.println("actual min"+min1);

        // our goal minimum
        for(int j=0;j<1024;j++){
            for(int k=0;k<1024;k++){
                double tmp2=sol.FuncCalc(j*10000+k);
                if(tmp2<min2) min2=tmp2;
            }
        }
        System.out.println("goal min"+min2);
        //
        int flag=0;
        double a=Math.sqrt(min1);
        double b=Math.sqrt(min2);
        if(Math.abs(a-b)/a>0.002) flag=1; //failed marked
        assertEquals(0,flag);

    }

}

