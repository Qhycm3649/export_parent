package com.xyou.domain.system;

//部门实体类
public class Dept {

    private String id;
    private String deptName;
    private Dept parent;
    private String state;
    private String companyId;
    private String companyName;

    public Dept() {
    }

    public Dept(String id, String deptName, Dept parent, String state, String companyId, String companyName) {
        this.id = id;
        this.deptName = deptName;
        this.parent = parent;
        this.state = state;
        this.companyId = companyId;
        this.companyName = companyName;
    }

    /**
     * 获取
     * @return id
     */
    public String getId() {
        return id;
    }

    /**
     * 设置
     * @param id
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * 获取
     * @return deptName
     */
    public String getDeptName() {
        return deptName;
    }

    /**
     * 设置
     * @param deptName
     */
    public void setDeptName(String deptName) {
        this.deptName = deptName;
    }

    /**
     * 获取
     * @return parent
     */
    public Dept getParent() {
        return parent;
    }

    /**
     * 设置
     * @param parent
     */
    public void setParent(Dept parent) {
        this.parent = parent;
    }

    /**
     * 获取
     * @return state
     */
    public String getState() {
        return state;
    }

    /**
     * 设置
     * @param state
     */
    public void setState(String state) {
        this.state = state;
    }

    /**
     * 获取
     * @return companyId
     */
    public String getCompanyId() {
        return companyId;
    }

    /**
     * 设置
     * @param companyId
     */
    public void setCompanyId(String companyId) {
        this.companyId = companyId;
    }

    /**
     * 获取
     * @return companyName
     */
    public String getCompanyName() {
        return companyName;
    }

    /**
     * 设置
     * @param companyName
     */
    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String toString() {
        return "Dept{id = " + id + ", deptName = " + deptName + ", parent = " + parent + ", state = " + state + ", companyId = " + companyId + ", companyName = " + companyName + "}";
    }
}
