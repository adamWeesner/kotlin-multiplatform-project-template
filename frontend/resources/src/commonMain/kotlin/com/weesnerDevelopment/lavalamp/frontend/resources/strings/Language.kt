package com.weesnerDevelopment.lavalamp.frontend.resources.strings

internal enum class Language(val isoCode: String) {
    Unknown(""),
    Afar("aa"),
    Abkhazian("abk"),
    Avestan("ave"),
    Afrikaans("afr"),
    Akan("aka"),
    Amharic("amh"),
    Aragonese("arg"),
    Arabic("ara"),
    Assamese("asm"),
    Avaric("ava"),
    Aymara("aym"),
    Azerbaijani("aze"),
    Bashkir("bak"),
    Belarusian("bel"),
    Bulgarian("bul"),
    Bihari("bih"),
    Bislama("bis"),
    Bambara("bam"),
    Bengali("ben"),
    Tibetan("bod"),
    Breton("bre"),
    Bosnian("bos"),
    Catalan("cat"),
    Chechen("che"),
    Chamorro("cha"),
    Corsican("cos"),
    Cree("cre"),
    Czech("ces"),
    ChurchSlavic("chu"),
    Chuvash("chv"),
    Welsh("cym"),
    Danish("dan"),
    German("deu"),
    Divehi("div"),
    Dzongkha("dzo"),
    Ewe("ewe"),
    Greek("ell"),
    English("eng"),
    Esperanto("epo"),
    Spanish("spa"),
    Estonian("est"),
    Basque("eus"),
    Persian("fas"),
    Fulah("ful"),
    Finnish("fin"),
    Fijian("fij"),
    Faroese("fao"),
    French("fra"),
    Frisian("fry"),
    Irish("gle"),
    ScottishGaelic("gla"),
    Gallegan("glg"),
    Guarani("grn"),
    Gujarati("guj"),
    Manx("glv"),
    Hausa("hau"),
    Hindi("hin"),
    HiriMotu("hmo"),
    Croatian("hrv"),
    Haitian("hat"),
    Hungarian("hun"),
    Armenian("hye"),
    Herero("her"),
    Interlingua("ina"),
    Interlingue("ile"),
    Igbo("ibo"),
    SichuanYi("iii"),
    Inupiaq("ipk"),
    Indonesian("ind"),
    Ido("ido"),
    Icelandic("isl"),
    Italian("ita"),
    Inuktitut("iku"),
    Hebrew("heb"),
    Japanese("jpn"),
    Javanese("jav"),
    Georgian("kat"),
    Kongo("kon"),
    Kikuyu("kik"),
    Kwanyama("kua"),
    Kazakh("kaz"),
    Greenlandic("kal"),
    Khmer("khm"),
    Kannada("kan"),
    Korean("kor"),
    Kanuri("kau"),
    Kashmiri("kas"),
    Kurdish("kur"),
    Komi("kom"),
    Cornish("cor"),
    Kirghiz("kir"),
    Latin("lat"),
    Luxembourgish("ltz"),
    Ganda("lug"),
    Limburgish("lim"),
    Lingala("lin"),
    Lao("lao"),
    Lithuanian("lit"),
    LubaKatanga("lub"),
    Latvian("lav"),
    Malagasy("mlg"),
    Marshallese("mah"),
    Maori("mri"),
    Macedonian("mkd"),
    Malayalam("mal"),
    Mongolian("mon"),
    Moldavian("mol"),
    Marathi("mar"),
    Malay("msa"),
    Maltese("mlt"),
    Burmese("mya"),
    Nauru("nau"),
    NorwegianBokml("nob"),
    NorthNdebele("nde"),
    Nepali("nep"),
    Ndonga("ndo"),
    Dutch("nld"),
    NorwegianNynorsk("nno"),
    Norwegian("nor"),
    SouthNdebele("nbl"),
    Navajo("nav"),
    Nyanja("nya"),
    Occitan("oci"),
    Ojibwa("oji"),
    Oromo("orm"),
    Oriya("ori"),
    Ossetian("oss"),
    Panjabi("pan"),
    Pali("pli"),
    Polish("pol"),
    Pushto("pus"),
    Portuguese("por"),
    Quechua("que"),
    RaetoRomance("roh"),
    Rundi("run"),
    Romanian("ron"),
    Russian("rus"),
    Kinyarwanda("kin"),
    Sanskrit("san"),
    Sardinian("srd"),
    Sindhi("snd"),
    NorthernSami("sme"),
    Sango("sag"),
    Sinhalese("sin"),
    Slovak("slk"),
    Slovenian("slv"),
    Samoan("smo"),
    Shona("sna"),
    Somali("som"),
    Albanian("sqi"),
    Serbian("srp"),
    Swati("ssw"),
    SouthernSotho("sot"),
    Sundanese("sun"),
    Swedish("swe"),
    Swahili("swa"),
    Tamil("tam"),
    Telugu("tel"),
    Tajik("tgk"),
    Thai("tha"),
    Tigrinya("tir"),
    Turkmen("tuk"),
    Tagalog("tgl"),
    Tswana("tsn"),
    Tonga("ton"),
    Turkish("tur"),
    Tsonga("tso"),
    Tatar("tat"),
    Twi("twi"),
    Tahitian("tah"),
    Uighur("uig"),
    Ukrainian("ukr"),
    Urdu("urd"),
    Uzbek("uzb"),
    Venda("ven"),
    Vietnamese("vie"),
    Volapk("vol"),
    Walloon("wln"),
    Wolof("wol"),
    Xhosa("xho"),
    Yiddish("yid"),
    Yoruba("yor"),
    Zhuang("zha"),
    Chinese("zho"),
    Zulu("zul");

    companion object {
        fun get(code: String) = Language.values().find { it.isoCode == code } ?: Unknown
    }
}