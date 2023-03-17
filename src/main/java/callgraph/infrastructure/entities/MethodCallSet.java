/*******************************************************************************
 		* Copyright (C) 2021-2022 UoM - University of Macedonia
 * 
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * SPDX-License-Identifier: EPL-2.0
 ******************************************************************************/
package callgraph.infrastructure.entities;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
public class MethodCallSet {
    @Id
    @GeneratedValue
    private Long methodCallSetId;

    private final MethodDecl methodDeclaration;

    public final Set<MethodDecl> methodCalls;

    public MethodCallSet(MethodDecl method) {
        this.methodDeclaration = method;
        this.methodCalls = new HashSet<>();
    }

    public MethodCallSet(Long methodCallSetId, MethodDecl methodDeclaration, Set<MethodDecl> methodCalls) {
        this.methodCallSetId = methodCallSetId;
        this.methodDeclaration = methodDeclaration;
        this.methodCalls = methodCalls;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
         MethodCallSet that = (MethodCallSet) o;
        return Objects.equals(methodDeclaration, that.methodDeclaration);
    }
    		 			
    @Override
    public int hashCode() {
        return Objects.hash(methodDeclaration);
    }
     
    public MethodDecl getMethod() {
        return methodDeclaration;
    }

    public MethodDecl getMethodDeclaration() {
        return methodDeclaration;
    }

    public Set<MethodDecl> getMethodCalls() {

        return methodCalls;
    }

    public void addMethodCall(MethodDecl methodDeclaration) {
        this.getMethodCalls().add(methodDeclaration);
    }

    @Override
    public String toString() {
        return "MethodCallGraph{" +
                "method='" + methodDeclaration + '\'' +
                ", methodCalls=" + methodCalls +
                '}';
    }
}
