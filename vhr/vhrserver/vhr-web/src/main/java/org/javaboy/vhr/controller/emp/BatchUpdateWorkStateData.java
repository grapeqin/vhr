package org.javaboy.vhr.controller.emp;

public class BatchUpdateWorkStateData {
    private String idCard;
    private String workState;

    public String getIdCard() {
        return idCard;
    }

    public void setIdCard(String idCard) {
        this.idCard = idCard;
    }

    public String getWorkState() {
        return workState;
    }

    public void setWorkState(String workState) {
        this.workState = workState;
    }

    @Override
    public String toString() {
        return "BatchUpdateWorkStateData{" +
                "idCard='" + idCard + '\'' +
                ", workState='" + workState + '\'' +
                '}';
    }
}
