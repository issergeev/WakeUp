package com.cogwheelSoft.androiddevelop.wakeup;

import java.util.ArrayList;
import java.util.List;

public class ItemsAdder {
    private String Headding;
    private int CardImage;

    public ItemsAdder(String Headding, int CardImage){
        this.Headding = Headding;
        this.CardImage = CardImage;
    }

    public String getAbout(){
        return Headding;
    }
    public int getCardImg(){
        return CardImage;
    }

    public static List<ItemsAdder> getCard(){
        ArrayList<ItemsAdder> arrayList = new ArrayList<>();
        arrayList.add(new ItemsAdder("Disable screen when proximity sensor activates", R.drawable.help_lop));
        arrayList.add(new ItemsAdder("Do not lock the screen when proximity sensor " +
                "activates if the phone is in a landscape orientation", R.drawable.help_nd));
        arrayList.add(new ItemsAdder("Keeps the display on while the function enabled", R.drawable.help_aod));
        arrayList.add(new ItemsAdder("Enable vibration mode when the phone lies upside down on the screen", R.drawable.help_mor));
        arrayList.add(new ItemsAdder("Lock the screen when the phone lies on the back", R.drawable.help_dl));
        arrayList.add(new ItemsAdder("Allow application to use administrator rights", R.drawable.help_admin));
        arrayList.add(new ItemsAdder("Show application's logotype on the top of the screen", R.drawable.help_shlogo));
        return arrayList;
    }
}