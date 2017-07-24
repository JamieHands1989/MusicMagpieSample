package uk.co.jamiehands.musicmagpiesample.model;

import java.util.List;

public class UPCLookup {
    public String code;
    public String total;
    public String offset;
    public List<LookupItem> items;

    public class LookupItem {
        public String ean;
        public String title;
        public String Description;
        public String elid;
        public String brand;
        public String model;
        public String color;
        public String size;
        public String dimension;
        public String weight;
        public double lowest_recorded_price;
        public double highest_recorded_price;
    }
}
