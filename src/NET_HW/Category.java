package NET_HW;

public enum Category {
    POP("강수확률"),
    PTY("강수형태"),
    pcp("1시간 강수량"),
    REH("습도"),
    SNO("1시간 신적설"),
    SKY("하늘상태"),
    TMP("1시간 기온"),
    TMN("일 최저기온"),
    TMX("일 최고기온"),
    UUU("풍속(동서성분)"),
    VVV("풍속(남북성분)"),
    WAV("파고"),
    VEC("풍향"),
    WSD("풍속"),
    // 초단기예보 관련 정보
    //////////////////////////////////
    RN1("1시간 강수량"),
    T1H("기온"),
    LGT("낙뢰");

    private final String desc;

    Category(String desc) {
        this.desc = desc;
    }
    public static Category getCategory(String value) {
        return Category.valueOf(value);
    }

    public String getDesc() {
        return desc;
    }
}
