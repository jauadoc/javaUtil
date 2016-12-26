package com.sun.structure;

import java.util.ArrayList;
import java.util.List;

/**
 * 树状关系结构
 * @author sunx(sunxin@strongit.com.cn)<br/>
 * @version V1.0.0<br/>
 */

public class TreeStructure {

    /**
     * 节点id
     */
    private String nodeId;

    /**
     * 节点描述/节点string数据
     */
    private String nodeName;

    /**
     * 节点数据
     */
    private Object nodeData;

    /**
     * 子节点
     */
    private List<TreeStructure> childs;

    /**
     * 父节点id
     */
    private String parentId;

    public String getNodeId() {
        return nodeId;
    }

    /**
     * 设置节点id
     * @param nodeId
     */
    public void setNodeId(String nodeId) {
        this.nodeId = nodeId;
    }

    /**
     * 获取节点名称/节点字符串数据
     * @return
     */
    public String getNodeName() {
        return nodeName;
    }

    /**
     * 设置节点名称/字符串数据
     * @param nodeName
     */
    public void setNodeName(String nodeName) {
        this.nodeName = nodeName;
    }

    /**
     * 获取当前节点的所有子节点
     * @return
     */
    public List<TreeStructure> getChilds() {
        return childs;
    }

    /**
     * 添加子节点
     * @param child
     */
    public void addChild(TreeStructure child){
        childs.add(child);
    }

    /**
     * 设置子节点list
     * @param childs
     */
    public void setChilds(List<TreeStructure> childs){
        this.childs = childs;
    }

    /**
     * 获取当前节点的obj数据
     * @return
     */
    public Object getNodeData() {
        return nodeData;
    }

    /**
     * 设置当前节点的obj数据
     * @param objData
     */
    public void setNodeData(Object nodeData) {
        this.nodeData = nodeData;
    }

    /**
     * 获取 该节点及所有子节点 的List<TreeStructure>
     */
    public List<TreeStructure> getNodesListWithoutHierarchy(){

        List<TreeStructure> nodes = new ArrayList<TreeStructure>();

        nodes.add(this);

        if(hasChildren()){
            for(TreeStructure child : childs){
                nodes.add(child);
                if(child.hasChildren()){
                    nodes.containsAll(child.getNodesListWithoutHierarchy());
                }
            }
        }

        return nodes;
    }

    /**
     * 获取 该节点及所有子节点的 节点名 的List<String>
     */
    public List<String> getNamesWithoutHierarchy(){

        List<String> nodeNames = new ArrayList<String>();

        nodeNames.add(this.nodeName);

        if(hasChildren()){
            for(TreeStructure child : childs){
                nodeNames.add(child.getNodeName());
                if(child.hasChildren()){
                    nodeNames.containsAll(child.getNamesWithoutHierarchy());
                }
            }
        }

        return nodeNames;
    }

    /**
     * 获取 该节点及所有子节点 节点数据 的List<Object>
     */
    public Object getObjDatasWithoutHierarchy(){
        List<Object> nodeObjs = new ArrayList<Object>();

        nodeObjs.add(this.nodeData);

        if(hasChildren()){
            for(TreeStructure child : childs){
                nodeObjs.add(child.getNodeData());
                if(child.hasChildren()){
                    nodeObjs.containsAll(child.getNamesWithoutHierarchy());
                }
            }
        }

        return nodeObjs;
    }

    /**
     * 根据id获取节点，如果不存在该节点返回null
     * @param nodeId
     */
    public TreeStructure getNode(String nodeId){

        if(this.nodeData == nodeId){
            return this;
        }

        if(hasChildren()){
            for(TreeStructure child : childs){
                if(child.getNodeId() == nodeId){
                    return child;
                }
                if(child.hasChildren()){
                    TreeStructure node =  getNode(child.getNodeId());
                    if(node != null){
                        return node;
                    }
                }
            }
        }
        return null;
    }

    /**
     * 是否有子节点
     * @return
     */
    public boolean hasChildren(){
        return childs == null;
    }

    /**
     * 获取父节点id
     * @return
     */
    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }
}
