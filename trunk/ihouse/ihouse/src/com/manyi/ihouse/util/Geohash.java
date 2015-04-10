package com.manyi.ihouse.util;

import java.io.File;  
import java.io.FileInputStream;  
import java.text.DecimalFormat;
import java.util.BitSet;  
import java.util.HashMap;  
  
  
public class Geohash {  
  
    private static int numbits = 6 * 5;  
    final static char[] digits = { '0', '1', '2', '3', '4', '5', '6', '7', '8',  
            '9', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'j', 'k', 'm', 'n', 'p',  
            'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z' };  
      
    final static HashMap<Character, Integer> lookup = new HashMap<Character, Integer>();  
    static {  
        int i = 0;  
        for (char c : digits)  
            lookup.put(c, i++);  
    }  
  
    public static void main(String[] args)  throws Exception{  
    	//wx4g2448vd7h
    	//wx4g2453c8jc
//        System.out.println(new Geohash().encode(31.252897,121.436193)); 
 //       double[] d = new Geohash().decode("wx4g24573gkx");31.230482,121.456755,
//        System.out.println(d[0] + "," + d[1]); 
//    	 System.out.println( Direction.getName(1));
    }  

    public double[] decode(String geohash) {  
        StringBuilder buffer = new StringBuilder();  
        for (char c : geohash.toCharArray()) {  
  
            int i = lookup.get(c) + 32;  
            buffer.append( Integer.toString(i, 2).substring(1) );  
        }  
          
        BitSet lonset = new BitSet();  
        BitSet latset = new BitSet();  
          
        //even bits  
        int j =0;  
        for (int i=0; i< numbits*2;i+=2) {  
            boolean isSet = false;  
            if ( i < buffer.length() )  
              isSet = buffer.charAt(i) == '1';  
            lonset.set(j++, isSet);  
        }  
          
        //odd bits  
        j=0;  
        for (int i=1; i< numbits*2;i+=2) {  
            boolean isSet = false;  
            if ( i < buffer.length() )  
              isSet = buffer.charAt(i) == '1';  
            latset.set(j++, isSet);  
        }  
          
        double lon = decode(lonset, -180, 180);  
        double lat = decode(latset, -90, 90);  
          
        return new double[] {lat, lon};       
    }  
      
    private static double decode(BitSet bs, double floor, double ceiling) {  
        double mid = 0;  
        for (int i=0; i<bs.length(); i++) {  
            mid = (floor + ceiling) / 2;  
            if (bs.get(i))  
                floor = mid;  
            else  
                ceiling = mid;  
        }  
        return mid;  
    }  
      
    public static String encode(String lat,String lon){
    	double d1 = Double.parseDouble(lat);
    	double d2 = Double.parseDouble(lon);
    	return encode(d1, d2);
    }
      
    public static String encode(double lat, double lon) {  
        BitSet latbits = getBits(lat, -90, 90);  
        BitSet lonbits = getBits(lon, -180, 180);  
        StringBuilder buffer = new StringBuilder();  
        for (int i = 0; i < numbits; i++) {  
            buffer.append( (lonbits.get(i))?'1':'0');  
            buffer.append( (latbits.get(i))?'1':'0');  
        }  
        return base32(Long.parseLong(buffer.toString(), 2));  
    }  
  
    private static BitSet getBits(double lat, double floor, double ceiling) {  
        BitSet buffer = new BitSet(numbits);  
        for (int i = 0; i < numbits; i++) {  
            double mid = (floor + ceiling) / 2;  
            if (lat >= mid) {  
                buffer.set(i);  
                floor = mid;  
            } else {  
                ceiling = mid;  
            }  
        }  
        return buffer;  
    }  
  
    public static String base32(long i) {  
        char[] buf = new char[65];  
        int charPos = 64;  
        boolean negative = (i < 0);  
        if (!negative)  
            i = -i;  
        while (i <= -32) {  
            buf[charPos--] = digits[(int) (-(i % 32))];  
            i /= 32;  
        }  
        buf[charPos] = digits[(int) (-i)];  
  
        if (negative)  
            buf[--charPos] = '-';  
        return new String(buf, charPos, (65 - charPos));  
    }  
    
    
   public static final int TOP = 0;
   public static final int Right  = 1;
   public static final int Bottom  = 2;
   public static final int Left  = 3;

    private static String Base32 = "0123456789bcdefghjkmnpqrstuvwxyz";
    private static Integer[] Bits = new Integer[] {16, 8, 4, 2, 1};

    private static String[][] Neighbors = {
                                                       new String[]
                                                           {
                                                               "p0r21436x8zb9dcf5h7kjnmqesgutwvy", // Top
                                                               "bc01fg45238967deuvhjyznpkmstqrwx", // Right
                                                               "14365h7k9dcfesgujnmqp0r2twvyx8zb", // Bottom
                                                               "238967debc01fg45kmstqrwxuvhjyznp", // Left
                                                           }, new String[]
                                                                  {
                                                                      "bc01fg45238967deuvhjyznpkmstqrwx", // Top
                                                                      "p0r21436x8zb9dcf5h7kjnmqesgutwvy", // Right
                                                                      "238967debc01fg45kmstqrwxuvhjyznp", // Bottom
                                                                      "14365h7k9dcfesgujnmqp0r2twvyx8zb", // Left
                                                                  }
                                                   };

    private static String[][] Borders = {
                                                     new String[] {"prxz", "bcfguvyz", "028b", "0145hjnp"},
                                                     new String[] {"bcfguvyz", "prxz", "0145hjnp", "028b"}
                                                 };

    public static String CalculateAdjacent(String hash, int direction)
    {
        hash = hash.toLowerCase();

        char lastChr = hash.charAt(hash.length() - 1);
        int type = hash.length() % 2;
        String nHash = hash.substring(0, hash.length() - 1);

        if (Borders[type][direction].indexOf(lastChr) != -1)
        {
           nHash = CalculateAdjacent(nHash, direction);
        }
        return "";//nHash + Base32[Neighbors[type][direction].indexOf(lastChr)];
    }
  
}