package Metropolises;

import java.math.BigInteger;

public class Metropolis {
    private String metropolis;
    private String continent;
    private int population;

    public Metropolis(){
        metropolis = null;
        continent = null;
        population = -1;
    }

    public void setMetropolis(String metropolis){
        this.metropolis = metropolis;
    }

    public void setContinent(String continent){
        this.continent = continent;
    }

    public void setPopulation(int population){
        if(population < 0)
            throw new IllegalArgumentException("Population can't be negative.");
        this.population = population;
    }


    public String getMetropolis(){
        if(metropolis == null)
            throw new RuntimeException("Metropolis name isn't set");
        return metropolis;
    }


    public String getContinent(){
        if(continent == null)
            throw new RuntimeException("Continent name isn't set.");
        return continent;
    }

    public int getPopulation(){
        if(population == -1)
            throw new RuntimeException("Population amount isn't set");
        return population;
    }

    @Override
    public boolean equals(Object other){
        if(other == null || !(other instanceof Metropolis)) return false;
        Metropolis tmp = (Metropolis) other;
        return getMetropolis().equals(tmp.getMetropolis()) && getContinent().equals(tmp.getContinent()) && getPopulation() == tmp.getPopulation();
    }


}
