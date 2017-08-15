package com.payment.entity.groupwormhole;

import com.payment.entity.publicenitty.Entity;

/**
 * Created by shi_y on 2016/9/9.
 */
public class OperationLog extends Entity {

    private long logId;
    private String operator;
    private String target;
    private int operationType;
    private String resultCode;
    private String description;

    public OperationLog(){

    }

    public OperationLog(String operator, String target, int operationType, String resultCode, String description){
        this.operator = operator;
        this.target = target;
        this.resultCode = resultCode;
        this.description = description;
        this.operationType = operationType;
    }

    public long getLogId() {
        return logId;
    }

    public void setLogId(long logId) {
        this.logId = logId;
    }

    public String getOperator() {
        return operator;
    }

    public void setOperator(String operator) {
        this.operator = operator;
    }

    public String getTarget() {
        return target;
    }

    public void setTarget(String target) {
        this.target = target;
    }

    public int getOperationType() {
        return operationType;
    }

    public void setOperationType(int operationType) {
        this.operationType = operationType;
    }

    public String getResultCode() {
        return resultCode;
    }

    public void setResultCode(String resultCode) {
        this.resultCode = resultCode;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString(){
        return String.format("logId:%s, operator:%s, target:%s,operationType:%s, resultCode:%s,description:%s",
                this.logId, this.operator,this.target, this.operationType, this.resultCode, this.description);
    }
}
