package javaLibraryUtilization.models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.util.List;
@Entity
public class MethodDetailsDTO {
    @Id
    @GeneratedValue
    private Long methodDetailsDTOid;
    private String methodName;
    @OneToMany
    public List<CallDTO> callDTOList;
    public MethodDetailsDTO(String methodName, List<CallDTO> callDTOList) {
        this.methodName = methodName;
        this.callDTOList = callDTOList;
    }

    public String getMethodName() {
        return methodName;
    }

    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }

}
