package com.example.recyclerviewtest;

public class Tools {
    private int toolsId;
    private String toolsName;

    public Tools(int toolsId, String toolesName) {
        this.toolsId = toolsId;
        this.toolsName = toolesName;
    }

    public int getToolsId() {
        return toolsId;
    }

    public void setToolsId(int toolsId) {
        this.toolsId = toolsId;
    }

    public String getToolesName() {
        return toolsName;
    }

    public void setToolesName(String toolesName) {
        this.toolsName = toolesName;
    }
}
