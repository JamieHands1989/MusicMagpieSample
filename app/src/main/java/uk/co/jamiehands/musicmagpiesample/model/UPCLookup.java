package uk.co.jamiehands.musicmagpiesample.model;

import java.util.List;

@SuppressWarnings("CanBeFinal")
public class UPCLookup {
    public List<LookupItem> items;

    public class LookupItem {
        public String ean;
        public String title;
        public String brand;
    }
}
