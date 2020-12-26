package com.xyou.domain.system;

//模块管理
public class Module {
    private String id;
    //父模块id
    private String parentId;
    //父模块名称,冗余字段
    private String parentName;
    //模块名称
    private String name;
    private Integer layerNum;
    private Integer isLeaf;
    private String ico;
    private String cpermission;
    private String curl;
    //0 主菜单/1 左侧菜单/2按钮
    private Integer ctype;
    //1启用0停用
    private Integer state;
    /**
     * 从属关系
     *  0：sass系统内部菜单
     *  1：租用企业菜单
     */
    private Integer belong;
    private String cwhich;
    private Integer quoteNum;
    private String remark;
    private Integer orderNo;

    public Module() {
    }

    public Module(String id, String parentId, String parentName, String name, Integer layerNum, Integer isLeaf, String ico, String cpermission, String curl, Integer ctype, Integer state, Integer belong, String cwhich, Integer quoteNum, String remark, Integer orderNo) {
        this.id = id;
        this.parentId = parentId;
        this.parentName = parentName;
        this.name = name;
        this.layerNum = layerNum;
        this.isLeaf = isLeaf;
        this.ico = ico;
        this.cpermission = cpermission;
        this.curl = curl;
        this.ctype = ctype;
        this.state = state;
        this.belong = belong;
        this.cwhich = cwhich;
        this.quoteNum = quoteNum;
        this.remark = remark;
        this.orderNo = orderNo;
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
     * @return parentId
     */
    public String getParentId() {
        return parentId;
    }

    /**
     * 设置
     * @param parentId
     */
    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    /**
     * 获取
     * @return parentName
     */
    public String getParentName() {
        return parentName;
    }

    /**
     * 设置
     * @param parentName
     */
    public void setParentName(String parentName) {
        this.parentName = parentName;
    }

    /**
     * 获取
     * @return name
     */
    public String getName() {
        return name;
    }

    /**
     * 设置
     * @param name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * 获取
     * @return layerNum
     */
    public Integer getLayerNum() {
        return layerNum;
    }

    /**
     * 设置
     * @param layerNum
     */
    public void setLayerNum(Integer layerNum) {
        this.layerNum = layerNum;
    }

    /**
     * 获取
     * @return isLeaf
     */
    public Integer getIsLeaf() {
        return isLeaf;
    }

    /**
     * 设置
     * @param isLeaf
     */
    public void setIsLeaf(Integer isLeaf) {
        this.isLeaf = isLeaf;
    }

    /**
     * 获取
     * @return ico
     */
    public String getIco() {
        return ico;
    }

    /**
     * 设置
     * @param ico
     */
    public void setIco(String ico) {
        this.ico = ico;
    }

    /**
     * 获取
     * @return cpermission
     */
    public String getCpermission() {
        return cpermission;
    }

    /**
     * 设置
     * @param cpermission
     */
    public void setCpermission(String cpermission) {
        this.cpermission = cpermission;
    }

    /**
     * 获取
     * @return curl
     */
    public String getCurl() {
        return curl;
    }

    /**
     * 设置
     * @param curl
     */
    public void setCurl(String curl) {
        this.curl = curl;
    }

    /**
     * 获取
     * @return ctype
     */
    public Integer getCtype() {
        return ctype;
    }

    /**
     * 设置
     * @param ctype
     */
    public void setCtype(Integer ctype) {
        this.ctype = ctype;
    }

    /**
     * 获取
     * @return state
     */
    public Integer getState() {
        return state;
    }

    /**
     * 设置
     * @param state
     */
    public void setState(Integer state) {
        this.state = state;
    }

    /**
     * 获取
     * @return belong
     */
    public Integer getBelong() {
        return belong;
    }

    /**
     * 设置
     * @param belong
     */
    public void setBelong(Integer belong) {
        this.belong = belong;
    }

    /**
     * 获取
     * @return cwhich
     */
    public String getCwhich() {
        return cwhich;
    }

    /**
     * 设置
     * @param cwhich
     */
    public void setCwhich(String cwhich) {
        this.cwhich = cwhich;
    }

    /**
     * 获取
     * @return quoteNum
     */
    public Integer getQuoteNum() {
        return quoteNum;
    }

    /**
     * 设置
     * @param quoteNum
     */
    public void setQuoteNum(Integer quoteNum) {
        this.quoteNum = quoteNum;
    }

    /**
     * 获取
     * @return remark
     */
    public String getRemark() {
        return remark;
    }

    /**
     * 设置
     * @param remark
     */
    public void setRemark(String remark) {
        this.remark = remark;
    }

    /**
     * 获取
     * @return orderNo
     */
    public Integer getOrderNo() {
        return orderNo;
    }

    /**
     * 设置
     * @param orderNo
     */
    public void setOrderNo(Integer orderNo) {
        this.orderNo = orderNo;
    }

    public String toString() {
        return "Module{id = " + id + ", parentId = " + parentId + ", parentName = " + parentName + ", name = " + name + ", layerNum = " + layerNum + ", isLeaf = " + isLeaf + ", ico = " + ico + ", cpermission = " + cpermission + ", curl = " + curl + ", ctype = " + ctype + ", state = " + state + ", belong = " + belong + ", cwhich = " + cwhich + ", quoteNum = " + quoteNum + ", remark = " + remark + ", orderNo = " + orderNo + "}";
    }
}
