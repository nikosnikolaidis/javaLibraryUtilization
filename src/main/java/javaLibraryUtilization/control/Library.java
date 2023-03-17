package javaLibraryUtilization.control;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Library {
    @Id
    @GeneratedValue
    private long id;
    private String name;
    private Double PUC;
    //Percentage of Used Classes Tracing
    private Double PUCT;
    //Percentage of Used Methods Of Classes
    private Double PUMC;
    private Double LUF;
    //Percentage of Used Methods Of Classes

    public Library(String name, Double PUC, Double PUCT, Double PUMC, Double LUF) {
        this.name = name;
        this.PUC = PUC;
        this.PUCT = PUCT;
        this.PUMC = PUMC;
        this.LUF = LUF;
    }

    public String getName() {
        return name;
    }

    public Double getPUC() {
        return PUC;
    }

    public Double getPUCT() {
        return PUCT;
    }

    public Double getPUMC() {
        return PUMC;
    }

    public Double getLUF() {
        return LUF;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPUC(Double PUC) {
        this.PUC = PUC;
    }

    public void setPUCT(Double PUCT) {
        this.PUCT = PUCT;
    }

    public void setPUMC(Double PUMC) {
        this.PUMC = PUMC;
    }

    public void setLUF(Double LUF) {
        this.LUF = LUF;
    }
}