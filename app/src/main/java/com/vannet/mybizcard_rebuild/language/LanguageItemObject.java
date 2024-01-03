package com.vannet.mybizcard_rebuild.language;

/*
 * Created by ChanYouvita on 1/18/18.
 */

public class LanguageItemObject {

    private int language_id;
    private String loca_key;
    private String iso_code;
    private String flag_code;
    private String nation_cd;

    /*
     * id
     */
    public int getLanguage_id() {
        return language_id;
    }

    /*
     * param language id
     */
    void setLanguage_id(int language_id) {
        this.language_id = language_id;
    }

    /*
     * loca key
     */
    public String getLoca_key() {
        return loca_key;
    }

    /*
     * param language loca key
     */
    void setLoca_key(String loca_key) {
        this.loca_key = loca_key;
    }

    /*
     * iso code
     */
    public String getIso_code() {
        return iso_code;
    }

    /*
     * param language iso code
     */
    void setIso_code(String iso_code) {
        this.iso_code = iso_code;
    }

    /*
     * flag
     */
    public String getFlag_code() {
        return flag_code;
    }

    /*
     * param language flag code
     */
    void setFlag_code(String flag_code) {
        this.flag_code = flag_code;
    }

    public String getNation_cd() {
        return nation_cd;
    }

    public void setNation_cd(String nation_cd) {
        this.nation_cd = nation_cd;
    }
}
