package javaLibraryUtilization.control;

import java.util.List;

public class MethodDetailsDTO {
    private String methodName;
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

    public List<CallDTO> getCallPDOList() {
        return callDTOList;
    }

    public void setCallPDOList(List<CallDTO> callDTOList) {
        this.callDTOList = callDTOList;
    }
}
