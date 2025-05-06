package com.springboot.runaway.converter;

public class TMCoordinateConverter {

    private static final double TM_ORIGIN_LAT     = 38.0;
    private static final double TM_ORIGIN_LON     = 127.0;
    private static final double TM_FALSE_EASTING  = 200000.0;
    private static final double TM_FALSE_NORTHING = 600000.0;
    private static final double K0                = 1.0;
    private static final double A                 = 6378137.0;
    private static final double F                 = 1.0 / 298.257222101;
    private static final double E2                = 2 * F - F * F;

    public static double[] toWGS84(double x, double y) {

        double lat0 = Math.toRadians(TM_ORIGIN_LAT);
        double lon0 = Math.toRadians(TM_ORIGIN_LON);


        double dx = x - TM_FALSE_EASTING;
        double dy = y - TM_FALSE_NORTHING;


        double term1 = (1 - E2/4 - 3*E2*E2/64 - 5*E2*E2*E2/256) * lat0;
        double term2 = (3*E2/8 + 3*E2*E2/32 + 45*E2*E2*E2/1024) * Math.sin(2 * lat0);
        double term3 = (15*E2*E2/256 + 45*E2*E2*E2/1024)       * Math.sin(4 * lat0);
        double term4 = (35*E2*E2*E2/3072)                      * Math.sin(6 * lat0);
        double M0    = A * (term1 - term2 + term3 - term4);

        double M = dy / K0 + M0;

        double mu = M / (A * (1 - E2/4 - 3*E2*E2/64 - 5*E2*E2*E2/256));

        double e1 = (1 - Math.sqrt(1 - E2)) / (1 + Math.sqrt(1 - E2));
        double J1 = 3*e1/2 - 27*Math.pow(e1,3)/32;
        double J2 = 21*Math.pow(e1,2)/16 - 55*Math.pow(e1,4)/32;
        double J3 = 151*Math.pow(e1,3)/96;
        double J4 = 1097*Math.pow(e1,4)/512;

        double fp = mu + J1*Math.sin(2*mu) + J2*Math.sin(4*mu)
                + J3*Math.sin(6*mu) + J4*Math.sin(8*mu);

        double sinfp = Math.sin(fp);
        double cosfp = Math.cos(fp);
        double tanfp = Math.tan(fp);
        double e2p   = E2 / (1 - E2);
        double C1    = e2p * cosfp * cosfp;
        double T1    = tanfp * tanfp;
        double R1    = A * (1 - E2) / Math.pow(1 - E2 * sinfp * sinfp, 1.5);
        double N1    = A / Math.sqrt(1 - E2 * sinfp * sinfp);
        double D     = dx / (N1 * K0);

        double lat = fp - (N1 * tanfp / R1) * (
                D*D/2
                        - (5 + 3*T1 + 10*C1 - 4*C1*C1 - 9*e2p) * Math.pow(D,4)/24
                        + (61 + 90*T1 + 298*C1 + 45*T1*T1 - 252*e2p - 3*C1*C1) * Math.pow(D,6)/720
        );

        double lon = lon0 + ( D
                - (1 + 2*T1 + C1) * Math.pow(D,3)/6
                + (5 - 2*C1 + 28*T1 - 3*C1*C1 + 8*e2p + 24*T1*T1) * Math.pow(D,5)/120
        ) / cosfp;

        return new double[]{ Math.toDegrees(lat), Math.toDegrees(lon) };
    }
}
