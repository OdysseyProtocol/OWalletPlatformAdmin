package com.stormfives.admin.coin.dao.entity;

import java.util.Date;

public class RechargeGatherRule {
    private Integer id;

    private Integer coinId;

    private Integer coinLowerLimit;

    private Integer coinHigherLimit;

    private Integer dayCycleRound;

    private Integer createdBy;

    private Date createdAt;

    private Integer updatedBy;

    private Date updatedAt;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getCoinId() {
        return coinId;
    }

    public void setCoinId(Integer coinId) {
        this.coinId = coinId;
    }

    public Integer getCoinLowerLimit() {
        return coinLowerLimit;
    }

    public void setCoinLowerLimit(Integer coinLowerLimit) {
        this.coinLowerLimit = coinLowerLimit;
    }

    public Integer getCoinHigherLimit() {
        return coinHigherLimit;
    }

    public void setCoinHigherLimit(Integer coinHigherLimit) {
        this.coinHigherLimit = coinHigherLimit;
    }

    public Integer getDayCycleRound() {
        return dayCycleRound;
    }

    public void setDayCycleRound(Integer dayCycleRound) {
        this.dayCycleRound = dayCycleRound;
    }

    public Integer getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(Integer createdBy) {
        this.createdBy = createdBy;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Integer getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(Integer updatedBy) {
        this.updatedBy = updatedBy;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }
}