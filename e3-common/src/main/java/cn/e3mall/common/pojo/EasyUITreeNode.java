package cn.e3mall.common.pojo;

import java.io.Serializable;

public class EasyUITreeNode implements Serializable {
    /**
     *
     * id
     */
    private Long id;
    /**
     * text
     */
    private String text;
    /**
     * state
     */
    private String state;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }
}
