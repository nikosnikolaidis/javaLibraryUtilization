package javaLibraryUtilization.models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.util.List;

@Entity
public class LibraryDTO {
    @Id
    @GeneratedValue
    private long libraryDTOid;
    private String name;
    private Double PUCD;
    //Percentage of Used Classes Tracing
    private Double PUCI;
    //Percentage of Used Methods Of Classes
    private Double LDUF;
    private Double LIUF;
    //Percentage of Used Methods Of Classes
    @OneToMany
    public List<MethodDetailsDTO> methodDetailsDTOList;

    public LibraryDTO() {
    }

    public LibraryDTO(String name, Double PUCD, Double PUCI, Double LDUF, Double LIUF) {
        this.name = name;
        this.PUCD = PUCD;
        this.PUCI = PUCI;
        this.LDUF = LDUF;
        this.LIUF = LIUF;
    }

    public LibraryDTO(String name, Double PUCD, Double PUCI, Double LDUF, Double LIUF, List<MethodDetailsDTO> methodDetailsDTOList) {
        this.name = name;
        this.PUCD = PUCD;
        this.PUCI = PUCI;
        this.LDUF = LDUF;
        this.LIUF = LIUF;
        this.methodDetailsDTOList = methodDetailsDTOList;
    }

    public String getName() {
        return name;
    }

    public Double getPUCD() {
        return PUCD;
    }

    public Double getPUCI() {
        return PUCI;
    }

    public Double getLDUF() {
        return LDUF;
    }

    public Double getLIUF() {
        return LIUF;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPUCD(Double PUCD) {
        this.PUCD = PUCD;
    }

    public void setPUCI(Double PUCI) {
        this.PUCI = PUCI;
    }

    public void setLDUF(Double LDUF) {
        this.LDUF = LDUF;
    }

    public void setLIUF(Double LIUF) {
        this.LIUF = LIUF;
    }
}