package com.sinata.framework.navigator.mode;


import java.util.List;

public class BottomBar {
    public List<Tab> tabs;

    public class Tab {
        public boolean enable;
        public String pageUrl;
        public int index;
        public int title;
    }
}
