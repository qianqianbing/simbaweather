package com.simba.membercenter.bean;
/**
 * @description: 车型类型
 * @author: luojunjie
 * @createDate: 2020/4/19 16:04
 */

public class VehicleInfoBean {
        String plateno; //": "苏A88888",
        String vehicleType; //": "新能源S61车型"

        public String getPlateno() {
                return plateno;
        }

        public void setPlateno(String plateno) {
                this.plateno = plateno;
        }

        public String getVehicleType() {
                return vehicleType;
        }

        public void setVehicleType(String vehicleType) {
                this.vehicleType = vehicleType;
        }
}
