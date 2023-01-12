package javaLibraryUtilization.control;

public class Library {
    private String name;
    private Double PLMI;
    private Double LUF;

    public Library(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getPLMI() {
        return PLMI;
    }

    public void setPLMI(Double PLMI) {
        this.PLMI = PLMI;
    }

    public Double getLUF() {
        return LUF;
    }

    public void setLUF(Double LUF) {
        this.LUF = LUF;
    }
}
