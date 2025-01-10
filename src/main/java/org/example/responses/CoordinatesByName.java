package org.example.responses;

import com.fasterxml.jackson.annotation.*;
import java.util.Map;

@JsonIgnoreProperties(ignoreUnknown = true)
public class CoordinatesByName extends Coordinates {
    @JsonProperty("local_names")
    // private LocalNames localNames;
    // sometimes local_names contains lot of params:
    // {br=Londrez, wo=Londar, kv=Лондон, et=London, mi=Rānana, pt=Londres, ay=London, uz=London, sc=Londra, sw=London,
    // ba=Лондон, su=London, pa=ਲੰਡਨ, kn=ಲಂಡನ್, jv=London, to=Lonitoni, kw=Loundres, fj=Lodoni, it=Londra, tl=Londres,
    // ta=இலண்டன், bo=ལོན་ཊོན།, ee=London, is=London, sm=Lonetona, tt=Лондон, no=London, nv=Tooh Dineʼé Bikin Haalʼá,
    // yi=לאנדאן, sv=London, sd=لنڊن, sq=Londra, ko=런던, ug=لوندۇن, ascii=London, ig=London, bm=London, eu=Londres,
    // pl=Londyn, kk=Лондон, na=London, gu=લંડન, or=ଲଣ୍ଡନ, he=לונדון, lv=Londona, nl=Londen, tr=Londra, ln=Lóndɛlɛ,
    // bs=London, lt=Londonas, gd=Lunnainn, ps=لندن, av=Лондон, ny=London, uk=Лондон, sk=Londýn, ms=London, wa=Londe,
    // ka=ლონდონი, az=London, bg=Лондон, si=ලන්ඩන්, vo=London, qu=London, ar=لندن, zh=伦敦, ff=London, hu=London,
    // mk=Лондон, ky=Лондон, my=လန်ဒန်မြို့, rm=Londra, mn=Лондон, co=Londra, mt=Londra, lb=London, fr=Londres,
    // be=Лондан, lo=ລອນດອນ, eo=Londono, sl=London, cv=Лондон, nn=London, zu=ILondon, ja=ロンドン, ml=ലണ്ടൻ,
    // km=ឡុងដ៍, ne=लन्डन, gl=Londres, st=London, oc=Londres, feature_name=London, om=Landan, am=ለንደን, sa=लन्डन्,
    // bh=लंदन, gn=Lóndyre, ga=Londain, th=ลอนดอน, os=Лондон, sh=London, hr=London, io=London, yo=Lọndọnu, es=Londres,
    // ru=Лондон, hi=लंदन, hy=Լոնդոն, af=Londen, kl=London, cu=Лондонъ, fo=London, te=లండన్, tw=London, ca=Londres,
    // sr=Лондон, fy=Londen, mr=लंडन, mg=Lôndôna, sn=London, fa=لندن, an=Londres, ku=London, tg=Лондон, en=London,
    // bn=লন্ডন, ce=Лондон, ur=علاقہ لندن, de=London, gv=Lunnin, ht=Lonn, ro=Londra, tk=London, ia=London, ab=Лондон,
    // fi=Lontoo, se=London, da=London, li=Londe, el=Λονδίνο, bi=London, ha=Landan, id=London, cs=Londýn, cy=Llundain,
    // so=London, ie=London, vi=Luân Đôn}
    // in this case better to use Map and find value by key
    private Map<String, String> localNames;
    @JsonProperty("lat")
    private double lat;
    @JsonProperty("lon")
    private double lon;
    @JsonProperty("country")
    private String country;
    @JsonProperty("state")
    private String state;

    @Override
    public String toString() {
        return "Coordinates by name {name=" + getName() + ", localNames=" + localNames.toString() + ", lat=" + lat + ", lon=" + lon + ", country= " + country + ", state= " + state + "}";
    }

    // getters and setters
    public Map<String, String> getLocalNames() {
        return localNames;
    }

    public String getLocalNameByKey(String key) {
        if (localNames == null || localNames.isEmpty()) {
            return null;
        }
        if (localNames.containsKey(key)) {
            return localNames.get(key);
        }
        return null;
    }

    public double getLat() {
        return lat;
    }

    public double getLon() {
        return lon;
    }

    public String getCountry() {
        return country;
    }

    public String getState() {
        return state;
    }
}