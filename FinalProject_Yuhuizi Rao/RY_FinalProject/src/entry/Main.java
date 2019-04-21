package entry;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
//package pkg6205_final_project;

/**
 *
 * @author raoyuhuizi
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        DataGenerator da = new DataGenerator();
        Genetic sol = new Genetic(da.DataReader());
        sol.init();
        sol.CalcWork();
        sol.PrintWork();

    }

}

