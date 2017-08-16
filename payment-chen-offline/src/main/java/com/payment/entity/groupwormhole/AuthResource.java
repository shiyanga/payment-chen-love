package com.payment.entity.groupwormhole;
import com.payment.common.util.CommonUtil;
import com.payment.entity.publicenitty.Entity;

import java.util.ArrayList;
import java.util.List;


public class AuthResource extends Entity {

    private Integer id;
    private Integer parentId;
    private String code;
    private String description;
    private String url;
    private String reqMethod;

    private List<AuthResource> children;

    public AuthResource() {

    }

    public AuthResource(Integer parentId, String code, String description, String url, String reqMethod) {
        this.parentId = parentId;
        this.code = code;
        this.description = description;
        this.url = url;
        this.reqMethod = reqMethod;
    }

    public enum ReqMethodEnum {
        GET("GET", "GET请求"),
        POST("POST", "POST请求"),
        PATCH("PATCH", "PATCH请求"),
        PUT("PUT", "PUT请求"),
        DELETE("DELETE", "DELETE请求");

        private String code;
        private String desc;

        ReqMethodEnum(String code, String desc) {
            this.code = code;
            this.desc = desc;
        }

        public static ReqMethodEnum getItem(String typeCode) {
            if (typeCode == null) {
                return null;
            }
            for (ReqMethodEnum statusEnum : ReqMethodEnum.values()) {
                if (statusEnum.getCode().equalsIgnoreCase(typeCode)) {
                    return statusEnum;
                }
            }
            return null;
        }

        public String getCode() {
            return code;
        }

        public String getDesc() {
            return desc;
        }
    }

    public static List<AuthResource> convert2Tree(List<AuthResource> origList) {
        if (origList == null || origList.size() == 0) {
            return null;
        }
        List<AuthResource> treeList = new ArrayList<>();
        //层级关系处理
        for (AuthResource node1 : origList) {
            boolean mark = false;
            for (AuthResource node2 : origList) {
                if (node1.getParentId() != null && node1.getParentId().equals(node2.getId())) {
                    mark = true;
                    if (node2.getChildren() == null)
                        node2.setChildren(new ArrayList<AuthResource>());
                    node2.getChildren().add(node1);
                    break;
                }
            }
            if (!mark) {
                treeList.add(node1);
            }
        }
        return treeList;
    }

    //获取某个资源下的子资源
    public static List<AuthResource> queryChildren(List<AuthResource> origList, Integer resourceId) {
        if (origList == null || origList.size() == 0 || resourceId == null) {
            return null;
        }
        List<AuthResource> allResources = convert2Tree(origList);
        if(allResources == null){
            return null;
        }

        List<AuthResource> treeList = new ArrayList<>();
        //层级关系处理
        for (AuthResource node : origList) {
            //找到resourceID记录，遍历children直到没有child
            if(node != null && node.getId().equals(resourceId)){
                List<AuthResource> tempRes = new ArrayList<>();
                tempRes.add(node);
                treeList = recursiveTraverTree(tempRes);
                break;
            }
        }
        return treeList;
    }

    //递归遍历 resourceTree
    private static List<AuthResource> recursiveTraverTree(List<AuthResource> origList){
        List<AuthResource> authResources = new ArrayList<>();
        if(origList != null){
            for(AuthResource res : origList){
                if(res != null){
                    authResources.add(res);
                    if(CommonUtil.listIsNotBlank(res.getChildren())){
                        authResources.addAll(recursiveTraverTree(res.getChildren()));
                    }
                }
            }
        }
        return authResources;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getParentId() {
        return parentId;
    }

    public void setParentId(Integer parentId) {
        this.parentId = parentId;
    }

    public List<AuthResource> getChildren() {
        return children;
    }

    public void setChildren(List<AuthResource> children) {
        this.children = children;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getReqMethod() {
        return reqMethod;
    }

    public void setReqMethod(String reqMethod) {
        this.reqMethod = reqMethod;
    }

}
