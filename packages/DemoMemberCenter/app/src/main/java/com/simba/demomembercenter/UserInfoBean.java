package com.simba.demomembercenter;

public class UserInfoBean {

        private String nickname;

        private String headimgurl;

        private String id;

        private String level;

        private String levelIcon;

        private String currentLevelPoint;

        private String nextLevelPoint;

        private String vehicleType;

        private int hasBindWechat;

        private int hasCertification;

        public String getNickname() {
                return nickname;
        }

        public void setNickname(String nickname) {
                this.nickname = nickname;
        }

        public String getHeadimgurl() {
                return headimgurl;
        }

        public void setHeadimgurl(String headimgurl) {
                this.headimgurl = headimgurl;
        }

        public String getId() {
                return id;
        }

        public void setId(String id) {
                this.id = id;
        }

        public String getLevel() {
                return level;
        }

        public void setLevel(String level) {
                this.level = level;
        }

        public String getLevelIcon() {
                return levelIcon;
        }

        public void setLevelIcon(String levelIcon) {
                this.levelIcon = levelIcon;
        }

        public String getCurrentLevelPoint() {
                return currentLevelPoint;
        }

        public void setCurrentLevelPoint(String currentLevelPoint) {
                this.currentLevelPoint = currentLevelPoint;
        }

        public String getNextLevelPoint() {
                return nextLevelPoint;
        }

        public void setNextLevelPoint(String nextLevelPoint) {
                this.nextLevelPoint = nextLevelPoint;
        }

        public String getVehicleType() {
                return vehicleType;
        }

        public void setVehicleType(String vehicleType) {
                this.vehicleType = vehicleType;
        }

        public int getHasBindWechat() {
                return hasBindWechat;
        }

        public void setHasBindWechat(int hasBindWechat) {
                this.hasBindWechat = hasBindWechat;
        }

        public int getHasCertification() {
                return hasCertification;
        }

        public void setHasCertification(int hasCertification) {
                this.hasCertification = hasCertification;
        }
}
