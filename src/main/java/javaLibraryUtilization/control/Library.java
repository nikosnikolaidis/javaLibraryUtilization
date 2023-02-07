package javaLibraryUtilization.control;

public class Library {
    private String name;
    private Double LUF;
    //Percentage of Used Classes
    private Double PUC;
    //Percentage of Used Methods Of Classes
    private Double PUMC;

    public Library(String name, Double LUF, Double PUC, Double PUMC) {
        this.name = name;
        this.LUF = LUF;
        this.PUC = PUC;
        this.PUMC = PUMC;
    }

    public Double getPUC() {
        return PUC;
    }

    public void setPUC(Double PUC) {
        this.PUC = PUC;
    }

    public Double getPUMC() {
        return PUMC;
    }

    public void setPUMC(Double PUMC) {
        this.PUMC = PUMC;
    }

    public Library(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getLUF() {
        return LUF;
    }

    public void setLUF(Double LUF) {
        this.LUF = LUF;
    }
}