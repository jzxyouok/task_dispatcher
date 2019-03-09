package com.hptpd.taskdispatcherserver.common.util;

/**
 * @author lc
 */
public class DistanceHelper {
    private static double EARTH_RADIUS = 6378.137;

    /**
     *
     * @param d
     * @return
     */
    private static double getRad(double d) {
        return d * Math.PI / 180.0;
    }


    public static double getDistance(double lon1, double lat1, double lon2, double lat2) {
        double radLat1 = getRad(lat1);
        double radLat2 = getRad(lat2);
        double a = radLat1 - radLat2;
        double b = getRad(lon1) - getRad(lon2);
        double s = 2 * Math.asin(Math.sqrt(Math.pow(Math.sin(a / 2), 2) + Math.cos(radLat1) * Math.cos(radLat2) * Math.pow(Math.sin(b / 2), 2)));
        s = s * EARTH_RADIUS;
        //s = Math.round(s * 10000) / 10000;
        return s;
    }

    public static void findNeighPosition(double longitude, double latitude, double distance) {
        //先计算查询点的经纬度范围
        double r = 6378.137;
        //地球半径千米
        double dis = distance;
        //千米距离
        double dlng = 2 * Math.asin(Math.sin(dis / (2 * r)) / Math.cos(latitude * Math.PI / 180));
        dlng = dlng * 180 / Math.PI;
        //角度转为弧度
        double dlat = dis / r;
        dlat = dlat * 180 / Math.PI;
        double minlat = latitude - dlat;
        double maxlat = latitude + dlat;
        double minlng = longitude - dlng;
        double maxlng = longitude + dlng;
    }
}
