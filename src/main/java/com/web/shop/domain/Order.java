package com.web.shop.domain;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

public class Order implements Serializable {
    private static final long serialVersionUID = 1L;
    private int orderId;
    private int userId;
    private List<Product> orderList;
    private Map<Integer, Integer> amountMap;
    private String date;

    public Order() {
    }

    public Order(int orderId, int userId, List<Product> orderList, Map<Integer, Integer> amountMap, String date) {
        this.orderId = orderId;
        this.userId = userId;
        this.orderList = orderList;
        this.amountMap = amountMap;
        this.date = date;
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public List<Product> getOrderList() {
        return orderList;
    }

    public void setOrderList(List<Product> orderList) {
        this.orderList = orderList;
    }

    public Map<Integer, Integer> getAmountMap() {
        return amountMap;
    }

    public void setAmountMap(Map<Integer, Integer> amountMap) {
        this.amountMap = amountMap;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }

        Order o = (Order) obj;

        if (orderId != o.orderId) {
            return false;
        }
        if (userId != o.userId) {
            return false;
        }
        if (amountMap == null) {
            if (o.amountMap != null) {
                return false;
            }
        } else if (!amountMap.equals(o.amountMap)) {
            return false;
        }

        if (date == null) {
            if (o.date != null) {
                return false;
            }
        } else if (!date.equals(o.date)) {
            return false;
        }

        if (orderList == null) {
            if (o.orderList != null) {
                return false;
            }
        } else if (!orderList.equals(o.orderList)) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + orderId;
        result = prime * result + userId;
        result = prime * result + (orderList != null ? orderList.hashCode() : 0);
        result = prime * result + (amountMap != null ? amountMap.hashCode() : 0);
        result = prime * result + (date != null ? date.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getName());
        sb.append(": orderId=" + orderId);
        sb.append(", userId=" + userId);
        sb.append(", orderList=" + orderList);
        sb.append(", amountMap=" + amountMap);
        sb.append(", date=" + date);
        return sb.toString();
    }
}
