package com.sumscope.cdh.realtime.model.handler;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by liu.yang on 2017/10/18.
 */
public class BondEventModel extends AbstractEvent {
    private Map<String,Object> source;
    private List<HashMap<String, Object>> target;

    public BondEventModel(){
        this.target = new ArrayList<>();
    }

    public Map<String, Object> getSource() {
        return source;
    }

    public void setSource(Map<String, Object> source) {
        this.source = source;
    }

    public List<HashMap<String, Object>> getTarget() {
        return target;
    }

    public void setTarget(List<HashMap<String, Object>> target) {
        this.target = target;
    }
}
